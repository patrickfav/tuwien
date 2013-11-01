package swag.ejb.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import swag.bl.IConfigDBManagement;
import swag.models.MilitaryBuilding;
import swag.models.ResourceBuilding;
import swag.models.Troop;
import swag.models.UpgradeBuilding;
import swag.models.enums.MilitaryType;
import swag.models.enums.ResourceType;


@Stateless
public class ConfigDBManagementBean implements IConfigDBManagement{
	
	@PersistenceContext
	public EntityManager em;
	
	@Override
	public void addDataToDB() {
		addMilitaryTypes();
		addBuildingTypes();
		
	}
	private void addMilitaryTypes() {
		Troop t1 = new Troop();
		t1.setName("Swordsman");
		t1.setPoints(10);
		t1.setSmallImage("small_infantry.png");
		t1.setMiniImage("mini_infantry.png");
		t1.setArmor(10);
		t1.setSpeed(5);
		t1.setStrength(15);
		t1.setTrainingTime(1);
		t1.setType(MilitaryType.INFANTRY);
		Map<ResourceType, Long> c1 = new HashMap<ResourceType, Long>();
		c1.put(ResourceType.WOOD, new Long(0));
		c1.put(ResourceType.STONE, new Long(0));
		c1.put(ResourceType.IRON, new Long(10));
		t1.setResourceCost(c1);
		em.persist(t1);
		
		Troop t2 = new Troop();
		t2.setName("Knigt");
		t2.setPoints(30);
		t2.setSmallImage("small_cavalry.png");
		t2.setMiniImage("mini_cavalry.png");
		t2.setArmor(25);
		t2.setSpeed(15);
		t2.setStrength(25);
		t2.setTrainingTime(2);
		t2.setType(MilitaryType.CAVALRY);
		Map<ResourceType, Long> c2 = new HashMap<ResourceType, Long>();
		c2.put(ResourceType.WOOD, new Long(10));
		c2.put(ResourceType.STONE, new Long(0));
		c2.put(ResourceType.IRON, new Long(30));
		t2.setResourceCost(c2);
		em.persist(t2);
		
		Troop t3 = new Troop();
		t3.setName("Catapult");
		t3.setPoints(30);
		t3.setSmallImage("small_artillery.png");
		t3.setMiniImage("mini_artillery.png");
		t3.setArmor(20);
		t3.setSpeed(3);
		t3.setStrength(100);
		t3.setTrainingTime(3);
		t3.setType(MilitaryType.ARTILLERY);
		Map<ResourceType, Long> c3 = new HashMap<ResourceType, Long>();
		c3.put(ResourceType.WOOD, new Long(50));
		c3.put(ResourceType.STONE, new Long(50));
		c3.put(ResourceType.IRON, new Long(50));
		t3.setResourceCost(c3);
		em.persist(t3);
	}
	
