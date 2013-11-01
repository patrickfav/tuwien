package util;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.validator.LengthValidator;

import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.ui.component.html.HtmlDiv;
import org.jboss.seam.ui.component.html.HtmlFragment;
import org.jboss.seam.ui.component.html.HtmlLink;
import org.jboss.seam.ui.component.html.HtmlSelectItems;
import org.richfaces.component.html.HtmlCalendar;
import org.richfaces.component.html.HtmlDataList;
import org.richfaces.component.html.HtmlSpacer;

import util.convert.BigDecimalLocaleConverter;

import component.pain.HtmlImageLabelRadio;
import component.vss.VancouverScarScale;

import entities.formelement.DATATYPE;
import entities.formelement.DropDown;
import entities.formelement.FormElement;
import entities.formelement.TextArea;
import entities.formelement.TextField;

@Stateless
@Name("UIComponentFactory")
@BypassInterceptors
public class UIComponentFactoryBean implements UIComponentFactory {

	private MessageUtils messages = MessageUtilsBean.getInstance();
	
	public UIComponent getComponentWithExpressions(FormElement elem, boolean disabled, String beanName, String keyString) {
		switch (elem.getType()) {
		case TEXTFIELD:
			HtmlInputText inputText = new HtmlInputText();
			entities.formelement.TextField tf = (entities.formelement.TextField) elem;
			inputText.setSize(tf.getSize());
			inputText.setDisabled(disabled);
			inputText.addValidator(new LengthValidator(TextField.LENGTH));
			setDefaultValueExpression(inputText, beanName, keyString);
			return inputText;
		case TEXTAREA:
			HtmlInputTextarea inputArea = new HtmlInputTextarea();
			inputArea.setRows(12);
			inputArea.setCols(75);
			inputArea.setDisabled(disabled);
			inputArea.addValidator(new LengthValidator(TextArea.LENGTH));
			setDefaultValueExpression(inputArea, beanName, keyString);
			return inputArea;
		case DROPDOWN:
			HtmlSelectOneMenu select = new HtmlSelectOneMenu();
			DropDown dd = (DropDown) elem;

			// using seam selectItems instead of faces to get "noSelectionLabel"
			HtmlSelectItems values = new HtmlSelectItems();
			values.setHideNoSelectionLabel(false);
			values.setNoSelectionLabel(messages.get("label.noselection"));
			values.setVar("dd_item");
			if(dd.getDataType().equals(DATATYPE.DECIMAL)) {
				values.setValueExpression("label", createValueExpression("#{BigDecimalLocaleConverter.internationalize(dd_item)}"));
			} else {
				values.setValueExpression("label", createValueExpression("#{dd_item}"));
			}
			values.setValue(dd.getValues());
			select.getChildren().add(values);
			select.setDisabled(disabled);
			setDefaultValueExpression(select, beanName, keyString);
			return select;
		case DATEPICKER:

			// now using RichFaces Calendar
			HtmlCalendar cal = new HtmlCalendar(); 
			cal.setPopup(true);
			cal.setDatePattern(messages.get("dateformat"));
			cal.setShowApplyButton(false);
			cal.setDisabled(disabled);
			cal.setButtonIcon("/graphics/icon/calendar.gif");
			cal.setButtonIconDisabled("/graphics/icon/calendar_disabled.gif");
			cal.setButtonClass("rich-calendar-button");
			setDefaultValueExpression(cal, beanName, keyString);
			return cal;

		case LIST:
			HtmlSelectOneListbox list = new HtmlSelectOneListbox();
			entities.formelement.List listFE = (entities.formelement.List)elem;
			List<String> listValues = listFE.getValues();
			UISelectItems selectItemsList = new UISelectItems();
			List<SelectItem> listItems = new LinkedList<SelectItem>();
			for (String s : listValues) {
				SelectItem i = new SelectItem(s, internationalize(listFE.getDataType(), s));
				listItems.add(i);
			}
			selectItemsList.setValue(listItems);
			list.getChildren().add(selectItemsList);
			list.setDisabled(disabled);
			setDefaultValueExpression(list, beanName, keyString);
			return list;
		case CHECKBOX:
			HtmlSelectBooleanCheckbox checkbox = new HtmlSelectBooleanCheckbox();
			checkbox.setDisabled(disabled);
			setDefaultValueExpression(checkbox, beanName, keyString);
			return checkbox;
		case RADIOBUTTONS:
			HtmlSelectOneRadio radio = new HtmlSelectOneRadio();
			radio.setLayout("pageDirection");
			entities.formelement.RadioButtons rbs = ((entities.formelement.RadioButtons) elem);
			List<String> radioValues = rbs.getValues();
			UISelectItems radioItemsList = new UISelectItems();
			List<SelectItem> radioItems = new LinkedList<SelectItem>();
			for (String s : radioValues) {
				SelectItem i = new SelectItem(s, internationalize(rbs.getDataType(), s));
				radioItems.add(i);
			}
			radioItemsList.setValue(radioItems);
			radio.getChildren().add(radioItemsList);
			radio.setDisabled(disabled);
			setDefaultValueExpression(radio, beanName, keyString);
			return radio;
		case VASSCALE:
			HtmlSelectOneRadio vas = new HtmlSelectOneRadio();
			vas.setLayout("lineDirection");
			UISelectItems vasItemsList = new UISelectItems();
			List<SelectItem> vasItems = new LinkedList<SelectItem>();
			for (int i = 1; i < 11; i++) {
				SelectItem si = new SelectItem(i);
				vasItems.add(si);
			}
			vasItemsList.setValue(vasItems);
			vas.getChildren().add(vasItemsList);
			vas.setDisabled(disabled);
			setDefaultValueExpression(vas, beanName, keyString);
			return vas;
		case FPRSSCALE:
			HtmlImageLabelRadio fprsScale = new HtmlImageLabelRadio();
			UISelectItems fprsScaleItemsList = new UISelectItems();
			List<SelectItem> fprsScaleItems = new LinkedList<SelectItem>();

	// 6 items
			for (int i = 1; i < 7; i++) { // at the moment fixed size and values (1-6)
				SelectItem s = new SelectItem();
				s.setValue(new Integer(i));

				s.setLabel("graphics/image/s_skala" + i + ".gif");

				fprsScaleItems.add(s);
			}
			fprsScaleItemsList.setValue(fprsScaleItems);
			fprsScale.getChildren().add(fprsScaleItemsList);
			fprsScale.setDisabled(disabled);
			setDefaultValueExpression(fprsScale, beanName, keyString);
			return fprsScale;
			
		case FILEUPLOAD:
			
			HtmlDiv fragment = new HtmlDiv();
			fragment.setId("fileupload_wrapper");
			
			HtmlAjaxCommandLink addFileLnk = new HtmlAjaxCommandLink();
			addFileLnk.setOncomplete("Richfaces.showModalPanel('fileUploadMP');");
			addFileLnk.setId(getUid());
			addFileLnk.setValue(messages.get("label.addattachment"));
			addFileLnk.setReRender("trialDataEditFileUpForm");
			addFileLnk.setDisabled(disabled);
			
			if(keyString != null)
				addFileLnk.setActionExpression(createMethodExpression("#{" + beanName
						+ ".setUploadId('" + keyString
						+ "')}", new Class[] {String.class}));
			
			fragment.getChildren().add(addFileLnk);
			
			if(keyString != null) {
				HtmlDataList fileList = new HtmlDataList();				
				fileList.setValueExpression("value", createValueExpression("#{" + beanName + ".dataMap['" + keyString +  "'].files}"));		
				fileList.setStyleClass("downloadlist");
				fileList.setRowClasses("download");
				fileList.setVar("file");
				fileList.setRowKeyVar("rowKey");
				
				HtmlLink dlLink = new HtmlLink();
				dlLink.setValueExpression("value", createValueExpression("#{file.fileName}"));
				dlLink.setView("/seam/resource/spicsfile/getfile.xhtml");	
				
				UIParameter param = new UIParameter();
				param.setName("taid");
				param.setValueExpression("value", createValueExpression("#{file.id}"));
				dlLink.getChildren().add(param);
				
				fileList.getChildren().add(dlLink);
				
				HtmlSpacer spacer = new HtmlSpacer();
				spacer.setWidth("7px");
				
				HtmlAjaxCommandLink deleteLink = new HtmlAjaxCommandLink();
				deleteLink.setId(getUid());
				deleteLink.setActionExpression(createMethodExpression("#{" + beanName
						+ ".deleteFile('" + keyString
						+ "', rowKey)}", new Class[] {String.class, Integer.class}));
				deleteLink.setOnclick("if (!confirm(' " + messages.get("deletefile.question") + " ')) { return false }");
				deleteLink.setReRender("trialDataEditorForm");
				
				HtmlGraphicImage deleteImage = new HtmlGraphicImage();
				deleteImage.setAlt(messages.get("button.delete"));
				deleteImage.setTitle(messages.get("button.delete"));
				deleteImage.setValue("graphics/icon/delete.png");
				deleteLink.getChildren().add(deleteImage);
				
				fileList.getChildren().add(spacer);
				fileList.getChildren().add(deleteLink);
				
				fragment.getChildren().add(fileList);
			}
			return fragment;
		case VANCOUVERSCARSCALE:
			VancouverScarScale vss = new VancouverScarScale();
			vss.setDisabled(disabled);
			vss.setStyleClass("vssClass");
			List<SelectItem> itemsList = new LinkedList<SelectItem>();
			SelectItemGroup sig = new SelectItemGroup(messages.get("component.vss.pliability"));
			sig.setSelectItems(new SelectItem[] {
					new SelectItem(0, messages.get("component.vss.pliability.0")),
					new SelectItem(1, messages.get("component.vss.pliability.1")),
					new SelectItem(2, messages.get("component.vss.pliability.2")),
					new SelectItem(3, messages.get("component.vss.pliability.3")),
					new SelectItem(4, messages.get("component.vss.pliability.4"))
			});
			itemsList.add(sig);
			SelectItemGroup sig2 = new SelectItemGroup(messages.get("component.vss.height"));
			sig2.setSelectItems(new SelectItem[] {
					new SelectItem(0, messages.get("component.vss.height.0")),
					new SelectItem(1, messages.get("component.vss.height.1")),
					new SelectItem(2, messages.get("component.vss.height.2")),
					new SelectItem(3, messages.get("component.vss.height.3")),
				//	new SelectItem(4, messages.get("component.vss.height.4"))
			});
			itemsList.add(sig2);
			SelectItemGroup sig3 = new SelectItemGroup(messages.get("component.vss.vascularity"));
			sig3.setSelectItems(new SelectItem[] {
					new SelectItem(0, messages.get("component.vss.vascularity.0")),
					new SelectItem(1, messages.get("component.vss.vascularity.1")),
					new SelectItem(2, messages.get("component.vss.vascularity.2")),
					new SelectItem(3, messages.get("component.vss.vascularity.3"))
			});
			itemsList.add(sig3);
			SelectItemGroup sig4 = new SelectItemGroup(messages.get("component.vss.pigmentation"));
			sig4.setSelectItems(new SelectItem[] {
					new SelectItem(0, messages.get("component.vss.pigmentation.0")),
					new SelectItem(1, messages.get("component.vss.pigmentation.1")),
					new SelectItem(2, messages.get("component.vss.pigmentation.2")),
					new SelectItem(3, messages.get("component.vss.pigmentation.3"))
			});
			itemsList.add(sig4);
			UISelectItems items = new UISelectItems();
			items.setValue(itemsList);
			vss.getChildren().add(items);
			setDefaultValueExpression(vss, beanName, keyString);
			return vss;
		default:
			return getNotYetImplemented();
		}
	}
	
