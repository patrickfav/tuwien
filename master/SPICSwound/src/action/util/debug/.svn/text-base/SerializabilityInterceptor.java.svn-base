package util.debug;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.seam.annotations.intercept.Interceptor;

@Interceptor(stateless=true)
public class SerializabilityInterceptor {

	@AroundInvoke
	public Object checkSerializability(InvocationContext invocation) throws Exception {
		
		
		// check if target object is serializable
		ByteArrayOutputStream ostr = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		oos = new ObjectOutputStream(ostr);
		oos.writeObject(invocation.getTarget());	
		ostr.toByteArray();
			
		return invocation.proceed();
	}
}
