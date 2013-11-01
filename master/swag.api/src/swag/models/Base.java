package swag.models;

import javax.persistence.*;

import java.util.*;

import swag.models.enums.*;
import swag.util.*;
import java.io.Serializable;

@Entity
public class Base implements Serializable {

        private static final long serialVersionUID = 1L;
        @Id
        @GeneratedValue
        private Long id;

        @OneToMany(fetch = FetchType.EAGER)
        private List<BaseSquare> squares = new LinkedList<BaseSquare>();
    
    	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.EAGER)
    	@JoinTable(name="base_unitsCount_jt",
                joinColumns=@JoinColumn(name="base_unitsCount_troop"),
                inverseJoinColumns=@JoinColumn(name="base_unitsCount_long"))
    	@MapKeyJoinColumn(name="base_unitsCount_id")
    	private Map<Troop, LongEntity> unitsCount = new HashMap<Troop, LongEntity>();

        @ElementCollection(fetch = FetchType.EAGER)
        private Map<ResourceType,Long> ressourcesPerTick = new HashMap<ResourceType,Long>();
       
        @OneToOne()
        private User user;
        private Integer maxRessources;
        private Boolean starterBase;

        @OneToMany(fetch = FetchType.EAGER)
        private List<BuildListEntry> buildingList;

        @OneToMany(fetch = FetchType.EAGER)
        private List<UpgradeListEntry> upgradeList;

        @OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
        @JoinTable(name="troop_create_jt",
                   joinColumns=@JoinColumn(name="troop_create_troop"),
                   inverseJoinColumns=@JoinColumn(name="troop_create_entry"))
        @MapKeyJoinColumn(name="troop_create_id")
    	private Map<Troop, MilitaryCreateListEntry> trainingList = new HashMap<Troop, MilitaryCreateListEntry>();    	


        public Base() {
        }

        public void setSquares(List<BaseSquare> squares) {
                this.squares = squares;

        }

        public List<BaseSquare> getSquares() {
                return squares;
        }

        public void setId(Long id) {
                this.id = id;

        }

        public Long getId() {
                return id;
        }
         
        public void setMaxRessources(Integer maxRessources) {
                this.maxRessources = maxRessources;
        }

        public Integer getMaxRessources() {
                return maxRessources;
        }

        public Map<ResourceType, Long> getRessourcesPerTick() {
                return ressourcesPerTick;
        }

        public void setRessourcesPerTick(Map<ResourceType, Long> ressourcesPerTick) {
                this.ressourcesPerTick = ressourcesPerTick;
        }

        public void setStarterBase(Boolean starterBase) {
                this.starterBase = starterBase;

        }

        public Boolean getStarterBase() {
                return starterBase;
        }

        public void setBuildingList(List<BuildListEntry> buildingList) {
                this.buildingList = buildingList;

        }

        public List<BuildListEntry> getBuildingList() {
                return buildingList;
        }

        public void setUpgradeList(List<UpgradeListEntry> upgradeList) {
                this.upgradeList = upgradeList;

        }

        public List<UpgradeListEntry> getUpgradeList() {
                return upgradeList;
        }

        public void setUser(User user) {
                this.user = user;

        }

        public User getUser() {
                return user;
        }

        @Override
        public boolean equals(Object cmp) {
                if (cmp instanceof Base) {
                        return this.getId().equals(((Base) cmp).getId());
                }
                return false;
        }

		public void setUnitsCount(Map<Troop, LongEntity> unitsCount) {
			this.unitsCount = unitsCount;
		}

		public Map<Troop, LongEntity> getUnitsCount() {
			return unitsCount;
		}

		public Map<Troop, MilitaryCreateListEntry> getTrainingList() {
			return trainingList;
		}
		
		public List<Troop> getUnits() {
			List<Troop> troops = new ArrayList<Troop>();
			for(Troop t : unitsCount.keySet()) {
				troops.add(t);
			}
			return troops;
		}

		public void setTrainingList(Map<Troop, MilitaryCreateListEntry> trainingList) {
			this.trainingList = trainingList;
		}
		
		public List<MilitaryCreateListEntry> getTrainings() {
			List<MilitaryCreateListEntry> trainings = new ArrayList<MilitaryCreateListEntry>();
			Set<Troop> troops = trainingList.keySet();
			for(Troop t : troops) {
				trainings.add(trainingList.get(t));
			}
			return trainings;
		}
		
		
}