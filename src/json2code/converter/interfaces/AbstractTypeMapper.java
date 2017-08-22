package json2code.converter.interfaces;

import java.util.HashMap;
import java.util.Map;

import juard.contract.Contract;

/**
 * The type mapper maps the json scheme-types into the target language types.
 * 
 * @author hauke
 *
 */
public abstract class AbstractTypeMapper
{
	protected static final String	STRING	= "string";
	protected static final String	NUMBER	= "int";
	protected static final String	BOOLEAN	= "bool";
	
	protected Map<String, String> typeMap;
	
	public AbstractTypeMapper()
	{
		typeMap = new HashMap<>();
	}
	
	/**
	 * Converts the given scheme type into the type of the target language (specified by the implementation of this method).
	 * 
	 * @param schemeType
	 *            The scheme type.
	 * @return The type in the target language.
	 */
	public String getTargetType(String schemeType)
	{
		if (!typeMap.containsKey(schemeType))
		{
			return schemeType;
		}
		
		String targetType = typeMap.get(schemeType);
		
		Contract.NotNull(targetType);
		return targetType;
	}
}
