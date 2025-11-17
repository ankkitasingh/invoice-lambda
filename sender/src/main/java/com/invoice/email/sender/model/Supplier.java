package com.invoice.email.sender.model;

public class Supplier {
	
	private String businessName;
    private String abn;
    private String address;
    private String contact;
    private String email;
    private String phone;
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getAbn() {
		return abn;
	}
	public void setAbn(String abn) {
		this.abn = abn;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Supplier [businessName=" + businessName + ", abn=" + abn + ", address=" + address + ", contact="
				+ contact + ", email=" + email + ", phone=" + phone + "]";
	}
    
    

}
