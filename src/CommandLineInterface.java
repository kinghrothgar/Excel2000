import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Austin Almond
 *
 */
public class CommandLineInterface
{
	private PrintStream out;
	private InputStream in;
	private Scanner input;
	private User user;
	
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
		mainMenu();
	}
	
	/*
	 *  Menus (Only accessible through user commands [main menu called initially])
	 */
	
	private void mainMenu()
	{
		out.println("Main Menu");
		/*
		 * Options:
		 * 1. Initialize Database
		 * 2. Login to role
		 * 3. Exit
		 */
		String[] options = {"Initialize database", "Login to role", "Exit"};
		printOptions(options);
		switch(getChoice(options.length))
		{
		case 1:
			initializeDatabase();
			break;
		case 2:
			loginToRole();
			break;
		case 3:
			// Exit
			closeScanner();
			out.println("Goodbye!");
			break;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	private void initializeDatabase()
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
		printOptions(options);
		switch(getChoice(options.length))
		{
		case 1:
			out.println("[DIRECT DATA ENTRY]");// TODO: direct data entry
			break;
		case 2:
			out.println("[LOAD TABLE]");// TODO: load table from file
			break;
		case 3:
			out.println("[SAVE TABLE]");// TODO: store table to file
			break;
		case 4:
			mainMenu();
			break;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	private void loginToRole()
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
		printOptions(options);
		switch(getChoice(options.length))
		{
		case 1:
			user = new Student();
			makeSelection();
			break;
		case 2:
			user = new Instructor();
			makeSelection();
			break;
		case 3:
			user = new Administrator();
			makeSelection();
			break;
		case 4:
			mainMenu();
			break;
		default:
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	private void makeSelection()
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
		printOptions(optionsArray);
		int choice = getChoice(options.size());
		if (choice == basicInfoIdx)
			out.println("[GET BASIC INFO]");// TODO: Get basic info
		else if (choice == modifyGradesIdx)
			out.println("[MODIFY GRADES]");// TODO: Modify grades
		else if (choice == putStudentsIdx)
			out.println("[PUT STUDENTS IN COURSES]");// TODO: Put students in courses
		else if (choice == createCoursesIdx)
			out.println("[CREATE COURSES]");// TODO: Create courses
		else if (choice == seeStudentGradesIdx)
			out.println("[SEE STUDENT GRADES]");// TODO: See student grades
		else if (choice == seeCourseGradesIdx)
			out.println("course grades");// TODO: See course grades
		else if (choice == rmStudentsIdx)
			out.println("rm students");// TODO: Remove students from courses
		else if (choice == helpIdx)
			out.println("help");// TODO: Help
		else if (choice == logoutIdx)
			loginToRole();// Logout
		else
			throw new ArrayIndexOutOfBoundsException();
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