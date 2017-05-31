package json2code.converter.interfaces;

import java.util.Map;

import json2code.scheme.SchemeFile;

/**
 * The pattern applier actually knows how a class in the target language is structured. It therefore has to construct the target code.
 * 
 * @author hauke
 *
 */
public interface IPatternApplier
{
	/**
	 * Converts the scheme file into a string of classes.
	 * 
	 * @param schemeFile
	 *            A map from class name into class code.
	 * @return A string with all classes.
	 */
	Map<String, String> convert(SchemeFile schemeFile);
}
