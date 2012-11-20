package database;
import java.util.ArrayList;

public class Database 
{
	private CourseTable courses;
	private StudentTable students;
	private GradeTable grades;
	
	public Database()
	{
		this.courses = new CourseTable();
		this.students = new StudentTable();
		this.grades = new GradeTable();
	}
	
	// Load from file.  Throws error if location doesn't exist or fails to load
	public Database(Folder location)
	{
		
	}
	
	// Saves database to file. Throws error if location doesn't exist or save fails
	public void save(Folder location)
	{
		
	}
	
	// Checks to make sure required fields (for the unique ids) are present and
	// fills in missing ones with blanks
	public void insert(String table, ArrayList<String> fields, ArrayList<String> values)
	{
		
	}
	
	// Adds values in "standard" order must at least have required unique entries for table
	public void insert(String table, ArrayList<String> values)
	{
		
	}
	
	// Throw error if field doesn't exist, value is the wrong type, table doesn't exist,
	// or field is a uniqueId
	public <T> void update(String table, String field, T value)
	{
		
	}
	
	public 
	
	
	
	// Throws error if uniqueId doesn't exist
	public void deleteGrade(String uniqueId)
	{
		
	}
	
	// Throws error if uniqueId doesn't exist
	public void deleteGrade(Integer student, String course)
	{
		
	}

}
