package database;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class StudentTable
	extends Table<Integer>
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
		
	//protected Map<String, ArrayList<Object>> records; //will hold all records

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
	
	// Throw error if student (uniqueId) already exists
	protected void insert(Integer student)
	{
		if(this.records.get("student").contains(student))
			// TODO: Throw error
			;
		else
			this.insert(student, null, null, null, null);
	}
	protected void insert(Integer student, String first, String last, Integer age, Integer year)
	{
		if(this.uniqueIdExists(student))
			// TODO: Throw error
			;
		else
		{
			this.records.get("student").add(student);
			this.records.get("first").add(first);
			this.records.get("last").add(last);
			this.records.get("age").add(age);
			this.records.get("year").add(year);
		}
	}

	@Override
	protected <T> void update(String field, T value) 
	{
		// ERROR Checks:
		if(!(this.fields.contains(field)))
			// TODO: Throw error
			System.out.println("No field");
		else if(field == "student")
			// TODO: Throw error
			System.out.println("Cant change student field");
		else if(value != null)
		{
			if(!(value.getClass() == FIELD_TYPES.get(field).getClass()))
				//TODO: Throw error
				System.out.println("Is not instanceof");
		}
		// If no errors then:
		for(int i=0; i < this.records.get(field).size(); i++)
			this.records.get(field).set(i, value);
	}
	@Override
	protected <T extends Object> void update(Integer student, String field, T value)
	{
		int index;
		// ERROR Checks:
		if(!(this.uniqueIdExists(student)))
			// TODO: Throw error
			System.out.println("No student");
		else if(!(this.fields.contains(field)))
			// TODO: Throw error
			System.out.println("No field");
		else if(field == "student")
			// TODO: Throw error
			System.out.println("Cant change student");
		else if(value != null)
		{
			if(!(value.getClass() == FIELD_TYPES.get(field).getClass()))
				//TODO: Throw error
				System.out.println("Is not instanceof");
		}
		// If no errors then:
		index = this.getRecordIndex(student);
		this.records.get(field).set(index, value);
		System.out.println("Set");
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