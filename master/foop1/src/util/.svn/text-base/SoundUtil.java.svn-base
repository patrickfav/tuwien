package util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import client.gui.cfg.MediaPaths;

public class SoundUtil {
	
	private static Clip clip;
	
	public static void playNope() {
		playSound(MediaPaths.SOUND_NOPE);
	}
	public static void playMjam() {
		playSound(MediaPaths.SOUND_MJAM);
	}
	public static void playEat() {
		playSound(MediaPaths.SOUND_EAT);
	}
	public static void playGameOver() {
		playSound(MediaPaths.SOUND_GAMEOVER);
	}
	public static void playWin() {
		playSound(MediaPaths.SOUND_WIN);
	}
	public static void playBazing() {
		playSound(MediaPaths.SOUND_BAZING);
	}

	public static void playSound(String soundFileName) {

		try {
			clip = AudioSystem.getClip();
			
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(SoundUtil.class
							.getResourceAsStream(MediaPaths.SOUND_PATH
									+ soundFileName));
			clip.open(inputStream);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
}
