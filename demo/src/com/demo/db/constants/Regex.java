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

	public static final String PASSWORD = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])[A-Za-z\\d]{8,20}$";

	public static final String EMAIL =
			"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])"+
			"?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

	public static final String LOGIN = "^(?=.*[A-Za-z\\d]$)[A-Za-z][A-Za-z\\d.-]{1,20}$";

	public static final String DATE_FORMAT = "[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}";

}
