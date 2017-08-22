package json2code.converter;

import java.util.HashMap;
import java.util.Map;

import json2code.converter.interfaces.AbstractPatternApplier;
import json2code.converter.languages.go.GoPatternApplier;
import json2code.converter.languages.java.JavaPatternApplier;
import json2code.scheme.SchemeFile;
import juard.contract.Contract;
import juard.log.Logger;

/**
 * The scheme converter takes the scheme file and the target language and turn them into classes of this target language.
 * 
 * @author hauke
 *
 */
public class SchemeConverter
{
	private static Map<String, AbstractPatternApplier> knownLanguages;
	
	private SchemeFile	schemeFile;
	private String		targetLanguage;
	
	/**
	 * Initializes the converter.
	 * 
	 * @param schemeFile
	 *            The scheme file which should be converted.
	 * @param targetLanguage
	 *            The language you want to have your output classes in.
	 * @param additionalArgs
	 *            Some additional arguments that are language specific.
	 */
	public SchemeConverter(SchemeFile schemeFile, String targetLanguage, String[] additionalArgs)
	{
		Contract.NotNull(schemeFile);
		Contract.NotNullOrEmpty(targetLanguage);
		Contract.NotNull(additionalArgs);
		
		this.schemeFile = schemeFile;
		this.targetLanguage = targetLanguage.toLowerCase();
		
		if (knownLanguages == null)
		{
			initKnownLanguages(additionalArgs);
		}
	}
	
	private void initKnownLanguages(String[] additionalArgs)
	{
		knownLanguages = new HashMap<>();
		
		knownLanguages.put("java", new JavaPatternApplier(additionalArgs));
		knownLanguages.put("go", new GoPatternApplier(additionalArgs));
	}
	
	/**
	 * Converts the scheme into classes of the target language. Specify them in the constructor.
	 */
	public void convert()
	{
		AbstractPatternApplier patternApplier = getPatternApplierFor(targetLanguage);
		
		patternApplier.convert(schemeFile);
	}
	
	/**
	 * Prints the result of the converter.
	 * Call its {@link AbstractPatternApplier#convert(SchemeFile)} method first.
	 */
	public void printResult()
	{
		AbstractPatternApplier patternApplier = getPatternApplierFor(targetLanguage);
		
		if (patternApplier.hasResult())
		{
			Map<String, String> result = patternApplier.getResult();
			result.values().forEach(string -> Logger.info(string));
		}
		else
		{
			Logger.error("Converter for language " + targetLanguage + " has no result. First convert the json, then get the result.");
		}
	}
	
	/**
	 * Writes the result of the converter to the given output directory.
	 * Call its {@link AbstractPatternApplier#convert(SchemeFile)} method first.
	 * 
	 * @param outputDirectory
	 *            The directory the result classes should be in.
	 */
	public void writeResult(String outputDirectory)
	{
		AbstractPatternApplier patternApplier = getPatternApplierFor(targetLanguage);
		
		if (patternApplier.hasResult())
		{
			patternApplier.writeResultTo(outputDirectory);
		}
		else
		{
			Logger.error("Converter for language " + targetLanguage + " has no result. First convert the json, then get the result.");
		}
	}
	
	/**
	 * Finds the pattern applier for the given language.
	 * 
	 * @param targetLanguage
	 *            The language which pattern you want to find.
	 * @return The pattern being found.
	 */
	private AbstractPatternApplier getPatternApplierFor(String targetLanguage)
	{
		if (knownLanguages.containsKey(targetLanguage))
		{
			return knownLanguages.get(targetLanguage);
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Unrecognized target language ");
		stringBuilder.append(targetLanguage);
		stringBuilder.append(".\nKnown languages are:\n\n");
		
		for (String language : knownLanguages.keySet())
		{
			stringBuilder.append("    ");
			stringBuilder.append(language);
			stringBuilder.append("\n");
		}
		String message = stringBuilder.toString();
		
		Logger.fatal(message);
		return null; // will never be executed (Logger.__fatal will close the app), but the compiler needs it ;)
	}
}
