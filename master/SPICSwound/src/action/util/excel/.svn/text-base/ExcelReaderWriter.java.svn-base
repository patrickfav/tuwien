package util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import entities.TrialData;
import entities.value.FileValue;
import entities.value.FileValueSet;
import entities.value.Value;

public class ExcelReaderWriter implements IExcelReaderWriter {
	
	private static final long serialVersionUID = 1L;

	public void writeTrialDataToXls(TrialData td, OutputStream os)
			throws ExcelImportExportException {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("data");

		HSSFCellStyle dateStyle = wb.createCellStyle();
		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

		int cnt = 0;
		for (Value v : td.getValues()) {
			
			// attachments won't be exported
			if(v instanceof FileValue || v instanceof FileValueSet)
				continue;
			
			HSSFRow row = sheet.createRow(cnt);

			row.createCell(0).setCellValue(v.getAttribute().getName());

			switch (v.getType()) {
			case DATE:
				HSSFCell cell = row.createCell(1);
				cell.setCellValue((Date) v.getValueAsObject());
				cell.setCellStyle(dateStyle);
				break;
			case INTEGER:
				row.createCell(1).setCellValue((Integer) v.getValueAsObject());
				break;
			case STR:
				row.createCell(1).setCellValue((String) v.getValueAsObject());
				break;
			case DECIMAL:
				row.createCell(1).setCellValue(
						((BigDecimal) v.getValueAsObject()).doubleValue());
				break;
			default:
				row.createCell(1).setCellValue(v.getValueAsObject().toString());
			}

			cnt++;
		}

		try {
			wb.write(os);
		} catch (IOException e) {
			throw new ExcelImportExportException(e);
		}
	}

	public Map<String, Serializable> readXLSX(String filename)
			throws ExcelImportExportException {
		return readXLSX(new File(filename));
	}

	private Serializable getData(Cell c) {

		if (c == null)
			return null;

		switch (c.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return c.getRichStringCellValue().getString();
		case Cell.CELL_TYPE_NUMERIC:

			// excel saves dates as doubles. therefore we have to distinguish
			if (HSSFDateUtil.isCellDateFormatted(c)) {
				return HSSFDateUtil.getJavaDate(c.getNumericCellValue());
			} else {
				// XXX: api isn't able to read exact values!!!!
				return BigDecimal.valueOf(c.getNumericCellValue()).setScale(2,
						RoundingMode.HALF_UP);
			}
		default:
			return c.toString();
		}
	}
	
	public Map<String, Serializable> readXLSX(File file) throws ExcelImportExportException{
		
		InputStream inp;
		try {
			inp = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ExcelImportExportException(e);
		}
		
		return this.readXLSX(inp);
	}

	public Map<String, Serializable> readXLSX(InputStream is)
			throws ExcelImportExportException {
		// property map to save the data from the excel file
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();

		

		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(is);
		} catch (Exception e) {
			throw new ExcelImportExportException(e);
		}

		Sheet sheet = wb.getSheetAt(0);

		int rowNr = 0;
		Row row = null;

		while ((row = sheet.getRow(rowNr)) != null) {

			Serializable key = getData(row.getCell(0));
			Serializable value = getData(row.getCell(1));

			// key or value is null. skip this row
			if (key == null || value == null) {
				// TODO: add logger
				continue;
			}

			map.put(key.toString(), value);

			rowNr++;
		}

		return map;
	}
	
}
