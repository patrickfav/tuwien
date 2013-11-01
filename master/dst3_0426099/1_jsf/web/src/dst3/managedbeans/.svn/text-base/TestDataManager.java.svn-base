package dst3.managedbeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dst2.ejb.interfaces.Testing;
import dst3.util.I18nUtil;

@ManagedBean
@SessionScoped
public class TestDataManager {
	
	private boolean insertActive = true;
	private boolean removeActive = false;
	
	@EJB
	Testing testDataBean;
	
	public String insert() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.successfully_insertedTestData"), null));
		
		testDataBean.insertTestData();
		
		insertActive = false;
		removeActive = true;
		
		return "home.xhtml";
	}
	
	public String remove() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.successfully_removedTestData"), null));
		
		testDataBean.deleteAll();
		
		insertActive = true;
		removeActive = false;
		
		return "home.xhtml";
	}
	
	
	/* 
	 * 
	 * GETTER AND SETTER 
	 * 
	 */
	
	public boolean isInsertActive() {
		return insertActive;
	}

	public void setInsertActive(boolean insertActive) {
		this.insertActive = insertActive;
	}

	public boolean isRemoveActive() {
		return removeActive;
	}

	public void setRemoveActive(boolean removeActive) {
		this.removeActive = removeActive;
	}
	
	
}
