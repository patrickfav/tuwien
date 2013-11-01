import java.util.Random;
import java.util.Scanner;

/**
 * SWWE lab1
 * Patrick Favre-Bulle, 0426099
 *
 */

public class ProgramXYZ {
	//OPTION1 OPTION3 OPTION5 OPTION4
	public enum Color {
		INVALID,OPTION1,OPTION2,OPTION3,OPTION4,OPTION5,OPTION6
	}
	
	private static final int GAME_LENGTH = 4;
	private static final Color[] allOptions = new Color[]{Color.OPTION1,Color.OPTION2,Color.OPTION3,Color.OPTION4,Color.OPTION5,Color.OPTION6};
	
	/* needs to be static because there is no call by reference in java */
	private static int hits_other_position = 0;
	private static int hits = 0;
	
	/* additional Java fields */
	private static Random r;
	
	public static void main(String[] args) {
		
		String answer;
		Color[] code = new Color[GAME_LENGTH];
		Color[] tip = new Color[GAME_LENGTH];
		
		code = random();
		
		//System.out.println("Cheat: "+Arrays.toString(code));
		
		Scanner in = new Scanner(System.in);
		System.out.println("YOUR OPTIONS: " +
				"OPTION1, OPTION2, OPTION3, OPTION4, OPTION5, OPTION6");
		
		while(true) {
			System.out.println("TIP?");
			answer = in.nextLine();
			
			if(answer.equals("")) {
				return;
			}
			
			tip = translation(answer);
			
			//System.out.println("Converted Tip: "+Arrays.toString(tip));
			
			if(checkMatchingPositions(tip,code) == GAME_LENGTH) {
				break;
			}
			
			evaluation(code,tip);
			
			System.out.println("HITS: " +
								hits + ", " +
								"HITS_OTHER_POSITION: " 
								+ hits_other_position);
			
		}
		
		System.out.println("ALL RIGHT! PRESS ENTER");
		in.nextLine();
	}
	
	/**
	 * Generates a Random Color Enum.
	 * @return random color
	 */
	public static Color[] random() {
		Color[] colorCode = new Color[] {Color.OPTION1,Color.OPTION2,Color.OPTION3,Color.OPTION4,Color.OPTION5,Color.OPTION6};
		
		if(r == null) {
			r = new Random(System.currentTimeMillis());
		}
		/* here's the error in the code: the result of the random func in pli script is always 4 times the same color */
		return new Color[]{colorCode[r.nextInt(5)],colorCode[r.nextInt(5)],colorCode[r.nextInt(5)],colorCode[r.nextInt(5)]};
	}
	
	
	public static Color[] translation(String answer) {
		
		int blankPos = 0;
		int letterPos;
		String s;
		
		Color[] tip = new Color[] {Color.INVALID,Color.INVALID,Color.INVALID,Color.INVALID};
		
		if(answer.length() == 0)
			return tip;
		
		s = answer.toUpperCase();
		
		for(int i=0;i<GAME_LENGTH;i++) {
			letterPos = verify(s,' ',blankPos);
			
			//System.out.println("letterpos: "+letterPos);
			
			if(letterPos == -1)
				break;
			
			blankPos = s.indexOf(" ",letterPos);
			
			//System.out.println("blankPos: "+blankPos);
			
			if(blankPos == -1)
				blankPos = s.length();
			
			
			//System.out.println(s.substring(letterPos, blankPos));
			
			for(Color f:allOptions) {
				//System.out.println(c.toString()+" checkd for "+s.substring(letterPos, blankPos));
				if(s.substring(letterPos, blankPos).equals(f.toString())) {
					//System.out.println("match");
					tip[i] = f;
					break;
				}
			}
			
		}
		
		return tip;
	}
	
	public static void evaluation(Color[] code, Color[] tip) {
		int hits_overall = 0;
		
		hits = checkMatchingPositions(tip,code);
			
		for(Color f:allOptions) {
			hits_overall += Math.min(checkMatchingPositions(tip,new Color[]{f,f,f,f}), checkMatchingPositions(code,new Color[]{f,f,f,f}));
		}
		
		hits_other_position = hits_overall - hits;
	}
	
	/**
	 * Functionality copied from pl/i verify function:
	 * VERIFY returns an int value indicating the 
	 * position in x of the leftmost string 
	 * that is not in y. It also allows you to specify the location 
	 * within x at which to begin processing. If nothing found it
	 * will return -1
	 * 
	 * @param x
	 * @param y
	 * @param pos
	 * @return
	 */
	public static int verify(String x, char y, int pos) {
		//System.out.println("Given String: '"+x.substring(pos)+"'");
		for(int i=pos;i<x.length();i++)
			if(x.charAt(i) != y)
					return i;
		
		return -1;
	}
	
	/**
	 * Substitues the sum function in PLI
	 * @param c1
	 * @param c2
	 * @return
	 */
	
	public static int checkMatchingPositions(Color[] c1, Color[] c2) {
		int count = 0;
		for(int i=0;i<c1.length;i++)
			if(c1[i] == c2[i])
				count++;
		
		
		return count;
	}
}
