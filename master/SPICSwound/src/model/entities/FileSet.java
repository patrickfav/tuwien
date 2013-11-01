package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import entities.value.FileValueSet;

@Entity
public class FileSet implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Id
	private Long id;
	
	@OneToMany(mappedBy="fileset", cascade=CascadeType.ALL)
	private List<TrialAttachment> files;
	
	@OneToOne(mappedBy="fileset", cascade=CascadeType.ALL)
	private FileValueSet value;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<TrialAttachment> getFiles() {
		if(files == null)
			files = new LinkedList<TrialAttachment>();
		return files;
	}

	public void setFiles(List<TrialAttachment> files) {
		this.files = files;
	}
	
	public FileValueSet getValue() {
		return value;
	}

	public void setValue(FileValueSet value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FileSet) {
			FileSet sec = (FileSet) o;
			
			return(this.getFiles().equals(sec.getFiles()));
			
		}
		return false;
	}
	
	public boolean isEmpty() {
		return getFiles().isEmpty();
	}
	
	public static boolean isEmptyFileset(Object o) {
		if (o instanceof FileSet) {
			return ((FileSet) o).isEmpty();
		}
		return false;
	}

}
