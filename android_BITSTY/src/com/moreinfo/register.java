package com.moreinfo;


import java.io.IOException;
import java.net.URL;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.bitxty.R;

public class register extends Activity {
	private Button zhuce;
	private boolean flag;
	private ImageView back;
	private URL url;
	private EditText name;
	private EditText password;
	private EditText major;
	private EditText mail;
	private EditText queren;
	private String registersult="0";
	private View view;
    /** Called when the activity is first created. 
     * @return */
    public void FromWebservice(){
    	registersult = "0";	
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		view = this.getLayoutInflater().inflate(R.layout.more_register, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
        
        //输入非空判定
        name = (EditText)findViewById(R.id.register_username);
        password = (EditText)findViewById(R.id.register_password);
        major = (EditText)findViewById(R.id.register_major);
        mail=(EditText)findViewById(R.id.register_email);
        queren = (EditText)findViewById(R.id.register_queren);
        zhuce = (Button)findViewById(R.id.register_submit01);
        zhuce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flag=true;
				
				if(name.getText().toString().equals("")==true)
					{Toast.makeText(getApplicationContext(),"用户名不能为空！",Toast.LENGTH_SHORT).show(); flag = false;}
				else
				{
				if(password.getText().toString().equals("")==true)
					{Toast.makeText(getApplicationContext(),"密码不能为空！",Toast.LENGTH_SHORT).show(); flag = false;}
				else
				{
					if(queren.getText().toString().equals("")==true||password.getText().toString().equals(queren.getText().toString()) == false)
						{Toast.makeText(getApplicationContext(),"确认密码与密码不相符！",Toast.LENGTH_SHORT).show(); flag = false;}
					else
					{
						if(major.getText().toString().equals("")==true)
							{Toast.makeText(getApplicationContext(),"专业不能为空！",Toast.LENGTH_SHORT).show(); flag = false;}
						else 
							{
							if(password.getText().toString().equals(queren.getText().toString())==false)
								{Toast.makeText(getApplicationContext(),"密码不一致！",Toast.LENGTH_SHORT).show(); flag = false;}
							}
						}
				}
				}
				if(flag== true)
				{
					new MyThread().start();
				}	
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
			String str=(String)msg.obj;
			System.out.println(str);
			  if(str.toString().equals("anyType{}")==true){
			     	System.out.println(str);
			        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
			    	finish();
			    }
			    else{
			    	Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
			    }
		}
	}
	
	class MyThread extends Thread{
		public void run(){
			String strname=name.getText().toString();
			String strpassword=password.getText().toString();
			String strmail=mail.getText().toString();
			String strmajor=major.getText().toString();
			String method="adduser";
			String serviceurl="http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/","regist"); 
			request.addProperty("user_name", strname.toString()); //sessionID
			request.addProperty("user_pwd",strpassword.toString()); 
			request.addProperty("user_mail",strmail.toString()); 
			request.addProperty("user_major",strmajor.toString()); 
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	        envelope.bodyOut=request;
	        envelope.dotNet=true;
	        HttpTransportSE http = new HttpTransportSE(serviceurl);
	    	(new MarshalBase64()).register(envelope);
	        try {
	        	http.call(null,envelope);
	            SoapObject sb = (SoapObject)envelope.bodyIn;
	            registersult = ((SoapObject) ((SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0)).getProperty(0)).getPropertyAsString(0).toString();
	            System.out.println(registersult);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Looper looper = Looper.getMainLooper();
			MyHandler handler = new MyHandler(looper);
			Message message = handler.obtainMessage(1,2,3,registersult);
			handler.sendMessage(message);
		}
	}
  }
