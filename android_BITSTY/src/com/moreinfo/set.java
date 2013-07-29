package com.moreinfo;
import java.io.IOException;
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

//import com.school.club.MainActivity;
//import com.school.club.MainActivity;
import com.bitxty.R;
import com.club.club_main;
import com.moreinfo.DynamicListView.DynamicListViewListener;
//import com.school.club.MainActivity.SpinnerSelectedListener;
import com.sqlite.getdatafromSQ;
  
import android.annotation.SuppressLint;  
import android.app.Activity;  
import android.content.Intent;
import android.os.Bundle;  
import android.os.Handler;  
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;  
  
@SuppressLint("HandlerLeak")  
public class set extends Activity implements DynamicListViewListener {  
    DynamicListView listView;
    private static final String[] m={"全部","失物招领","社团活动"};
    private static final String token="guoguoguo";
    private TextView view ;
    private View view2;
    private String typemain;
    private String idmain;
    private String listflag;
    private ImageView send;
    private String flag="0";
    private String type="全部";
    private Spinner spinner;
    private getdatafromSQ data=null;
    private HashMap<String, Object> map = new HashMap<String, Object>(); 
    private String [] strs={"date","title","tag"};
    private ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
    SimpleAdapter adapter;
    private String userid;
    private ArrayAdapter<String> adapter1;
    private List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
    // 用于刷新控件状态  
    Handler handler = new Handler() {  
        @Override  
        public void handleMessage(Message msg) {  
            if (msg.what == 1) {  
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
		view2 = this.getLayoutInflater().inflate(R.layout.set, null);
		setContentView(view2);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, // 注意顺序
				R.layout.title);
        view = (TextView) findViewById(R.id.spinnerText);  
        spinner = (Spinner) findViewById(R.id.Spinner01);
        send =(ImageView)findViewById(R.id.send);
        listView=(DynamicListView)findViewById(R.id.newsshowlist);
        data =  ((getdatafromSQ)getApplicationContext());  
		data.updatalog();
		list = data.getlog();
		Map<String , Object> map = new HashMap<String,Object>();
		map=list.get(0);
		int islog = (Integer) map.get("islog");
		if(islog==1)
		{userid=(String)map.get("user_id");}
		System.out.println(userid); 
		send.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("papa:"+userid);
				if(userid.equals("")==true){
					Toast.makeText(set.this, "请登录后管理", 0).show();
					Intent intent =new Intent();
  					intent.setClass(set.this, com.moreinfo.login.class);
  					startActivity(intent);
				}
				else{
					flag="0";
					pulllist();
					adapter.notifyDataSetChanged();  
	                listView.doneRefresh(); 
				}
			}
			
		});
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);  
        
        //设置下拉列表的风格  
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
          
        //将adapter 1添加到spinner中  
        spinner.setAdapter(adapter1);  
          
        //添加事件Spinner事件监听    
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());  
          
        //设置默认值  
        spinner.setVisibility(View.VISIBLE);
        adapter = new SimpleAdapter(this, listItem, R.layout.management_item, strs, new int[]{R.id.date,R.id.title,R.id.tag});
        listView.setAdapter(adapter);  
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String item =arg0.getItemAtPosition(arg2).toString();
				System.out.println(item);
				String [] str =item.split(",");
				String [] str3=str[0].split("=");
				String [] str2=str[2].split("=");
				idmain=str3[1];
				typemain=str2[1];
					if(userid.equals("")!=true){
						if(typemain.equals("0")==true){
							Intent intent =new Intent();
							intent.putExtra("idmain", idmain.toString());
							intent.putExtra("typemain", typemain.toString());
							intent.setClass(set.this, more_detail.class);
							startActivity(intent);
						}
						else{
							Intent intent =new Intent();
							intent.putExtra("idmain", idmain.toString());
							intent.putExtra("typemain", typemain.toString());
							intent.setClass(set.this, Expand.class);
							startActivity(intent);
						}
				}
					else{
						Toast.makeText(set.this, "请登录后管理", 0).show();
						Intent intent =new Intent();
	  					intent.setClass(set.this, com.moreinfo.login.class);
	  					startActivity(intent);
					}
			}  
        });  
        listView.setDoMoreWhenBottom(false);    // 滚动到低端的时候不自己加载更多  
        listView.setOnRefreshListener(this);  
        listView.setOnMoreListener(this);  
    }  
    class SpinnerSelectedListener implements OnItemSelectedListener{  
    	  
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
        	type=m[arg2].toString();
            System.out.println(type);
            flag="0";
            pulllist();
            adapter.notifyDataSetChanged();  
            listView.doneRefresh(); 
//            onRefreshOrMore(listView,true);
        }  
  
        public void onNothingSelected(AdapterView<?> arg0) {  
        }  
    }
    public void pulllist(){
		String method="GetList";
		String methods="GetList";
		if(flag.equals("0")==true){
			listItem .clear();
		}
		String serviceurl="http://10.1.151.26/ydzw.asmx";
		SoapObject request = new SoapObject("http://tempuri.org/",methods);
		System.out.println(userid);
		if(flag.equals("0")==true){
			request.addProperty("user_id", userid.toString());
			if(type.equals("全部")==true){
				request.addProperty("type","");
			}
			else if(type.equals("社团活动")==true){
				request.addProperty("type","0");
			}
			else if(type.equals("失物招领")==true){
				request.addProperty("type","1");
			}
			request.addProperty("listid","");
		}
		if(flag.equals("2")==true){
			request.addProperty("user_id", userid.toString());
			if(type.equals("全部")==true){
				request.addProperty("type","");
			}
			else if(type.equals("社团活动")==true){
				request.addProperty("type","0");
			}
			else if(type.equals("失物招领")==true){
				request.addProperty("type","1");
			}
			request.addProperty("listid",listflag.toString());
		}
//		if(flag.equals("1")==true){
//			if(typemain.equals("0")){
//				request.addProperty("token",token.toString());
//				request.addProperty("activity_id",idmain.toString());
//			}
//			else{
//				request.addProperty("token",token.toString());
//				request.addProperty("lost_id",idmain.toString());
//			}
//		}
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.bodyOut=request;
        envelope.dotNet=true;
        HttpTransportSE http = new HttpTransportSE(serviceurl);
    	(new MarshalBase64()).register(envelope);
        try {
        	http.call(null,envelope);
            SoapObject sb = (SoapObject)envelope.bodyIn;
            SoapObject newlist= (SoapObject)((SoapObject)sb.getProperty(0)).getProperty(0);
            System.out.println((SoapObject)sb.getProperty(0));
            System.out.println(newlist);
            String error=(((SoapObject)newlist.getProperty(0)).getProperty(0)).toString();
            System.out.println("2:"+(SoapObject)newlist.getProperty(0));
            System.out.println("3:"+error);
            System.out.println("4:"+newlist.getPropertyCount());
            if(error.equals("anyType{}")==true){
            	for(int i=1;i<newlist.getPropertyCount();i++){
            		SoapObject newlist2=(SoapObject)newlist.getProperty(i);
            		HashMap<String, Object> map = new HashMap<String, Object>();
            		for(int j=0;j<newlist2.getPropertyCount();j++){
            			switch(j){
            			    case 0:{map.put("id",(newlist2.getProperty(j)).toString());break;}
	            			case 1:{map.put("title",(newlist2.getProperty(j)).toString());break;}
	            			case 2:{map.put("date",(newlist2.getProperty(j)).toString());break;}
	            			case 3:{map.put("type", (newlist2.getProperty(j)).toString());break;}
	            			case 4:{map.put("list_id", (newlist2.getProperty(j)).toString());break;}
	            			case 5:{
	            				String flag=(newlist2.getProperty(j)).toString();
	            				if(flag.equals("-1")==true){
	            					map.put("tag", "["+(newlist2.getProperty(6)).toString()+"]");
//	            					System.out.println((newlist2.getProperty(6)).toString());
	            				}
	            				else if(flag.equals("0")==true){
	            					map.put("tag", "[失物]");
	            				}
	            				else{
	            					map.put("tag", "[招领]");
	            				}
	            				break;
	            			}
            			}
            			if(i==newlist.getPropertyCount()-1){
            				listflag=newlist2.getProperty(4).toString();
            			}
            		}
            		System.out.println(map);
            		listItem.add(map);
//            		System.out.println(listItem);
            	}
            }
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    @Override  
    public boolean onRefreshOrMore(DynamicListView dynamicListView, boolean isRefresh) {  
        if (isRefresh==false) { 
            new Thread(new Runnable() {  
                @Override  
                public void run() {  
                    // 加载更多 
                	flag="2";
                	pulllist();   
                	Message message = new Message();  
                    message.what = 1;  
                    message.arg1 = listItem.size();  
                    handler.sendMessage(message);
                }  
            }).start();  
        } 
        return false;  
    } 
    public void deletelist(){
    	String method="delete";
    	String methods;
	    if(typemain.equals("0")){
			methods="DeleteActivity";
		}
		else{
			methods="DeleteLost";
		}
    	String serviceurl="http://10.1.151.26/ydzw.asmx";
		SoapObject request = new SoapObject("http://tempuri.org/",methods);
		if(typemain.equals("0")){
			request.addProperty("token",token.toString());
			request.addProperty("activity_id",idmain.toString());
		}
		else{
			request.addProperty("token",token.toString());
			request.addProperty("lost_id",idmain.toString());
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.bodyOut=request;
        envelope.dotNet=true;
        HttpTransportSE http = new HttpTransportSE(serviceurl);
    	(new MarshalBase64()).register(envelope);
        try {
        	http.call(null,envelope);
            SoapObject sb = (SoapObject)envelope.bodyIn;
            SoapObject newlist= (SoapObject)((SoapObject)sb.getProperty(0)).getProperty(0);
            System.out.println((SoapObject)sb.getProperty(0));
            System.out.println(newlist);
            String error=(((SoapObject)newlist.getProperty(0)).getProperty(0)).toString();
            System.out.println(error);
//            if(error.equals("anyType{}")==true){
//            	Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
//            	flag="0";
//                pulllist();
//                adapter.notifyDataSetChanged();  
//                listView.doneRefresh(); 
//            }
//            else{
//            	Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
//            }
//            System.out.println("2:"+(SoapObject)newlist.getProperty(0));
//            System.out.println("3:"+error);
//            System.out.println("4:"+newlist.getPropertyCount());
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
} 