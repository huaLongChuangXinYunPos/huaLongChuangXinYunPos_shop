package com.shop.hlcloundposproject.tasks;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.shop.hlcloundposproject.Configs;
import com.shop.hlcloundposproject.client.ClientApi;

public class UserLoginTask extends BaseTask {

	public UserLoginTask(TaskCallBack taskCallback) {
		/**
		 * ���ʸ���    �������
		 */
		super(taskCallback);
	}

	@Override
	protected TaskResult doInBackground(String... params) {
		TaskResult result = new TaskResult();//�������ؽ������
		//������첽�����     ��ʶ   
		result.task_id = Configs.USER_LOGIN_TASK_AUTHORITY;
		
		if(params!=null){
			//��ȡ��������
			JSONObject object = ClientApi.getClientInfo(params[0]);
			
			if(object!=null){
				//��ȡ����    ״̬��Ϣ ��
				try {
					//��Ϣ  ״̬�룺
					result.resultStatus = object.getIntValue("resultStatus");
					//�����ȡ�����
					result.resultData = object.getJSONArray("data");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
