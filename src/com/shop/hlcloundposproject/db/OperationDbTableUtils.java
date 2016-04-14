package com.shop.hlcloundposproject.db;

import java.util.ArrayList;

import com.shop.hlcloundposproject.Content;
import com.shop.hlcloundposproject.entity.Goods;
import com.shop.hlcloundposproject.entity.JsType;
import com.shop.hlcloundposproject.entity.SpecialGoods;
import com.shop.hlcloundposproject.entity.User;
import com.shop.hlcloundposproject.entity.VIPGoods;
import com.shop.hlcloundposproject.utils.TimeUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * ����  ���ݿ��    ��  ������
 * com.hlrj.hlcloundpos.utils
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-27
 */
public final class OperationDbTableUtils {

	/**
	 * �����ݲ��뵽   �ؼ���Ʒ���У�
	 * @param goodsDataDb
	 * @param goods
	 * @param tableSpecialprice
	 */
	public static void insertSpecialGoodsToTable(SQLiteDatabase goodsDataDb,
			SpecialGoods goods, String tableSpecialprice) {
		ContentValues values = new ContentValues();
		
		values.put("fVipValue", goods.getfVipValue());
		values.put("cPloyNo", goods.getcPloyNo());
		values.put("cGoodsNo", goods.getcGoodsNo());
		values.put("cGoodsName", goods.getcGoodsName());
		values.put("fQuantity_Ploy", goods.getfQuantity_Ploy());
		
		values.put("fPrice_SO", goods.getfPrice_SO());
		values.put("dDateStart", goods.getdDateStart());
		values.put("cTimeStart", goods.getcTimeStart());
		values.put("dDateEnd", goods.getdDateEnd());
		values.put("cTimeEnd", goods.getcTimeEnd());
		
		values.put("iPriority", goods.getiPriority());
		values.put("bSO", goods.getbSO());
		values.put("bPresent", goods.getbPresent());
		values.put("cPresentPloyNo", goods.getcPresentPloyNo());
		values.put("cPloyTypeNo", goods.getcPloyTypeNo());
		
		values.put("cPloyTypeName", goods.getcPloyTypeName());
		values.put("bEnabled", goods.getbEnabled());
		values.put("bLimitQty", goods.getbLimitQty());
		values.put("fLimitQty", goods.getfLimitQty());
		values.put("bJiOu", goods.getbJiOu());
		
		values.put("fQty_Ji", goods.getfQty_Ji());
		values.put("fQty_Ou", goods.getfQty_Ou());
		values.put("fPrice_ji", goods.getfPrice_ji());
		values.put("fPrice_Ou", goods.getfPrice_Ou());
		values.put("fRatio_JiOu", goods.getfRatio_JiOu());
		
		insertWeek(goods, values);
		insertHour(goods, values);
		
		goodsDataDb.insert("t_" + tableSpecialprice, null, values);
	}

	private static void insertHour(SpecialGoods goods, ContentValues values) {
		values.put("hour0", goods.getHour().getHour0());
		values.put("hour1", goods.getHour().getHour1());
		values.put("hour2", goods.getHour().getHour2());
		values.put("hour3", goods.getHour().getHour3());
		values.put("hour4", goods.getHour().getHour4());
		values.put("hour5", goods.getHour().getHour5());
		values.put("hour6", goods.getHour().getHour6());
		values.put("hour7", goods.getHour().getHour7());
		values.put("hour8", goods.getHour().getHour8());
		values.put("hour9", goods.getHour().getHour9());
		values.put("hour10", goods.getHour().getHour10());
		values.put("hour11", goods.getHour().getHour11());
		values.put("hour12", goods.getHour().getHour12());
		values.put("hour13", goods.getHour().getHour13());
		values.put("hour14", goods.getHour().getHour14());
		values.put("hour15", goods.getHour().getHour15());
		values.put("hour16", goods.getHour().getHour16());
		values.put("hour17", goods.getHour().getHour17());
		values.put("hour18", goods.getHour().getHour18());
		values.put("hour19", goods.getHour().getHour19());
		values.put("hour21", goods.getHour().getHour20());
		values.put("hour20", goods.getHour().getHour21());
		values.put("hour22", goods.getHour().getHour22());
		values.put("hour23", goods.getHour().getHour23());
	}

	private static void insertWeek(SpecialGoods goods, ContentValues values) {
		values.put("week1", goods.getWeek().getWeek1());
		values.put("week2", goods.getWeek().getWeek2());
		values.put("week3", goods.getWeek().getWeek3());
		values.put("week4", goods.getWeek().getWeek4());
		values.put("week5", goods.getWeek().getWeek5());
		values.put("week6", goods.getWeek().getWeek6());
		values.put("week7", goods.getWeek().getWeek7());
	}

