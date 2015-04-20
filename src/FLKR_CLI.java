import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;

public class FLKR_CLI
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        String username = null;
        String password = null;
        String databaseName;

        System.out.println("What is the name of the database you would like to connect to?");
        databaseName = scan.next();
        System.out.println("Do you set a username and password for " + databaseName + "? (yes/no)");
        String answer = scan.next();
        if(answer.equals("yes"))
        {
            System.out.println("What is your username?");
            username = scan.next();
            System.out.println("What is your password?");
            password = scan.next();
        }
        Database db;
        if(username == null || password == null)
        {
            System.out.println("swagger swaggington");
            db = new Database();
        }
        else
        {
            db = new Database(username, password, databaseName, true);
        }

        while(true)
        {
            System.out.println("Welcome to FLKR.");
            System.out.println("[1] Top Popular Movies");
            System.out.println("[2] Search by Title");
            System.out.println("[3] Search by Genre");
            System.out.println("[4] Search by Director");
            System.out.println("[5] Search by Actor");
            System.out.println("[6] Search by Tag");
            System.out.println("[7] Top 20 Directors");
            System.out.println("[8] Top 20 Directors");
            System.out.println("[0] Exit");

            int command = 99;
            command = scan.nextInt();

            switch(command)
            {
                case 1:     
                            LinkedList<HashMap<String,String>> result = db.getTopMovies(20);
                            for(HashMap<String,String> map : result)
                            {
                                for(String key : map.keySet())
                                {
                                    System.out.printf("%s\t\t", map.get(key));
                                }
                                System.out.println();
                            }
                            break;
                case 2:     //System.out.printf("%s\n\n", db.getMovie());
                            break;
                case 3:     //System.out.printf("%s\n\n", db.getGenre());
                            break;
                case 4:     //System.out.printf("%s\n\n", db.getDirector());
                            break;
                case 5:     //System.out.printf("%s\n\n", db.getActor());
                            break;
                case 6:     //System.out.printf("%s\n\n", db.getTag());
                            break;
                case 7:     //System.out.printf("%s\n\n", db.getTopDirectors());
                            break;
                case 8:     //System.out.printf("%s\n\n", db.getTopActors());
                            break;
                case 0:     System.out.println("Exiting...");
                            break;
                default:    System.out.println("Not a valid number.");
                            continue;
            }
        }
    }
}
