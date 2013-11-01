package entities;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.seam.annotations.Name;
import org.richfaces.model.UploadItem;

import util.FileUtils;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name="TRIAL_ATTACHMENT")
@Inheritance(strategy=InheritanceType.JOINED)
@Name("attachment")
public class TrialAttachment implements Serializable {
	
	private static final long serialVersionUID = 234L;
	
	@Id @GeneratedValue
	@Column(name="TRIAL_ATTACHMENT_ID")
    private Long id;
    
	@XmlTransient
    @Basic(fetch=FetchType.LAZY) 
    @Lob
    @Column(name="DATA")
	private byte[] data;
	
    @Column(name="CONTENT_TYPE")
	private String contentType;
	
    @Column(name="FILENAME",nullable=true)
	private String fileName;
    
    @Column(name="FILESIZE")
    private Long fileSize;

	@XmlTransient
	@ManyToOne(optional=true)
	@JoinColumn(name="TRIAL_ID",nullable=true)
	private Trial trial;
	
	@XmlTransient
	@ManyToOne(optional=true)
    @JoinColumn(name="FILESET_ID",nullable=true)
    private FileSet fileset;
    
    // not persisted!
	@Transient
    private transient List<UploadItem> uploads;
    
    public TrialAttachment() {
    	uploads = new LinkedList<UploadItem>();
    }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Trial getTrial() {
		return trial;
	}
	
	public void setTrial(Trial trial) {
		this.trial = trial;
	}
	
	public FileSet getFileSet() {
		return fileset;
	}

	public void setFileSet(FileSet fileSet) {
		this.fileset = fileSet;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long filesize) {
		this.fileSize = filesize;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TrialAttachment other = (TrialAttachment) obj;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (!Arrays.equals(data, other.data))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (fileSize == null) {
			if (other.fileSize != null)
				return false;
		} else if (!fileSize.equals(other.fileSize))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "File: " + fileName;
	}

	public void loadUploadItem(UploadItem ui) throws IOException {
		if(ui.getFile() != null) {

			this.data = FileUtils.instance().getBytesFromFile(ui.getFile());
			this.fileName = ui.getFileName();
			this.fileSize = ui.getFile().length();
			this.contentType = FileUtils.instance().getContentType(ui.getFile());
		} else {
			// handle in memory files (should be the same as above with ui.getData and ui.getData().length
			throw new RuntimeException("Not jet implemented - upload has to use tempFiles");
		}
	}
	
	public List<UploadItem> getUploads() {
		return uploads;
	}

	public void setUploads(List<UploadItem> uploads) {
		this.uploads = uploads;
	}
	
}
