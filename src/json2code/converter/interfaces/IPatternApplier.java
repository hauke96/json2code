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
	 */
	void convert(SchemeFile schemeFile);
	
	/**
	 * Writes the result created in {@link IPatternApplier#convert(SchemeFile)} into the given output directory.
	 * The {@link #convert(SchemeFile)} method must be called first.
	 * 
	 * @param outputDirectory
	 *            The directory where the sources should be
	 */
	void writeResultTo(String outputDirectory);
	
	/**
	 * Gets the result from the {@link #convert(SchemeFile)} method which must be called first.
	 * 
	 * @return The result map, which maps from class name to class code.
	 */
	Map<String, String> getResult();
	
	/**
	 * @return True when a result exists. Call {@link #convert(SchemeFile)} to create a result.
	 */
	boolean hasResult();
}
