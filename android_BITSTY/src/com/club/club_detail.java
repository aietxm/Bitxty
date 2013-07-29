package com.club;

import java.io.IOException;
import java.util.HashMap;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import android.os.Bundle;
import com.bitxty.R;

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

public class club_detail extends Activity{
private TextView title,content,time,type,name;
private String activityid;
private String [] detail={"","","","","",""};
private ProgressDialog mDialog;
private View view;
	@Override
protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		view= this.getLayoutInflater().inflate(R.layout.club_detail, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
		title=(TextView)findViewById(R.id.title);
		content=(TextView)findViewById(R.id.content);
		type=(TextView)findViewById(R.id.type);
		time=(TextView)findViewById(R.id.time);
		name=(TextView)findViewById(R.id.name);
//		Bundle bundle =getIntent().getExtras();
//		activityid =bundle.getString("activity_id");
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
			String activityid=bundle.getString("activity_id");
			System.out.println(activityid);
//			String strtitle=title.getText().toString();
//			String strcontent=content.getText().toString();
			String method="activitydetail";
			String serviceurl="http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/","GetActivityInfo"); 
			request.addProperty("activity_id", activityid.toString()); 
//			request.addProperty("activity_name", strtitle.toString()); //sessionID
//			request.addProperty("activity_content",strcontent.toString()); 
			request.addProperty("activity_tag", type.toString());
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	        envelope.bodyOut=request;
	        envelope.dotNet=true;
	        HttpTransportSE http = new HttpTransportSE(serviceurl);
	    	(new MarshalBase64()).register(envelope);
	        try {
	        	http.call(null,envelope);
	            SoapObject sb = (SoapObject)envelope.bodyIn;
	            SoapObject contactlist1 = (SoapObject)((SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0)).getProperty(0);
//	        	System.out.println(contactlist1);
	            for(int i=0;i<contactlist1.getPropertyCount();i++){
	        	detail[i]=contactlist1.getProperty(i).toString();
//	            System.out.println(contactlist1.getProperty(i).toString());
	            System.out.println("2:"+detail[i]);
//	            	System.out.println((contactlist1.getProperty(i)).toString());
//	            	activityresult[i]=(contactlist1.getProperty(i)).toString();
//	            	System.out.println(activityresult[i]);
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
} 
