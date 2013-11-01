package bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.ejb.EJB;
import javax.ejb.PostActivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ValueChangeEvent;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.seam.log.Log;
import org.richfaces.model.UploadItem;

import util.BeanResetter;
import util.FormElementFactory;
import util.IFormElementFactory;
import util.JSFNavigationConstants;
import util.convert.BigDecimalStringListConverter;
import util.convert.IntegerStringListConverter;
import db.interfaces.IAttributeDAO;
import db.interfaces.IAttributeGroupDAO;
import db.interfaces.IConstraintDAO;
import db.interfaces.IFormElementDAO;
import db.interfaces.ITrialAttachmentDAO;
import db.interfaces.ITrialDataDAO;
import db.interfaces.ITrialFormDAO;
import entities.Attribute;
import entities.AttributeGroup;
import entities.Image;
import entities.constraint.Constraint;
import entities.constraint.DecimalConstraint;
import entities.constraint.IntegerConstraint;
import entities.constraint.StringConstraint;
import entities.formelement.DATATYPE;
import entities.formelement.FORMELEMENTTYPE;
import entities.formelement.FormElement;
import entities.formelement.IFormElementList;
import entities.formelement.TextField;

@Stateful
@Scope(ScopeType.SESSION)
@Name("attributeGroupEditor")
public class AttributeGroupEditorBean implements AttributeGroupEditor {

	private static final long serialVersionUID = 1L;
	
	@Logger
	private Log log;
	
	private List<Attribute> attributesForDelete = new ArrayList<Attribute>();
	private List<Image> imageCommentForDelete = new ArrayList<Image>();
	private List<FormElement> formelementForDelete = new ArrayList<FormElement>();
	private List<Constraint> constraintForDelete = new ArrayList<Constraint>();
	private transient HtmlPanelGrid attributeGroupEditPanel;
	
	private Map<String, Object> dataField = new HashMap<String, Object>();

	@EJB private IAttributeGroupDAO attributeGroupDAO;
	@EJB private AttributeGroupEditorUI attributeGroupEditorUI;
	@EJB private IAttributeDAO attributeDAO;
	@EJB private ITrialFormDAO trialFormDAO;
	@EJB private ITrialDataDAO trialDataDAO;
	@EJB private ITrialAttachmentDAO fileDAO;
	@EJB private IFormElementDAO formElementDAO;
	@EJB private IConstraintDAO constraintDAO;
	
	private AttributeGroup editAttributeGroup;
	private int selectedLine;
	private ValueChangeEvent formElementValueChangedEvent;
	private ValueChangeEvent dataTypeValueChangedEvent;
	
	@In
	private Long editAttributeGroupId;
	
//	TODO: remove
//	@In(required=false)
//	private String comingFrom;
	
	@In @Out
	private Boolean attributeGroupEditorReset;
	
	@In(required = false)
	private BeanResetter beanResetter;
	
	@SuppressWarnings("unused")
	@Out(required=false)
	private Long trialFormToArchiveId;
	
	private transient List<UploadItem> uploads = new LinkedList<UploadItem>();
	
	private String trialFormName;
	
	public HtmlPanelGrid getAttributeGroupEditPanel() {
		log.info("getAttributeGroupEditPanel() #0 AG: #1 in ID: #2 sort: #3 ", attributeGroupEditPanel, (editAttributeGroup != null ? editAttributeGroup.getId() : ""), editAttributeGroupId, selectedLine);
		
		if(editAttributeGroup == null)
			return null;	//trigger reload
		
		return attributeGroupEditPanel;
	}
	
