package util.compare;

import java.util.Comparator;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import entities.event.EVENTTYPE;

/**
 * Comparator that allows for alphabetical sorting of the Enum entities.event.EVENTTYPE
 * 
 * @author inso
 *
 */
@Name("EventTypeAlphabeticalComparator")
@Scope(ScopeType.STATELESS)
public class EventTypeAlphabeticalComparator implements Comparator<EVENTTYPE> {

	public int compare(EVENTTYPE et1, EVENTTYPE et2) {
		return et1.toString().compareTo(et2.toString());
	}

}
