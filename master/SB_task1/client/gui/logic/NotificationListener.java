package gui.logic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import net.jini.core.event.RemoteEvent;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.event.UnknownEventException;

public class NotificationListener implements RemoteEventListener {
	
	List<RemoteEventListener> listener;
	
	public NotificationListener() {
		super();
		this.listener = new ArrayList<RemoteEventListener>();
	}
	
	public void addListner(RemoteEventListener l) {
		listener.add(l);
	}


	@Override
	public void notify(RemoteEvent event) throws UnknownEventException, RemoteException {
		for(RemoteEventListener l: listener) {
			l.notify(event);
		}
	}

}
