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

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.bitxty.R;
import com.sqlite.getdatafromSQ;

public class UserInfo extends Activity {
	private TextView nickname, sex, mail, major, intro;
	private ProgressDialog mDialog;
	private getdatafromSQ data = null;
	private String userid;
	private String[] userinfo;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Map<String, Object> expand = new HashMap<String,Object>();
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 getWindow().setBackgroundDrawableResource(R.color.white);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
			view = this.getLayoutInflater().inflate(R.layout.userinfo, null);
			setContentView(view);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
	                R.layout.title);
		nickname = (TextView) findViewById(R.id.nickname1);
		sex = (TextView) findViewById(R.id.sex1);
		mail = (TextView) findViewById(R.id.mail1);
		major = (TextView) findViewById(R.id.major1);
		intro = (TextView) findViewById(R.id.info1);
		data = ((getdatafromSQ) getApplicationContext());
		data.updatalog();
		list = data.getlog();
		Map<String, Object> map = new HashMap<String, Object>();
		map = list.get(0);
		int islog = (Integer) map.get("islog");
		if (islog == 1) {
			userid = (String) map.get("user_id");
		}
		new MyThread().start();
		creatDialog();
	}

	private void creatDialog() {
		// TODO Auto-generated method stub
		mDialog = new ProgressDialog(this);
		mDialog.setTitle("");
		mDialog.setMessage("正在加载中。。。");
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
			
			if (expand.get("state").toString().equals("anyType{}") == true) {
				nickname.setText(expand.get("nickname").toString());
				if(expand.get("sex").toString().equals("anyType{}") == true){
				sex.setText("");
				}else{
					sex.setText(expand.get("sex").toString());
				}
				
				mail.setText(expand.get("mail").toString());
				major.setText(expand.get("major").toString());
				if(expand.get("intro").toString().equals("anyType{}") == true){
					intro.setText("");
					}else{
						intro.setText(expand.get("intro").toString());
					}

			} else {
				Toast.makeText(getApplicationContext(), expand.get("state").toString(),
						Toast.LENGTH_SHORT).show();
			}
			}
		}
	}

	class MyThread extends Thread {
		public void run() {
			String method = "suerinfo";
			String serviceurl = "http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/",
					"GetUserInfo");
			request.addProperty("user_id", userid.toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);
			envelope.bodyOut = request;
			envelope.dotNet = true;
			HttpTransportSE http = new HttpTransportSE(serviceurl);
			(new MarshalBase64()).register(envelope);
			try {
				http.call(null, envelope);
				SoapObject sb = (SoapObject) envelope.bodyIn;
				SoapObject contactlist1 =(SoapObject)((SoapObject) ((SoapObject)sb.getProperty(0)).getProperty(0)).getProperty(0);
				System.out.println(contactlist1);
				System.out.println(contactlist1.getPropertyCount());
				expand.put("state",  contactlist1.getProperty(0).toString());
				expand.put("nickname", contactlist1.getProperty(1).toString());
				expand.put("sex",  contactlist1.getProperty(2).toString());
				expand.put("mail", contactlist1.getProperty(3).toString());
				expand.put("major",contactlist1.getProperty(4).toString());
				expand.put("intro",contactlist1.getProperty(5).toString());
				expand.put("pic",contactlist1.getProperty(6).toString());
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
