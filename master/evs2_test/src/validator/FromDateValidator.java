package validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import validator.annotations.FromDate;

import model.Occupation;

public class FromDateValidator implements ConstraintValidator<FromDate, Occupation>{

	@Override
	public void initialize(FromDate arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Occupation arg0, ConstraintValidatorContext arg1) {
		return arg0.getFrom().getTime() < arg0.getCompany().getFounded().getTime();
	}


}
