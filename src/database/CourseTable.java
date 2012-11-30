package database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CourseTable
	extends Table<String>
{
	protected static Map<String, Object> FIELD_TYPES;
	static {
		Map<String, Object> fieldTypes = new HashMap<String, Object>();
		fieldTypes.put("course", "string");
		fieldTypes.put("name", "string");
		fieldTypes.put("instructor", "string");
		FIELD_TYPES = Collections.unmodifiableMap(fieldTypes);
	}
	
	protected CourseTable()
	{
		this.fields = new ArrayList<String>();
			this.fields.add("course");
			this.fields.add("name");
			this.fields.add("instructor");
		this.records = new HashMap<String, ArrayList<Object>>();
			this.records.put("course", new ArrayList<Object>());
			this.records.put("name", new ArrayList<Object>());
			this.records.put("instructor", new ArrayList<Object>());
	}
	
	private void insert(String course, String name, String instructor)
	{
		if(this.uniqueIdExists(course))
			// TODO: Throw error
			;
		else if(course == null)
			// TODO: Throw error
			;
		else
		{
			this.records.get("course").add(course);
			this.records.get("name").add(name);
			this.records.get("instructor").add(instructor);
		}
	}
	
	// Throw error if course (uniqueId) already exists
	@Override
	protected void insert(ArrayList<Object> values) 
	{
		if(values.size() > this.fields.size())
			// TODO: Throw error
			;
		else
			while(values.size() < this.fields.size())
				values.add(null);
			// TODO: Catch error if wrong type
			String course = (String) values.get(0);
			String name = (String) values.get(1);
			String instructor = (String) values.get(2);
			this.insert(course, name, instructor);
	}

	@Override
	protected void insert(String uniqueId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected <T> void update(String field, T value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected <T> void update(String uniqueId, String field, T value) {
		// TODO Auto-generated method stub
		
	}
}
