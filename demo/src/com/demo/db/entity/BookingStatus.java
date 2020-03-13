/**
 *
 */
package com.demo.db.entity;

/**
 * @author A.Serbin
 *
 */
public enum BookingStatus {

	UNCONFIRMED("unconfirmed"),
	NOT_PAID("not paid"),
	EXPIRED("expired"),
	ACTIVE("active");

	private String title;

	/**
	 * @param title
	 */
	private BookingStatus(String title) {
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
