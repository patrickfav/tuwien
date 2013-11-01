package entities;

import java.util.Calendar;
import java.util.Date;

public class User {
	
	private String name;
	private String pswd;
	private Boolean online;
	private Date update;
	
	public User(String name, String pswd, Boolean online) {
		super();
		this.name = name;
		this.pswd = pswd;
		this.online = online;
		update = getTimeNow();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
		update = getTimeNow();
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}
	
	private Date getTimeNow() {
		Calendar cal = Calendar.getInstance();
	    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return cal.getTime();
	}
	
	
}
