package com.demo.web.constants;



public final class Path {

	// pages
	public static final String PAGE__LOGIN = "/login.jsp";
	public static final String PAGE__REGISTRATION = "/registration.jsp";
	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE__ACTIVATION = "/WEB-INF/jsp/inactive/activation.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/settings.jsp";
	public static final String PAGE__WELCOME = "/welcome.jsp";
	public static final String PAGE__REGISTRATION_ERROR = "/registration.jsp";

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
















	//public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
	//public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";

}