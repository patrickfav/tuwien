package dst2.ejb.interfaces;

import java.util.List;

import javax.ejb.Remote;

import dst2.exceptions.ComputerNotFreeAnyMore;
import dst2.exceptions.LoginRequired;
import dst2.exceptions.NotEnoughFreeComputers;

@Remote
public interface JobManagement {
	
	public boolean login(String usr, String psw);
	public void addJob(Long gridId,int cpus,String workflow, List<String> param) throws NotEnoughFreeComputers;
	public void buyAndStartJobs() throws LoginRequired, ComputerNotFreeAnyMore;
	public void removeJobsFromList(Long gridId);
	public int getJobAmount(Long gridId);
	public void logout();
}
