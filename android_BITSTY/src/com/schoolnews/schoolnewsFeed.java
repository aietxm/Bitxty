package com.schoolnews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class schoolnewsFeed {
	private String title;
	private String pubdate;
	private int itemcount;
	public List<schoolnewsItem> itemlist;
	private int end=-1;
	
	public void setend(int t) {
		end = t;
	}
	public int getend() {
		return end;
	}
	
	public schoolnewsFeed() {
		itemlist = new Vector(0);
	}
	
	public int addItem(schoolnewsItem item) {
		itemlist.add(item);
		itemcount++;
		
		return itemcount;
	}
	
	public schoolnewsItem getItem(int location) {
		return itemlist.get(location);
	}
	
	public List getAllItem() {
		return itemlist;
	}
	
	public List getAllItemsForListView() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = itemlist.size();
		for(int i=0; i<size; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("title", itemlist.get(i).getName());
			item.put("time", itemlist.get(i).gettime());
			item.put("text", itemlist.get(i).gettext());
			data.add(item);
		}
		
		return data;
	}
	public List getsomeItemsForListView(int start,int end) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = itemlist.size();
		for(int i=start; i<end; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("title", itemlist.get(i).getName());
			item.put("time", itemlist.get(i).gettime());
			item.put("text", itemlist.get(i).gettext());
			data.add(item);
		}
		
		return data;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public int getItemcount() {
		return itemcount;
	}

	public void setItemcount(int itemcount) {
		this.itemcount = itemcount;    
	}

	public List<schoolnewsItem> getItemlist() {
		return itemlist;
	}
	public int getItemListSize()
	{
		return itemlist.size();
	}

	public void setItemlist(List<schoolnewsItem> itemlist) {
		this.itemlist = itemlist;
	}	
}
