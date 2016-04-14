package com.shop.hlcloundposproject.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.Content;
import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.db.MyOpenHelper;
import com.shop.hlcloundposproject.db.OperationDbTableUtils;
import com.shop.hlcloundposproject.entity.JsType;
import com.shop.hlcloundposproject.entity.SpecialGoods;
import com.shop.hlcloundposproject.entity.User;
import com.shop.hlcloundposproject.entity.VIPGoods;
import com.shop.hlcloundposproject.utils.ExitApplicationUtils;
import com.shop.hlcloundposproject.utils.FastJsonUtils;
import com.shop.hlcloundposproject.utils.GetDeviceId;
import com.shop.hlcloundposproject.utils.MyToast;
import com.shop.hlcloundposproject.utils.SelledToServerUtils;
import com.shop.hlcloundposproject.utils.VolleyUtils;
import com.shop.hlcloundposproject.utils.VolleyUtils.VolleyCallback;

import android.os.Bundle;
import android.view.KeyEvent;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SplashActivity extends Activity implements Runnable,VolleyCallback{
	
	private MyOpenHelper userHelper;

	private MyOpenHelper goodsHelper;

	private static final int GET_USERS_DATA_AUTHORITY = 0;//��ȡ  �û�������Ϣ    ��ʶ
	
	private static final int GET_VIPGOODS_DATA_AUTHORITY = 1;//��ȡ   VIP��Ʒ��ʶ��ʶ
	
	private static final int GET_SPECIALGOODS_DATA_AUTHORITY = 2;//��ȡ   �ؼ�  ��Ʒ��ʶ
	
	private static final int GET_POS_ID = 20;  //��ȡ    pos��id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		
		ExitApplicationUtils.getInstance().addActivity(this);
		
		userHelper = new MyOpenHelper(SplashActivity.this,Content.USER_INFO_DB_NAME);
		goodsHelper = new MyOpenHelper(SplashActivity.this, Content.GOODS_DB_NAME);
		
		/**
		 * ��ȡ     ��ǰ     �ֻ�Ψһ��ʶ�� 
		 */
		GetDeviceId getDeviceId = new GetDeviceId(this);
		String CombinedID = getDeviceId.getCombinedId();
		
		new Thread(this).start();
		
		//��ѯ������     ���������    ������Ʒ���е�����    �����Ѿ��ϴ���    ������������ɾ��
		initSellTableData();
		
		/**
		 * ����    Ӳ����ʶ    ��ȡ��ǰ�������������ݱ�ʶ��
		 */
		new VolleyUtils(this).getVolleyDataInfo(String.format(Configs.SERVER_BASE_URL+Configs.GET_POS_Id,CombinedID),
				this, GET_POS_ID);
		
	}

	private void initSellTableData() {
		goodsDb = goodsHelper.getReadableDatabase();
		goodsDb.beginTransaction();
		Cursor cursor = goodsDb.query("t_"+Content.TABLE_SELL_FORM, new String[]{"cSaleSheetNo","dSellTime","isUp"}, null,
				null, null, null, null);
		long currTime = System.currentTimeMillis();
		while(cursor.moveToNext()){
			long sellTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("dSellTime")));
			String isUp = cursor.getString(cursor.getColumnIndex("isUp"));
			if(((currTime-sellTime)/(60*60*1000)>48) && isUp.equals("1")){ //ɾ��   ��ǰ    ����  24Сʱ   �����Ѿ��ϴ�����������   ��Ʒ����
				goodsDb.delete("t_"+Content.TABLE_SELL_FORM, "dSellTime = '"+sellTime+"'", null);
			}
			if(isUp.equals("0")){//��ǰ����δ������  ������
				//��ȡ��ǰ�����     ���۵���
				String cSheet = cursor.getString(cursor.getColumnIndex("cSaleSheetNo"));
				//�������۵���   ����ǰδ���۶�������  ������
				SelledToServerUtils.selledToServer(cSheet,goodsHelper,
						getSharedPreferences(Configs.APP_NAME, MODE_PRIVATE));
			}
		}
		goodsDb.setTransactionSuccessful();
		goodsDb.endTransaction();
	}

	@Override
	public void run() {
		try {
//			��ȡ  ����  ��ʽ��,
			new VolleyUtils(SplashActivity.this).getVolleyDataInfo(Configs.SERVER_BASE_URL+Configs.GET_PAY_CALCULATE_WAY,
					SplashActivity.this,Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY);
		
			
//			����     ������      ��ȡ�����û���Ϣ     ��ӽ�      �û���Ϣ��
			new VolleyUtils(SplashActivity.this).getVolleyDataInfo(
					Configs.SERVER_BASE_URL+Configs.QUERY_ALL_USERS_DATA,
					SplashActivity.this,GET_USERS_DATA_AUTHORITY);
			
//			����   ������    ��ȡ     VIP  ��Ʒ  ��Ϣ��
			new VolleyUtils(SplashActivity.this).getVolleyDataInfo(
					Configs.SERVER_BASE_URL+Configs.QUERY_VIP_GOODS_DATA,
					SplashActivity.this,GET_VIPGOODS_DATA_AUTHORITY);
			
//			����   ������    ��ȡ    �����ؼ���Ʒ��Ϣ��
			new VolleyUtils(SplashActivity.this).getVolleyDataInfo(
					Configs.SERVER_BASE_URL+Configs.QUERY_SPECIAL_GOODS,
					SplashActivity.this, GET_SPECIALGOODS_DATA_AUTHORITY);
			
			Thread.sleep(4000);
			
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private SQLiteDatabase userdb;
	private SQLiteDatabase goodsDb;
	/**
	 * volley  ���ݵĻص���
	 */
	@Override
	public void volleyFinishedSuccess(final JSONArray array, int authority) {
		
		goodsDb = goodsHelper.getWritableDatabase();
		
		switch(authority){
		
			case GET_POS_ID:   //��ȡ��  ��ǰpos����ʶ
				
				JSONObject obj = array.getJSONObject(0);
				String serverPosId = obj.getString("posid");
				
				//��ȡ  �����������
				SharedPreferences sp = getSharedPreferences(
						Configs.APP_NAME,MODE_PRIVATE);
				
				//��ȡ  pos��Id  ���浽  sp
				String localPosId = sp.getString(Configs.POS_ID, 
						"01");
				
				if(!localPosId.equals("serverPosId")){
					SharedPreferences.Editor editor = sp.edit();
					editor.putString("posid", serverPosId);
					editor.commit();
				}
				
			break;
		
			case GET_USERS_DATA_AUTHORITY: //��ȡ   �û� ������Ϣ�����뵽���أ�
				
				new Thread(){

					public void run() {
						//�����߳�   ������  ���뵽���ݱ��ڣ�
						ArrayList<User> users = FastJsonUtils.getListFromArray(array,User.class);
						
						userdb = userHelper.getWritableDatabase();

						//��յ�ǰ����������Ϣ��
						userdb.execSQL("delete from "+Content.TABLE_USERS_NAME);
						
						userdb.beginTransaction();
						for(User user :users){
							//������Ϣ  ��  �û�  ���ݱ��ڣ�
							OperationDbTableUtils.insertUsersTable(userdb,user);
						}
						userdb.setTransactionSuccessful();
						userdb.endTransaction();
					};
				}.start();
				break;
				
			case GET_VIPGOODS_DATA_AUTHORITY://��ȡ   vip�û�  ���е��ؼ���Ʒ��    ��Ϣ  �浽���أ�
				
				new Thread(){

					public void run() {
						ArrayList<VIPGoods> vipGoods = FastJsonUtils.getListFromArray(array,VIPGoods.class);
						
						//��յ�ǰ����������Ϣ��
						goodsDb.execSQL("delete from t_"+Content.TABLE_VIPGOODS_PRICE);
						
						goodsDb.beginTransaction();
						for(VIPGoods vipGood :vipGoods){
							//������Ϣ  ��  �û�  ���ݱ��ڣ�
							OperationDbTableUtils.insertVipGoodsTable(goodsDb,vipGood);
						}
						goodsDb.setTransactionSuccessful();
						goodsDb.endTransaction();
					};
				}.start();
				break;
				
			case GET_SPECIALGOODS_DATA_AUTHORITY: //����  �ؼ���Ʒ��Ϣ��
				
				new Thread(){
					public void run() {
						ArrayList<SpecialGoods> spGoods = FastJsonUtils.getListFromArray(array,SpecialGoods.class);
						
						//��յ�ǰ����������Ϣ��
						goodsDb.execSQL("delete from t_" + Content.TABLE_SPECIALPRICE);
						
						goodsDb.beginTransaction();
						for(SpecialGoods vipGood : spGoods){
							//������Ϣ  ��  �û�  ���ݱ��ڣ�
							OperationDbTableUtils.insertSpecialGoodsToTable(goodsDb, vipGood,Content.TABLE_SPECIALPRICE);
						}
						goodsDb.setTransactionSuccessful();
						goodsDb.endTransaction();
					};
				}.start();
				break;
			
			case Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY:  //��ȡ ���㷽ʽ��  ���浽�������ݿ⣺
				new Thread(){
					public void run() {
						ArrayList<JsType> jsTypes = FastJsonUtils.getListFromArray(array,JsType.class);
						
						//��յ�ǰ����������Ϣ��
						goodsDb.execSQL("delete from t_" + Content.TABLE_JSTYPE_NAME);
						
						goodsDb.beginTransaction();
						
						for(JsType jstype : jsTypes){
							//������Ϣ  ��  �û�  ���ݱ��ڣ�
							OperationDbTableUtils.insertTempJsType(goodsDb, jstype,Content.TABLE_JSTYPE_NAME);
						}
						goodsDb.setTransactionSuccessful();
						goodsDb.endTransaction();
					};
				}.start();
				
				break;
				
				default:break;
		}
	}

	@Override
	public void vollayFinishedFail(int authority) {
		switch(authority){
		
		case GET_USERS_DATA_AUTHORITY: //��ȡ   �û� ������Ϣ�����뵽���أ�
			
			//��ҳ  ������  ʧ�����ݣ�   ��ʾ�û�
			MyToast.ToastIncenter(this, "��ǰδ����������").show();
			
			break;
		}
	}
	
	
	/* �ٰ�һ���˳�����   ���÷��ؼ� */
	private long exitTime = 0;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				MyToast.ToastIncenter(this, "�ٰ�һ���˳�����").show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				ExitApplicationUtils.getInstance().exit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
