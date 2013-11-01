package bean.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import util.ValueFactory;
import util.ValueFactoryBean;
import util.excel.ExcelImportExportException;
import util.excel.ExcelReaderWriter;
import util.excel.IExcelReaderWriter;
import db.interfaces.ITrialDataDAO;
import db.interfaces.IValueDAO;
import entities.Attribute;
import entities.AttributeGroup;
import entities.TrialData;
import entities.formelement.DATATYPE;
import entities.value.Value;

@Name("trialDataImportAction")
@Scope(ScopeType.STATELESS)
@AutoCreate
public class TrialDataImportAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Logger
	private Log log;

	@In
	private IValueDAO valueDAO;

	@In
	private ITrialDataDAO trialDataDAO;

	private ValueFactory valueFactory = new ValueFactoryBean();

	private IExcelReaderWriter excelReader = new ExcelReaderWriter();

	public TrialDataImportResult importTrialData(Long editTrialDataID, File file)
			throws ExcelImportExportException {

		InputStream is;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ExcelImportExportException(e);
		}

		return this.importTrialData(editTrialDataID, is);
	}

	public TrialDataImportResult importTrialData(Long editTrialDataID,
			InputStream is) throws ExcelImportExportException {

		// map to collect invalid date values. this map allows further
		// processing
		// (not implemented atm)
		Map<Value, Serializable> invalidDateValues = new HashMap<Value, Serializable>();

		// map to store the values from the xls
		Map<String, Serializable> valueMap = null;

		int importCnt = 0;

		TrialData trialData = trialDataDAO.findByID(editTrialDataID);

		valueMap = excelReader.readXLSX(is);

		log.info("sucessfully loaded data from excel sheet");

		for (AttributeGroup aGroup : trialData.getTrialform()
				.getAttributeGroups()) {

			for (Attribute attr : aGroup.getAttributes()) {

				// search attribute in map
				Serializable mapVal = valueMap.get(attr.getName());

				if (mapVal == null)
					continue;

				importCnt++;
				
				Value value = valueFactory.getValueObject(attr.getFormElement()
						.getDataType());

				try {
					value.setValueObject(mapVal);
				} catch (IllegalArgumentException e) {
					if (value.getType() == DATATYPE.DATE) {
						invalidDateValues.put(value, mapVal);
					}
				}

				value.setAttribute(attr);
				value.setTrialData(trialData);

				trialData.getValues().add(value);
				valueDAO.persist(value);
			}
		}

		TrialDataImportResult result = new TrialDataImportResult();
		result.setImportCnt(importCnt);
		result.setSuccCnt(valueMap.keySet().size());
		result.setTrialData(trialData);

		return result;
	}
}
