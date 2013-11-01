package util;

import java.util.Random;

import server.cfg.GameSettings;
import client.model.enums.BonusType;
import client.model.enums.GameColor;

public class ServerUtil {
	
	public static BonusType getRandomBonusType() {
		Random r = new Random();
		double randomType = r.nextDouble();
		
		if(randomType < GameSettings.FRUIT3_PROBABILITY) {
			return BonusType.MELON;
		} else if(randomType < GameSettings.FRUIT2_PROBABILITY) {
			return BonusType.PINEAPPLE;
		} else {
			return BonusType.STRAWBERRY;
		}
	}
	
	public static GameColor getRandomGameColor() {
		Random r = new Random();
		GameColor gc = null;
		GameColor[] gcArr = GameColor.values();
		
		do {
			gc = gcArr[r.nextInt(gcArr.length)];
		}while(gc.equals(GameColor.NONE));
		
		return gc;
	}
	
	public static void waitTime(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
	}
	
	
	/**
	 * Returns 1 if gc1 kills gc2, otherwise if gc2 kills gc1 it returns -1
	 * If gc1 and gc2 are equal: 0
	 * @param gc1
	 * @param gc2
	 * @return
	 */
	public static int pacmanFight(GameColor gc1,GameColor gc2) {
		if(gc1.equals(gc2))
			return 0;
		
		if(gc1.equals(GameColor.RED) && gc2.equals(GameColor.BLUE)) {
			return 1;
		}
		if(gc1.equals(GameColor.BLUE) && gc2.equals(GameColor.GREEN)) {
			return 1;
		}
		if(gc1.equals(GameColor.GREEN) && gc2.equals(GameColor.RED)) {
			return 1;
		}
		
		
		if(gc1.equals(GameColor.BLUE) && gc2.equals(GameColor.RED)) {
			return -1;
		}
		if(gc1.equals(GameColor.GREEN) && gc2.equals(GameColor.BLUE)) {
			return -1;
		}
		if(gc1.equals(GameColor.RED) && gc2.equals(GameColor.GREEN)) {
			return -1;
		}
		
		return 0;
	}
}