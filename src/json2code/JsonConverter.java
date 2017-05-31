package json2code;

import java.io.File;
import java.text.MessageFormat;

import json2code.converter.SchemeConverter;
import json2code.converter.SchemeReader;
import json2code.scheme.SchemeFile;
import juard.log.Logger;

public class JsonConverter
{
	private static final boolean DUMMY_MODE = true;
	
	public static void main(String[] args)
	{
		if (DUMMY_MODE)
		{
			Logger.__showDebug = true;
			args = new String[2];
			
			args[0] = "example.json";
			args[1] = "java";
		}
		
		checkParameter(args);
		
		String fileName = args[0];
		String targetLanguage = args[1];
		
		Logger.__info("Use file     : " + fileName);
		Logger.__info("Use language : " + targetLanguage);
		
		SchemeReader schemeReader = new SchemeReader(fileName);
		SchemeFile schemeFile = schemeReader.getSchemeFile();
		
		SchemeConverter converter = new SchemeConverter(schemeFile, targetLanguage);
		System.out.println(converter.convert());
	}
	
	/**
	 * Checks if the parameters are valid. If not, a fatal error will be printed and the application will be closed.
	 * 
	 * @param args
	 *            The arguments passed to this application.
	 */
	private static void checkParameter(String[] args)
	{
		if (args.length != 2)
		{
			Logger.__fatal("Invalid argument count! The arguments must be: \n\n"
			        + "    1.) The file name of the scheme definition\n"
			        + "    2.) The language of the output classes\n\n"
			        + "Make sure your command applies this pattern.");
		}
		
		File file = new File(args[0]);
		if (!file.exists())
		{
			String message = MessageFormat.format("File {0} does not exist!", args[0]);
			Logger.__fatal(message);
		}
	}
}