	public void setAttributeGroupEditPanel(HtmlPanelGrid panel) {
	
		log.info("setAttributeGroupEditPanel called for #0 reset? #1 selectedLine: #2" ,editAttributeGroupId, attributeGroupEditorReset, selectedLine);
		if(editAttributeGroup==null || attributeGroupEditorReset) {
			log.info("(re)loading attributegroup by id");
			selectedLine = 0;
			attributeGroupEditorReset = false;
			editAttributeGroup = attributeGroupDAO.findByID(editAttributeGroupId);
			if(editAttributeGroup.getAttributes().isEmpty()){
				log.info("new attributegroup - creating default textfield");
				TextField newTf = new TextField();
				newTf.setDataType(DATATYPE.STR);
				Attribute newAtt = new Attribute();
				newAtt.setAttributeGroup(editAttributeGroup);
				newAtt.setFormElement(newTf);
				newAtt.setSort(0);
				newAtt.setName("");
				editAttributeGroup.getAttributes().add(newAtt);
			}
			attributeGroupEditorUI.updateAttribtueGroupEditPanel(editAttributeGroup, panel);
		}

		this.attributeGroupEditPanel = panel;
		
		this.beanResetter.addGridBean(this);
		
		this.trialFormName = editAttributeGroup.getTrialForm().getName();
	}

	@Remove
	@Destroy
	public void cleanUp(){
		attributeGroupEditPanel = null;	
	}
	
	@SuppressWarnings("unchecked")
	private void syncFormElementDropDownWithModel(int line){
		
		// sync attributeGroupName
		editAttributeGroup.setName((String)dataField.get("id_attributegroupname"));
		
		//sync attributeName
		editAttributeGroup.getAttributes().get(line).setName((String)dataField.get("id_name_"+line));
		
		Attribute currentAtt = editAttributeGroup.getAttributes().get(line);
		FormElement currentFE = currentAtt.getFormElement();
		
		//sync values for FormElements containing a list of values
		if(currentFE instanceof IFormElementList){		
			String id = "id_formelementvalues_" + line;
			
			((IFormElementList)currentFE).getValues().clear();
			
			List<String> values = (List<String>)dataField.get(id);
			
			if(values != null)
				((IFormElementList)currentFE).getValues().addAll(values);
			
		}
		
		// syncing unit
		currentAtt.setUnit((String)dataField.get("id_unit_"+line));
		
		// syncing reference value
		if(currentFE.getDataType() == DATATYPE.STR){
			Constraint prev = currentFE.getConstraint();
			StringConstraint sc = new StringConstraint();
			sc.setId(prev == null ? null : prev.getId());
			sc.setRegexp((String)dataField.get("id_constraint_"+line));
			currentFE.setConstraint(sc);
		}
		if(currentFE.getDataType() == DATATYPE.INTEGER){
			Constraint prev = currentFE.getConstraint();
			IntegerConstraint ic = new IntegerConstraint();
			ic.setId(prev == null ? null : prev.getId());
			ic.setMin((Integer)dataField.get("id_constraint_"+line+"_1"));
			ic.setMax((Integer)dataField.get("id_constraint_"+line+"_2"));
			currentFE.setConstraint(ic);
		}
		if(currentFE.getDataType() == DATATYPE.DECIMAL){
			Constraint prev = currentFE.getConstraint();
			DecimalConstraint dc = new DecimalConstraint();
			dc.setId(prev == null ? null : prev.getId());
			dc.setMin((BigDecimal)dataField.get("id_constraint_"+line+"_1"));
			dc.setMax((BigDecimal)dataField.get("id_constraint_"+line+"_2"));
			currentFE.setConstraint(dc);
		}
		
		// syncing recommended flag
		currentAtt.setRecommended((Boolean)dataField.get("id_recommended_"+line));
		
				
	}

	
	public String insertAfter(Integer line){
		
		for(int i=0; i<editAttributeGroup.getAttributes().size(); i++)
			syncFormElementDropDownWithModel(i);
		
		TextField newTf = new TextField();
		newTf.setDataType(DATATYPE.STR);
		
		Attribute newAtt = new Attribute();
		newAtt.setAttributeGroup(editAttributeGroup);
		newAtt.setFormElement(newTf);
		newAtt.setSort(line);
		editAttributeGroup.getAttributes().add(line+1, newAtt);
		
		for(int i=line+1; i<editAttributeGroup.getAttributes().size(); i++){
			editAttributeGroup.getAttributes().get(i).setSort(editAttributeGroup.getAttributes().get(i).getSort()+1);
		}
		
		attributeGroupEditorUI.updateAttribtueGroupEditPanel(editAttributeGroup, attributeGroupEditPanel);
	
		dataField.clear();
		
		
		return JSFNavigationConstants.RELOADPAGE;
	}

	
	
