var spawn = require('child_process').spawn;
var JavaBridge = function()
{
}

JavaBridge.prototype = 
{
	getTopMovies: function(callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'top_movies']);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
	getMovie: function(title, callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'get_movie', title]);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
	getGenre: function(genre, callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'get_genre', genre]);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
	getDirector: function(director, callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'get_director', director]);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
	getActor: function(actor, callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'get_actor', actor]);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
	getTag: function(tag, callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'get_tag', tag]);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
	getTopDirectors: function(callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'top_directors']);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
	getTopActors: function(callback)
	{
		var flkr = spawn('java', ['-cp', '::../src::../lib/*', 'Flkr_Direct', 'top_actors']);
		flkr.stdout.on('data', function(data){
			try
			{
				data = JSON.parse(data.toString());
			}
			catch(e)
			{
				console.log(e);
				return;
			}
			callback(data);
		});
	},
}

module.exports = JavaBridge;