package com.demo.web.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimestampUtil {

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String SHIFT_CHANGE_TIME = "12:00:00";

	/**
	 * 	Input string format      : "yyyy-MM-dd"
	 * 	Parsing timestamp format : "yyyy-MM-dd HH:mm:ss"
	 * 	Output timestamp format  : "yyyy-MM-dd 12:00:00"
	 * */
	public static Timestamp parseTimestamp(String timestampParam) {
		Timestamp timestamp = null;
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT).parse(timestampParam + " " + SHIFT_CHANGE_TIME);
			timestamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static Timestamp getNextDateIn() {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		Timestamp currentShiftChangeTimestamp = getCurrentShiftChangeTimestamp();
		if(currentTimestamp.before(currentShiftChangeTimestamp)) {
			return currentShiftChangeTimestamp;
		}else {
			return Timestamp.valueOf(currentShiftChangeTimestamp.toLocalDateTime().plusDays(1));
		}
	}

	public static String getNextDateIn(String pattern) {
		return getNextDateIn().toLocalDateTime().format(DateTimeFormatter.ofPattern(pattern));
	}

	public static Timestamp getNextDateOut() {
		return Timestamp.valueOf(getNextDateIn().toLocalDateTime().plusDays(1));
	}

	public static String getNextDateOut(String pattern) {
		return getNextDateOut().toLocalDateTime().format(DateTimeFormatter.ofPattern(pattern));
	}

	public static Timestamp getCurrentShiftChangeTimestamp() {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		int year = currentTimestamp.toLocalDateTime().getYear();
		int month = currentTimestamp.toLocalDateTime().getMonthValue();
		int day = currentTimestamp.toLocalDateTime().getDayOfMonth();
		String timestampParam = year + "-" + month + "-" + day + " " +  SHIFT_CHANGE_TIME;

		Timestamp timestamp = null;
		try {
			Date date = new SimpleDateFormat(DATE_FORMAT).parse(timestampParam + " " + SHIFT_CHANGE_TIME);
			timestamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp;
	}
}
