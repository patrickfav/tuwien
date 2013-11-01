package client.model.enums;

public enum Movement {
	LEFT,RIGHT,UP,DOWN,NONE;
	
	public static Movement getOpposite(Movement m) {
		if(m.equals(Movement.LEFT)) {
			return Movement.RIGHT;
		} else if(m.equals(Movement.RIGHT)) {
			return Movement.LEFT;
		} else if(m.equals(Movement.UP)) {
			return Movement.DOWN;
		} else if(m.equals(Movement.DOWN)) {
			return Movement.UP;
		}
		
		return Movement.NONE;
	}
}
