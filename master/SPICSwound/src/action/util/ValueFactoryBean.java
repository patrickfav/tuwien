package util;

import javax.ejb.Stateless;

import entities.formelement.DATATYPE;
import entities.value.BooleanValue;
import entities.value.DateValue;
import entities.value.DecimalValue;
import entities.value.FileValueSet;
import entities.value.IntegerValue;
import entities.value.IntegerValueSet;
import entities.value.LongstringValue;
import entities.value.StringValue;
import entities.value.Value;

@Stateless
public class ValueFactoryBean implements ValueFactory {
	
	private static final long serialVersionUID = 1L;

	public Value getValueObject(DATATYPE type) {
		
		switch(type) {
		case STR:
			return new StringValue();
		case LONGSTR:
			return new LongstringValue();
		case INTEGER:
			return new IntegerValue();
		case INTEGERSET:
			return new IntegerValueSet();
		case DATE:
			return new DateValue();
		case BOOLEAN:
			return new BooleanValue();
		case DECIMAL:
			return new DecimalValue();
		case FILE:
			return new FileValueSet();
		default:
			return null;
		}
	}

}
