package util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


public class ReflectionUtil {
	
	public static String printObjectDetails(Object obj) {
		if(obj == null)
			return "null";
			
		StringBuffer sb = new StringBuffer();
		
		sb.append(obj.getClass()+"\n");
		
		for(Field field: obj.getClass().getDeclaredFields()) {
			
			field.setAccessible(true);
			
			try {
				sb.append("\t"+field.getName()+"["+getModifierString(field.getModifiers())+"]: "+(field.get(obj) != null ? field.get(obj) : "null")+"\n");
			} catch (Exception e) {
				e.printStackTrace();
			} 
				
		}
		
		return sb.toString();
	}
	
	public static String getModifierString(int mod) {
		StringBuffer sb = new StringBuffer();
		
		if(Modifier.isFinal(mod))
			sb.append("FINAL,");
		
		if(Modifier.isStatic(mod))
			sb.append("STATIC,");
		
		if(Modifier.isPrivate(mod))
			sb.append("PRIVATE,");
		
		if(Modifier.isPublic(mod))
			sb.append("PUBLIC,");
		
		if(Modifier.isProtected(mod))
			sb.append("PROTECTED,");
		
		if(Modifier.isSynchronized(mod))
			sb.append("SYNCHRONIZED,");
		
		if(Modifier.isTransient(mod))
			sb.append("TRANSIENT,");
		
		if(Modifier.isVolatile(mod))
			sb.append("VOLATILE,");
		
		if(Modifier.isStrict(mod))
			sb.append("STRICT,");
		
		if(Modifier.isAbstract(mod))
			sb.append("ABSTRACT,");
		
		if(Modifier.isNative(mod))
			sb.append("NATIVE,");
		
		return sb.toString().substring(0, sb.toString().length()-1);
	}
}
