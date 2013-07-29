package com.club;


import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.bitxty.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class club_publish extends Activity{
	   private static final String[] m={"个人发起","社团招新","社团大赛","社团晚会","其他"};  
	    private TextView view ;  
	    private Spinner spinner;  
	    private ArrayAdapter<String> adapter;  
	    private String type;
	    private EditText title,content;
	    private Button send;
	    private String user_id;
	    private String [] activityresult={"",""};
	    private View vie;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		vie= this.getLayoutInflater().inflate(R.layout.club_publish, null);
		setContentView(vie);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
	     view = (TextView) findViewById(R.id.spinnerText);  
	        spinner = (Spinner) findViewById(R.id.Spinner01); 
	        title=(EditText)findViewById(R.id.title);
	        content=(EditText)findViewById(R.id.content);
	        send=(Button)findViewById(R.id.send);
	        //将可选内容与ArrayAdapter连接起来  
	        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);  
	          
	        //设置下拉列表的风格  
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	          
	        //将adapter 添加到spinner中  
	        spinner.setAdapter(adapter);  
	          
	        //添加事件Spinner事件监听    
	        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
	          
	        //设置默认值  
	        spinner.setVisibility(View.VISIBLE); 
	        send.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					System.out.println(title.getText().toString());
					System.out.println(content.getText().toString());
					if(title.getText().toString().equals("")==true)
						{Toast.makeText(getApplicationContext(),"活动题目不能为空！",Toast.LENGTH_SHORT).show();}
					else{
						if(content.getText().toString().equals("")==true)
						{Toast.makeText(getApplicationContext(),"活动内容不能为空！",Toast.LENGTH_SHORT).show();}
						else{
							new MyThread().start();
						}
					}
				}
	        	
	        });
	    }  
	      
	    //使用数组形式操作  
	    class SpinnerSelectedListener implements OnItemSelectedListener{  
	  
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
	                long arg3) {  
	        	type=m[arg2].toString();
	            System.out.println(type);
	        }  
	  
	        public void onNothingSelected(AdapterView<?> arg0) {  
	        }  
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
				        Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_SHORT).show();
				    	 Intent intent =new Intent();
				    	 intent.setClass(club_publish.this, club_main.class);
				    	 startActivity(intent);
				    	 finish();
				    }
				    else{
				    	Toast.makeText(getApplicationContext(),str[0],Toast.LENGTH_SHORT).show();
				    }
			}
		}
		
		class MyThread extends Thread{
			public void run(){
				Bundle bundle =getIntent().getExtras();
				String userid=bundle.getString("user_id");
				System.out.println(userid);
				String strtitle=title.getText().toString();
				String strcontent=content.getText().toString();
				String method="InsertActivity";
				String serviceurl="http://10.1.151.26/ydzw.asmx";
				SoapObject request = new SoapObject("http://tempuri.org/","InsertActivity"); 
				request.addProperty("user_id", userid.toString()); 
				request.addProperty("activity_name", strtitle.toString()); //sessionID
				request.addProperty("activity_content",strcontent.toString()); 
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
		        	System.out.println(contactlist1);
		            for(int i=0;i<contactlist1.getPropertyCount();i++){
		            	System.out.println(contactlist1.getProperty(i).toString());
		            	System.out.println((contactlist1.getProperty(i)).toString());
		            	activityresult[i]=(contactlist1.getProperty(i)).toString();
		            	System.out.println(activityresult[i]);
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
				Message message = handler.obtainMessage(1,2,3,activityresult);
				handler.sendMessage(message);
			}
		}
	}

