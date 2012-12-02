import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import database.Database;

/**
 * Interactive user interface which allows a user to query and manage a database
 *
 */
public class CommandLineInterface
{
	private PrintStream out;
	private InputStream in;
	
	/**
	 * Starts the SQL Query Command-line interface.
	 * User can choose options using numeric input.
	 * Data can be manipulated through alphanumeric input.
	 */
	public static void main(String[] args)
	{
		CommandLineInterface cli = new CommandLineInterface(System.out, System.in);
		cli.start();
	}
	
	private Scanner input;
	private User user;
	private Database DB;
	private String filepath;
	
	/**
	 * Establish an interface using I/O streams.
	 * @param out Output stream in which to display information
	 * @param in Input stream by which the user can send information
	 */
	public CommandLineInterface(PrintStream out, InputStream in)
	{
		this.out = out;
		this.in = in;
		initializeScanner();
	}

	public void start()
	{
		out.println("SQL Command Line Interface\n");
		mainMenu(true);
	}
	
	/*
	 *  Menus (Only accessible through user commands [main menu called initially])
	 */
	
	private void mainMenu(boolean listHelp)
	{
		out.println("Main Menu");
		/*
		 * Options:
		 * 1. Initialize database
		 * 2. Login to role
		 * 3. Exit
		 */
		// If DB already initialized, choice 1 should read "Close database"
		String option1 = DB == null ? "Initialize database" : "Close database";
		String[] options = {option1, "Login to role", "Exit"};
		if (listHelp) printOptions(options);
		switch(getChoice(options.length))
		{
		case 1:
			if (DB == null)
				initializeDatabase(true);
			else
				closeDatabase(true);
			break;
		case 2:
			loginToRole(true);
			break;
		case 3:
			// Exit
			if (DB != null)
				closeDatabase(false);
			closeScanner();
			out.println("Goodbye!");
			break;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	private void initializeDatabase(boolean listHelp)
	{
		out.println("Initialize database");
		/*
		 * Options:
		 * 1. Direct Data Entry
		 * 2. Load table from File
		 * 3. Store table to File
		 * 4. Back to main menu
		 */
		String[] options = {"Direct data entry", "Load table from file",
				"Store table to file", "Go back to main menu"};
		if (listHelp) printOptions(options);
		switch(getChoice(options.length))
		{
		case 1:
			directDataEntry();
			break;
		case 2:
			loadFile();
			break;
		case 3:
			saveFile(true);
			break;
		case 4:
			mainMenu(true);
			break;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	private void saveFile(boolean backToMain)
	{
		out.println("Saving to " + filepath);
		save();
		if (backToMain) mainMenu(true);
	}
	
	private void closeDatabase(boolean backToMain)
	{
		out.println("closing DB");
		// TODO: Only prompt if changes were made
		if (prompt("Save changes? (Y/N)").equalsIgnoreCase("Y"))
			saveFile(false);
		DB = null;
		if (backToMain) mainMenu(true);
	}
	
	private void loginToRole(boolean listHelp)
	{
		out.println("Login to role:");
		/*
		 * Options:
		 * 1. Student
		 * 2. Teacher
		 * 3. Administrator
		 * 4. Back to main menu
		 */
		String[] options = {"Student", "Teacher", "Administrator", "Back to main menu"};
		if (listHelp) printOptions(options);
		switch(getChoice(options.length))
		{
		case 1:
			user = new Student();
			makeSelection(true);
			break;
		case 2:
			user = new Instructor();
			makeSelection(true);
			break;
		case 3:
			user = new Administrator();
			makeSelection(true);
			break;
		case 4:
			mainMenu(true);
			break;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	private void makeSelection(boolean listHelp)
	{
		out.printf("Logged in as %s. Please make a selection.%n", user.name);
		
		int highestIdx = 0;
		int basicInfoIdx = 0, modifyGradesIdx = 0, putStudentsIdx = 0, createCoursesIdx = 0, seeStudentGradesIdx = 0,
				seeCourseGradesIdx = 0, rmStudentsIdx = 0, helpIdx = 0, logoutIdx = 0;
		
		// make list of options based on user permissions, associating each option with a number
		ArrayList<String> options = new ArrayList<String>(0);
		if (user.canSeeBasicCourseInfo()){			basicInfoIdx = ++highestIdx;		options.add("See basic course Info");}
		if (user.canModifyGrades()){				modifyGradesIdx = ++highestIdx;		options.add("Modify grades");}
		if (user.canPutStudentsInCourses()){		putStudentsIdx = ++highestIdx;		options.add("Put students in courses");}
		if (user.canCreateCourses()){				createCoursesIdx = ++highestIdx;	options.add("Create courses");}
		if (user.canSeeAllGradesForOneStudent()){	seeStudentGradesIdx = ++highestIdx;	options.add("See student grades");}
		if (user.canSeeAllGradesForOneCourse()){	seeCourseGradesIdx = ++highestIdx;	options.add("See course grades");}
		if (user.canRemoveStudentFromCourse()){		rmStudentsIdx = ++highestIdx;		options.add("Remove students from courses");}
		if (user.canGetHelp()){						helpIdx = ++highestIdx;				options.add("Help");}
		if (user.canLogout()){						logoutIdx = ++highestIdx;			options.add("Logout");}
		
		String[] optionsArray = new String[options.size()];
		for (int i = 0; i < options.size(); i++)
			optionsArray[i] = options.get(i).toString();
		if (listHelp) printOptions(optionsArray);
		int choice = getChoice(options.size());
		if (choice < 1)
		{
			out.println("Invalid selection.");
			makeSelection(false);
		}
		else if (choice == basicInfoIdx) {
			out.println("[GET BASIC INFO]");// TODO: Get basic info
			makeSelection(false);
		}
		else if (choice == modifyGradesIdx) {
			out.println("[MODIFY GRADES]");// TODO: Modify grades
			makeSelection(false);
		}
		else if (choice == putStudentsIdx) {
			out.println("[PUT STUDENTS IN COURSES]");// TODO: Put students in courses
			makeSelection(false);
		}
		else if (choice == createCoursesIdx) {
			out.println("[CREATE COURSES]");// TODO: Create courses
			makeSelection(false);
		}
		else if (choice == seeStudentGradesIdx) {
			out.println("[SEE STUDENT GRADES]");// TODO: See student grades
			makeSelection(false);
		}
		else if (choice == seeCourseGradesIdx) {
			out.println("course grades");// TODO: See course grades
			makeSelection(false);
		}
		else if (choice == rmStudentsIdx) {
			out.println("rm students");// TODO: Remove students from courses
			makeSelection(false);
		}
		else if (choice == helpIdx)
			help();
		else if (choice == logoutIdx)
			loginToRole(true);// Logout
		else
		{
			out.println("Invalid selection.");
			makeSelection(false);
		}
	}
	
	/*
	 * Database methods
	 */
	
	private void directDataEntry()
	{
		DB = new Database();
		String query;
		do
		{
			query = prompt("Input SQL");
			// TODO: pass query to parser
		} while (!query.equals(""));
		out.println("Manual sql");// TODO: delete this line
		out.println("direct data entry");	// TODO: delete this line
		mainMenu(true);
	}
	
	private void getBasicInfo()
	{
		
	}
	
	private void select(String command)	// TODO
	{
		out.println("select");
	}
	private void update(String command) // TODO
	{
		out.println("update");
	}
	private void delete(String command)	// TODO
	{
		out.println("delete");
	}
	private void insert(String command)	// TODO
	{
		out.println("insert");
	}
	
	private void avg(String command)	// TODO
	{
		out.println("avg");
	}
	private void max(String command){	// TODO
		out.println("max");
	}
	private void min(String command){	// TODO
		out.println("min");
	}
	private void sum(String command){	// TODO
		out.println("sum");
	}
	private void count(String command){	// TODO
		out.println("count");
	}

    private void help() {
        makeSelection(true);// queries current user information and prints permissions
    }

    private void loadFile() {
        // TODO: instantiates DB from a file
    	String inFilepath = prompt("Load from file:");
    	if (!inFilepath.endsWith(".xml"))
    		inFilepath += ".xml";
    	try
    	{
    		BufferedReader loadIn = new BufferedReader(new FileReader(inFilepath));
    		// TODO: read table
    		loadIn.close();
    	}
    	catch (IOException e)
    	{
    		out.println("Error: Invalid filepath");
    	}
    	finally
    	{
    		mainMenu(true);
    	}
        //DB = new Database(file);
        out.println("loaded file");
    }

    private void save() {
    	String outFilepath = prompt("Save to file:");
    	if (!outFilepath.endsWith(".xml"))
    		outFilepath += ".xml";
		try
		{
			PrintWriter saveOut = new PrintWriter(new FileWriter(outFilepath));
			// TODO: write table
	    	out.println("saved file");
	    	saveOut.close();
		}
		catch (IOException e)
		{
			out.println("Error: Invalid filepath");
		}
		finally
		{
			mainMenu(true);
		}
    }
	
	/*
	 * Internal Methods
	 */
	
	private void initializeScanner()
	{
		input = new Scanner(in);
	}
	
	private void closeScanner()
	{
		input.close();
	}
	
	private int getChoice(int numChoices)
	{
		try
		{
			int choice = Integer.parseInt(prompt("Selection:"));
			if (choice < 1 || choice > numChoices)
				throw new ArrayIndexOutOfBoundsException(choice);
			return choice;
		}
		catch (Exception e)
		{
			out.printf("Please input an integer from 1 to %d.%n", numChoices);
			return getChoice(numChoices);
		}
	}
	
	private void printOptions(String[] options)
	{
		out.println("Options:");
		for (int i = 0; i < options.length; i++)
		{
			out.printf("%d. %s%n", i+1, options[i]);
		}
	}
	
	private String prompt(String message)
	{
		out.printf("%s ", message);
		String res = input.nextLine();
		out.println();
		return res;
	}
	
	abstract class User
	{
		String name;
		boolean[] permissions;
		User(boolean... permissions)
		{
			this.permissions = permissions;
		}
		boolean canSeeBasicCourseInfo()
		{
			return permissions[0];
		}
		boolean canModifyGrades()
		{
			return permissions[1];
		}
		boolean canPutStudentsInCourses()
		{
			return permissions[2];
		}
		boolean canCreateCourses()
		{
			return permissions[3];
		}
		boolean canSeeAllGradesForOneStudent()
		{
			return permissions[4];
		}
		boolean canSeeAllGradesForOneCourse()
		{
			return permissions[5];
		}
		boolean canRemoveStudentFromCourse()
		{
			return permissions[6];
		}
		boolean canGetHelp()
		{
			return permissions[7];
		}
		boolean canLogout()
		{
			return permissions[8];
		}
	}
	
	class Student extends User
	{
		Student()
		{
			super(true, false, false, false, true, false, false, true, true);
			name = "Student";
		}
	}
	
	class Instructor extends User
	{
		Instructor()
		{
			super(true, true, false, false, false, true, false, true, true);
			name = "Instructor";
			
		}
	}
	
	class Administrator extends User
	{
		Administrator()
		{
			super(true, false, true, true, true, true, true, true, true);
			name = "Administrator";
		}
	}
}
