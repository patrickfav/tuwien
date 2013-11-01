package gui.mem;

import java.util.ArrayList;
import java.util.List;

import entities.Peer;
import gui.logic.DeleteEvent;
import gui.logic.DeleteListener;
import net.jini.space.JavaSpace;

public class GuiMemory {
	
	private static JavaSpace space;
	private static Peer user;
	private static List<DeleteListener> delListener;
	
	public static JavaSpace getSpaceInstance() {
		return space;
	}
	public static void setSpace(JavaSpace space) {
		GuiMemory.space = space;
	}
	
	
	public static Peer getLoginUser() {
		return user;
	}
	public static void login(Peer user) {
		GuiMemory.user = user;
	}
	
	public static void addDeleteListener(DeleteListener d) {
		if(delListener == null)
			delListener = new ArrayList<DeleteListener>();
		
		delListener.add(d);
	}
	
	public static void fireDeleteEvent(String eventName) {
		DeleteEvent de = new DeleteEvent(eventName);
		
		for(DeleteListener d: delListener) {
			d.deletePerformed(de);
		}
	}
}
