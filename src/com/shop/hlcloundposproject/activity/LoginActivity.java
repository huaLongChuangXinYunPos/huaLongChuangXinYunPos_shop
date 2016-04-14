package com.shop.hlcloundposproject.activity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.Content;
import com.shop.hlcloundposproject.MainActivity;
import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.db.MyOpenHelper;
import com.shop.hlcloundposproject.entity.User;
import com.shop.hlcloundposproject.tasks.TaskCallBack;
import com.shop.hlcloundposproject.tasks.TaskResult;
import com.shop.hlcloundposproject.tasks.UserLoginTask;
import com.shop.hlcloundposproject.utils.ExitApplicationUtils;
import com.shop.hlcloundposproject.utils.KeyboardUtil;
import com.shop.hlcloundposproject.utils.MyProgressDialog;
import com.shop.hlcloundposproject.utils.MyToast;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��¼   ����
 */
public class LoginActivity extends Activity implements OnClickListener, TaskCallBack, OnTouchListener {

	@ViewInject(R.id.account)
	private EditText etAccount;
	@ViewInject(R.id.password)
	private EditText etPassword;

	@ViewInject(R.id.login_btn_sure)
	private Button loginSure;
	
	@ViewInject(R.id.login_btn_exit)
	private Button loginExit;
	
	/**
	 * ���Ե�¼ʱ��      �˻�������ֵ��
	 * */
	private String account;
	private String password;
	private SQLiteOpenHelper userHelper;
	private SQLiteDatabase userdb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ExitApplicationUtils.getInstance().addActivity(this);

		setContentView(R.layout.activity_login_in);
		
		ViewUtils.inject(this);
		
		userHelper = new MyOpenHelper(LoginActivity.this,
				Content.USER_INFO_DB_NAME);
		
		/**
		 * ��ʼ���༭�¼�    �͵�¼�¼�
		 */
		loginSure.setOnClickListener(this);
		loginExit.setOnClickListener(this);
		
		etAccount.setOnTouchListener(this);
		etPassword.setOnTouchListener(this);
	}

	//��¼��ȡ����    �����¼���
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.login_btn_sure:
				
				// ��ȡ  ����ֵ
				account = etAccount.getText().toString().trim();
				password = etPassword.getText().toString().trim();
				
				// �˻���֤��
				if (TextUtils.isEmpty(account)) {
					//  ���Ϊnull
					etAccount.setError("�˺Ų�׼Ϊ��");
					etAccount.requestFocus();
					break;
				} 
				
				// ������֤
				if (TextUtils.isEmpty(password)) {
					etPassword.setError("���벻׼Ϊ��");
					etPassword.requestFocus();
					break;
				} 
				
				userdb = userHelper.getReadableDatabase();
				
				Cursor cursor = userdb.query(Content.TABLE_USERS_NAME,
						new String[]{"user","password","name","right","ki","quanXian"},
						"user = '"+ account+"'", null,null, null, null);
				
				MyProgressDialog.showProgress(this,"���ڵ�¼...",null);
				/**
				 * ��֤�����û���
				 */
				switch(cursor.getCount()){
					case 0://û�в�ѯ���û�   ���������˲���
						new UserLoginTask(this).execute(String.format(Configs.LOGIN_USER,account));
						break;
					case 1://��ѯ�� һλ�û����ж�����  �Ƿ�һ��
						User user = new User();
						while(cursor.moveToNext()){
							if(cursor.getString(cursor.getColumnIndex("password")).equals(password)){
								//��ȡuser ����
								user.setKi(cursor.getString(cursor.getColumnIndex("ki")));
								user.setName(cursor.getString(cursor.getColumnIndex("name")));
								user.setPass(cursor.getString(cursor.getColumnIndex("password")));
								user.setQuanxian(cursor.getString(cursor.getColumnIndex("quanXian")));
								user.setRight(cursor.getString(cursor.getColumnIndex("right")));
								user.setUser(cursor.getString(cursor.getColumnIndex("user")));
								
								toMainActivity(user);
								
								finish();
							}else{
								Toast.makeText(this, "����֤��������", Toast.LENGTH_SHORT).show();
							}
							MyProgressDialog.stopProgress();
							break;
						}
						
						break;
					default:
						//��ѯ�� ��λ�û�
						Toast.makeText(this, "����ϵ����Ա����ǰ�û������ڳ�ͻ��",Toast.LENGTH_SHORT).show();
						MyProgressDialog.stopProgress();
						break;
					}
				
				break;
				
			case R.id.login_btn_exit:
				finish();
				break;

			default:
					break;
		}
	}

	//����   �ص�����   �ص�����
	@Override
	public void onTaskFinished(TaskResult result) {
		/**
		 * �жϷ�������  ���첽����ı�ʶ��
		 */
		if(result!=null && result.task_id == Configs.USER_LOGIN_TASK_AUTHORITY){
			
			if(result.resultStatus != 0 ){
				JSONArray array = (JSONArray) result.resultData;
					try {
						JSONObject jobj = array.getJSONObject(0);
						User user = User.userLoginParser(jobj);
						/**
						 * ����һ��    ���е�¼
						 */
						if(user.getPass().equals(password)){
							// ���Ե�¼   ����
							toMainActivity(user);
							
							MyProgressDialog.stopProgress();
							
							/**
							 * ������Ϣ  ������ ���ݿ�
							 */
							userdb = userHelper.getWritableDatabase();
							// �����û���Ϣ������
							ContentValues values = new ContentValues();
							values.put("ki", user.getKi());
							values.put("name", user.getName());
							values.put("password", user.getPass());
							values.put("quanxian", user.getQuanxian());
							values.put("right", user.getRight());
							values.put("user",user.getUser());
							
							userdb.insert(Content.TABLE_USERS_NAME, null, values);
						}else{
							MyProgressDialog.stopProgress();
							Toast.makeText(this, "����֤��������", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			}else{
				MyProgressDialog.stopProgress();
				Toast.makeText(this, "����֤�û���������", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * �������棺
	 * @param user
	 */
	private void toMainActivity(User user) {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		intent.putExtra("user",user);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		switch(v.getId()){
			case R.id.password:
				etPassword.requestFocus();
				etPassword.setInputType(InputType.TYPE_NULL);
				new KeyboardUtil(this,getApplicationContext(),etPassword).showKeyboard();
				
				break;
				
			case R.id.account:
				etAccount.requestFocus();
				etAccount.setInputType(InputType.TYPE_NULL);
				new KeyboardUtil(this,getApplicationContext(),etAccount).showKeyboard();
				
				break;
				default:
					break;
		}
		return false;
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
