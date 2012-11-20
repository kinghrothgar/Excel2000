package database;
import java.util.ArrayList;
import java.util.HashMap;


public class CourseTable
	extends Table<String>
{
	protected CourseTable()
	{
		this.uniqueIds = new ArrayList<String>();
		this.fields = new ArrayList<String>();
			this.fields.add("course");
			this.fields.add("name");
			this.fields.add("instructor");
		this.records = new HashMap<String, ArrayList<?>>();
			this.records.put("course", new ArrayList<String>());
			this.records.put("name", new ArrayList<String>());
			this.records.put("instructor", new ArrayList<String>());
	}
	
	// Throw error if course (uniqueId) already exists
	protected void insert(String table, ArrayList<String> fields, ArrayList<String> values)
	{
		
	}
}