	public String formElementValueChangedAction(){
		
		selectedLine = Integer.parseInt(formElementValueChangedEvent.getComponent().getId().split("_")[2]);
		log.info("selectedLine: " + selectedLine);
		
		for(int i=0; i<editAttributeGroup.getAttributes().size(); i++)
			syncFormElementDropDownWithModel(i);
	
		Attribute currentAtt = editAttributeGroup.getAttributes().get(selectedLine);
		FormElement previous = currentAtt.getFormElement();
		
		if(previous.getId() != null) { // already persisted?
			formelementForDelete.add(previous);
		}
		
		// vorher schon ein FormElement mit mehreren Values?
		// wenn ja, behalte die values
		List <String> values = null;
		if(previous instanceof IFormElementList){
			values = ((IFormElementList)previous).getValues();
		}
		
		IFormElementFactory formElementFactory = new FormElementFactory();
		FormElement newFE = formElementFactory.createFormElementWithListValues(FORMELEMENTTYPE.valueOf((String)formElementValueChangedEvent.getNewValue()), values);
		
		currentAtt.setFormElement(newFE);
	
		dataField.clear();
		
		attributeGroupEditorUI.updateAttribtueGroupEditPanel(editAttributeGroup, attributeGroupEditPanel);

		
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	
	public void formElementValueChanged(ValueChangeEvent e) {

		formElementValueChangedEvent = e;

	}
	
	public String dataTypeValueChangedAction(){
		
		log.info("datatype value changed action called!");
		UIOutput component = (UIOutput)dataTypeValueChangedEvent.getComponent();
		
		Integer line = Integer.parseInt(component.getId().split("_")[2]);
		
		FormElement fe = editAttributeGroup.getAttributes().get(line).getFormElement();
		
		DATATYPE newDataType = DATATYPE.valueOf((String)dataTypeValueChangedEvent.getNewValue());
		
		// make sure the datatype isn't changed if in the list contains a wrong datatype in the first place!!
		if(fe instanceof IFormElementList) {
			IFormElementList list = (IFormElementList)fe;
			
			Converter conv = component.getConverter();
			
			switch(newDataType) {
			case INTEGER:
				conv = new IntegerStringListConverter();
				break;
			case DECIMAL:
				conv = new BigDecimalStringListConverter();
				break;
			}
			if(conv != null) {
				try {
					StringBuffer strBuf = new StringBuffer();
					for(String str : list.getValues()) {
						strBuf.append(str);
						strBuf.append("\n");
					}
					conv.getAsObject(FacesContext.getCurrentInstance(), component, strBuf.toString());
				} catch(ConverterException e) {
					dataField.put("id_datatype_" + line, DATATYPE.valueOf((String)dataTypeValueChangedEvent.getOldValue()));
					// TODO: is there no way to "rethrow" the e.getFacesMessage() without modification?
					FacesMessages.instance().add(Severity.ERROR, e.getMessage());
					return JSFNavigationConstants.RELOADPAGE;
				}
			}
		}
		
		for(int i=0; i<editAttributeGroup.getAttributes().size(); i++)
			syncFormElementDropDownWithModel(i);
		
		fe.setDataType(newDataType);

		if(fe.getConstraint() != null && fe.getConstraint().getId() != null)
			constraintForDelete.add(fe.getConstraint());
		
		editAttributeGroup.getAttributes().get(line).getFormElement().setConstraint(null);
		
		dataField.clear();
		
		attributeGroupEditorUI.updateAttribtueGroupEditPanel(editAttributeGroup, attributeGroupEditPanel);
		
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	public void dataTypeValueChanged(ValueChangeEvent e){
		log.info("datatype value change listener called");
		dataTypeValueChangedEvent = e;	
	}
	
	public String cancelEdit(){
		attributeGroupEditorReset = true;
		attributesForDelete = new ArrayList<Attribute>();
		imageCommentForDelete = new ArrayList<Image>();
		formelementForDelete = new ArrayList<FormElement>();
		constraintForDelete = new ArrayList<Constraint>();
		dataField.clear();
		editAttributeGroup = null;
		
		// TODO remove
//		if(comingFrom.equals(TrialFormsViewerBean.BEANNAME))
//			return JSFNavigationConstants.FINISHEDEDITINGTRIALSPECIFICTF;
		
		return JSFNavigationConstants.EDITTRIALFORM;
	}
	

	public String addImageComment(Integer line) {
		log.info("adding image comment to line #0", line);
		
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	public String moveAttributeUp(Integer line){
		
		for(int i=0; i<editAttributeGroup.getAttributes().size(); i++)
			syncFormElementDropDownWithModel(i);
		
		
		//get Attribute to be moved
		Attribute toBeMoved = editAttributeGroup.getAttributes().get(line);
		
		//get Attribute to be moved to (toBeMoved.sortId minus one)
		Attribute toBeMovedTo = editAttributeGroup.getAttributes().get(line-1);
		
		//on fast clicks one/both of these tho vars can be null
		if(toBeMoved!=null && toBeMovedTo != null){
			//change sort ids
			toBeMoved.setSort(toBeMoved.getSort()-1);
			toBeMovedTo.setSort(toBeMovedTo.getSort()+1);
			//change place in list
			editAttributeGroup.getAttributes().set(line, toBeMovedTo);
			editAttributeGroup.getAttributes().set(line-1, toBeMoved);
		}
		
		
		attributeGroupEditorUI.updateAttribtueGroupEditPanel(editAttributeGroup, attributeGroupEditPanel);
		
		dataField.clear();
		
		return JSFNavigationConstants.RELOADPAGE;
	}
	
	
	
	public String moveAttributeDown(Integer line){
		
		for(int i=0; i<editAttributeGroup.getAttributes().size(); i++)
			syncFormElementDropDownWithModel(i);
		
		//get Attribute to be moved
		Attribute toBeMoved = editAttributeGroup.getAttributes().get(line);
		
		//get Attribute to be moved to (toBeMoved.sortId plus one)
		Attribute toBeMovedTo = editAttributeGroup.getAttributes().get(line+1);
		
		//on fast clicks one/both of these tho vars can be null
		if(toBeMoved!=null && toBeMovedTo != null){
			//change sort ids
			toBeMoved.setSort(toBeMoved.getSort()+1);
			toBeMovedTo.setSort(toBeMovedTo.getSort()-1);
			//change place in list
			editAttributeGroup.getAttributes().set(line, toBeMovedTo);
			editAttributeGroup.getAttributes().set(line+1, toBeMoved);
		}
	
		
		attributeGroupEditorUI.updateAttribtueGroupEditPanel(editAttributeGroup, attributeGroupEditPanel);
		
		return JSFNavigationConstants.RELOADPAGE;
	}

	
	
	public String deleteAttribute(Integer line){
		
		for(int i=0; i<editAttributeGroup.getAttributes().size(); i++)
			syncFormElementDropDownWithModel(i);
		
		
		Attribute att = editAttributeGroup.getAttributes().get(line);
		int deletedAttributeSortId = att.getSort();
		
		
		//if att is in the database, mark it for deletion
		if(att.getId()!=null) {
			if(trialDataDAO.attributeHasTrialData(att.getId())) {
				log.info("Cannot delete attribute with id #0 - TrialData exists - redirecting to archive trialform page", att.getId());
				trialFormToArchiveId = att.getAttributeGroup().getTrialForm().getId();
				return JSFNavigationConstants.ARCHIVETRIALFORM;
			}
			
			attributesForDelete.add(att);
		}
		
		//remove from editAttributeGroup
		for(int i = 0; i<editAttributeGroup.getAttributes().size(); i++){
			if(editAttributeGroup.getAttributes().get(i).getSort() == att.getSort()) {
				editAttributeGroup.getAttributes().remove(i);
				break;
			}
		}
				
		//rearrange all following Attributes
		for(Attribute a: editAttributeGroup.getAttributes()){
			if(a.getSort()>deletedAttributeSortId) a.setSort(a.getSort()-1);
		}

		attributeGroupEditorUI.updateAttribtueGroupEditPanel(editAttributeGroup, attributeGroupEditPanel);
		dataField.clear();
		
		return JSFNavigationConstants.RELOADPAGE;
	}


	public String save() {
		//check input for constraints
		for(int line=0; line<editAttributeGroup.getAttributes().size(); line++){
			
			String attributeName = editAttributeGroup.getAttributes().get(line).getName();
			
			if(editAttributeGroup.getAttributes().get(line).getFormElement().getDataType() == DATATYPE.STR){
				String regexp = (String)dataField.get("id_constraint_"+line);
				if(regexp != null && regexp.trim().length() > 0){
					try{
						Pattern.compile(regexp);
					}catch(PatternSyntaxException e){
						FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.invalidref", attributeName);
						return JSFNavigationConstants.RELOADPAGE;
					}
				}
			}
			if(editAttributeGroup.getAttributes().get(line).getFormElement().getDataType() == DATATYPE.INTEGER){
				Integer min = (Integer)dataField.get("id_constraint_"+line+"_1");
				Integer max = (Integer)dataField.get("id_constraint_"+line+"_2");;
		
				if(min != null && max != null) {
					if(min.compareTo(max) > 0) {
						FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.invalidrefminmax", attributeName);
						return JSFNavigationConstants.RELOADPAGE;
					}
				}
			}
			if(editAttributeGroup.getAttributes().get(line).getFormElement().getDataType() == DATATYPE.DECIMAL){
				BigDecimal min = (BigDecimal)dataField.get("id_constraint_"+line+"_1");
				BigDecimal max = (BigDecimal)dataField.get("id_constraint_"+line+"_2");
				
				if(min != null && max != null) {
					if(min.compareTo(max) > 0) {
						FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.invalidrefminmax", attributeName);
						return JSFNavigationConstants.RELOADPAGE;
					}
				}
				
			}
		}
		//end of (check input for constraints)
		
		// sync all values
		for(int i=0; i<editAttributeGroup.getAttributes().size(); i++)
			syncFormElementDropDownWithModel(i);
		
		// message if there is an empty list
		for(Attribute a: editAttributeGroup.getAttributes()){
			if(a.getFormElement() instanceof IFormElementList){
						
				IFormElementList ifel =(IFormElementList) a.getFormElement();
				if(ifel.getValues().size()==0){
					FacesMessages.instance().addFromResourceBundle(Severity.ERROR, "error.emptylist", a.getName());
					return JSFNavigationConstants.RELOADPAGE;
				}
			}
		}
		
		// check for empty constraints and remove
		for(Attribute a : editAttributeGroup.getAttributes()) {
			Constraint c = a.getFormElement().getConstraint();
			if(c != null) {
				if(c.isEmpty()) {
					log.info("removing empty constraint for attribute #0", a.getName());
					a.getFormElement().setConstraint(null);
				}
			}
		}

		//save
		attributeGroupEditorReset = true;
		editAttributeGroup = attributeGroupDAO.merge(editAttributeGroup);
		
		// delete all attributes, marked for delete
		for(Attribute a: attributesForDelete){
			Attribute toRem = attributeDAO.findByID(a.getId());
			if(toRem == null)
				continue;
			
			attributeDAO.remove(toRem);
		}
		
		// delete all imagecomments marked for delete
		for(Image img : imageCommentForDelete) {
			fileDAO.remove(fileDAO.merge(img));
		}
		
		for(FormElement fe : formelementForDelete) {
			formElementDAO.remove(formElementDAO.merge(fe));
		}
		
		for(Constraint c : constraintForDelete) {
			constraintDAO.remove(constraintDAO.merge(c));
		}
		
		// set last modified date of trialform
//		List<Attribute> tmpList = editAttributeGroup.getAttributes();
		editAttributeGroup.getTrialForm().setLastModified(new Date(System.currentTimeMillis()));
		log.info("merging trialform, edited attribute group with name #0 has now #1 attributes", 
				editAttributeGroup.getName(), editAttributeGroup.getAttributes().size());
//		editAttributeGroup.getAttributes().clear();
//		editAttributeGroup.getAttributes().addAll(tmpList);
		trialFormDAO.merge(editAttributeGroup.getTrialForm());
	
		dataField.clear();
		
		// reset (to fix emtpy grid bug)
		editAttributeGroup = null;
		attributesForDelete = new ArrayList<Attribute>();
		imageCommentForDelete = new ArrayList<Image>();
		formelementForDelete = new ArrayList<FormElement>();
		constraintForDelete = new ArrayList<Constraint>();
		
//		TODO: remove
//		if(comingFrom.equals(TrialFormsViewerBean.BEANNAME))
//			return JSFNavigationConstants.FINISHEDEDITINGTRIALSPECIFICTF;
		
		return JSFNavigationConstants.EDITTRIALFORM;
	}
	
	public void processImgUpload() {
		if(uploads.size() == 0) {
			log.warn("cannot do anything, no files uploaded!!");
			return;
		}
		
		Image img = new Image();
		try {
			img.loadUploadItem(uploads.get(0));
			
			editAttributeGroup.getAttributes().get(selectedLine).setImageComment(img);
			log.info("Image comment added successfully!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uploads = new LinkedList<UploadItem>();
	}
	
	public void cancelUpload() {
		log.info("image comment upload canceled");
		uploads = new LinkedList<UploadItem>();
	}
	
	public boolean attHasImageComment() {
		return editAttributeGroup.getAttributes().get(selectedLine).getImageComment() != null;
	}

	public Map<String, Object> getDataField() {
		return dataField;
	}

	public void setDataField(Map<String, Object> df) {
		this.dataField = df;
	}

	public int getSelectedLine() {
		return selectedLine;
	}

	public void setSelectedLine(int selectedLine) {
		this.selectedLine = selectedLine;
	}

	public void reset() {
		log.info("reset by BeanResetter");
		this.attributeGroupEditorReset = true;	
	}

	public List<UploadItem> getUploads() {
		return uploads;
	}

	public void setUploads(List<UploadItem> uploads) {
		this.uploads = uploads;
	}

	public void deleteImageComment() {
		log.info("delete image comment called for line #0", selectedLine);
		Attribute current = this.editAttributeGroup.getAttributes().get(selectedLine);
		if(current.getImageComment() != null && current.getImageComment().getId() != null)
			imageCommentForDelete.add(current.getImageComment());
		
		current.setImageComment(null);
		
	}
	
	public byte[] getImageCommentPreview() {
		return this.editAttributeGroup.getAttributes().get(selectedLine).getImageComment().getData();
	}
	
	@PostActivate
	public void postActivate() {
		uploads = new LinkedList<UploadItem>();
	}

	public String getTrialFormName() {
		return trialFormName;
	}

	public void setTrialFormName(String trialFormName) {
		this.trialFormName = trialFormName;
	}

}
