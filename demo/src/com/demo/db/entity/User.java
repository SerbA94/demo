/**
 *
 */
package com.demo.db.entity;

import java.util.Set;

/**
 * @author A.Serbin
 *
 */
public class User extends Entity {

	private static final long serialVersionUID = -6889036256149495388L;

	private String login;
	private String password;
	private String localeName;
	private String email;
	private Set<Role> role;
	private String activationToken;


	/**
	 * @param login
	 * @param password
	 * @param localeName
	 * @param email
	 * @param role
	 * @param activationToken
	 */
	public User(String login, String password, String localeName, String email, Set<Role> role,
			String activationToken) {
		super();
		this.login = login;
		this.password = password;
		this.localeName = localeName;
		this.email = email;
		this.role = role;
		this.activationToken = activationToken;
	}

	/**
	 * @param login
	 * @param password
	 * @param localeName
	 * @param email
	 * @param role
	 */
	public User(String login, String password, String localeName, String email, Set<Role> role) {
		super();
		this.login = login;
		this.password = password;
		this.localeName = localeName;
		this.email = email;
		this.role = role;
	}

	/**
	 *
	 */
	public User() {
		super();
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocaleName() {
		return localeName;
	}

	public void setLocaleName(String localeName) {
		this.localeName = localeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", email=" + email + ", role=" + role.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if ((email == null && other.email != null) || !email.equals(other.email)) {
			return false;
		}
		if ((login == null && other.login != null) || !login.equals(other.login)) {
			return false;
		}
		return true;
	}

}
