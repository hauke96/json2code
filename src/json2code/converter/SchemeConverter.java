package json2code.converter;

import java.util.HashMap;
import java.util.Map;

import json2code.converter.interfaces.IPatternApplier;
import json2code.converter.java.JavaPatternApplier;
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
	private interface IPatternResolver
	{
		IPatternApplier get();
	}
	
	private static Map<String, IPatternResolver> knownLanguages;
	
	static
	{
		knownLanguages = new HashMap<>();
		
		knownLanguages.put("java", () -> getJavaPatternApplier());
	}
	
	private SchemeFile	schemeFile;
	private String		targetLanguage;
	
	/**
	 * Initializes the converter.
	 * 
	 * @param schemeFile
	 *            The scheme file which should be converted.
	 * @param targetLanguage
	 *            The language you want to have your output classes in.
	 */
	public SchemeConverter(SchemeFile schemeFile, String targetLanguage)
	{
		Contract.RequireNotNull(schemeFile);
		Contract.RequireNotNullOrEmpty(targetLanguage);
		
		this.schemeFile = schemeFile;
		this.targetLanguage = targetLanguage.toLowerCase();
	}
	
	/**
	 * Converts the scheme into classes of the target language. Specify them in the constructor.
	 * 
	 * @return A string of classes
	 */
	public String convert()
	{
		IPatternApplier patternApplier = getPatternApplierFor(targetLanguage);
		
		return patternApplier.convert(schemeFile);
	}
	
	/**
	 * Finds the pattern applier for the given language.
	 * 
	 * @param targetLanguage
	 *            The language which pattern you want to find.
	 * @return The pattern being found.
	 */
	private IPatternApplier getPatternApplierFor(String targetLanguage)
	{
		if (knownLanguages.containsKey(targetLanguage))
		{
			return knownLanguages.get(targetLanguage).get();
		}
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Unrecognized target language ");
		stringBuilder.append(targetLanguage);
		stringBuilder.append(".\nKnown languages are:\n\n");
		String message = stringBuilder.toString();
		
		for (String language : knownLanguages.keySet())
		{
			stringBuilder.append("    ");
			stringBuilder.append(language);
			stringBuilder.append("\n");
		}
		
		Logger.__fatal(message);
		return null; // will never be executed (Logger.__fatal will close the app), but the compiler needs it ;)
	}
	
	/**
	 * Gets the pattern for {@code Java}.
	 * 
	 * @return The java pattern applier.
	 */
	private static IPatternApplier getJavaPatternApplier()
	{
		return new JavaPatternApplier();
	}
}
