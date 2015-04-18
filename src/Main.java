import java.util.ArrayList;
public class Main
{
    public static void main(String[] args)
    {
        String[][] movies;
        String[][] actors;
        String[][] directors;
        String[][] genres;
        String[][] tags;
        String[][] tagItems;
        String[] queries = new String[6];

        System.out.println("Parsing movies.dat");
        Reader reader = new ReaderMovie("../dataset/movies.dat");
        movies = reader.getItemsToInsert();
        queries[0] = reader.getQuery();

        System.out.println("Parsing movie_actors.dat");
        reader = new ReaderActor("../dataset/movie_actors.dat");
        actors = reader.getItemsToInsert();
        queries[1] = reader.getQuery();

        System.out.println("Parsing movie_directors.dat");
        reader = new ReaderDirector("../dataset/movie_directors.dat");
        directors = reader.getItemsToInsert();
        queries[2] = reader.getQuery();

        System.out.println("Parsing movie_genres.dat");
        reader = new ReaderGenre("../dataset/movie_genres.dat");
        genres = reader.getItemsToInsert();
        queries[3] = reader.getQuery();

        System.out.println("Parsing movie_tags.dat");
        reader = new ReaderTag("../dataset/movie_tags.dat");
        tags = reader.getItemsToInsert();
        queries[4] = reader.getQuery();

        System.out.println("Parsing tags.dat");
        reader = new ReaderTagItem("../dataset/tags.dat");
        tagItems = reader.getItemsToInsert();
        queries[5] = reader.getQuery();

        //Find unique actors
        System.out.println("Filtering unique actors... This may take a while");
        ArrayList<ArrayList<String>> actors_single = new ArrayList<ArrayList<String>>();
        outer_loop:
        for(int i = 0; i < actors.length; i++)
        {
            for(int j = 0; j < actors_single.size(); j++)
            {
                //If the actor_id has already been added to the actors database
                if(actors_single.get(j).contains(actors[i][1]))
                {
                    continue outer_loop;
                }
            }
            //Make array list out of the selected row and columns from movie_actor database
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(actors[i][1]);
            temp.add(actors[i][2]);
            actors_single.add(temp);
        }

        //Find unique directors
        System.out.println("Filtering unique directors... This may take a while");
        ArrayList<ArrayList<String>> directors_single = new ArrayList<ArrayList<String>>();
        outer_loop:
        for(int i = 0; i < directors.length; i++)
        {
            for(int j = 0; j < directors_single.size(); j++)
            {
                //If the director_id has already been added to the directors database
                if(directors_single.get(j).contains(directors[i][1]))
                {
                    continue outer_loop;
                }
            }
            //Make array list out of the selected row and columns from movie_director database
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(directors[i][1]);
            temp.add(directors[i][2]);
            directors_single.add(temp);
        }

        String[][] actors_single_array = convertTo2D(actors_single);
        String[][] directors_single_array = convertTo2D(directors_single);

        Database db = new Database();
        System.out.println("Inserting into movie table...");
        db.executeBatch(queries[0], movies, 2000);
        System.out.println("Inserting into actor table...");
        db.executeBatch("insert into actor (id, actor_name) values (?,?)", actors_single_array, 2000);
        System.out.println("Inserting into director table...");
        db.executeBatch("insert into director (id, director_name) values (?,?)", directors_single_array, 2000);
        System.out.println("Inserting into tag table...");
        db.executeBatch(queries[5], tagItems, 1000);
        System.out.println("Inserting into movie_actor table...");
        db.executeBatch(queries[1], actors, 2000);
        System.out.println("Inserting into movie_director table...");
        db.executeBatch(queries[2], directors, 2000);
        System.out.println("Inserting into movie_genres table...");
        db.executeBatch(queries[3], genres, 2000);
        System.out.println("Inserting into movie_tags table...");
        db.executeBatch(queries[4], tags, 2000);
        System.out.println("Database has been set up.");
    }

    public static String[][] convertTo2D(ArrayList<ArrayList<String>> lists)
    {
        String[][] array = new String[lists.size()][];
        String[] blankArray = new String[0];
        for(int i = 0; i < lists.size(); i++)
        {
            array[i] = lists.get(i).toArray(blankArray);
        }
        return array;
    }
}
