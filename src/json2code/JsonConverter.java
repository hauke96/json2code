package json2code;

import java.io.File;
import java.text.MessageFormat;
import java.util.Arrays;

import json2code.converter.SchemeConverter;
import json2code.converter.SchemeReader;
import json2code.scheme.SchemeFile;
import juard.log.Logger;

/**
 * The json2code converter takes a scheme definition (in form of a json file) and creates classes of the specified target language.
 * 
 * @author hauke
 *
 */
public class JsonConverter
{
	/**
	 * Turn to true to print debug output and load the {@code example.json} file and {@code Java} target language.
	 */
	private static final boolean DUMMY_MODE = true;
	// private static final boolean DUMMY_MODE = false;
	
	public static void main(String[] args)
	{
		if (DUMMY_MODE)
		{
			Logger.__showDebug = true;
			args = new String[4];
			
			args[0] = "./example/example.json";
			args[1] = "java";
			args[2] = "./example/src/com/foo/bar/material/";
			args[3] = "com.foo.bar.material";
		}
		
		checkParameter(args);
		
		String fileName = args[0];
		String targetLanguage = args[1];
		String outputDirectory = args[2];
		
		String[] additionalArgs = Arrays.copyOfRange(args, 3, args.length);
		
		Logger.__info("Use file       : " + fileName);
		Logger.__info("Use language   : " + targetLanguage);
		Logger.__info("Use output dir : " + outputDirectory);
		
		SchemeReader schemeReader = new SchemeReader(fileName);
		SchemeFile schemeFile = schemeReader.getSchemeFile();
		
		SchemeConverter converter = new SchemeConverter(schemeFile, targetLanguage, additionalArgs);
		
		converter.convert();
		converter.printResult();
		converter.writeResult(outputDirectory);
	}
	
	/**
	 * Checks if the parameters are valid. If not, a fatal error will be printed and the application will be closed.
	 * 
	 * @param args
	 *            The arguments passed to this application.
	 */
	private static void checkParameter(String[] args)
	{
		if (args.length < 3)
		{
			Logger.__fatal("Invalid argument count! The arguments must be: \n\n"
			        + "    1.) The file name of the scheme definition\n"
			        + "    2.) The language of the output classes\n"
			        + "    3.) Output directory\n"
			        + "    4.) Optional language specific parameter (e.g. package name)\n\n"
			        + "Make sure your command applies this pattern.");
		}
		
		// Make sure the input scheme file exists
		File file = new File(args[0]);
		if (!file.exists())
		{
			String message = MessageFormat.format("File {0} does not exist!", args[0]);
			Logger.__fatal(message);
		}
	}
}
