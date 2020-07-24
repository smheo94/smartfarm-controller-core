package com.kt.cmmn.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil2 {
	public static LocalDateTime toLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static Date toDate(LocalDateTime date) {
		return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate toLocalDate(java.sql.Date  date) {
		return date.toLocalDate();
	}

	public static LocalDateTime toLocalDateTime(java.sql.Timestamp  timestamp) {
		return timestamp.toLocalDateTime();
	}
	public static java.sql.Date toDate(LocalDate date) {
		return java.sql.Date.valueOf(date);
	}

	public static Timestamp toTimestamp(LocalDateTime date) {
		return Timestamp.valueOf(date);
	}

	public static LocalDate parseLocalDate(String dateStr) {
		return LocalDate.parse(dateStr);
	}

	public static LocalDate parseLocalDate(String dateStr, String pattern) {
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
	}

	public static LocalDateTime parseLocalDateTime(String dateStr) {
		return LocalDateTime.parse(dateStr);
	}

	public static LocalDateTime parseLocalDateTime(String dateStr, String pattern) {
		return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
	}


	public static String toString(LocalDate date, String pattern) {
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}

	public static String toString(LocalDateTime date, String pattern) {
		return date.format(DateTimeFormatter.ofPattern(pattern));
	}
}
