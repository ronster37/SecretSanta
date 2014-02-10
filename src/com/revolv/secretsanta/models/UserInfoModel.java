package com.revolv.secretsanta.models;

import java.util.Observable;

public class UserInfoModel extends Observable {
	private String name = null;
	private String family;

	public UserInfoModel() {
		this(null, null);
	}
	
	public UserInfoModel(String name, String family) {
		this.name = name;
		this.family = family;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setName(String name) {
		this.name = name;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setFamily(String family) {
		this.family = family;
		this.setChanged();
		this.notifyObservers();
	}

	public String getName() {
		return name;
	}

	public String getRelationship() {
		return family;
	}
	
}
