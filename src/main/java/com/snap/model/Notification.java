package com.snap.model;

import java.util.List;

public class Notification {
	
	private int id;
	private String name;
	private String type;
	private List<String> address;
	private String username;
	public Notification(int id, String name, String type,List<String> address2, String username) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.address = address2;
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getAddress() {
		return address;
	}
	public void setAddress(List<String> address) {
		this.address = address;
	}
	
	
	@Override
	public String toString() {
		return "Notification [id=" + id + ", name=" + name + ", type=" + type + ", address=" + address + ", username="
				+ username + "]";
	}
	
	
	

}
