package aufgabe8;

public class FactoryException extends RuntimeException{

	private static final long serialVersionUID = -5207562573026075099L;
	
	public FactoryException(){
			
	  }
	
	public FactoryException(String s){
		super(s);	
	}
}
