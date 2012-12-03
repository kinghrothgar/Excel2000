package database;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Database
{
	private CourseTable courses;
	private StudentTable students;
	private GradeTable grades;
	private Map<String, Table> tables;

	public Database()
	{
		this.courses = new CourseTable();
		this.students = new StudentTable();
		this.grades = new GradeTable();
		this.tables = new HashMap<String, Table>();
			this.tables.put("courses", this.courses);
			this.tables.put("students", this.students);
			this.tables.put("grades", this.grades);
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
		values = this.tables.get(table).orderValues(fields, values);
		if(table.equals("courses") || table.equals("students"))
			this.tables.get(table).insert(values);
		else if(table.equals("grades"))
		{
			if(!(this.students.uniqueIdExists(sArray(values.get(0)))))
				throw new IllegalArgumentException("Error: student ID must exist in students table");
			else if(!(this.courses.uniqueIdExists(sArray(values.get(1)))))
				throw new IllegalArgumentException("Error: course number must exist in courses table");
			this.grades.insert(values);
		}
		else
			// TODO: Raise error, not valid table
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}

	// Adds values in "standard" order must at least have required unique entries for table
	public void insert(String table, ArrayList<String> values)
	{
		if(table.equals("courses") || table.equals("students"))
			this.tables.get(table).insert(values);
		else if(table.equals("grades"))
		{
			if(values.size() < 2)
				throw new IllegalArgumentException("Error: student ID and course number are required");
			else if(!(this.students.uniqueIdExists(sArray(values.get(0)))))
				throw new IllegalArgumentException("Error: student ID must exist in students table");
			else if(!(this.courses.uniqueIdExists(sArray(values.get(1)))))
				throw new IllegalArgumentException("Error: course number must exist in courses table");
			this.grades.insert(values);
		}
		else
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}

	// Throw error if field doesn't exist, value is the wrong type, table doesn't exist,
	// or field is a uniqueId
	public void update(String table, String field, String value)
	{
		if(this.tables.containsKey(table))
			this.tables.get(table).update(field, value);
		else
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}

//	// Throw error if types aren't right or things are missing
//	public void update(String table, String uniqueId, String field, String value)
//	{
//		
//	}

	// Throw error if types aren't right or things are missing
	public void update(String table, int recordIndex, String field, String value)
	{
		if(this.tables.containsKey(table))
			this.tables.get(table).update(recordIndex, field, value);
		else
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}

	// TODO: Do intertable checks
	public void delete(String table)
			throws Exception
	{
		if(table.equals("students") || table.equals("courses"))
		{
			if(this.tables.get("grades").records.get("student").isEmpty())
				this.tables.get(table).delete();
			else
				throw new Exception("Error: grades table must be empty before" + table + " can be deleted.");
		}
		else if(table.equals("grades"))
			this.tables.get("grades").delete();
		else
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}

	// Throw error if deleting from students or courses if student or course exist in
	// in the respective columns in grades
	public void delete(String table, int recordIndex)
	{
		ArrayList<Object> record = new ArrayList<Object>();
		if(table.equals("courses"))
		{
			record = this.courses.getRecord(recordIndex);
			if(this.grades.fieldContains("course", record.get(0)))
			{
				throw new IllegalArgumentException("Error: All instances of course number " + record.get(0) + "must be" +
						" deleted from grades table first");
			}
			else
				this.courses.delete(recordIndex);
		}
		else if(table.equals("students"))
		{
			record = this.students.getRecord(recordIndex);
			if(this.grades.fieldContains("student", record.get(0)))
			{
				throw new IllegalArgumentException("Error: All instances of student ID " + record.get(0) + "must be" +
						" deleted from grades table first");
			}
			else
				this.students.delete(recordIndex);
		}
		else if(table.equals("grades"))
			this.grades.delete(recordIndex);
		else
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}

	public ArrayList<Object> getField(String table, String field)
	{
		if(this.tables.containsKey(table))
			return this.tables.get(table).getField(field);
		else
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}
	public ArrayList<String> getFieldList(String table)
	{
		if(this.tables.containsKey(table))
			return this.tables.get(table).getFieldList();
		else
			throw new IllegalArgumentException("Error: Invalid table, must be courses, students, or grades.");
	}

	private String[] sArray(String stuff)
	{
		String[] result = {stuff};
		return result;
	}

	private String[] sArray(String stuff1, String stuff2)
	{
		String[] result = {stuff1, stuff2};
		return result;
	}

//	public static void main(String[] args)
//	{
//		Database test = new Database();
//		ArrayList<String> values = new ArrayList<String>();
//			values.add("88694986");
//			values.add("Luke");
//			values.add("2013");
//		ArrayList<String> fields = new ArrayList<String>();
//			fields.add("student");
//			fields.add("first");
//			fields.add("year");
//		test.insert("students", fields, values);
//		System.out.println("student: " + test.students.getField("student").toString() + " " + test.students.getField("student").get(0).getClass().toString());
//		System.out.println("first: " + test.students.getField("first").toString());
//		System.out.println("last: " + test.students.getField("last").toString());
//		System.out.println("age: " + test.students.getField("age").toString());
//		System.out.println("year: " + test.students.getField("year").toString());
//	}

}
