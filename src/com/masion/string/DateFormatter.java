/**
 *
 */
package com.masion.string;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zhang
 *
 */
public class DateFormatter {
	public static DecimalFormat df = new DecimalFormat("#.##");
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");

	public DateFormatter() {

	}

	/**
	 * @param args
	 */
	public static void main2(String[] args) {

		System.out.println(DateFormatter.df.format(0.11111));
		System.out.println(DateFormatter.df.format(10.11111));
		System.out.println(DateFormatter.df.format(100.11111));
		System.out.println(DateFormatter.df.format(1000.11111));
		System.out.println(DateFormatter.df.format(0.11111E10));
		System.out.println(DateFormatter.df.format(1.0));
		System.out.println(DateFormatter.df.format(1.00));
		System.out.println(DateFormatter.df.format(1.000));

		System.out.println(DateFormatter.sdf.format(1350985312635L));


	}
	public static void main3(String[] args) {

		long time = System.currentTimeMillis();
		System.out.println(sdf.format(time));

		long nowTime = System.currentTimeMillis();

		Date now = new Date();
		Calendar cal = new GregorianCalendar();

		System.out.println(cal.getTimeInMillis());

		long expireTime = nowTime + (60 * 24 * 3600 * 1000); //default 60 days
		String installTimeString = sdf.format(now);
		System.out.println(installTimeString);

		String expireTimeString = sdf.format(expireTime);
		System.out.println(expireTime);
		System.out.println(expireTimeString);
	}

	 public static void main(String[] args) {

		  Date date = new Date();
		  System.out.println(ddf.format(date));
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  cal.add(Calendar.HOUR_OF_DAY, -1);
		  System.out.println(ddf.format(cal.getTime()));

		}

}
