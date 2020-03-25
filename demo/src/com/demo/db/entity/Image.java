/**
 *
 */
package com.demo.db.entity;

/**
 * @author A.Serbin
 *
 */
public class Image extends Entity {

	private static final long serialVersionUID = -1840048628095650436L;

	private String name;
    private byte[] data;
    private Long roomId;


	/**
	 * @param name
	 * @param data
	 * @param roomId
	 */
	public Image(String name, byte[] data, Long roomId) {
		super();
		this.name = name;
		this.data = data;
		this.roomId = roomId;
	}

	/**
	 * @param data
	 */
	public Image(byte[] data) {
		super();
		this.data = data;
	}

	/**
	 * @param name
	 */
	public Image(String name) {
		super();
		this.name = name;
	}

	/**
	 *
	 */
	public Image() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	@Override
	public String toString() {
		return "Image [name=" + name + "]" + System.lineSeparator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Image other = (Image) obj;
		if ((name == null && other.name != null) || !name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
