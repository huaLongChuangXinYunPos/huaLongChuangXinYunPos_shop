package com.shop.hlcloundposproject.adapter;

import java.util.ArrayList;

import com.shop.hlcloundposproject.R;
import com.shop.hlcloundposproject.entity.Goods;
import com.shop.hlcloundposproject.entity.PayWayMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public final class QueryFragmentListAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<Goods> goods;
	
	public QueryFragmentListAdapter(Context context, ArrayList<Goods> goods) {
		super();
		this.context = context;
		this.goods = goods;
	}

	@Override
	public int getCount() {
		int ret = 0;
		if(goods!=null){
			ret = goods.size();
		}
		return ret;
	}

	@Override
	public Object getItem(int position) {
		return goods.get(position);
	}
	

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			
		    viewHolder = new ViewHolder();
			
		    convertView = LayoutInflater.from(context).inflate(R.layout.query_fragment_list_item,
					null);
		    viewHolder.textView1 =  (TextView) convertView.findViewById(R.id.query_fragment_item_goodsName);
		    viewHolder.textView2 = (TextView) convertView.findViewById(R.id.query_fragment_item_goodsPrice);
		    
		    convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.textView1.setText((position+1) +","+"品名："+goods.get(position).getcGoodsName());
		viewHolder.textView2.setText("条码："+goods.get(position).getcBarcode()+
				"   单价："+goods.get(position).getfNormalPrice()+"/"
				+goods.get(position).getcUnit());		
		
		return convertView;
	}

	class ViewHolder{
		TextView textView1,textView2;
	}
}
