package exe;


import java.util.ArrayList;

import net.jini.core.lease.Lease;
import net.jini.space.JavaSpace;

import com.j_spaces.core.client.SpaceFinder;

import entities.Event;
import entities.Peer;


public class Server {

	public static final String SPACE_URI = "jini://*/sboodle_container1/sboodle";
	
	public static void main(String[] args) {
		try {
			System.out.println("Search for Space");
			
			JavaSpace space = findSpace(SPACE_URI);
			
			ArrayList<Peer> invitedPeers = new ArrayList<Peer>();
			
			Peer peer = new Peer("test");
			Peer peer2 = new Peer("test2");
			
			invitedPeers.add(peer);
			invitedPeers.add(peer2);
			
			Event e = new Event();
			e.setName("Hallo Event");
			e.setInvitedPeers(invitedPeers);
			
			if(space != null) {
				System.out.println("Space Found");
				
				System.out.println("Write Event to Space");
				space.write(e, null, Lease.FOREVER);

				e.setName(null);
				invitedPeers.remove(peer2);
				System.out.println("Find Event in Space");
				Event e2 = (Event) space.read(e, null, 0);
				System.out.println("Event " + e2.getName());
				
			} else {
				System.out.println("Space not Found");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Server() {
		
	}
	
	static public JavaSpace findSpace(String spaceName) throws Exception {
		return (JavaSpace) SpaceFinder.find(spaceName);
	}
}
