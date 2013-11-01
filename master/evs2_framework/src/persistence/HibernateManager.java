package persistence;

import indexing.entities.IndexedService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javassist.Modifier;

import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Transient;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import annotations.RestSearchable;


import exceptions.DatabaseException;
import exceptions.DeleteException;
import exceptions.UnsupportedSearchFieldException;
import exceptions.UpdateException;

public class HibernateManager {
	private static Logger log = Logger.getLogger(HibernateManager.class);
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	private EntityManager em;
	private Validator validator;
	
	private static HibernateManager instance;
	
	private HibernateManager() {
		log.info("Starting Database");
		emf = Persistence.createEntityManagerFactory( "restframework" );  
		em = emf.createEntityManager();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}
	
	public synchronized static HibernateManager getInstance() {
        if (instance == null) {
            instance = new HibernateManager();
        }
        return instance;
    }

	public EntityManager getEm() {
		return em;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}
	
	/* *************************************************** CRUD METODS */
	
	public synchronized <T> T find(long id,Class<T> clazz) throws DatabaseException {
		T o = null;
		
		try {
			em.getTransaction().begin();
			o = em.find(clazz, id);
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				log.warn("Hiberante Error (find) "+e1.getMessage());
				throw new DatabaseException("Error while find",e1);
			}
			log.warn("Hiberante Error (find) "+e.getMessage());
			throw new DatabaseException("Error while find",e);
		}
		
		if(o != null) {
			log.info("Entity "+o.getClass().getSimpleName()+" found through id "+id);
		} else {
			log.info("Enitiy from type "+clazz.getSimpleName()+" with id "+id+" could not be found.");
		}
		
