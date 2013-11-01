package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import validator.ToDateValidator;
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ToDateValidator.class })
public @interface ToDate {
	
	public String message() default "Occupation.to <= Company.abandonded";
	public java.lang.Class<?>[] groups() default {};
	public java.lang.Class<? extends Payload>[] payload() default {};
}
