package swag.broker;
import java.net.InetAddress;

import javax.ejb.Remote;

import swag.bl.IBaseManagement;
import swag.bl.IBuildingManagement;
import swag.bl.IConfigDBManagement;
import swag.bl.IMapManagement;
import swag.bl.IRankingManagement;
import swag.bl.IResourceManagement;
import swag.bl.ITestDataManagement;
import swag.bl.ITickManagement;
import swag.bl.ITroopManagement;
import swag.bl.IUserManagement;
import swag.bl.IUtilizationManagement;

@Remote
public interface IBroker extends IBaseManagement, IBuildingManagement, IConfigDBManagement, 
IMapManagement, IRankingManagement, IResourceManagement, ITestDataManagement, ITickManagement,
ITroopManagement, IUserManagement, IUtilizationManagement
{
	public void register(String ip);
}
