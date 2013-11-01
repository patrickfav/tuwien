package bean;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.el.ValueExpression;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.IntegerConverter;
import javax.faces.event.MethodExpressionValueChangeListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import util.MessageUtilsBean;
import util.UIComponentFactory;
import util.convert.BigDecimalLocaleConverter;
import util.convert.BigDecimalStringListConverter;
import util.convert.IntegerStringListConverter;
import util.convert.StringListConverter;

import component.colspan.HTMLColSpan;

import db.interfaces.IFormElementDAO;
import entities.Attribute;
import entities.AttributeGroup;
import entities.constraint.DecimalConstraint;
import entities.constraint.IntegerConstraint;
import entities.constraint.StringConstraint;
import entities.formelement.DATATYPE;
import entities.formelement.FORMELEMENTTYPE;
import entities.formelement.FormElement;
import entities.formelement.IFormElementList;


@Stateless
@Name("attributeGroupEditorBean")
public class AttributeGroupEditorUIBean implements AttributeGroupEditorUI {
	
	@In private transient Map<String, String> messages;
	
	
	private String headers[];
	
	@EJB private UIComponentFactory compFactory;
	@EJB private IFormElementDAO formElementDAO;
	
	public AttributeGroupEditorUIBean() { }
	
	@SuppressWarnings("unchecked")
	public void updateAttribtueGroupEditPanel(AttributeGroup attributeGroup, HtmlPanelGrid grid){
	
		headers = new String[] {messages.get("label.label"), messages.get("label.formelement"),messages.get("label.datatype"), 
				messages.get("label.unit"), messages.get("label.referencevalue"), messages.get("label.recommended"), "", "", "",""};
		
		
		int id=0;	
		
		// get and reset the grid
		List children = grid.getChildren();
		children.clear();
		grid.setStyleClass("attgroup_overall");		
		
		//edit by pstaud
		HtmlPanelGrid attributeExtendedPanel = new HtmlPanelGrid();
		attributeExtendedPanel.setStyleClass("extended_group");
		attributeExtendedPanel.setStyle("font-size:12px;padding-left:5px;padding-right:5px;padding-bottom:5px;background:#fff;border-bottom:1px solid #A7CEB7;border-left:1px solid #A7CEB7;border-right:1px solid #A7CEB7");
		
		HtmlPanelGrid attributeGrandPanel = new HtmlPanelGrid();
		attributeGrandPanel.setColumns(1);
		attributeGrandPanel.setCellpadding("0");
		attributeGrandPanel.setCellspacing("0");
		attributeGrandPanel.setStyleClass("attgroup_overall");
		
		//TODO export style information!!!
		//edit_over
		
		// displaying attribute group name
		HtmlPanelGrid attributeNamePanel = new HtmlPanelGrid();
		attributeNamePanel.setId("id_"+id++);
		attributeNamePanel.setColumns(3);
		attributeNamePanel.setStyleClass("overall_group_table");
		attributeNamePanel.setStyle("padding-bottom:0px;");
		//TODO get style info out of here!
		attributeNamePanel.setRowClasses("group_header,");
		attributeNamePanel.setColumnClasses("left_active,middle_active,right_active");
		attributeNamePanel.setCellpadding("0");
		attributeNamePanel.setCellspacing("0");
		
		HtmlGraphicImage arrow = new HtmlGraphicImage();
		arrow.setId("id_"+id++);
		arrow.setValue("/graphics/icon/opened.gif");
		
		HtmlInputText groupNameInputText = new HtmlInputText();
		groupNameInputText.setValue(attributeGroup.getName());
		groupNameInputText.setId("id_attributegroupname");
		groupNameInputText.setValueExpression("value",compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+groupNameInputText.getId()+"']}"));
		
