package indexing;

import indexing.entities.IndexedService;
import indexing.entities.MethodReference;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


public class IndexStorage {
	private static Logger log = Logger.getLogger(IndexStorage.class);
	
	public Map<String,IndexedService> serviceMap;
	private Set<MethodReference> createInterceptors;
	private Set<MethodReference> deleteInterceptors;
	private Set<MethodReference> readingInterceptors;
	private Set<MethodReference> updateInterceptors;
	private Set<MethodReference> searchInterceptors;
	
	public static IndexStorage instance;
	
	private IndexStorage() {
		serviceMap = Collections.synchronizedMap(new HashMap<String,IndexedService>());
		createInterceptors = Collections.synchronizedSet(new HashSet<MethodReference>());
		deleteInterceptors =  Collections.synchronizedSet(new HashSet<MethodReference>());
		readingInterceptors =  Collections.synchronizedSet(new HashSet<MethodReference>());
		updateInterceptors =  Collections.synchronizedSet(new HashSet<MethodReference>());
		searchInterceptors =  Collections.synchronizedSet(new HashSet<MethodReference>());
	}
	
	public synchronized static IndexStorage getInstance() {
        if (instance == null) {
            instance = new IndexStorage();
        }
        return instance;
    }

	public synchronized Map<String, IndexedService> getServiceMap() {
		return serviceMap;
	}

	public synchronized void  putToMap(String s, IndexedService is) {
		log.debug("Adding service '"+s+"' to Index Storage (enc:"+is.isRequiresEncryptionExternal()+","+is.isRequiresEncryptionInternal()+")");
		serviceMap.put(s, is);
	}
	
	public synchronized IndexedService searchMap(Class<?> clazz) {
		for(IndexedService is: serviceMap.values()) {
			if(is.getClassReference().getClazz().equals(clazz))
				return is;
		}
		
		return null;
	}
	
	public synchronized Set<MethodReference> getCreateInterceptors() {
		return createInterceptors;
	}

	public synchronized Set<MethodReference> getDeleteInterceptors() {
		return deleteInterceptors;
	}

	public synchronized Set<MethodReference> getReadingInterceptors() {
		return readingInterceptors;
	}

	public synchronized Set<MethodReference> getUpdateInterceptors() {
		return updateInterceptors;
	}
	
	public synchronized Set<MethodReference> getSearchInterceptors() {
		return searchInterceptors;
	}
	
	public synchronized void callAllReadInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,readingInterceptors);
	}
	public synchronized void callAllCreateInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,createInterceptors);
	}
	public synchronized void callAllUpdateInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,updateInterceptors);
	}
	public synchronized void callAllDeleteInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,deleteInterceptors);
	}
	public synchronized void callAllSearchInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,searchInterceptors);
	}

	
	private void callAllMethods(Object param, Set<MethodReference> methods) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Object calledBy;
		
		for(MethodReference m:methods) {
			calledBy = m.getClassReference().getClazz().newInstance();
			
			m.callVoid(calledBy, param);
		}
	}
}
