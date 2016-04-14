package com.shop.hlcloundposproject.fragments;

import java.math.BigDecimal;

import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.utils.NumKeysUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ��Ʒ  ����  ��ѯ    fragment
 * com.hlrj.hlcloundpos.fragments
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-26
 */
public class PayBalanceFragmentDialog extends DialogFragment implements OnClickListener, TextWatcher {
	
	@ViewInject(R.id.pay_should_banlance)
	private TextView shouldPay; //Ӧ�����
	
	@ViewInject(R.id.pay_input_banlance)
	private EditText inputPay;//ʵ�����
	
	@ViewInject(R.id.pay_overplus)
	private TextView overplusPay; //������
	
	@ViewInject(R.id.pay_sure_btn)
	private Button btnSure;
	
	@ViewInject(R.id.pay_exit_btn)
	private Button btnExit;
	
	private ImageView keysIc;
	
	private static PayBalanceFragmentDialog dialog;
	
	public static PayBalanceFragmentDialog getInstance(String pay) {
		 
		dialog = new PayBalanceFragmentDialog();
		
		Bundle bundle = new Bundle();
		bundle.putString("pay", pay);
		dialog.setArguments(bundle);
		
		return dialog;
	}
	
	private FragmentCallback callback;

	private String[] payStrs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();//  �ص���  activity��
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
		View view = inflater.inflate(R.layout.pay_balance_dialog, container,true);
		ViewUtils.inject(this,view);
		
		keysIc = (ImageView) view.findViewById(R.id.key_icon);
		
		String pay = getArguments().getString("pay", null);
		payStrs = pay.split("-");
		
		shouldPay.setText(payStrs[0]);
		
		inputPay.addTextChangedListener(this);
		btnExit.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		keysIc.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		
			case R.id.pay_sure_btn:  
				//ȷ�ϰ�ť      ȷ��  �ص�  activity
				//�������
				if(!inputPay.getText().toString().trim().equals("")&&inputPay.getText().toString().trim()!=null){
					String backMoney = 
							shouldPay.getText().toString().trim()+"-"+
									inputPay.getText().toString().trim()+"-"+
									overplusPay.getText().toString().trim()+"-"+
									payStrs[1];
					
					callback.fragmentCallback(backMoney, Configs.GET_PAY_CALCULATE_RESULT_AUTHORITY);
					
					onDestroyView();
				}else{
					inputPay.setError("���������");
				}

				break;
				
			case R.id.pay_exit_btn:
				//ȡ��  ������棺
				onDestroyView();
				
				break;
			case R.id.key_icon:
				
				//�������ּ���
				NumKeysUtils keyDialog = new NumKeysUtils(getActivity(), R.style.MyKeyDialogStyleTop,
						inputPay, NumKeysUtils.FLOAT,
						new NumKeysUtils.TextChangeListener() {
							@Override
							public void onTextChange(String value) {
								inputPay.setText(value);
							}
						});
				keyDialog.show();
				
				break;
		}
	}
	
	/**
	 * ����   ����  ���ı仯��
	 */
	@Override
	public void afterTextChanged(Editable arg0) {
		if(arg0.toString()==null||arg0.toString().equals("")){
			overplusPay.setText(0.0+"");
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//����     ���ڸı��     ʱ��     �޸�     �����
		if(s.toString()!=null&&!s.toString().equals("")){
			float inputMoney = Float.parseFloat(s.toString());
			if((inputMoney - Float.parseFloat(payStrs[0]))>=0){
				overplusPay.setText(getFormatFloat((inputMoney - Float.parseFloat(payStrs[0]))+"").toString());
				btnSure.setEnabled(true);
				inputPay.setError(null);
			}else{
				inputPay.setError("��ǰ����������");
				btnSure.setEnabled(false);
			}
		}
	}

	public BigDecimal getFormatFloat(String floatNum){
		BigDecimal bd = new BigDecimal(floatNum);
		bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);  
		return bd;
	}
}
