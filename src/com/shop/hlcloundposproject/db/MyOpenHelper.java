package com.shop.hlcloundposproject.db;

import com.shop.hlcloundposproject.Content;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
	
	private String name;

	public MyOpenHelper(Context context, String name) {
		super(context, name, null, 1);
		this.name= name;//��ȡ���ݿ�����
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(name.equals(Content.GOODS_DB_NAME)){
			/**
			 * ����    ������Ʒ��      ��Ʒ���۱�
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_SELL_FORM_ENTITY, Content.TABLE_SELL_FORM));
			
			/**
			 * ����     ��Ʒ������Ϣ��
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_GOODS_ENTITY, Content.TABLE_FORMNALPRICE));
			/**
			 * ����    �ؼ���Ʒ��Ϣ��
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_SPECTIAL_GOODS_ENTITY, Content.TABLE_SPECIALPRICE));
			/**
			 * ����    VIP��Ʒ����Ϣ��
			 */
			db.execSQL(String.format(Content.CREATE_TABLE_VIPGOODS_ENTITY,Content.TABLE_VIPGOODS_PRICE));
			/**
			 * ����   �������ͱ�
			 */
			db.execSQL(String.format(Content.CREATE_TEMP_JSTYPE_ENTITY,Content.TABLE_JSTYPE_NAME));
		}else if(name.equals(Content.USER_INFO_DB_NAME)){
			/**
			 * ����   �û�������Ϣ��
			 */
			db.execSQL(Content.CREATE_TABLE_USER_ENTITY);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
