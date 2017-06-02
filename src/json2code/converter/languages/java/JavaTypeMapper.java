package json2code.converter.languages.java;

import json2code.converter.interfaces.ITypeMapper;

/**
 * This type mapper turns the json scheme-types into real java types.
 * 
 * @author hauke
 *
 */
public class JavaTypeMapper extends ITypeMapper
{
	/**
	 * Initializes the mapper and its map.
	 */
	public JavaTypeMapper()
	{
		typeMap.put(STRING, "String");
		typeMap.put(NUMBER, "int");
		typeMap.put(BOOLEAN, "boolean");
	}
}
