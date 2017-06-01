package json2code.converter.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import json2code.converter.interfaces.IOutputWriter;
import juard.contract.Contract;
import juard.log.Logger;

/**
 * Creates for every java class an own file with its content.
 * 
 * @author hauke
 *
 */
public class JavaOutputWriter implements IOutputWriter
{
	@Override
	public void write(String outputDirectory, Map<String, String> classes)
	{
		Contract.RequireNotNullOrEmpty(outputDirectory);
		Contract.RequireNotNull(classes);
		
		if (!outputDirectory.endsWith("/"))
		{
			outputDirectory += "/";
		}
		
		for (String className : classes.keySet())
		{
			File file = new File(outputDirectory + className + ".java");
			
			// Create output file if not exists
			// If file already exists, this will to nothing.
			file.getParentFile().mkdirs();
			
			// Write to output file
			try (FileOutputStream stream = new FileOutputStream(file))
			{
				stream.write(classes.get(className).getBytes());
			}
			catch (IOException e)
			{
				Logger.__error("Cannot write to file " + file.getName(), e);
			}
		}
	}
}
