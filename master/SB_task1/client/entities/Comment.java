package entities;

import java.io.Serializable;
import java.util.Date;
import net.jini.core.entry.Entry;

public class Comment implements Entry, Serializable {

	private static final long serialVersionUID = -1671190078343731957L;
	
	public Peer author;
	public Date date;
	public String text;
	
	public Comment() {
		super();
	}
	
	public Comment(Peer author, Date date, String text) {
		super();
		this.author = author;
		this.date = date;
		this.text = text;
	}
	
	/* GETTER AND SETTER *****************************************/
	
	public Peer getAuthor() {
		return author;
	}
	public void setAuthor(Peer author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
