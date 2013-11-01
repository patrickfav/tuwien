package bean;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.BooleanConverter;
import javax.faces.convert.IntegerConverter;
import javax.faces.model.SelectItem;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.jboss.seam.ui.component.html.HtmlSpan;
import org.jboss.seam.ui.component.html.HtmlTransformImageSize;

import util.ComponentMapKey;
import util.SpicsPermissions;
import util.UIComponentFactory;
import util.convert.BigDecimalLocaleConverter;

import component.decorate.WarningAwareDecorate;

import db.interfaces.ITrialFormDAO;
import entities.Attribute;
import entities.AttributeGroup;
import entities.TrialData;
import entities.TrialForm;
import entities.formelement.DATATYPE;

@Stateless
@Name("TrialFormEditorUI")
public class TrialFormEditorUIBean implements TrialFormEditorUI {

	@In	// TODO: replace? 
	private Map<String, String> messages;

	@EJB
	private UIComponentFactory compFactory;

	@EJB
	private ITrialFormDAO trialformDAO;

	@Logger
	private Log log;

	@Out(required = false)	// TODO not used (i think)
	private LinkedList<ComponentMapKey> keyList;

	private TrialForm tf;

	private TrialData td;

	private Boolean[] renderFlags;

	private HtmlPanelGrid mainGrid;

	private boolean formEditable = true;
	private boolean canFillForm = false;
	private boolean editExistingAgOnly = false;

	private String beanName;

	public void updateMainGrid(HtmlPanelGrid mainGrid, TrialForm tf,
			Boolean[] renderFlags) {
		updateMainGrid(mainGrid, tf, renderFlags, "TrialFormEditor", false);
	}

	public void updateMainGrid(HtmlPanelGrid mainGrid, TrialForm tf,
			Boolean[] renderFlags, String beanName, boolean editExistingAgOnly) {
		this.mainGrid = mainGrid;
		this.tf = tf;
		this.td = null;
		this.renderFlags = renderFlags;
		this.editExistingAgOnly = editExistingAgOnly;
		this.beanName = beanName;

		formEditable = tf.isEditable() && Identity.instance().hasPermission(tf.getTrial(), SpicsPermissions.EDIT_TRIAL_FORMS);
		canFillForm = false;

		update();
	}

	public LinkedList<ComponentMapKey> updateTrialDataEdit(
			HtmlPanelGrid tsGrid, TrialForm tf, TrialData td,
			Boolean[] renderFlags, String beanName) {
		this.mainGrid = tsGrid;
		this.tf = tf;
		this.td = td;
		this.renderFlags = renderFlags;
		this.beanName = beanName;

		formEditable = false;
		canFillForm = tf.isFillable();

		keyList = new LinkedList<ComponentMapKey>();

		update();

		return keyList;
	}
	
	public LinkedList<ComponentMapKey> updateTrialDataEdit(
			HtmlPanelGrid tsGrid, TrialForm tf, TrialData td,
			Boolean[] renderFlags, String beanName, boolean canFill) {
		this.mainGrid = tsGrid;
		this.tf = tf;
		this.td = td;
		this.renderFlags = renderFlags;
		this.beanName = beanName;

		formEditable = false;
		canFillForm = canFill;

		keyList = new LinkedList<ComponentMapKey>();

		update();

		return keyList;
	}

