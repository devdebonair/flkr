var express = require('express');
var app = express();
var http = require('http').Server(app);
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var FLKR = require('./java-bridge');
var db = new FLKR();

app.use(express.static(__dirname + '/public'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(methodOverride());
app.set('view engine', 'jade');


app.get('/api/top_movies', function(req, res){
	db.getTopMovies(function(data){
		res.json(data);
	});
});

app.get('/api/get_movie', function(req, res){
	db.getMovie(req.query.title, function(data){
		res.json(data);
	});
});

app.get('/api/get_genre', function(req, res){
	db.getGenre(req.query.genre, function(data){
		res.json(data);
	});
});

app.get('/api/get_director', function(req, res){
	db.getDirector(req.query.director, function(data){
		res.json(data);
	});
});

app.get('/api/get_actor', function(req, res){
	db.getActor(req.query.actor, function(data){
		res.json(data);
	});
});

app.get('/api/get_tag', function(req, res){
	db.getTag(req.query.tag, function(data){
		res.json(data);
	});
});

app.get('/api/top_directors', function(req, res){
	db.getTopDirectors(function(data){
		res.json(data);
	});
});

app.get('/api/top_actors', function(req, res){
	db.getTopActors(function(data){
		res.json(data);
	});
});

http.listen(process.env.PORT, function(){
	console.log("Server running at port %s", process.env.PORT);
});
