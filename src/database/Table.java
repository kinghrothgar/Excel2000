package database;
import java.util.ArrayList;
import java.util.Map;

// TODO: MAKE SURE CHECKS FOR NULL COME FIRST

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
	//protected abstract void update(String uniqueId, String field, String value);
	
	protected abstract void update(int recordIndex, String field, String value);
	
	protected abstract Object castValue(String field, String value);
	
	// Checks number of values, that required fields are not null, that they are the
	// right type, and casts them. Must have the correct number of values.
	protected ArrayList<Object> checkAndCastValues(ArrayList<String> values) 
	{
		ArrayList<Object> castedValues = new ArrayList<Object>(this.fields.size());
		
		// Check if castedValues size
		if(values.size() != this.fields.size())
			throw new IllegalArgumentException("Error: there must be " + this.fields.size() + " values");
		
		// Check and cast
		for(int i=0; i < this.fields.size(); i++)
			castedValues.add(this.castValue(this.fields.get(i), values.get(i)));
		
		return castedValues;
	}
	
	// Throw error if field doesn't exist
	protected ArrayList<Object> getField(String field)
	{
		return this.records.get(field);
	}
	
	protected ArrayList<String> getFieldList()
	{
		return this.fields;
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
	protected void delete(String[] uniqueIds)
	{
		int recordIndex;
		if(!(this.uniqueIdExists(uniqueIds)))
			// TODO: Throw error
			;
		else
		{
			recordIndex = this.getRecordIndex(uniqueIds);
			for(ArrayList<Object> field: this.records.values())
				field.remove(recordIndex);
		}
	}
	
	protected void delete(int recordIndex)
	{
		for(ArrayList<Object> field: this.records.values())
			field.remove(recordIndex);
	}
	
	protected boolean isValidName(String name)
	{
		return name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
	}
	
	protected boolean fieldContains(String field, Object value)
	{
		if(!(this.fields.contains(field)))
			throw new IllegalArgumentException("Error: " + field + " is not a valid field");
		else if(this.records.get(field).contains(value))
			return true;
		else
			return false;
	}
	
	// Return true if uniqueId Exists
	protected boolean uniqueIdExists(String[] uniqueIds)
	{
		if(uniqueIds.length == 1)
		{
			String uniqueField = this.fields.get(0);
			Object castedValue = this.castValue(uniqueField, uniqueIds[0]);
			if(this.records.get(uniqueField).contains(castedValue))
				return true;
			else
				return false;
		}
		else if(uniqueIds.length == 2)
		{
			String studentField = this.fields.get(0);
			String courseField = this.fields.get(1);
			Object studentCasted = this.castValue(studentField, uniqueIds[0]);
			Object courseCasted = this.castValue(courseField, uniqueIds[1]);
			for(int i=0; i < this.records.get(studentField).size(); i++)
			{
				if(this.records.get(studentField).get(i).equals(studentCasted))
				{
					if(this.records.get(courseField).get(i).equals(courseCasted))
						return true;
				}
			}
			return false;
		}
		return true;
	}
	
	protected int getRecordIndex(String[] uniqueIds)
	{
		if(uniqueIds.length == 1)
		{
			String uniqueField = this.fields.get(0);
			int index = this.records.get(uniqueField).indexOf(uniqueIds[0]);
			if(index == -1)
				// TODO: Throw error
				;
			else
				return index;
		}
		else
		{
			String studentField = this.fields.get(0);
			String courseField = this.fields.get(1);
			Object studentCasted = this.castValue(studentField, uniqueIds[0]);
			Object courseCasted = this.castValue(courseField, uniqueIds[1]);
			for(int i=0; i < this.records.get("student").size(); i++)
			{
				if(this.records.get(studentField).get(i).equals(studentCasted))
				{
					if(this.records.get(courseField).get(i).equals(courseCasted))
						return i;
				}
			}
		}
		return -1;
	}
	
	protected ArrayList<Object> getRecord(int recordIndex)
	{
		ArrayList<Object> record = new ArrayList<Object>();
		for(String field: this.fields)
			record.add(this.records.get(field).get(recordIndex));
		return record;
	}
	
	protected String[] sArray(String stuff)
	{
		String[] result = {stuff};
		return result;
	}
	
	protected String[] sArray(String stuff1, String stuff2)
	{
		String[] result = {stuff1, stuff2};
		return result;
	}
}