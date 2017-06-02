package json2code.converter.languages.go;

import java.util.HashMap;

import json2code.converter.interfaces.AbstractPatternApplier;
import json2code.converter.interfaces.ITypeMapper;
import json2code.scheme.Class;
import json2code.scheme.Field;
import json2code.scheme.SchemeFile;
import juard.contract.Contract;
import juard.log.Logger;

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
}
