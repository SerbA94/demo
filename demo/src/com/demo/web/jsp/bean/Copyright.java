/**
 *
 */
package com.demo.web.jsp.bean;

import java.sql.Timestamp;

/**
 * Copyright (JSP JavaBean).
 *
 * @author A.Serbin
 *
 */
public class Copyright {

	public String getCopyright() {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		Integer currentYear = currentTimestamp.toLocalDateTime().getYear();
		String copyright = " Copyright © Demo " + currentYear;
		return copyright;
	}

}
