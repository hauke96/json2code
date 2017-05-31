package json2code.converter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;

import com.google.gson.Gson;

import json2code.scheme.SchemeFile;
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
	 */
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
		
		Gson gson = new Gson();
		return gson.fromJson(content, SchemeFile.class);
	}
}
