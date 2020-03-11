/**
 *
 */
package com.demo.db.constants;

/**
 * Holder for fields names of DB tables.
 *
 * @author A.Serbin
 *
 */
public final class Fields {

	public static final String ENTITY__ID = "id";

	public static final String USER__LOGIN = "login";
	public static final String USER__PASSWORD = "password";
	public static final String USER__ROLE_ID = "role_id";
	public static final String USER__LOCALE_NAME = "locale_name";
	public static final String USER__EMAIL = "email";
	public static final String USER__ACTIVATION_TOKEN = "activation_token";

	public static final String ROLE__TITLE = "role_title";

	public static final String BOOKING__USER_ID = "user_id";
	public static final String BOOKING__ROOM_ID = "room_id";
	public static final String BOOKING__BOOKING_STATUS_ID = "booking_status_id";
	public static final String BOOKING__DATE_IN = "date_in";
	public static final String BOOKING__DATE_OUT = "date_out";
	public static final String BOOKING__DATE_OF_BOOKING = "date_of_booking";

	public static final String BOOKING_STATUS__TITLE = "booking_status_title";

	public static final String BOOKING_REQUEST__USER_ID = "user_id";
	public static final String BOOKING_REQUEST__CAPACITY = "capacity";
	public static final String BOOKING_REQUEST__DATE_IN = "date_in";
	public static final String BOOKING_REQUEST__DATE_OUT = "date_out";
	public static final String BOOKING_REQUEST__ROOM_CLASS_ID = "room_class_id";

	public static final String IMAGE__NAME = "name";
	public static final String IMAGE__DATA = "data";

	public static final String ROOM__NUMBER = "number";
	public static final String ROOM__CAPACITY = "capacity";
	public static final String ROOM__PRICE = "price";
	public static final String ROOM__DESCRIPTION = "description";
	public static final String ROOM__ROOM_STATUS_ID = "room_status_id";
	public static final String ROOM__ROOM_CLASS_ID = "room_class_id";

	public static final String ROOM_STATUS__TITLE = "room_status_title";

	public static final String ROOM_CLASS__TITLE = "room_class_title";

}