	@SuppressWarnings("unchecked")
	public UIComponent getComponentFromFormElement(FormElement elem,
			boolean disabled) {

		return getComponentWithExpressions(elem, disabled, null, null);

	}
	
	private void setDefaultValueExpression(UIComponent comp, String beanName, String keyString) {
		if(keyString == null) 	return;
		
		comp.setValueExpression("value", createValueExpression("#{" + beanName
						+ ".dataMap['" +keyString
						+ "']}"));
	}
	
	private String internationalize(DATATYPE dt, String s) {
		if(dt.equals(DATATYPE.DECIMAL))
			return BigDecimalLocaleConverter.internationalize(FacesContext.getCurrentInstance().getViewRoot().getLocale(), s);
		
		return s;
	}

	private HtmlInputText getNotYetImplemented() {
		HtmlInputText notYetImplemented = new HtmlInputText();
		notYetImplemented.setValue("Not yet implemented");
		notYetImplemented.setDisabled(true);
		return notYetImplemented;
	}
	
	public MethodExpression createMethodExpression(String expression) {
		return createMethodExpression(expression, new Class[] { });
	}

	public MethodExpression createMethodExpression(String expression,
			Class<?>[] classes) {
		FacesContext fc = FacesContext.getCurrentInstance();
		return fc.getApplication().getExpressionFactory().createMethodExpression(fc.getELContext(), expression, null, classes);
	}
	
	public ValueExpression createValueExpression(String expression) {
		FacesContext fc = FacesContext.getCurrentInstance();
		return fc.getApplication().getExpressionFactory().createValueExpression(fc.getELContext(), expression, Object.class);
	}
	
	private String getUid() {
		return FacesContext.getCurrentInstance().getViewRoot().createUniqueId();
	}

}
