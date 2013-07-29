package com.moreinfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bitxty.R;
import com.sqlite.getdatafromSQ;

public class login extends Activity {
	private EditText name;
	private EditText pass;
	private CheckBox isRemenber;
	private CheckBox isLoginSelf;
	private Button longin;
	private Button register;
	private ProgressDialog mDialog;
	private String [] logresult={"",""};
	private View view;
	private getdatafromSQ data=null;
	private List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // ×¢ÒâË³Ðò
		view = this.getLayoutInflater().inflate(R.layout.more_log, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // ×¢ÒâË³Ðò
                R.layout.title);
        name=(EditText)findViewById(R.id.log_username);
        pass=(EditText)findViewById(R.id.log_password);
        longin=(Button)findViewById(R.id.log_in);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent (login.this,register.class);
				overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
				startActivity(intent);
			}
		});
        longin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nameValue = name.getText().toString();
				String passValue = pass.getText().toString();
					new MyThread().start();
			}
		});
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
			for(int i=0;i<2;i++)
			{System.out.println(str[i]);}
			  if(str[0].toString().equals("anyType{}")==true){
			     	System.out.println(str[0]);
			        Toast.makeText(getApplicationContext(),"µÇÂ¼³É¹¦",Toast.LENGTH_SHORT).show();
			        data =  ((getdatafromSQ)getApplicationContext());  
					data.setalog(1,str[1].toString());
					Log.v("id", str[1].toString());
			    	finish();
			    }
			    else{
			    	Toast.makeText(getApplicationContext(),str[0],Toast.LENGTH_SHORT).show();
			    }
		}
	}
	
	class MyThread extends Thread{
		public void run(){
			String strname=name.getText().toString();
			String strpassword=pass.getText().toString();
			String method="log";
			String serviceurl="http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/","log"); 
			request.addProperty("user_name", strname.toString()); //sessionID
			request.addProperty("user_pwd",strpassword.toString()); 
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	        envelope.bodyOut=request;
	        envelope.dotNet=true;
	        HttpTransportSE http = new HttpTransportSE(serviceurl);
	    	(new MarshalBase64()).register(envelope);
	        try {
	        	http.call(null,envelope);
	            SoapObject sb = (SoapObject)envelope.bodyIn;
	            SoapObject contactlist1 = (SoapObject)((SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0)).getProperty(0);
	            for(int i=0;i<contactlist1.getPropertyCount();i++){
	            	System.out.println(contactlist1.getProperty(i).toString());
	            	System.out.println((contactlist1.getProperty(i)).toString());
	            	logresult[i]=(contactlist1.getProperty(i)).toString();
	            	System.out.println(logresult[i]);
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
			Message message = handler.obtainMessage(1,2,3,logresult);
			handler.sendMessage(message);
		}
	}
}
  