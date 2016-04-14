package com.shop.hlcloundposproject;

//���ݿ������     �ַ�����װ�ࣺ
public final class Content {
	
	/**
	 * ����         ����          ���� ���ݿ�� provider �ı�ʶ��
	 */
	public static final String PROVIDER_AUTHORITY = "com.hlrj.hlcloundpos.userdatadb.t_users";
	
	/**
	 * ����   ����  ��Ʒ��Ϣ��     ����������
	 */
	public static final String GOODS_DB_NAME = "goodsDataDb";
	
	/**
	 * ���  ��Ʒ������Ϣ
	 */
	public static final String TABLE_FORMNALPRICE = "goods";
	
	/**
	 * ���   ������Ʒ��Ϣ
	 */
	public static final String TABLE_SPECIALPRICE = "spGoods";
	
	/**
	 * �����û�������Ϣ��    ���ݿ�����
	 */
	public static final String USER_INFO_DB_NAME = "usersDataDb";
	
	/**
	 * �������� �û�����    users����
	 */
	public static final String TABLE_USERS_NAME = "t_users";
	
	/**
	 * ����  �ҵ� ȡ�������ݿ�      ����
	 */
	public static final String TEMP_DATA_DB = "tempDataDb";
	
	/**
	 * ����    temp�йҵ�   ȡ��     �ı�������Ϣ t_12_20_11
	 */
	public static final String CREATE_TABLE_TEMP_ENTITY = "create table if not exists t_%s(id integer primary key autoincrement," +
			"cBarcode text,amount numeric(10,2),payMoney numeric(10,4))";
	
	/**
	 * ����  temp ��������  t_12_20_11
	 */
	public static final String INSERT_TABLE_TEMP = "insert into t_%s(cBarcode,amount,payMoney) " +
			"values('%s', %f, %f)";
	
	/**
	 * ����   ���ݿ�� t_users  �����û���Ϣ
	 */
	public static final String CREATE_TABLE_USER_ENTITY = "create table if not exists t_users" +
			"(id integer primary key autoincrement,user text,password text,name text," +
			"right text,ki text,quanXian text)";
	
	/**
	 * ��ѯ  ���ݱ��Ƿ���ڣ���������    ֱ�Ӵ�������
	 * ����Ϊ  t_����   t_6902
	 * cGoodsNo,cUnitedNo,cGoodsName,cBarcode,cUnit,
	 * cSpec,fNormalPrice,fVipPrice,fVipScore ,bWeight,
	 * fVipScore_base,fVipPrice_student,bHidePrice,bHideQty,bNoVipPrice,
	 * bUpdate
	 */
	public static final String CREATE_TABLE_GOODS_ENTITY = "create table if not exists t_%s" + 
	 "(id integer primary key autoincrement,cGoodsNo text,cUnitedNo text,cGoodsName text," +
	 "cBarcode text,cUnit text,cSpec text,fNormalPrice numeric(10,4),fVipPrice numeric(10,4)," +
	 "fVipScore numeric(10,4),bWeight text,fVipScore_base numeric(10,4),fVipPrice_student numeric(10,4)," +
	 "bHidePrice text,bHideQty text,bNoVipPrice text,bUpdate text)";
	
	/**
	 * ����   �ؼ���Ʒ��Ϣ��
	 */
	public static final String CREATE_TABLE_SPECTIAL_GOODS_ENTITY = "create table if not exists t_%s" + 
			 "(id integer primary key autoincrement,fVipValue text,cPloyNo text,cGoodsNo text,cGoodsName text," +
			 "fQuantity_Ploy text,fPrice_SO text,dDateStart text,cTimeStart text,dDateEnd text," +
			 "cTimeEnd text,iPriority text,bSO text,bPresent text,cPresentPloyNo text," +
			 "cPloyTypeNo text,cPloyTypeName text,bEnabled text,bLimitQty text,fLimitQty text," +
			 "bJiOu text,fQty_Ji text,fPrice_ji text,fQty_Ou text,fPrice_Ou text,fRatio_JiOu text," +
			 "week1 text,week2 text,week3 text,week4 text,week5 text,week6 text,week7 text," +
			 "hour0 text,hour1 text,hour2 text,hour3 text,hour4 text,hour5 text,hour6 text,hour7 text,hour8 text," +
			 "hour9 text,hour10 text,hour11 text,hour12 text,hour13 text,hour14 text,hour15 text,hour16 text,hour17 text,hour18 text," +
			 "hour19 text,hour20 text,hour21 text,hour22 text,hour23 text)";
	
	/**
	 * ����    VIP��Ʒ��Ϣ������
	 */
	public static final String TABLE_VIPGOODS_PRICE = "vipGoods";

	
	/**
	 * ����     VIP��Ʒ��Ϣ��
	 */
	public static final String CREATE_TABLE_VIPGOODS_ENTITY = "create table if not exists t_%s" + 
	 "(id integer primary key autoincrement,cSheetno text,iLineNo text,cGoodsNo text,cBarcode text,fVipPrice text,fVipPrice_student text," +
	 "week1 text,week2 text,week3 text,week4 text,week5 text,week6 text,week7 text," +
	 "hour0 text,hour1 text,hour2 text,hour3 text,hour4 text,hour5 text,hour6 text,hour7 text,hour8 text," +
	 "hour9 text,hour10 text,hour11 text,hour12 text,hour13 text,hour14 text,hour15 text,hour16 text,hour17 text,hour18 text," +
	 "hour19 text,hour20 text,hour21 text,hour22 text,hour23 text)";
	
	/**
	 * ����  �������͵� ��
	 */
	public static final String TABLE_JSTYPE_NAME = "jsType";

	/**
	 * ����  ��������  ��
	 */
	public static final String CREATE_TEMP_JSTYPE_ENTITY = "create table if not exists t_%s" +
			"(id integer primary key autoincrement,jstype text,detail text,mianzhi numeric(10,4),zhekou numeric(10,4)," +
			"shishou numeric(10,4),zhaoling numeric(10,4))";
	
	/**
	 * ��¼��ǰ���������  ���۱�
	 */
	public static final String TABLE_SELL_FORM = "sell";
	
	/**
	 * ����   ������������۱�
	 */
	public static final String CREATE_TABLE_SELL_FORM_ENTITY = "create table if not exists t_%s" +
			"(id integer primary key autoincrement,cGoodsNo text,cBarCode text,sellAmount text,fNormalPrice numeric(10,4)," +
			"goodsMoney numeri  c(10,4),shouldMoney numeric(10,4),payMoney numeric(10,4),overPlus numeric(10,4),"+
			"executePrice numeric(10,4),isSpeG text,spPrice numeric(10,4),isVip text,vipPrice numeric(10,4),vipScore numeric(10,4)," +
			"vipCardNo text,dSellTime text,jsType text,cOperationName text,cOperationNo text,cSaleSheetNo text,isUp text,exactlyTime text)";
	//,
	
	public static final String CHAR_SET = "GBK";  //���뼯
	
	//�̻�����  ˽Կ
	public static final String PRIVATE_KEY = "9c1Ick1pYX50YB3GOcWNsZCEi9m0XL9S";//�̻�˽Կ
	
	//�̻�pid  ֧����ǩԼ�̻�    ���ṩ
	public static final String SHOP_PID ="3088677747502334";
	
	//������    ��ַ
	public static final String SERVER_IP = "121.199.41.235";
	
	//������   �˿ڣ�
	public static final int SERVER_PORT = 6666;//���Ӷ˿ں�

}
