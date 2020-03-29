package com.demo.web.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String SHIFT_CHANGE_TIME = "12:00:00";

	/**
	 * 	Input string format      : "yyyy-MM-dd"
	 * 	Parsing timestamp format : "yyyy-MM-dd HH:mm:ss"
	 * 	Output timestamp format  : "yyyy-MM-dd 12:00:00"
	 * */
	public static Timestamp parseTimestamp(String timestampStr) {
		Timestamp timestamp = null;
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT).parse(timestampStr + " " + SHIFT_CHANGE_TIME);
			timestamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
}
