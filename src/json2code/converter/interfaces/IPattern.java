package json2code.converter.interfaces;

import json2code.scheme.Field;

public interface IPattern
{
	String getHeader(json2code.scheme.Class clazz);
	
	String getFieldDefinition(Field field);
	
	String getCreator(json2code.scheme.Class clazz);
	
	String getMethods(Field field);
	
	String getFooter(json2code.scheme.Class clazz);
}
