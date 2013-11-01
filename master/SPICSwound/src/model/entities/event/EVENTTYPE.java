package entities.event;

import java.util.ArrayList;
import java.util.List;

public enum EVENTTYPE {
	
	LOGIN,
	ALIASLOGIN,
	LOGOUT,
	TIMEOUT,
	TRIALCREATE,
	TRIALEDIT,
	STATUSCHANGE,
	VALUECREATE,
	VALUECHANGE,
	VALUEDELETE, 
	PARTNERADD,  
	PARTNERDEACTIVATE, 
	PARTNERACTIVATE,
	ERROR;
	
	public static List<String> getNames() {
		List<String> types = new ArrayList<String>();
		EVENTTYPE[] e = EVENTTYPE.values();
		for (int i = 0; i < e.length; i++) {
			types.add(e[i].toString());
		}
		return types;
	}
	
	public static List<String> getMailableNonAdminEventNames() {
		List<String> types = new ArrayList<String>();
		types.add(EVENTTYPE.TRIALEDIT.name());
		types.add(EVENTTYPE.PARTNERADD.name());
		types.add(EVENTTYPE.STATUSCHANGE.name());
		return types;
	}
}
