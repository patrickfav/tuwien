package validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import validator.annotations.ToDate;

import model.Occupation;

public class ToDateValidator implements ConstraintValidator<ToDate, Occupation>{

	@Override
	public void initialize(ToDate arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Occupation arg0, ConstraintValidatorContext arg1) {
		return arg0.getTo().getTime() > arg0.getCompany().getAbandoned().getTime();
	}


}
