package dst2.ejb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import javax.naming.Context;
import javax.naming.InitialContext;

import dst2.ejb.interfaces.GeneralManagement;
import dst2.ejb.interfaces.JobManagement;
import dst2.ejb.interfaces.Testing;
import dst2.exceptions.ComputerNotFreeAnyMore;
import dst2.exceptions.LoginRequired;
import dst2.exceptions.NotEnoughFreeComputers;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			List<String> paramList = new ArrayList<String>();
			paramList.add("a=1");
			paramList.add("x*4");
			paramList.add("[t]");
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			Context ctx = new InitialContext();
			
			System.out.println("===================================");
			System.out.println(" Starting Client (dst2_0426099)");
			System.out.println("===================================");
			
			System.out.println("\n-----------------------------------");
			System.out.println("Inserting TestData with TestingBean:\n");
			
			Testing testingBean = (Testing)ctx.lookup("java:global/dst2_1/TestingBean"); 
			testingBean.insertTestData();
			
			System.out.println("\nHit Enter to continue");
			
			stdIn.readLine();
			
			System.out.println("\n-----------------------------------");
			System.out.println("Inserting PriceSteps:\n");
			
			GeneralManagement generalBean = (GeneralManagement)ctx.lookup("java:global/dst2_1/GeneralManagementBean"); 
			System.out.println(generalBean.storePriceStep(2, new BigDecimal(10)));
			System.out.println(generalBean.storePriceStep(5, new BigDecimal(7)));
			System.out.println(generalBean.storePriceStep(10, new BigDecimal(5)));
			System.out.println(generalBean.storePriceStep(50, new BigDecimal(3)));
			System.out.println(generalBean.storePriceStep(100, new BigDecimal(1)));
			
			System.out.println("\nHit Enter to continue");
			
			stdIn.readLine();
			
			System.out.println("\n-----------------------------------");
			System.out.println("Job Management 1:\n");
			
			JobManagement jobBean = (JobManagement)ctx.lookup("java:global/dst2_1/JobManagementBean"); 
			System.out.println("Invalid Login:");
			System.out.println("usr:wrong, pswd:no - "+jobBean.login("wrong", "no"));
			
			System.out.println("\nValid Login:");
			System.out.println("usr:Phillip9507, pswd:9f3797 - "+jobBean.login("Phillip9507", "9f3797"));
			System.out.println("\nJob Amount (grid1): "+jobBean.getJobAmount(1l));
			
			System.out.println("\nAdd Jobs:");
			try {
				System.out.println("Grid 1, 1 cpus");
				Collections.shuffle(paramList);
				jobBean.addJob(1l, 1, "Worklow1", paramList);
			} catch (NotEnoughFreeComputers e) {
				System.out.println("Not enough cpus for job:" +e.getMessage());
			}
			try {
				System.out.println("Grid 1, 2 cpus");
				Collections.shuffle(paramList);
				jobBean.addJob(1l, 2, "Worklow2", paramList);
			} catch (NotEnoughFreeComputers e) {
				System.out.println("Not enough cpus for job:" +e.getMessage());
			}
			try {
				System.out.println("Grid 1, 4 cpus");
				Collections.shuffle(paramList);
				jobBean.addJob(1l, 4, "Worklow3", paramList);
			} catch (NotEnoughFreeComputers e) {
				System.out.println("Not enough cpus for job:" +e.getMessage());
			}
			
			System.out.println("Job Amount (grid1): "+jobBean.getJobAmount(1l));
			
			try {
				System.out.println("Try to finalize jobs:");
				jobBean.buyAndStartJobs();
				System.out.println("Done!");
			} catch (LoginRequired e) {
				System.out.println("Must login befor buying: " +e.getMessage());
			} catch (ComputerNotFreeAnyMore e) {
				System.out.println("One scheduled computer seems to be reserved now: " +e.getMessage());
			}
			
			System.out.println("\nHit Enter to continue");
			
			stdIn.readLine();
			
			
			
			System.out.println("\n-----------------------------------");
			System.out.println("Job Management 2:\n");
			
			JobManagement jobBean2 = (JobManagement)ctx.lookup("java:global/dst2_1/JobManagementBean"); 
			System.out.println("\nValid Login:");
			
			System.out.println("usr:Ivan9476, pswd:51ad7ef - "+jobBean2.login("Ivan9476", "51ad7ef"));
			System.out.println("\nJob Amount (grid1): "+jobBean2.getJobAmount(1l));
			System.out.println("Job Amount (grid2): "+jobBean2.getJobAmount(2l));
			
			System.out.println("\nAdd Jobs:");
			try {
				System.out.println("Grid 2, 60 cpus");
				Collections.shuffle(paramList);
				jobBean2.addJob(2l, 60, "Worklow2_1", paramList);
			} catch (NotEnoughFreeComputers e) {
				System.out.println("Not enough cpus for job:" +e.getMessage());
			}
			try {
				System.out.println("Grid 2, 55 cpus");
				Collections.shuffle(paramList);
				jobBean2.addJob(2l, 55, "Worklow2_2", paramList);
			} catch (NotEnoughFreeComputers e) {
				System.out.println("Not enough cpus for job:" +e.getMessage());
			}
			try {
				System.out.println("Grid 2, 10 cpus");
				Collections.shuffle(paramList);
				jobBean2.addJob(2l, 10, "Worklow2_3", paramList);
			} catch (NotEnoughFreeComputers e) {
				System.out.println("Not enough cpus for job:" +e.getMessage());
			}
			
			System.out.println("\nJob Amount (grid2): "+jobBean2.getJobAmount(2l));
			System.out.println("Remove jobs from grid 2.");
			jobBean2.removeJobsFromList(2l);
			System.out.println("Job Amount (grid2): "+jobBean2.getJobAmount(2l));
			
			
			
			try {
				System.out.println("Try to finalize jobs:");
				jobBean2.buyAndStartJobs();
				System.out.println("Done!");
			} catch (LoginRequired e) {
				System.out.println("Must login befor buying: " +e.getMessage());
			} catch (ComputerNotFreeAnyMore e) {
				System.out.println("One scheduled computer seems to be reserved now: " +e.getMessage());
			}
			
			
			System.out.println("\n-----------------------------------");
			System.out.println("Wait 15 sec for jobs to end...\n");
			
			Thread.sleep(15000l);
			
			System.out.println("\nHit Enter to continue");
			
			stdIn.readLine();
			
			
			System.out.println("\n-----------------------------------");
			System.out.println("Asynchronus Bill Invocation:\n");
			
			Future<String> futs = generalBean.createBill("Phillip9507");
			
			System.out.println(futs.get());
			
			System.out.println("\nHit Enter to continue");
			
			stdIn.readLine();
			
			
			System.out.println("\n-----------------------------------");
			System.out.println("Show AuditRecords:\n");
			
			System.out.println(generalBean.getAllAuditRecords().toString());
			
			System.out.println("===================================");
			System.out.println(" Client finished!");
			System.out.println("===================================");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
