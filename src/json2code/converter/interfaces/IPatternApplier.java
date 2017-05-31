package json2code.converter.interfaces;

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
	 *            The scheme file to convert.
	 * @return A string with all classes.
	 */
	// TODO return a list of class strings
	String convert(SchemeFile schemeFile);
}
