package json2code.converter;

import java.util.HashMap;
import java.util.Map;

import json2code.converter.interfaces.IPatternApplier;
import json2code.converter.java.JavaPatternApplier;
import json2code.scheme.SchemeFile;
import juard.contract.Contract;
import juard.log.Logger;

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
		
		knownLanguages.put("java", () -> getJavaPattern());
	}
	
	private SchemeFile	schemeFile;
	private String		targetLanguage;
	
	public SchemeConverter(SchemeFile schemeFile, String targetLanguage)
	{
		Contract.RequireNotNull(schemeFile);
		Contract.RequireNotNullOrEmpty(targetLanguage);
		
		this.schemeFile = schemeFile;
		this.targetLanguage = targetLanguage.toLowerCase();
	}
	
	public String convert()
	{
		IPatternApplier patternApplier = getPatternFor(targetLanguage);
		
		return patternApplier.convert(schemeFile);
	}
	
	private IPatternApplier getPatternFor(String targetLanguage)
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
	
	private static IPatternApplier getJavaPattern()
	{
		return new JavaPatternApplier();
	}
}
