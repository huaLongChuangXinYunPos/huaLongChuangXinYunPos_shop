package com.example.hlcloundposproject.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 操作系统  时间的  utils
 * com.example.hlcloundposproject.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-2-26
 */
public final class TimeUtils {
	
	/**
	 * 获取系统当前时间   作为 挂单数据的表名
	 * @return
	 */
	public static String getSystemNowTime(String formatPattern) {
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);
		long date = System.currentTimeMillis();															
		String time = df.format(new Date());
		return time;
	}
	

}
