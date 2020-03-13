/**
 *
 */
package com.demo.db.entity;

/**
 * @author A.Serbin
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
	private RoomClass(String title) {
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
