package server.cfg;

import client.model.enums.BonusType;

public class FruitPoints {
	public static final long FRUIT1=100;
	public static final long FRUIT2=400;
	public static final long FRUIT3=600;
	
	
	public static long getPointsForType(BonusType type) {
		if(type.equals(BonusType.MELON)) {
			return FRUIT3;
		} else if(type.equals(BonusType.PINEAPPLE)) {
			return FRUIT2;
		} else if(type.equals(BonusType.STRAWBERRY)) {
			return FRUIT1;
		}
		
		return 0;
	}
}
