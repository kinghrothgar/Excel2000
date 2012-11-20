package database;
import java.util.ArrayList;
import java.util.HashMap;


public class StudentTable 
	extends Table<Integer> 
{
	//uniqueId is the student ID
	
	protected StudentTable()
	{
		this.uniqueIds = new ArrayList<Integer>();
		this.fields = new ArrayList<String>();
			this.fields.add("student");
			this.fields.add("first");
			this.fields.add("last");
			this.fields.add("age");
			this.fields.add("year");
		this.records = new HashMap<String, ArrayList<?>>();
			this.records.put("student", new ArrayList<Integer>());
			this.records.put("first", new ArrayList<String>());
			this.records.put("last", new ArrayList<String>());
			this.records.put("age", new ArrayList<Integer>());
			this.records.put("year", new ArrayList<Integer>());
	}
	
	// Throw error if student (uniqueId) already exists
	protected void insert(Integer uniqueId, String first, String last, Integer age, Integer year)
	{
		
	}

}
