package dao.interfaces;

import java.util.List;

import exceptions.IDaoSaveException;
import products.PartsContainer;
import products.parts.Arm;
import products.parts.Body;
import products.parts.Head;
import products.parts.Leg;
import products.parts.abstracts.Cap;
import products.parts.abstracts.Product;

/**
 * 
 * @author Favre-Bulle, Rauscha
 *
 * Data Access Object for Teddybear Parts
 */
public interface IPartDao {
	
	/**
	 * Saves an arm to Storage
	 * @param arm
	 * @throws IDaoSaveException
	 */
	public void saveArm(Arm arm) throws IDaoSaveException;
	
	/**
	 * Saves a leg to Storage
	 * @param leg
	 * @throws IDaoSaveException
	 */
	public void saveLeg(Leg leg) throws IDaoSaveException;
	
	/**
	 * Saves a body to Storage
	 * @param body
	 * @throws IDaoSaveException
	 */
	public void saveBody(Body body) throws IDaoSaveException;
	
	/**
	 * Saves a cap to Storage
	 * @param cap
	 * @throws IDaoSaveException
	 */
	public void saveCap(Cap cap) throws IDaoSaveException;
	
	/**
	 * Saves a head to Storage
	 * @param head
	 * @throws IDaoSaveException
	 */
	public void saveHead(Head head) throws IDaoSaveException;
	
	/**
	 * Get a PartsContainer filled with all Parts needed to create a teddybear
	 * @return
	 */
	public PartsContainer getAllPartsAtomic();
	
	/**
	 * Get all produced and unused Parts
	 * @return
	 */
	public List<Product> getAllParts();
}
