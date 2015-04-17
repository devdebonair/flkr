import java.sql.*;
import java.util.List;

public class Database
{
    private Connection connection;
    private String username;
    private String password;
    private String database;

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
            System.out.println("flkr database does not exist.");
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
    private void createDatabase()
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

    private void createSchemas()
    {
        try
        {
            System.out.println("Connecting to " + this.database + ".....");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/flkr", this.username, this.password);
            Statement createSchemaStatement = connection.createStatement();

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
    public String getTopMovies(int limit)
    {
        return null;
    }

    public String getMovie(int limit)
    {
        return null;
    }

    public String getGenre(int limit)
    {
        return null;
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
