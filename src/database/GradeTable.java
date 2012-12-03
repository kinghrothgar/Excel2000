package database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class GradeTable
	extends Table
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
	
	// Throw error if course (uniqueId) already exists
	private void insert(Integer student, String course, Double grade, Boolean isFinal)
	{
		if(student == null || course == null)
			throw new IllegalArgumentException("Error: neither student ID nor course number can be null");
		else if(this.uniqueIdExists(sArray(student.toString(), course)))
			throw new IllegalArgumentException("Error: student ID and course number combination already exists");
		else
		{
			this.records.get("student").add(student);
			this.records.get("course").add(course);
			this.records.get("grade").add(grade);
			this.records.get("isFinal").add(isFinal);
		}
	}
	
	@Override
	protected void insert(ArrayList<String> values)
	{
		ArrayList<Object> castedValues;
		while(values.size() < this.fields.size())
			values.add(null);
		castedValues = this.checkAndCastValues(values);
		Integer student = (Integer) castedValues.get(0);
		String course = (String) castedValues.get(1);
		Double grade = (Double) castedValues.get(2);
		Boolean isFinal = (Boolean) castedValues.get(3);
		this.insert(student, course, grade, isFinal);
	}
	
	// Checks if field exists, is not a unique field, and that it is the right type
	@Override
	protected void update(String field, String value) 
	{
		Object castedValue;
		// ERROR Checks:
		castedValue = this.castValue(field, value);
		if(field.equals("student") || field.equals("course"))
			throw new IllegalArgumentException("Error: neither student ID nor course number can be changed.");
		else
		{
			for(int i=0; i < this.records.get(field).size(); i++)
				this.records.get(field).set(i, castedValue);
		}
	}
	
//	// TODO: Block this
//	@Override
//	protected void update(String uniqueId, String field, String value) 
//	{
//		
//	}
	
	protected void update(String student, String course, String field, String value)
	{
		int index;
		Object castedValue;
		// ERROR Checks:
		castedValue = this.castValue(field, value);
		if(!(this.uniqueIdExists(sArray(student, course))))
			throw new IllegalArgumentException("Error: " + student + " and " + course + " combination does not exists in table");
		else if(field.equals("student") || field.equals("course"))
			throw new IllegalArgumentException("Error: neither student ID nor course number can be changed.");
		else
		{
			index = this.getRecordIndex(sArray(student, course));
			this.records.get(field).set(index, castedValue);
		}
	}
	
	@Override
	protected void update(int recordIndex, String field, String value)
	{
		Object castedValue;
		// Error checks
		castedValue = this.castValue(field, value);
		if(field.equals("student") || field.equals("course"))
			throw new IllegalArgumentException("Error: neither student ID nor course number can be changed.");
		else
			this.records.get(field).set(recordIndex, castedValue);
	}
	
	// Checks if field exists, is not a unique field, and if it's correct type
	@Override
	protected Object castValue(String field, String value) 
	{
		if(!(this.fields.contains(field)))
			throw new IllegalArgumentException("Error: " + field + " is not a valid field");
		if(field.equals("student"))
		{
			Integer result;
			if(value == null)
				throw new IllegalArgumentException("Error: student ID cannot be null.");
			try {result = new Integer(value);}
			catch (Exception e) {throw new IllegalArgumentException("Error: student ID must be an Integer");}
			return result;
		}
		else if(field.equals("course"))
		{
			if(value == null)
				throw new IllegalArgumentException("Error: course number cannot be null.");
			return value;
		}
		else if(field.equals("grade"))
		{
			Double result;
			if(value == null)
				return value;
			try {result = new Double(value);}
			catch (NumberFormatException e) {throw new IllegalArgumentException("Error: grade must be a Double");}
			return result;
		}
		else if(field.equals("isFinal"))
		{
			if(value == null)
				return value;
			else if(value.equalsIgnoreCase("true"))
				return new Boolean(true);
			else if(value.equalsIgnoreCase("false"))
				return new Boolean(false);
			else
				throw new IllegalArgumentException("Error: grade must be a Double");
		}
		return -1;
	}
}
