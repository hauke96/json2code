package json2code.scheme;

import java.util.ArrayList;
import java.util.List;

/**
 * A scheme is the definition of one single class in the JSON file.
 * 
 * @author hauke
 *
 */
public class Class
{
	private String				name;
	private ArrayList<Field>	properties;
	
	/**
	 * @return The name of the class.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return The list of fields.
	 */
	@SuppressWarnings ("unchecked")
	public List<Field> getFields()
	{
		return (List<Field>) properties.clone();
	}
}
