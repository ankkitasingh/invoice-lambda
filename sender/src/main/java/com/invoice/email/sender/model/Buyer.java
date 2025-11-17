package com.invoice.email.sender.model;

public class Buyer {
	
	
	private String name;
    private String businessName;
    private String abn;
    private String address;
    private String email;
    private Long id;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Buyer [name=" + name + ", businessName=" + businessName + ", abn=" + abn + ", address=" + address
				+ ", email=" + email + ", id=" + id + "]";
	}
    
    
	

}
