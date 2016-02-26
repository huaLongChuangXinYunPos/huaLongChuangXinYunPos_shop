package com.example.hlcloundposproject.db;

import com.example.hlcloundposproject.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
	
	private String name;

	public MyOpenHelper(Context context, String name) {
		super(context, name, null, 1);
		this.name= name;//获取数据库名称
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if(name.equals(Constants.GOODS_DB_NAME)){
			/**
			 * 创建     商品基本信息表：
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_GOODS_ENTITY, Constants.TABLE_FORMNALPRICE));
			/**
			 * 创建    特价商品信息表：
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_SPECTIAL_GOODS_ENTITY, Constants.TABLE_SPECIALPRICE));
			/**
			 * 创建    VIP商品的信息表：
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_VIPGOODS_ENTITY,Constants.TABLE_VIPGOODS_PRICE));
			/**
			 * 创建   结算类型表：
			 */
			db.execSQL(String.format(Constants.CREATE_TEMP_JSTYPE_ENTITY,Constants.TABLE_JSTYPE_NAME));
			/**
			 * 创建   出售商品的     商品出售表：
			 */
			db.execSQL(String.format(Constants.CREATE_TABLE_SELL_FORM_ENTITY, Constants.TABLE_SELL_FORM));
			
		}else if(name.equals(Constants.USER_INFO_DB_NAME)){
			/**
			 * 创建   用户基本信息表：
			 */
			db.execSQL(Constants.CREATE_TABLE_USER_ENTITY);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
}
