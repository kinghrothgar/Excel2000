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
	
	//TODO: simply block this method with an error or get the student id and the course code from the uniqueId
	// Throw error if uniqueId already exists or is not of the correct format, add new record with "blank" entries
	protected void insert(String uniqueId)
	{
		//Check if uniqueId already exists, add if it doesn't add with blank
	}
	
	@Override
	protected void insert(ArrayList<Object> values) 
	{
		// ERROR Checks:
		if(values.size() > this.fields.size())
			// TODO: Throw error
			;
		else
			while(values.size() < this.fields.size())
				values.add(null);
			// TODO: Catch error if wrong type
			Integer student = (Integer) values.get(0);
			String course = (String) values.get(1);
			Double grade = (Double) values.get(2);
			Boolean isFinal = (Boolean) values.get(3);
			this.insert(student, course, grade, isFinal);
	}

	@Override
	protected void insert(ArrayList<String> fields, ArrayList<Object> values) {
		// TODO Auto-generated method stub
		
	}
	
	// Throw error if course (uniqueId) already exists
	private void insert(Integer student, String course, Double grade, Boolean isFinal)
	{
		if(this.uniqueIdExists(student, course))
			// TODO: Throw error
			;
		else if(student == null ||course == null)
			// TODO: Throw error
			;
		else
		{
			this.records.get("student").add(student);
			this.records.get("course").add(course);
			this.records.get("grade").add(grade);
			this.records.get("isFinal").add(isFinal);
		}
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
