/**
 *
 */
package com.demo.db.entity;

/**
 * @author A.Serbin
 *
 */
public enum BookingRequestStatus {

	INACTIVE("inactive"),
	ACTIVE("active");

	private String title;

	/**
	 * @param title
	 */
	private BookingRequestStatus(String title) {
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
