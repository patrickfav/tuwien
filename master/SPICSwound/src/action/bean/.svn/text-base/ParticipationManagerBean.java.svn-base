package bean;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.faces.model.SelectItem;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.log.Log;

import util.SpicsPermissions;
import db.interfaces.IParticipationDAO;
import db.interfaces.IUserDAO;
import entities.Participation;
import entities.User;
import entities.event.PartnerActivateEvent;
import entities.event.PartnerAddEvent;
import entities.event.PartnerDeactivateEvent;

@Stateful
@Scope(ScopeType.CONVERSATION)
@Name("ParticipationManager")
public class ParticipationManagerBean implements ParticipationManager {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In
	private User user;

	@In
	private SessionInfo sessionInfo;

	@EJB
	private IParticipationDAO participationDAO;
	@EJB
	private IUserDAO userDAO;
	@EJB
	private EventManager eventManager;

	private String selectedUsername;
	
	@SuppressWarnings("unused")
	@DataModel(value="participations",scope=ScopeType.UNSPECIFIED) //changed from ScopeType.Session to Unspecified
	private List<Participation> participations;
	
	private int page;
	private static final int MAX_PAGES = 10;
	private static final int MAX_ROWS = 15;

	/*
	 * (non-Javadoc)
	 * 
	 * @see bean.ParticipationManager#queryParticipations()
	 */
	@Factory(value="participations")
	public void queryParticipations() {
		this.log.info("Querying participation list for user #0", this.user
				.getUsername());
		participations = participationDAO.findByTrialId(sessionInfo.getTrialID(), MAX_PAGES * MAX_ROWS);
	}

	public void addParticipation() {
		if (selectedUsername == null) {
			log.warn("cannot add participation: no username selected");
			return;
		}

		Participation p = new Participation();
		p.setParticipatingSince(new Date(System.currentTimeMillis()));
		p.setTrial(sessionInfo.getTrial());
		p.setUser(userDAO.findByID(selectedUsername));

		participationDAO.persist(p);

		PartnerAddEvent pae = new PartnerAddEvent();
		pae.setUser(user);
		pae.setPartnerId(selectedUsername);
		pae.setTrial(sessionInfo.getTrial());

		eventManager.registerEvent(pae);
		
		SpicsPermissions.instance().grantPartner(sessionInfo.getTrial(), p.getUser());
		
		participations = null;	// trigger reload
		
	}

	public void aktivateParticipation(Participation participation) {
		participation.setActive(true);	
		participationDAO.merge(participation);
		
		PartnerActivateEvent pae = new PartnerActivateEvent();
		pae.setUser(user);
		pae.setTrial(sessionInfo.getTrial());
		pae.setPartnerId(participation.getUser().getUsername());
		eventManager.registerEvent(pae);
		
		SpicsPermissions.instance().grantPartner(sessionInfo.getTrial(), participation.getUser());
		
		participations = null;
	}

	public void deaktivateParticipation(Participation participation) {
		participation.setActive(false);	
		participationDAO.merge(participation);
		
		PartnerDeactivateEvent pde = new PartnerDeactivateEvent();
		pde.setUser(user);
		pde.setTrial(sessionInfo.getTrial());
		pde.setPartnerId(participation.getUser().getUsername());
		eventManager.registerEvent(pde);
		
		SpicsPermissions.instance().revokePartner(sessionInfo.getTrial(), participation.getUser());
		participations = null;
	}

	public List<SelectItem> getAllUsers() {
		LinkedList<SelectItem> selectList = new LinkedList<SelectItem>();

		List<User> users = userDAO.usersNotParticipatingInTrial(sessionInfo
				.getTrialID());

		for (User u : users) {
			SelectItem si = new SelectItem();
			si.setValue(u.getUsername());
			si.setLabel(u.getScreenname());
			selectList.add(si);
		}

		return selectList;
	}

	public String getSelectedUsername() {
		return selectedUsername;
	}

	public void setSelectedUsername(String selectedUsername) {
		this.selectedUsername = selectedUsername;
	}

	@Remove
	@Destroy
	public void remove() {
	}

	public void toggleViewAllPatients(Participation p) {
		log.info("toggleCanViewAllPatients called ... setting can view all patients to #0 for user #1", p.getCanViewAllPatients(), p.getUser().getUsername());

		participationDAO.merge(p);

	}
	
	public int getPatientCount(Participation p) {
		return participationDAO.getPatientCount(p);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMAX_PAGES() {
		return MAX_PAGES;
	}

	public int getMAX_ROWS() {
		return MAX_ROWS;
	}


}
