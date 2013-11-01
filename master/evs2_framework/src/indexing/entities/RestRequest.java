package indexing.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetAddress;


public class RestRequest {
	private String name;
	private String id;
	private String crudAction;
	private String simpleSearchKeyword;
	private Reader contentReader;
	private InetAddress addr;
	private boolean needsEncryption;
	private String searchText;
	
	public RestRequest() {
		id="";
		name="";
		crudAction="";
		simpleSearchKeyword="";
		setNeedsEncryption(false);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public StringBuffer getContent() {
		StringBuffer jb = new StringBuffer();
		String line = null;

		BufferedReader reader = new BufferedReader(contentReader);
		
		try {
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jb;
	}
	public String getCrudAction() {
		return crudAction;
	}
	public void setCrudAction(String crudAction) {
		this.crudAction = crudAction;
	}
	
	
	@Override
	public String toString() {
		return "Entity: "+name+", Method:"+crudAction+", Id:"+id+", ip: "+addr.toString()+", enc:"+needsEncryption;
	}

	public void setContentReader(Reader contentReader) {
		this.contentReader = contentReader;
	}

	public Reader getContentReader() {
		return contentReader;
	}

	public void setAddr(InetAddress addr) {
		this.addr = addr;
	}

	public InetAddress getAddr() {
		return addr;
	}

	public void setNeedsEncryption(boolean needsEncryption) {
		this.needsEncryption = needsEncryption;
	}

	public boolean isNeedsEncryption() {
		return needsEncryption;
	}

	public void setSimpleSearchKeyword(String simpleSearchKeyword) {
		this.simpleSearchKeyword = simpleSearchKeyword;
	}

	public String getSimpleSearchKeyword() {
		return simpleSearchKeyword;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchText() {
		return searchText;
	}
}
