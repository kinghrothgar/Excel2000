import database.Database;
import java.util.Map;
import java.util.Scanner;

public class CLI {

    private static Database DB;
    private static String role;

    public static void main(String[] args) {
        // Use Scanner for general navigation
        Scanner userInput = new Scanner( System.in );
        // Numbers are used for menu selection (e.g. enter 1 to initialize database, 2 to login, etc)
        // example code below
        System.out.println("Please choose from the following options:");
        System.out.println("1. Initialize Database");
        System.out.println("2. Login to Role");
        System.out.println("3. Exit");
        if(Integer.parseInt(userInput.next()) == 1) {
            System.out.println("Please choose from the following options:");
            System.out.println("1. Direct Data Entry");
            System.out.println("2. Load table from file");
            System.out.println("3. Store table to file");
            if(Integer.parseInt(userInput.next()) == 1) {
                DB = new Database();
            }
        }
    }
    
	public static String select(String command)
	{
		return "Output";
	}
	public static String update(String command)
	{
		return "Output";
	}
	public static String delete(String command)
	{
		return "Output";
	}
	public static String insert(String command)
	{
		return "Output";
	}
	
	public static String avg(String command)
	{
		
	}
	public static String max(String command){
		
	}
	public static String min(String command){
		
	}
	public static String sum(String command){
		
	}
	public static String count(String command){
		
	}

    public String help() {
        // queries current user information and prints permissions
    }

    public void load(File file) {
        // instantiates DB from a file
        DB = Database(file);
    }

    public void save() {
        // saves the database to a file
    }
}
