package com.revolv.secretsanta.models;

import java.util.LinkedList;
import java.util.Observable;

public class ListModel<T> extends Observable {

	LinkedList<T> list = null;
	
	public void setList(LinkedList<T> list) {
		this.list = list;
		this.setChanged();
		this.notifyObservers();
	}
	
	public T getAt(int location) {
		if(list == null || list.size() == 0) return null;
		
		return list.get(location);
	}
	
	public void clear() {
		if(list != null) list.clear();
	}
	
	public LinkedList<T> getList() {
		return list;
	}
	
}
