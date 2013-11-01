

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.JButtonTester;

import junit.extensions.abbot.ComponentTestFixture;

/**
 * @author Favre-Bulle, Patrick
 * @matNum 0426099
 * @exercise Exercise 2-2
 * 
 */
public class JCalcTest extends ComponentTestFixture {
	
	private JFrame f;
	private JButtonTester bt;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private JButton button0;
	private JButton buttonPlus;
	private JButton buttonMinus;
	private JButton buttonDiv;
	private JButton buttonMult;
	private JButton buttonEquals;
	private JTextField tf;
	
	public void setUp() {
		f = new CalculatorFrame("JCalculator");
		f.setSize(375,275);
		f.setVisible(true);
		
		try {
			button1 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("1");
						}
					});
			button2 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("2");
						}
					});
			button3 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("3");
						}
					});
			button4 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("4");
						}
					});
			button5 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("5");
						}
					});
			button6 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("6");
						}
					});
			button7 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("7");
						}
					});
			button8 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("8");
						}
					});
			button9 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("9");
						}
					});
			button0 = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("0");
						}
					});
			buttonPlus = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("+");
						}
					});
			buttonMinus = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("-");
						}
					});
			buttonMult = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("*");
						}
					});
			buttonDiv = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("/");
						}
					});
			buttonEquals = (JButton) getFinder().find(
					new ClassMatcher(JButton.class) {
						public boolean matches(Component c) {
							return super.matches(c)
									&& ((JButton) c).getText().equals("=");
						}
					});
			
			tf = (JTextField) getFinder().find(new ClassMatcher(JTextField.class));
		} catch (ComponentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MultipleComponentsFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bt = new JButtonTester();
	}
	
	public void testAdd() {
		bt = new JButtonTester();
		bt.actionClick(button1);
		bt.actionClick(buttonPlus);
		bt.actionClick(button2);
		bt.actionClick(buttonEquals);
		assertEquals("3", tf.getText());
	}
	
	public void testSub() {
		bt.actionClick(button4);
		bt.actionClick(button6);
		bt.actionClick(button8);
		bt.actionClick(button2);
		bt.actionClick(buttonMinus);
		bt.actionClick(button1);
		bt.actionClick(button3);
		bt.actionClick(button9);
		bt.actionClick(button7);
		bt.actionClick(buttonEquals);
		assertEquals("3285", tf.getText());
	}
	
	public void testMult() {
		bt.actionClick(button1);
		bt.actionClick(button1);
		bt.actionClick(button4);
		bt.actionClick(button7);
		bt.actionClick(buttonMult);
		bt.actionClick(button9);
		bt.actionClick(button5);
		bt.actionClick(button8);
		bt.actionClick(buttonEquals);
		assertEquals("1098826", tf.getText());
	}
	
	public void testDiv() {
		bt.actionClick(button4);
		bt.actionClick(button5);
		bt.actionClick(button9);
		bt.actionClick(button8);
		bt.actionClick(buttonDiv);
		bt.actionClick(button2);
		bt.actionClick(buttonEquals);
		assertEquals("2299", tf.getText());
	}
	
	/* java -classpath fit.jar;bin fit.FileRunner spec\DoubleSpec_input.htm spec\DoubleSpec_output.htm */
}
