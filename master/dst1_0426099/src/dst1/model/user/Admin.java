package dst1.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import dst1.model.hardware.Cluster;

@Entity
public class Admin extends Person{

	private static final long serialVersionUID = -6178756282360062714L;
	
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="cluster_admin_id")
	private Set<Cluster> cluster;

	public Set<Cluster> getCluster() {
		if(cluster == null)
			cluster = new HashSet<Cluster>();
		return cluster;
	}

	public void setCluster(Set<Cluster> cluster) {
		this.cluster = cluster;
	}
	
	
}
