package com.pulllist;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
//import android.net.sip.SipSession.State;

public class getintnetstate {
	
	 public static boolean detect(Context act) {  
	        
	       ConnectivityManager manager = (ConnectivityManager) act  
	              .getApplicationContext().getSystemService(  
	                     Context.CONNECTIVITY_SERVICE);  
	        
	       if (manager == null) {  
	           return false;  
	       }  
	        
	       NetworkInfo networkinfo = manager.getActiveNetworkInfo();  
	        
	       if (networkinfo == null || !networkinfo.isAvailable()) {  
	           return false;  
	       }  
	   
	       return true;  
	    }

}
