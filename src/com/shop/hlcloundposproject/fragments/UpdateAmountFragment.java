package com.shop.hlcloundposproject.fragments;

import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.utils.NumKeysUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

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
import android.widget.Toast;

public class UpdateAmountFragment extends DialogFragment implements OnClickListener {
	
	@ViewInject(R.id.update_amount_fragment_input)
	private EditText etInput;
	
	@ViewInject(R.id.update_amount_fragment_sure)
	private Button sureBtn;
	
	@ViewInject(R.id.update_amount_fragment_exit)
	private Button exitBtn;

	public static UpdateAmountFragment getInstance() {
		return new UpdateAmountFragment();
	}
	
	//����   fragment�Ļص�
	private FragmentCallback callback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();
	}
	
	private ImageView keysIc;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		View view = inflater.inflate(R.layout.update_amount_fragment, container,true);
		
		keysIc = (ImageView) view.findViewById(R.id.keys);
		
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
		
			case R.id.update_amount_fragment_sure:
				String etAmount = etInput.getText().toString().trim();
				
				callback.fragmentCallback(etAmount, Configs.UPDATE_FRAGMENT_AMOUNT);
				
				onDestroyView();
				
				break;
			case R.id.update_amount_fragment_exit:
				
				//����     fragment
				onDestroyView();
				
				break;
				
			case R.id.keys:
				
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
}


