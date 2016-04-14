package com.shop.hlcloundposproject;


import hardware.print.printer;
import hardware.print.printer.PrintType;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.activity.LoginActivity;
import com.shop.hlcloundposproject.activity.QueryGoodsActivity;
import com.shop.hlcloundposproject.adapter.GoodsAdapter;
import com.shop.hlcloundposproject.db.MyOpenHelper;
import com.shop.hlcloundposproject.db.OperationDbTableUtils;
import com.shop.hlcloundposproject.entity.Goods;
import com.shop.hlcloundposproject.entity.SpecialGoods;
import com.shop.hlcloundposproject.entity.User;
import com.shop.hlcloundposproject.entity.VIPGoods;
import com.shop.hlcloundposproject.fragments.BackFragment;
import com.shop.hlcloundposproject.fragments.FragmentCallback;
import com.shop.hlcloundposproject.fragments.PayBalanceFragmentDialog;
import com.shop.hlcloundposproject.fragments.PayDialogFragment;
import com.shop.hlcloundposproject.fragments.SelectFormTempFragment;
import com.shop.hlcloundposproject.fragments.UpdateAmountFragment;
import com.shop.hlcloundposproject.fragments.UpdateUserPwdDialog;
import com.shop.hlcloundposproject.fragments.VipDialog;
import com.shop.hlcloundposproject.tasks.GetGoodsInfoAsyncTask;
import com.shop.hlcloundposproject.tasks.TaskCallBack;
import com.shop.hlcloundposproject.tasks.TaskResult;
import com.shop.hlcloundposproject.utils.ExitApplicationUtils;
import com.shop.hlcloundposproject.utils.FastJsonUtils;
import com.shop.hlcloundposproject.utils.MD5Util;
import com.shop.hlcloundposproject.utils.MapUtils;
import com.shop.hlcloundposproject.utils.MyProgressDialog;
import com.shop.hlcloundposproject.utils.MyToast;
import com.shop.hlcloundposproject.utils.SelledToServerUtils;
import com.shop.hlcloundposproject.utils.SocketToNet;
import com.shop.hlcloundposproject.utils.TimeUtils;
import com.shop.hlcloundposproject.utils.VolleyUtils;
import com.shop.hlcloundposproject.utils.VolleyUtils.VolleyCallback;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mining.app.zxing.MipcaActivityCapture;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.barcode.Scanner;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements TaskCallBack,
		FragmentCallback, OnClickListener, OnItemClickListener, VolleyCallback, TextWatcher{

	@ViewInject(R.id.keys_1)
	private Button keys_1;
	@ViewInject(R.id.keys_2)
	private Button keys_2;
	@ViewInject(R.id.keys_3)
	private Button keys_3;
	@ViewInject(R.id.keys_4)
	private Button keys_4;
	@ViewInject(R.id.keys_5)
	private Button keys_5;
	@ViewInject(R.id.keys_6)
	private Button keys_6;
	@ViewInject(R.id.keys_7)
	private Button keys_7;
	@ViewInject(R.id.keys_8)
	private Button keys_8;
	@ViewInject(R.id.keys_9)
	private Button keys_9;
	@ViewInject(R.id.keys_0)
	private Button keys_0;
	@ViewInject(R.id.keys_dot)
	private Button keys_dot;
	@ViewInject(R.id.keys_back_space)
	private Button keys_back_space;

	@ViewInject(R.id.total_ago_money)
	private TextView agoMoney;// ��Ʒԭ��
	@ViewInject(R.id.total_special_price)
	private TextView spPrice;// �ؼ���Ʒ
	@ViewInject(R.id.total_vip_price)
	private TextView vipPrice;// vip��Ʒ
	@ViewInject(R.id.total_money)
	private TextView totalMoney;// ��ǰ���ܼ�

	@ViewInject(R.id.main_scan_etScanCodeBar)
	private EditText etCodeBar;
	private Editable etext;
	private String codebar;

	@ViewInject(R.id.main_scan_btn_sure)
	private Button scanBtnSure;
	@ViewInject(R.id.main_scan_btn_fkeys)
	private Button scanBtnFkeys;

	@ViewInject(R.id.main_f_keys)
	private View viewFkeys;

	@ViewInject(R.id.main_keys_others_vip)
	private Button vip;
	@ViewInject(R.id.main_keys_others_calcuate)
	private Button calcute;
	@ViewInject(R.id.main_keys_others_re_print)
	private Button rePrint;
	@ViewInject(R.id.main_keys_others_goods_back)
	private Button goodBack;
	@ViewInject(R.id.main_keys_others_amount)
	private Button amount;
	@ViewInject(R.id.main_keys_others_constume_back)
	private Button constumeBack;
	@ViewInject(R.id.main_keys_wait_form)
	private Button waitForm;
	@ViewInject(R.id.main_keys_go_form)
	private Button goForm;

	@ViewInject(R.id.main_keys_ln)
	private LinearLayout llayout;
	private boolean isShow = false;

	private List<Goods> list;
	private GoodsAdapter adapter;
	private ListView tableListView;
	private String codeBarStr;

	private MyOpenHelper goodsDataHelper;
	private MyOpenHelper tempDatahelper;
	private SQLiteDatabase goodsDataDb;
	private SQLiteDatabase tempDataDb;

	@ViewInject(R.id.main_keysF1)
	private Button btnf1;
	@ViewInject(R.id.main_keysF2)
	private Button btnf2;
	@ViewInject(R.id.main_keysF3)
	private Button btnf3;
	@ViewInject(R.id.main_keysF4)
	private Button btnf4;
	@ViewInject(R.id.main_keysF5)
	private Button btnf5;
	@ViewInject(R.id.main_keysF6)
	private Button btnf6;
	@ViewInject(R.id.main_keysF7)
	private Button btnf7;
	@ViewInject(R.id.main_keysF8)
	private Button btnf8;
	@ViewInject(R.id.main_keysF9)
	private Button btnf9;
	@ViewInject(R.id.main_keysF10)
	private Button btnf10;
	@ViewInject(R.id.main_keysF11)
	private Button btnf11;
	@ViewInject(R.id.main_keysF12)
	private Button btnf12;

	private User user = null;
	private int listViewCurrCilckPosition = 0; // ��¼��ǰ ����� item
	private boolean clickItem = false;// ��¼��ǰ listView��item�Ƿ�ɵ����
	private AlertDialog dialog;// �����
	
	@ViewInject(R.id.ic_scan)
	private ImageView cordBarScan;

	private static final int GET_VIPGOODS_DATA_AUTHORITY = 1;// ��ȡ VIP��Ʒ��ʶ��ʶ

	private static final int GET_SPECIALGOODS_DATA_AUTHORITY = 2;// ��ȡ �ؼ� ��Ʒ��ʶ

	private static final int UPDATE_ALLGOODS_DATA_COMPLATE = 3; // ��� ���ݿ� ����
																// ֪ͨ���߳� ����ui
	private static final int GET_CALCULATE_WAY_AUTHORITY = 4; // ��ȡ ���㷽ʽ
	
	private static final int UPDATE_VIP_SCRORE_AUTHORITY = 5;//ˢ��    ��������   �첽�����
	
	printer m_printer = new printer();// ���� ��ӡ����
	private Handler handler = new MainHandler();// ���ܴ�ӡ�� �ش�����������
	private static String sellAmount;
	private static String dayDate;
	private SharedPreferences sp;
	
	private class MainHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {

			case Scanner.BARCODE_READ: { // ���� ɨ����   ��������Ϣ
				
					Toast.makeText(MainActivity.this, "----", 1).show();
				 
					etCodeBar.setText("");
					// ��ʾ�������������ݣ�
					etCodeBar.setText((String) msg.obj);
					// ����ƶ��� �༭����һ�У�
					etCodeBar.setSelection(etCodeBar.getText().length());
					// ��������
					Scanner.play();
				}
				break;

			case Scanner.BARCODE_NOREAD: { // ����  ɨ����  ��������Ϣ
				MyToast.ToastIncenter(MainActivity.this, "δɨ�赽������Ϣ,����ɨ�����豸").show();
				break;
			}

			case UPDATE_ALLGOODS_DATA_COMPLATE: { // ���� ���� ��Ʒ��Ϣ�� ui
				proDialog.dismiss();
				MyToast.ToastIncenter(MainActivity.this, "��Ʒ��Ϣ�������...").show();
				break;
			}
			
			case PAY_THREAD_IS_OK: { //֧���ɹ�
				
				MyProgressDialog.stopProgress();
				
				for(Goods goods : list){  //���۵�  ������Ϣ   �������ݿ�
					OperationDbTableUtils.sellGoodsInsertTable(goodsDataDb,
							jsWay.equals("�����")?((String)msg.obj).split("-"):null, 
							goods,vipInput,cVipNo,(vipScore+fCurValue)+"",user,
							sellSheetNo,jsWay,getFormatNumber(totalMoney.getText().toString().trim()),shPayMoney);
				}
				
				//�������    ��������     �����±���������Ϣ��
				SelledToServerUtils.selledToServer(sellSheetNo,goodsDataHelper,sp);
				
				MyToast.ToastIncenter(MainActivity.this, "֧���ɹ�,���ڴ�ӡ�ۻ���,���Ե�...").show();
				
				printSellForm();
				
				break;
			}
			
			case SCAN_PAY_THREAD_IS_FAIL:{  //ɨ��֧��ʧ��
				MyProgressDialog.stopProgress();
				MyToast.ToastIncenter(MainActivity.this, "֧��ʧ��").show();
			}
			
			default:
				break;
			}
		}
	}

	@ViewInject(R.id.query_goods_btn)
	private Button queryGoodsBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		user = (User) getIntent().getSerializableExtra("user");// ��ȡ��ǰ�û�����
		goodsDataHelper = new MyOpenHelper(MainActivity.this,
				Content.GOODS_DB_NAME);
		tempDatahelper = new MyOpenHelper(MainActivity.this,
				Content.TEMP_DATA_DB);
		
		ExitApplicationUtils.getInstance().addActivity(this);

		ViewUtils.inject(this);

		// ����   �������   ������ɫ
		ViewGroup tableTitle = (ViewGroup) findViewById(R.id.table_title);
		tableTitle.setBackgroundColor(Color.rgb(177, 173, 172));

		list = new ArrayList<Goods>();

		tableListView = (ListView) findViewById(R.id.list);

		initListener(this);

		adapter = new GoodsAdapter(this, list);
		adapter.registerDataSetObserver(adapterObserver);

		tableListView.setAdapter(adapter);
		tableListView.setOnItemClickListener(this);
		
		m_printer.Open();
		
		sp = getSharedPreferences(Configs.APP_NAME, MODE_PRIVATE);
		dayDate = TimeUtils.getSystemNowTime("yyyy-MM-dd");//��ȡ      ��������
		
		inScanBar.addTextChangedListener(this);
		
		//���    ǰһ������������¼���ݣ�
		String beforeDay = getBeforeDay();
		if(sp.contains(beforeDay)){
			Editor editor = sp.edit();
			editor.remove(beforeDay);
			editor.commit();
		}
	} 

	public static String getBeforeDay(){
		//��ȡ  �����������ڣ�
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());//����������Ϊ��ǰ  ����
		calendar.add(Calendar.DAY_OF_MONTH, -1);//��  �����е�ǰһ��ʱ��  ��һ   ��ȡ  ǰһ��ʱ��
		
		Date beforeDay = calendar.getTime();//��ȡǰһ��ʱ�䣺
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//��ʽ��ʱ��
		String form = format.format(beforeDay);
	    
		return form;
	}
	
	private boolean vipInput = false;// ��¼��ǰ�Ƿ���VIP�û�
	
	private float vipScore;//��¼  ��Ա������Ϣ
	
	private float priceDiscount;

	private DataSetObserver adapterObserver = new DataSetObserver() {
		/**
		 * �Զ��ص��÷�����
		 */
		public void onChanged() {

			if (list.size() == 0) {
				vipInput = false;// ��ǰ�û� ��Ϊ��ͨ�û�
			}

			listViewCurrCilckPosition = 0;// ��ǰ ��ѡ���� ��Ϊ0;
			clickItem = false;// ��Ϊ ���ɵ��

			float agoSum = 0;// ԭ��
			// float accSum = 0;//�ּ�
			float spSum = 0;// �ؼ�
			float vipSum = 0;// vip��Ʒ
			float totalSum = 0;// С��

			String nowTime = TimeUtils.getSystemNowTime("yyyy-MM-dd");

			goodsDataDb = goodsDataHelper.getReadableDatabase();

			for (Goods item : list) {
				// �ж�     ��Ʒ��    �Ƿ����ؼ���Ʒ��   ����     ��Ʒ�ؼ���Ϣ ��� ���ؼ���Ʒ     �����ؼ���Ʒ��Ϣ �� vip��Ʒ�۸񣺣�
				Cursor spGoodsCursor = OperationDbTableUtils.getSpGoodsCursor(goodsDataDb,nowTime, item);
				Cursor vipGoodsCursor = OperationDbTableUtils.getVipCursor(goodsDataDb,item);
				// ��ѯ�� �ؼ���Ʒ��Ϣ�Ļ���ֱ��ʹ�� �� �ؼ���Ʒ�� ����۸�
				if (spGoodsCursor.moveToFirst()) {
					// ��ȡ      �ؼ���Ʒ������۸�
					float spPrice = Float.parseFloat(spGoodsCursor
							.getString(spGoodsCursor
									.getColumnIndex("fPrice_SO")));
					if (vipInput && vipGoodsCursor.moveToFirst()) {
						float vipPrice = Float.parseFloat(vipGoodsCursor
								.getString(vipGoodsCursor
										.getColumnIndex("fVipPrice")));
						if (vipPrice < spPrice) {
							item.setfNormalPrice(vipPrice);
							item.setPayMoney(vipPrice * item.getAmount());
							vipSum += (vipPrice * item.getAmount());
						}
					} else {
						item.setfNormalPrice(spPrice);
						item.setPayMoney(spPrice * item.getAmount());
					}
					spSum += (spPrice * item.getAmount());// ʹ�� ����۸�
					//   vip   �۸�ʹ�� �ؼۼ۸�
					totalSum += (spPrice * item.getAmount());
				} else if (vipGoodsCursor.moveToFirst()) {
					float vipPrice = Float.parseFloat(vipGoodsCursor
							.getString(vipGoodsCursor
									.getColumnIndex("fVipPrice")));
					if (vipInput) {// ��ǰ Ϊvip�û� ���� ui
						item.setfNormalPrice(vipPrice);
						item.setPayMoney(vipPrice * item.getAmount());
						totalSum += (vipPrice * item.getAmount()); // �ܼ�
					}else{  //��ǰ  ����vip�û�
						totalSum += item.getPayMoney();
					}
					vipSum += (vipPrice * item.getAmount()); // ��ȡ vip ��Ʒ�۸�
				} else {
					totalSum += item.getPayMoney(); // �ܼ� ʹ��������Ʒ�۸�
				}
				agoSum += item.getPayMoney(); // ��ȡ ԭ��
				vipScore += (item.getfVipScore()*item.getAmount());
			}
			
			agoMoney.setText(getFormatNumber(getFormatFloat(agoSum+"").toString()));
			spPrice.setText(getFormatNumber(getFormatFloat(spSum + "").toString()));
			vipPrice.setText(getFormatNumber(getFormatFloat(vipSum + "").toString()));
			totalMoney.setText(getFormatNumber(getFormatFloat(totalSum + "").toString()));
			priceDiscount = getFormatFloat(spSum + "").floatValue()
					+getFormatFloat(vipSum + "").floatValue();
			
		}
		/**
		 * �� Adpater ���� notifyDataSetInvalidate() ʱ��ص�
		 */
		@Override
		public void onInvalidated() {
		}
	};
	
	public BigDecimal getFormatFloat(String floatNum){
		BigDecimal bd = new BigDecimal(floatNum);
		bd = bd.setScale(4,BigDecimal.ROUND_HALF_UP);  
		return bd;
	}

	private void initListener(MainActivity mainActivity) {
		
		queryGoodsBtn.setOnClickListener(this);
		cordBarScan.setOnClickListener(this);
		
		keys_1.setOnClickListener(this);
		keys_2.setOnClickListener(this);
		keys_3.setOnClickListener(this);
		keys_4.setOnClickListener(this);
		keys_5.setOnClickListener(this);
		keys_6.setOnClickListener(this);
		keys_7.setOnClickListener(this);
		keys_8.setOnClickListener(this);
		keys_9.setOnClickListener(this);
		keys_0.setOnClickListener(this);
		keys_dot.setOnClickListener(this);
		keys_back_space.setOnClickListener(this);

		scanBtnSure.setOnClickListener(this);
		scanBtnFkeys.setOnClickListener(this);

		vip.setOnClickListener(this);
		calcute.setOnClickListener(this);
		rePrint.setOnClickListener(this);
		goodBack.setOnClickListener(this);
		amount.setOnClickListener(this);
		constumeBack.setOnClickListener(this);
		waitForm.setOnClickListener(this);
		goForm.setOnClickListener(this);

		btnf1.setOnClickListener(this);
		btnf2.setOnClickListener(this);
		btnf3.setOnClickListener(this);
		btnf4.setOnClickListener(this);
		btnf5.setOnClickListener(this);
		btnf6.setOnClickListener(this);
		btnf7.setOnClickListener(this);
		btnf8.setOnClickListener(this);
		btnf9.setOnClickListener(this);
		btnf10.setOnClickListener(this);
		btnf11.setOnClickListener(this);
		btnf12.setOnClickListener(this);
	}

	private static final int QUERY_GOODS_FRAGMENT_AUTHORITY = 87;
	
	@Override
	public void onClick(View v) {
		// ʵ�ֵ����¼���
		switch (v.getId()) {
		
		case R.id.query_goods_btn:  //ģ����ѯ   ��Ʒ��Ϣ
			
			Intent in = new Intent(this,QueryGoodsActivity.class);
			startActivityForResult(in, Configs.QUERY_ACTIVITY_REQUEST_CODE);
			
			break;
		
		case R.id.ic_scan:
			
//			 ��ɨ���ά���         ������Ϣ��
			Intent intent = new Intent();
			intent.setClass(this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, Configs.TO_SCAN_CODEBAR_RESULT_CODE);
			
			break;

		case R.id.keys_1:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("1");
			break;
		case R.id.keys_2:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("2");
			break;
		case R.id.keys_3:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("3");
			break;
		case R.id.keys_4:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("4");
			break;
		case R.id.keys_5:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("5");
			break;
		case R.id.keys_6:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("6");
			break;
		case R.id.keys_7:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("7");
			break;
		case R.id.keys_8:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("8");
			break;
		case R.id.keys_9:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("9");
			break;
		case R.id.keys_0:
			codebar = etCodeBar.getText().toString().trim();
			appendCodeBar("0");
			break;
		case R.id.keys_dot:
			codebar = etCodeBar.getText().toString().trim();
			if (codebar.contains(".") || codebar.equals("") || codebar == null) {
				break;
			} else {
				appendCodeBar(".");
			}
			break;
		case R.id.keys_back_space:
			codebar = etCodeBar.getText().toString().trim();
			if (codebar != null && !codebar.equals("")) {
				char[] chCode = codebar.toCharArray();
				etCodeBar.setText(codebar.substring(0, chCode.length - 1));
			}
			etext = etCodeBar.getText();
			Selection.setSelection(etext, etext.length());
			break;

		// ��ѯ �������� �Ƿ���ڣ�
		case R.id.main_scan_btn_sure:
			codeBarStr = etCodeBar.getText().toString().trim();
			if (codeBarStr != null && !codeBarStr.equals("")) {
				MyProgressDialog.showProgress(this, "���Ժ�", "���ڲ�ѯ������...");
				queryCodeBarIsExists(codeBarStr);
			} else {
				MyToast.ToastIncenter(MainActivity.this,  "�����ʽ����ȷ������������").show();
				
			}
			break;

		case R.id.main_scan_btn_fkeys:
			if (!isShow) {
				viewFkeys.setVisibility(View.VISIBLE);
				llayout.setVisibility(View.GONE);
				isShow = true;
			} else {
				viewFkeys.setVisibility(View.GONE);
				llayout.setVisibility(View.VISIBLE);
				isShow = false;
			}
			break;

		case R.id.main_keys_others_vip:// vip����
			if (list.size() != 0) {
				if(!vipInput){
					// ��ʾ    �����Ա     ���ţ�
					VipDialog dialog = new VipDialog(this,R.style.DialogStyle);
					dialog.show();
				}else{
					MyToast.ToastIncenter(MainActivity.this,  "��ǰ�Ѿ��ǻ�Ա����׼�ظ�����").show();
				}
			} else {
				MyToast.ToastIncenter(MainActivity.this, "��ǰ�޽�����Ʒ").show();
			}
			break;
		case R.id.main_keysF5:// ����
		case R.id.main_keys_others_calcuate:
			if (list.size() != 0) {
				// �� ���� ���ݿ��в�ѯ ������Ϣ��
				SQLiteDatabase ggodb = goodsDataHelper.getReadableDatabase();

				Cursor tempCursor = ggodb.query("t_"
						+ Content.TABLE_JSTYPE_NAME, new String[] { "*" },
						null, null, null, null, null);

				payWaylist = new ArrayList<String>();

				if (tempCursor.getCount() != 0) {
					for (tempCursor.moveToFirst(); !tempCursor.isAfterLast(); tempCursor
							.moveToNext()) {
						payWaylist.add(tempCursor.getString(tempCursor
								.getColumnIndex("detail")));
					}
					// ���� ҳ�棺
					PayDialogFragment editNameDialog = PayDialogFragment
							.getInstance(payWaylist,totalMoney.getText().toString().trim());
					editNameDialog.show(getSupportFragmentManager(),
							"PayDialog");
				} else {
					// ��ȡ    ����    ��ʽ��
					new VolleyUtils(this).getVolleyDataInfo(
							Configs.SERVER_BASE_URL
									+ Configs.GET_PAY_CALCULATE_WAY, this,
							GET_CALCULATE_WAY_AUTHORITY);
				}
			} else {
				MyToast.ToastIncenter(MainActivity.this, "��ǰ�޽�����Ʒ").show();
			}
			break;

		case R.id.main_keysF6:// ���´�ӡ
		case R.id.main_keys_others_re_print:
			if (list.size() != 0) {
				MyToast.ToastIncenter(MainActivity.this,"���ڴ�ӡ���Ե�...").show();
				printSellForm();
			} else {
				MyToast.ToastIncenter(MainActivity.this,"��ǰ����Ʒ���Դ�ӡ").show();
			}
			break;
		case R.id.main_keysF2:// ��Ʒ����
		case R.id.main_keys_others_goods_back:
			if (clickItem) {
				if (list.size() == 0) {
					MyToast.ToastIncenter(MainActivity.this,"��ǰ����Ʒ���Դ�ӡ").show();
				} else {
					// �� ��Ʒ ���� ��һ
					float amount = list.get(listViewCurrCilckPosition)
							.getAmount();
					if (amount == 1) {
						list.remove(list.get(listViewCurrCilckPosition));
					} else {
						list.get(listViewCurrCilckPosition).setAmount(
								amount - 1);
					}
					adapter.notifyDataSetChanged();
				}
			} else {
				MyToast.ToastIncenter(MainActivity.this,"��ǰδѡ���κ���").show();
			}
			break;
		case R.id.main_keysF4:// ����
		case R.id.main_keys_others_amount:
			// ѡ��һ�� listitem  ���� ����������
			if (clickItem) {
				UpdateAmountFragment upFragment = UpdateAmountFragment
						.getInstance();
				upFragment.show(getSupportFragmentManager(), "upFragment");
			} else {
				MyToast.ToastIncenter(MainActivity.this,"��ǰδѡ���κ���Ʒ��").show();
			}
			break;
		case R.id.main_keysF3:// ����
		case R.id.main_keys_others_constume_back:
			// ���� ���۵��� ��ѯ ������Ʒ��
			BackFragment frag = BackFragment.getInstance();
			frag.show(getSupportFragmentManager(), "backFrag");
			break;
		case R.id.main_keysF8:// �ҵ�
		case R.id.main_keys_wait_form:
			// �ҵ�
			if (list.size() != 0) {
				//����  ����
				String tableName = TimeUtils.getSystemNowTime("HH_mm_ss_1_MM_dd");

				tempDataDb = tempDatahelper.getWritableDatabase();
				// ���� ���ݿ���洢 waitForm
				tempDataDb.execSQL(String.format(
						Content.CREATE_TABLE_TEMP_ENTITY, tableName));
				/**
				 * ���뵽���ݿ�
				 */
				for (Goods good : list) {
					tempDataDb.execSQL(String.format(
							Content.INSERT_TABLE_TEMP, tableName,
							good.getcBarcode(), good.getAmount(),
							good.getPayMoney()));
				}
				MyToast.ToastIncenter(this, "�ҵ��ɹ�").show();
				list.clear();
				adapter.notifyDataSetChanged();
			} else {
				MyToast.ToastIncenter(this, "��ǰ�޵��ɹ�").show();
			}
			break;
		case R.id.main_keysF9:// ȡ��
		case R.id.main_keys_go_form:
			// ȡ��
			tempDataDb = tempDatahelper.getReadableDatabase();
			// ��ȡ���б�����
			Cursor cursor = tempDataDb.query("sqlite_master",
					new String[] { "name" }, "type = 'table'", null, null,
					null, null);
			if (cursor.getCount() != 0) {
				/**
				 * ��ѯ�� ���йҵ����� ����ʾ����
				 */
				selectTempTableNamesToShow(tempDataDb, cursor);
			} else {
				MyToast.ToastIncenter(this, "��ǰû�����ڵȴ��ĵ�").show();
			}
			break;

		case R.id.main_keysF1:// �޸�����:
			// ��ȡ��ǰ user���� �޸ĵ�ǰuser����� ���룺
			UpdateUserPwdDialog updateUserPasswordDialog = UpdateUserPwdDialog
					.getInstance(user);
			updateUserPasswordDialog.show(getSupportFragmentManager(),
					"updateUserPasswordDialog");
			break;
		case R.id.main_keysF7:// ��Ǯ��

			break;
			
		case R.id.main_keysF10:// �����
			
			break;

		// �������ݿ�
		case R.id.main_keysF11:
			// ��ʾ�Ի��� ���������ݿ���Ϣ
			dialog = new AlertDialog.Builder(this)
					.setTitle("��ȷ��")
					.setMessage("ȷ�ϸ������ݿ���Ϣ?\n\r�������Ҫ�����ӵ�ʱ�䡣")
					.setIcon(R.drawable.ic_launcher)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									proDialog = new ProgressDialog(
											MainActivity.this);
									proDialog.setMessage("���ڸ������ݿ�...");
									proDialog.show();
									proDialog.setCancelable(false);

									// �����߳� ���� ���ݿ���Ϣ
									new Thread() {
										public void run() {
											// ���� ������ ��ȡ VIP��Ʒ�ؼ���Ϣ��
											new VolleyUtils(MainActivity.this)
													.getVolleyDataInfo(
															Configs.SERVER_BASE_URL
																	+ Configs.QUERY_VIP_GOODS_DATA,
															MainActivity.this,
															GET_VIPGOODS_DATA_AUTHORITY);

											// ���� ������ ��ȡ �����ؼ���Ʒ��Ϣ��
											new VolleyUtils(MainActivity.this)
													.getVolleyDataInfo(
															Configs.SERVER_BASE_URL
																	+ Configs.QUERY_SPECIAL_GOODS,
															MainActivity.this,
															GET_SPECIALGOODS_DATA_AUTHORITY);
											// ʹ�� xutils���� ��Ʒ��Ϣ�����⣺
											// ���� ������ ��ȡ �����ؼ���Ʒ��Ϣ��
											updateGoodsDataWithX(Configs.SERVER_BASE_URL+ 
													String.format(Configs.QUERY_ALL_GOODS_INFO,1+"",100000+""));
										};
									}.start();
								}
							}).setNegativeButton("ȡ��", null).create();
			dialog.show();// ��ʾ�Ի���
			break;
		case R.id.main_keysF12:// ����
			// ������¼ҳ��
			Intent intent2 = new Intent();
			intent2.setClass(this, LoginActivity.class);
			startActivity(intent2);
			finish();
			break;
		}
	}
	
	private static String jsWay = "RMB";//��¼�տʽ��
	private static String shPayMoney = "0";// ��¼�û�Ӧ��֧���ܽ�
	private static String consumePayMoney = "0";//��¼    �տ���(�ͻ�֧�����ܽ��)
	private static String overPlus = "0";//��¼    ������
	
	/**
	 * ��ӡ   ������  ��Ϣ
	 */
	private void printSellForm() {
		// ��ӡ    ������Ϣ��
		m_printer.PrintStringEx("\n���������\n", 38, false, false,
				printer.PrintType.Centering);
		m_printer.PrintStringEx("(�˿���)\n", 30, false, false,
				printer.PrintType.Centering);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(20);m_printer.PrintLineString("", 10,PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(24);
		m_printer.PrintLineString("��Ʒ��/����", 24, PrintType.Left, false);
		m_printer.PrintLineString("   ����  *����",  24, PrintType.Centering, false);
		m_printer.PrintLineString("С��    ", 24, PrintType.Right, false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		int i=0;
		for (Goods goods : list) {
			i++;
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString(i+","+goods.getcGoodsName(),22,
					printer.PrintType.Left,false);
			m_printer.PrintLineEnd();
			
			m_printer.PrintLineInit(22);
			m_printer.PrintLineString("  "+goods.getcBarcode()+"-",22, PrintType.Left, false);
			m_printer.PrintLineString("       "+goods.getfNormalPrice()+" *"+goods.getAmount(), 22, PrintType.Centering, false);
			m_printer.PrintLineString(goods.getPayMoney()+"  ", 22, PrintType.Right, false);
			m_printer.PrintLineEnd();
		}
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("Ӧ�գ�"+totalMoney.getText().toString(), 22,PrintType.Left, false);
		m_printer.PrintLineString("�Żݣ�"+priceDiscount, 22,PrintType.Centering, false);
		m_printer.PrintLineString("ʵ�գ�"+totalMoney.getText().toString().trim() , 22,PrintType.Right, false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("��Ʒ����:"+ i , 22,PrintType.Left,false);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("�����ࣺ���ۣ�"+totalMoney.getText().toString().trim()
				+ "    �ؼۣ�"+ getFormatNumber(spPrice.getText().toString().trim()),
				22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();

		m_printer.PrintLineInit(22);
		if(sellSheetNo==null){
			updateSaleSheetNo();
		}
		m_printer.PrintLineString("No: " + sellSheetNo,22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(15);
		m_printer.PrintLineString("", 15,PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("����Ա:" + user.getUser(), 22,	printer.PrintType.Left,false);
		m_printer.PrintLineString(TimeUtils.getSystemNowTime("yyyy-MM-dd   hh:mm:ss"), 
				21,printer.PrintType.Right,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("���ʽ  " , 22,printer.PrintType.Left,false);
		m_printer.PrintLineString("�տ�    ���� ", 22,printer.PrintType.Centering,false);
		m_printer.PrintLineString("ʵ��   ",22,printer.PrintType.Right,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString(jsWay, 22,printer.PrintType.Left,false);
		m_printer.PrintLineString(consumePayMoney + "    " + overPlus, 24,PrintType.Centering,false);
		m_printer.PrintLineString(totalMoney.getText().toString().trim(),
				24,PrintType.Right,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(18);
		m_printer.PrintLineString("-------------------------------------------------", 18,
				PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("ƾ��Ʊ30���ڻ�ȡ��Ʊ" , 22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(22);
		m_printer.PrintLineString("����̨����ϵ�绰:18513667437" ,22,PrintType.Left,false);
		m_printer.PrintLineEnd();
		
		m_printer.PrintLineInit(100);
		m_printer.PrintLineString("", 100,PrintType.Centering, true);
		m_printer.PrintLineEnd();
		
		list.clear();//�������
		adapter.notifyDataSetChanged();
	}
	
	/**
	 * ʹ�� xutils ���� ��Ʒ��������Ϣ��
	 * @param url
	 */
	public void updateGoodsDataWithX(String url) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						MyToast.ToastIncenter(MainActivity.this, "���������").show();
						proDialog.dismiss();
					}
					@Override
					public void onSuccess(ResponseInfo<String> response) {
						if (response != null) {
							final JSONObject obj = JSON
									.parseObject(response.result);
							final int resuStatus = obj.getIntValue("resultStatus");
							if (resuStatus == 1) {// ������
								new Thread() {
									public void run() {
										Looper.prepare();
										ArrayList<Goods> gList = FastJsonUtils.getGoodsList(obj.getJSONArray("data"));
										goodsDataDb = goodsDataHelper
												.getWritableDatabase();
										// �������������Ϣ��
										goodsDataDb.execSQL("delete from t_"
												+ Content.TABLE_FORMNALPRICE);

										goodsDataDb.beginTransaction();
										// ���� ������ ��ӽ� ���ڣ�
										for (Goods goods : gList) {
											OperationDbTableUtils
													.insertGoodsToTable(
															goodsDataDb,
															goods,
															Content.TABLE_FORMNALPRICE);
										}
										
										goodsDataDb.setTransactionSuccessful();
										goodsDataDb.endTransaction();
										goodsDataDb.close();
//										 ����Ϣ ���͸����̣߳����� ui: ��� ���ݿ������Ϣ��
										Message message = handler
												.obtainMessage();
										message.what = UPDATE_ALLGOODS_DATA_COMPLATE;
										handler.sendMessage(message);
									};
								}.start();
							} else {
								MyToast.ToastIncenter(MainActivity.this,  "��ǰ������������").show();
							}
						}
					}
				});
	}
	
	private ProgressDialog proDialog;

	private ArrayList<String> payWaylist; // ��¼ ���㷽ʽ�� list
	private String cVipNo = null;
	private float fCurValue;
	private String sellSheetNo;

	/**
	 * ��ѯ�� ���йҵ��ı� ����ʾ����
	 */
	private void selectTempTableNamesToShow(SQLiteDatabase tempdb, Cursor cursor) {
		/**
		 * �����еı� ���뵽 ������
		 */
		ArrayList<String> tableNames = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String tableName = cursor.getString(cursor.getColumnIndex("name"));
			if (tableName.startsWith("t_")) {
				tableNames.add(tableName);
			}
		}

		/**
		 * �ж��йҵ����ݱ�����ʾ ��ֱ����ʾ ���û�ѡ��ҵ����ݱ� ����ʾ��
		 */
		if (tableNames.size() != 0) {
			// ������� list������
			list.clear();
			adapter.notifyDataSetChanged();
			// ��ʾ ѡ������û�ѡ�� �ҵ����ݣ�
			SelectFormTempFragment selectFragment = SelectFormTempFragment
					.getInstance(tableNames);
			selectFragment.show(getSupportFragmentManager(),
					"SelectFormFragment");
		} else if (tableNames.size() == 0) {
			MyToast.ToastIncenter(this, "��ǰû�����ڵȴ��ĵ�").show();
		}
	}

	/**
	 * �� �ùҵ������� ֱ����ʾ�� list�����У�
	 * @param tempdb
	 * @param tablename
	 */
	private void selectTempOneTableDataToShow(SQLiteDatabase tempdb,
			String tablename) {
		Cursor oneCursor = tempdb.query(tablename, new String[] { "*" }, null,
				null, null, null, null);
		goodsDataDb = goodsDataHelper.getReadableDatabase();
		while (oneCursor.moveToNext()) {
			String cBarcode = oneCursor.getString(oneCursor
					.getColumnIndex("cBarcode"));
			float amount = oneCursor.getFloat(oneCursor
					.getColumnIndex("amount"));
			double payMoney = oneCursor.getDouble(oneCursor
					.getColumnIndex("payMoney"));
			/**
			 * ���� ���� ��ѯ��Ʒ������Ϣ��
			 */
			Cursor cursor = OperationDbTableUtils.selectDataFromLocal(cBarcode,
					goodsDataHelper);
			Goods good = OperationDbTableUtils.goodsCursorToEntity(cursor);
			good.setAmount(amount);
			good.setPayMoney(payMoney);
			list.add(good);
		}
		adapter.notifyDataSetChanged();
		// ɾ�� ��ǰ ����
		tempdb.execSQL("drop table " + tablename);
	}

	/**
	 * ��ѯ������Ϣ��
	 */
	private void queryCodeBarIsExists(String codrBar) {

		goodsDataDb = goodsDataHelper.getWritableDatabase();

		/**
		 * ��������Ʒ��Ϣ���� ��ѯ������ ��
		 */
		Cursor goodsCursor = OperationDbTableUtils.selectDataFromLocal(codrBar,
				goodsDataHelper);

		if (goodsCursor.getCount() != 0) {// �����ݣ�

			Goods goods = OperationDbTableUtils
					.goodsCursorToEntity(goodsCursor);
			// ���� list �ж� ��ǰ �������Ƿ���� ���� �����õ�ǰ������һ ����������
			if (list.contains(goods)) {
				list.get(list.indexOf(goods)).setAmount(
						list.get(list.indexOf(goods)).getAmount() + 1);
			} else {
				list.add(goods);
			}
			adapter.notifyDataSetChanged();// ���� ������

			etCodeBar.setText("");

			MyProgressDialog.stopProgress();
		} else {
			/**
			 * ����û������  �� �������� ��ѯ�����Ƿ���ڣ� ������������ݵ��������ݱ� ��������ʾ�û�����Ʒ������ ��ʾ�û���
			 */
			new GetGoodsInfoAsyncTask(this).execute(Configs.SERVER_BASE_URL
					+ String.format(Configs.QUERY_ONE_GOODS_DATA, codrBar));
		}
	}
	
	/**
	 * ׷��
	 * 
	 * @param code
	 */
	private void appendCodeBar(String code) {
		StringBuffer strb = new StringBuffer(codebar);
		etCodeBar.setText(new String(strb.append(code)));
		etext = etCodeBar.getText();
		Selection.setSelection(etext, etext.length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * ��ȡ��ǰ �������һ�����ݣ�
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		listViewCurrCilckPosition = position;
		clickItem = true;
	}

	/**
	 * ���� fragment�Ļص���ʶ��
	 */
	@Override
	public void fragmentCallback(final String result, int fragmentAuthority) {
		
		if(result!=null&&!result.equals("")){
			switch (fragmentAuthority) {
			
			case Configs.SELECT_TEMP_FRAGMENT_AUTHORITY:// �û� ѡ��ҵ���� ����SelectFormTempFragment
				/**
				 * ��ȡ�� �û���� �� �Ա���в�����
				 */
				SQLiteDatabase tempdb = tempDatahelper.getReadableDatabase();
				selectTempOneTableDataToShow(tempdb,
						result.substring(0, result.length()));
				
				break;
				
			case Configs.UPDATE_FRAGMENT_AMOUNT: // ���� ���� ��Ʒ���� UpdateAmountFragment
				// ��ȡ Vip��Ϣ �������ݼ��ϣ� ����Ʒ���� �޸�Ϊ ����������
					if (Float.parseFloat(result) <= 0) {
						list.remove(listViewCurrCilckPosition);
					} else {
						list.get(listViewCurrCilckPosition).setAmount(
								Float.parseFloat(result));
					}
					adapter.notifyDataSetChanged();
				break;
				
			case Configs.CONSUME_BACK_FRAGMENT_AUTHORITY: // ����fragment���ŵ� backFragment�Ļص�
				
				//���ݿ��˵���      ��ѯ��Ʒ��Ϣ��
				goodsDataDb = goodsDataHelper.getReadableDatabase();
				Cursor cursor  = goodsDataDb.query("t_"+Content.TABLE_SELL_FORM,
						new String[]{"*"}, " cSaleSheetNo='"+result+"'", 
						null, null, null, null);
				
				if(cursor.moveToFirst()){
					list.clear();
					for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
						String cBarcode = cursor.getString(cursor.getColumnIndex("cBarCode"));
						Cursor cur = OperationDbTableUtils.selectDataFromLocal(
								cBarcode,goodsDataHelper);
						Goods goo = OperationDbTableUtils.goodsCursorToEntity(cur);
						Goods good = OperationDbTableUtils.sellGoodsToGoodsEntity(cursor,goo);
						list.add(good);
					}
					goodsDataDb = goodsDataHelper.getWritableDatabase();
					goodsDataDb.delete("t_"+Content.TABLE_SELL_FORM,
							" cSaleSheetNo = '"+ result +"'", null);
				}else{
					MyToast.ToastIncenter(this, "��ǰ���Ų�����").show();
				}
				adapter.notifyDataSetChanged();
				break;
				
			case Configs.VIP_FRAGMENT_QUHORITY: // Vip ����vip���Ų�ѯ �õ� ��Ա��Ϣ��VipFragment
				JSONObject object = JSON.parseObject(result);
				JSONArray array = object.getJSONArray("data");
				JSONObject obj = array.getJSONObject(0);
				cVipNo = obj.getString("cVipno");
				fCurValue = obj.getFloatValue("fCurValue");
				vipInput = true; // ��¼��ǰ ������ Ϊ VIP�û�
				adapter.notifyDataSetChanged();
				break;
				
			case Configs.GET_CALCULATE_WAY_AUTHORITY: // ��õ�   ���㷽ʽ    ��Ϣ ��ȡ���㷽ʽ���д���
				// ���  ���㷽ʽ ��
				if (payWaylist.get(Integer.parseInt(result)).equals("�����")) {
					float payMoney = Float.parseFloat(totalMoney.getText()
							.toString().trim());
					PayBalanceFragmentDialog fragment = PayBalanceFragmentDialog
							.getInstance(payMoney+"-"+"�����");
					fragment.show(getSupportFragmentManager(), "payBalance");
				} else if (payWaylist.get(Integer.parseInt(result)).equals("΢��")) {
					
					updateSaleSheetNo();  //��������������     ��ȡ����
					jsWay = "΢��";
					consumePayMoney = totalMoney.getText().toString().trim();
					overPlus = "0.00";
					
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, Configs.SCANNIN_WE_CHAT_REQUEST_CODE);
				} else if(payWaylist.get(Integer.parseInt(result)).equals("֧����")){
					
					updateSaleSheetNo();  //��������������     ��ȡ����
					jsWay = "֧����";
					consumePayMoney = totalMoney.getText().toString().trim();
					overPlus = "0.00";
					
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, Configs.SCANNIN_ALI_REQUEST_CODE);
				}else{
					MyToast.ToastIncenter(this, "��ǰ�汾δ���ɸ�֧����ʽ").show();
				}
				break;
				
			case Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY:
				// ������       �� fragment��       �ص�            ����¼�������       ���list������
				String[] payStrs = result.split("-");//��ȡ֧����ʽ 
				goodsDataDb = goodsDataHelper.getWritableDatabase();
				
				jsWay = "�����";
				shPayMoney = payStrs[0];//��¼  Ӧ��֧���Ľ��
				consumePayMoney = payStrs[1]; //��¼   �û�֧���ܽ��
				overPlus = payStrs[2]; //��¼�û�    Ӧ������Ľ��
				updateSaleSheetNo();  //����   ����������     ��ȡ����
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						//֧��,�ɹ�    ��    main ������Ϣ    ����ui:
						Message msg = Message.obtain(); 
						msg.what = PAY_THREAD_IS_OK;
						msg.obj = result;
						handler.sendMessage(msg);
					}
				}).start();
				
				if(vipInput){// ��ǰΪ    vip�û�
					//��  vip �û���Ϣ  ��������������
					new VolleyUtils(this).getVolleyDataInfo(
							String.format(Configs.SERVER_BASE_URL+Configs.UPDATE_VIP_SCORE,cVipNo,vipScore+fCurValue+""),
							this,UPDATE_VIP_SCRORE_AUTHORITY);
				}
				break;
				
			case QUERY_GOODS_FRAGMENT_AUTHORITY://��ѯ��Ʒ��Ϣ��   fragment
				break;
			default:
				break;
			}
		}
	}
	
	private String getFormatNumber(String text){
		String str = text;
		if(str!=null&&!str.equals("")&&str.contains(".")){
			while(str.endsWith("0")){
				str = str.substring(0,str.length()-1);
			}
		}
		if(str.endsWith(".")){
			str = str.substring(0,str.length()-1);
		}
		return str;
	}
	
	private void updateSaleSheetNo() {
		//��ȡ�����Ѿ�    ������������Ϣ :  
		sellAmount = sp.getString(dayDate, 0+"");  //��ȡ���������������Ϣ
		
		//��ȡ      ������������       ������������1
		int sellAm =  Integer.parseInt(sellAmount)+1;
		Editor ed = sp.edit();  //����    ��ǰ��������
		ed.putString(dayDate, sellAm+"");//����     ��������
		ed.commit();

		sellSheetNo = sp.getString(Configs.POS_ID, "01") + 
				TimeUtils.getSystemNowTime("yyyyMMdd") + getFormatAmount(sellAm+"");
	}
	
	/**
	 * ��ȡ  ��ʽ�����   ������Ϣ��
	 * @param string
	 * @return
	 */
	private static String getFormatAmount(String string) {
		//��ʽ��    ������Ϣ��
		StringBuffer str = new StringBuffer();
		if(string.length()<4){
			for(int i=0; i<4-string.length(); i++){
				str.append("0");
			}
			str.append(string);
		}
		return str.toString();
	}

	@Override
	public void volleyFinishedSuccess(final JSONArray array, int authority) {

		switch (authority) {
		case GET_VIPGOODS_DATA_AUTHORITY:// ��ȡ vip�û� ���е��ؼ���Ʒ�� ��Ϣ �浽���أ�

			new Thread() {
				public void run() {
					ArrayList<VIPGoods> vipGoods = FastJsonUtils
							.getListFromArray(array, VIPGoods.class);

					SQLiteDatabase goodsDb = goodsDataHelper
							.getWritableDatabase();

					// ��յ�ǰ����������Ϣ��
					goodsDb.execSQL("delete from t_"
							+ Content.TABLE_VIPGOODS_PRICE);

					goodsDb.beginTransaction();
					for (VIPGoods vipGood : vipGoods) {
						// ������Ϣ �� �û� ���ݱ��ڣ�
						OperationDbTableUtils.insertVipGoodsTable(goodsDb,
								vipGood);
					}

					goodsDb.setTransactionSuccessful();
					goodsDb.endTransaction();
					goodsDb.close();
				};
			}.start();
			break;

		case GET_SPECIALGOODS_DATA_AUTHORITY: // ���� �ؼ���Ʒ��Ϣ��
			new Thread() {
				public void run() {
					ArrayList<SpecialGoods> spGoods = FastJsonUtils
							.getListFromArray(array, SpecialGoods.class);

					SQLiteDatabase goDb = goodsDataHelper.getWritableDatabase();

					// ��յ�ǰ����������Ϣ��
					goDb.execSQL("delete from t_"
							+ Content.TABLE_SPECIALPRICE);

					goDb.beginTransaction();
					for (SpecialGoods vipGood : spGoods) {
						// ������Ϣ �� �û� ���ݱ��ڣ�
						OperationDbTableUtils.insertSpecialGoodsToTable(goDb,
								vipGood, Content.TABLE_SPECIALPRICE);
					}
					goDb.setTransactionSuccessful();
					goDb.endTransaction();
					goDb.close();
				};
			}.start();
			break;

		case GET_CALCULATE_WAY_AUTHORITY:
			// ��ȡ���㷽ʽ�ɹ�
			if (array.size() != 0) {
				payWaylist = new ArrayList<String>();
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					payWaylist.add(obj.getString("detail"));
				}
				// ���� ҳ�棺
				PayDialogFragment editNameDialog = PayDialogFragment
						.getInstance(payWaylist,totalMoney.getText().toString().trim());
				editNameDialog.show(getSupportFragmentManager(), "PayDialog");
			} else {
				// ��ȡʧ�ܣ�
				MyToast.ToastIncenter(this, "��ǰ�޿ɽ������ͣ�����������Ƿ���...").show();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void vollayFinishedFail(int authority) {
		switch (authority) {
		case GET_CALCULATE_WAY_AUTHORITY:
			MyToast.ToastIncenter(this, "��ǰ�޽��㷽ʽ�������Ƿ���������...").show();
			break;
		}
	}

	/**
	 * �첽 ����ص� ���� ���� �ص����������ݣ�
	 */
	@Override
	public void onTaskFinished(TaskResult result) {
		
		switch (result.task_id) {
		/**
		 * ��ѯ ����������Ϣ
		 */
		case Configs.GET_GOODS_INFO_AUTHORITY: // �ж��첽�����ʶ��
			if (result.resultStatus == 1) {

				JSONArray jsonArray = (JSONArray) result.resultData;

				if (jsonArray != null) {
					/**
					 * ������ ��ȡ�� ��Ʒ���ݼ� ���浽������
					 */
					ArrayList<Goods> gList = FastJsonUtils.getListFromArray(
							jsonArray, Goods.class);
					OperationDbTableUtils.insertGoodsToTable(goodsDataDb,
							gList.get(0), Content.TABLE_FORMNALPRICE);

					/**
					 * �ٴ� �������ݿ��в�ѯ ������Ϣ��
					 */
					Cursor cursor = OperationDbTableUtils.selectDataFromLocal(
							codeBarStr, goodsDataHelper);
					Goods goods = OperationDbTableUtils
							.goodsCursorToEntity(cursor);
					// ������������
					list.add(goods);
					adapter.notifyDataSetChanged();// ���� ������
				} else {
					// ������ δ��������
					MyToast.ToastIncenter(MainActivity.this, "����������Ƿ�����"
							).show();
				}
			} else {
				// ������ �����ݣ�
				MyToast.ToastIncenter(MainActivity.this,"��ǰ��Ʒ�����ڣ�������Ƿ����"
						).show();
			}
			etCodeBar.setText("");
			MyProgressDialog.stopProgress();
			break;

		default:
			break;
		}
	}

	/**
	 * ���� ��ά�� ɨ�� �����������ݣ�
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(requestCode != RESULT_CANCELED){
				if(data!=null){
					// �ж������룺
					switch (requestCode) {
					 case Configs.TO_SCAN_CODEBAR_RESULT_CODE:{
						 Bundle bundle = data.getExtras();
						 String cCodeBar = bundle
								 .getString("result");
						 // �жϱ����䣺
						 if (cCodeBar != null && !cCodeBar.equals("")
								 && cCodeBar.length() > 3) {
							 // ��ʾɨ�赽���������ݣ�
							 etCodeBar.setText(cCodeBar);
							 MyProgressDialog.showProgress(this, "���Ժ�", "���ڲ�ѯ������...");
							 queryCodeBarIsExists(cCodeBar);
						 } else {
							 Toast.makeText(this, "�����ʽ����ȷ������������", Toast.LENGTH_SHORT)
							 .show();
						 }
					 }
						break;
						
					case Configs.SCANNIN_ALI_REQUEST_CODE:{
						
						MyProgressDialog.showProgress(this, "���Ժ�", "���ڸ���...");
						
						Bundle bundle = data.getExtras();
						//��ʾɨ�赽������
						String payId = bundle.getString("result");
						
						toPay(payId,jsWay);  //ƴ�Ӳ���  ����ʼ֧��
					}
						break;
								    	
					case Configs.SCANNIN_WE_CHAT_REQUEST_CODE:{
						
						MyProgressDialog.showProgress(this, "���Ժ�", "���ڸ���...");
						
						Bundle bundle = data.getExtras();
						//��ʾɨ�赽������
						String payId = bundle.getString("result");
						
						toPay(payId,jsWay);  //ƴ�Ӳ���  ����ʼ֧��
					}
					break;
					case Configs.QUERY_ACTIVITY_REQUEST_CODE:{ //�����ѯ     ��Ʒ��Ϣ���ݵ�   activity�������ݣ�
						if(resultCode ==Configs.QUERY_ACTIVITY_RESULT_CODE){
							Goods goods = (Goods) data.getExtras().getSerializable("goods");
							list.add(goods);
							adapter.notifyDataSetChanged();
						}
					}
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * ��ʼ֧��
	 */
	private void toPay(String payId,String jsWay) {
		//ƴ�����������data:
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = format.format(new Date());    //ʱ��
		JSONObject testStr = new JSONObject();
		testStr.put("service", jsWay.equals("΢��")?"wx_pay":"alipay_pay_2");
		testStr.put("terminal_no", sp.getString(Configs.POS_ID, "01"));  // �����ն˺�
		testStr.put("subject", "����");  //��������
		testStr.put("total_fee", "0.01");//totalMoney.getText().toString().trim()
		testStr.put("out_trade_no", sellSheetNo);  //�̻���ˮ��
		
		if(jsWay.equals("֧����")){
			testStr.put("store_id", "001");
			testStr.put("undiscountable_amount", "");
		}
		
		testStr.put("timestamp", timestamp);
		testStr.put("dynamic_id", payId); //ɨ�赽��������
		testStr.put("oto_pid",Content.SHOP_PID); 
		
		/**
		 * ������  ת���ɼ�ֵ�Ե���ʽ      
		 */
		Map<String, String> map = MapUtils.getParamsFromJson(JSON.toJSONString(testStr));
		// Ȼ������ݰ�������ĸ˳��     ��������     
		String prestr = MapUtils.createLinkString(map); 
		// ��ȡ   �������ַ�����ժҪ��Ϣ
		String sign = MD5Util.GetMD5Code(prestr+ Content.PRIVATE_KEY);
		testStr.put("sign", sign);  //���    ժҪ��Ϣ  ����   ��������
		toServer(testStr);//��ʼ֧��
	}

	@Override
	protected void onDestroy() {
		if (goodsDataDb != null && goodsDataDb.isOpen()) {
			goodsDataDb.close();
		}
		if (tempDataDb != null && tempDataDb.isOpen()) {
			tempDataDb.close();
		}
		m_printer.Close();
		adapter.unregisterDataSetObserver(adapterObserver);
		super.onDestroy();
	}

	private static final int PAY_THREAD_IS_OK = 2;
	
	/**
	 * �����߳�   ��������
	 * @param testStr
	 */
	private void toServer(final JSONObject testStr) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Looper.prepare();  //����   ѭ������   ׼��������Ϣ
				
				String resultStr = SocketToNet.socketTcpRequset(Content.SERVER_IP, Content.SERVER_PORT,
						testStr);
				
				if(resultStr!=null&&!resultStr.equals("")){
						JSONObject objj = JSON.parseObject(resultStr);
						if(objj.getString("result_code").equals("SUCCESS")){
							//֧��,�ɹ�    ��    main ������Ϣ    ����ui:
							Message msg = Message.obtain(); 
							msg.what = PAY_THREAD_IS_OK;
							msg.obj = jsWay.equals("΢��") ? "΢��":"֧����";
							handler.sendMessage(msg);
						}else{
							//֧��ʧ��    ��    main ������Ϣ    ����ui:
							Message msg = Message.obtain(); 
							msg.what = SCAN_PAY_THREAD_IS_FAIL;
							handler.sendMessage(msg);
						}
				}else{
					//֧��ʧ��    ��    main ������Ϣ    ����ui:
					Message msg = Message.obtain(); 
					msg.what = SCAN_PAY_THREAD_IS_FAIL;
					handler.sendMessage(msg);
				}
			}
		}).start();		
	}
	private static final int SCAN_PAY_THREAD_IS_FAIL = 19;//ɨ��֧��ʧ��

	
	@ViewInject(R.id.scan_code_in_bar)
	private EditText inScanBar;  //���ɨ�赽��������Ϣ
	
	//------------------ ���    ѡ�е��¼�
	@Override
	public void afterTextChanged(Editable s) {
		
		if(s.toString()!=null&& !s.toString().equals("")){
//			��ʼ��ѯ�����Ƿ����
			MyProgressDialog.showProgress(this, "���Ժ�", "���ڲ�ѯ������...");
			queryCodeBarIsExists(codeBarStr);
			inScanBar.setText("");
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
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
