import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

// E is the type of the unique Ids for the table
public class Table<E>
{
	protected ArrayList<String> fields;
	protected ArrayList<E> uniqueIds; //unique Ids for each record
	protected Map<String, ArrayList<?>> records; //will hold all records
	
	public Table()
	{
		this.fields = new ArrayList<String>();
		this.uniqueIds = new ArrayList<E>();
		this.records = new HashMap<String, ArrayList<?>>();
	}
	
	// Throw error if uniqueId already exists, add new record with "blank" entries
	public void insert(E uniqueId)
	{
		//Check if uniqueId already exists, add if it doesn't
	}
	
	// Throw error if value type isn't correct
	public <T> void update(String field, T value)
	{
		
	}
	
	// Throw error if uniqueId doesn't exist or value type is incorrect
	public <T> void update(E uniqueId, String field, T value)
	{
		//Make sure uniqueId exists and the value type is correct
	}
	
	// Deletes all the table's records
	public void delete()
	{
		
	}
	// Throw error if uniqueId doesn't exist
	public void delete(E uniqueId)
	{
		
	}
	
	// Throw error if field doesn't exist
	public ArrayList<?> getField(String field)
	{
		return this.records.get(field);
	}
	
	public ArrayList<String> getFieldList()
	{
		return this.fields;
	}
	// Return true if uniqueId Exists
	public <T> boolean uniqueIdExists(T uniqueId)
	{
		return true;
	}
}