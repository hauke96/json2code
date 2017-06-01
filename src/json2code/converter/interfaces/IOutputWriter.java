package json2code.converter.interfaces;

import java.util.Map;

/**
 * The output writer writes the conversion result in a proper way into the output directory.
 * 
 * @author hauke
 *
 */
public interface IOutputWriter
{
	/**
	 * Takes the result map and writes it to the output directory.
	 * 
	 * @param outputDirectory
	 *            The directory all classes should be in.
	 * @param classes
	 *            The map from language to class code.
	 */
	void write(String outputDirectory, Map<String, String> classes);
}