	@SuppressWarnings("unchecked")
	private void update() {

		this.tf = trialformDAO.merge(tf); // reattach

		log.info("generating HtmlPanelGrid for bean " + beanName);

		if (mainGrid == null) {

			log.info("maingrid null, nothing to do here");

			return;

		}

		List<AttributeGroup> agList = tf.getAttributeGroups();

		Collections.sort(agList);

		log.info("ready to render " + agList.size() + " attribute groups");

		mainGrid.setColumns(1);
		mainGrid.setStyleClass("attgroup_overall");
		List mainKids = mainGrid.getChildren();
		mainKids.clear();

		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();

		if (agList.size() == 0) { // no AttributeGroups have been defined yet
			HtmlOutputText noAgTxt = new HtmlOutputText();
			noAgTxt.setId("noAgTxt");
			noAgTxt.setValue(messages.get("yetnoforms.info"));
			mainKids.add(noAgTxt);
		}

		for (AttributeGroup ag : agList) { // iterate over all attribute groups

			HtmlPanelGrid attributeGroupGrid = new HtmlPanelGrid();
			attributeGroupGrid.setId("viewAgTable" + ag.getSort());
			attributeGroupGrid.setColumns(1);
			attributeGroupGrid.setBorder(0);
			attributeGroupGrid.setCellspacing("0");
			attributeGroupGrid.setCellpadding("0");
			attributeGroupGrid.setStyleClass("overall_group_table");
			String rowClasses = "";
			if (ag.getTrialForm().getTrialSpecific()) {
				rowClasses += "group_header_critical,group_body_critical,group_body_critical";
			} else {
				rowClasses += "group_header,group_body,group_body";
			}
			attributeGroupGrid.setRowClasses(rowClasses);

			/* ********** begin AG header *********** */
			HtmlPanelGrid headerGrid = (HtmlPanelGrid) app
					.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
			headerGrid.setColumns(5);
			headerGrid.setId("viewAgHeader" + ag.getSort());
			headerGrid.setBorder(0);
			headerGrid.setCellspacing("0");
			headerGrid.setCellpadding("0");
			headerGrid.setStyleClass("group_header");
			headerGrid
					.setColumnClasses("left_active,middle_lock_active,middle_active,right_move,right_active");

			// arrow (for collapsing/expanding)
			HtmlCommandLink arrowLink = new HtmlCommandLink();
			arrowLink.setId("arrowLink" + ag.getSort());

			HtmlGraphicImage arrow = new HtmlGraphicImage();
			arrow.setId("arrowImg" + ag.getSort());

			if (renderFlags[ag.getSort()]) {
				arrowLink.setTitle(messages.get("button.reduce"));
				arrow.setAlt(messages.get("button.reduce"));
				arrow.setValue("/graphics/icon/opened.gif");
			} else {
				arrowLink.setTitle(messages.get("button.expand"));
				arrow.setAlt(messages.get("button.expand"));
				arrow.setValue("/graphics/icon/closed.gif");
			}

			arrowLink.getChildren().add(arrow);

			// create method binding
			arrowLink.setActionExpression(compFactory
					.createMethodExpression("#{" + beanName
							+ ".toggleRendering(" + ag.getSort() + ")}", new Class[] {ag.getSort().getClass()}));

			List headerKids = headerGrid.getChildren();
			headerKids.add(arrowLink);

			// secure lock symbol
			if (ag.getSecure()) {
				HtmlGraphicImage lock = new HtmlGraphicImage();
				lock.setId("secureImg" + ag.getSort());
				lock.setAlt(messages.get("label.confidential"));
				lock.setTitle(messages.get("label.confidential"));
				lock.setValue("/graphics/icon/lock.gif");
				headerKids.add(lock);
			} else {
				HtmlGraphicImage group = new HtmlGraphicImage();
				group.setId("groupImg" + ag.getSort());
				group.setValue("/graphics/icon/group.gif");
				headerKids.add(group);
			}

			// display AG name
			HtmlOutputText agName = new HtmlOutputText();
			agName.setValue(ag.getName());
			agName.setId("agName" + ag.getSort());
			headerKids.add(agName);

			/*
			 * HtmlGraphicImage empty_with_width = new HtmlGraphicImage();
			 * empty_with_width.setWidth("200");
			 * empty_with_width.setHeight("1");
			 * empty_with_width.setUrl("/graphics/design/spacer.gif");
			 */

			// move up/down buttons
			if (editExistingAgOnly || !formEditable) {
				headerKids.add(getEmptyOutputText());
			} else { // can be moved up or down
				HtmlPanelGrid moveGrid = new HtmlPanelGrid();
				moveGrid.setColumns(2);
				moveGrid.setId("moveTable" + ag.getSort());
				moveGrid.setStyleClass("bn_updown");
				moveGrid.setColumnClasses("bn_up,bn_down");
				moveGrid.setCellpadding("0");
				moveGrid.setCellspacing("0");

				if (ag.getSort() > 0) { // can be moved up
					moveGrid.getChildren().add(createTrialFormEditorMoveUp(ag));
				} else {
					moveGrid.getChildren().add(getEmptyOutputText());
				}

				if (tf.getAGbySortID(ag.getSort() + 1) != null) { // can be
																	// moved
																	// down

					moveGrid.getChildren().add(
							createTrialFormEditorMoveDown(ag));

				} else {
					moveGrid.getChildren().add(getEmptyOutputText());
				}

				headerKids.add(moveGrid);
			}

			// edit and delete buttons
			HtmlPanelGrid editDeleteGrid = new HtmlPanelGrid();
			editDeleteGrid.setId("editDeleteTable" + ag.getSort());
			editDeleteGrid.setColumns(2);
			headerKids.add(editDeleteGrid);

			if (editExistingAgOnly || !formEditable) {
				editDeleteGrid.getChildren().add(getEmptyOutputText());
			} else { // generate delete button
				editDeleteGrid.getChildren().add(
						createTrialFormEditorDeleteSmall(ag));
			}

			if (formEditable) { // generate edit button
				editDeleteGrid.getChildren().add(
						createTrialFormEditorBearbSmall(ag));
			} else {
				editDeleteGrid.getChildren().add(getEmptyOutputText());
			}

			attributeGroupGrid.getChildren().add(headerGrid);
			/* ********** end header ************ */

			/* ********** begin AG description ******** */

			HtmlPanelGrid descrGrid = new HtmlPanelGrid();
			descrGrid.setId("descTable" + ag.getSort());
			descrGrid.setColumns(1);
			descrGrid.setBorder(0);
			descrGrid.setCellspacing("0");
			descrGrid.setCellpadding("0");
			if (ag.getTrialForm().getTrialSpecific())
				descrGrid.setStyleClass("middle_critical");
			else
				descrGrid.setStyleClass("middle");

			descrGrid.setColumnClasses("info");

			descrGrid.setRendered(renderFlags[ag.getSort()]);

			// description text
			HtmlOutputText description = new HtmlOutputText();
			description.setId("descrTxt" + ag.getSort());
			// edited by sbachl
			String descr = messages.get("recommended.info");
			if (ag.getDescription() != null) {
				descr = ag.getDescription() + " "
						+ messages.get("recommended.info");
			}
			if (ag.getTrialForm().getTrialSpecific()) {
				descr = messages.get("casedata.info") + " "
						+ messages.get("recommended.info");
			}

			// end edited by sbachl
			description.setValue(descr);
			descrGrid.getChildren().add(description);
			attributeGroupGrid.getChildren().add(descrGrid);

			/* ********** end AG description ********* */

			/* ************ begin AG body ********** */
			HtmlPanelGrid subGrid = new HtmlPanelGrid();
			subGrid.setId("bodyTable" + ag.getSort());
			subGrid.setColumns(3);
			subGrid.setBorder(0);
			subGrid.setCellspacing("0");
			subGrid.setCellpadding("0");
			subGrid.setRendered(renderFlags[ag.getSort()]);
			subGrid
					.setColumnClasses("bottom_first_column,bottom_second_column,bottom_fourth_column");
			subGrid.setRowClasses("bottom_row");
			if (ag.getTrialForm().getTrialSpecific())
				subGrid.setStyleClass("bottom_critical");
			else
				subGrid.setStyleClass("bottom_normal");

			List subKids = subGrid.getChildren();

			List<Attribute> attList = ag.getAttributes();
			Collections.sort(attList);

			for (Attribute att : attList) { // iterate over attributes
											// themselves

				HtmlPanelGrid unitAndDecorateGrid = new HtmlPanelGrid();
				unitAndDecorateGrid.setId("unitAndDecorateGrid_" + ag.getSort() + "_" + att.getSort());
				unitAndDecorateGrid.setColumns(3);

				HtmlOutputText label = new HtmlOutputText();
				label.setId("attrLbl_" + ag.getSort() + "_" + att.getSort());
				String name = att.getName() + ":"
						+ (att.getRecommended() ? " *" : ""); // mark
																// recommended
				label.setValue(name);
				subKids.add(label);
				
				UIComponent comp = null;
				ComponentMapKey key = null;
				if(td != null) {
					key = new ComponentMapKey(att.getId(), td
						.getId(), null, ag.getSort(), att.getSort());
						keyList.add(key);
					comp = compFactory.getComponentWithExpressions(att
						.getFormElement(), !canFillForm, beanName, key.getKeyString());
				} else {
					comp = compFactory.getComponentFromFormElement(att.getFormElement(), !canFillForm);
				}
				
				// set id of component
				comp.setId("attrComp_" + ag.getSort() + "_" + att.getSort());
				
				// label to display name of field in converter message
				comp.getAttributes().put("label", att.getName());

				if(td != null)
					key.setComponentName(comp.getClientId(FacesContext.getCurrentInstance()));
				
				String decoAroundId = null;
				
				if (td != null) { // trial data exists

					setConverter(comp, att.getFormElement().getDataType());
					
					/* start around decorate */
					WarningAwareDecorate decoErrorAround = new WarningAwareDecorate();
					decoAroundId = "decoAround" + ag.getSort() + "_" + att.getSort();
					decoErrorAround.setId(decoAroundId);
					
					HtmlSpan aroundErrorSpan = new HtmlSpan();
					aroundErrorSpan.setId("decoAroundErrorSpan" + ag.getSort() + "_" + att.getSort());
					aroundErrorSpan.setStyleClass("errors");
					decoErrorAround.getFacets().put("aroundInvalidField",
							aroundErrorSpan);
					
					HtmlSpan aroundWarningSpan = new HtmlSpan();
					aroundWarningSpan.setId("decoAroundWarnSpan"+ ag.getSort() + "_" + att.getSort());
					aroundWarningSpan.setStyleClass("warnings");
					decoErrorAround.getFacets().put("aroundWarningField",
							aroundWarningSpan);

					decoErrorAround.getChildren().add(comp);

					unitAndDecorateGrid.getChildren().add(decoErrorAround);
					/* end around decorate */

				} else { // no trialdata, so just for viewing/editing the
							// form itself
					unitAndDecorateGrid.getChildren().add(comp);
				}

				if (att.getUnit() == null) { // no unit there
					unitAndDecorateGrid.getChildren().add(getEmptyOutputText());

				} else { // we've got a unit
					HtmlOutputText unitTf = new HtmlOutputText();
					unitTf.setId("unit" + ag.getSort() + "_" + att.getSort());
					unitTf.setValue(att.getUnit());

					unitAndDecorateGrid.getChildren().add(unitTf);
				}
				
				if(td != null) {	/* add message decorator after unit */
					WarningAwareDecorate decoAfter = new WarningAwareDecorate(); 
					decoAfter.setId("decoAfter" + ag.getSort() + "_" + att.getSort());
					
					String forId = null;
					if (comp instanceof HtmlPanelGroup) {
						// TODO: i think this is deprecated - we are not using seam datepicker anymore!
						HtmlPanelGroup dateGrid = (HtmlPanelGroup) comp;
						forId = ((UIComponent) dateGrid.getChildren().get(0))
								.getId();
						decoAfter.setFor(((UIComponent) dateGrid.getChildren().get(
								0)).getId());
					} else {
						forId = comp.getId();	// TODO: this is incomplete and needs a custom search in WarningAwareDecorate
						log.info("warningawaredecorator created for component with id #0", forId);
						decoAfter.setFor(forId);
					}
	
					HtmlSpan decoErrorMsgSpan = new HtmlSpan();
					decoErrorMsgSpan.setId("decoErrorMsgSpan" + ag.getSort() + "_" + att.getSort());
					decoErrorMsgSpan.setStyleClass("errorMsg");
					HtmlOutputText errorMsgLabel = new HtmlOutputText();
					errorMsgLabel.setId("errorMsgLbl"+ ag.getSort() + "_" + att.getSort());
					errorMsgLabel.setValue(messages.get("error") + ": ");
					decoErrorMsgSpan.getChildren().add(errorMsgLabel);
					HtmlMessage errorMsg = new HtmlMessage();
					errorMsg.setFor(forId);
					errorMsg.setId("errorMsg" + ag.getSort() + "_" + att.getSort());
					decoErrorMsgSpan.getChildren().add(errorMsg);
					decoAfter.getFacets()
							.put("afterInvalidField", decoErrorMsgSpan);
	
					HtmlSpan decoWarnMsgSpan = new HtmlSpan();
					decoWarnMsgSpan.setId("decoWarnMsgSpan"+ ag.getSort() + "_" + att.getSort());
					decoWarnMsgSpan.setStyleClass("warnMsg");
					HtmlOutputText warnMsgLabel = new HtmlOutputText();
					warnMsgLabel.setId("warnMsgLbl" + ag.getSort() + "_" + att.getSort());
					warnMsgLabel.setValue(messages.get("warn") + ": ");
					decoWarnMsgSpan.getChildren().add(warnMsgLabel);
					HtmlMessage warnMsg = new HtmlMessage();
					warnMsg.setId("warnMsg" + + ag.getSort() + "_" + att.getSort());
					warnMsg.setFor(forId);
					decoWarnMsgSpan.getChildren().add(warnMsg);
					decoAfter.getFacets().put("afterWarningField", decoWarnMsgSpan);
	
					unitAndDecorateGrid.getChildren().add(decoAfter);
				} else { /* if td == null */
					unitAndDecorateGrid.getChildren().add(getEmptyOutputText());
				}
				subKids.add(unitAndDecorateGrid);

				// display image comment if available
				if(att.getImageComment() != null) {
					org.jboss.seam.ui.component.html.HtmlGraphicImage imageComment = new org.jboss.seam.ui.component.html.HtmlGraphicImage();
					imageComment.setId("imageComment_" + ag.getSort() + "_" + att.getSort());
					imageComment.setValue(att.getImageComment().getData());
					imageComment.setFileName(att.getImageComment().getFileName());
					
					// restrict size of image comment
					final int MAX_WIDTH = 250;
					if(att.getImageComment().getWidth() > MAX_WIDTH) {
						HtmlTransformImageSize transform = new HtmlTransformImageSize();
						transform.setWidth(MAX_WIDTH);
						transform.setMaintainRatio(true);
						imageComment.getChildren().add(transform);
					}
					subKids.add(imageComment);
				} else {
					subKids.add(getEmptyOutputText());
				}
				

			}

			// create Edit button
			if (formEditable) {
				subKids.add(createTrialFormBearbeiten(ag));

				subKids.add(getEmptyOutputText());
			}

			attributeGroupGrid.getChildren().add(subGrid);
			mainKids.add(attributeGroupGrid);
			/* *************** end Ag body **************** */

		}

		if (formEditable && !editExistingAgOnly) {
			// neue AG hinzufuegen
			HtmlPanelGrid addAgGrid = new HtmlPanelGrid();
			addAgGrid.setId("addAgTable");
			addAgGrid.setColumns(5);
			addAgGrid.setCellspacing("0");
			addAgGrid.setStyleClass("group_header");
			addAgGrid
					.setColumnClasses("left_active,middle_active,middle_active,middle_active,right_active");

			HtmlGraphicImage addAgArrowImg = new HtmlGraphicImage();
			addAgArrowImg.setId("addAgArrowImg");
			addAgArrowImg.setAlt(messages.get("label.newgroup"));
			addAgArrowImg.setValue("/graphics/icon/closed_disabled.gif");

			addAgGrid.getChildren().add(addAgArrowImg);

			HtmlInputText addAgTf = new HtmlInputText();
			addAgTf.setOnclick("javascript:if(this.value=='"
					+ messages.get("label.newgroup") + "'){this.value=''};");
			addAgTf.setValue(messages.get("label.newgroup"));
			addAgTf.setOnblur("javascript:if(this.value==''){this.value='"
					+ messages.get("label.newgroup") + "';}");
			addAgTf.setId("addAgTf");
			addAgTf
					.setValueExpression(
							"value",
							compFactory
									.createValueExpression("#{TrialFormEditor.newAttributeGroupName}"));

			addAgGrid.getChildren().add(addAgTf);

			if (tf.getAttributeGroups().size() > 0) { // only if there is a
														// group yet
				HtmlOutputText nachOt = new HtmlOutputText();
				nachOt.setId("addAgNachTxt");
				nachOt.setValue(messages.get("label.after"));
				addAgGrid.getChildren().add(nachOt);

				HtmlSelectOneMenu nachMenu = new HtmlSelectOneMenu();
				nachMenu.setId("addAgNachDd");
				UISelectItems items = new UISelectItems();
				items.setId("addAgNachDdItems");
				LinkedList<SelectItem> selectList = new LinkedList<SelectItem>();

				for (AttributeGroup ag : tf.getAttributeGroups()) {
					SelectItem item = new SelectItem();
					item.setLabel(ag.getName());
					item.setValue(ag.getSort().toString());
					selectList.add(item);
				}
				items.setValue(selectList);
				nachMenu.getChildren().add(items);
				nachMenu.setValueExpression("value", compFactory.createValueExpression("#{TrialFormEditor.newAGnach}"));
				addAgGrid.getChildren().add(nachMenu);

			} else { // if there is no group
				HtmlOutputText nach_dummy1 = new HtmlOutputText();
				nach_dummy1.setId("addAgNachDummy1");
				nach_dummy1.setValue("");
				HtmlOutputText nach_dummy2 = new HtmlOutputText();
				nach_dummy2.setId("addAgNachDummy2");
				nach_dummy2.setValue("");
				addAgGrid.getChildren().add(nach_dummy1);
				addAgGrid.getChildren().add(nach_dummy2);
			}

			HtmlCommandButton addButton = new HtmlCommandButton();
			addButton.setId("addAgBtn");
			addButton.setTitle(messages.get("button.add"));
			addButton.setValue(messages.get("button.add"));
			addButton.setStyleClass("button");
			addButton.setStyle("font-weight:bold");

			addButton.setActionExpression(compFactory
					.createMethodExpression("#{TrialFormEditor.createAg}"));
			addAgGrid.getChildren().add(addButton);

			mainKids.add(addAgGrid);
		}

	}

