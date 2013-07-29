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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class more_detail extends Activity{
private TextView title,content,time,type,name;
private String activityid;
private String [] detail={"","","","","",""};
private ProgressDialog mDialog;
private Button delete;
private static String token="guoguoguo";
private View view;
	@Override
protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		view = this.getLayoutInflater().inflate(R.layout.more_detail, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
		title=(TextView)findViewById(R.id.title);
		content=(TextView)findViewById(R.id.content);
		delete=(Button)findViewById(R.id.delete);
		type=(TextView)findViewById(R.id.type);
		time=(TextView)findViewById(R.id.time);
		name=(TextView)findViewById(R.id.name);
		delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pulllist();
			}
			
		});
		new MyThread().start();
		creatDialog();
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
			String [] str=(String [])msg.obj;
			for(int i=0;i<6;i++)
			{System.out.println(str[i]);}
			 if(mDialog.isShowing()==true){
				  mDialog.dismiss(); 
			  }
			  if(str[0].toString().equals("anyType{}")==true){
//			     	System.out.println(str[0]);
				  title.setText(str[1]);
				  time.setText(str[2]);
				  type.setText(str[3]);
				  content.setText(str[4]);
				  name.setText(str[5]);
//			        Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
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
			System.out.println(activityid);
			String method="activitydetail";
			String serviceurl="http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/","GetActivityInfo");
			request.addProperty("activity_id", activityid.toString());
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
	        	detail[i]=contactlist1.getProperty(i).toString();
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
			Message message = handler.obtainMessage(1,2,3,detail);
			handler.sendMessage(message);
		}
	}
	public void pulllist(){
		Bundle bundle =getIntent().getExtras();
		String activityid=bundle.getString("idmain");
		String id=bundle.getString("typemain");
		String method="delete";
		String serviceurl="http://10.1.151.26/ydzw.asmx";
		SoapObject request = new SoapObject("http://tempuri.org/","DeleteActivity");
		request.addProperty("activity_id", activityid.toString());
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
        		intent.setClass(more_detail.this, set.class);
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