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
	
	public Class()
	{
	}
	
	public String getName()
	{
		return name;
	}
	
	@SuppressWarnings ("unchecked")
	public List<Field> getProperties()
	{
		return (List<Field>) properties.clone();
	}
}
