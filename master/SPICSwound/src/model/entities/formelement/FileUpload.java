package entities.formelement;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue(value="FILEUPLOAD")
public class FileUpload extends FormElement {
	
	private static final DATATYPE[] VALID_DATATYPES = {DATATYPE.FILE};
	
	@Column(name="MIMETYPES")
	private String mimetypes;
	
	public FileUpload() {
		super();
		this.setType(FORMELEMENTTYPE.FILEUPLOAD);
		this.setDataType(DATATYPE.FILE);
	}

	public String getMimetypes() {
		return mimetypes;
	}

	public void setMimetypes(String mimetypes) {
		this.mimetypes = mimetypes;
	}
	
	public FileUpload duplicate() {
		FileUpload dup = new FileUpload();
		dup.setConstraint(this.getConstraint() == null ? null : this.getConstraint().duplicate());
		dup.setMimetypes(this.getMimetypes());
		return dup;
	}
	
	@Override
	public DATATYPE[] getValidDatatypes() {
		return VALID_DATATYPES;
	}
	
	@Override
	public boolean hasUnit() {
		return false;
	}

}
