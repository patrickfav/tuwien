package swag.ejb;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import swag.bl.IRankingManagement;
import swag.bl.IResourceManagement;
import swag.dtos.RankingDTO;
import swag.models.GameMap;
import swag.models.User;
import swag.models.enums.ResourceType;

@Singleton
public class RankingManagementBean implements IRankingManagement
{
	private static Logger log = Logger.getLogger("RankingManagement");
	
	private final long intervall = 10000;
	
	@PersistenceContext
	public EntityManager em;
	
	@EJB
	public IResourceManagement resourceManagement;
	
	private List<RankingDTO> rankingList;
	private long lastUpdate;

	public List<RankingDTO> getRanking(GameMap map)
	{
		if(rankingList == null || (new Date()).getTime() > lastUpdate + intervall)
		{
			rankingList = new LinkedList<RankingDTO>();
			
			Query query = em.createQuery("select u from swag_user u join u.userMaps as gm where gm.map.id = :mid");
			query.setParameter("mid", map.getId());
			List<User> users = (List<User>)query.getResultList();
			
			for(User u : users)
			{
				long wood = resourceManagement.getRessourceStock(ResourceType.WOOD, u, map);
				long stone = resourceManagement.getRessourceStock(ResourceType.STONE, u, map);
				long iron = resourceManagement.getRessourceStock(ResourceType.IRON, u, map);
					
				RankingDTO ranking = new RankingDTO();
				
				ranking.setUsername(u.getUsername());
				ranking.setPoints(u.getPoints());
				ranking.setWood(wood);
				ranking.setStone(stone);;
				ranking.setIron(iron);
				ranking.setAll(wood + stone + iron);
				
				rankingList.add(ranking);
			}
			
			lastUpdate = (new Date()).getTime();
		}
		
		return rankingList;
	}

}
