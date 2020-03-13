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

	/**
	 * @param name
	 * @param data
	 */
	public Image(String name, byte[] data) {
		super();
		this.name = name;
		this.data = data;
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

	@Override
	public String toString() {
		return "Image [name=" + name + "]";
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
