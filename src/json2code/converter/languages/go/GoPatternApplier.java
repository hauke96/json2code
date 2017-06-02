package json2code.converter.languages.go;

import java.util.HashMap;
import java.util.Map;

import json2code.converter.interfaces.AbstractPatternApplier;
import json2code.converter.interfaces.ITypeMapper;
import json2code.scheme.Class;
import json2code.scheme.Field;
import json2code.scheme.SchemeFile;
import juard.contract.Contract;
import juard.log.Logger;

// TODO make AbstractPatternApplier to an abstract class and move fields there
public class GoPatternApplier extends AbstractPatternApplier
{
	public GoPatternApplier(String[] additionalArgs)
	{
		Contract.RequireNotNull(additionalArgs);
		
		if (additionalArgs.length != 1)
		{
			Logger.__fatal("The amount of arguments after the java-argument must be 1. Only specify the package name.");
		}
		
		this.additionalArgs = additionalArgs;
		
		ITypeMapper typeMapper = new GoTypeMapper();
		
		pattern = new GoPattern(typeMapper);
		outputWriter = new GoOutputWriter();
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
			
			classCode.append(pattern.getFooter(clazz));
			
			classCode.append(pattern.getCreator(clazz));
			
			for (Field field : clazz.getFields())
			{
				classCode.append(pattern.getMethods(field, clazz));
			}
			
			classCode.append("\n");
			
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
		return resultMap;
	}
	
	// TODO also move this up into super class (s.o.)
	@Override
	public boolean hasResult()
	{
		return resultMap != null;
	}
	
}
