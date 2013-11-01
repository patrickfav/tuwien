package dst1.validator;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import dst1.model.hardware.Cluster;
import dst1.model.hardware.Computer;




public class CPUValidator implements ConstraintValidator<CPUs, Set<Cluster>>{
	
	//private static Logger log = Logger.getLogger(CPUValidator.class);
	
	long max;
	long min;
	
	@Override
	public void initialize(CPUs annotiation) {
		
		max = annotiation.max();
		min = annotiation.min();
	}

	@Override
	public boolean isValid(Set<Cluster> set, ConstraintValidatorContext context) {
		for(Cluster cl:set) {
			for(Computer c:cl.getComputer()) {
				if(!((min <= c.getCpus()) && (c.getCpus() <= max)))
						return false;
			}
		}
		return true;
	}


}
