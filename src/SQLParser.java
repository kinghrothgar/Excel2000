import java.util.Arrays;
import java.util.Scanner;
import database.Database;
import java.util.ArrayList;

public class SQLParser
{
    private Database DB;

    public SQLParser(Database DB) {
        this.DB = DB;
    }


    // the main method only has contents for testing the parser, the final version should be empty
    public static void main(String args[]) {
        Database testDB = new Database();
        SQLParser Parser = new SQLParser(testDB);
        String test = Parser.query("INSERT INTO courses VALUES (COP3504, Advanced Programming Fundamentals, Horton)");
        String test2 = Parser.query("INSERT INTO courses (course, name, instructor) VALUES (COP3504, Advanced Programming Fundamentals, Horton)");
    }

    public String query(String query) {
        // queryType is set depending on what kind of query is given (e.g. INSERT, DELETE, etc)
        // INSERT = 0
        // SELECT = 1
        // DELETE = 2
        // UPDATE = 3
        int queryType = -1;
        // this is just a check to see if it's the first word or not, so we don't have to check the string every time to see if it's UPDATE, INSERT, etc
        int wordNumber = 0;
        // this holds the name of the table, can be either courses, students, or grades
        String tableName = "";
        // ArrayList of Strings to hold the list of values provided in the query
        ArrayList<String> valueList = new ArrayList<String>();
        // ArrayList of Strings to hold the list of columns provided in the query
        ArrayList<String> columnList = new ArrayList<String>();
        // ArrayList of Strings that holds the list of all fields for the courses table, then students, then grades
        ArrayList<String> coursesFieldList = DB.getFieldList("courses");
        ArrayList<String> studentsFieldList = DB.getFieldList("students");
        ArrayList<String> gradesFieldList = DB.getFieldList("grades");
        Scanner parser = new Scanner(query);
        String ret = "";
        String in = "";
        while(parser.hasNext()) {
            in = parser.next();
            ++wordNumber;
            if(wordNumber == 1) {
                if(in.equalsIgnoreCase("INSERT")) {
                    queryType = 0;
                }
                else if(in.equalsIgnoreCase("SELECT")) {
                    queryType = 1;
                }
                else if(in.equalsIgnoreCase("DELETE")) {
                    queryType = 2;
                }
                else if(in.equalsIgnoreCase("UPDATE")) {
                    queryType = 3;
                }
                else {
                    throw new IllegalArgumentException("First word must be INSERT, SELECT, DELETE, or UPDATE");
                }
            }

            if(queryType == 0 && wordNumber == 3) {
                if(in.equalsIgnoreCase("courses") || in.equalsIgnoreCase("students") || in.equalsIgnoreCase("grades")) {
                    tableName = in;
                }
                else {
                    throw new IllegalArgumentException("Invalid Table Name");
                }
            }

            if(queryType == 0 && wordNumber == 4) {
                if(in.equalsIgnoreCase("VALUES")) {
                    String values = "";
                    while(!(in.contains(")"))) {
                        ++wordNumber;
                        in = parser.next();
                        values += in;
                    }
                    Scanner subScanner = new Scanner(values.substring(1, values.length() - 1)).useDelimiter(", *");
                    while(subScanner.hasNext()) {
                        valueList.add(subScanner.next());
                    }
                    System.out.println(tableName);
                    System.out.println(valueList.toString());
                    DB.insert(tableName, valueList);
                    subScanner.close();
                }
                else {
                    String columns = "";
                    columns += in;
                    while(!(in.contains(")"))) {
                        ++wordNumber;
                        in = parser.next();
                        if (tableName.equalsIgnoreCase("courses")) {
                            for (String field : coursesFieldList) {
                                if(!(in.toLowerCase().contains(field))) {
                                    throw new IllegalArgumentException("Must give a valid column name");
                                }
                            }
                        }
                        if (tableName.equalsIgnoreCase("students")) {
                            for (String field : studentsFieldList) {
                                if(!(in.toLowerCase().contains(field))) {
                                    throw new IllegalArgumentException("Must give a valid column name");
                                }
                            }
                        }
                        if (tableName.equalsIgnoreCase("grades")) {
                            for (String field : gradesFieldList) {
                                if(!(in.toLowerCase().contains(field))) {
                                    throw new IllegalArgumentException("Must give a valid column name");
                                }
                            }
                        }
                        columns += in;
                    }
                    Scanner subScanner = new Scanner(columns.substring(1, columns.length() - 1)).useDelimiter(", *");
                    while(subScanner.hasNext()) {
                        columnList.add(subScanner.next());
                    }
                    in = parser.next();
                    String values = "";
                    while(!(in.contains(")"))) {
                        ++wordNumber;
                        in = parser.next();
                        values += in;
                    }
                    subScanner.close();
                    Scanner subScanner2 = new Scanner(values.substring(1, values.length() - 1)).useDelimiter(", *");
                    while(subScanner2.hasNext()) {
                        valueList.add(subScanner2.next());
                    }
                    DB.insert(tableName, columnList, valueList);
                    subScanner2.close();
                }
            }
            if(queryType == 1 && wordNumber == 2) {
                ArrayList<ArrayList<Object>> returnArr = new ArrayList<ArrayList<Object>>();
                if(in.equals("*")) {
                    ++wordNumber;
                    in = parser.next();
                    in = parser.next();
                    if(in.equalsIgnoreCase("courses") || in.equalsIgnoreCase("students") || in.equalsIgnoreCase("grades")) {
                        tableName = in;
                    }
                    else {
                        throw new IllegalArgumentException("Invalid Table Name");
                    }

                    if (tableName.equalsIgnoreCase("courses")) {
                        for (String field : coursesFieldList) {
                            returnArr.add(DB.getField(tableName, field));
                        }
                    }
                    if (tableName.equalsIgnoreCase("students")) {
                        for (String field : studentsFieldList) {
                            returnArr.add(DB.getField(tableName, field));
                        }
                    }
                    if (tableName.equalsIgnoreCase("grades")) {
                        for (String field : gradesFieldList) {
                            returnArr.add(DB.getField(tableName, field));
                        }
                    }
                }
                System.out.print(outputFormatter(returnArr));
            }
            if(queryType == 2 && wordNumber == 3)
            {
                 if(in.equalsIgnoreCase("courses") || in.equalsIgnoreCase("students") || in.equalsIgnoreCase("grades")) {
                    tableName = in;
                }
                else {
                    throw new IllegalArgumentException("Invalid Table Name");
                }
            }
            if(queryType == 2 && wordNumber == 4)
            {
                if(in.equalsIgnoreCase("WHERE"))
                {
                    // Handle where clause
                }
                else
                {
                    DB.delete(tableName); // Delete all records in table
                }
            }

        }
        return ret;
        parser.close();
    }

    public String outputFormatter(ArrayList<ArrayList<Object>> inArr) {
        return "";
    }
}
