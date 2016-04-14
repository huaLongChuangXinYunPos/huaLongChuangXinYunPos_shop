package com.shop.hlcloundposproject.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * com.example.scancodecollectmoney.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-3-1
 */
public class MapUtils {
	
	/**
	 * ��map����  ����    ����  ������ĸ˳���������  ����ֵƴ�ӵ��ַ���
	 * @param params
	 * @return
	 */
	public static String createLinkString(Map<String, String> map) {
		
		List<String> keys = new ArrayList<String>(map.keySet());//��ȡ  ���м�
		Collections.sort(keys);//��������
		
		/**
		 * ������ȡ  ֵ
		 */
		String pStr = "";
		
		for(int i= 0;i<keys.size();i++){
			
			String key = keys.get(i);
			String value = map.get(key);
			
			/**
			 * sign �Ѿ�����ӹ�   ��continue   ����  ��ӵ��ַ�����
			 */
			if("sign".equals("key")){
				continue;
			}
			
			pStr = pStr + value;
		}
		return pStr;
	}


	/**
	 * ͨ��json  ��ȡ���еĲ�����     ����װ��   map����
	 * @param jsonString
	 * @return
	 */
	public static Map<String, String> getParamsFromJson(String jsonString) {
		Map<String,String> result  = new HashMap<String,String>();

		JSONObject obj = JSON.parseObject(jsonString);
		
		for(Entry<String,Object> par : obj.entrySet()){
			result.put(par.getKey(), (String)par.getValue());
		}
		return result;
	}

}
