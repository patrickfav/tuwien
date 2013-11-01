package bl;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import ticketline.bl.GuiMemory;
import ticketline.bl.Login;

/**
 * 
 * @author PatrickM
 * @version 0.1
 *
 */
public class LoginTest {
	private String correctUsername = "pTPrJhgZMs";
	private String correctPassword = "OEPy";
	private String incorrectUsername = "Test";
	private String incorrectPassword = "word";
	
	@After
	public void After() {
		GuiMemory.logout();
	}
	
	@Test
	public void login_logsInCorrect() {
		Login l = new Login(correctUsername, correctPassword);
		assertEquals(true, l.isLoggedIn());
	}
	
	@Test
	public void login_reactsCorrectlyToInvalidInput() {
		Login l1 = new Login(correctUsername, incorrectPassword);
		assertEquals(false, l1.isLoggedIn());
		
		Login l2 = new Login(incorrectUsername, correctPassword);
		assertEquals(false, l2.isLoggedIn());
		
		Login l3 = new Login(incorrectUsername, incorrectPassword);
		assertEquals(false, l3.isLoggedIn());
	}
}
