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

    public String select(String table, Map<String,String> map) {
        // selects from table where String key is String value in the map, if the strings are not empty
    }

    public String insert(String table, Map<String,String> map) {
        // inserts into table the key in the map, and if the value is not empty, into the columns specified by the value
    }

    public String update(String table, Map<String,String> map) {
        // alters table by adding all keys in the map, if value is not set, at the end, value can specify location of new column
    }

    public String delete(String table, Map<String,String> map) {
        // deletes from table where String key is String value in the map, if the strings are not empty
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
