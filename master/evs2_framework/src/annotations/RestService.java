package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value= {ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface RestService {
	String value() default "";
	String name() default "";
	String contentType();
	boolean internalEncryption() default false;
	boolean externalEncryption() default false;
}