	/* *********** helper methoden ************ */

	@SuppressWarnings("unused")
	private HtmlCommandButton createTrialFormLoeschen(AttributeGroup ag) {
		HtmlCommandButton delete = new HtmlCommandButton();
		delete.setId("agDeleteBig" + ag.getSort());
		delete.setTitle(messages.get("button.delete"));
		delete.setValue(messages.get("button.delete"));
		delete.setStyleClass("button");

		delete.setActionExpression(compFactory
				.createMethodExpression("#{TrialFormEditor.deleteAg(" + ag.getSort() + ")}", new Class[] {ag.getSort().getClass()}));

		return delete;
	}

	private HtmlCommandButton createTrialFormBearbeiten(AttributeGroup ag) {
		HtmlCommandButton bearbeiten = new HtmlCommandButton();
		bearbeiten.setId("agEditBig" + ag.getSort());
		bearbeiten.setTitle(messages.get("button.edit"));
		bearbeiten.setValue(messages.get("button.edit"));
		bearbeiten.setStyleClass("button");

		bearbeiten.setActionExpression(compFactory.createMethodExpression("#{"
				+ beanName + ".editAttributeGroup(" + ag.getId() + ")}", new Class[] {ag.getId().getClass()}));

		return bearbeiten;
	}

	@SuppressWarnings("unchecked")
	private HtmlCommandLink createTrialFormEditorDeleteSmall(AttributeGroup ag) {
		HtmlCommandLink deleteSmall = new HtmlCommandLink();
		deleteSmall.setId("viewAgDeleteSmLink" + ag.getSort());
		HtmlGraphicImage deleteSmImg = new HtmlGraphicImage();
		deleteSmImg.setId("viewAgDeleteSmImg" + ag.getSort());
		deleteSmImg.setAlt(messages.get("button.delete"));
		deleteSmImg.setTitle(messages.get("button.delete"));
		deleteSmImg.setValue("/graphics/button/delete_small.png");
		deleteSmImg
				.setOnmouseout("this.src=\"./graphics/button/delete_small.png\"");
		deleteSmImg
				.setOnmouseover("this.src=\"./graphics/button/delete_small_hover.png\"");
		deleteSmall.setOnclick("if (!confirm('"
				+ messages.get("deletegroup.question") + "')) return false");

		deleteSmall.getChildren().add(deleteSmImg);

		deleteSmall.setActionExpression(compFactory
				.createMethodExpression("#{TrialFormEditor.deleteAg(" + ag.getSort() + ")}", new Class[] { ag.getSort().getClass() }));

		return deleteSmall;
	}

