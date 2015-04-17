import java.util.Scanner;

public class FLKR_CLI
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        Database db = new Database();

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

            int command = null;
            command = scan.nextInt();

            switch(command)
            {
                case 1:     //System.out.printf("%s\n\n", db.getTopMovies());
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
