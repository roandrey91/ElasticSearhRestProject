package com.fortech.data;

public class AutoComplete {

	private int id;
	private	String displayName;
	private String name;

	public AutoComplete() {
		
	}
	
	public AutoComplete(int id, String displayName, String name) {
		this.id = id;
		this.displayName = displayName;
		this.name = name;
	}
			
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AutoComplete [id=" + id + ", displayName=" + displayName + ", name=" + name + "]";
	}



}
