/**
 *
 */
package com.demo.db.entity;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author A.Serbin
 *
 */
public class Booking extends Entity {

	private static final long serialVersionUID = 871358725825790601L;

	private User user;
	private Room room;
	private Set<BookingStatus> bookingStatus;
	private Timestamp dateIn;
	private Timestamp dateOut;
	private Timestamp dateOfBooking;

	/**
	 * @param user
	 * @param room
	 * @param bookingStatus
	 * @param dateIn
	 * @param dateOut
	 * @param dateOfBooking
	 */
	public Booking(User user, Room room, Set<BookingStatus> bookingStatus,
			Timestamp dateIn,Timestamp dateOut,Timestamp dateOfBooking) {
		super();
		this.user = user;
		this.room = room;
		this.bookingStatus = bookingStatus;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.dateOfBooking = dateOfBooking;
	}

	/**
	 *
	 */
	public Booking() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Set<BookingStatus> getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(Set<BookingStatus> bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Timestamp getDateIn() {
		return dateIn;
	}

	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}

	public Timestamp getDateOut() {
		return dateOut;
	}

	public void setDateOut(Timestamp dateOut) {
		this.dateOut = dateOut;
	}

	public Timestamp getDateOfBooking() {
		return dateOfBooking;
	}

	public void setDateOfBooking(Timestamp dateOfBooking) {
		this.dateOfBooking = dateOfBooking;
	}

	@Override
	public String toString() {
		return "Booking [user=" + user + ", room=" + room + ", bookingStatus="
				+ bookingStatus + ", dateIn=" + dateIn+ ", dateOut="
				+ dateOut + ", dateOfBooking=" + dateOfBooking + "]" + System.lineSeparator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookingStatus == null) ? 0 : bookingStatus.hashCode());
		result = prime * result + ((dateIn == null) ? 0 : dateIn.hashCode());
		result = prime * result + ((dateOfBooking == null) ? 0 : dateOfBooking.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Booking other = (Booking) obj;

		if(( dateIn == null && other.dateIn != null ) || !dateIn.equals(other.dateIn) ) {
			return false;
		}
		if(( dateOut == null && other.dateOut != null ) || !dateOut.equals(other.dateOut) ) {
			return false;
		}
		if(( room == null && other.room != null ) || !room.equals(other.room) ) {
			return false;
		}
		if(( user == null && other.user != null ) || !user.equals(other.user) ) {
			return false;
		}
		return true;
	}


}
