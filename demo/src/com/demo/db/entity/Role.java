/**
 *
 */
package com.demo.db.entity;

/**
 * @author alex
 *
 */
public enum Role {

	ADMIN("admin"),
	MANAGER("manager"),
	CUSTOMER("customer"),
	INACTIVE("inactive");

	private String title;

	/**
	 * @param title
	 */
	private Role(String title) {
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
