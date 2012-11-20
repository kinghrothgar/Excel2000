package database;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Table<E>
{
	protected ArrayList<String> fields;
	protected ArrayList<E> uniqueIds; //unique Ids for each record
	protected Map<String, ArrayList<?>> records; //will hold all records
	
	protected Table()
	{
		this.fields = new ArrayList<String>();
		this.uniqueIds = new ArrayList<E>();
		this.records = new HashMap<String, ArrayList<?>>();
	}
	
	// Throw error if uniqueId already exists, add new record with "blank" entries
	protected void insert(E uniqueId)
	{
		//Check if uniqueId already exists, add if it doesn't
	}
	
	// Throw error if value type isn't correct
	protected <T> void update(String field, T value)
	{
		
	}
	
	// Throw error if uniqueId doesn't exist or value type is incorrect
	protected <T> void update(E uniqueId, String field, T value)
	{
		//Make sure uniqueId exists and the value type is correct
	}
	
	// Deletes all the table's records
	protected void delete()
	{
		
	}
	// Throw error if uniqueId doesn't exist
	protected void delete(E uniqueId)
	{
		
	}
	
	// Throw error if field doesn't exist
	protected ArrayList<?> getField(String field)
	{
		return this.records.get(field);
	}
	
	protected ArrayList<String> getFieldList()
	{
		return this.fields;
	}
	// Return true if uniqueId Exists
	protected <T> boolean uniqueIdExists(T uniqueId)
	{
		return true;
	}
}