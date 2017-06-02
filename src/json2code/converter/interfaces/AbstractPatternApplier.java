package json2code.converter.interfaces;

import java.util.HashMap;
import java.util.Map;

import json2code.scheme.SchemeFile;
import juard.contract.Contract;

/**
 * The pattern applier actually knows how a class in the target language is structured. It therefore has to construct the target code.
 * 
 * @author hauke
 *
 */
public abstract class AbstractPatternApplier
{
	protected IPattern		pattern;
	protected IOutputWriter	outputWriter;
	
	protected HashMap<String, String>	resultMap;
	protected String[]					additionalArgs;
	
	/**
	 * Converts the scheme file into a string of classes.
	 * 
	 * @param schemeFile
	 *            A map from class name into class code.
	 */
	public abstract void convert(SchemeFile schemeFile);
	
	/**
	 * Writes the result created in {@link AbstractPatternApplier#convert(SchemeFile)} into the given output directory.
	 * The {@link #convert(SchemeFile)} method must be called first.
	 * 
	 * @param outputDirectory
	 *            The directory where the sources should be
	 */
	public void writeResultTo(String outputDirectory)
	{
		Contract.RequireNotNullOrEmpty(outputDirectory);
		Contract.Require(hasResult());
		
		outputWriter.write(outputDirectory, resultMap);
	}
	
	/**
	 * Gets the result from the {@link #convert(SchemeFile)} method which must be called first.
	 * 
	 * @return The result map, which maps from class name to class code.
	 */
	public Map<String, String> getResult()
	{
		Contract.Require(hasResult());
		
		return resultMap;
	}
	
	/**
	 * @return True when a result exists. Call {@link #convert(SchemeFile)} to create a result.
	 */
	public boolean hasResult()
	{
		return resultMap != null;
	}
}
