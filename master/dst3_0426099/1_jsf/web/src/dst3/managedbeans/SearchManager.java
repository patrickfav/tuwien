package dst3.managedbeans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import dst3.util.I18nUtil;
import dst3.ws.JobDTO;
import dst3.ws.SearchBeanService;

@ManagedBean
public class SearchManager {
	
	private String text;
	private List<JobDTO> resultList;
	
	public String findJobs() {
		try {
			SearchBeanService sbs = new SearchBeanService();
			resultList = sbs.getSearchBeanPort().findJobs(text);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_WARN,I18nUtil.getInstance(FacesContext.getCurrentInstance()).getString("info.gridDoesNotExists") , null));
			
			
			return "search.xhtml";
		}
		
		return "result.xhtml";
	}
	

	
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setResultList(List<JobDTO> resultList) {
		this.resultList = resultList;
	}

	public List<JobDTO> getResultList() {
		return resultList;
	}
}
