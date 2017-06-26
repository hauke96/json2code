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
	private Boolean	constant;
	private Boolean	collection;
	
	/**
	 * @return The variable name of the field.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @return The raw type of the field.
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * @return True when this field is a constant, false when not.
	 */
	public Boolean isConstant()
	{
		return constant;
	}
	
	/**
	 * @return True when this field is a collection, false when not.
	 */
	public Boolean isCollection()
	{
		return collection;
	}
}
