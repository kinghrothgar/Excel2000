package database;
import java.util.ArrayList;
import java.util.Map;

public abstract class Table<E>
{
	//Unique Id is always first field
	protected ArrayList<String> fields;
	//protected ArrayList<E> uniqueIds; //unique Ids for each record
	protected Map<String, ArrayList<Object>> records; //will hold all records

	
	// Throw error if uniqueId already exists, add new record with "blank" entries
	protected abstract void insert(E uniqueId);
	
	protected abstract void insert(ArrayList<Object> values);
	
	protected void insert(ArrayList<String> fields, ArrayList<Object> values)
	{
		ArrayList<Object> sortedValues = new ArrayList<Object>(this.fields.size());
		// ERROR Checks:
		if(fields.size() != values.size())
			// TODO: Throw error
			;
		else if(fields.size() > this.fields.size())
			// TODO: Throw error
			;
		for(String field: fields)
		{
			if(!(this.fields.contains(field)))
				// TODO: Throw error
				;
		}
		// Insert the data
		// Add missing fields
		for(int i=0; i < this.fields.size(); i++)
		{
			int index = fields.indexOf(this.fields.get(i));
			if(index == -1)
				sortedValues.add(null);
			else
				sortedValues.add(values.get(index));
		}
		this.insert(sortedValues);
	}
	
	// Throw error if value type isn't correct
	protected abstract <T extends Object> void update(String field, T value);
	
	// Throw error if uniqueId doesn't exist or value type is incorrect
	protected abstract <T extends Object> void update(E uniqueId, String field, T value);

	
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
	protected boolean uniqueIdExists(E uniqueId)
	{
		String uniqueField = this.fields.get(0);
		if(this.records.get(uniqueField).contains(uniqueId))
			return true;
		else
			return false;
	}
	protected int getRecordIndex(E uniqueId)
	{
		String uniqueField = this.fields.get(0);
		if(!(this.records.get(uniqueField).contains(uniqueId)))
			// TODO: Throw error
			;
		else
			return this.records.get(uniqueField).indexOf(uniqueId);
		return -1;
	}
	// Deletes all the table's records
	protected void delete()
	{
		for(ArrayList<Object> field: this.records.values())
			field.clear();
	}

	// Throw error if uniqueId doesn't exist
	protected void delete(E uniqueId)
	{
		int recordIndex;
		if(!(this.uniqueIdExists(uniqueId)))
			// TODO: Throw error
			;
		else
		{
			recordIndex = this.getRecordIndex(uniqueId);
			for(ArrayList<Object> field: this.records.values())
				field.remove(recordIndex);
		}
	}
}