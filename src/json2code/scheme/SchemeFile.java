package json2code.scheme;

import java.util.ArrayList;
import java.util.List;

/**
 * A scheme file is the file containing the actual definitions of the classes. This must be a json file.
 * 
 * @author hauke
 *
 */
public class SchemeFile
{
	private ArrayList<Class> schemes;
	
	/**
	 * Adds a clazz to the scheme file.
	 * @param clazz The class to add.
	 */
	public void addClass(Class clazz)
	{
		schemes.add(clazz);
	}
	
	/**
	 * @return All classes defined in the scheme file.
	 */
	@SuppressWarnings ("unchecked")
	public List<Class> getClasses()
	{
		return (List<Class>) schemes.clone();
	}
}