		HtmlCommandLink saveSmallLink = new HtmlCommandLink();
		saveSmallLink.setId("id_"+id++);
		saveSmallLink.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.save}"));
		HtmlGraphicImage saveSmallImage = new HtmlGraphicImage();
		saveSmallImage.setId("id_"+id++);
		saveSmallImage.setTitle(messages.get("button.save"));
		saveSmallImage.setAlt(messages.get("button.save"));
		// TODO: remove hard coded graphics paths
		saveSmallImage.setValue("/graphics/button/save_small.png");
		saveSmallImage.setOnmouseover("this.src=\"./graphics/button/save_small_hover.png\"");
		saveSmallImage.setOnmouseout("this.src=\"./graphics/button/save_small.png\"");
		saveSmallLink.getChildren().add(saveSmallImage);
		
		attributeNamePanel.getChildren().add(arrow);
		attributeNamePanel.getChildren().add(groupNameInputText);
		attributeNamePanel.getChildren().add(saveSmallLink);
		children.add(attributeNamePanel);
		// end of displaying attribute group name
		
		
		
		HtmlPanelGrid attributeGroupPanel = new HtmlPanelGrid();
		attributeGroupPanel.setId("id_"+id++);
		attributeGroupPanel.setCellspacing("0");
		attributeGroupPanel.setCellpadding("2");
		attributeGroupPanel.setColumns(headers.length);
		attributeGroupPanel.setStyleClass("concrete_list");
		
		attributeGroupPanel.setStyle("padding-top:0px;");
		//TODO Get style info out of here!!

		/*String colClasses="left_active,";
		for(int i=0; i<headers.length-2; i++){
			colClasses += "middle_active,";
		}
		colClasses+="right_active";
		System.out.println(">>>>>>> colClasses: " + colClasses);
		attributeGroupPanel.setColumnClasses(colClasses);*/

		

		//creating and adding headers	
		for(int i =0; i<headers.length; i++){
			HtmlOutputText h = new HtmlOutputText();
			h.setValue(headers[i]);
			
			h.setStyle("h2");
			attributeGroupPanel.getChildren().add(h);
		}
		//end of adding header


		String rowClasses = "even";
		boolean lastEven = true;
		Integer line = 0;
		for(Attribute att: attributeGroup.getAttributes()){
			
			if(!lastEven){
				rowClasses +=",even";
				lastEven = true;
			} else{
				rowClasses +=",uneven";
				lastEven = false;
			}
			
			//Description field
			HtmlInputText attname = new HtmlInputText();
			attname.setId("id_name_" + line);
			attname.setValueExpression("value",compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+attname.getId()+"']}"));
			attname.setValue(att.getName());
			attributeGroupPanel.getChildren().add(attname);

			//Formelement
			HtmlSelectOneMenu formelement = getFormElementSelector(att.getFormElement());
			formelement.setId("id_formelementtype_" + line);			
			formelement.addValueChangeListener(new MethodExpressionValueChangeListener(compFactory.createMethodExpression("#{attributeGroupEditor.formElementValueChanged}", new Class[] { ValueChangeEvent.class })));
		
			attributeGroupPanel.getChildren().add(formelement);

			//DataType
			HtmlSelectOneMenu datatype = getDataTypeDropDown(att.getFormElement(), line);
			datatype.setId("id_datatype_" + line);
			datatype.addValueChangeListener(new MethodExpressionValueChangeListener(compFactory.createMethodExpression("#{attributeGroupEditor.dataTypeValueChanged}",
	                new Class[] { ValueChangeEvent.class })));

			attributeGroupPanel.getChildren().add(datatype);


			//Unit 
			// if date, checkbox or vancouverscarscale add empty element
			HtmlPanelGroup unitGroup = new HtmlPanelGroup();
			unitGroup.setId("unitGroup_" + line);
			if (!att.getFormElement().hasUnit()) {
				HtmlOutputText empty = new HtmlOutputText();
				unitGroup.getChildren().add(empty);
			} else { 
				HtmlInputText unit = new HtmlInputText();
				unit.setId("id_unit_"+line);
				unit.setSize(7);
				unit.setValueExpression("value", compFactory.createValueExpression("#{attributeGroupEditor.dataField['id_unit_"+line+"']}"));
				if(att.getUnit()!=null) unit.setValue(att.getUnit());
				unitGroup.getChildren().add(unit);
			}
			
			// link to add an image comment
			HtmlAjaxCommandLink addImgLink = new HtmlAjaxCommandLink();
			addImgLink.setOncomplete("Richfaces.showModalPanel('fileUploadMP', {line:" + line + "});");
			addImgLink.setId("addImgLnk_" + line);
			addImgLink.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.setSelectedLine(" + line +")}", new Class[] {line.getClass()}));
			addImgLink.setValue(messages.get("label.image"));
			addImgLink.setReRender("agEditFileUpForm");
			
			unitGroup.getChildren().add(addImgLink);
			attributeGroupPanel.getChildren().add(unitGroup);
					
			
			HtmlOutputText hot = null;

			//Referenzwert - makes only sense with textfields
			if(att.getFormElement().getType() == FORMELEMENTTYPE.TEXTFIELD){
				String id_constraint = "id_constraint_"+line;
				switch(att.getFormElement().getDataType()){
				case STR: 
					String regexp = null;
					
					if(att.getFormElement().getConstraint()!=null)
						regexp = ((StringConstraint)att.getFormElement().getConstraint()).getRegexp();
					
					ValueExpression vb_constraint = compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+id_constraint+"']}");
					attributeGroupPanel.getChildren().add(
							getHtmlPanelGroupForStringConstraint(id_constraint, regexp, vb_constraint)
							);
					break;
					
				case INTEGER:
					Integer min=null;
					Integer max=null;
					if(att.getFormElement().getConstraint()!=null){
						min = ((IntegerConstraint)att.getFormElement().getConstraint()).getMin();
						max = ((IntegerConstraint)att.getFormElement().getConstraint()).getMax();
					}
					String id_constraint_min = id_constraint + "_1";
					String id_constraint_max = id_constraint + "_2";
					ValueExpression vb_min = compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+id_constraint_min+"']}");
					ValueExpression vb_max = compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+id_constraint_max+"']}");
					attributeGroupPanel.getChildren().add(
							getHtmlPanelGroupForIntegerConstraint(id_constraint_min, id_constraint_max, min, max, vb_min, vb_max, att.getName())
							);
					break;
					
				case DECIMAL:
					BigDecimal min_decimal=null;
					BigDecimal max_decimal=null;
					if(att.getFormElement().getConstraint()!=null){
						min_decimal = ((DecimalConstraint)att.getFormElement().getConstraint()).getMin();
						max_decimal = ((DecimalConstraint)att.getFormElement().getConstraint()).getMax();
					}
					String id_constraint_min_float = id_constraint + "_1";
					String id_constraint_max_float = id_constraint + "_2";
					ValueExpression vb_min_float = compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+id_constraint_min_float+"']}");
					ValueExpression vb_max_float = compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+id_constraint_max_float+"']}");
					attributeGroupPanel.getChildren().add(
							getHtmlPanelGroupForFloatConstraint(id_constraint_min_float, id_constraint_max_float, min_decimal,
								max_decimal, vb_min_float, vb_max_float, att.getName())
							);
					break;
					
	//			case DATE:
	//				Date from = null;
	//				Date to = null;
	//				if(att.getFormElement().getConstraint()!=null){
	//					from = ((DateConstraint)att.getFormElement().getConstraint()).getFrom();
	//					to = ((DateConstraint)att.getFormElement().getConstraint()).getTo();
	//				}
	//				String id_constraint_from_date = id_constraint + "_1";
	//				String id_constraint_to_date = id_constraint + "_2";
	//				ValueBinding vb_from_date = app.createValueBinding("#{attributeGroupEditor.dataField['"+id_constraint_from_date+"']}");
	//				ValueBinding vb_to_date = app.createValueBinding("#{attributeGroupEditor.dataField['"+id_constraint_to_date+"']}");
	//				attributeGroupPanel.getChildren().add(
	//						getHtmlPanelGroupForDateConstraint(id_constraint_from_date, id_constraint_to_date, from,
	//						to, vb_from_date, vb_to_date)
	//						);
	//				break;
					
				default:
					// empty element
					hot = new HtmlOutputText();
					attributeGroupPanel.getChildren().add(hot);
				}
			} else{
				hot = new HtmlOutputText();
				attributeGroupPanel.getChildren().add(hot);
			}
				
			
			
			//recommended
			HtmlSelectBooleanCheckbox recommended = new HtmlSelectBooleanCheckbox();
			recommended.setId("id_recommended_"+line);
			if(att.getRecommended()!=null)recommended.setSelected(att.getRecommended());
			recommended.setValueExpression("value",compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+recommended.getId()+"']}"));
			attributeGroupPanel.getChildren().add(recommended);

			
			
			//move up
			//show only if its NOT the first element in list
			if(att.getSort()>0){
				HtmlCommandLink moveUp = new HtmlCommandLink();
				moveUp.setId("id_"+id++);
				moveUp.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.moveAttributeUp(" + line + ")}", new Class[] { line.getClass()}));
				HtmlGraphicImage moveUpImg = new HtmlGraphicImage();
				moveUpImg.setId("id_"+id++);
				moveUpImg.setAlt(messages.get("button.moveup"));
				moveUpImg.setTitle(messages.get("button.moveup"));
				moveUpImg.setValue("/graphics/button/up_small.png");
				moveUpImg.setOnmouseover("this.src=\"./graphics/button/up_small_hover.png\"");
				moveUpImg.setOnmouseout("this.src=\"./graphics/button/up_small.png\"");
				moveUp.getChildren().add(moveUpImg);
				
				attributeGroupPanel.getChildren().add(moveUp);
			}else{
				//fill cell with empty element
				hot = new HtmlOutputText();
				attributeGroupPanel.getChildren().add(hot);
			}
			//end move up
			
			
			//move down
			//show only if its NOT the last element in list
			if((att.getSort()+1)<attributeGroup.getAttributes().size()){
				HtmlCommandLink moveDown = new HtmlCommandLink();
				moveDown.setId("id_"+id++);
				moveDown.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.moveAttributeDown(" + line +")}", new Class[] {line.getClass()}));
				HtmlGraphicImage moveDownImg = new HtmlGraphicImage();
				moveDownImg.setId("id_"+id++);
				moveDownImg.setAlt(messages.get("button.movedown"));
				moveDownImg.setTitle(messages.get("button.movedown"));
				moveDownImg.setValue("/graphics/button/down_small.png");
				moveDownImg.setOnmouseover("this.src=\"./graphics/button/down_small_hover.png\"");
				moveDownImg.setOnmouseout("this.src=\"./graphics/button/down_small.png\"");
				moveDown.getChildren().add(moveDownImg);
				attributeGroupPanel.getChildren().add(moveDown);
			}else{
				//fill cell with empty element
				hot = new HtmlOutputText();
				attributeGroupPanel.getChildren().add(hot);
			}
			//end move down
			
			
			
			// delete
			if(attributeGroup.getAttributes().size() > 1){
				HtmlCommandLink delete = new HtmlCommandLink();
				delete.setId("id_"+id++);
				delete.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.deleteAttribute(" + line +")}", new Class[] {line.getClass()}));
				HtmlGraphicImage deleteSmImg = new HtmlGraphicImage();
				deleteSmImg.setId("id_"+id++);
				deleteSmImg.setAlt(messages.get("button.delete"));
				deleteSmImg.setTitle(messages.get("button.delete"));
				deleteSmImg.setValue("/graphics/button/delete_small.png");
				deleteSmImg.setOnmouseout("this.src=\"./graphics/button/delete_small.png\"");
				deleteSmImg.setOnmouseover("this.src=\"./graphics/button/delete_small_hover.png\"");
				delete.setOnclick("if (!confirm('"+messages.get("deleteentry.question")+"')) return false");
				delete.getChildren().add(deleteSmImg);
				attributeGroupPanel.getChildren().add(delete);
			} else {
				//fill cell with empty element
				hot = new HtmlOutputText();
				attributeGroupPanel.getChildren().add(hot);
			}
			// end delete
		
			List<String> listvalues = null;
			
			//get listelements if formelementtype has a list of elements to pick from (depicted by Interface)
			if(att.getFormElement() instanceof IFormElementList) {
				IFormElementList listFE = null;
				
				if(att.getFormElement().getId() != null) {
					listFE = (IFormElementList)formElementDAO.findByID(att.getFormElement().getId());
				} else {
					listFE = (IFormElementList)att.getFormElement();
				}

				listvalues = listFE.getValues();
			}
			
			//insert after
			HtmlCommandLink insertAfterLink = new HtmlCommandLink();
			insertAfterLink.setId("id_"+id++);
			insertAfterLink.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.insertAfter(" + line +")}", new Class[] {line.getClass()}));
			HtmlGraphicImage insertAfterImg = new HtmlGraphicImage();
			insertAfterImg.setId("id_"+id++);
			insertAfterImg.setAlt(messages.get("button.insertbelow"));
			insertAfterImg.setTitle(messages.get("button.insertbelow"));
			insertAfterImg.setValue("/graphics/button/add_below_small.png");
			insertAfterImg.setOnmouseover("this.src=\"./graphics/button/add_below_small_hover.png\"");
			insertAfterImg.setOnmouseout("this.src=\"./graphics/button/add_below_small.png\"");
			insertAfterLink.getChildren().add(insertAfterImg);
			
			attributeGroupPanel.getChildren().add(insertAfterLink);
			
			
			//display listelements
			if(att.getFormElement() instanceof IFormElementList) {
				hot = new HtmlOutputText();
				hot.setId("id_"+id++);
				attributeGroupPanel.getChildren().add(hot);
				
				HtmlInputTextarea values_textarea = new HtmlInputTextarea();
				setListConverter(values_textarea, att.getFormElement().getDataType());
				values_textarea.setValue(listvalues);
				values_textarea.setId("id_formelementvalues_"+line);
				values_textarea.setValueExpression("value",compFactory.createValueExpression("#{attributeGroupEditor.dataField['"+values_textarea.getId()+"']}"));
				values_textarea.setRows(3);
				values_textarea.setCols(40);
				
				if(lastEven) rowClasses+=",even";
				else rowClasses+=",uneven";
				
				// make sure the textfield stretches over 2 rows!
				HTMLColSpan colspan = new HTMLColSpan();
				colspan.setId(getUid());
				colspan.setColspan(2);
				colspan.getChildren().add(values_textarea);
				
				attributeGroupPanel.getChildren().add(colspan);
				
				for(int i=0; i<headers.length-3; i++){
					hot = new HtmlOutputText();
					hot.setId("id_"+id++);
					attributeGroupPanel.getChildren().add(hot);
				}
				
			}
			
			
			line++;
		} // end for each Attribute

		attributeGroupPanel.setRowClasses(rowClasses);
		//edit pstaud
		
		attributeGrandPanel.getChildren().add(attributeNamePanel);
		attributeExtendedPanel.getChildren().add(attributeGroupPanel);
		attributeGrandPanel.getChildren().add(attributeExtendedPanel);
		
