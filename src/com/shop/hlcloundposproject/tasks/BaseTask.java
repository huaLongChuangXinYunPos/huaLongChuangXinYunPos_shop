package com.shop.hlcloundposproject.tasks;

import android.os.AsyncTask;

/**
 * �첽�����    ����  ��װ��   ����  Ϊһ��������
 * @author zhaoq_hero@163.com
 */
public abstract class BaseTask extends AsyncTask<String, Void, TaskResult> {

	/**
	 * ����ص��ӿ�      ���ڻص�����
	 */
	private TaskCallBack taskCallback;

	/**
	 * �������   
	 * @param taskCallback
	 */
	public BaseTask(TaskCallBack taskCallback) {
		this.taskCallback = taskCallback;
	}
	
	/**
	 * ��д�÷�����
	 * ��   ������Ϣ   �ص���  ���߳�
	 */
	@Override
	protected void onPostExecute(TaskResult result) {
		if(result!=null){
			taskCallback.onTaskFinished(result);
		}
	}
	
}
