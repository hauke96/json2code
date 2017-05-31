package json2code.scheme;

/**
 * A field is the definition of an actual field in the output class.
 * 
 * @author hauke
 *
 */
public class Field
{
	private String	name;
	private String	type;
	private boolean	constant;
	private boolean	collection;
	
	public Field()
	{
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getType()
	{
		return type;
	}
	
	public boolean isConstant()
	{
		return constant;
	}
	
	public boolean isCollection()
	{
		return collection;
	}
}
