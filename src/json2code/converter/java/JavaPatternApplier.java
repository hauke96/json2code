package json2code.converter.java;

import java.util.HashMap;
import java.util.Map;

import json2code.converter.interfaces.IOutputWriter;
import json2code.converter.interfaces.IPattern;
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
	private IPattern pattern;
	private IOutputWriter outputWriter;
	
	/**
	 * Creates an empty pattern applier using the {@link JavaTypeMapper} and {@link JavaPattern}.
	 */
	public JavaPatternApplier()
	{
		JavaTypeMapper typeMapper = new JavaTypeMapper();
		pattern = new JavaPattern(typeMapper);
		outputWriter = new JavaOutputWriter();
	}
	
	@Override
	public Map<String, String> convert(SchemeFile schemeFile)
	{
		Map<String, String> resultMap = new HashMap<>(schemeFile.getClasses().size());
		
		for (Class clazz : schemeFile.getClasses())
		{
			StringBuilder classCode = new StringBuilder();
			
			classCode.append(pattern.getHeader(clazz));
			
			for (Field field : clazz.getFields())
			{
				classCode.append(pattern.getFieldDefinition(field));
			}
			
			classCode.append(pattern.getCreator(clazz));
			
			for (Field field : clazz.getFields())
			{
				classCode.append(pattern.getMethods(field));
			}
			
			classCode.append(pattern.getFooter(clazz));
			classCode.append("\n\n\n");
			
			resultMap.put(clazz.getName(), classCode.toString());
		}
		
		return resultMap;
	}
}
