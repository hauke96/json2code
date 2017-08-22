package json2code.converter.languages.go;

import java.util.List;

import json2code.converter.interfaces.AbstractTypeMapper;
import json2code.converter.interfaces.IPattern;
import json2code.scheme.Class;
import json2code.scheme.Field;
import juard.contract.Contract;

public class GoPattern implements IPattern
{
	private AbstractTypeMapper typeMapper;
	
	/**
	 * Initializes the pattern.
	 * 
	 * @param typeMapper
	 *            A java type mapper.
	 */
	public GoPattern(AbstractTypeMapper typeMapper)
	{
		Contract.NotNull(typeMapper);
		
		this.typeMapper = typeMapper;
	}
	
	@Override
	public String getHeader(Class clazz)
	{
		Contract.NotNull(clazz);
		
		StringBuilder builder = new StringBuilder();
		builder.append("type ");
		builder.append(clazz.getName());
		builder.append(" struct {\n");
		
		return builder.toString();
	}
	
	@Override
	public String getHeader(Class clazz, String packageName)
	{
		Contract.NotNull(clazz);
		
		String header = "package " + packageName + "\n\n";
		
		header += getHeader(clazz);
		
		return header;
	}
	
	@Override
	public String getFieldDefinition(Field field)
	{
		Contract.NotNull(field);
		
		String name = field.getName();
		
		StringBuilder builder = new StringBuilder();
		builder.append("\t");
		builder.append(startWithUpperCase(name));
		builder.append(" ");
		builder.append(" ");
		builder.append(getTypeOf(field));
		builder.append(" ");
		builder.append("`json:\"");
		builder.append(name);
		builder.append("\"`\n");
		
		return builder.toString();
	}
	
	@Override
	public String getCreator(Class clazz)
	{
		Contract.NotNull(clazz);
		
		StringBuilder builder = new StringBuilder();
		builder.append("func New");
		builder.append(clazz.getName());
		builder.append("(");
		
		// Create parameters
		List<Field> properties = clazz.getFields();
		for (int i = 0; i < properties.size(); i++)
		{
			Field field = properties.get(i);
			
			builder.append(startWithLowerCase(field.getName()));
			builder.append(" ");
			builder.append(getTypeOf(field));
			
			// comma between parameters (except the last one)
			if (i + 1 != properties.size())
			{
				builder.append(", ");
			}
		}
		
		builder.append(")");
		builder.append(clazz.getName()); // return type
		builder.append("{\n\t");
		builder.append("return ");
		builder.append(clazz.getName());
		builder.append("{");
		
		// Initialize class
		for (int i = 0; i < properties.size(); i++)
		{
			Field field = properties.get(i);
			
			String upperCaseFieldName = startWithUpperCase(field.getName());
			String lowerCaseFieldName = startWithLowerCase(field.getName());
			
			builder.append(upperCaseFieldName);
			builder.append(": ");
			builder.append(lowerCaseFieldName);
			
			// comma between arguments (except the last one)
			if (i + 1 != properties.size())
			{
				builder.append(", ");
			}
		}
		
		builder.append("}\n}\n\n");
		
		return builder.toString();
	}
	
	@Override
	public String getMethods(Field field)
	{
		throw new UnsupportedOperationException("Please use GetMethods(Field field, Class clazz)");
	}
	
	@Override
	public String getMethods(Field field, Class clazz)
	{
		char receriverName = field.getName().substring(0, 1).toLowerCase().charAt(0);
		
		StringBuilder builder = new StringBuilder();
		builder.append("func (");
		builder.append(receriverName);
		builder.append(" ");
		builder.append(clazz.getName());
		builder.append(") Get");
		builder.append(field.getName());
		builder.append("() ");
		builder.append(getTypeOf(field));
		builder.append(" {\n\treturn ");
		builder.append(receriverName);
		builder.append(".");
		builder.append(startWithUpperCase(field.getName()));
		builder.append("\n}\n\n");
		
		return builder.toString();
	}
	
	@Override
	public String getFooter(Class clazz)
	{
		return "}\n\n";
	}
	
	private String startWithLowerCase(String text)
	{
		return Character.toLowerCase(text.charAt(0)) + text.substring(1);
	}
	
	private String startWithUpperCase(String text)
	{
		return Character.toUpperCase(text.charAt(0)) + text.substring(1);
	}
	
	/**
	 * Gets the type of the given field considering its properties (e.g. {@code isCollection}).
	 * 
	 * @param field
	 *            The field which type you want to know.
	 * @return The real type.
	 */
	// TODO move into type mapper
	private String getTypeOf(Field field)
	{
		Contract.NotNull(field);
		
		String rawType = typeMapper.getTargetType(field.getType());
		String type = field.isCollection() ? "[]" + rawType : rawType;
		
		return type;
	}
}
