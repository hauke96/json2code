package json2code.converter.languages.go;

import java.util.HashMap;

import json2code.converter.interfaces.ITypeMapper;
import juard.contract.Contract;

public class GoTypeMapper implements ITypeMapper
{
	// TODO make ITypeMapper abstract class and move field up there
	private HashMap<String, String> typeMap;
	
	/**
	 * Initializes the mapper and its map.
	 */
	public GoTypeMapper()
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
