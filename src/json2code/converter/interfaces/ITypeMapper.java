package json2code.converter.interfaces;

/**
 * The type mapper maps the json scheme-types into the target language types.
 * 
 * @author hauke
 *
 */
public abstract class ITypeMapper
{
	protected static final String	STRING	= "string";
	protected static final String	NUMBER	= "int";
	protected static final String	BOOLEAN	= "bool";
	
	/**
	 * Converts the given scheme type into the type of the target language (specified by the implementation of this method).
	 * 
	 * @param schemeType
	 *            The scheme type.
	 * @return The type in the target language.
	 */
	public abstract String getTargetType(String schemeType);
}
