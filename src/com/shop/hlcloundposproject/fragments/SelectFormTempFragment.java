package com.shop.hlcloundposproject.fragments;

import java.util.ArrayList;

import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectFormTempFragment extends DialogFragment implements OnItemClickListener {
	
	/**
	 * ��volatile���εı��� 
	 * �߳���ÿ��ʹ�ñ�����ʱ��  �����ȡ�����޸ĺ��ֵ
	 * volatile��������ԭ���Բ���
	 */
	public static SelectFormTempFragment slectFormFragment = null;
	
	private View view;
	
	private ArrayList<String> list;
	
	private ArrayAdapter<String> adapter;
	
	@ViewInject(R.id.select_table_list)
	private ListView listView;

	//�ص�����
	private FragmentCallback callback;
	
	/**
	 * ����ģʽ��ȡ����
	 * @return
	 */
	public static SelectFormTempFragment getInstance(ArrayList<String> tableNames){
		
		slectFormFragment = new SelectFormTempFragment();
		/**
		 * �������ݣ�
		 */
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("tableNamesList", tableNames);
		slectFormFragment.setArguments(bundle);
		
		return slectFormFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callback = (FragmentCallback) getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//���õ�ǰ�Ի���û��   û��ͷ
		getDialog().setTitle("��ǰ�ҵ���");
		
		view = inflater.inflate(R.layout.select_temp_table_dialog_fragment,container,false);
		
		ViewUtils.inject(this,view);
		
		//��ʼ��   ������
		initView();
		
		return view;
	}

	private void initView() {
		
		list = getArguments().getStringArrayList("tableNamesList");
		
		//��list��Щ������ʾ������
		ArrayList<String> showList = initList(list);
		
		adapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1,showList);
		
		listView.setOnItemClickListener(this);
		
		listView.setAdapter(adapter);
	}

	/**
	 * �� list��Ϊ��12:13:23(01/16)  ����ʽ��ʾ������
	 * t_12_13_23   _1_   01_16
	 * @param list2 
	 */
	private ArrayList<String> initList(ArrayList<String> list) {
		ArrayList<String> showList = new ArrayList<String>();
		for(String text : list){
			
			String[] showText = text.split("_1_");
			String[] nums = showText[0].split("_");
			String[] tes = showText[1].split("_");
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(nums[1]+":"+nums[2]+":"+nums[3]+"("+tes[0]+"/"+tes[1]+")");
			
			showList.add(sb.toString());
		}
		return showList;
	}

	/**
	 * ��  ��ȡ�������ݻص�  ��activity
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		callback.fragmentCallback(list.get(position),Configs.SELECT_TEMP_FRAGMENT_AUTHORITY);
		onDestroyView();
	}
}
