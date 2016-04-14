package com.shop.hlcloundposproject;

/**
 * com.hlrj.hlcloundpos
 * @Email zhaoq_hero@163.com
 * @author zhaoQiang : 2016-1-21
 */
/**
 * ��������    ����汾  ��Ϣ  ���������url
 */
public final class Configs {
	
	/**
	 * ���ڱ��湲�����  ����   �������  �ļ�
	 */
	public static final String APP_NAME = "hlrjPosClound";
	
	/**
	 * ��ȡ ��������ַ��192.168.3.200:1238
	 */
	public static final String SERVER_BASE_URL = "http://192.168.3.200:1238/AppServer/";
	
	/**
	 * ����    �ֻ�Ӳ��  ���к�  ��ȡ   pos����ʶ��s
	 */
	public static final String GET_POS_Id = "GetPosID.aspx?action=RQPOS&AppCode=%s";
	
	/**
	 * �û��˻���¼����  ��������ַ��
	 */
	public static final String LOGIN_USER = SERVER_BASE_URL +
			"GetUserInf.aspx?action=RQONE&userName=%s";
	
	/**
	 * ����   ��¼�첽�����  ���ʱ�ʶ��   UserLoginTask�ı�ʶ
	 */
	public static final int USER_LOGIN_TASK_AUTHORITY = 0;
	
	/**
	 * ����     ����  ������  ��ȡ   ��������Ʒ��Ϣ��   �첽�����ʶ   ��GetGoodsInfoAsyncTask 
	 */
	public static final int GET_GOODS_INFO_AUTHORITY = 1;
	
	/**
	 * ����    ��ȡ    �����û���Ϣ�������ʶ��
	 */
	public static final int GET_ALL_USER_DATA_AUTHORITY = 2;
	
	/**
	 * �����ȡ    ������Ʒ��Ϣ    ������Ʒ���ݿ��  �첽�����ʶ
	 */
	public static final int UPDATE_ALL_GOOD_DB_AUTHORITY = 3;
	
	/**
	 * ����  ��ȡ  �����ؼ���Ʒ   �첽�����ʶ��
	 */
	public static final int UPDATE_GOODS_SPECIAL_AUTHORITY = 4;
	
	/**
	 * ����      ��    ɨ��Activity   �������ݵ������룺
	 */
	public static final int TO_SCAN_CODEBAR_RESULT_CODE = 14;
	
	/**
	 * ����  ��ȡ ���н��㷽ʽ ��    fragment�ı�ʶ
	 */
	public static final int GET_CALCULATE_WAY_AUTHORITY = 5;  
	
	/**
	 * ����   vipFragment�Ļص���ʶ
	 */
	public static final int VIP_FRAGMENT_QUHORITY = 6;
	
	/**
	 * ��ȡ  ���� �����    �첽��ʶ
	 */
	public static final int GET_PAY_CALCULATE_RESULT_AUTHORITY = 7;
	
	/**
	 * ��ѯ����   ������Ϣ
	 * http://192.168.3.199:1234/AppServer/GetGoodsList.aspx?action=RQONE&cBarcode=6921744700275
	 */
	public static String QUERY_ONE_GOODS_DATA = "GetGoodsList.aspx?action=RQONE&cBarcode=%s";
	
	/**
	 * ��ѯ����     �û���Ϣ    http://192.168.3.199:1234/AppServer/GetUserInf.aspx?action=RQAll
	 */
	public static String QUERY_ALL_USERS_DATA = "GetUserInf.aspx?action=RQAll";
	
	/**
	 * ����  ������Ʒ����Ϣ     �ӷ�������ȡ����
	 * http://192.168.3.199:1234/AppServer/GetGoodsList.aspx?action=RQPOS
	 */
	public static String QUERY_ALL_GOODS_INFO = "GetGoodsList.aspx?action=RQPOS&Bgn=%s&End=%s";
	
	/**
	 * ������Ʒ��Ϣ����   �ؼ۱�����Ʒ
	 */
	public static String QUERY_ALL_SPECIAL_GOODS ="GetPloyOfSale.aspx?action=RQPloy";
	
	/**
	 * ��ѯ    vip��Ա��Ϣ��
	 */
	public static String QUERY_VIP_INFO = "GetVip.aspx?action=RQVIP&vipNo=%s";
	
	/**
	 * ��ѯ  vip��Ʒ��Ϣ��    ����   
	 */
	public static final String QUERY_VIP_GOODS_DATA = "GetGoodsList.aspx?action=RQVIP";
	
	/**
	 * ��ȡ  ���� �ؼ���Ʒ��Ϣ��  ���ӣ�
	 */
	public static final String QUERY_SPECIAL_GOODS = "GetPloyOfSale.aspx?action=RQPloy";
	
	
	/**
	 * ��ȡ  ���еļ��㷽ʽ��
	 */
	public static final String GET_PAY_CALCULATE_WAY = "GetJsType.aspx?action=RQJSTYPR";
	
	/**
	 * �޸�����  ���˺�  ��������������
	 */
	public static final String UPDATE_PWD_TO_SERVER ="GetUserInf.aspx?action=RQUPDATE&" +
			"userName=%s&newPass=%s";
	
	/**
	 * ��   ��Ա������Ϣ���͵�    ��������
	 */
	public static final String UPDATE_VIP_SCORE = "GetVip.aspx?action=RQSCO&vipNo=%s&fValue=%s";

	/**
	 * ���˻ػص�    ��ʶ  backFragment
	 */
	public static final int CONSUME_BACK_FRAGMENT_AUTHORITY = 2;

	/**
	 * ˢ��   ������Ʒ������    �ص���ʶ
	 */
	public static final int UPDATE_FRAGMENT_AMOUNT = 1;

	/**
	 * �û� ѡ��ҵ���� ����SelectFormTempFragment
	 */
	public static final int SELECT_TEMP_FRAGMENT_AUTHORITY = 0;

	/**
	 * ����   ���������pos����id
	 */
	public static final String POS_ID = "posid";
	
	public static final int SCANNIN_ALI_REQUEST_CODE = 13;//֧����ɨ�践����
	
	public static final int SCANNIN_WE_CHAT_REQUEST_CODE = 30; //΢��ɨ�践����
	
	/** 
	 * ����  ��Ͻ����ݷ�������������
	 */
	public static final String SELLED_DATA_TO_SERVER = "http://192.168.3.200:1238/AppPosServer.asmx/RetPosDetail";
	
	public static final int QUERY_ACTIVITY_RESULT_CODE = 34;
	
	public static final int QUERY_ACTIVITY_REQUEST_CODE = 36;
}
