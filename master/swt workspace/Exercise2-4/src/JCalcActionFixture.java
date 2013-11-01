

import abbot.finder.ComponentNotFoundException;
import abbot.finder.MultipleComponentsFoundException;
import fit.ActionFixture;

/**
 * @author Favre-Bulle, Patrick
 * @matNum 0426099
 * @exercise Exercise 2-4
 * 
 */
public class JCalcActionFixture extends ActionFixture{
	private JCalcFixtureHelper jcfh;
	
	
	public JCalcActionFixture() throws ComponentNotFoundException, MultipleComponentsFoundException {
		jcfh = new JCalcFixtureHelper();
	}
	
	public void button1() {
		jcfh.getBt().actionClick(jcfh.getButton1());
	}
	
	public void button2() {
		jcfh.getBt().actionClick(jcfh.getButton2());
	}
	public void button3() {
		jcfh.getBt().actionClick(jcfh.getButton3());
	}
	public void button4() {
		jcfh.getBt().actionClick(jcfh.getButton4());
	}
	public void button5() {
		jcfh.getBt().actionClick(jcfh.getButton5());
	}
	public void button6() {
		jcfh.getBt().actionClick(jcfh.getButton6());
	}
	public void button7() {
		jcfh.getBt().actionClick(jcfh.getButton7());
	}
	public void button8() {
		jcfh.getBt().actionClick(jcfh.getButton8());
	}
	public void button9() {
		jcfh.getBt().actionClick(jcfh.getButton9());
	}
	public void button0() {
		jcfh.getBt().actionClick(jcfh.getButton0());
	}
	public void buttonMult() {
		jcfh.getBt().actionClick(jcfh.getButtonMult());
	}
	public void buttonMinus() {
		jcfh.getBt().actionClick(jcfh.getButtonMinus());
	}
	public void buttonPlus() {
		jcfh.getBt().actionClick(jcfh.getButtonPlus());
	}
	public void buttonDiv() {
		jcfh.getBt().actionClick(jcfh.getButtonDiv());
	}
	public void buttonEquals() {
		jcfh.getBt().actionClick(jcfh.getButtonEquals());
	}
	
	public String result() {
		return jcfh.getTf().getText();
	}
}