	/**
	 * ����  ���ݵ�     ��Ʒ������Ϣ��
	 * @param goodsDataDb
	 * @param goods
	 * @param tableFnormalprice
	 */
	public static void insertGoodsToTable(SQLiteDatabase goodsDataDb,
			Goods goods, String tableFnormalprice) {
		ContentValues values = new ContentValues();
		values.put("cGoodsNo", goods.getcGoodsNo());
		values.put("cUnitedNo", goods.getcUnitedNo());
		values.put("cGoodsName", goods.getcGoodsName());
		values.put("cBarcode", goods.getcBarcode());
		values.put("cUnit", goods.getcUnit());
		
		values.put("cSpec", goods.getcSpec());
		values.put("fNormalPrice", goods.getfNormalPrice());
		values.put("fVipPrice", goods.getfVipPrice());
		values.put("fVipScore", goods.getfVipScore());
		values.put("bWeight", goods.getbWeight());
		
		values.put("fVipScore_base", goods.getfVipScore_base());
		values.put("fVipPrice_student", goods.getfVipPrice_student());
		
		values.put("bHidePrice", goods.getbHidePrice());
		values.put("bHideQty", goods.getbHideQty());
		values.put("bNoVipPrice", goods.getbNoVipPrice());
		values.put("bUpdate", goods.getbUpdate());
		
		goodsDataDb.insert("t_"+ tableFnormalprice, null, values);
	}

	/**
	 * ����Ʒ������Ϣ��     ��  ����    ��Ʒ    ��������:
	 * @param codrBar
	 * @param goodsDataHelper
	 * @return
	 */
	public static Cursor selectDataFromLocal(String codrBar,
			MyOpenHelper goodsDataHelper) {
		SQLiteDatabase goodsDataDb = goodsDataHelper.getReadableDatabase();
		Cursor cursor = goodsDataDb.query("t_"+Content.TABLE_FORMNALPRICE, new String[]{"*"}, 
					" cBarcode = '"+codrBar+"'", null, null, null, null);
		return cursor;
	}

	/**
	 * ��  ��ѯ����   ������Ʒ��Ϣ   ��װ��ʵ���ࣺ
	 * @param cursor
	 * @return
	 */
	public static Goods goodsCursorToEntity(Cursor cursor) {
		Goods goods = null;
		//�����α꼯��
		while(cursor.moveToNext()){
		    goods = new Goods();
			goods.setId(cursor.getInt(cursor.getColumnIndex("id")));
			goods.setcGoodsNo(cursor.getString(cursor.getColumnIndex("cGoodsNo")));
			goods.setcUnitedNo(cursor.getString(cursor.getColumnIndex("cUnitedNo")));
			goods.setcGoodsName(cursor.getString(cursor.getColumnIndex("cGoodsName")));
			goods.setcBarcode(cursor.getString(cursor.getColumnIndex("cBarcode")));
			
			goods.setcUnit(cursor.getString(cursor.getColumnIndex("cUnit")));
			goods.setcSpec(cursor.getString(cursor.getColumnIndex("cSpec")));
			goods.setfNormalPrice(cursor.getFloat(cursor.getColumnIndex("fNormalPrice")));
			goods.setfVipPrice(cursor.getFloat(cursor.getColumnIndex("fVipPrice")));
			goods.setfVipScore(cursor.getFloat(cursor.getColumnIndex("fVipScore")));
			goods.setfVipScore_base(cursor.getFloat(cursor.getColumnIndex("fVipScore_base")));
			
			goods.setfVipPrice_student(cursor.getFloat(cursor.getColumnIndex("fVipPrice_student")));
			goods.setbWeight(cursor.getInt(cursor.getColumnIndex("bWeight")));
			goods.setbHidePrice(cursor.getInt(cursor.getColumnIndex("bHidePrice")));
			goods.setbHideQty(cursor.getInt(cursor.getColumnIndex("bHideQty")));
			goods.setbNoVipPrice(cursor.getInt(cursor.getColumnIndex("bNoVipPrice")));
			
			goods.setbUpdate(cursor.getInt(cursor.getColumnIndex("bUpdate")));
			
			goods.setAmount(1);//����Ĭ��  ������    ���Ϊ��ǰ�۸�
			goods.setPayMoney(cursor.getDouble(cursor.getColumnIndex("fNormalPrice")));
		}
		return goods;
	}
	
