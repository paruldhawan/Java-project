package com.snap.model;

public class Datasource {

	private int id;
	private String name;
	private String url;
	private String database;
	private String username;
	private String password;

	
	public Datasource(int id, String name, String url, String database, String username, String password) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.database = database;
		this.username = username;
		this.password = password;
	}


	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

}
