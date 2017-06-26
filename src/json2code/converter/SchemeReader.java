package json2code.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.management.RuntimeErrorException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import json2code.scheme.Field;
import json2code.scheme.SchemeFile;
import json2code.scheme.Class;
import juard.log.Logger;

/**
 * Basically this reads a scheme file.
 * 
 * @author hauke
 *
 */
public class SchemeReader
{
	private String fileName;
	
	/**
	 * Initializes the reader for the given file.
	 * 
	 * @param fileName
	 *            The file you want to load.
	 */
	public SchemeReader(String fileName)
	{
		this.fileName = fileName;
	}
	
	/**
	 * @return The converted scheme file.
	 * @throws Exception
	 * @throws IOException
	 *             When JSON-scheme file cannot be opened/read.
	 */
	// TODO add simple warning handler (lambda, which is defined here as "DefaultWarningHandler")
	public SchemeFile getSchemeFile()
	{
		Logger.__info("Read file ...");
		
		byte[] encoded = null;
		try
		{
			encoded = Files.readAllBytes(Paths.get(fileName));
		}
		catch (IOException e)
		{
			String message = MessageFormat.format("File {0} could not be read properly.", fileName);
			Logger.__fatal(message, e);
		}
		String content = new String(encoded, Charset.defaultCharset());
		
		Logger.__info("Convert json into scheme object...");
		
		Set<String> knownClasses = new HashSet<>();
		
		Gson gson = new GsonBuilder().create();
		SchemeFile result = gson.fromJson(content, SchemeFile.class);
		
		boolean warningOccurred = false;
		boolean errorOccurred = false;
		
		for (Class clazz : result.getClasses())
		{
			if (knownClasses.contains(clazz.getName()))
			{
				error("Class " + clazz.getName() + " appears multiple times.");
				errorOccurred = true;
			}
			else
			{
				knownClasses.add(clazz.getName());
			}
			
			// Name
			if (clazz.getName() == null || clazz.getName().isEmpty())
			{
				error("\"name\" field of a class is missing or empty.");
				errorOccurred = true;
			}
			
			// Fields
			if (clazz.getFields() == null)
			{
				error("\"properties\" object in class " + clazz.getName() + " is missing.");
				errorOccurred = true;
			}
			else if (clazz.getFields().size() == 0)
			{
				warn("Class " + clazz.getName() + " has no fields.");
			}
			else
			{
				Set<String> knownFields = new HashSet<>();
				
				for (Field field : clazz.getFields())
				{
					if (knownFields.contains(field.getName()))
					{
						error("Field \"" + field.getName() + "\" in class " + clazz.getName() + " appears multiple times.");
						errorOccurred = true;
					}
					else
					{
						knownFields.add(field.getName());
					}
					
					// Name
					if (field.getName() == null || field.getName().isEmpty())
					{
						error("\"name\" field is missing.");
						errorOccurred = true;
					}
					
					// Type
					if (field.getType() == null)
					{
						error("\"type\" of field " + field.getName() + " in class " + clazz.getName() + " is missing.");
						errorOccurred = true;
					}
					else if (field.getType().isEmpty())
					{
						warn("The type of the field " + field.getName() + " is empty.");
						warningOccurred = true;
					}
					
					// Constant
					if (field.isConstant() == null)
					{
						error("\"constant\" of field " + field.getName() + " in class " + clazz.getName() + " is missing.");
						errorOccurred = true;
					}
					
					// Collection
					if (field.isCollection() == null)
					{
						error("\"collection\" of field " + field.getName() + " in class " + clazz.getName() + " is missing.");
						errorOccurred = true;
					}
				}
			}
		}
		
		if (errorOccurred)
		{
			Logger.__fatal("Parsing exited with error(s).");
		}
		if (warningOccurred)
		{
			Logger.__info("Parsing exited with warning(s).");
		}
		
		return result;
		
		// Gson gson = new Gson();
		// return gson.fromJson(content, SchemeFile.class);
	}
	
	private void warn(String message)
	{
		Logger.__info("Parsing warning: " + message);
	}
	
	private void error(String message)
	{
		Logger.__error("Parsing error: " + message);
	}
}
