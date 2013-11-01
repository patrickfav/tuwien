package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.model.UploadItem;

import util.Resettable;

@Local
public interface AttributeGroupEditor extends Resettable, Serializable {

	public HtmlPanelGrid getAttributeGroupEditPanel();

	public void setAttributeGroupEditPanel(HtmlPanelGrid attributeGroupEditPanel);

	public void cleanUp();

	public String cancelEdit();

	public String save();

	public String moveAttributeUp(Integer line);

	public String moveAttributeDown(Integer line);

	public String deleteAttribute(Integer line);
	
	public String addImageComment(Integer line);

	public Map<String, Object> getDataField();

	public void setDataField(Map<String, Object> dataField);

	public void formElementValueChanged(ValueChangeEvent e);

	public String formElementValueChangedAction();

	public void dataTypeValueChanged(ValueChangeEvent e);

	public String dataTypeValueChangedAction();

	public int getSelectedLine();

	public void setSelectedLine(int selectedLine);

	public String insertAfter(Integer line);
	
	public void processImgUpload();
	public boolean attHasImageComment();
	public void deleteImageComment();
	public void cancelUpload();
	public byte[] getImageCommentPreview();
	
	public List<UploadItem> getUploads();

	public void setUploads(List<UploadItem> uploads);
	
	public void postActivate();
	
	public String getTrialFormName();

	public void setTrialFormName(String trialFormName);
}
