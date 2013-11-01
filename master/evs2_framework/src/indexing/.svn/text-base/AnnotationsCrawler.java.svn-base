package indexing;

import indexing.entities.ClassReference;
import indexing.entities.IndexedSearchField;
import indexing.entities.IndexedService;
import indexing.entities.MethodReference;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ConvertersScanner;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import constants.SearchFieldType;

import exceptions.UnsupportedInterceptorException;

import annotations.RestSearchable;
import annotations.RestService;
import annotations.interceptors.CreateInterceptor;
import annotations.interceptors.DeleteInterceptor;
import annotations.interceptors.InterceptorForService;
import annotations.interceptors.ReadInterceptor;
import annotations.interceptors.SearchInterceptor;
import annotations.interceptors.UpdateInterceptor;


public class AnnotationsCrawler {
	private static Logger log = Logger.getLogger(AnnotationsCrawler.class);
	
	private Reflections reflections;
	private Set<Class<?>> services;
	private IndexStorage indexStorage;
	
	public AnnotationsCrawler() {
		
        reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(
                        new SubTypesScanner(),
                        new TypeAnnotationsScanner(),
                        new FieldAnnotationsScanner(),
                        new MethodAnnotationsScanner(),
                        new ConvertersScanner())
                .setUrls(getCleanURLsToScan()));
        
