/**
 *
 */
package com.demo.db.entity;

/**
 * @author alex
 *
 */
public enum RoomClass {

	BUDGETARY("budgetary"),
	FAMILY("family"),
	BUSINESS("business"),
	LUXURY("luxury");

	private String title;

	/**
	 * @param title
	 */
	RoomClass(String title) {
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
