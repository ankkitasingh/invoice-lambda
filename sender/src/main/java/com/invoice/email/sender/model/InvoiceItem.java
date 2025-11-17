package com.invoice.email.sender.model;

public class InvoiceItem {

	private String description;
    private int quantity;
    private double unitPrice;
    private double gst;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getGst() {
		return gst;
	}
	public void setGst(double gst) {
		this.gst = gst;
	}
	@Override
	public String toString() {
		return "InvoiceItem [description=" + description + ", quantity=" + quantity + ", unitPrice=" + unitPrice
				+ ", gst=" + gst + "]";
	}
    
    
}
