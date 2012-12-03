package database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CourseTable
	extends Table
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
	
	// Checks if course is unique and not null
	private void insert(String course, String name, String instructor)
	{
		if(course == null)
			throw new IllegalArgumentException("Error: course number must not be null");
		else if(this.uniqueIdExists(sArray(course)))
			throw new IllegalArgumentException("Error: course number already exists");
		else
		{
			this.records.get("course").add(course);
			this.records.get("name").add(name);
			this.records.get("instructor").add(instructor);
		}
	}
	
	// Throw error if course (uniqueId) already exists
	@Override
	protected void insert(ArrayList<String> values) 
	{
		ArrayList<Object> castedValues;
		while(values.size() < this.fields.size())
			values.add(null);
		castedValues = this.checkAndCastValues(values);
		String course = (String) castedValues.get(0);
		String name = (String) castedValues.get(1);
		String instructor = (String) castedValues.get(2);
		this.insert(course, name, instructor);
	}

	// Checks if field exists, is not a unique field, and that it is the right type
	@Override
	protected void update(String field, String value)
	{
		Object castedValue;
		// ERROR Checks:
		castedValue = this.castValue(field, value);
		if(field.equals("course"))
			throw new IllegalArgumentException("Error: course number cannot be changed.");
		else
		{
			for(int i=0; i < this.records.get(field).size(); i++)
				this.records.get(field).set(i, castedValue);
		}
	}

//	@Override
//	protected void update(String course, String field, String value)
//	{
//		int index;
//		Object castedValue;
//		// ERROR Checks:
//		castedValue = this.castValue(field, value);
//		if(!(this.uniqueIdExists(sArray(course))))
//			throw new IllegalArgumentException("Error: " + course + "does not exists in table");
//		else if(field.equals("course"))
//			throw new IllegalArgumentException("Error: course name cannot be changed.");
//		else
//		{
//			index = this.getRecordIndex(sArray(course));
//			this.records.get(field).set(index, castedValue);
//		}
//	}
	
	@Override
	protected void update(int recordIndex, String field, String value)
	{
		Object castedValue;
		// Error checks
		castedValue = this.castValue(field, value);
		if(field.equals("course"))
			throw new IllegalArgumentException("Error: course name cannot be changed.");
		else
			this.records.get(field).set(recordIndex, castedValue);
	}
	
	// Checks if field exists, is not a unique field, and if it's correct type
	@Override
	protected Object castValue(String field, String value) 
	{
		if(!(this.fields.contains(field)))
			throw new IllegalArgumentException("Error: " + field + " is not a valid field");
		if(field.equals("course"))
		{
			if(value == null)
				throw new IllegalArgumentException("Error: course number cannot be null.");
			else if(value.getClass() != String.class)
				throw new IllegalArgumentException("Error: course number must be a String");
			return value;
		}
		else if(field.equals("name"))
		{
			if(value != null && value.getClass() != String.class)
				throw new IllegalArgumentException("Error: course name must be a String");
			return value;
		}
		else if(field.equals("instructor"))
		{
			if(value != null && !(this.isValidName((String) value)))
				throw new IllegalArgumentException("Error: instructor's last name may only contain A-Z, a-z, ' ', -, and '");
			return value;
		}
		return -1;
	}
}
