import org.json.simple.JSONValue;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

public class Flkr_Direct
{
	public static void main(String[] args)
	{
		Database db = new Database();
		if(args[0].equals("top_movies"))
		{
			List retval = db.getTopMovies(20);
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
		if(args[0].equals("get_movie"))
		{
			List retval = db.getMovie(args[1].replaceAll("_"," "), 20);
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
		if(args[0].equals("get_genre"))
		{
			List retval = db.getGenre(args[1].replaceAll("_"," "), 20);
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
		if(args[0].equals("get_director"))
		{
			List retval = db.getDirector(args[1].replaceAll("_"," "));
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
		if(args[0].equals("get_actor"))
		{
			List retval = db.getActor(args[1].replaceAll("_"," "));
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
		if(args[0].equals("get_tag"))
		{
			List retval = db.getTag(args[1].replaceAll("_"," "));
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
		if(args[0].equals("top_directors"))
		{
			List retval = db.getTopDirectors(20);
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
		if(args[0].equals("top_actors"))
		{
			List retval = db.getTopActors(20);
			String jsonString = JSONValue.toJSONString(retval);
			System.out.print(jsonString);
		}
	}
}
