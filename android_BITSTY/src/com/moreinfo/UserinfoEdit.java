package com.moreinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.bitxty.R;
import com.moreinfo.UserInfo.MyHandler;
import com.moreinfo.UserInfo.MyThread;
import com.moreinfo.UserInfo.mylistener;
import com.sqlite.getdatafromSQ;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class UserinfoEdit extends Activity{

	private View view;
	private EditText nickname,intro,major,mail;
	private Button submit;
	private RadioGroup sex;
	private getdatafromSQ data;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private String userid,sex_v;
	private ProgressDialog mDialog;
	private static String state;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	 getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // ×¢ÒâË³Ðò
		view = this.getLayoutInflater().inflate(R.layout.userinfo_edit, null);
		setContentView(view);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // ×¢ÒâË³Ðò
             R.layout.title);
	nickname = (EditText) findViewById(R.id.nickname1);
	sex = (RadioGroup) findViewById(R.id.sex1);
	mail = (EditText) findViewById(R.id.mail1);
	major = (EditText) findViewById(R.id.major1);
	intro = (EditText) findViewById(R.id.info1);
	submit = (Button)findViewById(R.id.edit_submit);
	sex.setOnCheckedChangeListener(new sexListener());
	submit.setOnClickListener(new mylistener());
	data = ((getdatafromSQ) getApplicationContext());
	data.updatalog();
	list = data.getlog();
	Map<String, Object> map = new HashMap<String, Object>();
	map = list.get(0);
	int islog = (Integer) map.get("islog");
	if (islog == 1) {
		userid = (String) map.get("user_id");
	}
	
}

private void creatDialog() {
	// TODO Auto-generated method stub
	mDialog = new ProgressDialog(this);
	mDialog.setTitle("");
	mDialog.setMessage("ÕýÔÚ¼ÓÔØÖÐ¡£¡£¡£");
	mDialog.setIndeterminate(true);
	mDialog.setCancelable(true);
	mDialog.show();

}

class MyHandler extends Handler {

	public MyHandler(Looper looper) {
		super(looper);
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		int  str = (Integer) msg.obj;
		if(str ==1 ){
		if (mDialog.isShowing() == true) {
			mDialog.dismiss();
		}
		if(state.equals("anyType{}")){
			Intent intent  = new Intent(UserinfoEdit.this,UserInfo.class);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
			finish();
			
		}else{
			Toast.makeText(UserinfoEdit.this, "ÐÞ¸ÄÊ§°Ü", 0).show();		
		}
		
		
		}
	}
}

class MyThread extends Thread {
	public void run() {
		String method = "suerinfo";
		String serviceurl = "http://10.1.151.26/ydzw.asmx";
		SoapObject request = new SoapObject("http://tempuri.org/",
				"UpdateUserInfo");
		request.addProperty("user_id", userid.toString());
		request.addProperty("user_nickname",nickname.getText().toString());
		request.addProperty("user_sex",sex_v);
		request.addProperty("user_mail",mail.getText().toString());
		request.addProperty("user_major",major.getText().toString());
		request.addProperty("user_intro",intro.getText().toString());
		request.addProperty("user_pic", "");
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		envelope.bodyOut = request;
		envelope.dotNet = true;
		HttpTransportSE http = new HttpTransportSE(serviceurl);
		(new MarshalBase64()).register(envelope);
		try {
			http.call(null, envelope);
			SoapObject sb = (SoapObject) envelope.bodyIn;
			state = ((SoapObject) ((SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0)).getProperty(0)).getPropertyAsString(0).toString();
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

class mylistener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(nickname.getText().toString().equals("")){
			Toast.makeText(UserinfoEdit.this, "ÇëÌîÐ´êÇ³Æ£¡", 0).show();
			
		}else{
		new MyThread().start();
		creatDialog();
	}
	
}
}

class sexListener implements OnCheckedChangeListener{

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		System.out.println(checkedId);
		if(checkedId == 2131427510){
			sex_v= "ÄÐ";
			
		}else if(checkedId == 2131427511){
			sex_v="Å®";
		}
	}
	
}
	

}
