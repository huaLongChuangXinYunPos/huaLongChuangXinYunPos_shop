package com.shop.hlcloundposproject.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * volley �Ļص�  ������Ϣ�ࣺ
 * com.hlrj.hlcloundpos.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-28
 */
public final class VolleyUtils {
	
	private VolleyCallback volleyCallback;//����   �ӿڲ���
	
//	ʵ�֡����������  �ص���
	public VolleyUtils(VolleyCallback volleyCallback){
		this.volleyCallback = volleyCallback;
	}
	
	/**
	 * ʹ��   volley   �ص�������Ϣ��
	 * @param url
	 * @param context
	 */
	public void getVolleyDataInfo(String url,final Context context,final int authority) {
		
		//ʵ�����������
		StringRequest request = new StringRequest(url,new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
					if(response!=null){
						
						//�ص����ݣ�
						JSONObject Object = JSON.parseObject(response);
						
						if(Object.getIntValue("resultStatus")==1){
							JSONArray array = Object.getJSONArray("data");
							volleyCallback.volleyFinishedSuccess(array,authority);
						}
						
					}
				}
			},new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					volleyCallback.vollayFinishedFail(authority);
				}
			});
								
			//3. ��������ӵ�Volley�����������
			Volley.newRequestQueue(context).add(request);
		}
	
	/**
	 * �ص��ӿ�     
	 * com.hlrj.hlcloundpos.utils
	 * @Email zhaoq_hero@163.com
	 * @author zhaoQiang : 2016-1-28
	 */
	public interface VolleyCallback{
		/**
		 * �ص�  ����  ����ʵ��   �ص�
		 * @param array  �ص� ��ȥ��  ����
		 * @param authority   ��ʶ��
		 */
		void volleyFinishedSuccess(JSONArray array,int authority);
		void vollayFinishedFail(int authority);
	} 
}
