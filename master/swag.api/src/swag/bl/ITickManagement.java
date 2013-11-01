package swag.bl;

import java.util.Date;

import javax.ejb.Local;

import swag.models.GameMap;

@Local
public interface ITickManagement {

	public void init();
	
	public Date calculateEnd(Date start, Integer duration, GameMap m);
}
