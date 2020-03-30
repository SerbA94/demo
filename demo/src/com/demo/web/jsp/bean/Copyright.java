package com.demo.web.jsp.bean;

import java.sql.Timestamp;

public class Copyright {

	public String getCopyright() {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		Integer currentYear = currentTimestamp.toLocalDateTime().getYear();
		String copyright = " Copyright © Demo " + currentYear;
		return copyright;
	}

}
