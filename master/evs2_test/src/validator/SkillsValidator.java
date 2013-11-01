package validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import validator.annotations.SkillIntersection;

import model.Occupation;
import model.Skill;

public class SkillsValidator implements ConstraintValidator<SkillIntersection, Occupation>{

	@Override
	public void initialize(SkillIntersection arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Occupation arg0, ConstraintValidatorContext arg1) {
		/*for(Skill s:arg0.getPerson().getSkills()) {
			if(arg0.getCompany().getSkills().contains(s))
				return true;
		}*/
		
		return true;
	}


}
