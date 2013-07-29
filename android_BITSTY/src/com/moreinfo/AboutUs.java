package com.moreinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bitxty.R;
import com.sqlite.getdatafromSQ;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class AboutUs extends Activity{

	private View view;
	private getdatafromSQ data=null;
	private List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // ×¢ÒâË³Ðò
		view = this.getLayoutInflater().inflate(R.layout.more_aboutus, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // ×¢ÒâË³Ðò
                R.layout.title);
		data =  ((getdatafromSQ)getApplicationContext());  
		list = data.getRSSlist(); 
		 data.addActivity(this);
	}
	

}