		return o;
	}
	
	public synchronized void persist(Object o) throws DatabaseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		try {
			em.getTransaction().begin();
			em.persist(o);
			em.getTransaction().commit();
		} catch (Exception e) {
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e.printStackTrace();
				log.warn("Hiberante Error (persist) "+e1.getMessage());
				throw new DatabaseException("Error while persist",e1);
			}
			e.printStackTrace();
			log.warn("Hiberante Error (persist) "+e.getMessage());
			throw new DatabaseException("Error while persist",e);
			
		}
		
		if(o != null)
			log.info("Entity "+o.getClass().getSimpleName()+" saved with id "+getIdFromEntity(o));
	}
	
	public synchronized Object merge(long id,Object o) throws DatabaseException, UpdateException {
		Object out = null;
		try {
			em.getTransaction().begin();
			Object ref = em.find(o.getClass(), id);
			
			if(ref == null) {
				log.warn("Could not find entity with id "+id+" for update.");
				throw new UpdateException("Could not find entity with id "+id+" for update.");
			}
			
			out = em.merge(mergeObjects(ref,o));
			
			em.getTransaction().commit();
		} catch (Exception e) {
			if(e instanceof UpdateException)
				throw new UpdateException("",e);
			
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e.printStackTrace();
				log.warn("Hiberante Error (merge) "+e1.getMessage());
				throw new DatabaseException("Error while merge",e1);
			}
			e.printStackTrace();
			log.warn("Hiberante Error (merge) "+e.getMessage());
			throw new DatabaseException("Error while merge",e);
			
		}
		
		if(o != null)
			log.info("Entity "+o.getClass().getSimpleName()+" with id "+id+" updated.");
		
		return out;
	}	
	
	public synchronized <T> T remove(long id, Class<T> entityClass) throws DatabaseException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, DeleteException {
		T ref = null;
		
		try {
			em.getTransaction().begin();
			ref = em.find(entityClass, id);
			
			if(ref == null) {
				log.warn("Could not find entity with id "+id+" for to remove.");
				throw new DeleteException("Could not find entity with id "+id+" for update.");
			}
			em.remove(ref);
			em.getTransaction().commit();
		} catch (Exception e) {
			
			if(e instanceof DeleteException)
				throw new DeleteException("",e);
			
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e.printStackTrace();
				log.warn("Hiberante Error (remove) "+e1.getMessage());
				throw new DatabaseException("Error while remove",e1);
			}
			e.printStackTrace();
			log.warn("Hiberante Error (remove) "+e.getMessage());
			throw new DatabaseException("Error while remove",e);
			
		}
		
		if(entityClass != null)
			log.info("Entity "+entityClass.getSimpleName()+" with id "+id+" removed");
		
		return ref;
	}
	
	/**
	 * Searches for all of the not null and valid given parameters in the object
	 * 
	 * @param searchObj
	 * @param is
	 * @return
	 * @throws DatabaseException
	 * @throws UnsupportedSearchFieldException
	 */
	@SuppressWarnings("unchecked")
	public synchronized List<Object> search(Object searchObj,IndexedService is) throws DatabaseException, UnsupportedSearchFieldException {
		Session session;
		Criteria search;
		List<Object> resultList;
		
		try {
			em.getTransaction().begin();
			
			session = (Session) em.getDelegate();
			search = session.createCriteria(searchObj.getClass());
			
			for(Field f:getAllNotNullWriteableFields(searchObj)) {
				if(!is.checkSearchField(f.getName()))
					throw new UnsupportedSearchFieldException("The following searchfield is not an annotated valid searchfields: "+f.getName());
				
				f.setAccessible(true);
				search.add(Restrictions.ilike(f.getName(),"%"+f.get(searchObj)+"%"));
				
			}
			
			resultList = search.list();

			em.getTransaction().commit();
		} catch (Exception e) {
			if(e instanceof UnsupportedSearchFieldException)
				throw new UnsupportedSearchFieldException("",e);
			
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e.printStackTrace();
				log.warn("Hiberante Error (search) "+e1.getMessage());
				throw new DatabaseException("Error while search",e1);
			}
			e.printStackTrace();
			log.warn("Hiberante Error (search) "+e.getMessage());
			throw new DatabaseException("Error while search",e);
			
		} finally {
			session = null;
			search = null;
		}
		
		if(searchObj != null)
			log.info("Search with Entity "+searchObj.getClass().getSimpleName()+" resulted in "+resultList.size()+" hit(s).");
		
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> simlpeSearch(IndexedService is,String keyword) throws UnsupportedSearchFieldException, DatabaseException {
		Session session;
		Criteria search;
		List<Object> resultList = new ArrayList<Object>();
		
		try {
			em.getTransaction().begin();
			
			session = (Session) em.getDelegate();
			search = session.createCriteria(is.getClassReference().getClazz());
			
			for(Field f:is.getClassReference().getClazz().getDeclaredFields()) {
				if(is.checkSearchField(f.getName())) {
					//log.info("found search field: "+f.getName());
					session = (Session) em.getDelegate();
					search = session.createCriteria(is.getClassReference().getClazz());
					search.add(Restrictions.ilike(f.getName(),"%"+keyword+"%"));
					resultList.addAll(search.list());
				}
				
			}
			

			em.getTransaction().commit();
		} catch (Exception e) {
			if(e instanceof UnsupportedSearchFieldException)
				throw new UnsupportedSearchFieldException("",e);
			
			try {
				em.getTransaction().rollback();
			} catch (Exception e1) {
				e.printStackTrace();
				log.warn("Hiberante Error (search) "+e1.getMessage());
				throw new DatabaseException("Error while search",e1);
			}
			e.printStackTrace();
			log.warn("Hiberante Error (search) "+e.getMessage());
			throw new DatabaseException("Error while search",e);
			
		} finally {
			session = null;
			search = null;
		}
		
		log.info("SimpleSearch with Entity person resulted in "+resultList.size()+" hit(s).");
		
		return resultList;
	}
	
	public synchronized <T> Set<ConstraintViolation<T>> validateEntity(T obj) {
		
		log.info("Validating "+obj.getClass().getSimpleName());
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		return  validator.validate(obj);
	}
	
	
	/**
	 * Check if only valid searchfields are used. Returns a list of invalid fields.
	 * 
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public List<String> validateObjectForSearchFields(Object obj) throws IllegalArgumentException, IllegalAccessException {
		List<String> invalidFields = new ArrayList<String>();
		boolean invalidField;
		
		for(Field field: obj.getClass().getDeclaredFields()) {
			/* ignore final and static (and transient) fields */
			if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			
			invalidField = true;
			
			/* if field is not null it should be searchable */
			if(field.get(obj) != null) {
				for(Annotation an:field.getAnnotations()) {
					if(an instanceof RestSearchable) {
						invalidField = false;
						break;
					}
				}
			}
			
			if(invalidField)
				invalidFields.add(field.getName());
		}
		
		return invalidFields;
	}
	
	/* *********************************************************************************** PRIVATES */
	
	/**
	 * Gets the id from the entity if the id getter method name is getId();
	 * @param o
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private synchronized Long getIdFromEntity(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		for(Method m:o.getClass().getMethods()) {
			if(m.getName().equals("getId")) {
				return (Long) m.invoke(o,new Object[]{});
			}
		}
		return null;
	}
	
	/**
	 * Merges a partial filled object with newer data 
	 * with an filled object with older data.
	 * @param <T>
	 * @param ref
	 * @param partialNew
	 * @return merged updated version
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private <T> T mergeObjects(T ref, T partialNew) throws IllegalArgumentException, IllegalAccessException {

		boolean ignoreField;
		for(Field field: partialNew.getClass().getDeclaredFields()) {
			ignoreField = false;
			
			/* ignore fields with id or transient annotation */
			for(Annotation an:field.getAnnotations()) {
				if(an instanceof Id || an instanceof Transient || an instanceof EmbeddedId) {
					ignoreField = true;
					break;
				}
			}
			
			/* ignore final and static (and transient) fields */
			if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
				ignoreField = true;
			}
			
			if(!ignoreField) {
				//log.debug("Reflection merge: Updating "+field.getName());
				field.setAccessible(true);
				if(field.get(partialNew) != null) {
					field.set(ref, field.get(partialNew));
				}
			}
		}
		
		return ref;
	}
	
	private List<Field> getAllNotNullWriteableFields(Object obj) throws IllegalArgumentException, IllegalAccessException {
		List<Field> fields = new ArrayList<Field>();
		
		for(Field field: obj.getClass().getDeclaredFields()) {
			/* ignore final and static (and transient) fields */
			if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			/* check if field is null */
			field.setAccessible(true);
			if(field.get(obj) != null) {
				fields.add(field);
			}
		}
		
		return fields;
	}
	
	private Object getFilledObjectInstance(Class<?> c) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		List<Field> fields = new ArrayList<Field>();
		Object obj = c.newInstance();
		
		for(Field field: obj.getClass().getDeclaredFields()) {
			/* ignore final and static (and transient) fields */
			if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			/* check if field is null */
			field.setAccessible(true);
			if(field.get(obj) != null) {
				fields.add(field);
			}
		}
		
		return fields;
	}
	




















}
