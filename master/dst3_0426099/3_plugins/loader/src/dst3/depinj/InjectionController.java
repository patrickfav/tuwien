package dst3.depinj;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dst3.annotations.Component;
import dst3.annotations.ComponentId;
import dst3.annotations.Inject;
import dst3.annotations.ScopeType;

public class InjectionController implements IInjectionController{
	
	private Map<Class<?>,Object> singeltonMap;
	private Random r = new Random();
	
	private static InjectionController injc;
	
	private InjectionController() {
		singeltonMap = Collections.synchronizedMap(new HashMap<Class<?>,Object>());
	}
	
	public static synchronized InjectionController getInstance(){
		if(injc==null) {
			injc = new InjectionController();
		}
		return injc;
	}
	
	@Override
	public synchronized void initialize(Object obj) throws InjectionException {
		int mode = -1;
		boolean hasId = false;
		Field idField = null;
		Class<?> preferredClass;
		boolean required;
		
		/* check if injection is needed */
		if(obj.getClass().getAnnotation(Component.class) != null) {
			
			mode = ((Component) obj.getClass().getAnnotation(Component.class)).scope();
			/* check for id field */
			for(Field field: obj.getClass().getDeclaredFields()) {
				if(field.getAnnotation(ComponentId.class) != null) {
					idField=field;
					hasId = true;
					
					/*if id of wrong type */
					if(idField.getType() != Long.class && idField.getType() != long.class)
						throw new InjectionException("Id of wrong type "+idField.getType());
					
					break;
				}
			}
			
			/* if no id was found */
			if(!hasId || idField == null)
				throw new InjectionException("No Id field found.");
			
			/* inject fields */
			for(Field field: obj.getClass().getDeclaredFields()) {
				if(field.getAnnotation(Inject.class) != null) {
					
					required = ((Inject) field.getAnnotation(Inject.class)).required();
				
					
					/* get the class type to inject */
					if(((Inject) field.getAnnotation(Inject.class)).specificType() != Object.class) {
						preferredClass = ((Inject) field.getAnnotation(Inject.class)).specificType();
					} else {
						preferredClass = field.getType();
					}
					
					/* make private accessible */
					field.setAccessible(true);
					
					
					try {
						if(mode == ScopeType.PROTOTYPE) { /* if normal mode */
							field.set(obj,initializeObject(preferredClass));
						} else if (mode == ScopeType.SINGELTON) { /* singelton mode */
							field.set(obj,getSingletonInstance(preferredClass));
						} else {
							if(required)
								throw new InjectionException("Wrong mode type");
						}
					} catch (Exception e) {
						/* only throw exception if field is required to be injected */
						if(required)
							throw new InjectionException(e);
					}
				}
			}
			
			/* initialize id */
			try {
				idField.setAccessible(true);
				idField.set(obj, r.nextLong());
			} catch (Exception e) {
					throw new InjectionException(e);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSingletonInstance(Class<T> clazz) throws InjectionException {
		/* if found in singelton map */
		if(!singeltonMap.containsKey(clazz)) {
			try {
				singeltonMap.put(clazz, initializeObject(clazz));
			} catch (Exception e) {
				throw new InjectionException("could not instanciate object for class "+clazz);
			} 
		}
		
		return (T) singeltonMap.get(clazz);
	}
	
	
	private <T> T initializeObject(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		T t = clazz.newInstance();
		/* recursivly inject the new objects fields */
		initialize(t);
		
		return t;
	}

}
