package com.pulllist;

import java.util.ArrayList;

import com.activity.life_phoneActivity1;
import com.activity.life_phoneActivity2;
import com.bitxty.R;
import com.sqlite.getdatafromSQ;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


public class PopMenu1 implements OnItemClickListener {
	public interface OnItemClickListener {
		public void onItemClick(int index);
	}
	
	private ArrayList<String> itemList;
	private Activity context;
	private PopupWindow popupWindow;
	private ListView listView;
	private OnItemClickListener listener;
	private LayoutInflater inflater;

	
	public PopMenu1(Activity context) {
		this.context = context;
		itemList = new ArrayList<String>(5);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.popmenu, null);

		listView = (ListView) view.findViewById(R.id.listView);
		listView.setAdapter(new PopAdapter());
		listView.setOnItemClickListener(this);

		popupWindow = new PopupWindow(view, 
				context.getResources().getDimensionPixelSize(R.dimen.popmenu_width), 
				LayoutParams.WRAP_CONTENT);

		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		listView.setOnItemClickListener(mOnClickListener);
		
		
	}
	
	private AdapterView.OnItemClickListener mOnClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id)
        {
            switch(position)
            {
            case 0:  
            	break;
            case 1: 		
            	getdatafromSQ SQdata = ((getdatafromSQ)context.getApplicationContext());
            	for (int i = 0; i < SQdata.activity.size(); i++) {
        			try {
        				SQdata.activity.get(i).finish();
        			} catch (Exception e) {
        				continue;
        			}
            	}
            	break;

            case 2:
            	break;
            }
        }

		private getdatafromSQ getApplicationContext() {
			// TODO Auto-generated method stub
			return null;
		}
    };
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (listener != null) {
			listener.onItemClick(position);
		}
		dismiss();
	}


	public void setOnItemClickListener(OnItemClickListener listener) {
		 this.listener = listener;
	}

	public void addItems() {
		for (String s : items)
			itemList.add(s);
	}


	public void addItem(String item) {
		itemList.add(item);
	}


	public void showAsDropDown(View parent) {
		
		popupWindow.showAsDropDown(parent, 10,

				context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));
		
//		popupWindow.showAtLocation(parent,  Gravity.CENTER,10,
//
//				context.getResources().getDimensionPixelSize(R.dimen.popmenu_yoff));



		popupWindow.setFocusable(true);

		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}


	public void dismiss() {
		popupWindow.dismiss();
	}

	private final class PopAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.pomenu_item, null);
				holder = new ViewHolder();
				convertView.setTag(holder);
				holder.groupItem = (TextView) convertView.findViewById(R.id.textView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.groupItem.setText(itemList.get(position));

			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}
	String[] items = new String[]{ "关于我们", "退出"};
}
