package entities.internal;

import entities.Product;

public class StockStatus {
	
	private Product product;
	private int quantity;
	private int delivery_time;
	
	public StockStatus(Product product, int quantity, int delivery_time) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.delivery_time = delivery_time;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(int delivery_time) {
		this.delivery_time = delivery_time;
	}
	
	@Override
	public String toString() {
		return quantity +"x  "+product+" | "+delivery_time +" days|";
	}
	
}
