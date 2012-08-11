package com.keepard.models;

import java.io.Serializable;

public class Company implements Serializable {
	
	private int _ID;
	private String name;
	private String description;
	private int code;
	private String picture;
	private String card_picture;

	public Company() {}

	public int get_ID() {
		return _ID;
	}

	public void set_ID(int _ID) {
		this._ID = _ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getCard_picture() {
		return card_picture;
	}

	public void setCard_picture(String card_picture) {
		this.card_picture = card_picture;
	}

	@Override
	public String toString() {
		return "Company [_ID=" + _ID + ", name=" + name + ", description="
				+ description + ", code=" + code + ", picture=" + picture
				+ ", card_picture=" + card_picture + "]";
	}

	
	
	
}
