package util.excel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import entities.Attribute;
import entities.Patient;
import entities.Trial;
import entities.TrialData;
import entities.TrialForm;
import entities.value.DateValue;
import entities.value.DecimalValue;
import entities.value.LongstringValue;
import entities.value.Value;

/**
 * All this code in here is just for demo purposes and has to be revised
 * before going into production use!
 * 
 * @author inso
 *
 */
public class FullTrialDataExport {
	
	private static final String exportDateString = "dd.MM.yyyy";
	private static final String exportTimeString = "hh:mm:ss";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(exportDateString);
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(exportDateString + " " + exportTimeString);
	private static final NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);
	
	/*
	 * alle header die am anfang des exports stehen
	 */
	public static final String[] unhashedHeaders = new String[] { "PatientID",
			"ArztID", "FormularName",
			"Erstellungsdatum", "Modifikationsdatum", "archiviert" };
	
	public static final String[] hashedHeaders = new String[] { "UniquePatientHash",
		"FormularName", "Erstellungsdatum", "Modifikationsdatum", "archiviert" };
	
	private Trial trial;
	private Map<Long, Integer> attributeOffsets;
	private List<String> headers;
	private boolean hashPatientAndUserId;
	
	public FullTrialDataExport(Trial trial) {
		this(trial, true);
	}
	
	public FullTrialDataExport(Trial trial, boolean hashPatientAndUserId) {
		this.trial = trial;
		this.hashPatientAndUserId = hashPatientAndUserId;
		attributeOffsets = new HashMap<Long, Integer>();
		init(trial);
	}

	/**
	 * achtung... das geht nur bis zu einer gewissen größe, dann müssen wir das iterativ machen!!
	 * @param trialDataCnt
	 * @return
	 */
	@Deprecated
	public String[][] export(int trialDataCnt) {
		String[][] result = new String[trialDataCnt+1][headers.size()];		
		headers.toArray(result[0]);
		int line = 1;
		for(Patient p : trial.getAllPatients()) {
			for(TrialData td : p.getTrialdatas()) {
				buildLine(result[line], td);
				line++;
			}
		}	
		
		return result;
	}
	
	public String[][] export(List<TrialData> trialDatas) {
		String[][] result = new String[trialDatas.size()+1][headers.size()];		
		headers.toArray(result[0]);

		int line = 1;
		for(TrialData td : trialDatas) {
			buildLine(result[line], td);
			line++;
		}
		
		return result;
	}
	
	private void buildLine(String[] line, TrialData td) {
		buildIntro(line, td);
		buildTrialData(line, td);
	}
	
	private void buildTrialData(String[] line, TrialData td) {
		String stringValue = "";
		for(Value v : td.getValues()) {
			switch(v.getType()) {
			case LONGSTR:
				stringValue = removeCSVCharacters(((LongstringValue)v).getValue());
				break;
			case FILESET:
				stringValue = "files_not_supported";	// TODO: solution?
				break;
			case DATE:
				stringValue = dateFormat.format(((DateValue)v).getValue());
				break;
			case DECIMAL:
				stringValue = numberFormat.format(((DecimalValue)v).getValue());
				break;
			default:
				stringValue = v.getValueAsObject().toString();
				break;
			}
			line[attributeOffsets.get(v.getAttribute().getId())] = stringValue;
		}

	}

	/**
	 * removes ';' and newline characters and replaces them with a regular space
	 * @param string
	 * @return
	 */
	private String removeCSVCharacters(String string) {
		return string.replace(';', ' ').replace('\n', ' ').replace('\r', ' ');
	}

	private void buildIntro(String[] line, TrialData td) {
		int lineCnt = 0;
		if(hashPatientAndUserId) {
			line[lineCnt++] = hashPatientAndUser(td.getPatient().getKennnummer(), td.getPatient().getSavedBy().getUsername());
		} else {
			line[lineCnt++] = (td.getPatient().getKennnummer());
			line[lineCnt++] = (td.getPatient().getSavedBy().getUsername());
		}
		line[lineCnt++] = (td.getTrialform().getName());
		line[lineCnt++] = (dateTimeFormat.format(td.getSavedOn()));
		line[lineCnt++] = (dateTimeFormat.format(td.getLastModified())); 
		line[lineCnt++] = (td.getTrialform().getArchived().toString());
	}


	private String hashPatientAndUser(String kennnummer, String username) {
		int hash = kennnummer.concat(username).hashCode();
		if(hash < 0) {
			hash *= -1;
		}
		return String.valueOf(hash);
	}

	private void init(Trial trial) {
		List<String> result = new LinkedList<String>();
		result.addAll(hashPatientAndUserId ? Arrays.asList(hashedHeaders) : Arrays.asList(unhashedHeaders));
		
		Iterator<TrialForm> tfIter = new TrialFormIterator(trial);
		int attCnt = hashPatientAndUserId ? hashedHeaders.length : unhashedHeaders.length;
		
		while(tfIter.hasNext()) {
			TrialForm current = tfIter.next();
			
			for(Attribute att : current.fullSortedAttributeList()) {
				result.add(att.getName());
				attributeOffsets.put(att.getId(), attCnt);
				attCnt++;
			}
		}

		this.headers = result;

	}

	private class TrialFormIterator implements Iterator<TrialForm> {

		private final List<TrialForm> trialForms;
		private final Iterator<TrialForm> activeTFIterator;
		private TrialForm current;

		public TrialFormIterator(Trial trial) {
			super();
			this.trialForms = trial.getTrialForms();
			Collections.sort(this.trialForms);
			this.activeTFIterator = trialForms.iterator();
		}

		public boolean hasNext() {
			
			boolean hasNext = activeTFIterator.hasNext();
			if(current != null && !hasNext) {
				return current.getPredecessor() != null;
			}
			return hasNext;
		}

		public TrialForm next() {
			if (current != null && current.getPredecessor() != null) {
				current = current.getPredecessor();
				return current;
			}
			current = activeTFIterator.next();
			return current;
		}

		public void remove() {
			throw new UnsupportedOperationException(
					"remove on TrialFormIterator");

		}

	}
	

}
