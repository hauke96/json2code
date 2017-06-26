package json2code.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;

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
		
		Gson gson = new GsonBuilder().create();
		SchemeFile result = gson.fromJson(content, SchemeFile.class);
		
		for (Class clazz : result.getClasses())
		{
			try
			{
				// Name
				if (clazz.getName() == null) throw new Exception("\"name\" field of a class is missing or null."); // TODO put this in error() method
				else if (clazz.getName().isEmpty()) ;// TODO warning
				
				// Fields
				if (clazz.getFields() == null) throw new Exception("\"properties\" object in class " + clazz.getName() + " is missing or null."); // TODO put this in error() method
				else if (clazz.getFields().size() == 0) ;// TODO warning
				
				for (Field field : clazz.getFields())
				{
					// Name
					if (field.getName() == null) throw new Exception("\"name\" field is missing or null."); // TODO put this in error() method
					else if (field.getName().isEmpty()) ;// TODO warning
					
					// Type
					if (field.getType() == null) throw new Exception("\"type\" of field " + field.getName() + " in class " + clazz.getName() + " is missing or null."); // TODO put this in
					                                                                                                                                                    // error()
					else if (field.getType().isEmpty()) ;// TODO warning
					
					// Constant
					if (field.isConstant() == null) throw new Exception("\"constant\" of field " + field.getName() + " in class " + clazz.getName()
					        + " is missing or null."); // TODO put this in
					                                   // error() method
					// Collection
					if (field.isCollection() == null) throw new Exception("\"collection\" of field " + field.getName() + " in class " + clazz.getName()
					        + " is missing or null."); // TODO put this in error() method
				}
			}
			catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
		
		return result;
		
		// Gson gson = new Gson();
		// return gson.fromJson(content, SchemeFile.class);
	}
	
	private void warn()
	{
		Logger.__info("Warning during parsing.");
	}
}
