package validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import validator.SkillsValidator;
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { SkillsValidator.class })
public @interface SkillIntersection {
	public String message() default "Intersection of Person.skills with Company.skills is not {}";
	public java.lang.Class<?>[] groups() default {};
	public java.lang.Class<? extends Payload>[] payload() default {};
}
