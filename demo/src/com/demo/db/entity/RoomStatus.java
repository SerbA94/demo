/**
 *
 */
package com.demo.db.entity;

/**
 * @author alex
 *
 */
public enum RoomStatus {

	INACCESSIBLE("inaccessible"),
	OCCUPIED("occupied"),
	BOOKED("booked"),
	FREE("free");

	private String title;

	/**
	 * @param title
	 */
	private RoomStatus(String title) {
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
