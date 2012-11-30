package database;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Database 
{
	private CourseTable courses;
	private StudentTable students;
	private GradeTable grades;
//	private Map<String, Table> tables; //will hold all tables
	
	public Database()
	{
		this.courses = new CourseTable();
		this.students = new StudentTable();
		this.grades = new GradeTable();
//		this.tables = new HashMap<String, Table>();
//			this.tables.put("courses", this.courses);
//			this.tables.put("students", this.students);
//			this.tables.put("grades", this.grades);
	}
	
	// Load from file.  Throws error if location doesn't exist or fails to load
//	public Database(Folder location)
//	{
//		
//	}
	
	// Saves database to file. Throws error if location doesn't exist or save fails
//	public void save(Folder location)
//	{
//		
//	}
	
	// Checks to make sure required fields (for the unique ids) are present and
	// fills in missing ones with blanks
	public void insert(String table, ArrayList<String> fields, ArrayList<String> values)
	{

	}
	
	// Adds values in "standard" order must at least have required unique entries for table
	public void insert(String table, ArrayList<Object> values)
	{
		if(table == "student")
		{
			if(values.size() > 5)
				// TODO: Throw error
				;
			else
				while(values.size() < 5)
					values.add(null);
				// TODO: Catch error if wrong type
				Integer student = (Integer) values.get(0);
				String first = (String) values.get(1);
				String last = (String) values.get(2);
				Integer age = (Integer) values.get(3);
				Integer year = (Integer) values.get(4);
				this.students.insert(student, first, last, age, year);
		}
	}
	
	// Throw error if field doesn't exist, value is the wrong type, table doesn't exist,
	// or field is a uniqueId
	public <T> void update(String table, String field, T value)
	{
		
	}
	
	// Throw error if types aren't right or things are missing
	public <T,E> void update(String table, T uniqueId, String field, E value)
	{
		
	}
	
	// Throw error if deleting from students or courses if student or course exist in
	// in the respective columns in grades
	public <T> void delete(String table, T uniqueId)
	{
		
	}
	
	// Throws error if uniqueId doesn't exist
	public void deleteGrade(Integer student, String course)
	{
		
	}
	
//	public ArrayList<?> getField(String table, String field)
//	{
//		return ArrayList<?>;
//	}
	public static void main(String[] args)
	{
		Database test = new Database();
		ArrayList<Object> values = new ArrayList<Object>();
			values.add((Integer) 88694986);
			values.add("Luke");
			values.add((Integer) 21);
			values.add((Integer) 21);
			values.add((Integer) 2013);
		test.insert("student", values);
		for(ArrayList<Object> field: test.students.records.values())
			System.out.println(field.toString());
	}

}
