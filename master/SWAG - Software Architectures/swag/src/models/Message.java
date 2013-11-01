package models;

import javax.persistence.*;
import java.util.*;

@Entity
public class Message {

	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@OneToOne()
	private Player sender;

	public void setSender(Player sender) {
		this.sender = sender;
	}

	public Player getSender() {
		return sender;
	}

	private String content;

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
