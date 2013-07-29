package com.intnet;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.xmlpull.v1.XmlPullParserException;
import android.app.Activity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 

import com.sqlite.getdatafromSQ;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;  
import java.util.Set; 
import android.app.Activity;  
import android.content.Context;
import android.os.Bundle;  
import android.view.ContextMenu;  
import android.view.MenuItem;  
import android.view.View;  
import android.view.ContextMenu.ContextMenuInfo;  
import android.view.View.OnCreateContextMenuListener;  
import android.widget.AdapterView;  
import android.widget.ListView;  
import android.widget.SimpleAdapter;  
import android.widget.AdapterView.OnItemClickListener;
public class FromWebservice {
  
	private String NAMESPACE = "http://tempuri.org/";

	// WebService地址
	//
	private String URL = "http://ss.bit.edu.cn/websvc/android.asmx";
	private String logresult="0";
	private String token="jidi2013_#sdnaw";
	private Context mcontext;
	private int PT_ID;
	
	private static final String METHOD_NAME1 = "log";
	private static final String METHOD_NAME2 = "GetBaoguo";
	private static final String METHOD_NAME3 = "GetContact";
	private static final String METHOD_NAME4 = "GetNewsList";
	private static final String METHOD_NAME5 = "News";
	private Map<String, Object> list =new HashMap<String, Object>();
	private getdatafromSQ data=null;
	
    public FromWebservice(){
		logresult = "0";	
    };
    
    public FromWebservice(Context context,int t){
    	mcontext=context;
    	PT_ID = t;
		data =  ((getdatafromSQ)context.getApplicationContext());  
		list = data.getPT(PT_ID);
    	URL = (String)list.get("url");
    	NAMESPACE = (String)list.get("namespace");
		token=(String)list.get("token");
    	
    };
	public String log(String Name,String password)
	{	
		try
		{
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true; 	
		SoapObject soapObject = new SoapObject(NAMESPACE, METHOD_NAME1);	 
		soapObject.addProperty("txtNm", Name); //sessionID
		soapObject.addProperty("txtpwd",password); 
		soapObject.addProperty("token", token); 
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		envelope.bodyOut=soapObject; 
		envelope.dotNet=true;

		(new MarshalBase64()).register(envelope);
        try{
            transport.call(null, envelope);
            
            for(int i=0;i<0;i++);
          	//SoapObject result1=(SoapObject)envelope.getResponse();
            SoapObject sb = (SoapObject)envelope.bodyIn;
            logresult = sb.getProperty(0).toString();
            // 获取从服务器端返回的XML字符串、
           
        }
        catch(Exception e)
        {
        	return "网络连接错误";
        	
        }
        String[] result= logresult.split(",");
        logresult=result[0];
        String name=null;
        String num=null;
        String shenf=null;
        name = result[1];
        num = result[2];
        shenf = result[3];
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", PT_ID);
        map.put("userid", logresult);
        map.put("name", name);
        map.put("num", num);
        map.put("shenf", shenf);
        data.setPT_ID(map);
        return logresult;
		}
		catch(Exception e)
        {
        	return "网络连接错误";
        	
        }

	}
	public ArrayList<Map<String, String>> getBaoguo()
	{
		HttpResponse httpResponse;
		try{
		
			 HttpTransportSE transport = new HttpTransportSE(URL);
	     	   transport.debug = true; 
	     	   SoapObject soapObject;
	   		   soapObject = new SoapObject(NAMESPACE, METHOD_NAME2);	
	   		   soapObject.addProperty("userID", data.getPT_ID(PT_ID).get("userid")); //sessionID
	   		   soapObject.addProperty("token", token); //sessionID
	   		   SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	   		   envelope.bodyOut=soapObject; 
	   		   envelope.dotNet=true;

	   		  (new MarshalBase64()).register(envelope);
	           try{
	        	   transport.call(null, envelope);
	           }
	           catch(Exception e){
	        	   return null;
	        	 //  e.printStackTrace();
	           }
	           SoapObject sb = (SoapObject)envelope.bodyIn;
	           SoapObject contactlist1 = (SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0);
	           ArrayList<Map<String, String>> contactlist =new ArrayList<Map<String, String>>();
	           for(int i=0;i<contactlist1.getPropertyCount();i++)
	           {
	        	   Map<String, String> map = new HashMap<String, String>();
	        	   SoapObject newlist2 = (SoapObject)contactlist1.getProperty(i);
	               String name= newlist2.getAttributeAsString("信件类型").toString();
	               String num= newlist2.getAttributeAsString("到达时间").toString();
	               String phone= newlist2.getAttributeAsString("备注").toString();

	               map.put("name", name);
	               map.put("num", num);
	               map.put("phone", phone);
	               contactlist.add(map);
	               
	           }
	           return contactlist;
		}
	    catch (Exception e) {}   
	    return null;
       }; 
       
