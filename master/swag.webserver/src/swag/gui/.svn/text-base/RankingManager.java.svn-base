package swag.gui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import swag.bl.IRankingManagement;
import swag.dtos.RankingDTO;
import swag.models.GameMap;


@ManagedBean
@RequestScoped
public class RankingManager implements Serializable {

	private static final long serialVersionUID = 5414284781471439228L;
	
	@EJB
	public IRankingManagement rankingManagement;
	

	public String show() {
		return "/game/ranking.xhtml";
	}
	
	public List<RankingDTO> getRanking(GameMap map)
	{
		return rankingManagement.getRanking(map);
	}

}
