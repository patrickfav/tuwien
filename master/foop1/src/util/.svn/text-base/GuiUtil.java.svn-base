package util;

import javax.swing.ImageIcon;

import client.gui.cfg.GameCellType;
import client.gui.cfg.MediaPaths;
import client.model.enums.BonusType;
import client.model.enums.GameColor;
import client.model.enums.Movement;

public class GuiUtil {
	public static ImageIcon createImageIcon(String path, String description,Object thisObj) {
		java.net.URL imgURL = thisObj.getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	public static String getImgPathForColor(GameColor color) {
		if(color.equals(GameColor.GREEN)) {
			return MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_GREEN;
		} else if(color.equals(GameColor.BLUE)) {
			return MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_BLUE;
		} else if(color.equals(GameColor.RED)) {
			return MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_RED;
		}
		
		return "";
	}
	
	public static String getSmallImgPathForColor(GameColor color) {
		if(color.equals(GameColor.GREEN)) {
			return MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_GREEN_SM;
		} else if(color.equals(GameColor.BLUE)) {
			return MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_BLUE_SM;
		} else if(color.equals(GameColor.RED)) {
			return MediaPaths.IMAGE_PATH+MediaPaths.IMG_PACMAN_RED_SM;
		}
		
		return "";
	}
	
	public static String getControlStringForColor(GameColor color) {
		if(color.equals(GameColor.GREEN)) {
			return "(WASD-Keys)";
		} else if(color.equals(GameColor.BLUE)) {
			return "(Arrow-Keys)";
		} else if(color.equals(GameColor.RED)) {
			return "(IJKL-Keys)";
		}
		
		return "";
	}
	
	public static GameCellType getPacmanCellTypeForColor(GameColor gc) {
		if(gc.equals(GameColor.GREEN)) {
			return GameCellType.PACMAN_GREEN;
		} else if(gc.equals(GameColor.BLUE)) {
			return GameCellType.PACMAN_BLUE;
		} else if(gc.equals(GameColor.RED)) {
			return GameCellType.PACMAN_RED;
		}
		
		return GameCellType.NONE;
	}
	
	public static GameCellType getWallCellTypeForColor(GameColor gc) {
		if(gc.equals(GameColor.GREEN)) {
			return GameCellType.WALL_GREEN;
		} else if(gc.equals(GameColor.BLUE)) {
			return GameCellType.WALL_BLUE;
		} else if(gc.equals(GameColor.RED)) {
			return GameCellType.WALL_RED;
		} else if(gc.equals(GameColor.NONE)) {
			return GameCellType.WALL;
		}
		
		return GameCellType.NONE;
	}
	
	public static GameCellType getWallWithPacmanCellTypeForColor(GameColor gc) {
		if(gc.equals(GameColor.GREEN)) {
			return GameCellType.WALL_PACMAN_GREEN;
		} else if(gc.equals(GameColor.BLUE)) {
			return GameCellType.WALL_PACMAN_BLUE;
		} else if(gc.equals(GameColor.RED)) {
			return GameCellType.WALL_PACMAN_RED;
		} 
		return GameCellType.NONE;
	}
	
	public static GameCellType getFruitCellType(BonusType type) {
		if(type.equals(BonusType.STRAWBERRY)) {
			return GameCellType.FRUIT1;
		} else if(type.equals(BonusType.PINEAPPLE)) {
			return GameCellType.FRUIT2;
		} else if(type.equals(BonusType.MELON)) {
			return GameCellType.FRUIT3;
		}
		
		return GameCellType.NONE;
	}
	
	public static Movement translateKeyCode(GameColor c,int keycode) {
		if(c.equals(GameColor.GREEN)) { /* WASD */
			if(keycode == 87) {
				return Movement.UP;
			} else if(keycode == 83) {
				return Movement.DOWN;
			} else if(keycode == 65) {
				return Movement.LEFT;
			} if(keycode == 68) {
				return Movement.RIGHT;
			}	
		} else if(c.equals(GameColor.BLUE)) { /* ARROW KEYS */
			if(keycode == 38) {
				return Movement.UP;
			} else if(keycode == 40) {
				return Movement.DOWN;
			} else if(keycode == 37) {
				return Movement.LEFT;
			} if(keycode == 39) {
				return Movement.RIGHT;
			}	
		} else if(c.equals(GameColor.RED)) { /* IJKL KEYS */
			if(keycode == 73) {
				return Movement.UP;
			} else if(keycode == 75) {
				return Movement.DOWN;
			} else if(keycode == 74) {
				return Movement.LEFT;
			} if(keycode == 76) {
				return Movement.RIGHT;
			}	
		}
		
		return Movement.NONE;
	}
	
	public static double getMovementDirection(int oldX, int oldY, int newX, int newY) {
		if(oldX < newX) {
			return 0;
		} else if(oldX > newX) {
			return -180.0;
		} else if(oldY < newY) {
			return 90.0;
		} else if(oldY > newY) {
			return -90.0;
		}
		
		return 0.0;
		
	}
}
