package dst3.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dst1.model.tasks.Job;
import dst3.dto.JobDTO;

@WebService
public class SearchBean {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@WebMethod
	public List<JobDTO> findJobs(String gName) throws EJBException{
		if(em.createQuery("select g from Grid g where g.name = :name").setParameter("name", gName).getResultList().size() <= 0) 
			throw new EJBException(gName+" is not a valid grid name");
		
		List<JobDTO> list = new ArrayList<JobDTO>();
		for(Job j: (List<Job>) em.createQuery("Select distinct exec.job from Grid g join g.cluster as cl join cl.computer as comp join comp.executions as exec where g.name = :name").setParameter("name", gName).getResultList()) {
			list.add(new JobDTO(j));
		}
		return list;
	}
}

/*
wsimport -Xnocompile -p dst3.ws -d "C:\Users\PatrickF\eclipse workspaces\master workspace\dst3_0426099\1_jsf\web\src"  -s src http://localhost:8080/dst3/SearchBeanService?wsdl
*/