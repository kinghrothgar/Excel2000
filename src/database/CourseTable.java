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
	
	// Throw error if course (uniqueId) already exists
	protected void insert(String table, ArrayList<String> fields, ArrayList<String> values)
	{
		
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
