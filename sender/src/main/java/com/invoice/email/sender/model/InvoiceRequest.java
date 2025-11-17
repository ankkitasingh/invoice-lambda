package com.invoice.email.sender.model;

import java.util.List;

public class InvoiceRequest {

	private String invoiceNumber;
    private String issueDate;
    private Supplier supplier;
    private Buyer buyer;
    private List<InvoiceItem> items;
    private double totalAmount;
    private double gstAmount;
    private String paymentTerms;
    private BankDetails bankDetails;
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public List<InvoiceItem> getItems() {
		return items;
	}
	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getGstAmount() {
		return gstAmount;
	}
	public void setGstAmount(double gstAmount) {
		this.gstAmount = gstAmount;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public BankDetails getBankDetails() {
		return bankDetails;
	}
	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}
	@Override
	public String toString() {
		return "InvoiceRequest [invoiceNumber=" + invoiceNumber + ", issueDate=" + issueDate + ", supplier=" + supplier
				+ ", buyer=" + buyer + ", items=" + items + ", totalAmount=" + totalAmount + ", gstAmount=" + gstAmount
				+ ", paymentTerms=" + paymentTerms + ", bankDetails=" + bankDetails + "]";
	}
    
    
    
    
	
	

}
