package pgr;

import java.util.Arrays;
import java.util.Random;

public class DoodleDecider {
	
	public static final int DICE_THROWS = 1000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int options = args.length;
		
		if(options == 0) {
			System.out.println("No arguments given.");
		} else {
			Integer[] results = new Integer[options];
			Arrays.fill(results, 0);
			
			Random r = new Random();
			
			for(int i=0;i<options;i++) {
				for(int j=0;j<DICE_THROWS;j++) {
					if(r.nextDouble() < 0.50d) {
						results[i]+=r.nextInt(100);
					}
				}
			}
			
			int max = 0;
			int max_index = 0;
			for(int i=0;i<options;i++) {
				if(results[i] > max) {
					max = results[i];
					max_index = i;
				}
				
				System.out.println(args[i]+": "+results[i]+" points.");
			}
			
			System.out.println("\n**************************************************************************");
			System.out.println("*");
			System.out.println("*     WINNER: "+args[max_index]+" with "+results[max_index]+" points.");
			System.out.println("*");
			System.out.println("**************************************************************************");
		}
	}
}
