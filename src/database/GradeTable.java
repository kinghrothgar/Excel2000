package database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class GradeTable
	extends Table<String>
{
	// Unique ID is a combination of the student id and the course code with a "+" in between
	protected static Map<String, Object> FIELD_TYPES;
	static {
		Map<String, Object> fieldTypes = new HashMap<String, Object>();
		fieldTypes.put("student", (Integer) 0);
		fieldTypes.put("course", "string");
		fieldTypes.put("grade", (Double) 0.0);
		fieldTypes.put("isFinal", true);
		FIELD_TYPES = Collections.unmodifiableMap(fieldTypes);
	}
	
	protected GradeTable()
	{
		//this.uniqueIds = new ArrayList<String>();
		this.fields = new ArrayList<String>();
			this.fields.add("student");
			this.fields.add("course");
			this.fields.add("grade");
			this.fields.add("isFinal");
		this.records = new HashMap<String, ArrayList<Object>>();
			this.records.put("student", new ArrayList<Object>());
			this.records.put("course", new ArrayList<Object>());
			this.records.put("grade", new ArrayList<Object>());
			this.records.put("isFinal", new ArrayList<Object>());
	}
	
	//TODO: simply block this method with an error or get the student id and the course code from the uniqueId
	// Throw error if uniqueId already exists or is not of the correct format, add new record with "blank" entries
	protected void insert(String uniqueId)
	{
		//Check if uniqueId already exists, add if it doesn't add with blank
	}
	
	// Throw error if course (uniqueId) already exists
	protected void insert(Integer student, String course, Double grade, Boolean isFinal)
	{
		//Combined student and course to make uniqueId and then check if exists
	}
	
	// Throw error if uniqueId doesn't exist or value type is incorrect
	protected <T> void update(Integer student, String course, String field, T value)
	{
		//Make sure uniqueId exists and the value type is correct
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
