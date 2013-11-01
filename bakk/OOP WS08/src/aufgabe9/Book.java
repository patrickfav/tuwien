package aufgabe9;

public class Book extends AbstractGift {
	
	public Book(String name, float height, float length, float width) {
		super(name,height);
		this.form = new Rectangle(length,width);
	}
	
	public String getGiftType() {
		return "Book";
	}
}
