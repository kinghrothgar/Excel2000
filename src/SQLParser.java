import java.util.Scanner;
import database.Database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SQLParser
{
    private Database DB;

    public SQLParser(Database DB) {
        this.DB = DB;
    }


    // the main method only has contents for testing the parser, the final version should be empty
    public static void main(String args[]) throws Exception {
        Database testDB = new Database();
        SQLParser Parser = new SQLParser(testDB);
        System.out.println(Parser.query("INSERT INTO courses VALUES (COP3504, Advanced Programming Fundamentals, Horton)"));
        System.out.println(Parser.query("INSERT INTO courses (course, name, instructor) VALUES (COP3505, Advanced Programming Fundamentals2, Hortona)"));
        //System.out.println(Parser.query("SELECT * FROM courses"));
        //System.out.println(Parser.query("SELECT course FROM courses"));
        //System.out.println(Parser.query("SELECT course, name FROM courses"));
        System.out.println(Parser.query("DELETE FROM courses WHERE course='COP3505' AND instructor='Hortona'"));
        System.out.println(Parser.query("SELECT * FROM courses"));
    }

    public String query(String query) throws Exception {
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
        ArrayList<String> valueList = new ArrayList<>();
        // ArrayList of Strings to hold the list of columns provided in the query
        ArrayList<String> columnList = new ArrayList<>();
        // ArrayList of Strings that holds the list of all fields for the courses table, then students, then grades
        ArrayList<String> coursesFieldList = DB.getFieldList("courses");
        ArrayList<String> studentsFieldList = DB.getFieldList("students");
        ArrayList<String> gradesFieldList = DB.getFieldList("grades");
        Scanner parser = new Scanner(query);
        String ret = "";
        String in;
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
                	parser.close();
                    throw new IllegalArgumentException("Error: First word must be INSERT, SELECT, DELETE, or UPDATE");
                }
            }

            if(queryType == 0 && wordNumber == 3) {
                if(in.equalsIgnoreCase("courses") || in.equalsIgnoreCase("students") || in.equalsIgnoreCase("grades")) {
                    tableName = in;
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
                    Scanner subScanner = new Scanner(values.substring(1, values.length() - 1));
                    subScanner.useDelimiter(", *");
                    while(subScanner.hasNext()) {
                        valueList.add(subScanner.next());
                    }
                    DB.insert(tableName, valueList);
                    subScanner.close();
                    ret = "Done.";
                }
                else {
                    String columns = "";
                    columns += in;
                    while(!(in.contains(")"))) {
                        ++wordNumber;
                        in = parser.next();
                        columns += in;
                    }
                    Scanner subScanner = new Scanner(columns.substring(1, columns.length() - 1));
                    subScanner.useDelimiter(", *");
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
                    Scanner subScanner2 = new Scanner(values.substring(1, values.length() - 1));
                    subScanner2.useDelimiter(", *");
                    while(subScanner2.hasNext()) {
                        valueList.add(subScanner2.next());
                    }
                    DB.insert(tableName, columnList, valueList);
                    subScanner2.close();
                    ret = "Done.";
                }
            }
            if(queryType == 1 && wordNumber == 2) {
                ArrayList<ArrayList<Object>> returnArr = new ArrayList<>();
                if(in.equals("*")) {
                    ++wordNumber;
                    ++wordNumber;
                    in = parser.next();
                    in = parser.next();
                    if(in.equalsIgnoreCase("courses") || in.equalsIgnoreCase("students") || in.equalsIgnoreCase("grades")) {
                        tableName = in;
                    }
                    if (tableName.equalsIgnoreCase("courses")) {
                        for (String field : coursesFieldList) {
                            returnArr.add(DB.getField(tableName, field));
                        }
                        ret = outputFormatter(returnArr, coursesFieldList);
                    }
                    if (tableName.equalsIgnoreCase("students")) {
                        for (String field : studentsFieldList) {
                            returnArr.add(DB.getField(tableName, field));
                        }
                        ret = outputFormatter(returnArr, studentsFieldList);
                    }
                    if (tableName.equalsIgnoreCase("grades")) {
                        for (String field : gradesFieldList) {
                            returnArr.add(DB.getField(tableName, field));
                        }
                        ret = outputFormatter(returnArr, gradesFieldList);
                    }
                }
                else {
                	ArrayList<String> inputTables = new ArrayList<>();
                	ArrayList<String> inputFields = new ArrayList<>();
                	ArrayList<ArrayList<Object>> joinedList = new ArrayList<>(); // this list is for holding the final joined list as a result of a JOIN command
                	ArrayList<String> joinFields = new ArrayList<>();
                	boolean JWO = false; // does this statement include a JOIN, WHERE, or ORDER clause
                	if(in.contains(",")) {
                        String inField = "";
                        while (!in.contains("FROM")) {
                            inField += in;
                            in = parser.next();
                        }
                        Scanner fieldScanner = new Scanner(inField);
                        fieldScanner.useDelimiter(",");
                        while (fieldScanner.hasNext()) {
                            inputFields.add(fieldScanner.next());
                        }
                        fieldScanner.close();
                        in = parser.next();
                        inputTables.add(in);
                        while(parser.hasNext()) {
                        	in = parser.next();
                        	if(in.equalsIgnoreCase("JOIN")) {
                        		in = parser.next();
                                inputTables.add(in);
                        		in = parser.next();
                        		in = parser.next();
                        		if(in.contains(inputTables.get(0))) {
                        			joinFields.add(0, in.split("\\.")[1]);
                        		}
                        		else if(in.contains(inputTables.get(1))) { 
                        			joinFields.add(1, in.split("\\.")[1]);
                        		}
                        		else {
                        			throw new IllegalArgumentException("Syntax Error, make sure your join statement is correctly formed");
                        		}
                        		in = parser.next();
                        		if(in.contains(inputTables.get(0))) {
                        			joinFields.add(0, in.split("\\.")[1]);
                        		}
                        		else if(in.contains(inputTables.get(1))) { 
                        			joinFields.add(1, in.split("\\.")[1]);
                        		}
                        		else {
                        			throw new IllegalArgumentException("Syntax Error, make sure your join statement is correctly formed");
                        		}
                        		// TODO finish up creating joined list
                        	}
                        	if(in.equalsIgnoreCase("WHERE")) {
                        		// TODO Handle WHERE
                        	}
                        	if(in.equalsIgnoreCase("ORDER")) {
                        		// TODO Handle ORDER
                        	}
                        }
                        
                        if(!JWO) {
                        	for (String field : inputFields) {
                        		returnArr.add(DB.getField(inputTables.get(0), field));
                        	}
                        	ret = outputFormatter(returnArr, inputFields);
                        }
                        else {
                        	// TODO Handle cases where there is JOIN, WHERE, ORDER
                        }
                        
                	}
                    else {
                        inputFields.add(in);
                        in = parser.next(); // here it's on FROM
                        in = parser.next();
                        inputTables.add(in);
                        while(parser.hasNext()) {
                        	in = parser.next();
                        	if(in.equalsIgnoreCase("JOIN")) {
                        		in = parser.next();
                                inputTables.add(in);
                        		in = parser.next();
                        		in = parser.next();
                        		if(in.contains(inputTables.get(0))) {
                        			joinFields.add(0, in.split("\\.")[1]);
                        		}
                        		else if(in.contains(inputTables.get(1))) { 
                        			joinFields.add(1, in.split("\\.")[1]);
                        		}
                        		else {
                        			throw new IllegalArgumentException("Syntax Error, make sure your join statement is correctly formed");
                        		}
                        		in = parser.next();
                        		if(in.contains(inputTables.get(0))) {
                        			joinFields.add(0, in.split("\\.")[1]);
                        		}
                        		else if(in.contains(inputTables.get(1))) { 
                        			joinFields.add(1, in.split("\\.")[1]);
                        		}
                        		else {
                        			throw new IllegalArgumentException("Syntax Error, make sure your join statement is correctly formed");
                        		}
                        		// TODO finish up creating joined list
                        	}
                        	if(in.equalsIgnoreCase("WHERE")) {
                        		// TODO Handle WHERE
                        	}
                        	if(in.equalsIgnoreCase("ORDER")) {
                        		// TODO Handle ORDER
                        	}
                        }
                        
                        if(!JWO) {
                        	for (String field : inputFields) {
                        		returnArr.add(DB.getField(inputTables.get(0), field));
                        	}
                        	ret = outputFormatter(returnArr, inputFields);
                        }
                        else {
                        	// TODO Handle cases where there is JOIN, WHERE, ORDER
                        }
                        
                    }
                }
            }
            /// START DELETE BLOCK ///
            if(queryType == 2 && wordNumber == 3) // Sets the table name
            {
                if(in.equalsIgnoreCase("courses") || in.equalsIgnoreCase("students") || in.equalsIgnoreCase("grades")) 
                {
                    tableName = in;
                    if(query.split(" ").length == 3) 
                    {
                    	DB.delete(tableName);
                    	ret = "Done.";
                    }
                }
                else 
                {
                	parser.close();
                	throw new IllegalArgumentException(in + " is not a valid table name");
                }
            }
            if(queryType == 2 && wordNumber == 4) // Handles the additional conditions for the WHERE clause
            {
                if(in.equalsIgnoreCase("WHERE")) // Checks for WHERE text
                {
                    // Handle where clause
                    ArrayList<String> conditions = new ArrayList<>();
                    while(parser.hasNext()) // Adds conditions to the list
                    {
                        conditions.add(parser.next());
                    }

                    int conditionType = -1; // 0 for OR 1 for AND -1 for neither
                    if(conditions.size() > 1) {
                        if(conditions.get(1).equalsIgnoreCase("OR"))
                        {
                            conditionType = 0;
                        }
                        else
                        {
                            conditionType = 1;
                        }
                    }
                    
                    ArrayList<Integer> removeList = new ArrayList<>(); // Creates a list of records to be removd
                    if(conditionType == 0) // OR Query
                    {
                        for(String s : conditions) // Loop through Conditions
                        {
                            if(s.compareToIgnoreCase("OR") != 0) // Discard OR operators
                            {
                                String[] pieces = s.split("=|<>|>=|<=|<|>"); // Splits the condition up, excludes operator
                                pieces[1] = pieces[1].replace("'", ""); // Removes " ' " 's from value
                                ArrayList<Object> list = DB.getField(tableName, pieces[0]); // Gets a list of record values for the field being compared

                                for(int i = 0; i < list.size(); i++) // For each record,
                                {
                                    try
                                    {
                                        if(s.contains("=")) // Finds which operator was used, so that correct comparison may be made
                                        {
                                            if(s.contains(">"))
                                            {
                                                if(Integer.parseInt(list.get(i).toString()) >= Integer.parseInt(pieces[1]))
                                                    removeList.add(i);
                                            }
                                            else if(s.contains("<"))
                                            {
                                                if(Integer.parseInt(list.get(i).toString()) <= Integer.parseInt(pieces[1]))
                                                    removeList.add(i);
                                            }
                                            else
                                            {
                                                if(list.get(i).toString().compareTo(pieces[1]) == 0)
                                                    removeList.add(i);
                                            }
                                        }
                                        else if(s.contains("<>"))
                                        {
                                            if(list.get(i).toString().compareTo(pieces[1]) != 0)
                                                    removeList.add(i);
                                        }
                                        else if(s.contains(">"))
                                        {
                                            if(Integer.parseInt(list.get(i).toString()) > Integer.parseInt(pieces[1]))
                                                removeList.add(i);
                                        }
                                        else if(s.contains("<"))
                                        {
                                            if(Integer.parseInt(list.get(i).toString()) < Integer.parseInt(pieces[1]))
                                                removeList.add(i);
                                        }
                                        else 
                                        {
                                            throw new Exception("Invalid operator specified.");
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        return "Binary comparisons (> and <) can only be done with integers";
                                    }
                                }
                            }
                        }
                    }
                    else if(conditionType == -1) // Single criteria,  Everything else is the same
                    {
                        String[] pieces = conditions.get(0).split("=|<>|>=|<=|<|>");
                        pieces[1] = pieces[1].replace("'", "");
                        ArrayList<Object> list = DB.getField(tableName, pieces[0]);
                        
                        for(int i = 0; i < list.size(); i++)
                        {
                            try
                                {
                                    if(conditions.get(0).contains("=")) // =, <= or >=
                                    {
                                        if(conditions.get(0).contains(">"))
                                        {
                                            if(Integer.parseInt(list.get(i).toString()) >= Integer.parseInt(pieces[1]))
                                                removeList.add(i);
                                        }
                                        else if(conditions.get(0).contains("<"))
                                        {
                                            if(Integer.parseInt(list.get(i).toString()) <= Integer.parseInt(pieces[1]))
                                                removeList.add(i);
                                        }
                                        else
                                        {
                                            if(list.get(i).toString().compareTo(pieces[1]) == 0)
                                                removeList.add(i);
                                        }
                                    }
                                    else if(conditions.get(0).contains("<>"))
                                    {
                                        if(list.get(i).toString().compareTo(pieces[1]) != 0)
                                                removeList.add(i);
                                    }
                                    else if(conditions.get(0).contains(">"))
                                    {
                                        if(Integer.parseInt(list.get(i).toString()) > Integer.parseInt(pieces[1]))
                                            removeList.add(i);
                                    }
                                    else if(conditions.get(0).contains("<"))
                                    {
                                        if(Integer.parseInt(list.get(i).toString()) < Integer.parseInt(pieces[1]))
                                            removeList.add(i);
                                    }
                                    else 
                                    {
                                        throw new Exception("Invalid operator specified.");
                                    }
                                }
                                catch(Exception e)
                                {
                                    return "Binary comparisons (> and <) can only be done with integers";
                                }
                        }
                    }
                    else // AND operators, everything is the same, but condition checks are NEGATED
                    {
                        ArrayList<Object> temp = DB.getField(tableName, conditions.get(0).split("=|<>|>=|<=|<|>")[0]);
                        int max = temp.size();
                        int[] negate = new int[max];
                        for(String s : conditions)
                        {
                            if(s.compareToIgnoreCase("AND") != 0)
                            {
                                String[] pieces = s.split("=|<>|>=|<=|<|>");
                                pieces[1] = pieces[1].replace("'", "");
                                ArrayList<Object> list = DB.getField(tableName, pieces[0]);
                                for(int i = 0; i < list.size(); i++)
                                {
                                    try
                                    {
                                        if(s.contains("=")) // =, <= or >=
                                        {
                                            if(s.contains(">"))
                                            {
                                                if(Integer.parseInt(list.get(i).toString()) <= Integer.parseInt(pieces[1]))
                                                    negate[i] = 1;
                                            }
                                            else if(s.contains("<"))
                                            {
                                                if(Integer.parseInt(list.get(i).toString()) >= Integer.parseInt(pieces[1]))
                                                    negate[i] = 1;
                                            }
                                            else
                                            {
                                                if(list.get(i).toString().compareTo(pieces[1]) != 0)
                                                    negate[i] = 1;
                                            }
                                        }
                                        else if(s.contains("<>"))
                                        {
                                            if(list.get(i).toString().compareTo(pieces[1]) == 0)
                                                    removeList.add(i);
                                        }
                                        else if(s.contains(">"))
                                        {
                                            if(Integer.parseInt(list.get(i).toString()) > Integer.parseInt(pieces[1]))
                                                negate[i] = 1;
                                        }
                                        else if(s.contains("<"))
                                        {
                                            if(Integer.parseInt(list.get(i).toString()) < Integer.parseInt(pieces[1]))
                                                negate[i] = 1;
                                        }
                                        else 
                                        {
                                            throw new Exception("Invalid operator specified.");
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        return "Binary comparisons (> and <) can only be done with integers";
                                    }
                                }
                            }
                        }
                        for(int x = 0; x < max; x++) // Reverses negation, to be able to add ACTUAL items that survived the conditions to the list.
                        {
                            if(negate[x] != 1)
                                removeList.add(x);
                        }
                    }

                    Collections.sort(removeList,  // sorts the removal list in REVERSE ORDER, so that the indexes are not ruined by removing entires earlier in the list, thus shifting all elements after it.
                            new Comparator<Integer>() 
                            {
                                @Override
                                public int compare(Integer o1, Integer o2) 
                                {
                                    return o2.compareTo(o1);
                                }
                            }       );
                    for(Integer i : removeList) // Deletes the appropriate entries.
                    {
                        DB.delete(tableName, i.intValue());
                    }
                    ret = "Done.";
                }
                else
                {
                    DB.delete(tableName); // Delete all records in table
                    ret = "Done.";
                }
            }

        }
        return ret;
    }

    public String outputFormatter(ArrayList<ArrayList<Object>> dataArr, ArrayList<String> fieldsArr) {
        int[] columnWidths = new int[fieldsArr.size()];
        int[] columnWidths2 = new int[fieldsArr.size()];
    	int tableLength = 0;
        String output = "";
        for (int i = 0; i < fieldsArr.size(); i++) {
            columnWidths[i] = fieldsArr.get(i).length();
        }
        for (int i = 0; i < dataArr.size(); i++) {
        	ArrayList<Object> fArr = dataArr.get(i);
            for (Object o : fArr) {
                if(o.toString().length() > columnWidths2[i]) {
                	columnWidths2[i] = o.toString().length();
                }
            }
        }
        for (int i = 0; i < columnWidths.length; i++) {
        	if(columnWidths[i] < columnWidths2[i])
        		columnWidths[i] = columnWidths2[i];
        }
        for (int i = 0; i < columnWidths.length; i++) {
        	tableLength += columnWidths[i];
        }
        tableLength += columnWidths.length * 4;
        for (int i = 0; i < tableLength; i++) {
            output += "-";
        }
        output += "\n";
        for (int i = 0; i < columnWidths.length; i++) {
            output += "| " + fieldsArr.get(i);
            for (int j = 0; j < columnWidths[i] - fieldsArr.get(i).length(); j++) {
            	output += " ";
            }
            output += " |";
        }
        output += "\n";
        for (int i = 0; i < tableLength; i++) {
            output += "-";
        }
        output += "\n";
        for (int j = 0; j < dataArr.get(0).size(); j++) {
            for (int i = 0; i < dataArr.size(); i++) {
                output += "| " + dataArr.get(i).get(j);
                for (int k = 0; k < columnWidths[i] - dataArr.get(i).get(j).toString().length(); k++) {
                	output += " ";
                }
                output += " |";
            }
            output += "\n";
        }
        for (int i = 0; i < tableLength; i++) {
            output += "-";
        }
        return output;
    }
}