	@SuppressWarnings("unchecked")
	private HtmlCommandLink createTrialFormEditorBearbSmall(AttributeGroup ag) {
		HtmlCommandLink bearbSmall = new HtmlCommandLink();
		bearbSmall.setId("agEditSmLink" + ag.getSort());
		HtmlGraphicImage bearbSmImg = new HtmlGraphicImage();
		bearbSmImg.setId("agEditSmImg" + ag.getSort());
		bearbSmImg.setAlt(messages.get("button.edit"));
		bearbSmImg.setTitle(messages.get("button.edit"));
		bearbSmImg.setValue("/graphics/button/edit_small.png");
		bearbSmImg
				.setOnmouseout("this.src=\"./graphics/button/edit_small.png\"");
		bearbSmImg
				.setOnmouseover("this.src=\"./graphics/button/edit_small_hover.png\"");

		bearbSmall.getChildren().add(bearbSmImg);

		bearbSmall.setActionExpression(compFactory.createMethodExpression("#{"
				+ beanName + ".editAttributeGroup(" + ag.getId() + ")}", new Class[] {ag.getId().getClass()}));

		return bearbSmall;
	}

	@SuppressWarnings("unchecked")
	private HtmlCommandLink createTrialFormEditorMoveDown(AttributeGroup ag) {
		HtmlCommandLink moveDown = new HtmlCommandLink();
		moveDown.setId("moveDownLink" + ag.getSort());
		moveDown.setTitle(messages.get("button.movedown"));

		HtmlGraphicImage moveDownImg = new HtmlGraphicImage();
		moveDownImg.setId("moveDownImg" + ag.getSort());
		moveDownImg.setAlt(messages.get("button.movedown"));
		moveDownImg.setTitle(messages.get("button.movedown"));
		moveDownImg.setValue("/graphics/button/down_small.png");
		moveDownImg
				.setOnmouseover("this.src=\"./graphics/button/down_small_hover.png\"");
		moveDownImg
				.setOnmouseout("this.src=\"./graphics/button/down_small.png\"");
		moveDown.getChildren().add(moveDownImg);

		moveDown.setActionExpression(compFactory
				.createMethodExpression("#{TrialFormEditor.moveAgDown(" + ag.getSort() + ")}", new Class[] { ag.getSort().getClass()}));

		return moveDown;

	}

