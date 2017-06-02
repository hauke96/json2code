package json2code.converter.languages.java;

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
	// TODO make ITypeMapper abstract class and move field up there
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
	
	// TODO move method also in super class (s.o.)
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
