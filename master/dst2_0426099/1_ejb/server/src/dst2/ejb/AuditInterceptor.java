package dst2.ejb;

import java.util.Date;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst2.model.AuditRecord;

public class AuditInterceptor {
	
	@PersistenceContext
	private EntityManager em;
	
	@AroundInvoke
	public Object logCall(InvocationContext context) throws Exception {
		/* hello */
		AuditRecord ar = new AuditRecord();
		ar.setDate(new Date());
		ar.setMethodName(context.getMethod().getName());
		
		StringBuffer sb = new StringBuffer();
		if(context.getParameters() != null) {
			for(Object param: context.getParameters()) {
				sb.append(param.getClass().getSimpleName()+":"+param.toString()+",");
			}
			ar.setParams(sb.toString());
		}
			
		
		Object o = null;
		try{
			o =context.proceed();
			if(o != null)
				ar.setResult(o.toString());
			
		} catch(Exception e) {
			ar.setException(e.toString());
			
		}
		
		em.persist(ar);
		
		return o;
	}
	
	
}
