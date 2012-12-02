package database;
import java.util.ArrayList;
import java.util.Map;

public abstract class Table
{
	//Unique Id is always first field
	protected ArrayList<String> fields;
	//protected ArrayList<E> uniqueIds; //unique Ids for each record
	protected Map<String, ArrayList<Object>> records; //will hold all records

	
	// Throw error if uniqueId already exists, add new record with "blank" entries
	//protected abstract void insert(String uniqueId);
	
	protected abstract void insert(ArrayList<String> values);
	
	// Throw error if value type isn't correct
	protected abstract void update(String field, String value);
	
	// Throw error if uniqueId doesn't exist or value type is incorrect
	protected abstract void update(String uniqueId, String field, String value);
	
	protected abstract ArrayList<Object> checkAndCastValues(ArrayList<String> values);
	
	protected abstract Object castValue(String field, String value);
	
	// Throw error if field doesn't exist
	protected ArrayList<Object> getField(String field)
	{
		return this.records.get(field);
	}
	
	protected ArrayList<String> getFieldList()
	{
		return this.fields;
	}

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
	
	protected ArrayList<String> orderValues(ArrayList<String> fields, ArrayList<String> values)
	{
		ArrayList<String> sortedValues = new ArrayList<String>(this.fields.size());
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
		return sortedValues;
	}
	
	// Deletes all the table's records
	protected void delete()
	{
		for(ArrayList<Object> field: this.records.values())
			field.clear();
	}

	// Throw error if uniqueId doesn't exist
	protected void delete(String uniqueId)
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
	
	protected boolean isValidName(String name)
	{
		return name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
	}
	
	// Return true if uniqueId Exists
	protected boolean uniqueIdExists(String uniqueId)
	{
		String uniqueField = this.fields.get(0);
		Object castedValue = this.castValue(uniqueField, uniqueId);
		if(this.records.get(uniqueField).contains(castedValue))
			return true;
		else
			return false;
	}
}