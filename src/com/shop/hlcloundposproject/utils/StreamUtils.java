package com.shop.hlcloundposproject.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;

/**
 * �ر�   ���� ������
 * @author hl_zq
 * zhaoq_hero@163.com
 */
public final class StreamUtils {
	
	private StreamUtils(){}

	/**
	 * �ر���  �����������ӵ� ����
	 * @param conn
	 */
	public static void close(Object o) {
		
		if(o!=null){
			try {
				if(o instanceof InputStream){
					((InputStream) o).close();
				}else if(o instanceof OutputStream){
					((OutputStream) o).close();
				}else if(o instanceof Reader){
					((Reader) o).close();
				}else if(o instanceof Writer){
					((Writer) o).close();
				}else if(o instanceof HttpURLConnection){
					((HttpURLConnection) o).disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����ֽ���  �����ֽ����飺
	 * @param fin
	 * @return
	 */
	public static byte[] readStream(InputStream in) throws IOException{
		byte[] ret  = null;
		
		if(in!=null){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[128];
			int len;//��ȡ����
			while(true){
				len = in.read(buffer);
				if(len == -1){
					break;
				}
				bos.write(buffer,0,len);
			}
			//ע��   ���ֱ���Ҫ���� �ÿղ���  �����ڴ� ���
			buffer = null;
			ret = bos.toByteArray();
			bos.close();
		}
		return ret;
	}
}
