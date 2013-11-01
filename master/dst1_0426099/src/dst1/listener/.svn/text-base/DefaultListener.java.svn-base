package dst1.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

import dst1.helper.MathHelper;

public class DefaultListener {
	
	private static int loadCount = 0;
	private static int updateCount = 0;
	private static int removeCount = 0;
	private static int persistCount = 0;
	private static long persistSum = 0l;
	private static Map<Object,Long> map = Collections.synchronizedMap(new HashMap<Object,Long>());
	
	@PostLoad
	public synchronized static void defaultLoad(Object o) {
		loadCount++;
	}
	
	@PostUpdate
	public synchronized static void defaultUpdate(Object o) {
		updateCount++;
	}
	
	@PostRemove
	public synchronized static void defaultRemove(Object o) {
		removeCount++;
	}
	
	@PrePersist
	public synchronized static void persistStart(Object o) {
		map.put(o, System.currentTimeMillis());
	}
	
	@PostPersist
	public synchronized static void persistEnd(Object o) {
		if(map.containsKey(o)) {
			persistSum += System.currentTimeMillis() - map.get(o);
			map.remove(o);
		}
		persistCount++;
	}
	
	public synchronized static String generateReport() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		sb.append("\n");
		sb.append("--------------------------------\n");
		sb.append("| Load-Operations:        "+MathHelper.formatWithLeadingZeros(loadCount,4)+" |\n");
		sb.append("| Update-Operations:      "+MathHelper.formatWithLeadingZeros(updateCount,4)+" |\n");
		sb.append("| Remove-Operations:      "+MathHelper.formatWithLeadingZeros(removeCount,4)+" |\n");
		sb.append("|                              |\n");
		sb.append("| Persist-Operations:     "+MathHelper.formatWithLeadingZeros(persistCount,4)+" |\n");
		sb.append("| Overall Time Persist:   "+MathHelper.formatWithLeadingZeros((int) persistSum,4)+" |\n");
		sb.append("| Avg. Time:              "+MathHelper.round(new Double(persistSum)/new Double(persistCount),2)+" |\n");
		sb.append("--------------------------------");
		sb.append("\n");
		return sb.toString();
	}
	
	public synchronized static void reset() {
		loadCount=0;
		updateCount=0;
		removeCount=0;
		persistCount=0;
		persistSum=0;
		map = Collections.synchronizedMap(new HashMap<Object,Long>());
	}
	
	
}
