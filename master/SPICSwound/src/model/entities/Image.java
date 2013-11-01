package entities;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.richfaces.model.UploadItem;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Image extends TrialAttachment {

	private static final long serialVersionUID = 1L;
	
	@Column
	private Integer height;
	
	@Column
	private Integer width;

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Override
	public void loadUploadItem(UploadItem ui) throws IOException {
		super.loadUploadItem(ui);
		BufferedImage bi = ImageIO.read(ui.getFile());
		this.setHeight(bi.getHeight());
		this.setWidth(bi.getWidth());
	}

	@Override
	public String toString() {
		return "Image: " + getFileName();
	}

}
