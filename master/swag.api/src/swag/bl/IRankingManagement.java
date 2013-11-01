package swag.bl;

import java.util.LinkedList;
import java.util.List;

import swag.dtos.RankingDTO;
import swag.models.GameMap;
import swag.models.User;
import swag.models.enums.ResourceType;

public interface IRankingManagement 
{
	public List<RankingDTO> getRanking(GameMap map);
}
