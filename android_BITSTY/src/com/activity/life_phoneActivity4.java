package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitxty.R;

public class life_phoneActivity4 extends Activity {
	/** Called when the activity is first created. */
	View view;
	private String name;
	private String num;
	private String phone;
	private TextView nameView;
	private TextView numView;
	private TextView phoneView;
	private LinearLayout call;
	private LinearLayout addcontact;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawableResource(R.drawable.background);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, // 注意顺序
				R.layout.title);
		view = this.getLayoutInflater().inflate(R.layout.contactphone, null);
		setContentView(view);

		Intent intent = getIntent();// 得到上一个文件传入的ID号
		Bundle i = intent.getExtras();
		name = i.getString("name");
		num = i.getString("num");
		phone = i.getString("phone");
		nameView = (TextView) findViewById(R.id.contactname);
		numView = (TextView) findViewById(R.id.contactadress);
		phoneView = (TextView) findViewById(R.id.contactphone);
		nameView.setText(name);
		numView.setText(num);
		phoneView.setText(phone);

		call = (LinearLayout) findViewById(R.id.call);
		addcontact = (LinearLayout) findViewById(R.id.addcontact);

		call.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ phone));
				startActivity(intent);
			}
		});
		addcontact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(Intent.ACTION_INSERT, Uri
						.withAppendedPath(
								Uri.parse("content://com.android.contacts"),
								"contacts"));
				it.setType("vnd.android.cursor.dir/person");;
				it.putExtra(android.provider.ContactsContract.Intents.Insert.NAME,name);
				// 手机号码
				it.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE,phone);

				startActivity(it);

			}
		});
	}
}