       public ArrayList<Map<String, String>> getnewslist(int newsid,int flag )
       {
    	   HttpTransportSE transport = new HttpTransportSE(URL);
     	   transport.debug = true; 
     	   SoapObject soapObject;
   		   soapObject = new SoapObject(NAMESPACE, METHOD_NAME4);	
   		   soapObject.addProperty("userID", data.getPT_ID(PT_ID).get("userid")); //sessionID
   		   soapObject.addProperty("newsid", newsid); //sessionID
     	   soapObject.addProperty("flag", flag); 
   		   soapObject.addProperty("token", token); //sessionID
   		   SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
   		   envelope.bodyOut=soapObject; 
   		   envelope.dotNet=true;
   	   	   (new MarshalBase64()).register(envelope);
           try{
        	   transport.call(null, envelope);
           }
           catch(Exception e){
        	   return null;
           }
           SoapObject sb = (SoapObject)envelope.bodyIn;
           SoapObject newlist1 = (SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0);
           
           ArrayList<Map<String, String>> newslist =new ArrayList<Map<String, String>>();
           for(int i=0;i<newlist1.getPropertyCount();i++)
           {
        	   Map<String, String> map = new HashMap<String, String>();
        	   SoapObject newlist2 = (SoapObject)newlist1.getProperty(i);
               String name= newlist2.getAttributeAsString("标题").toString();
               String id= newlist2.getAttributeAsString("新闻ID").toString();
               String istop= newlist2.getAttributeAsString("置顶").toString();
               String type= newlist2.getAttributeAsString("类别").toString();
               String time= newlist2.getAttributeAsString("时间").toString();
           //    String time= newlist2.getAttributeAsString("NI_Title").toString();
               map.put("name", name);
               map.put("id", id);
               map.put("istop", istop);
               map.put("type", type);
               map.put("time", time);
             //  map.put("time", time);
               newslist.add(map);
               
           }
           return newslist;
           
       }
       public String getnews(String newsID )
       {
    	   HttpTransportSE transport = new HttpTransportSE(URL);
     	   transport.debug = true; 
     	   SoapObject soapObject;
   		   soapObject = new SoapObject(NAMESPACE, METHOD_NAME5);
   		   soapObject.addProperty("id", newsID); //sessionID
   		   soapObject.addProperty("token", token); //sessionID
   		   SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
   		   envelope.bodyOut=soapObject; 
   		   envelope.dotNet=true;
   	   	   (new MarshalBase64()).register(envelope);
           try{
        	   transport.call(null, envelope);
           }
           catch(Exception e){
        	   return null;
           }
           SoapObject sb = (SoapObject)envelope.bodyIn;
           String newshtml =  sb.getProperty(0).toString();
           return newshtml;
           
       }
       /**
     * @param newsID
     * @param password
     * @return
     */
    public ArrayList getschoolnews()
       {
    	  HttpTransportSE transport = new HttpTransportSE(URL);
   	      transport.debug = true; 
   	       SoapObject soapObject;
   	       soapObject = new SoapObject(NAMESPACE, METHOD_NAME5);
 		   // soapObject.addProperty("userID", UserID); //sessionID
 		   SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
 		   envelope.bodyOut=soapObject; 
 		   envelope.dotNet=true;
 	   	   (new MarshalBase64()).register(envelope);
         try{
      	   transport.call(null, envelope);
          	//SoapObject result1=(SoapObject)envelope.getResponse();
             
             // 获取从服务器端返回的XML字符串
             
         }
         catch(Exception e){
        	 return null;
         }
         SoapObject sb = (SoapObject)envelope.bodyIn;
//         SoapObject newlist1 = (SoapObject)((SoapObject)((SoapObject)sb.getProperty(0)).getProperty(1)).getProperty(0);
         
         ArrayList newsTab = new ArrayList(); 
         
         
//        or(int i=0;i<sb.getPropertyCount();i++)
//         {
//      	   // SoapObject newlist2 = (SoapObject)sb.getProperty(i);
        	  String str = sb.getProperty(0).toString();
        	  str.replace("url name", "");
        	  str.replace("href", "");
        	  str.replace("<url><", "");
              String[] CI_name = new String[2];   
              String[] strarray1=str.split("="); 
              for (int j = 0; j < strarray1.length; j++,j++) 
              {
            	  CI_name[0] = strarray1[j].substring(1,strarray1[j].length()-1);
                  CI_name[1] = strarray1[j+1].substring(1,strarray1[j].length()-1); 
                  newsTab.add(CI_name[0]);
                 newsTab.add(CI_name[1]);
              }
//                  System.out.println(strarray1[j]); 
//              String[] strarray2=strarray1[1].split(" href="); 
//              for (int j = 0; j < strarray2.length; j++) 
//                  System.out.println(strarray2[j]); 
              //CI_name[0] = newlist2.getProperty("CI_Name").toString();
              //CI_name[1] = newlist2.getProperty("CI_ID").toString();
             
//         }
        
         return newsTab;
       }
    public ArrayList<Map<String, String>> getContact(String Contact )
    {
 	   HttpTransportSE transport = new HttpTransportSE(URL);
  	   transport.debug = true; 
  	   SoapObject soapObject;
		   soapObject = new SoapObject(NAMESPACE, METHOD_NAME3);
		   soapObject.addProperty("userID",data.getPT_ID(PT_ID).get("userid")); //sessionID
		   soapObject.addProperty("str", Contact); //sessionID
		   soapObject.addProperty("token", token); //sessionID
		   SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		   envelope.bodyOut=soapObject; 
		   envelope.dotNet=true;
	   	   (new MarshalBase64()).register(envelope);
        try{
     	   transport.call(null, envelope);
         	//SoapObject result1=(SoapObject)envelope.getResponse();
            
            // 获取从服务器端返回的XML字符串
            
        }
        catch(Exception e){
        	return null;
        }
        SoapObject sb = (SoapObject)envelope.bodyIn;
        SoapObject contactlist1 = (SoapObject)( (SoapObject)sb.getProperty(0)).getProperty(0);
        ArrayList<Map<String, String>> contactlist =new ArrayList<Map<String, String>>();
        for(int i=0;i<contactlist1.getPropertyCount();i++)
        {
     	   Map<String, String> map = new HashMap<String, String>();
     	   SoapObject newlist2 = (SoapObject)contactlist1.getProperty(i);
            String name= newlist2.getAttributeAsString("姓名").toString();
            String num= newlist2.getAttributeAsString("学号").toString();
            String phone= newlist2.getAttributeAsString("手机号").toString();

            map.put("name", name);
            map.put("num", num);
            map.put("phone", phone);
          //  map.put("time", time);
            contactlist.add(map);
            
        }
        return contactlist;
        
    }



}
