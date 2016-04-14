package com.shop.hlcloundposproject.fragments;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.Content;
import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.activity.LoginActivity;
import com.shop.hlcloundposproject.db.MyOpenHelper;
import com.shop.hlcloundposproject.entity.User;
import com.shop.hlcloundposproject.utils.NumKeysUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * ����  �û������  fragment
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-22
 */
public class UpdateUserPwdDialog extends DialogFragment implements OnClickListener {
	
	private View view;
	
	@ViewInject(R.id.update_password_sure)
	private Button upSureBtn;
	@ViewInject(R.id.update_password_exit)
	private Button upExitBtn;
	
	@ViewInject(R.id.update_currPwd)
	private EditText currPwdEt;
	@ViewInject(R.id.update_newPwd)
	private EditText newPwdEt;
	@ViewInject(R.id.update_rePwd)
	private EditText rePwdEt;
	
	private String currStr;
	private String newPwdStr;
	private String rePwdStr;
	
	private MyOpenHelper userDbHelper;

	private SQLiteDatabase userdb;
	
	private ImageView keysIc1,keysIc2,keysIc3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		
		//��ȡ���ݿ�  ������
		userDbHelper = new MyOpenHelper(getActivity(),Content.USER_INFO_DB_NAME);
		
		getDialog().setTitle("�޸�����");
		
		view = inflater.inflate(R.layout.update_password_fragment, container,false);
		
		keysIc1 = (ImageView) view.findViewById(R.id.icon_keys1);
		keysIc2 = (ImageView) view.findViewById(R.id.icon_keys2);
		keysIc3 = (ImageView) view.findViewById(R.id.icon_keys3);
		
		ViewUtils.inject(this,view);
		
		initListener();
		
		return view;
	}

	//��ʼ��    �¼���
	private void initListener() {
		upSureBtn.setOnClickListener(this);
		upExitBtn.setOnClickListener(this);
		keysIc1.setOnClickListener(this);
		keysIc2.setOnClickListener(this);
		keysIc3.setOnClickListener(this);
	}

	/**
	 * �����¼���
	 */
	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.update_password_sure:
				
				currStr = currPwdEt.getText().toString().trim();
				newPwdStr = newPwdEt.getText().toString().trim();
				rePwdStr = rePwdEt.getText().toString().trim();

				user = (User) getArguments().getSerializable("user");
				
				if(currStr==null||currStr.equals("")){
					currPwdEt.setError("����֤��ǰ����");
					currPwdEt.requestFocus();
					break;
				}else if(newPwdStr==null||newPwdStr.equals("")){
					newPwdEt.setError("����֤��������");
					newPwdEt.requestFocus();
					break;
				}else if(rePwdStr==null||rePwdStr.equals("")){
					rePwdEt.setError("����֤��ǰ����");
					rePwdEt.requestFocus();
					break;
				}else if(!currStr.equals(    user.getPass())){
					currPwdEt.setError("��ǰ�������벻��ȷ");
					currPwdEt.requestFocus();
					break;
				}else if(!rePwdStr.equals(newPwdStr)){
					rePwdEt.setError("��֤������������벻ƥ��");
					rePwdEt.requestFocus();
					break;
				}else if(currStr.equals(newPwdStr)){
					newPwdEt.setError("������͵�ǰ���벻����ͬ");
					newPwdEt.requestFocus();
					break;
				}
				
				//������������   �û���Ϣ  �޸��ˣ��������ݵ�   ��������
				updateServerUserInfo(Configs.SERVER_BASE_URL+String.format(Configs.UPDATE_PWD_TO_SERVER,user.getUser(),rePwdStr));
				break;
				
			case R.id.update_password_exit:
				//ȡ��   ��ť��
				onDestroyView();
				break;
				
			case R.id.icon_keys1:
				//�������ּ���
				NumKeysUtils keyDialog = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						currPwdEt, NumKeysUtils.FLOAT,
						new NumKeysUtils.TextChangeListener() {
							@Override
							public void onTextChange(String value) {
								currPwdEt.setText(value);
							}
						});
				keyDialog.show();
				break;
			case R.id.icon_keys2:
				//�������ּ���
				NumKeysUtils keyDialog2 = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						newPwdEt, NumKeysUtils.FLOAT,
						new NumKeysUtils.TextChangeListener() {
							@Override
							public void onTextChange(String value) {
								newPwdEt.setText(value);
							}
						});
				keyDialog2.show();
				break;
			case R.id.icon_keys3:
				//�������ּ���
				NumKeysUtils keyDialog3 = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						rePwdEt, NumKeysUtils.FLOAT,
						new NumKeysUtils.TextChangeListener() {
							@Override
							public void onTextChange(String value) {
								rePwdEt.setText(value);
							}
						});
				keyDialog3.show();
				break;
				
				default:
					break;
		}
	}

	private void updateServerUserInfo(final String url) {
		//ʵ�����������
				StringRequest request = new StringRequest(url,new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
							if(response!=null){
								//�ص����ݣ�
								JSONObject Object = JSON.parseObject(response);
								if(Object.getIntValue("resultStatus")==1){
									
								userdb = userDbHelper.getWritableDatabase();
									
								ContentValues values = new ContentValues();
									
								values.put("password",rePwdStr);
									
								userdb.update(Content.TABLE_USERS_NAME,
								values, " user = ?", 
								new String[]{user.getUser()});
									
								onDestroyView();
									
								Toast.makeText(getActivity(), "�����޸ĳɹ�,�����µ�½", Toast.LENGTH_LONG).show();
									
								Intent intent = new Intent();
								intent.setClass(getActivity(), LoginActivity.class);
								startActivity(intent);
									
								getActivity().finish();
							}else{
								Toast.makeText(getActivity(), "�û���Ϣ����ʧ�ܣ������û���Ϣ", 1).show();
							}
						}
					}
				},new ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
						Toast.makeText(getActivity(), "�û���Ϣ����ʧ�ܣ����������", 1).show();
					}
				});
										
			//3. ��������ӵ�Volley�����������
			Volley.newRequestQueue(getActivity()).add(request);
		}

	private static UpdateUserPwdDialog updateUserPassword;

	private User user;
	
	/**
	 * ��ȡ     ʵ��������
	 * @param user2
	 * @return
	 */
	public static UpdateUserPwdDialog getInstance(User user) {
			updateUserPassword = new UpdateUserPwdDialog();
			//������ݣ�
			Bundle bundle = new Bundle();
			bundle.putSerializable("user", user);
			updateUserPassword.setArguments(bundle);
			
		return updateUserPassword;
	}
	
	@Override
	public void onDestroy() {
		if(userdb.isOpen()&&userdb!=null){
			userdb.close();
		}
		super.onDestroy();
	}
}
