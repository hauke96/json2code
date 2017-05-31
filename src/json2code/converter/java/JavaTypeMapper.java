package json2code.converter.java;

import java.util.HashMap;

import json2code.converter.interfaces.ITypeMapper;
import juard.contract.Contract;

/**
 * This type mapper turns the json scheme-types into real java types.
 * 
 * @author hauke
 *
 */
public class JavaTypeMapper implements ITypeMapper
{
	private HashMap<String, String> typeMap;
	
	/**
	 * Initializes the mapper and its map.
	 */
	public JavaTypeMapper()
	{
		typeMap = new HashMap<String, String>();
		
		typeMap.put(STRING, "String");
		typeMap.put(NUMBER, "int");
		typeMap.put(BOOLEAN, "boolean");
	}
	
	@Override
	public String getTargetType(String schemeType)
	{
		if (!typeMap.containsKey(schemeType))
		{
			return schemeType;
		}
		
		String targetType = typeMap.get(schemeType);
		
		Contract.EnsureNotNull(targetType);
		return targetType;
	}
	
}
