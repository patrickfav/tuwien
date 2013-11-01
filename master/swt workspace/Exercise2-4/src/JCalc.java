import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JCalc{

	public static void main(String[] args){
	  JFrame f = new CalculatorFrame("JCalculator");
	  f.setSize(375,275);
	  f.setVisible(true);	
	}
}
	//Frame Class
	class CalculatorFrame extends JFrame{

	  private JTextField tf = new JTextField("0",28);
          private String previousNumber = "0";
          private String currentNumber = "0";
          private char lastOperator = 0;
          private boolean equalsHit = false;
          private boolean waiting = false;
      
	  public CalculatorFrame(String title){
	    super(title);
            Container contentPane = getContentPane();//get the context in which objects will be placed
            contentPane.setLayout(new BorderLayout());//sets LayoutManger of context

	    //Top Panel
            JPanel textField = new JPanel(new FlowLayout(FlowLayout.RIGHT));//Begin textField (top) Panel
	    tf.setHorizontalAlignment(JTextField.RIGHT);
	    tf.setEditable(false);
            textField.add(tf);//end textField Panel
            contentPane.add(textField, BorderLayout.NORTH);//add top Panel
	    OperationListener ol = new OperationListener();
	    //Buttons
	    JButton divide = new JButton("/");
	    divide.addActionListener(ol);
	    JButton square = new JButton("x^2");
	    square.addActionListener(ol);
	    JButton multiply = new JButton("*");
	    multiply.addActionListener(ol);
	    JButton exponentiate = new JButton("X^Y");
	    exponentiate.addActionListener(ol);
	    JButton subtract = new JButton("-");
	    subtract.addActionListener(ol);
	    JButton mod = new JButton("%");
	    mod.addActionListener(ol);
	    NumberListener nl = new NumberListener();
	    JButton decimal = new JButton(".");
	    decimal.addActionListener(nl);
	    JButton equals = new JButton("=");
	    equals.addActionListener(ol);
	    JButton add = new JButton("+");
	    add.addActionListener(ol);
	    JButton factorial = new JButton("!");
	    factorial.addActionListener(ol);
	    //End generation of Buttons
	    
	    //Center Panel
	    JPanel buttonField = new JPanel(new GridLayout(4,5,3,3));//Creates buttonField Panel 
	    for(int i = 7; i <= 9; i++){//Begin placement of Buttons in CENTER Panel
	      JButton temp = new JButton(i + "");;
	      temp.addActionListener(nl);
	      buttonField.add(temp);
    	    }//end for
	    buttonField.add(divide);
	    buttonField.add(square);
	    for(int i = 4; i <= 6; i++){
	      JButton temp = new JButton(i + "");
	      temp.addActionListener(nl);
	      buttonField.add(temp);
	    }//end for
	    buttonField.add(multiply);
	    buttonField.add(exponentiate);
	    for(int i = 1; i <= 3; i++){
	      JButton temp = new JButton(i + "");
	      temp.addActionListener(nl);
	      buttonField.add(temp);
	    }//end for
	    buttonField.add(subtract);
	    buttonField.add(mod);
	    JButton zero = new JButton("0");
	    zero.addActionListener(nl);
	    buttonField.add(zero);
	    buttonField.add(decimal);
	    buttonField.add(equals);
	    buttonField.add(add);
	    buttonField.add(factorial);
	    contentPane.add(buttonField,BorderLayout.CENTER);//End Placement of Buttons in CENTER Panel
	     
	    //Start Bottom Panel
	    JPanel control = new JPanel(new GridLayout(1,2,3,3));
	    JButton clear = new JButton("Clear");
            clear.addActionListener(new FunctionListener());
	    control.add(clear);
	    contentPane.add(control, BorderLayout.SOUTH);
	    //End Bottom Panel
	    addWindowListener(new WindowCloser());//activates close button
	}//end CalculatorFrame

	class WindowCloser extends WindowAdapter{ //Closes Window
	  public void windowClosing(WindowEvent evt){
	    System.exit(0);
	  }//end windowClosing
	}//end WindowCloser
    

	class NumberListener implements ActionListener{
	  public void actionPerformed(ActionEvent evt){
	      String userInput = evt.getActionCommand();
	      String currentDisplay = tf.getText();
	      if(!userInput.equals(".")){
	        if(equalsHit){
	          String output = userInput;
	          equalsHit = false;
	          waiting = false;
	          tf.setText(output);
	        }//end if
	        else if(waiting){
	          String output = userInput;
	          tf.setText(output);
	          waiting = false;
	        }//end if
	        else if(currentDisplay.equals("0"))
	          tf.setText(userInput);
	        else
	          tf.setText(currentDisplay + userInput);
	      }//end if
	      else{
	        if(equalsHit){
	          equalsHit = false;
	          waiting = false;
	          tf.setText("0.");
	        }//end if
	        else if(waiting){
	          waiting = false;
	   	  tf.setText("0.");
	        }//end if
	        else if(currentDisplay.equals("0"))
	          tf.setText(currentDisplay + ".");
	        else if(currentDisplay.indexOf(".") < 0)
	          tf.setText(currentDisplay + ".");
	      }//end else
	  }//end actionPerformed
	}//end NumberListener

	class FunctionListener implements ActionListener{
	  public void actionPerformed(ActionEvent evt){
            String userInput = evt.getActionCommand();
            if(userInput.equals("Clear"))
	      clear();
	  }//end actionPerformed

	  public void clear(){
              previousNumber = "0";
              currentNumber = "0";
              lastOperator = 0;
              equalsHit = false;
              waiting = false;
              tf.setText("0");
	    }//end clear
	  }//end FunctionListener


	class OperationListener implements ActionListener{
	  public void actionPerformed(ActionEvent evt){
	    String userInput = evt.getActionCommand();
	    char character = userInput.charAt(0);
	    switch(character){
	      case '-': subtract();
			break;
	      case '*': multiply();
			break;
	      case '/': divide();
			break;
	      case '+': add();
			break;
	      case '=': equalsSign();
			break;
	      case 'x': square();
			break;
	      case 'X': exponentiate();
			break;
	      case '!': factorial();
			break;
	      case '%': mod();
			break;
	    }
	  }

	  public void subtract(){
	    if(!waiting){
	      equalsHit = false;
	      waiting = true;
	      previousNumber = tf.getText();
	      lastOperator = '-';
	    }
	    else if(lastOperator == '-'){
	      String currentNumber = tf.getText();
	      double num1 = 0; double num2 = 0;
	      try{
	        num1 = Double.parseDouble(previousNumber);
	        num2 = Double.parseDouble(currentNumber);
	      }
	      catch(NumberFormatException e){
	        tf.setText("Error!!! Invalid Number");
	        previousNumber = "0";
	        lastOperator = 0;
	        equalsHit = true;
	      }
	      double numericAnswer = num1 - num2;
	      previousNumber = numericAnswer + "";
	      previousNumber = format(previousNumber);
	      tf.setText(previousNumber);
	    }
	  }

	  public void multiply(){
            if(!waiting){
	      equalsHit = false;
	      waiting = true;
	      previousNumber = tf.getText();
	      lastOperator = '*';
	    }
	    else if(lastOperator == '*'){
	      String currentNumber = tf.getText();
	      double num1 = 0; double num2 = 0;
	      try{
	        num1 = Double.parseDouble(previousNumber);
	        num2 = Double.parseDouble(currentNumber);
	      }
	      catch(NumberFormatException e){
	        tf.setText("Error!!! Invalid Number");
	        previousNumber = "0";
	        lastOperator = 0;
	        equalsHit = true;
	      }
	      double numericAnswer = num1 * num2;
	      previousNumber = numericAnswer + "";
	      previousNumber = format(previousNumber);
	      tf.setText(previousNumber);
	    }
          }

	  public void divide(){
            if(!waiting){
	      equalsHit = false;
	      waiting = true;
	      previousNumber = tf.getText();
	      lastOperator = '/';
	    }
	    else if(lastOperator == '/'){
	      String currentNumber = tf.getText();
	      double num1 = 0; double num2 = 0;
	      try{
	        num1 = Double.parseDouble(previousNumber);
	        num2 = Double.parseDouble(currentNumber);
	      }
	      catch(NumberFormatException e){
	        tf.setText("Error!!! Invalid Number");
	        previousNumber = "0";
	        lastOperator = 0;
	        equalsHit = true;
	      }
	      double numericAnswer = num1 / num2;
	      previousNumber = numericAnswer + "";
	      previousNumber = format(previousNumber);
	      tf.setText(previousNumber);
	    }
	  }

	  public void add(){
            if(!waiting){
	      equalsHit = false;
	      waiting = true;
	      previousNumber = tf.getText();
	      lastOperator = '+';
	    }
	    else if(lastOperator == '+'){
	      String currentNumber = tf.getText();
	      double num1 = 0; double num2 = 0;
	      try{
	        num1 = Double.parseDouble(previousNumber);
	        num2 = Double.parseDouble(currentNumber);
	      }
	      catch(NumberFormatException e){
	        tf.setText("Error!!! Invalid Number");
	        previousNumber = "0";
	        lastOperator = 0;
	        equalsHit = true;
	      }
	      double numericAnswer = num1 + num2;
	      previousNumber = numericAnswer + "";
	      previousNumber = format(previousNumber);
	      tf.setText(previousNumber);
	    }
	  }

	  public void equalsSign(){
	  if(!equalsHit){
            currentNumber = tf.getText();
	  }
            equalsHit = true;
            double num1 = 0; double num2 = 0;
            try{
              num1 = Double.parseDouble(previousNumber);
              num2 = Double.parseDouble(currentNumber);
            }
            catch(NumberFormatException e){
              tf.setText("Error!!! Invalid Number");
	      previousNumber = "0";
	      lastOperator = 0;
	      equalsHit = true;
            }
            switch(lastOperator){
              case '*':  previousNumber = (num1 * num2) + "";
                         break;
              case '+':  previousNumber = (num1 + num2) + "";
                         break;
              case '-':  previousNumber = (num1 - num2) + "";
                         break;
              case '/':  previousNumber = (num1 / num2) + "";
                         break;
              case 'X':  previousNumber = Math.pow(num1,num2) + "";
                         break;
              case '%':  previousNumber = (num1 % num2) + "";
                         break;
            }
            previousNumber = format(previousNumber);
            tf.setText(previousNumber);
	  }

	  public void square(){
            previousNumber = tf.getText();
            double number = 0;
            try{
              number = Double.parseDouble(previousNumber);
            }
            catch(NumberFormatException e){
             tf.setText("Error!!! Invalid Number");
	     previousNumber = "0";
             lastOperator = 0;
	     equalsHit = true;
            }
            number *= number;
            previousNumber = number + "";
            previousNumber = format(previousNumber);
            tf.setText(previousNumber);
            equalsHit = true;
            lastOperator = 'x';
	  }

	  public void exponentiate(){
            if(!waiting){
	      equalsHit = false;
	      waiting = true;
	      previousNumber = tf.getText();
	      lastOperator = 'X';
	    }
	    else if(lastOperator == 'X'){
	      String currentNumber = tf.getText();
	      double num1 = 0; double num2 = 0;
	      try{
	        num1 = Double.parseDouble(previousNumber);
	        num2 = Double.parseDouble(currentNumber);
	      }
	      catch(NumberFormatException e){
	        tf.setText("Error!!! Invalid Number");
	        previousNumber = "0";
	        lastOperator = 0;
	        equalsHit = true;
	      }
	      double numericAnswer = Math.pow(num1,num2);
	      previousNumber = numericAnswer + "";
	      previousNumber = format(previousNumber);
	      tf.setText(previousNumber);
	    }
	  }

	  public void factorial(){
            previousNumber = tf.getText();
            double number = 0;
            try{
              number = Double.parseDouble(previousNumber);
            }
            catch(NumberFormatException e){
             tf.setText("Error!!! Invalid Number"); 
	     previousNumber = "0";
             lastOperator = 0;
	     equalsHit = true;
            }
            int factorial = (int) number;
	    if(factorial == 0){
	      factorial = 1;
	      previousNumber = factorial + "";
	      tf.setText(previousNumber);
	      equalsHit = true;
	      lastOperator = 'x';
	    }
	    else{
              for(int i = (int) number - 1; i > 1; i--){
	        factorial *= i;
	      }
              previousNumber = factorial + "";
              tf.setText(previousNumber);
              equalsHit = true;
              lastOperator = 'x';
	    }
	  }

	  public void mod(){
            if(!waiting){
	      equalsHit = false;
	      waiting = true;
	      previousNumber = tf.getText();
	      lastOperator = '%';
	    }
	    else if(lastOperator == '%'){
            if(!equalsHit){
	      currentNumber = tf.getText();
            }
              System.out.println(currentNumber);
	      double num1 = 0; double num2 = 0;
	      try{
	        num1 = Double.parseDouble(previousNumber);
	        num2 = Double.parseDouble(currentNumber);
	      }
	      catch(NumberFormatException e){
	        tf.setText("Error!!! Invalid Number");
	        previousNumber = "0";
	        lastOperator = 0;
	        equalsHit = true;
	      }
	      double numericAnswer = num1 % num2;
	      previousNumber = numericAnswer + "";
	      previousNumber = format(previousNumber);
	      tf.setText(previousNumber);
	    }
	  }

	  public String format(String answer){
            double theAnswer = 0;
            try{
              theAnswer = Double.parseDouble(answer);
            }
            catch(NumberFormatException e){
                tf.setText("Error!!! Invalid Number");
	        previousNumber = "0";
	        lastOperator = 0;
	        equalsHit = true;
            }
            if(theAnswer == (int) theAnswer)
              return (int) theAnswer + "";
            else
	      return answer;
	  }
     }
}

