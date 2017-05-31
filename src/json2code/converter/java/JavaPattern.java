package json2code.converter.java;

import java.text.MessageFormat;
import java.util.List;

import json2code.converter.interfaces.IPattern;
import json2code.converter.interfaces.ITypeMapper;
import json2code.scheme.Class;
import json2code.scheme.Field;
import juard.contract.Contract;

public class JavaPattern implements IPattern
{
	private ITypeMapper typeMapper;
	
	public JavaPattern(ITypeMapper typeMapper)
	{
		Contract.RequireNotNull(typeMapper);
		
		this.typeMapper = typeMapper;
	}
	
	@Override
	public String getHeader(Class clazz)
	{
		Contract.RequireNotNull(clazz);
		
		String header = MessageFormat.format("public class {0} '{'\n", clazz.getName());
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
		return MessageFormat.format("private {0} {1};\n", type, name);
	}
	
	@Override
	public String getCreator(Class clazz)
	{
		Contract.RequireNotNull(clazz);
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("public ");
		stringBuilder.append(clazz.getName());
		stringBuilder.append("(){}\npublic ");
		stringBuilder.append(clazz.getName());
		stringBuilder.append("(");
		
		List<Field> properties = clazz.getProperties();
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
		stringBuilder.append("){\n");
		
		for (Field field : properties)
		{
			stringBuilder.append("this.");
			stringBuilder.append(field.getName());
			stringBuilder.append(" = ");
			stringBuilder.append(field.getName());
			stringBuilder.append(";\n");
		}
		
		stringBuilder.append("}\n");
		
		String constructor = stringBuilder.toString();
		return constructor;
	}
	
	@Override
	public String getMethods(Field field)
	{
		Contract.RequireNotNull(field);
		
		String method = MessageFormat.format("public {0} get{1}()'{'\nreturn {1};'}'\n", getTypeOf(field), field.getName());
		
		if (!field.isConstant())
		{
			method += MessageFormat.format("public void set{1}({0} {1})'{'\nthis.{1} = {1};'}'\n", getTypeOf(field), field.getName());
		}
		
		return method;
	}
	
	@Override
	public String getFooter(Class clazz)
	{
		return "}";
	}
	
	private String getTypeOf(Field field)
	{
		Contract.RequireNotNull(field);
		
		String rawType = typeMapper.getTargetType(field.getType());
		String type = field.isCollection() ? "List<" + rawType + ">" : rawType;
		
		return type;
	}
}
