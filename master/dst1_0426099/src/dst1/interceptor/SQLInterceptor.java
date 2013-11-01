package dst1.interceptor;

import org.hibernate.EmptyInterceptor;

public class SQLInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 3894614218727237142L;
	private static long count = 0;
	private static String tmp;
	
	public String onPrepareStatement(String sql) {
		//should start with select
		if(sql.startsWith("select")) {
			if(sql.indexOf("from") != -1){
				//try to get the part before "from"
				tmp = sql.substring(0,sql.indexOf("from"));
			} else {
				tmp = sql;
			}
			
			//check if computer or execution
			if(tmp.contains("computer") || tmp.contains("execution"))
				count++;
			
			//System.out.println(" ---- ["+sql.indexOf("from")+"] "+sql);
		}
		
		
		
		return sql;
	}
	
	public static void reset() {
		count =0;
	}
	
	public static String printLog() {
		return "| Counted select statements for Computers and Executes "+count+" |";
	}
}
