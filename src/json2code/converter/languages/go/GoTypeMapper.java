package json2code.converter.languages.go;

import json2code.converter.interfaces.AbstractTypeMapper;

/**
 * This type mapper turns the json scheme-types into real go types.
 * 
 * @author hauke
 *
 */
public class GoTypeMapper extends AbstractTypeMapper
{
	/**
	 * Initializes the mapper and its map.
	 */
	public GoTypeMapper()
	{
		typeMap.put(STRING, "string");
		typeMap.put(NUMBER, "int");
		typeMap.put(LONG, "int64");
		typeMap.put(BOOLEAN, "bool");
	}
}