	private void addBuildingTypes() {
		ResourceBuilding b = new ResourceBuilding();
		b.setName("Lumberjack");
		b.setImage("building_holzfaeller.png");
		b.setSmallImage("holz.png");
		b.setBuildingTime(4);
		b.setBaseUpgradeTime(3);
		b.setBonusFactor(0.3);
		b.setMaxLevel(9);
		b.setPoints(100);
		b.getResourceCost().put(ResourceType.WOOD, 0l);
		b.getResourceCost().put(ResourceType.STONE, 500l);
		b.getResourceCost().put(ResourceType.IRON, 0l);
		b.setRessource(ResourceType.WOOD);
		b.setBaseProduction(100L);
		b.setLevelFactor(0.75);
		b.setNextToResourceFactor(1.5);
		em.persist(b);
		
		ResourceBuilding b1 = new ResourceBuilding();
		b1.setName("Mine");
		b1.setImage("building_eisenmine.png");
		b1.setSmallImage("mine.png");
		b1.setBuildingTime(5);
		b1.setBaseUpgradeTime(3);
		b1.setBonusFactor(0.3);
		b1.setMaxLevel(9);
		b1.setPoints(150);
		b1.getResourceCost().put(ResourceType.WOOD, 300l);
		b1.getResourceCost().put(ResourceType.STONE, 500l);
		b1.getResourceCost().put(ResourceType.IRON, 0l);
		b1.setRessource(ResourceType.IRON);
		b1.setBaseProduction(100L);
		b1.setLevelFactor(0.75);
		b1.setNextToResourceFactor(1.5);
		em.persist(b1);
		
		ResourceBuilding b2 = new ResourceBuilding();
		b2.setName("Quarry");
		b2.setImage("building_steinbruch.png");
		b2.setSmallImage("steinbruch.png");
		b2.setBuildingTime(4);
		b2.setBaseUpgradeTime(3);
		b2.setBonusFactor(0.3);
		b2.setMaxLevel(9);
		b2.setPoints(100);
		b2.getResourceCost().put(ResourceType.WOOD, 0l);
		b2.getResourceCost().put(ResourceType.STONE, 500l);
		b2.getResourceCost().put(ResourceType.IRON, 0l);
		b2.setRessource(ResourceType.STONE);
		b2.setBaseProduction(100L);
		b2.setLevelFactor(0.75);
		b2.setNextToResourceFactor(1.5);
		em.persist(b2);
		
		MilitaryBuilding b3 = new MilitaryBuilding();
		b3.setName("Barracks");
		b3.setImage("building_kaserne.png");
		b3.setSmallImage("kaserne.png");
		b3.setBuildingTime(7);
		b3.setBaseUpgradeTime(10);
		b3.setBonusFactor(0.2);
		b3.setMaxLevel(9);
		b3.setPoints(350);
		b3.getResourceCost().put(ResourceType.WOOD, 700l);
		b3.getResourceCost().put(ResourceType.STONE, 500l);
		b3.getResourceCost().put(ResourceType.IRON, 200l);
		b3.getCreateableMilitary().add(MilitaryType.INFANTRY);
		em.persist(b3);
		
		UpgradeBuilding b4 = new UpgradeBuilding();
		b4.setName("Smithy");
		b4.setImage("building_schmiede.png");
		b4.setSmallImage("schmiede.png");
		b4.setBuildingTime(10);
		b4.setBaseUpgradeTime(15);
		b4.setBonusFactor(0.25);
		b4.setMaxLevel(9);
		b4.setPoints(500);
		b4.getResourceCost().put(ResourceType.WOOD, 1000l);
		b4.getResourceCost().put(ResourceType.STONE, 750l);
		b4.getResourceCost().put(ResourceType.IRON, 500l);
		b4.getUpgradeableMilitary().add(MilitaryType.INFANTRY);
		em.persist(b4);
		
		MilitaryBuilding b5 = new MilitaryBuilding();
		b5.setName("Stable");
		b5.setImage("building_stall.png");
		b5.setSmallImage("stall.png");
		b5.setBuildingTime(12);
		b5.setBaseUpgradeTime(20);
		b5.setBonusFactor(0.2);
		b5.setMaxLevel(9);
		b5.setPoints(600);
		b5.getResourceCost().put(ResourceType.WOOD, 700l);
		b5.getResourceCost().put(ResourceType.STONE, 1000l);
		b5.getResourceCost().put(ResourceType.IRON, 600l);
		b5.getCreateableMilitary().add(MilitaryType.CAVALRY);
		em.persist(b5);
		
		MilitaryBuilding b6 = new MilitaryBuilding();
		b6.setName("Factory");
		b6.setImage("building_fabrik.png");
		b6.setSmallImage("fabrik.png");
		b6.setBuildingTime(15);
		b6.setBaseUpgradeTime(20);
		b6.setBonusFactor(0.2);
		b6.setMaxLevel(9);
		b6.setPoints(650);
		b6.getResourceCost().put(ResourceType.WOOD, 500l);
		b6.getResourceCost().put(ResourceType.STONE, 1000l);
		b6.getResourceCost().put(ResourceType.IRON, 1000l);
		b6.getCreateableMilitary().add(MilitaryType.ARTILLERY);
		em.persist(b6);
	}
}
