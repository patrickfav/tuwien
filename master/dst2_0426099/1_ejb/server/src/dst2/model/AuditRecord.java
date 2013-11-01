package dst2.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AuditRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="priceStepSequencer")
	private Long id;
	
	private String methodName;
	private String result;
	private String params;
	
	private String exception;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		String out = "";
		
		out += "["+date+"]: "+methodName+"(";
		
		if(params != null) {
			out += params;
		} 
		
		out +=")";
		
		if(result !=null)
			out += ":"+result;
		
		if(exception != null)
			out+="\n\t"+exception;
		
		
		return out;
	}
	
}
