package com.moreinfo;

import android.app.Activity;

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
	import android.view.View;
	import android.view.Window;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.EditText;
	import android.widget.RadioGroup;
	import android.widget.RadioGroup.OnCheckedChangeListener;
	import android.widget.TextView;
	import android.widget.Toast;

	public class UserInfoNewpass extends Activity{

		private View view;
		private EditText old_p,new_p;
		private Button submit;
		private getdatafromSQ data;
		private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		private String userid;
		private ProgressDialog mDialog;
		private static String state;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		 getWindow().setBackgroundDrawableResource(R.color.white);
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // ◊¢“‚À≥–Ú
			view = this.getLayoutInflater().inflate(R.layout.userinfo_newpass, null);
			setContentView(view);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // ◊¢“‚À≥–Ú
	             R.layout.title);
		old_p= (EditText) findViewById(R.id.old_p1);
		new_p = (EditText) findViewById(R.id.new_p1);
		submit = (Button)findViewById(R.id.new_submit);
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
		mDialog.setMessage("’˝‘⁄Ã·Ωª÷–°£°£°£");
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
				Intent intent  = new Intent(UserInfoNewpass.this,UserInfo.class);
				Toast.makeText(UserInfoNewpass.this, "–ﬁ∏ƒ≥…π¶", 0).show();		
				startActivity(intent);
				overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
				finish();
				
			}else{
				Toast.makeText(UserInfoNewpass.this, "–ﬁ∏ƒ ß∞‹", 0).show();		
			}
			
			
			}
		}
	}

	class MyThread extends Thread {
		public void run() {
			String method = "suerinfo";
			String serviceurl = "http://10.1.151.26/ydzw.asmx";
			SoapObject request = new SoapObject("http://tempuri.org/",
					"UpdatePwd");
			request.addProperty("user_id", userid.toString());
			request.addProperty("user_oldpwd",old_p.getText().toString());
			request.addProperty("user_newpwd",new_p.getText().toString());
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
			if(old_p.getText().toString().equals("")){
				Toast.makeText(UserInfoNewpass.this, "«ÎÃÓ–¥æ…√‹¬Î£°", 0).show();
			}else{
				if(new_p.getText().toString().equals("")){
					Toast.makeText(UserInfoNewpass.this, "«ÎÃÓ–¥–¬√‹¬Î£°", 0).show();
				}else{
					new MyThread().start();
					creatDialog();
				}
			}
			
		}
		
	}

		

	}

