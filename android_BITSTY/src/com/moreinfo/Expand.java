//package com.example.welcome;
//
//import com.example.welcome.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//
//public class Expand extends Activity{
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_expand);
//		Bundle bundle = getIntent().getExtras();
//		String userid=bundle.getString("lost_id");
//		System.out.println(userid);
//	}
//
//}
package com.moreinfo;
import java.io.IOException;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.bitxty.R;

import android.os.Bundle;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Expand extends Activity{
private TextView content,title,phone;
private String goodsid;
private String [] expand={"","","","","","","",""};
private ProgressDialog mDialog;
private View view;
private Button delete1;
private static String token="guoguoguo";
	@Override
protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		view = this.getLayoutInflater().inflate(R.layout.activity_expand, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
		title=(TextView)findViewById(R.id.title);
		content=(TextView)findViewById(R.id.content);
		phone=(TextView)findViewById(R.id.phone);
		delete1=(Button)findViewById(R.id.delete1);
		delete1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deletelist();
			}
		});
		new MyThread().start();
		creatDialog();
	}
    private void creatDialog() {
		// TODO Auto-generated method stub
    	  mDialog=new ProgressDialog(this);
    	  mDialog.setTitle("");
    	  mDialog.setMessage("正在加载中。。。");
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
			String [] str=(String [])msg.obj;
			for(int i=0;i<8;i++)
			{System.out.println(str[i]);}
			 if(mDialog.isShowing()==true){
				  mDialog.dismiss(); 
			  }
			  if(str[0].toString().equals("anyType{}")==true){
				  title.setText(str[3]);				 
				  content.setText(str[6]);
				  phone.setText(str[5]);

			    }
			    else{
			    	Toast.makeText(getApplicationContext(),str[0],Toast.LENGTH_SHORT).show();
			    }
		}
	}
	
	class MyThread extends Thread{
		public void run(){
			Bundle bundle =getIntent().getExtras();
			String activityid=bundle.getString("idmain");
			String id=bundle.getString("typemain");
			String method="goodsinfo";
			String serviceurl="http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/","GetLostInfo"); 
			request.addProperty("lost_id", activityid.toString()); 
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	        envelope.bodyOut=request;
	        envelope.dotNet=true;
	        HttpTransportSE http = new HttpTransportSE(serviceurl);
	    	(new MarshalBase64()).register(envelope);
	        try {
	        	http.call(null,envelope);
	            SoapObject sb = (SoapObject)envelope.bodyIn;
	            SoapObject contactlist1 = (SoapObject)((SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0)).getProperty(0);
	            System.out.println(contactlist1);
	            System.out.println(contactlist1.getPropertyCount());
	            for(int i=0;i<contactlist1.getPropertyCount();i++){
	        	expand[i]=contactlist1.getProperty(i).toString();
	            System.out.println("2:"+expand[i]);
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Looper looper = Looper.getMainLooper();
			MyHandler handler = new MyHandler(looper);
			Message message = handler.obtainMessage(1,2,3,expand);
			handler.sendMessage(message);
		}
	}
	public void deletelist(){
		Bundle bundle =getIntent().getExtras();
		String activityid=bundle.getString("idmain");
		String id=bundle.getString("typemain");
		String method="delete";
		String serviceurl="http://10.1.151.26/ydzw.asmx";
		SoapObject request = new SoapObject("http://tempuri.org/","DeleteLost");
		request.addProperty("lost_id", activityid.toString());
		request.addProperty("token",token.toString());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.bodyOut=request;
        envelope.dotNet=true;
        HttpTransportSE http = new HttpTransportSE(serviceurl);
    	(new MarshalBase64()).register(envelope);
        try {
        	http.call(null,envelope);
            SoapObject sb = (SoapObject)envelope.bodyIn;
            SoapObject contactlist1 = (SoapObject)((SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0)).getProperty(0);
        	System.out.println(contactlist1);
        	if(contactlist1.getProperty(0).toString().equals("anyType{}")==true){
        		Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
        		Intent intent =new Intent();
        		intent.setClass(Expand.this, set.class);
        		startActivity(intent);
        		finish();
        	}
        	else{
        		Toast.makeText(getApplicationContext(),contactlist1.getProperty(0).toString(),Toast.LENGTH_SHORT).show();
        	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 
