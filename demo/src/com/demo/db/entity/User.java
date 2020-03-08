package com.demo.db.entity;

import java.util.Set;

public class User extends Entity {

	private static final long serialVersionUID = -6889036256149495388L;

	private String login;
	private String password;
	private String localeName;
	private String email;
	private Set<Role> role;
	private String activationToken;

	public User(String login, String password, String localeName, String email, Set<Role> role) {
		super();
		this.login = login;
		this.password = password;
		this.localeName = localeName;
		this.email = email;
		this.role = role;
	}

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

}
