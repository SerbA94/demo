package com.demo.web.constants;



public final class Path {

	// pages
	public static final String PAGE__LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String PAGE__REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/settings.jsp";
	public static final String PAGE__WELCOME = "/WEB-INF/jsp/welcome.jsp";
	public static final String PAGE__ERROR = "/WEB-INF/jsp/error.jsp";

	public static final String PAGE__ADMIN_ROOM_LIST = "/WEB-INF/jsp/admin/room-list.jsp";
	public static final String PAGE__ADMIN_ROOM_CREATE = "/WEB-INF/jsp/admin/room-create.jsp";
	public static final String PAGE__ADMIN_ROOM_EDIT = "/WEB-INF/jsp/admin/room-edit.jsp";

	public static final String PAGE__CUSTOMER_ROOM = "/WEB-INF/jsp/customer/room.jsp";
	public static final String PAGE__CUSTOMER_ROOM_LIST = "/WEB-INF/jsp/customer/room-list.jsp";
	public static final String PAGE__CUSTOMER_BOOKING_REQUEST_CREATE = "/WEB-INF/jsp/customer/booking-request-create.jsp";
	public static final String PAGE__CUSTOMER_BOOKING_CREATE = "/WEB-INF/jsp/customer/booking-create.jsp";
	public static final String PAGE__CUSTOMER_ACCOUNT = "/WEB-INF/jsp/customer/account.jsp";

	public static final String PAGE__MANAGER_BOOKING_REQUEST = "/WEB-INF/jsp/manager/booking-request.jsp";
	public static final String PAGE__MANAGER_BOOKING_REQUEST_LIST = "/WEB-INF/jsp/manager/booking-request-list.jsp";

	public static final String PAGE__INACTIVE_ACTIVATION = "/WEB-INF/jsp/inactive/activation.jsp";

	// view commands
	public static final String COMMAND__VIEW_LOGIN = "/controller?command=view-login";
	public static final String COMMAND__VIEW_REGISTRATION = "/controller?command=view-registration";
	public static final String COMMAND__VIEW_SETTINGS = "/controller?command=view-settings";
	public static final String COMMAND__VIEW_WELCOME = "/controller?command=view-welcome";
	public static final String COMMAND__VIEW_ERROR = "/controller?command=view-error";
	public static final String COMMAND__VIEW_IMAGE = "/controller?command=view-image";
	public static final String COMMAND__VIEW_ROOM_LIST = "/controller?command=view-room-list";
	public static final String COMMAND__VIEW_ROOM_CREATE = "/controller?command=view-room-create";
	public static final String COMMAND__VIEW_ROOM_EDIT = "/controller?command=view-room-edit";
	public static final String COMMAND__VIEW_ROOM = "/controller?command=view-room";
	public static final String COMMAND__VIEW_BOOKING_REQUEST_CREATE = "/controller?command=view-booking-request-create";
	public static final String COMMAND__VIEW_BOOKING_CREATE = "/controller?command=view-booking-create";
	public static final String COMMAND__VIEW_ACCOUNT = "/controller?command=view-account";
	public static final String COMMAND__VIEW_BOOKING_REQUEST = "/controller?command=view-booking-request";
	public static final String COMMAND__VIEW_BOOKING_REQUEST_LIST = "/controller?command=view-booking-request-list";
	public static final String COMMAND__VIEW_ACTIVATION = "/controller?command=view-activation";

	// commands reserv
	public static final String COMMAND__ROOM_CREATE = "/controller?command=create-room";
	public static final String COMMAND__ROOM_EDIT = "/controller?command=edit-room";
	public static final String COMMAND__IMAGE_UPLOAD = "/controller?command=upload-image";
	public static final String COMMAND__IMAGE_DELETE = "/controller?command=delete-image";
	public static final String COMMAND__ACTIVATION_MAIL = "/controller?command=activation-mail";
	public static final String COMMAND__ACTIVATION = "/controller?command=activation";
	public static final String COMMAND__LOGIN = "/controller?command=login";
	public static final String COMMAND__LOGOUT = "/controller?command=logout";
	public static final String COMMAND__REGISTRATION = "/controller?command=registration";
	public static final String COMMAND__NO_COMMAND = "/controller?command=no-command";
	public static final String COMMAND__SETTINGS_UPDATE = "/controller?command=update-settings";

}