//		save & cancel button
		HtmlPanelGrid buttonGrid = new HtmlPanelGrid();
		buttonGrid.setId("id_"+id++);
		buttonGrid.setColumns(2);
		buttonGrid.setBorder(0);
		buttonGrid.setCellspacing("0");
		buttonGrid.setCellpadding("0");
		buttonGrid.setStyle("padding-top:10px");

		buttonGrid.getChildren().add(createTrialFormSpeichern());
		buttonGrid.getChildren().add(createTrialFormAbbrechen());
		attributeGrandPanel.getChildren().add(buttonGrid);
		
		children.add(attributeGrandPanel);
		
		//hidden command buttons for dropdown-value-changing
		HtmlCommandButton hidden_formElementValueChangedCommandButton = new HtmlCommandButton();
		hidden_formElementValueChangedCommandButton.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.formElementValueChangedAction}"));
		hidden_formElementValueChangedCommandButton.setId("hidden_formElementValueChangedCommandButton");
		hidden_formElementValueChangedCommandButton.setStyle("display:none; visibility: hidden;");
		hidden_formElementValueChangedCommandButton.setValue("hidden");
		children.add(hidden_formElementValueChangedCommandButton);
		
		HtmlCommandButton hidden_dataTypeValueChangedCommandButton = new HtmlCommandButton();
		hidden_dataTypeValueChangedCommandButton.setActionExpression(compFactory.createMethodExpression("#{attributeGroupEditor.dataTypeValueChangedAction}"));
		hidden_dataTypeValueChangedCommandButton.setId("hidden_dataTypeValueChangedCommandButton");
		hidden_dataTypeValueChangedCommandButton.setStyle("display:none; visibility: hidden;");
		hidden_dataTypeValueChangedCommandButton.setValue("hidden");
		children.add(hidden_dataTypeValueChangedCommandButton);		

		//edit end pstaud

	}


	private void setListConverter(HtmlInputTextarea values_textarea,
			DATATYPE dataType) {
		values_textarea.getAttributes().put(StringListConverter.MAX_WIDTH_ATTRIBUTE_KEY, new Integer(255));
		switch(dataType) {
		case STR:
			values_textarea.setConverter(new StringListConverter());
			break;
		case DECIMAL:
			values_textarea.setConverter(new BigDecimalStringListConverter());
			break;
		case INTEGER:
			values_textarea.setConverter(new IntegerStringListConverter());
			break;
		default:
			throw new IllegalArgumentException("dataType");
		}
		
	}

	private HtmlCommandButton createTrialFormSpeichern() {
		HtmlCommandButton speichern = new HtmlCommandButton();
		speichern.setId("saveAttributeGroup");
		speichern.setTitle(messages.get("button.save"));
		speichern.setValue(messages.get("button.save"));
		speichern.setStyleClass("button");

		speichern
				.setActionExpression(compFactory
						.createMethodExpression("#{attributeGroupEditor.save}"));
		return speichern;
	}
	
	private HtmlCommandButton createTrialFormAbbrechen() {
		HtmlCommandButton abbrechen = new HtmlCommandButton();
		abbrechen.setId("cancelAGEdit");
		abbrechen.setTitle(messages.get("button.cancel"));
		abbrechen.setValue(messages.get("button.cancel"));
		abbrechen.setImmediate(true);
		abbrechen.setStyleClass("button");

		abbrechen
				.setActionExpression(compFactory
						.createMethodExpression("#{attributeGroupEditor.cancelEdit}"));
		return abbrechen;
	}

	@SuppressWarnings("unchecked")
	private HtmlPanelGroup getHtmlPanelGroupForFloatConstraint(
			String id_constraint_min_decimal, String id_constraint_max_decimal,
			BigDecimal min_decimal, BigDecimal max_decimal, ValueExpression vb_min_decimal,
			ValueExpression vb_max_decimal, String attName) {
		
		HtmlPanelGroup panel = new HtmlPanelGroup();
		
		HtmlInputText hit_min = new HtmlInputText();
		hit_min.setId(id_constraint_min_decimal);
		hit_min.setValueExpression("value", vb_min_decimal);
		hit_min.setValue(min_decimal!=null?min_decimal:"");
		hit_min.setSize(3);
		hit_min.setConverter(new BigDecimalLocaleConverter());
		hit_min.setConverterMessage(MessageUtilsBean.formatErrorMessage("error.invalidrefdetail", attName, messages.get("error.bigdecimal")));
		
		HtmlInputText hit_max = new HtmlInputText();
		hit_max.setId(id_constraint_max_decimal);
		hit_max.setValueExpression("value", vb_max_decimal);
		hit_max.setValue(max_decimal!=null?max_decimal:"");	
		hit_max.setSize(3);
		hit_max.setConverter(new BigDecimalLocaleConverter());
		hit_max.setConverterMessage(MessageUtilsBean.formatErrorMessage("error.invalidrefdetail", attName, messages.get("error.bigdecimal")));
		
		HtmlOutputText hot = new HtmlOutputText();
		hot.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
		hot.setValue(" - ");
		
		panel.getChildren().add(hit_min);
		panel.getChildren().add(hot);
		panel.getChildren().add(hit_max);
		return panel;		
	}

	@SuppressWarnings("unchecked")
	private HtmlPanelGroup getHtmlPanelGroupForStringConstraint(
			String id_constraint,String regexp, ValueExpression valueexpression) {
		
		HtmlPanelGroup panel = new HtmlPanelGroup();
		HtmlInputText hit = new HtmlInputText();
		hit.setId(id_constraint);
		hit.setValue(regexp);
		hit.setValueExpression("value", valueexpression);

		panel.getChildren().add(hit);
		return panel;	
	}

	@SuppressWarnings("unchecked")
	private HtmlPanelGroup getHtmlPanelGroupForIntegerConstraint(
			String id_constraint_min, String id_constraint_max, Integer min,
			Integer max, ValueExpression vb_min, ValueExpression vb_max, String attName) {
		
		HtmlPanelGroup panel = new HtmlPanelGroup();
		
		HtmlInputText hit_min = new HtmlInputText();
		hit_min.setId(id_constraint_min);
		hit_min.setValueExpression("value", vb_min);
		hit_min.setValue(min!=null?min:"");
		hit_min.setSize(3);
		hit_min.setConverter(new IntegerConverter());
		hit_min.setConverterMessage(MessageUtilsBean.formatErrorMessage("error.invalidrefdetail", attName, messages.get("javax.faces.converter.IntegerConverter.INTEGER")));
		
		HtmlInputText hit_max = new HtmlInputText();
		hit_max.setId(id_constraint_max);
		hit_max.setValueExpression("value", vb_max);
		hit_max.setValue(max!=null?max:"");	
		hit_max.setSize(3);
		hit_max.setConverter(new IntegerConverter());
		hit_max.setConverterMessage(MessageUtilsBean.formatErrorMessage("error.invalidrefdetail", attName, messages.get("javax.faces.converter.IntegerConverter.INTEGER")));

		HtmlOutputText hot = new HtmlOutputText();
		hot.setId(FacesContext.getCurrentInstance().getViewRoot().createUniqueId());
		hot.setValue(" - ");
		
		panel.getChildren().add(hit_min);
		panel.getChildren().add(hot);
		panel.getChildren().add(hit_max);
		return panel;
		
	}
	
	@SuppressWarnings("unchecked")
	private HtmlSelectOneMenu getFormElementSelector(FormElement selected) {
		
		HtmlSelectOneMenu select_formElement = new HtmlSelectOneMenu();
		UISelectItems values_formElement = new UISelectItems();
		List<SelectItem> valueList_formElement = new LinkedList<SelectItem>();
		select_formElement.setImmediate(true);
		
		select_formElement.setOnchange("document.forms['agEditForm'].elements['agEditForm:hidden_formElementValueChangedCommandButton'].click()");
	
		SelectItem si = null;
		
		FORMELEMENTTYPE[] elemTypes = FORMELEMENTTYPE.values();
		for(FORMELEMENTTYPE t : elemTypes) {
			si = new SelectItem();
			si.setValue(t.toString());
			si.setLabel(messages.get("formelement." + t.toString().toLowerCase()));
			valueList_formElement.add(si);
		}
		
		values_formElement.setValue(valueList_formElement);
		select_formElement.getChildren().add(values_formElement);
		select_formElement.setValue(selected.getType().toString());
		
		return select_formElement;
	}

	private HtmlSelectOneMenu getDataTypeDropDown(FormElement fe, Integer line) {
		HtmlSelectOneMenu select_dataType = new HtmlSelectOneMenu();
		UISelectItems values = new UISelectItems();
		List<SelectItem> valueList = new LinkedList<SelectItem>();
		select_dataType.setStyleClass("selectdatatype");
		
		for(DATATYPE dt : fe.getValidDatatypes()) {
			valueList.add(new SelectItem(dt, messages.get("datatype." + dt.toString().toLowerCase())));
		}
		
		values.setValue(valueList);
		select_dataType.getChildren().add(values);
		select_dataType.setOnchange("document.forms['agEditForm'].elements['agEditForm:hidden_dataTypeValueChangedCommandButton'].click()");
		
		if(fe.getDataType() != null) 
			select_dataType.setValue(fe.getDataType().toString());
		
		return select_dataType;
	}
	
	private String getUid() {
		return FacesContext.getCurrentInstance().getViewRoot().createUniqueId();
	}
}
