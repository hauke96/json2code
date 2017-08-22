package json2code.converter.languages.java;

import java.util.HashMap;

import json2code.converter.interfaces.AbstractPatternApplier;
import json2code.scheme.Class;
import json2code.scheme.Field;
import json2code.scheme.SchemeFile;
import juard.contract.Contract;
import juard.log.Logger;

/**
 * The pattern applier is the real converter here. It takes the java pattern and the scheme file and turns them into java classes.
 * 
 * @author hauke
 *
 */
public class JavaPatternApplier extends AbstractPatternApplier
{
	/**
	 * Creates an empty pattern applier using the {@link JavaTypeMapper} and {@link JavaPattern}.
	 * 
	 * @param additionalArgs
	 *            Some java specific arguments
	 */
	public JavaPatternApplier(String[] additionalArgs)
	{
		Contract.NotNull(additionalArgs);
		
		if (additionalArgs.length != 1)
		{
			Logger.fatal("The amount of arguments after the java-argument must be 1. Only specify the package name.");
		}
		
		this.additionalArgs = additionalArgs;
		
		JavaTypeMapper typeMapper = new JavaTypeMapper();
		pattern = new JavaPattern(typeMapper);
		outputWriter = new JavaOutputWriter();
	}
	
	@Override
	public void convert(SchemeFile schemeFile)
	{
		resultMap = new HashMap<>(schemeFile.getClasses().size());
		
		for (Class clazz : schemeFile.getClasses())
		{
			StringBuilder classCode = new StringBuilder();
			
			classCode.append(pattern.getHeader(clazz, additionalArgs[0]));
			
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
		
		Contract.NotNull(hasResult());
	}
}