	@SuppressWarnings("unchecked")
	private HtmlCommandLink createTrialFormEditorMoveUp(AttributeGroup ag) {
		HtmlCommandLink moveUp = new HtmlCommandLink();
		moveUp.setId("moveUpLink" + ag.getSort());
		moveUp.setTitle(messages.get("button.moveup"));

		HtmlGraphicImage moveUpImg = new HtmlGraphicImage();
		moveUpImg.setId("moveUpImg" + ag.getSort());
		moveUpImg.setAlt(messages.get("button.moveup"));
		moveUpImg.setTitle(messages.get("button.moveup"));
		moveUpImg.setValue("/graphics/button/up_small.png");
		moveUpImg
				.setOnmouseover("this.src=\"./graphics/button/up_small_hover.png\"");
		moveUpImg.setOnmouseout("this.src=\"./graphics/button/up_small.png\"");
		moveUp.getChildren().add(moveUpImg);

		moveUp.setActionExpression(compFactory
				.createMethodExpression("#{TrialFormEditor.moveAgUp(" + ag.getSort() + ")}", new Class[] { ag.getSort().getClass()}));

		return moveUp;
	}

	private void setConverter(UIComponent comp, DATATYPE dataType) {
		switch (dataType) {
		case STR:
			break;
		case INTEGER:
			((UIInput) comp).setConverter(new IntegerConverter());
			break;
		case DECIMAL:
			((UIInput) comp).setConverter(new BigDecimalLocaleConverter());
			break;
		case DATE:
			// TODO: only needs converter for Display in Outputtext
			break;
		case BOOLEAN:
			((UIInput) comp).setConverter(new BooleanConverter());
			break;
		default:
			return;
		}

	}

	private HtmlOutputText getEmptyOutputText() {
		HtmlOutputText emptyOutputText = new HtmlOutputText();
		emptyOutputText.setValue(" ");
		emptyOutputText.setId(getUid());
		return emptyOutputText;
	}

	private String getUid() {
		return FacesContext.getCurrentInstance().getViewRoot().createUniqueId();
	}

}
