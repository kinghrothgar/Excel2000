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
	private void insert(Integer student, String course, Double grade, Boolean isFinal)
	{
		if(this.uniqueIdExists(student, course))
			throw new IllegalArgumentException("Error: student ID and course number combination already exists");
		else if(student == null ||course == null)
			throw new IllegalArgumentException("Error: neither student ID nor course number can be null");
		else
		{
			this.records.get("student").add(student);
			this.records.get("course").add(course);
			this.records.get("grade").add(grade);
			this.records.get("isFinal").add(isFinal);
		}
	}
	
	@Override
	protected void insert(ArrayList<Object> values)
	{
		while(values.size() < this.fields.size())
			values.add(null);
		values = this.checkAndCastValues(values);
		Integer student = (Integer) values.get(0);
		String course = (String) values.get(1);
		Double grade = (Double) values.get(2);
		Boolean isFinal = (Boolean) values.get(3);
		this.insert(student, course, grade, isFinal);
	}
	
	// Checks if field exists, is not a unique field, and that it is the right type
	@Override
	protected <T> void update(String field, T value) 
	{
		// ERROR Checks:
		this.checkValue(field, value);
		if(field.equals("student") || field.equals("course"))
			throw new IllegalArgumentException("Error: neither student ID nor course number can be changed.");
		else
		{
			if(value == null)
			{
				for(int i=0; i < this.records.get(field).size(); i++)
					this.records.get(field).set(i, value);
			}
			else if(FIELD_TYPES.get(field).getClass() == String.class)
			{
				for(int i=0; i < this.records.get(field).size(); i++)
					this.records.get(field).set(i, (String) value);
			}
			else if(FIELD_TYPES.get(field).getClass() == Boolean.class)
			{
				for(int i=0; i < this.records.get(field).size(); i++)
					this.records.get(field).set(i, Boolean.valueOf((String) value));
			}
			else if(FIELD_TYPES.get(field).getClass() == Double.class)
			{
				for(int i=0; i < this.records.get(field).size(); i++)
					this.records.get(field).set(i, new Double((String) value));
			}
			else
			{
				for(int i=0; i < this.records.get(field).size(); i++)
					this.records.get(field).set(i, new Integer((String) value));
			}
		}
	}
	
	// TODO: Block this
	@Override
	protected <T extends Object> void update(String uniqueId, String field, T value) {
	
	}
	
	protected <T extends Object> void update(Integer student, String course, String field, T value)
	{
		int index;
		// ERROR Checks:
		this.checkValue(field, value);
		if(!(this.uniqueIdExists(student, course)))
			throw new IllegalArgumentException("Error: " + student + " and " + course + " combination does not exists in table");
		else if(field.equals("student") || field.equals("course"))
			throw new IllegalArgumentException("Error: neither student ID nor course number can be changed.");
		else
		{
			index = this.getRecordIndex(student, course);
			if(value == null)
				this.records.get(field).set(index, value);
			else if(FIELD_TYPES.get(field).getClass() == String.class)
				this.records.get(field).set(index, (String) value);
			else if(FIELD_TYPES.get(field).getClass() == Integer.class)
				this.records.get(field).set(index, new Integer((String) value));
			else if(FIELD_TYPES.get(field).getClass() == Double.class)
				this.records.get(field).set(index, new Double((String) value));
			else if(FIELD_TYPES.get(field).getClass() == Boolean.class)
				this.records.get(field).set(index, Boolean.valueOf((String) value));
		}
	}
	
	// Checks if field exists, is not a unique field, and if it's correct type
	@Override
	protected <T extends Object> void checkValue(String field, T value) 
	{
		if(!(this.fields.contains(field)))
			throw new IllegalArgumentException("Error: " + field + " is not a valid field");
		if(field.equals("student"))
		{
			if(value == null)
				throw new IllegalArgumentException("Error: student ID cannot be null.");
			try {new Integer((String) value);}
			catch (Exception e) {throw new IllegalArgumentException("Error: student ID must be an Integer");}
		}
		else if(field.equals("course"))
		{
			if(value == null)
				throw new IllegalArgumentException("Error: course number cannot be null.");
			try {value.toString();}
			catch (Exception e) {throw new IllegalArgumentException("Error: course name must be a String");}
		}
		else if(field.equals("grade"))
		{
			if(value != null && !(this.isValidName((String) value)))
				throw new IllegalArgumentException("Error: first name may only contain A-Z, a-z, ' ', -, and '");
		}
		else if(field.equals("last"))
		{
			if(value != null && !(this.isValidName((String) value)))
				throw new IllegalArgumentException("Error: last name may only contain A-Z, a-z, ' ', -, and '");
		}
		else if(field.equals("age"))
		{
			if(value != null)
			{
				try {new Integer((String) value);}
				catch (Exception e) {throw new IllegalArgumentException("Error: age must be an Integer");}
			}
		}
		else if(field.equals("year"))
		{
			if(value != null)
			{
				try {new Integer((String) value);}
				catch (Exception e) {throw new IllegalArgumentException("Error: year must be an Integer");}
			}
		}
		
	}
	
	// TODO: get rid of this
	@Override
	protected boolean uniqueIdExists(String uniqueId)
	{
		String uniqueField = this.fields.get(0);
		if(this.records.get(uniqueField).contains(uniqueId))
			return true;
		else
			return false;
	}
	protected boolean uniqueIdExists(Integer student, String course)
	{
		String studentField = this.fields.get(0);
		String courseField = this.fields.get(1);
		for(int i=0; i < this.records.get("student").size(); i++)
		{
			if(this.records.get(studentField).get(i) == student)
			{
				if(this.records.get(courseField).get(i) == course)
					return true;
			}
		}
		return false;
	}
	// TODO: get rid of this
	@Override
	protected int getRecordIndex(String uniqueId)
	{
		String uniqueField = this.fields.get(0);
		if(!(this.records.get(uniqueField).contains(uniqueId)))
			// TODO: Throw error
			;
		else
			return this.records.get(uniqueField).indexOf(uniqueId);
		return -1;
	}
	protected int getRecordIndex(Integer student, String course)
	{
		String studentField = this.fields.get(0);
		String courseField = this.fields.get(1);
		for(int i=0; i < this.records.get("student").size(); i++)
		{
			if(this.records.get(studentField).get(i) == student)
			{
				if(this.records.get(courseField).get(i) == course)
					return i;
			}
		}
		return -1;
	}
}
