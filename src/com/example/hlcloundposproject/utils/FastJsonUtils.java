package com.example.hlcloundposproject.utils;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 解析  JSON工具类
 * com.hlrj.hlcloundpos.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-22
 */
public final class FastJsonUtils {
	
	public static <T> ArrayList<T> getListFromArray(JSONArray array,Class<T> t){
		
		ArrayList<T> ret = null;
		
		if(array.size()!= 0){
			ret = (ArrayList<T>)
					JSON.parseArray(array.toString(),t);//oom..
		}
		
		return ret;
	}
	
}
