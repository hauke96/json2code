package json2code.converter.interfaces;

import json2code.scheme.Class;
import json2code.scheme.Field;

/**
 * A pattern is the basic mapping between json and the target language. 'Which json pattern leads to which target pattern?' is the question this interface answers.
 * 
 * @author hauke
 *
 */
public interface IPattern
{
	/**
	 * Creates the beginning/header of the target class.
	 * 
	 * @param clazz
	 *            The class you want to have the header of.
	 * @return The header.
	 */
	String getHeader(Class clazz);
	
	/**
	 * Creates the beginning/header of the target class.
	 * 
	 * @param clazz
	 *            The class you want to have the header of.
	 * @param packageName
	 *            The name of the package.
	 * @return The header.
	 */
	String getHeader(Class clazz, String packageName);

	/**
	 * Creates the definition of the given field.
	 * 
	 * @param field
	 *            The field which definition you want to have.
	 * @return The definition
	 */
	String getFieldDefinition(Field field);
	
	/**
	 * Creates the initialization method/routine of the target class.
	 * 
	 * @param clazz
	 *            The class which initialization you want to create.
	 * @return The method/routine to init the class.
	 */
	String getCreator(Class clazz);
	
	/**
	 * Gets some default methods/getter/setter for the given field according to its properties.
	 * 
	 * @param field
	 *            The field which methods you want to have.
	 * @return The methods.
	 */
	String getMethods(Field field);
	
	/**
	 * Creates the end of the target class.
	 * 
	 * @param clazz
	 *            The class which should be closed.
	 * @return The ending part.
	 */
	String getFooter(Class clazz);
}
