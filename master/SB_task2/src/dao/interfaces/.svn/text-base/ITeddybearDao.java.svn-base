package dao.interfaces;

import java.util.List;

import exceptions.IDaoReadException;
import exceptions.IDaoSaveException;
import products.Teddybear;

/**
 * 
 * @author Favre-Bulle, Rauscha
 *
 * Data Access Object for Teddybear
 */
public interface ITeddybearDao {
	
	/**
	 * Saves produced Teddybear to assembled storage
	 * @param teddybear
	 * @throws IDaoSaveException
	 */
	public void saveToAssembled(Teddybear teddybear) throws IDaoSaveException;
	
	/**
	 * Returns and removes the first Teddybear in assembledList with template checker
	 * @return
	 * @throws IDaoReadException
	 */
	public Teddybear takeAssembledForDefectCheck() throws IDaoReadException;
	
	/**
	 * Returns and removes the first Teddybear in assembledList with template checker
	 * @return
	 * @throws IDaoReadException
	 */
	public Teddybear takeAssembledForWeightCheck() throws IDaoReadException;
	
	/**
	 * Moves an assembled Teddybear to checked storage
	 * @param teddybear
	 * @throws IDaoSaveException
	 */
	public void saveToChecked(Teddybear teddybear) throws IDaoSaveException;	
	

	/**
	 * Returns and removes a teddybear from checked storage
	 * @return
	 * @throws IDaoReadException
	 */
	public Teddybear takeChecked() throws IDaoReadException;
	
	/**
	 * Saves a teddybear to shipping storage
	 * @param teddybear
	 * @throws IDaoSaveException
	 */
	public void saveToShipping(Teddybear teddybear) throws IDaoSaveException;
	
	/**
	 * Saves a teddybear to defect storage
	 * @param teddybear
	 * @throws IDaoSaveException
	 */
	public void saveToDefect(Teddybear teddybear) throws IDaoSaveException;
	
	/**
	 * Returns the assembled teddy bears
	 * @return
	 */
	public List<Teddybear> getAssembled();
	
	/**
	 * Returns the checked teddy bears
	 * @return
	 */
	public List<Teddybear> getChecked();
	
	/**
	 * Returns the shipped teddy bears
	 * @return
	 */
	public List<Teddybear> getShipped();
	
	/**
	 * Returns the defect teddy bears
	 * @return
	 */
	public List<Teddybear> getDefect();

}
