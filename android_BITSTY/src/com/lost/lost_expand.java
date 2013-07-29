package com.lost;

import com.bitxty.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.os.Bundle;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class lost_expand extends Activity{
private TextView content1,title1,phone1,place1,time1,name1;
private ProgressDialog mDialog;
private View view;
private String lost;
private Map<String ,Object> map = new HashMap<String ,Object>();
	@Override
protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 getWindow().setBackgroundDrawableResource(R.color.white);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
			view= this.getLayoutInflater().inflate(R.layout.lost_expand, null);
			setContentView(view);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
	                R.layout.title);
		title1=(TextView)findViewById(R.id.title);
		content1=(TextView)findViewById(R.id.content);
		place1=(TextView)findViewById(R.id.place);
		phone1=(TextView)findViewById(R.id.phone);
		time1=(TextView)findViewById(R.id.time);		
		new MyThread().start();	
		creatDialog();
		if(mDialog.isShowing()==true){
			  mDialog.dismiss();   //关闭对话窗口			  
			  }
	}   
	private void creatDialog() {
		// TODO Auto-generated method stub
    	  mDialog=new ProgressDialog(this);
    	  mDialog.setTitle("");
    	  mDialog.setMessage("正在加载请稍后");
    	  mDialog.setIndeterminate(true);
    	  mDialog.setCancelable(true);
    	  mDialog.show();
    	  
	}
	class MyHandler extends Handler{		
		public MyHandler(Looper looper){
			super(looper);
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int str=(Integer)msg.obj;
			if(str==1){
			 if(mDialog.isShowing()==true){
				  mDialog.dismiss();   //关闭对话窗口
			  }
			  if(map.get("state").toString().equals("anyType{}")==true){
				  	title1.setText(map.get("goods_name").toString());
				  	phone1.setText(map.get("goods_contact").toString());
				  	content1.setText(map.get("goods_detail").toString());
		    }
			    else{
			    	Toast.makeText(getApplicationContext(),map.get("state").toString(),Toast.LENGTH_SHORT).show();
			    }
		}
		}

	}
	class MyThread extends Thread{
		public void run(){
			Bundle bundle =getIntent().getExtras();
			String lostid=bundle.getString("lost_id");
	    	System.out.println(lostid);
			String method="goodsexpand";
			String serviceurl="http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/","GetLostInfo"); 
			request.addProperty("lost_id", lostid.toString()); 
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	        envelope.bodyOut=request;
	        envelope.dotNet=true;
	        HttpTransportSE http = new HttpTransportSE(serviceurl);
	    	(new MarshalBase64()).register(envelope);
	        try {
	        	http.call(null,envelope);
	            SoapObject result = (SoapObject)envelope.bodyIn;
	            SoapObject newlist =(SoapObject) ((SoapObject)((SoapObject)result.getProperty(0)).getProperty(0)).getProperty(0);
	        //    System.out.println("***************:"+ newlist.getPropertyCount());
	            //String error=(((SoapObject)newlist.getProperty(0)).getProperty(0)).toString();
	            //System.out.println(error);
	           // System.out.println((SoapObject)newlist.getProperty(0));
	            map.put("state", newlist.getProperty(0).toString());
	            map.put("lost_tag	", newlist.getProperty(1).toString());
	            map.put("lost_fbtime", newlist.getProperty(2).toString());
	            map.put("goods_name", newlist.getProperty(3).toString());
	            map.put("goods_adress", newlist.getProperty(4).toString());
	            map.put("goods_contact",newlist.getProperty(5).toString());
	            map.put("goods_detail", newlist.getProperty(6).toString());
	            map.put("nickname", newlist.getProperty(7).toString());
	            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Looper looper = Looper.getMainLooper();
			MyHandler handler = new MyHandler(looper);
  		    Message message = handler.obtainMessage(1,2,3,1);
			handler.sendMessage(message);
	        
		}
	}
}

