package com.shop.hlcloundposproject.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * �Զ���  ������ʾ    �Ի���
 */
public final class MyProgressDialog extends ProgressDialog{
	
	/**
	 * ��volatile���εı��� 
	 * �߳���ÿ��ʹ�ñ�����ʱ��  �����ȡ�����޸ĺ��ֵ
	 * volatile��������ԭ���Բ���
	 */
	private static volatile MyProgressDialog dialog = null;
	
	@SuppressWarnings("unused")
	private Context context;

	/**
	 * ˽�л�   �����������
	 * @param context
	 */
	private MyProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}
	
	/**
	 * ��ʾ  ��ǰ����  �Ի���
	 */
	public static void showProgress(Context context,String title,String message){
		dialog = new MyProgressDialog(context);
		
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.show();
	}
	
	/**
	 * ֹͣ     ��ǰ������ʾ��
	 */
	public static void stopProgress(){
		if(dialog.isShowing()){
			dialog.dismiss();
		}
	}

}
