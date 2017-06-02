package json2code.converter.languages.go;

import json2code.converter.interfaces.ITypeMapper;

/**
 * This type mapper turns the json scheme-types into real go types.
 * 
 * @author hauke
 *
 */
public class GoTypeMapper extends ITypeMapper
{
	/**
	 * Initializes the mapper and its map.
	 */
	public GoTypeMapper()
	{
		typeMap.put(STRING, "String");
		typeMap.put(NUMBER, "int");
		typeMap.put(BOOLEAN, "boolean");
	}
}
