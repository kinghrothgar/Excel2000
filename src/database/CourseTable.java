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
	
	@Override
	protected void insert(String course)
	{
		this.insert(course, null, null);
	}
	
	// Checks if course is unique and not null
	private void insert(String course, String name, String instructor)
	{
		if(this.uniqueIdExists(course))
			throw new IllegalArgumentException("Error: course number already exists");
		else if(course == null)
			throw new IllegalArgumentException("Error: course number must not be null");
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
		while(values.size() < this.fields.size())
			values.add(null);
		values = this.checkAndCastValues(values);
		String course = (String) values.get(0);
		String name = (String) values.get(1);
		String instructor = (String) values.get(2);
		this.insert(course, name, instructor);
	}

	// Checks if field exists, is not a unique field, and that it is the right type
	@Override
	protected <T extends Object> void update(String field, T value) 
	{
		// ERROR Checks:
		this.checkValue(field, value);
		if(field.equals("course"))
			throw new IllegalArgumentException("Error: course number cannot be changed.");
		else
		{
			if(value == null)
			{
				for(int i=0; i < this.records.get(field).size(); i++)
					this.records.get(field).set(i, value);
			}
			else
			{
				for(int i=0; i < this.records.get(field).size(); i++)
					this.records.get(field).set(i, (String) value);
			}
		}
	}

	@Override
	protected <T extends Object> void update(String course, String field, T value)
	{
		int index;
		// ERROR Checks:
		this.checkValue(field, value);
		if(!(this.uniqueIdExists(course)))
			throw new IllegalArgumentException("Error: " + course + "does not exists in table");
		else if(field.equals("course"))
			throw new IllegalArgumentException("Error: course name cannot be changed.");
		else
		{
			index = this.getRecordIndex(course);
			if(value == null)
				this.records.get(field).set(index, value);
			else
				this.records.get(field).set(index, (String) value);
		}
	}
	
	// Checks if field exists, is not a unique field, and if it's correct type
	@Override
	protected <T extends Object> void checkValue(String field, T value) 
	{
		if(!(this.fields.contains(field)))
			throw new IllegalArgumentException("Error: " + field + " is not a valid field");
		if(field.equals("course"))
		{
			if(value == null)
				throw new IllegalArgumentException("Error: course number cannot be null.");
			else if(value.getClass() != String.class)
				throw new IllegalArgumentException("Error: course number must be a String");
		}
		else if(field.equals("name"))
		{
			if(value != null && value.getClass() != String.class)
				throw new IllegalArgumentException("Error: course name must be a String");
		}
		else if(field.equals("instructor"))
		{
			if(value != null && !(this.isValidName((String) value)))
				throw new IllegalArgumentException("Error: instructor's last name may only contain A-Z, a-z, ' ', -, and '");
		}		
	}
	
	// Checks number of values, that required fields are not null, that they are the
	// right type, and casts them. Must have the correct number of values.
	@Override
	protected ArrayList<Object> checkAndCastValues(ArrayList<Object> values) 
	{
		ArrayList<Object> correct = new ArrayList<Object>(this.fields.size());
		String course;
		String name;
		String instructor;
		
		// Check if correct size
		if(values.size() != this.fields.size())
			throw new IllegalArgumentException("Error: there must be " + this.fields.size() + "values");
		
		// Check and cast
		if(values.get(0) == null)
			throw new IllegalArgumentException("Error: course number must not be null");
		else {
			try {course = (String) values.get(0);}
			catch (Exception e) {throw new IllegalArgumentException("Error: course number must be a String");}
		}
		
		if(values.get(1) == null)
			name = null;
		else {
			try {name = (String) values.get(1);}
			catch (Exception e) {throw new IllegalArgumentException("Error: course name must be a String");}
		}
		
		if(values.get(2) == null)
			instructor = null;
		else {
			if(!(this.isValidName((String) values.get(2))))
				throw new IllegalArgumentException("Error: instructor's last name may only contain A-Z, a-z, ' ', -, and '");
			try {instructor = (String) values.get(2);}
			catch (Exception e) {throw new IllegalArgumentException("Error: instructor's last name must be a string");}
		}
		
		correct.add(course);
		correct.add(name);
		correct.add(instructor);
		
		return correct;
	}
}