	/**
	 * ��  ��ѯ����   ������Ʒ��Ϣ   ��װ��ʵ���ࣺ
	 * @param cursor
	 * @return
	 */
	public static ArrayList<Goods> goodsCursorToList(Cursor cursor,String limit) {
		ArrayList<Goods> list = new ArrayList<Goods>();
		Goods goods = null;
		int i = 0;
		//�����α꼯��
		while(cursor.moveToNext()&&i<50){
		    goods = new Goods();
			goods.setId(cursor.getInt(cursor.getColumnIndex("id")));
			goods.setcGoodsNo(cursor.getString(cursor.getColumnIndex("cGoodsNo")));
			goods.setcUnitedNo(cursor.getString(cursor.getColumnIndex("cUnitedNo")));
			goods.setcGoodsName(cursor.getString(cursor.getColumnIndex("cGoodsName")));
			goods.setcBarcode(cursor.getString(cursor.getColumnIndex("cBarcode")));
			
			goods.setcUnit(cursor.getString(cursor.getColumnIndex("cUnit")));
			goods.setcSpec(cursor.getString(cursor.getColumnIndex("cSpec")));
			goods.setfNormalPrice(cursor.getFloat(cursor.getColumnIndex("fNormalPrice")));
			goods.setfVipPrice(cursor.getFloat(cursor.getColumnIndex("fVipPrice")));
			goods.setfVipScore(cursor.getFloat(cursor.getColumnIndex("fVipScore")));
			goods.setfVipScore_base(cursor.getFloat(cursor.getColumnIndex("fVipScore_base")));
			
			goods.setfVipPrice_student(cursor.getFloat(cursor.getColumnIndex("fVipPrice_student")));
			goods.setbWeight(cursor.getInt(cursor.getColumnIndex("bWeight")));
			goods.setbHidePrice(cursor.getInt(cursor.getColumnIndex("bHidePrice")));
			goods.setbHideQty(cursor.getInt(cursor.getColumnIndex("bHideQty")));
			goods.setbNoVipPrice(cursor.getInt(cursor.getColumnIndex("bNoVipPrice")));
			
			goods.setbUpdate(cursor.getInt(cursor.getColumnIndex("bUpdate")));
			
			goods.setAmount(1);//����Ĭ��  ������    ���Ϊ��ǰ�۸�
			goods.setPayMoney(cursor.getDouble(cursor.getColumnIndex("fNormalPrice")));
			
			list.add(goods);
			
			if(limit.equals("") || limit==null){
				list.clear();
			}
			i++;//����list�����ݴ�С
		}
		return list;
	}

	/**
	 * ������Ϣ  ��  �û����ڣ�
	 * @param userdb
	 * @param user
	 */
	public static void insertUsersTable(SQLiteDatabase userdb, User user) {
		//�浽���أ�
		ContentValues values = new ContentValues();
		
		values.put("ki", user.getKi());
		values.put("name", user.getName());
		values.put("password", user.getPass());
		values.put("quanxian", user.getQuanxian());
		values.put("right", user.getRight());
		values.put("user",user.getUser());
					
		userdb.insert(Content.TABLE_USERS_NAME, null, values);
	}

	/**
	 * ����  ����  �� vip��Ʒ��Ϣ���ڣ�
	 * @param goodsDb
	 * @param vipGood
	 * cSheetno text,iLineNo text,cGoodsNo text,cBarcode text,fVipPrice text,fVipPrice_student text," +
	 "week1 boolean,week2 boolean,week3 boolean,week4 boolean,week5 boolean,week6 boolean,week7 boolean," +
	 "hour0 boolean,hour1 boolean,hour2 boolean,hour3 boolean,hour4 boolean,hour5 boolean,hour6 boolean,hour7 boolean,hour8 boolean," +
	 "hour9 boolean,hour10 boolean,hour11 boolean,hour12 boolean,hour13 boolean,hour14 boolean,hour15 boolean,hour16 boolean,hour17 boolean,hour18 boolean," +
	 "hour19 boolean,hour20 boolean,hour21 boolean,hour22 boolean,hour23 boolean)
	 */
	public static void insertVipGoodsTable(SQLiteDatabase goodsDb,
			VIPGoods vipGood) {
		
		ContentValues values = new ContentValues();
		values.put("cSheetno", vipGood.getcSheetno());
		values.put("iLineNo", vipGood.getiLineNo());
		values.put("cGoodsNo", vipGood.getcGoodsNo());
		values.put("cBarcode", vipGood.getcBarcode());
		values.put("fVipPrice", vipGood.getfVipPrice());
		values.put("fVipPrice_student", vipGood.getfVipPrice_student());
		
		insertVipGoodsWeek(vipGood, values);
		insertSpecialHour(vipGood, values);
		
		goodsDb.insert("t_"+Content.TABLE_VIPGOODS_PRICE, null, values);
		
	}

