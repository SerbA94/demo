package com.demo.web.constants;



public final class Path {

	public static final String ROOT = "/demo";

	// controller
	public static final String IMAGE_UPLOAD = "/upload-image";

	// pages commons
	public static final String PAGE__LOGIN = "/WEB-INF/jsp/login.jsp";
	public static final String PAGE__REGISTRATION = "/WEB-INF/jsp/registration.jsp";
	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE__ACTIVATION = "/WEB-INF/jsp/inactive/activation.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/settings.jsp";
	public static final String PAGE__WELCOME = "/WEB-INF/jsp/welcome.jsp";
	public static final String PAGE__REGISTRATION_ERROR = "/WEB-INF/jsp/registration.jsp";

	// pages admin
	public static final String PAGE__ADMIN_ROOMS = "/WEB-INF/jsp/admin/room-list.jsp";
	public static final String PAGE__ADMIN_ROOM_CREATE = "/WEB-INF/jsp/admin/room-create.jsp";
	public static final String PAGE__ADMIN_ROOM_EDIT = "/WEB-INF/jsp/admin/room-edit.jsp";



	// pages customer
	public static final String PAGE__CUSTOMER_ROOMS = "/WEB-INF/jsp/customer/room-list.jsp";


	// commands
	public static final String COMMAND__ACTIVATION_MAIL = "/controller?command=activationMail";
	public static final String COMMAND__ACTIVATION = "/controller?command=activation";

	// commands view
	public static final String COMMAND__VIEW_ACTIVATION = "/controller?command=view-activation";
	public static final String COMMAND__VIEW_WELCOME = "/controller?command=view-welcome";
	public static final String COMMAND__VIEW_REGISTRATION = "/controller?command=view-registration";
	public static final String COMMAND__VIEW_SETTINGS = "/controller?command=view-settings";
	public static final String COMMAND__VIEW_LOGIN = "/controller?command=view-login";
	public static final String COMMAND__VIEW_ERROR = "/controller?command=view-error";
	public static final String COMMAND__VIEW_ROOMS = "/controller?command=view-rooms";
	public static final String COMMAND__VIEW_EDIT_ROOM = "/controller?command=view-room-edit&edit_room_id=";


	// commands admin
	public static final String COMMAND__ROOM_CREATE= "/controller?command=create-room";


















	//public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
	//public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";

}