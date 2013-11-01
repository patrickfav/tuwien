package util;

import entities.formelement.CheckBox;
import entities.formelement.DATATYPE;
import entities.formelement.DatePicker;
import entities.formelement.DropDown;
import entities.formelement.FORMELEMENTTYPE;
import entities.formelement.FPRSScale;
import entities.formelement.FileUpload;
import entities.formelement.FormElement;
import entities.formelement.IFormElementList;
import entities.formelement.List;
import entities.formelement.RadioButtons;
import entities.formelement.TextArea;
import entities.formelement.TextField;
import entities.formelement.VASScale;
import entities.formelement.VancouverScarScale;

public class FormElementFactory implements IFormElementFactory {
	
	public FormElement createFormElement(FORMELEMENTTYPE ft) {
		switch(ft) {
		case CHECKBOX:
			return new CheckBox();
		case DATEPICKER:
			return new DatePicker();
		case DROPDOWN:
			DropDown dd = new DropDown();
			dd.setDataType(DATATYPE.STR);
			dd.getValues().add("");
			return dd;
		case LIST:
			List list = new List();
			list.setDataType(DATATYPE.STR);
			list.getValues().add("");
			return list;
		case RADIOBUTTONS:
			RadioButtons rb = new RadioButtons();
			rb.setDataType(DATATYPE.STR);
			rb.getValues().add("");
			return rb;
		case TEXTFIELD:
			TextField tf = new TextField();
			tf.setDataType(DATATYPE.STR);
			return tf;
		case TEXTAREA:
			TextArea ta = new TextArea();
			ta.setDataType(DATATYPE.LONGSTR);
			return ta;	
		case FPRSSCALE:
			FPRSScale fs = new FPRSScale();
			fs.setSize(6);
			fs.setDataType(DATATYPE.INTEGER);
			return fs;
		case VASSCALE:
			VASScale vas = new VASScale();
			vas.setSize(11);
			vas.setDataType(DATATYPE.INTEGER);
			return vas;
		case VANCOUVERSCARSCALE:
			VancouverScarScale vss = new VancouverScarScale();
			vss.setDataType(DATATYPE.INTEGERSET);
			return vss;
		case FILEUPLOAD:
			FileUpload fu = new FileUpload();
			fu.setDataType(DATATYPE.FILE);
			return fu;
		default:
			throw new IllegalArgumentException("Illegal Formelementtype specified! " + ft.toString());
			
		}
	}

	public FormElement createFormElementWithListValues(FORMELEMENTTYPE ft,
			java.util.List<String> values) {
		FormElement fe = this.createFormElement(ft);
		if(fe instanceof IFormElementList && values != null) {
			((IFormElementList)fe).setValues(values);
		}
		return fe;
	}

}
