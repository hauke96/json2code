package json2code.converter.java;

import json2code.converter.interfaces.IPatternApplier;
import json2code.scheme.Class;
import json2code.scheme.Field;
import json2code.scheme.SchemeFile;

/**
 * The pattern applier is the real converter here. It takes the java pattern and the scheme file and turns them into java classes.
 * 
 * @author hauke
 *
 */
public class JavaPatternApplier implements IPatternApplier
{
	private JavaPattern pattern;
	
	/**
	 * Creates an empty pattern applier using the {@link JavaTypeMapper} and {@link JavaPattern}.
	 */
	public JavaPatternApplier()
	{
		JavaTypeMapper typeMapper = new JavaTypeMapper();
		pattern = new JavaPattern(typeMapper);
	}
	
	@Override
	public String convert(SchemeFile schemeFile)
	{
		StringBuilder result = new StringBuilder();
		
		for (Class clazz : schemeFile.getClasses())
		{
			result.append(pattern.getHeader(clazz));
			
			for (Field field : clazz.getFields())
			{
				result.append(pattern.getFieldDefinition(field));
			}
			
			result.append(pattern.getCreator(clazz));
			
			for (Field field : clazz.getFields())
			{
				result.append(pattern.getMethods(field));
			}
			
			result.append(pattern.getFooter(clazz));
			result.append("\n\n\n");
		}
		
		return result.toString();
	}
}
