import java.util.ArrayList;
import java.util.HashMap;


public class GradeTable
	extends Table<String>
{
	// Unique ID is a combination of the student id and the course code with a "+" in between
	
	public GradeTable()
	{
		this.uniqueIds = new ArrayList<String>();
		this.fields = new ArrayList<String>();
			this.fields.add("student");
			this.fields.add("course");
			this.fields.add("grade");
			this.fields.add("isFinal");
		this.records = new HashMap<String, ArrayList<?>>();
			this.records.put("student", new ArrayList<Integer>());
			this.records.put("course", new ArrayList<String>());
			this.records.put("grade", new ArrayList<Double>());
			this.records.put("isFinal", new ArrayList<Boolean>());
	}
	
	//TODO: simply block this method with an error or get the student id and the course code from the uniqueId
	// Throw error if uniqueId already exists or is not of the correct format, add new record with "blank" entries
	public void insert(String uniqueId)
	{
		//Check if uniqueId already exists, add if it doesn't add with blank
	}
	
	// Throw error if course (uniqueId) already exists
	public void insert(Integer student, String course, Double grade, Boolean isFinal)
	{
		//Combined student and course to make uniqueId and then check if exists
	}
	
	// Throw error if uniqueId doesn't exist or value type is incorrect
	public <T> void update(Integer student, String course, String field, T value)
	{
		//Make sure uniqueId exists and the value type is correct
	}
}
