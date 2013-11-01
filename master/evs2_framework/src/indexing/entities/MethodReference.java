package indexing.entities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodReference {
	private ClassReference clazz;
	private Method method;

	public MethodReference(ClassReference clazz, Method method) {
		super();
		this.clazz = clazz;
		this.method = method;
	}

	public ClassReference getClassReference() {
		return clazz;
	}

	public void setClassReference(ClassReference clazz) {
		this.clazz = clazz;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public void callVoid(Object calledBy,Object... param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		method.invoke(calledBy,param);
	}
}
