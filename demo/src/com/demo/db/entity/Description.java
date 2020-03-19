/**
 *
 */
package com.demo.db.entity;

/**
 * @author A.Serbin
 *
 */
public class Description extends Entity {

	private static final long serialVersionUID = -2032268870013992516L;

	private String localeName;
	private String description;
	private Long roomId;

	/**
	 * @param localeName
	 * @param description
	 * @param roomId
	 */
	public Description(String localeName, String description, Long roomId) {
		super();
		this.localeName = localeName;
		this.description = description;
		this.roomId = roomId;
	}

	/**
	 *
	 */
	public Description() {
		super();
	}

	public String getLocaleName() {
		return localeName;
	}

	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	@Override
	public String toString() {
		return "Description [localeName=" + localeName + ", roomId=" + roomId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((localeName == null) ? 0 : localeName.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Description other = (Description) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (localeName == null) {
			if (other.localeName != null) {
				return false;
			}
		} else if (!localeName.equals(other.localeName)) {
			return false;
		}
		if (roomId == null) {
			if (other.roomId != null) {
				return false;
			}
		} else if (!roomId.equals(other.roomId)) {
			return false;
		}
		return true;
	}
}
