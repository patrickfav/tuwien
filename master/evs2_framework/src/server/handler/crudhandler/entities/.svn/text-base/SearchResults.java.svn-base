/**
 * VU Entwurfsmethoden fuer Verteilte Systeme (184.153)
 * Assignment 1
 * Florian Rauscha
 * 0604411
 */
package server.handler.crudhandler.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper Class for Search Results
 */
@XmlRootElement
public class SearchResults {
	
	@XmlElementWrapper(name="value")
	private List<?> results;
	
	public void setResults(List<?> results) {
		this.results = results;
	}
	
	public List<?> getIt() {
		return results;
	}
	
}
