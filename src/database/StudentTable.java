package database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class StudentTable
	extends Table
{
	//uniqueId is the student ID
	//protected ArrayList<String> fields;
	//protected ArrayList<Integer> uniqueIds; //unique Ids for each record
	protected static Map<String, Object> FIELD_TYPES;
	static {
		Map<String, Object> fieldTypes = new HashMap<String, Object>();
		fieldTypes.put("student", (Integer) 0);
		fieldTypes.put("first", "string");
		fieldTypes.put("last", "string");
		fieldTypes.put("age", (Integer) 0);
		fieldTypes.put("year", (Integer) 0);
		FIELD_TYPES = Collections.unmodifiableMap(fieldTypes);
	}

	protected StudentTable()
	{
		this.fields = new ArrayList<String>();
			this.fields.add("student");
			this.fields.add("first");
			this.fields.add("last");
			this.fields.add("age");
			this.fields.add("year");
		this.records = new HashMap<String, ArrayList<Object>>();
			this.records.put("student", new ArrayList<Object>());
			this.records.put("first", new ArrayList<Object>());
			this.records.put("last", new ArrayList<Object>());
			this.records.put("age", new ArrayList<Object>());
			this.records.put("year", new ArrayList<Object>());
	}
	
//	protected void insert(String student)
//	{
//		this.insert(student, null, null, null, null);
//	}
	
	// Checks if student is unique and not null
	protected void insert(Integer student, String first, String last, Integer age, Integer year)
	{
		if(student == null)
			throw new IllegalArgumentException("Error: student ID must not be null");
		else if(this.uniqueIdExists(sArray(student.toString())))
			throw new IllegalArgumentException("Error: student ID already exists");
		else
		{
			this.records.get("student").add(student);
			this.records.get("first").add(first);
			this.records.get("last").add(last);
			this.records.get("age").add(age);
			this.records.get("year").add(year);
		}
	}
	
	// Calls checkAndCast
	@Override
	protected void insert(ArrayList<String> values) 
	{
		ArrayList<Object> castedValues;
		while(values.size() < this.fields.size())
			values.add(null);
		castedValues = this.checkAndCastValues(values);
		Integer student = (Integer) castedValues.get(0);
		String first = (String) castedValues.get(1);
		String last = (String) castedValues.get(2);
		Integer age = (Integer) castedValues.get(3);
		Integer year = (Integer) castedValues.get(4);
		this.insert(student, first, last, age, year);
	}
	
	// Checks if field exists, is not a unique field, and that it is the right type
	@Override
	protected void update(String field, String value) 
	{
		Object castedValue;
		// ERROR Checks:
		castedValue = this.castValue(field, value);
		if(field.equals("student"))
			throw new IllegalArgumentException("Error: student ID cannot be changed.");
		else
		{
			for(int i=0; i < this.records.get(field).size(); i++)
				this.records.get(field).set(i, castedValue);
		}
	}
	
	
	@Override
	protected void update(String student, String field, String value)
	{
		int index;
		Object castedValue;
		// ERROR Checks:
		castedValue = this.castValue(field, value);
		if(!(this.uniqueIdExists(sArray(student))))
			throw new IllegalArgumentException("Error: " + student + " does not exists in table");
		else if(field.equals("student"))
			throw new IllegalArgumentException("Error: student ID cannot be changed.");
		else
		{
			index = this.getRecordIndex(sArray(student));
			this.records.get(field).set(index, castedValue);
		}
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
		else if(field.equals("first"))
		{
			if(value != null && !(this.isValidName(value)))
				throw new IllegalArgumentException("Error: first name may only contain A-Z, a-z, ' ', -, and '");
			return value;
		}
		else if(field.equals("last"))
		{
			if(value != null && !(this.isValidName(value)))
				throw new IllegalArgumentException("Error: last name may only contain A-Z, a-z, ' ', -, and '");
			return value;
		}
		else if(field.equals("age"))
		{
			Integer result;
			if(value != null)
			{
				try {result = new Integer(value);}
				catch (Exception e) {throw new IllegalArgumentException("Error: age must be an Integer");}
				return result;
			}
			else
				return null;
		}
		else if(field.equals("year"))
		{
			Integer result;
			if(value != null)
			{
				try {result = new Integer(value);}
				catch (Exception e) {throw new IllegalArgumentException("Error: year must be an Integer");}
				return result;
			}
			else
				return null;
		}
		return -1;
	}
	
//	public static void main(String[] args)
//	{
//		StudentTable test = new StudentTable();
//		test.insert((Integer) 88694986, "Luke", "Jolly", (Integer) 21, (Integer) 2013);
//		test.insert((Integer) 99999999, "Fake", "Person", (Integer) 18, (Integer) 2099);
//		System.out.println(test.records.get("first").toString());
//		test.update((Integer) 88694986, "first", null);
//		System.out.println(test.records.get("first").toString());
//		test.update((Integer) 88694986, "first", "Luke");
//		System.out.println(test.records.get("first").toString());
//		test.update("first", "Cool");
//		System.out.println(test.records.get("first").toString());
//	}
}