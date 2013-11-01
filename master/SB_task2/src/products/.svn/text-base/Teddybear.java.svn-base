package products;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.mozartspaces.capi3.Index;
import org.mozartspaces.capi3.Queryable;

import products.parts.Arm;
import products.parts.Body;
import products.parts.GreenCap;
import products.parts.Head;
import products.parts.Leg;
import products.parts.RedCap;
import products.parts.abstracts.Cap;
import workers.id.WorkerID;

@Queryable
public class Teddybear implements Serializable{

	private static final long serialVersionUID = -1166107761375884792L;
	private static final int MAX_PARTS = 7;
	
	private UUID id;
	
	private Head head;
	private Body body;
	private List<Leg> legs;
	private List<Arm> arms;
	private Cap cap;
	
	@Index
	private Boolean defectCheckDone; 
	@Index
	private Boolean weightCheckDone;
	private Boolean assembleDone; //automatically set true when all parts are assembled
	private Boolean shippingDone;
	private Boolean isDefect;
	
	private WorkerID assembleImp;
	private WorkerID defectTestDwarf;
	private WorkerID weightTestDwarf;
	private WorkerID shippingReindeer;
	
	public Teddybear() {
		id = UUID.randomUUID();
		legs = new ArrayList<Leg>(2);
		arms = new ArrayList<Arm>(2);
		defectCheckDone=false;
		weightCheckDone = false;
		shippingDone=false;
		isDefect=false;
		assembleDone=false;
		
	}
	public Teddybear(Boolean defect, Boolean weight) {
		id = null;
		legs = null;
		arms = null;
		defectCheckDone=defect;
		weightCheckDone = weight;
		shippingDone=null;
		isDefect=null;
		assembleDone=null;
		
	}
	
	/*
	 *  WORKER METHODS
	 */
	
	public void assemble(WorkerID imp, PartsContainer container) {
		assemble(imp,container.getCap(),container.getHead(),container.getBody(),container.getLeftArm(),container.getRightArm(),container.getLeftLeg(),container.getRightLeg());
	}
	
	public void assemble(WorkerID imp,Cap cap, Head head, Body body, Arm larm,Arm rarm,Leg lleg,Leg rleg) {
		assembleImp = imp;
		assembleDone = true;
		setCap(cap);
		setHead(head);
		setBody(body);
		addArm(larm);
		addArm(rarm);
		addLeg(lleg);
		addLeg(rleg);
	}
	
	public boolean checkForDefect(WorkerID dwarf) {
		isDefect = checkDefect();
		defectCheckDone = true;
		defectTestDwarf = dwarf;
		return isDefect;
	}
	private boolean checkDefect() {
		return head.isDefect() || cap.isDefect() || body.isDefect() || checkLegsForDefect() || checkArmsForDefect();
	}
	
	public boolean checkForWeight(WorkerID dwarf) {
		weightTestDwarf = dwarf;
		weightCheckDone = true;
		return true;
	}
	public void markShipped(WorkerID reindeer){
		shippingReindeer = reindeer;
		shippingDone=true;
	}
	public void markReadyButDefect(WorkerID reindeer) {
		shippingReindeer = reindeer;
	}
	
	public boolean addLeg(Leg leg) {
		if(legs.size()<2) {
			legs.add(leg);
			return true;
		}
		return false;
	}
	public boolean addArm(Arm arm) {
		if(arms.size()<2) {
			arms.add(arm);
			return true;
		}
		return false;
	}
	
	public int partsMissing() {
		int attached =0;
		
		if(head != null)
			attached++;
		if(body != null)
			attached++;
		if(cap != null)
			attached++;

		attached+=legs.size()+arms.size();
		
		return MAX_PARTS-attached;
	}
	
	/*
	 *  GETTER AND SETTER
	 */
	
	public UUID getId() {
		return id;
	}
	public Head getHead() {
		return head;
	}
	private void setHead(Head head) {
		this.head = head;
	}
	public Body getBody() {
		return body;
	}
	private void setBody(Body body) {
		this.body = body;
	}
	public List<Leg> getLegs() {
		return legs;
	}
	public List<Arm> getArms() {
		return arms;
	}
	public Cap getCap() {
		return cap;
	}
	private void setCap(Cap cap) {
		this.cap = cap;
	}
	
	
	public Boolean isDefectCheckDone() {
		return defectCheckDone;
	}
	public Boolean isWeightCheckDone() {
		return weightCheckDone;
	}
	public Boolean isAssembleDone() {
		return assembleDone;
	}
	public Boolean isShippingDone() {
		return shippingDone;
	}
	public Boolean isDefect() {
		return isDefect;
	}

	public WorkerID getDefectTestDwarf() {
		return defectTestDwarf;
	}
	public WorkerID getWeightTestDwarf() {
		return weightTestDwarf;
	}
	public WorkerID getShippingReindeer() {
		return shippingReindeer;
	}

	public WorkerID getAssembleImp() {
		return assembleImp;
	}
	
	/*
	 * PRIVATE METHODS
	 */
	
	private boolean checkLegsForDefect() {
		for(Leg l:legs) {
			if(l.isDefect())
				return true;
		}
		return false;
	}
	private boolean checkArmsForDefect() {
		for(Arm a:arms) {
			if(a.isDefect())
				return true;
		}
		return false;
	}

	/*
	 * OVERRIDES
	 */
	@Override
	public String toString() {
		String worker="";
		String defect="";
		String capString="";
		
		if(assembleImp != null) {
			worker += "a:"+assembleImp.getId();
		}
		if(defectTestDwarf != null) {
			worker += "/d:"+defectTestDwarf.getId();
		}
		if(weightTestDwarf != null) {
			worker += "/w:"+weightTestDwarf.getId();
		}
		if(shippingReindeer != null) {
			worker += "/s:"+shippingReindeer.getId();
		}
		if(checkDefect()) {
			defect="[d:";
			if(head.isDefect())
				defect+="head";
			if(body.isDefect())
				defect+=" body";
			if(cap.isDefect())
				defect+=" cap";
			if(legs.get(0).isDefect())
				defect+=" lLeg";
			if(legs.get(1).isDefect())
				defect+=" rLeg";
			if(arms.get(0).isDefect())
				defect+=" lArm";
			if(arms.get(1).isDefect())
				defect+=" rArm";
			defect+="]";
		}
		
		if(cap instanceof GreenCap) {
			capString = "{g}";
		} else if (cap instanceof RedCap) {
			capString = "{r}";
		}
		
		return "T:"+id.toString().substring(0, 6)+" "+capString+" ("+worker+") "+defect;
	}

}
