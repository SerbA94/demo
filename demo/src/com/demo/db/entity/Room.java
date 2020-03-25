/**
 *
 */
package com.demo.db.entity;

import java.util.List;
import java.util.Set;

/**
 * @author A.Serbin
 *
 */
public class Room extends Entity {

	private static final long serialVersionUID = -3526881698687457508L;

	private Integer number;
	private Integer capacity;
	private Integer price;
	private List<Image> images;
	private Set<RoomStatus> roomStatus;
	private Set<RoomClass> roomClass;
	private List<Description> descriptions;


	/**
	 * @param number
	 * @param capacity
	 * @param price
	 * @param images
	 * @param roomStatus
	 * @param roomClass
	 * @param descriptions
	 */
	public Room(Integer number, Integer capacity, Integer price, List<Image> images,
			Set<RoomStatus> roomStatus, Set<RoomClass> roomClass, List<Description> descriptions) {
		super();
		this.number = number;
		this.capacity = capacity;
		this.price = price;
		this.images = images;
		this.roomStatus = roomStatus;
		this.roomClass = roomClass;
		this.descriptions = descriptions;
	}

	/**
	 *
	 */
	public Room() {
		super();
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public Set<RoomStatus> getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Set<RoomStatus> roomStatus) {
		this.roomStatus = roomStatus;
	}

	public Set<RoomClass> getRoomClass() {
		return roomClass;
	}

	public void setRoomClass(Set<RoomClass> roomClass) {
		this.roomClass = roomClass;
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<Description>  descriptions) {
		this.descriptions = descriptions;
	}

	@Override
	public String toString() {
		return "Room [number=" + number + ", capacity=" + capacity
				+ ", price=" + price + ", images=" + images
				+ ", roomStatus=" + roomStatus + ", roomClass="
				+ roomClass + "]" + System.lineSeparator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capacity == null) ? 0 : capacity.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((roomClass == null) ? 0 : roomClass.hashCode());
		result = prime * result + ((roomStatus == null) ? 0 : roomStatus.hashCode());
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
		Room other = (Room) obj;
		if ((capacity == null&& other.capacity != null) || !capacity.equals(other.capacity)) {
			return false;
		}
		if ((number == null && other.number != null) || !number.equals(other.number)) {
			return false;
		}
		return true;
	}
}
