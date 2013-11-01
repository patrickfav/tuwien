package dst3.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dst2.ejb.interfaces.JobManagement;
import dst2.exceptions.ComputerNotFreeAnyMore;
import dst2.exceptions.LoginRequired;
import dst2.exceptions.NotEnoughFreeComputers;
import dst3.util.I18nUtil;

@ManagedBean
@SessionScoped
public class SessionManager {
	
	@EJB
	private JobManagement jb;
	
	private String usr;
	private String psw;
	private boolean loggedIn =false;
	private int jobCpus;
	private String jobWorkflow;
	private String param0;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private Long gridId;
	
	public String login() {
		loggedIn = jb.login(usr, psw);
		
		if(loggedIn) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_INFO, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.login_successful"), null));
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_WARN, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.login_unsuccessful"), null));
		}
		
		return "home.xhtml";
	}
	
	public String logout() {
		loggedIn=false;
		jb.logout();
		
		return "home.xhtml";
	}
	
	public String addJob() {
		
		System.out.println("GRID ID DEBUG FOR JOB: "+gridId);
		
		List<String> list = new ArrayList<String>();
		if(param0 != null && !param0.equals("")) {
			list.add(param0);
		}
		if(param1 != null && !param1.equals("")) {
			list.add(param1);
		}
		if(param2 != null && !param2.equals("")) {
			list.add(param2);
		}
		if(param3 != null && !param3.equals("")) {
			list.add(param3);
		}
		if(param4 != null && !param4.equals("")) {
			list.add(param4);
		}
		
		try {
			jb.addJob(gridId, jobCpus, jobWorkflow, list);
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_INFO, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.successfully_inserted"), null));
			
		} catch (NotEnoughFreeComputers e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_WARN, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.notEnoughFreeComputers"), null));
			
		}
		
		
		return "overview.xhtml";
	}
	
	public String gotoJobPage(Long gridid) {
		System.out.println("SET GRID ID:"+gridid);
		this.gridId = gridid;
		return "job.xhtml";
	}
	
	public int getJobAmount(long gridId) {
		return jb.getJobAmount(gridId);
	}
	
	public String removeJobs(long gridId) {
		jb.removeJobsFromList(gridId);
		return "assignment.xhtml";
	}
	
	public String submitJobs() {
		try {
			jb.buyAndStartJobs();
		} catch (LoginRequired e) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_WARN, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.requireLogin"), null));
			
			
		} catch (ComputerNotFreeAnyMore e) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_WARN, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.notEnoughFreeComputers"), null));
			
		}
		
		FacesContext.getCurrentInstance().addMessage(
				null, new FacesMessage(FacesMessage.SEVERITY_INFO, I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.successfullySubmitJobs"), null));
		
		
		return "home.xhtml";
	}
	/* 
	 * 
	 * GETTER AND SETTER 
	 * 
	 */
	
	
	public String getUsr() {
		return usr;
	}

	public int getJobCpus() {
		return jobCpus;
	}

	public void setJobCpus(int jobCpus) {
		this.jobCpus = jobCpus;
	}

	public String getJobWorkflow() {
		return jobWorkflow;
	}

	public void setJobWorkflow(String jobWorkflow) {
		this.jobWorkflow = jobWorkflow;
	}

	public String getParam0() {
		return param0;
	}

	public void setParam0(String param0) {
		this.param0 = param0;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}


	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}


	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public void setGridId(Long gridId) {
		this.gridId = gridId;
	}

	public Long getGridId() {
		return gridId;
	}
	
	
}
