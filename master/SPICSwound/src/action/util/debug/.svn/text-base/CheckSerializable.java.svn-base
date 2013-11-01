package util.debug;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.Interceptors;


@Target(value=ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Interceptors(SerializabilityInterceptor.class)
public @interface CheckSerializable { }
