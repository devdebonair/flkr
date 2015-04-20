import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
public class Database
{
    private Connection connection;
    private String username;
    private String password;
    private String database;
    public String QUERY;

    public Database()
    {
        this("root", "", "flkr", false);
    }

    public Database(String username, String password, String database)
    {
        this(username, password, database, false);
    }

    public Database(String username, String password, String database, boolean isStrict)
    {
        this.username = username;
        this.password = password;
        this.database = database;
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + this.database, this.username, this.password);
            createSchemas();
        }
        catch(Exception e)
        {
            System.err.println(e);
            System.out.println(this.database + " database does not exist.");
            if(!isStrict)
            {
                createDatabase();
                try
                {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
                    createSchemas();
                }
                catch(Exception ex)
                {
                    System.err.println(ex);
                    System.out.println("Make sure MySql Server is on and running on port 3306.");
                    System.exit(1);
                }
            }
        }
    }

    //If database does not exist, create new one called flkr
    public void createDatabase()
    {
        try
        {
            System.out.println("Connecting to mysql.....");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", this.username, this.password);
            String sql = "CREATE DATABASE " + this.database;
            Statement createDBStatement = connection.createStatement();
            System.out.println("Creating " + this.database + " database.....");
            createDBStatement.executeUpdate(sql);
            System.out.println(this.database + " database created.....");
            // System.out.println("Closing connection.....");
            // this.connection.close();
        }
        catch(Exception e)
        {
            System.err.println(e);
            // if(this.connection != null)
            // {
            //     this.connection.close();
            // }
            System.exit(1);
        }
    }

    public void createSchemas()
    {
        try
        {
            System.out.println("Connecting to " + this.database + ".....");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/flkr", this.username, this.password);
            Statement createSchemaStatement = connection.createStatement();

            // sql statements to create tables
            String sqlTableMovie = "CREATE TABLE IF NOT EXISTS movie " +
                                        "(id INTEGER," +
                                        "title_english VARCHAR(255)," +
                                        "title_spanish VARCHAR(255)," +
                                        "image_url_imdb VARCHAR(255)," +
                                        "year INTEGER," +
                                        "rt_critic_rating DECIMAL," +
                                        "rt_critic_num_reviews INTEGER," +
                                        "rt_critic_num_fresh INTEGER," +
                                        "rt_critic_num_rotten INTEGER," +
                                        "rt_critic_score DECIMAL," +
                                        "rt_critic_top_rating DECIMAL," +
                                        "rt_critic_top_num_reviews INTEGER," +
                                        "rt_critic_top_num_fresh INTEGER," +
                                        "rt_critic_top_num_rotten INTEGER," +
                                        "rt_critic_top_score DECIMAL," +
                                        "rt_audience_rating DECIMAL," +
                                        "rt_audience_num_rating DECIMAL," +
                                        "rt_audience_score DECIMAL," +
                                        "image_url_rt VARCHAR(255)," +
                                        "PRIMARY KEY (id))";

            String sqlTableActor = "CREATE TABLE IF NOT EXISTS actor" +
                                    "(id VARCHAR(255)," +
                                    "actor_name VARCHAR(255)," +
                                    "PRIMARY KEY(id))";
            
            String sqlTableDirector = "CREATE TABLE IF NOT EXISTS director" +
                                        "(id VARCHAR(255)," +
                                        "director_name VARCHAR(255)," + 
                                        "PRIMARY KEY(id))";

            String sqlTableTag = "CREATE TABLE IF NOT EXISTS tag " +
                                        "(id INTEGER," +
                                        "tag_name VARCHAR(255)," +
                                        "PRIMARY KEY (id))";

            String sqlTableMovieActor = "CREATE TABLE IF NOT EXISTS movie_actor " +
                                        "(movie_id INTEGER," +
                                        "actor_id VARCHAR(255)," +
                                        "actor_name VARCHAR(255)," +
                                        "ranking INTEGER," +
                                        "FOREIGN KEY (movie_id) REFERENCES movie(id)," + 
                                        "FOREIGN KEY (actor_id) REFERENCES actor(id))";

            String sqlTableMovieDirector = "CREATE TABLE IF NOT EXISTS movie_director " +
                                            "(movie_id INTEGER," +
                                            "director_id VARCHAR(255)," +
                                            "director_name VARCHAR(255)," +
                                            "FOREIGN KEY (movie_id) REFERENCES movie(id)," + 
                                            "FOREIGN KEY (director_id) REFERENCES director(id))";

            String sqlTableGenre = "CREATE TABLE IF NOT EXISTS movie_genre " +
                                        "(movie_id INTEGER," +
                                        "genre VARCHAR(255)," +
                                        "FOREIGN KEY (movie_id) REFERENCES movie(id))";

            String sqlTableMovieTag = "CREATE TABLE IF NOT EXISTS movie_tag " +
                                        "(movie_id INTEGER," +
                                        "tag_id INTEGER," +
                                        "tag_weight INTEGER," +
                                        "FOREIGN KEY (tag_id) REFERENCES tag(id)," +
                                        "FOREIGN KEY (movie_id) REFERENCES movie(id))";
            
            //create database tables
            System.out.println("Creating movie Table....");
            createSchemaStatement.executeUpdate(sqlTableMovie);
            System.out.println("Creating actor Table....");
            createSchemaStatement.executeUpdate(sqlTableActor);
            System.out.println("Creating director Table....");
            createSchemaStatement.executeUpdate(sqlTableDirector);
            System.out.println("Creating tag Table....");
            createSchemaStatement.executeUpdate(sqlTableTag);
            System.out.println("Creating movie_tag Table....");
            createSchemaStatement.executeUpdate(sqlTableMovieTag);
            System.out.println("Creating movie_actor Table....");
            createSchemaStatement.executeUpdate(sqlTableMovieActor);
            System.out.println("Creating movie_director Table....");
            createSchemaStatement.executeUpdate(sqlTableMovieDirector);
            System.out.println("Creating movie_genre Table....");
            createSchemaStatement.executeUpdate(sqlTableGenre);
        }
        catch(Exception e)
        {
            System.err.println(e);
            // if(this.connection != null)
            // {
            //     this.connection.close();
            // }
            System.exit(1);
        }
    }

    public boolean executeBatch(String command, String[][] values, int bufferSize)
    {
        int count = 0; //Keep track of buffer
        PreparedStatement ps;
        try
        {
            ps = this.connection.prepareStatement(command);
            for(int i = 0; i < values.length; i++)
            {
                for(int j = 0; j < values[i].length; j++)
                {
                    if(values[i][j].equals("\\N"))
                    {
                        ps.setNull(j+1, Types.NULL);
                    }
                    else
                    {
                        ps.setString(j+1, values[i][j]);
                    }
                }
                ps.addBatch();

                if(++count % bufferSize == 0)
                {
                    ps.executeBatch();
                }
            }
            ps.executeBatch(); // insert remaining records
            ps.close();
            return true;
        }
        catch(Exception e)
        {
            System.err.println(e);
            System.exit(1);
        }
        return true;
    }

    //Queries
    public LinkedList<HashMap<String,String>> getTopMovies(int limit)
    {
        try
        {        
            String query = "SELECT title_english, title_spanish, rt_audience_score, year, image_url_rt, image_url_imdb FROM movie ORDER BY rt_audience_score DESC LIMIT 0, " + limit;
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            LinkedList<HashMap<String, String>> listOfResults = new LinkedList<HashMap<String,String>>();
            HashMap<String, String> map;
            while(result.next())
            {
                map = new HashMap<String, String>();
                map.put("title_english", result.getString(1));
                map.put("title_spanish", result.getString(2));
                map.put("rt_audience_score", result.getString(3));
                map.put("year", result.getString(4));
                map.put("image_url_rt", result.getString(5));
                map.put("image_url_imdb", result.getString(6));
                listOfResults.add(map);
            }
            return listOfResults;
        }
        catch(Exception e)
        {
            System.err.println(e);
            return null;
        }
    }

    public LinkedList<HashMap<String,String>> getMovie(String title, int limit)
    {
        try
        {
            String query = "SELECT title_english, title_spanish, year, rt_audience_score, image_url_rt, image_url_imdb FROM movie WHERE title_english = '" + title + "' LIMIT 0, " + limit;
            System.out.println(query);
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", result.getString(1));
            map.put("year", result.getString(2));
            map.put("rt_audience_score", result.getString(3));
            map.put("image_url_rt", result.getString(4));
            map.put("image_url_imdb", result.getString(5));
            LinkedList<HashMap<String,String>> listOfResults = new LinkedList<HashMap<String,String>>();
            listOfResults.add(map);
            return listOfResults;
        }
        catch(Exception e)
        {
            System.err.println(e);
            return null;
        }
    }

    public LinkedList<HashMap<String,String>> getGenre(String genre, int limit)
    {
        try
        {
            String query = "SELECT title_english, title_spanish, year, rt_audience_score, image_url_rt, image_url_imdb FROM movie AS m, movie_genre AS g WHERE g.genre = '" + genre + "' AND m.id = g.movie_id ORDER BY rt_audience_score DESC LIMIT 0, " + limit;
            Statement statement = this.connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            LinkedList<HashMap<String, String>> listOfResults = new LinkedList<HashMap<String,String>>();
            HashMap<String, String> map;
            while(result.next())
            {
                map = new HashMap<String, String>();
                map.put("title_english", result.getString(1));
                map.put("title_spanish", result.getString(2));
                map.put("rt_audience_score", result.getString(3));
                map.put("year", result.getString(4));
                map.put("image_url_rt", result.getString(5));
                map.put("image_url_imdb", result.getString(6));
                listOfResults.add(map);
            }
            return listOfResults;
        }
        catch(Exception e)
        {
            System.err.println(e);
            return null;
        }
    }

    public String getDirector(int limit)
    {
        return null;
    }

    public String getActor(int limit)
    {
        return null;
    }

    public String getTag(int limit)
    {
        return null;
    }

    public String getTopDirectors(int limit)
    {
        return null;
    }

    public String getTopActors(int limit)
    {
        return null;
    }
}
