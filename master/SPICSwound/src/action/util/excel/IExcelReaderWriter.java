package util.excel;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

import entities.TrialData;

public interface IExcelReaderWriter extends Serializable {

	public Map<String, Serializable> readXLSX(String filename)
			throws ExcelImportExportException;

	public Map<String, Serializable> readXLSX(File file)
			throws ExcelImportExportException;

	public Map<String, Serializable> readXLSX(InputStream is)
			throws ExcelImportExportException;

	public void writeTrialDataToXls(TrialData td, OutputStream os)
			throws ExcelImportExportException;
}
