package indexing.entities;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public class IndexedService {
	
	private String name;
	private String urlName;
	private String contentType;
	
	private boolean requiresEncryptionExternal;
	private boolean requiresEncryptionInternal;
	
	private ClassReference classReference;
	
	private Set<MethodReference> createInterceptors;
	private Set<MethodReference> deleteInterceptors;
	private Set<MethodReference> readingInterceptors;
	private Set<MethodReference> updateInterceptors;
	private Set<MethodReference> searchInterceptors;
	
	private Set<IndexedSearchField> searchFields;
	
	public IndexedService() {
		createInterceptors = new HashSet<MethodReference>();
		deleteInterceptors = new HashSet<MethodReference>();
		readingInterceptors = new HashSet<MethodReference>();
		updateInterceptors = new HashSet<MethodReference>();
		searchInterceptors = new HashSet<MethodReference>();
		
		searchFields = new HashSet<IndexedSearchField>();
		
		requiresEncryptionExternal = false;
		requiresEncryptionInternal = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String pluralName) {
		this.urlName = pluralName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Set<MethodReference> getCreateInterceptors() {
		return createInterceptors;
	}

	public void setCreateInterceptors(Set<MethodReference> createInterceptors) {
		this.createInterceptors = createInterceptors;
	}

	public Set<MethodReference> getDeleteInterceptors() {
		return deleteInterceptors;
	}

	public void setDeleteInterceptors(Set<MethodReference> deleteInterceptors) {
		this.deleteInterceptors = deleteInterceptors;
	}

	public Set<MethodReference> getReadingInterceptors() {
		return readingInterceptors;
	}

	public void setReadingInterceptors(Set<MethodReference> readingInterceptors) {
		this.readingInterceptors = readingInterceptors;
	}

	public Set<MethodReference> getUpdateInterceptors() {
		return updateInterceptors;
	}

	public void setUpdateInterceptors(Set<MethodReference> updateInterceptors) {
		this.updateInterceptors = updateInterceptors;
	}
	
	public Set<MethodReference> getSearchInterceptors() {
		return searchInterceptors;
	}

	public void setSearchInterceptors(Set<MethodReference> searchInterceptors) {
		this.searchInterceptors = searchInterceptors;
	}

	public void setClassReference(ClassReference classReference) {
		this.classReference = classReference;
	}

	public ClassReference getClassReference() {
		return classReference;
	}
	
	public void setSearchFields(Set<IndexedSearchField> searchFields) {
		this.searchFields = searchFields;
	}

	public Set<IndexedSearchField> getSearchFields() {
		return searchFields;
	}
	
	
	public void callAllReadInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,readingInterceptors);
	}
	public void callAllCreateInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,createInterceptors);
	}
	public void callAllUpdateInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		callAllMethods(param,updateInterceptors);
	}
	public void callAllDeleteInterceptors(Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
	
	/**
	 * Returns true if the given string is a valid search field
	 * @param fieldName
	 * @return
	 */
	public boolean checkSearchField(String fieldName) {
		for(IndexedSearchField isf: searchFields) {
			if(isf.getFieldName().equals(fieldName))
				return true;
		}
		
		return false;
	}

	public void setRequiresEncryptionExternal(boolean requiresEncryptionExternal) {
		this.requiresEncryptionExternal = requiresEncryptionExternal;
	}

	public boolean isRequiresEncryptionExternal() {
		return requiresEncryptionExternal;
	}

	public void setRequiresEncryptionInternal(boolean requiresEncryptionInternal) {
		this.requiresEncryptionInternal = requiresEncryptionInternal;
	}

	public boolean isRequiresEncryptionInternal() {
		return requiresEncryptionInternal;
	}

	
	
	
	
	
	
	
	
}
