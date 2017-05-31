package json2code.scheme;

import java.util.ArrayList;
import java.util.List;

public class SchemeFile
{
	private ArrayList<Class> schemes;
	
	public SchemeFile()
	{
	}
	
	@SuppressWarnings ("unchecked")
	public List<Class> getSchemes()
	{
		return (List<Class>) schemes.clone();
	}
}
