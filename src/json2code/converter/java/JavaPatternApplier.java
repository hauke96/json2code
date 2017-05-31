package json2code.converter.java;

import json2code.converter.interfaces.IPatternApplier;
import json2code.scheme.Class;
import json2code.scheme.Field;
import json2code.scheme.SchemeFile;

public class JavaPatternApplier implements IPatternApplier
{
	private JavaPattern pattern;
	
	public JavaPatternApplier()
	{
		JavaTypeMapper typeMapper = new JavaTypeMapper();
		pattern = new JavaPattern(typeMapper);
	}
	
	@Override
	public String convert(SchemeFile schemeFile)
	{
		StringBuilder result = new StringBuilder();
		
		for (Class clazz : schemeFile.getSchemes())
		{
			result.append(pattern.getHeader(clazz));
			
			for (Field field : clazz.getProperties())
			{
				result.append(pattern.getFieldDefinition(field));
			}
			
			result.append(pattern.getCreator(clazz));
			
			for (Field field : clazz.getProperties())
			{
				result.append(pattern.getMethods(field));
			}
			
			result.append(pattern.getFooter(clazz));
			result.append("\n\n\n");
		}
		
		return result.toString();
	}
	
}
