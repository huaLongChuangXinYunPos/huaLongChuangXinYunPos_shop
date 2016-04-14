package com.shop.hlcloundposproject.fragments;

import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.utils.NumKeysUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * ��Ʒ  ����  ��ѯ    fragment
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-26
 */
public class BackFragment extends DialogFragment implements OnClickListener {
	
	@ViewInject(R.id.constume_back_fragment_inputEt)
	private EditText etInput;
	
	@ViewInject(R.id.constum_back_fragment_sure)
	private Button sureBtn;
	
	@ViewInject(R.id.constum_back_fragment_exit)
	private Button exitBtn;

	public static BackFragment getInstance() {
		return new BackFragment();
	}
	
	//����   fragment�Ļص�
	private FragmentCallback callback;

	private ProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();
	}
	
	private ImageView keysIc;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

	   /** 
         * ������     �ޱ�����ʽ��     �Ի��� 
         */  
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
		View view = inflater.inflate(R.layout.consume_back_fragment, container,true);
		
		keysIc = (ImageView) view.findViewById(R.id.key);
		
		EditText ed = (EditText) view.findViewById(R.id.constume_back_fragment_inputEt);
		ed.setHint("��������˵���");
		
		ViewUtils.inject(this,view);
		
		initListener();
		
		return view;
	}
	
	/**
	 * �����¼�������
	 */
	private void initListener() {
		sureBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
		keysIc.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		
			case R.id.constum_back_fragment_sure:
				
				//��ȡ     ���ţ�
				String inputSellNum = etInput.getText().toString().trim();
				
				if(inputSellNum!=null&&!inputSellNum.equals("")){
					
					dialog = new ProgressDialog(getActivity());
					
					dialog.setMessage("���ڹ�����Ʒ...");
					
					dialog.show();
					
					onDestroyView();
					
					querySellNum(inputSellNum);//��ѯ��Ʒ����  �Ƿ���ڣ�
					
				}else{
					etInput.setError("��Ʒ���Ų�׼Ϊ��");
				}
				
				break;
			case R.id.constum_back_fragment_exit:
				//����     fragment
				onDestroyView();
				
				break;
			case R.id.key:
				
				//�������ּ���
				NumKeysUtils keyDialog = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						etInput, NumKeysUtils.FLOAT,
						new NumKeysUtils.TextChangeListener() {
							@Override
							public void onTextChange(String value) {
								etInput.setText(value);
							}
						});
				keyDialog.show();
				
				break;
			
			default:break;
			
		}
	}

	/**
	 * ��ѯ��Ʒ���ţ�
	 */
	private void querySellNum(String inputSellNum) {
	
//		//TODO ��ѯ     ��Ʒ���� ��   ��ѯ����Ʒ   ��      �����ݻص���    activity:
		callback.fragmentCallback(inputSellNum, Configs.CONSUME_BACK_FRAGMENT_AUTHORITY);
		
		dialog.dismiss();
	}
}
