package json2code.converter.languages.java;

import java.util.HashMap;
import java.util.Map;

import json2code.converter.interfaces.IOutputWriter;
import json2code.converter.interfaces.IPattern;
import json2code.converter.interfaces.IPatternApplier;
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
// TODO make IPatternApplier to an abstract class and move fields there
public class JavaPatternApplier implements IPatternApplier
{
	private IPattern		pattern;
	private IOutputWriter	outputWriter;
	
	private HashMap<String, String>	resultMap;
	private String[]				additionalArgs;
	
	/**
	 * Creates an empty pattern applier using the {@link JavaTypeMapper} and {@link JavaPattern}.
	 * 
	 * @param additionalArgs
	 *            Some java specific arguments
	 */
	public JavaPatternApplier(String[] additionalArgs)
	{
		Contract.RequireNotNull(additionalArgs);
		
		if (additionalArgs.length != 1)
		{
			Logger.__fatal("The amount of arguments after the java-argument must be 1. Only specify the package name.");
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
		
		Contract.EnsureNotNull(hasResult());
	}
	
	// TODO also move this up into super class (s.o.)
	@Override
	public void writeResultTo(String outputDirectory)
	{
		Contract.RequireNotNullOrEmpty(outputDirectory);
		Contract.Require(hasResult());
		
		outputWriter.write(outputDirectory, resultMap);
	}
	
	// TODO also move this up into super class (s.o.)
	@Override
	public Map<String, String> getResult()
	{
		Contract.Require(hasResult());
		
		return resultMap;
	}
	
	// TODO also move this up into super class (s.o.)
	@Override
	public boolean hasResult()
	{
		return resultMap != null;
	}
}