	private static void insertSpecialHour(VIPGoods vipGood, ContentValues values) {
		values.put("hour0", vipGood.getHour().getHour0());
		values.put("hour1", vipGood.getHour().getHour1());
		values.put("hour2", vipGood.getHour().getHour2());
		values.put("hour3", vipGood.getHour().getHour3());
		values.put("hour4", vipGood.getHour().getHour4());
		values.put("hour5", vipGood.getHour().getHour5());
		values.put("hour6", vipGood.getHour().getHour6());
		values.put("hour7", vipGood.getHour().getHour7());
		values.put("hour8", vipGood.getHour().getHour8());
		values.put("hour9", vipGood.getHour().getHour9());
		values.put("hour10", vipGood.getHour().getHour10());
		values.put("hour11", vipGood.getHour().getHour11());
		values.put("hour12", vipGood.getHour().getHour12());
		values.put("hour13", vipGood.getHour().getHour13());
		values.put("hour14", vipGood.getHour().getHour14());
		values.put("hour15", vipGood.getHour().getHour15());
		values.put("hour16", vipGood.getHour().getHour16());
		values.put("hour17", vipGood.getHour().getHour17());
		values.put("hour18", vipGood.getHour().getHour18());
		values.put("hour19", vipGood.getHour().getHour19());
		values.put("hour21", vipGood.getHour().getHour20());
		values.put("hour20", vipGood.getHour().getHour21());
		values.put("hour22", vipGood.getHour().getHour22());
		values.put("hour23", vipGood.getHour().getHour23());
	}

	private static void insertVipGoodsWeek(VIPGoods vipGood,
			ContentValues values) {
		values.put("week1", vipGood.getWeek().getWeek1());
		values.put("week2", vipGood.getWeek().getWeek2());
		values.put("week3", vipGood.getWeek().getWeek3());
		values.put("week4", vipGood.getWeek().getWeek4());
		values.put("week5", vipGood.getWeek().getWeek5());
		values.put("week6", vipGood.getWeek().getWeek6());
		values.put("week7", vipGood.getWeek().getWeek7());
	}
	
