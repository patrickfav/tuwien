package aufgabe3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RecordFolder implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7789968994510698917L;
	private int id;
	private Date timestamp;
	private Station station;
	private HashMap<String,Record> records = new HashMap<String,Record>();
	private String flag;
	private String comment;
	
	/**
	 * Constructor
	 * @param id
	 * @param timestamp
	 * @param station
	 * @param records
	 * @param flag
	 * @param comment
	 */
	public RecordFolder(int id, Date timestamp, Station station) {
		this.id = id;
		this.timestamp = timestamp;
		this.station = station;
	}
	/**
	 * Returns the iterative id from the RecordFolder given by the Station
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter to change the iterative id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Return the Date
	 * @return
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * Setter to change the Date of the RecordFolder
	 * @param timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/** 
	 * Returns the Station
	 * @return
	 */
	public Station getStation() {
		return station;
	}
	/**
	 * Setter to change the Station of the RecordFolder
	 * @param station
	 */
	public void setStation(Station station) {
		this.station = station;
	}
	/**
	 * Returns the RecordFolder flag. 
	 * The Flag sets the state of the RecordFolder
	 * Active/Inactive/Destroyed
	 * @return
	 */
	public String getFlag() {
		return flag;
	}
	/**
	 * Changes the state of the RecordFolder 
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}	
	/**
	 * Returns the comment of the RecordFolder 
	 * @return
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * Sets the comment
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * Return the ArrayList full of records
	 * @return
	 */
	public HashMap<String,Record> getRecords() {
		return records;
	}
	/**
	 * Adds a Record to the HashMap
	 * @param record
	 * @return true or false
	 */
	public boolean addRecord(Record record) {
		records.put(record.getParameter().getName(),record);
		return true;
	}
	/**
	 * This method removes a record from the HashMap
	 * @param record
	 * @return true or false
	 */
	public boolean deleteRecord(Record record) {
		if(records.containsKey(record.getParameter().getName())) {
			records.remove(record.getParameter().getName());
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Returns an output string of all records ordered by parameter
	 */
	public String toString() {
		String output;
		
		List<String> sortedList = new ArrayList<String>();
		sortedList.addAll(records.keySet());
		Collections.sort(sortedList);

		output = "================ " + timestamp.toString() + " ================\n";
		
		Iterator<String> iter = sortedList.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Record rec = records.get(key);
			float value = rec.getValue();
			Parameter param = rec.getParameter();
			String symbol = param.getUnitSymbol();
			
			
			output += key + ": " + value + symbol;
			if(rec.getValue() > param.getThreshold())
				output += " Warnung: Wert liegt ueber dem Grenzwert!\n";
			else
				output += "\n";
		}
		
		return output;
	}
	
}