        log.debug("Start reflection Framework.");
		  
	}
	
	public void indexClasspath() {
		indexServices();
		indexReadInterceptors();
		indexCreateInterceptors();
		indexUpdateInterceptors();
		indexDeleteInterceptors();
		indexSearchInterceptors();
		indexSearchFields();
	}
	
	private void indexServices() {
		log.debug("Indexing Services through scanning annotations");
		indexStorage = IndexStorage.getInstance();
		services = reflections.getTypesAnnotatedWith(RestService.class);
		
		
		RestService restServiceAnnotation;
		IndexedService s;
		for(Class<?> c:services) {
			s = new IndexedService();
			restServiceAnnotation = (RestService) c.getAnnotation(RestService.class);
			
			if(restServiceAnnotation.name().equals("")) {
				s.setName(c.getSimpleName());
				s.setUrlName(c.getSimpleName().toLowerCase()+"s");
			} else {
				s.setName(restServiceAnnotation.name());
				s.setUrlName(restServiceAnnotation.name().toLowerCase());
			}
			
			s.setContentType(restServiceAnnotation.contentType());
			
			s.setClassReference(new ClassReference(c));
			
			s.setRequiresEncryptionExternal(restServiceAnnotation.externalEncryption());
			s.setRequiresEncryptionInternal(restServiceAnnotation.internalEncryption());
			
			indexStorage.putToMap(s.getUrlName(), s);
		}
	}
	
	private void indexCreateInterceptors() {
		log.info("Indexing create interceptors");
		
		indexStorage = IndexStorage.getInstance();
		String name;
		Boolean entityInterceptor;
		
		Set<Method> interceptors = reflections.getMethodsAnnotatedWith(CreateInterceptor.class);
		
		for(Method m:interceptors) {
			
			try {
				checkInterceptorMethod(m);
			} catch(UnsupportedInterceptorException e) {
				continue;
			}
			
			Annotation[] annotations = m.getAnnotations();
			entityInterceptor = false;
			for(Annotation an:annotations) {
				if(an instanceof InterceptorForService) {
					entityInterceptor = true;
					name = ((InterceptorForService) an).value();
					if(indexStorage.getServiceMap().containsKey(name)) {
						indexStorage.getServiceMap().get(name).getCreateInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
						log.debug("Adding create-interceptor to "+name);
					} else {
						log.warn("Entity "+name+" for Interceptor could not be found. Ignoring.");
						//throw new UnsupportedInterceptorException("Entity "+name+" for Interceptor could not be found");
					}
					break;
				}
			}
			
			if(!entityInterceptor) {
				indexStorage.getCreateInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
				log.debug("Adding global create-interceptor");
			}
		}
	}
	
	private void indexReadInterceptors() {
		log.info("Indexing read interceptors");
		
		indexStorage = IndexStorage.getInstance();
		String name;
		Boolean entityInterceptor;
		
		/* READ INTERCEPTORS */
		Set<Method> rInterceptors = reflections.getMethodsAnnotatedWith(ReadInterceptor.class);
		
		for(Method m:rInterceptors) {
			
			try {
				checkInterceptorMethod(m);
			} catch(UnsupportedInterceptorException e) {
				continue;
			}
			
			Annotation[] annotations = m.getAnnotations();
			entityInterceptor = false;
			for(Annotation an:annotations) {
				if(an instanceof InterceptorForService) {
					entityInterceptor = true;
					name = ((InterceptorForService) an).value();
					if(indexStorage.getServiceMap().containsKey(name)) {
						indexStorage.getServiceMap().get(name).getReadingInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
						log.debug("Adding read-interceptor to "+name);
					} else {
						log.warn("Entity "+name+" for Interceptor could not be found. Ignoring.");
						//throw new UnsupportedInterceptorException("Entity "+name+" for Interceptor could not be found");
					}
					break;
				}
			}
			
			if(!entityInterceptor) {
				indexStorage.getReadingInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
				log.debug("Adding global read-interceptor");
			}
		}
	}
	
	private void indexUpdateInterceptors() {
		log.info("Indexing update interceptors");
		
		indexStorage = IndexStorage.getInstance();
		String name;
		Boolean entityInterceptor;
		
		Set<Method> interceptors = reflections.getMethodsAnnotatedWith(UpdateInterceptor.class);
		
		for(Method m:interceptors) {
			
			try {
				checkInterceptorMethod(m);
			} catch(UnsupportedInterceptorException e) {
				continue;
			}
			
			Annotation[] annotations = m.getAnnotations();
			entityInterceptor = false;
			for(Annotation an:annotations) {
				if(an instanceof InterceptorForService) {
					entityInterceptor = true;
					name = ((InterceptorForService) an).value();
					if(indexStorage.getServiceMap().containsKey(name)) {
						indexStorage.getServiceMap().get(name).getUpdateInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
						log.debug("Adding update-interceptor to "+name);
					} else {
						log.warn("Entity "+name+" for Interceptor could not be found. Ignoring.");
						//throw new UnsupportedInterceptorException("Entity "+name+" for Interceptor could not be found");
					}
					break;
				}
			}
			
			if(!entityInterceptor) {
				indexStorage.getUpdateInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
				log.debug("Adding global update-interceptor");
			}
		}
	}
	
	private void indexDeleteInterceptors() {
		log.info("Indexing delete interceptors");
		
		indexStorage = IndexStorage.getInstance();
		String name;
		Boolean entityInterceptor;
		
		Set<Method> interceptors = reflections.getMethodsAnnotatedWith(DeleteInterceptor.class);
		
		for(Method m:interceptors) {
			
			try {
				checkInterceptorMethod(m);
			} catch(UnsupportedInterceptorException e) {
				continue;
			}
			
			Annotation[] annotations = m.getAnnotations();
			entityInterceptor = false;
			for(Annotation an:annotations) {
				if(an instanceof InterceptorForService) {
					entityInterceptor = true;
					name = ((InterceptorForService) an).value();
					if(indexStorage.getServiceMap().containsKey(name)) {
						indexStorage.getServiceMap().get(name).getDeleteInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
						log.debug("Adding delete-interceptor to "+name);
					} else {
						log.warn("Entity "+name+" for Interceptor could not be found. Ignoring.");
						//throw new UnsupportedInterceptorException("Entity "+name+" for Interceptor could not be found");
					}
					break;
				}
			}
			
			if(!entityInterceptor) {
				indexStorage.getDeleteInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
				log.debug("Adding global delete-interceptor");
			}
		}
	}
	
	private void indexSearchInterceptors() {
		log.info("Indexing search interceptors");
		
		indexStorage = IndexStorage.getInstance();
		String name;
		Boolean entityInterceptor;
		
		Set<Method> interceptors = reflections.getMethodsAnnotatedWith(SearchInterceptor.class);
		
		for(Method m:interceptors) {
			
			try {
				checkInterceptorMethod(m);
			} catch(UnsupportedInterceptorException e) {
				continue;
			}
			
			Annotation[] annotations = m.getAnnotations();
			entityInterceptor = false;
			for(Annotation an:annotations) {
				if(an instanceof InterceptorForService) {
					entityInterceptor = true;
					name = ((InterceptorForService) an).value();
					if(indexStorage.getServiceMap().containsKey(name)) {
						indexStorage.getServiceMap().get(name).getSearchInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
						log.debug("Adding search-interceptor to "+name);
					} else {
						log.warn("Entity "+name+" for Interceptor could not be found. Ignoring.");
						//throw new UnsupportedInterceptorException("Entity "+name+" for Interceptor could not be found");
					}
					break;
				}
			}
			
			if(!entityInterceptor) {
				indexStorage.getSearchInterceptors().add(new MethodReference(new ClassReference(m.getDeclaringClass()), m));
				log.debug("Adding global search-interceptor");
			}
		}
	}
	
	public void indexSearchFields() {
		log.info("Indexing searchfields");
		
		indexStorage = IndexStorage.getInstance();
		
		for(IndexedService is:indexStorage.getServiceMap().values()) {
			for(Field field: is.getClassReference().getClazz().getDeclaredFields()) {
				for(Annotation an:field.getAnnotations()) {
					if(an instanceof RestSearchable) {
						if(field.getType() == String.class) {
							is.getSearchFields().add(new IndexedSearchField(SearchFieldType.STRING_TYPE, field.getName()));
							log.debug("Indexing searchfield "+field.getName()+" at "+is.getClassReference().getClazz().getSimpleName());
						} else {
							log.warn("Searchfield "+field.getName()+" at "+is.getClassReference().getClazz().getSimpleName()+" has invalid type. Ignoring.");
							//throw new UnsupportedSearchFieldTypeException("Type for searchfield is not valid. ATM only String valid.");
						} 
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Checks the integrity of the interceptor method:
	 * Must return void and must have 1 param.
	 * @param m
	 * @throws UnsupportedInterceptorException 
	 */
	private void checkInterceptorMethod(Method m) throws UnsupportedInterceptorException {
		if(m.getGenericReturnType() != void.class) {
			log.warn("Interceptor Method "+m.getName() +" has the wrong return type. Must be void.");
			throw new UnsupportedInterceptorException("Interceptor Method "+m.getName() +" has the wrong return type. Must be void.");
		}
		
		Type[] types = m.getGenericParameterTypes();
		if(types.length != 1) {
			log.warn("Interceptor Method "+m.getName() +" has not the correct paramter count. Must have exactly one parameter.");
			throw new UnsupportedInterceptorException("Interceptor Method "+m.getName() +" has not the correct paramter count. Must have exactly one parameter.");
		}
	}
	
	/**
	 * This is a workaround for issue 36 in the google reflections code
	 * http://code.google.com/p/reflections/issues/detail?id=36
	 * @return
	 */
	private Set<URL> getCleanURLsToScan() {
		Set<URL> urls = ClasspathHelper.getUrlsForPackagePrefix("");
		Set<URL> fixedUrls = new HashSet<URL>(urls.size());
		for (URL u : urls) {
			try {
				fixedUrls.add(new URL("file", null, u.getPath().replace("%20", " ").replace("%c3%bc", "ü")));
				
			} catch (MalformedURLException e) {
				continue;
			}
		}
		
		return fixedUrls;
	}
}
