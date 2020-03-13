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
public class BookingRequest extends Entity {

	private static final long serialVersionUID = -4959112271698525301L;

	private User user;
	private Integer capacity;
	private Timestamp dateIn;
	private Timestamp dateOut;
	private Set<RoomClass> roomClass;

	/**
	 * @param user
	 * @param capacity
	 * @param dateIn
	 * @param dateOut
	 * @param roomClass
	 */
	public BookingRequest(User user, Integer capacity, Timestamp dateIn,
			Timestamp dateOut, Set<RoomClass> roomClass) {
		super();
		this.user = user;
		this.capacity = capacity;
		this.dateIn = dateIn;
		this.dateOut = dateOut;
		this.roomClass = roomClass;
	}

	/**
	 *
	 */
	public BookingRequest() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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

	public Set<RoomClass> getRoomClass() {
		return roomClass;
	}

	public void setRoomClass(Set<RoomClass> roomClass) {
		this.roomClass = roomClass;
	}

	@Override
	public String toString() {
		return "BookingRequest [user=" + user + ", capacity=" + capacity + ", dateIn="
				+ dateIn + ", dateOut=" + dateOut + ", roomClass=" + roomClass + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((dateIn == null) ? 0 : dateIn.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
		result = prime * result + ((roomClass == null) ? 0 : roomClass.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BookingRequest other = (BookingRequest) obj;
		if ((capacity == null && other.capacity != null) || !capacity.equals(other.capacity)) {
			return false;
		}
		if (( dateIn == null && other.dateIn != null ) || !dateIn.equals(other.dateIn)) {
			return false;
		}
		if (( dateOut == null && other.dateOut != null ) || !dateOut.equals(other.dateOut) ) {
			return false;
		}
		if (( roomClass == null && other.roomClass != null ) || !roomClass.equals(other.roomClass) ) {
			return false;
		}
		if (( user == null && other.user != null ) || !user.equals(other.user) ) {
			return false;
		}
		return true;
	}

}
