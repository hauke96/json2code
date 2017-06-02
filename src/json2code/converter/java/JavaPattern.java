package json2code.converter.java;

import java.text.MessageFormat;
import java.util.List;

import json2code.converter.interfaces.IPattern;
import json2code.converter.interfaces.ITypeMapper;
import json2code.scheme.Class;
import json2code.scheme.Field;
import juard.contract.Contract;

/**
 * The Java pattern provides methods to convert a scheme file into java classes.
 * 
 * @author hauke
 *
 */
public class JavaPattern implements IPattern
{
	private ITypeMapper typeMapper;
	
	/**
	 * Initializes the pattern.
	 * 
	 * @param typeMapper
	 *            A java type mapper.
	 */
	public JavaPattern(ITypeMapper typeMapper)
	{
		Contract.RequireNotNull(typeMapper);
		
		this.typeMapper = typeMapper;
	}
	
	@Override
	public String getHeader(Class clazz)
	{
		Contract.RequireNotNull(clazz);
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("import java.util.List;\n\n");
		builder.append("public class ");
		builder.append(clazz.getName());
		builder.append(" \n{\n");
		
		return builder.toString();
	}
	
	@Override
	public String getHeader(Class clazz, String packageName)
	{
		Contract.RequireNotNull(clazz);
		
		String header = "package " + packageName + ";\n\n";
		
		header += getHeader(clazz);
		
		return header;
	}
	
	@Override
	public String getFieldDefinition(Field field)
	{
		Contract.RequireNotNull(field);
		
		// For collections
		String type = getTypeOf(field);
		
		// Name
		String name = field.getName();
		
		// Generate definition
		return MessageFormat.format("\tprivate {0} {1};\n", type, name);
	}
	
	@Override
	public String getCreator(Class clazz)
	{
		Contract.RequireNotNull(clazz);
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\tpublic ");
		stringBuilder.append(clazz.getName());
		stringBuilder.append("(){}\n\n\tpublic ");
		stringBuilder.append(clazz.getName());
		stringBuilder.append("(");
		
		List<Field> properties = clazz.getFields();
		for (int i = 0; i < properties.size(); i++)
		{
			Field field = properties.get(i);
			
			stringBuilder.append(getTypeOf(field));
			stringBuilder.append(" ");
			stringBuilder.append(field.getName());
			
			// comma between arguments (except the last one)
			if (i + 1 != properties.size())
			{
				stringBuilder.append(", ");
			}
		}
		
		// End of constructor signature
		stringBuilder.append(")\n\t{\n");
		
		for (Field field : properties)
		{
			stringBuilder.append("\t\tthis.");
			stringBuilder.append(field.getName());
			stringBuilder.append(" = ");
			stringBuilder.append(field.getName());
			stringBuilder.append(";\n");
		}
		
		stringBuilder.append("\t}\n");
		
		String constructor = stringBuilder.toString();
		return constructor;
	}
	
	@Override
	public String getMethods(Field field)
	{
		Contract.RequireNotNull(field);
		
		String method = MessageFormat.format("\n\tpublic {0} get{1}()\n\t'{'\n\t\treturn {1};\n\t'}'\n", getTypeOf(field), field.getName());
		
		if (!field.isConstant())
		{
			method += MessageFormat.format("\n\tpublic void set{1}({0} {1})\n\t'{'\n\t\tthis.{1} = {1};\n\t'}'\n", getTypeOf(field), field.getName());
		}
		
		return method;
	}
	
	@Override
	public String getMethods(Field field, Class clazz)
	{
		Contract.RequireNotNull(field);
		
		return getMethods(field);
	}
	
	@Override
	public String getFooter(Class clazz)
	{
		return "}";
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
		Contract.RequireNotNull(field);
		
		String rawType = typeMapper.getTargetType(field.getType());
		String type = field.isCollection() ? "List<" + rawType + ">" : rawType;
		
		return type;
	}
}
