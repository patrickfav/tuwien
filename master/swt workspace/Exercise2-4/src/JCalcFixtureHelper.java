import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import abbot.finder.BasicFinder;
import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import abbot.finder.TestHierarchy;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.JButtonTester;

/**
 * @author Favre-Bulle, Patrick
 * @matNum 0426099
 * @exercise Exercise 2-4
 * 
 */
/* javac -d bin -cp bin;fit.jar;abbot.jar src\*.java

java -classpath fit.jar;bin;abbot.jar;junit-3.8.1.jar fit.FileRunner spec\input.htm spec\output.htm */
public class JCalcFixtureHelper {
	
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
	private BasicFinder finder;
	
	public JCalcFixtureHelper() throws ComponentNotFoundException, MultipleComponentsFoundException {
		finder = new BasicFinder(new TestHierarchy());
		
		f = new CalculatorFrame("JCalculator");
		f.setSize(375,275);
		f.setVisible(true);
		
		button1 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("1");
			}
		});
		button2 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("2");
			}
		});
		button3 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("3");
			}
		});
		button4 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("4");
			}
		});
		button5 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("5");
			}
		});
		button6 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("6");
			}
		});
		button7 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("7");
			}
		});
		button8 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("8");
			}
		});
		button9 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("9");
			}
		});
		button0 = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("0");
			}
		});
		buttonPlus = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("+");
			}
		});
		buttonMinus = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("-");
			}
		});
		buttonMult = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("*");
			}
		});
		buttonDiv = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("/");
			}
		});
		buttonEquals = (JButton) finder.find(new ClassMatcher(JButton.class) {
			public boolean matches(Component c) {
				return super.matches(c) && ((JButton) c).getText().equals("=");
			}
		});

		tf = (JTextField) finder.find(new ClassMatcher(JTextField.class));
		
		bt = new JButtonTester();
	}

	public JFrame getF() {
		return f;
	}

	public JButtonTester getBt() {
		return bt;
	}

	public JButton getButton1() {
		return button1;
	}

	public JButton getButton2() {
		return button2;
	}

	public JButton getButton3() {
		return button3;
	}

	public JButton getButton4() {
		return button4;
	}

	public JButton getButton5() {
		return button5;
	}

	public JButton getButton6() {
		return button6;
	}

	public JButton getButton7() {
		return button7;
	}

	public JButton getButton8() {
		return button8;
	}

	public JButton getButton9() {
		return button9;
	}

	public JButton getButton0() {
		return button0;
	}

	public JButton getButtonPlus() {
		return buttonPlus;
	}

	public JButton getButtonMinus() {
		return buttonMinus;
	}

	public JButton getButtonDiv() {
		return buttonDiv;
	}

	public JButton getButtonMult() {
		return buttonMult;
	}

	public JButton getButtonEquals() {
		return buttonEquals;
	}

	public JTextField getTf() {
		return tf;
	}

	public BasicFinder getFinder() {
		return finder;
	}
	
	
}
