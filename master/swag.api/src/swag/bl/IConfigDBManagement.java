package swag.bl;

import javax.ejb.Remote;

@Remote
public interface IConfigDBManagement {
	public void addDataToDB();
}
