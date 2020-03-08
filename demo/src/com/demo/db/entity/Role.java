package com.demo.db.entity;

public enum Role {

	ADMIN("admin"), CUSTOMER("customer"), INACTIVE("inactive");

	private String title;

	Role(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return title;
	}
}
