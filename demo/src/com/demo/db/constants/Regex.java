/**
 *
 */
package com.demo.db.constants;

/**
 * Holder for validation regexes.
 *
 * @author A.Serbin
 *
 */
public class Regex {

	/**
	 * Password regex.
	 *
	 * The string must contain at least 1 lowercase alphabetical character.
	 * The string must contain at least 1 uppercase alphabetical character.
	 * The string must contain at least 1 numeric character.
	 * The string must contain 8 - 20 characters.
	 */
	public static final String PASSWORD = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])[A-Za-z\\d]{8,20}$";

	/**
	 *	Email regex.
	 */
	public static final String EMAIL =
			"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])"+
			"?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

	/**
	 * Login regex.
	 *
	 * Login must ends with a letter or digit
	 * Login must starts with a letter
	 * Login must contain 2 - 20 characters
	 */
	public static final String LOGIN = "^(?=.*[A-Za-z\\d]$)[A-Za-z][A-Za-z\\d.-]{1,20}$";


}
