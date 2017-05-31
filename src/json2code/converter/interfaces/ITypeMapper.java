package json2code.converter.interfaces;

public interface ITypeMapper
{
	static final String	STRING	= "string";
	static final String	NUMBER	= "int";
	static final String	BOOLEAN	= "bool";
	
	String getTargetType(String schemeType);
}
