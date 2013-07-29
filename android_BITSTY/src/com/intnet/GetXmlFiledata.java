package com.intnet;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.util.ByteArrayBuffer;

import com.bitxty.*;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class GetXmlFiledata
{
	/** Called when the activity is first created. */
	URL url ;
	public GetXmlFiledata(String str)
	{
		try {
			url = new URL(str);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getdata();
	}
	public ArrayList getdata()
	{	
		String myString = null;
		try
		{
			URLConnection ucon = url.openConnection();
			
			/* 从上面的链接中取得InputStream */
			InputStream is = ucon.getInputStream();
			
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(100);
			int current = 0;
			/* 一直读到文件结束 */
			while ((current = bis.read()) != -1)
			{
				baf.append((byte) current);
			}
			myString = new String(baf.toByteArray());
		}
		catch (Exception e)
		{

			myString = e.getMessage();
		}

        
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<100;i++,i++)
        {
        	HashMap<String, Object> map = new HashMap<String, Object>();
        	map.put("String", R.drawable.icon);//图像资源的ID
        	map.put("String", "123");
        	listItem.add(map);
        }
		return listItem;
	}
}
