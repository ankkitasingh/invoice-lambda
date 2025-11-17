package com.invoice.email.sender.model;

public class BankDetails {
	
	private String bsb;
    private String accountNumber;
    private String accountName;
	public String getBsb() {
		return bsb;
	}
	public void setBsb(String bsb) {
		this.bsb = bsb;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	@Override
	public String toString() {
		return "BankDetails [bsb=" + bsb + ", accountNumber=" + accountNumber + ", accountName=" + accountName + "]";
	}
    
    

}
