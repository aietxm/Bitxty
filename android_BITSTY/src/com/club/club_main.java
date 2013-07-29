package com.club;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;  
import java.util.Date;  
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
import com.club.DynamicListView.DynamicListViewListener;
import com.sqlite.getdatafromSQ;
//import com.school.club.MainActivity.MyThread;

import android.annotation.SuppressLint;  
import android.app.Activity;  
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;  
import android.os.Handler;  
import android.os.Looper;
import android.os.Message;  
import android.util.Log;  
import android.view.MotionEvent;
import android.view.View;  
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;  
import android.widget.AdapterView.OnItemClickListener;  
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;  
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;   
@SuppressLint("HandlerLeak")  
public class club_main extends Activity implements DynamicListViewListener {  
    DynamicListView listView; 
    private static final String[] m={"全部","个人发起","社团招新","社团大赛","社团晚会","其他"};
    private String activity=null;
    private TextView view ;
    private String type="全部";
    private Spinner spinner; 
    private String flag="1";
    private String activityresult;
	private ImageView write,set;
	private String error;
	private View vie;
    private HashMap<String, Object> map; 
    private String [] strs={"date","title","type","nickname"};
    private ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
    SimpleAdapter adapter; 
    private ArrayAdapter<String> adapter1;
    private ArrayList<HashMap<String,Object>> info=new ArrayList<HashMap<String,Object>>();
    private getdatafromSQ data=null;
	private List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
    // 用于刷新控件状态  
    Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == 0) {  
                adapter.notifyDataSetChanged();  
                listView.doneRefresh();  
                Toast.makeText(club_main.this, "新加载"+msg.arg1+"条数据!", Toast.LENGTH_LONG).show();  
            } else if (msg.what == 1) {  
                adapter.notifyDataSetChanged();  
                listView.doneMore();  
            } else {  
                super.handleMessage(msg);  
            }  
        }  
    }; 
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState); 
        getWindow().setBackgroundDrawableResource(R.color.white);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); // 注意顺序
		vie= this.getLayoutInflater().inflate(R.layout.club_main, null);
		setContentView(vie);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,      // 注意顺序
                R.layout.title);
        write=(ImageView)findViewById(R.id.write);
	    view = (TextView) findViewById(R.id.spinnerText);  
        spinner = (Spinner) findViewById(R.id.Spinner01); 
        listView=(DynamicListView)findViewById(R.id.newsshowlist);
    	adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);  
        
        //设置下拉列表的风格  
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
          
        //将adapter 1添加到spinner中  
        spinner.setAdapter(adapter1);  
          
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
          
        //设置默认值  
        spinner.setVisibility(View.VISIBLE); 
    	write.setOnClickListener(new OnClickListener(){
  			@Override
  			public void onClick(View arg0) {
  				// TODO Auto-generated method stub
  				data =  ((getdatafromSQ)getApplicationContext());  
  				data.updatalog();
  				list = data.getlog();
  				Map<String , Object> map = new HashMap<String,Object>();
  				Intent intent =new Intent();
  				map=list.get(0);
  				int islog = (Integer) map.get("islog");
  				if(islog==1){
  				
  				intent.putExtra("user_id", (String)map.get("user_id"));
  				intent.setClass(club_main.this, club_publish.class);
  				startActivity(intent);
  				}
  				else if(islog==0){
  					Toast.makeText(club_main.this, "请登录后发布", 0).show();
  					intent.setClass(club_main.this, com.moreinfo.login.class);
  					startActivity(intent);
  				}
  			}
  	    }); 
        adapter = new SimpleAdapter(this, listItem, R.layout.activityinfo_item2, strs, new int[]{R.id.date,R.id.title,R.id.type,R.id.nickname});
        listView.setAdapter(adapter);  
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String item =arg0.getItemAtPosition(arg2).toString();
				System.out.println(item);
				String [] str =item.split(",");
				String [] str2=str[3].split("=");
				Intent intent=new Intent();
				intent.putExtra("activity_id", str2[1].toString());
				System.out.println(str2[1].toString());
				intent.setClass(club_main.this, club_detail.class);
				startActivity(intent);
				
			}  
        });  
        listView.setDoMoreWhenBottom(false);    // 滚动到低端的时候不自己加载更多  
        listView.setOnRefreshListener(this);  
        listView.setOnMoreListener(this);
    }  
	public void pulllist(){
		String method="showlist";
		String serviceurl="http://10.1.151.26/ydzw.asmx";
		SoapObject request = new SoapObject("http://tempuri.org/","GetActivityList");
		if(flag.equals("0")==true){
			request.addProperty("activity_id", activity.toString());	
		}
		if(flag.equals("1")==true)
		{request.addProperty("activity_id", "");} 
		request.addProperty("flag", flag.toString()); //sessionID
		if(type.equals("全部")!=true)
		{request.addProperty("activity_tag", type.toString());}
		else
		{request.addProperty("activity_tag", "");}
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.bodyOut=request;
        envelope.dotNet=true;
        HttpTransportSE http = new HttpTransportSE(serviceurl);
    	(new MarshalBase64()).register(envelope);
        try {
        	http.call(null,envelope);
            SoapObject sb = (SoapObject)envelope.bodyIn;
            SoapObject newlist= (SoapObject)((SoapObject)sb.getProperty(0)).getProperty(0);
//            System.out.println((SoapObject)sb.getProperty(0));
            String error=(((SoapObject)newlist.getProperty(0)).getProperty(0)).toString();
//            System.out.println("2:"+(SoapObject)newlist.getProperty(0));
//            System.out.println("3:"+((SoapObject)newlist.getProperty(0)).getProperty(0));
//            System.out.println("4:"+newlist.getPropertyCount());
            if(error.equals("anyType{}")==true){
            	HashMap<String, Object> map = null;
            		for(int i=1;i<newlist.getPropertyCount();i++){
                  	  SoapObject newlist2=(SoapObject)newlist.getProperty(i);
//                  	  System.out.println(newlist2.getPropertyCount());
                  	   map = new HashMap<String, Object>();
                  	 for(int j=0;j<newlist2.getPropertyCount();j++){
                  		 if(j==0){
                  			 if(i==newlist.getPropertyCount()-1){
                  				activity=(newlist2.getProperty(j)).toString();
                  				System.out.println(activity);
                  			 }
                  			 map.put("activity_id", newlist2.getProperty(j).toString());
                  			System.out.println("1:"+newlist2.getProperty(j).toString());
                  		 }
                  		 else {
	                  			if(j==1){
//	                  			System.out.println("1:"+newlist2.getPropertyCount());
	                  			 map.put("title", (newlist2.getProperty(j)).toString());
	                  		 }
	                  		 else if(j==2){
	                  			 map.put("date", (newlist2.getProperty(j)).toString());
	                  		 }
	                  		 else if(j==3){
	                  			map.put("type", "["+(newlist2.getProperty(j)).toString()+"]"); 
	                  		 }
	                  		 else if(j==4){
//	                  			 System.out.println((newlist2.getProperty(j)).toString());
	                  			map.put("nickname", (newlist2.getProperty(j)).toString()); 
	                  		 }
//	                          System.out.println(map);
                  		 }
                  	 }
                  	 listItem.add(map);
                    } 		
            }
//            System.out.println(activity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    class SpinnerSelectedListener implements OnItemSelectedListener{  
  	  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
        	type=m[arg2].toString();
            System.out.println(type);
            onRefreshOrMore(listView,true);
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    } 
    @Override  
    public boolean onRefreshOrMore(DynamicListView dynamicListView, boolean isRefresh) {  
        if (isRefresh) { 
        	listItem .clear();
            new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    // 刷新
                	flag="1"; 
                    pulllist();   
                    Message message = new Message();  
                    message.what = 0;  
                    message.arg1 = listItem.size();  
                    handler.sendMessage(message);
                }  
            }).start();  
        } else {  
            new Thread(new Runnable() {  
                @Override  
                public void run() {
                	flag="0"; 
                    pulllist(); 
                    // 加载更多  
                    Message message = new Message();  
                    message.what = 1;  
                    message.arg1 = listItem.size();  
                    handler.sendMessage(message);
                }  
            }).start();  
        }  
        return false;
    } 
} 