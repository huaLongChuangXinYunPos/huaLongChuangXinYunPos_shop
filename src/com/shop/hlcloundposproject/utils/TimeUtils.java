package com.shop.hlcloundposproject.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ����ϵͳ  ʱ���  utils
 * com.shop.hlcloundposproject.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-2-26
 */
public final class TimeUtils {
	
	public static String getSystemNowTime(String formatPattern) {
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);
		String time = df.format(new Date());
		return time;
	}
	

}
