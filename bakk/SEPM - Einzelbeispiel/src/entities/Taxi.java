package entities;

import java.sql.Date;
import java.sql.Timestamp;


public class Taxi implements Entity{

	private Integer id = null;
	private String brand = null;
	private String type = null;
	private String color = null;
	private String plate = null;
	private Integer seats = null;
	private Double consumption = null;
	private Boolean disabled = null;
	//private Integer main_driver = null;
	private Timestamp update = null;
	private Date create = null;
	
	/*
	 * EXTERN CONSTRUCTOR 
	 * (no id, update and create)
	 */
	
	public Taxi(String brand, String type, String color,
			String plate, Integer seats, Double consumption, Boolean disabled) {
		this.brand = brand;
		this.type = type;
		this.color = color;
		this.plate = plate;
		this.seats = seats;
		this.consumption = consumption;
		this.disabled = disabled;
	}
	
	/*
	 * INTERN CONSTRUCTOR (makes createable Object)
	 */
	
	public Taxi(Integer id) {
		this.id = id;
		this.brand = "";
		this.type = "";
		this.color = "";
		this.plate = "";
		this.seats = 3;
		this.consumption = 0.0;
		this.disabled = false;
		this.update = new Timestamp(0);
		this.create = new Date(0);
	}
	
	/*
	 * Empty CONSTRUCTOR 
	 */
	
	public Taxi() {
		//do nothing
	}
	
	/*
	 * clears all Data - empty Objekt afterwards, like when initialized
	 * (non-Javadoc)
	 * @see entities.Entity#clear()
	 */
	
	public void clear() {
		this.id = null;
		this.brand = null;
		this.type = null;
		this.color = null;
		this.plate = null;
		this.seats = null;
		this.consumption = null;
		this.disabled = null;
		//this.main_driver = null;
		this.update = null;
		this.create = null;
	}
	
	/*
	 * toString
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		return brand + " " + type + " (" + plate +")"; 
	}
	
	/*
	 *  SPECIAL GETTER - DEFENTIONS
	 */
	
	public String[] getColorArray() {
		String[] c_array = {"black","silver","red","yellow","orange","blue","green","white","violette","brown"};
		return c_array;
	}
	public String[] getBrandArray() {
		String[] b_array = {"Audi","Aston Martin","BMW","Chevrolet","Citroen","Fiat","Ford","Honda","Hummer","Mercedes","Mazda","Opel","Porsche","Suzuki","Toyota","Renault","VW"};
		return b_array;
	}
	public Integer[] getSeatsArray() {
		Integer[] s_array = {3,4,5,6,7,8,9,10};
		return s_array;
	}
	/*
	 *  GETTER AND SETTER 
	 */
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	public Double getConsumption() {
		return consumption;
	}
	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	/*public Integer getMain_driver() {
		return main_driver;
	}
	public void setMain_driver(Integer main_driver) {
		this.main_driver = main_driver;
	}*/
	public Timestamp getUpdate() {
		return update;
	}
	public void setUpdate(Timestamp update) {
		this.update = update;
	}
	public Date getCreate() {
		return create;
	}
	public void setCreate(Date create) {
		this.create = create;
	}
	
	
	
}
