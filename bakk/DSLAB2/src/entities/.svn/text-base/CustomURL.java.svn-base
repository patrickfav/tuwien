package entities;

import java.util.ArrayList;
import java.util.List;

public class CustomURL {
	
	private List<String> domain;
	private String path;
	
	public CustomURL(List<String> domain, String path) {
		super();
		this.domain = domain;
		this.path = path;
	}
	
	public CustomURL(String url) {
		
		String urlOnly;
		if(url.equals("")) {
			this.domain = new ArrayList<String>();
		}
		
		if(url.indexOf("/") == -1) {
			this.path = "";
			urlOnly = url;
		} else {
			String path = url.substring(url.indexOf("/"));
			this.path = path;
			urlOnly = url.substring(0, url.indexOf("/"));
		}
		
		/* split the domain part by "." */
		List<String> domainlist = new ArrayList<String>();
		while(true){
			if(urlOnly.indexOf(".") == -1) {
				domainlist.add(urlOnly);
				break;
			}
			domainlist.add(urlOnly.substring(0, urlOnly.indexOf(".")));
			urlOnly = urlOnly.substring(urlOnly.indexOf(".")+1);
		}
		
		this.domain = domainlist;
		
	}
	public List<String> getDomain() {
		return domain;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> cloneDomainList() {
		ArrayList<String> temp  = new ArrayList<String>(domain);
		return (List<String>) temp.clone();
	}
	
	public void setDomain(List<String> domain) {
		this.domain = domain;
	}
	
	public String getDomainToString() {
		String returnString = new String();
		
		for(int i=0; i<domain.size();i++) {
			if(i==(domain.size()-1)) {
				returnString += domain.get(i);
			} else {
				returnString += domain.get(i)+".";
			} 
		}
		
		return returnString;
	}
	
	public String getReverseDomainToString() {
		String returnString = new String();
		
		for(int i=(domain.size()-1); i>-1;i--) {
			if(i==0) {
				returnString += domain.get(i);
			} else {
				returnString += domain.get(i)+".";
			} 
		}
		
		return returnString;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