	public static void insertTempJsType(SQLiteDatabase tempDb, JsType jstype,
			String tableJstypeName) {
		//�浽���أ�
		ContentValues values = new ContentValues();
		
		values.put("jstype", jstype.getJsType());
		values.put("mianzhi", jstype.getMianzhi());
		values.put("shishou", jstype.getShishou());
		values.put("zhaoling", jstype.getZhaoling());
		values.put("zhekou",jstype.getZhekou());
		values.put("detail", jstype.getDetail());
					
		tempDb.insert("t_"+Content.TABLE_JSTYPE_NAME, null, values);
	}

	
	public static void sellGoodsInsertTable(SQLiteDatabase goodsDataDb,String[] payStrs, Goods goods
			,boolean isVip,String cVipNo,String vipScore,
			User user,String sheetNo,String jsType,
			String totalMoney,String shouldPayMoney) {
		
		long time = System.currentTimeMillis();
		
		Cursor spGoodsCursor = getSpGoodsCursor(goodsDataDb, TimeUtils.getSystemNowTime("yyyy-MM-dd"), goods);
		Cursor vipGoodsCursor = getVipCursor(goodsDataDb,goods);
		
		ContentValues values = new ContentValues();
		//��ȡ   ��Ʒ�ţ�
		values.put("cGoodsNo", goods.getcGoodsNo());
		values.put("cBarCode", goods.getcBarcode());
		values.put("sellAmount", goods.getAmount());
		values.put("fNormalPrice", goods.getfNormalPrice());
		values.put("goodsMoney", goods.getPayMoney());  //����ƷӦ��֧����    ���
		values.put("shouldMoney",jsType.equals("�����")?shouldPayMoney:totalMoney);  //�ܹ�Ӧ��֧����     ���
		values.put("payMoney",jsType.equals("�����")?payStrs[1]:totalMoney);  //ʵ��֧���Ľ��
		values.put("overPlus",jsType.equals("�����")?payStrs[2]:"0.00");//ʣ���
		
		if(spGoodsCursor.moveToFirst()){//��ǰΪ    �ؼ���Ʒ
			float spPrice = Float.parseFloat(spGoodsCursor
					.getString(spGoodsCursor
							.getColumnIndex("fPrice_SO")));
			if(vipGoodsCursor.moveToFirst()){//��ǰ     ��vip��Ʒ
				float vipPrice = Float.parseFloat(vipGoodsCursor
						.getString(vipGoodsCursor
								.getColumnIndex("fVipPrice")));
				if(isVip){ //  vip�û�
					values.put("executePrice",vipPrice<spPrice?vipPrice:spPrice);//ִ��
				}else{
					values.put("executePrice",spPrice);//ִ���ؼ�
				}
			}else{
				values.put("executePrice",spPrice);//ִ���ؼ�
			}
		}else{
			values.put("executePrice",goods.getfNormalPrice());//ִ��������Ʒ��  �۸�
		}
		/**
			integer primary key autoincrement,cGoodsNo text,cBarCode text,sellAmount text,fNormalPrice numeric(10,4)," +
			"goodsMoney numeric(10,4),shouldMoney numeric(10,4),payMoney numeric(10,4),overPlus numeric(10,4),"+
			"executePrice numeric(10,4),isSpeG text,spPrice numeric(10,4),isVip text,vipPrice numeric(10,4),vipScore numeric(10,4)," +
			"vipCardNo text,dSellTime text,jsType text,cOperationName text,cOperationNo text,cSaleSheetNo text,isUp text,exactlyTime text)";
		 */
		
		values.put("isSpeG", spGoodsCursor.moveToFirst()?1:0);
		values.put("spPrice",spGoodsCursor.moveToFirst()?spGoodsCursor
				.getString(spGoodsCursor.getColumnIndex("fPrice_SO")) : "0.0000");//�޼۸�

		values.put("isVip", isVip ? 1:0);
		values.put("vipPrice",vipGoodsCursor.moveToFirst()? vipGoodsCursor
				.getString(vipGoodsCursor.getColumnIndex("fVipPrice")) : "0.0000");
		
		values.put("vipScore", isVip?vipScore:"0.0000");
		
		values.put("vipCardNo", isVip?cVipNo:"0");
		values.put("dSellTime", time);   //��ǰϵͳ����ֵ
		values.put("jsType", jsType);
		values.put("cOperationName", user.getName());  //����Ա������
		values.put("cOperationNo", user.getUser());//��������Ա��
		values.put("cSaleSheetNo",sheetNo);  //��Ʒ���۵���:  02 20160226 0032
		values.put("isUp", 0);
		values.put("exactlyTime", TimeUtils.getSystemNowTime("yyyy-MM-dd HH:mm:ss"));
		
		goodsDataDb.insert("t_" + Content.TABLE_SELL_FORM, null, values);
	}
	
	/**
	 * ��ѯ vip�ؼ���Ʒ��Ϣ��
	 */
	public static Cursor getVipCursor(SQLiteDatabase goodsDataDb,Goods item) {
		Cursor vipGoodsCursor = null;
		vipGoodsCursor = goodsDataDb.rawQuery(
				"select * from t_" + Content.TABLE_VIPGOODS_PRICE
						+ " where cBarCode = '" + item.getcBarcode() + "'",
				null);
		return vipGoodsCursor;
	}

	/**
	 * �� ������Ʒ �ؼ� ��Ʒ�в�ѯ �Ƿ��д��ؼ� ��Ʒ��Ϣ��
	 */
	public static Cursor getSpGoodsCursor(SQLiteDatabase goodsDataDb,String nowTime, Goods item) {
		Cursor spGoodsCursor = null;
		spGoodsCursor = goodsDataDb.rawQuery(
				"select b.* from t_" + Content.TABLE_FORMNALPRICE + " a,"
						+ "t_" + Content.TABLE_SPECIALPRICE + " b "
						+ "where a.cGoodsNo = b.cGoodsNo and a.[cBarcode] = '"
						+ item.getcBarcode() + "' " + "and '" + nowTime
						+ "' between b.[dDateStart]" + " and b.[dDateEnd]",
				null);
		return spGoodsCursor;
	}

	//��     ���۳�����      ����Ϣ      ת����      ��Ʒ������Ϣ
	public static Goods sellGoodsToGoodsEntity(Cursor cursor,Goods goods) {
		
		//�����α꼯��
		goods.setcBarcode(cursor.getString(cursor.getColumnIndex("cBarCode")));
		goods.setAmount(Float.parseFloat(cursor.getString(cursor.getColumnIndex("sellAmount"))));//����Ĭ��  ������    ���Ϊ��ǰ�۸�
		goods.setfNormalPrice(cursor.getFloat(cursor.getColumnIndex("fNormalPrice")));
		goods.setPayMoney(cursor.getDouble(cursor.getColumnIndex("goodsMoney")));

		return goods;
	};

}
