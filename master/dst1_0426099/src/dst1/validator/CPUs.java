package dst1.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CPUValidator.class })
public @interface CPUs{
	public long min();
	public long max();
	
	public String message() default "CPU Num Validation Fail";
	public java.lang.Class<?>[] groups() default {};
	public java.lang.Class<? extends Payload>[] payload() default {};
}
