package com.keepard.models;

import java.io.Serializable;

public class Company implements Serializable {
	
	private int _ID;
	private String name;
	private String engName;
	private String description;
	private long code;
	private String picture;
	private String card_picture;
	private String code_format;

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

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}
	
	public String getEngName() {
		return engName;
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

	public String getCode_format() {
		return code_format;
	}

	public void setCode_format(String code_format) {
		this.code_format = code_format;
	}

	@Override
	public String toString() {
		return "Company [_ID=" + _ID + ", name=" + name + ", description="
				+ description + ", code=" + code + ", picture=" + picture
				+ ", card_picture=" + card_picture + ", code_format="
				+ code_format + "]";
	}

	
	
	
}
