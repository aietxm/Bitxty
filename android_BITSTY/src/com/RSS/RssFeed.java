package com.RSS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class RssFeed {
	private String title;
	private String pubdate;
	private int itemcount;
	private List<RssItem> itemlist;
	
	public RssFeed() {
		itemlist = new Vector(0);
	}
	
	public int addItem(RssItem item) {
		itemlist.add(item);
		itemcount++;
		
		return itemcount;
	}
	
	public RssItem getItem(int location) {
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
			item.put(RssItem.TITLE, itemlist.get(i).getTitle());
			item.put(RssItem.PUBDATE, itemlist.get(i).getPubDate());
			item.put("text", itemlist.get(i).getDescription());
			data.add(item);
		}
		
		return data;
	}
	public List getsomeItemsForListView(int start,int end) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = itemlist.size();
		for(int i=start; i<end; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(RssItem.TITLE, itemlist.get(i).getTitle());
			item.put(RssItem.PUBDATE, itemlist.get(i).getDescription());
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

	public List<RssItem> getItemlist() {
		return itemlist;
	}
	public int getItemListSize()
	{
		return itemlist.size();
	}

	public void setItemlist(List<RssItem> itemlist) {
		this.itemlist = itemlist;
	}	
}
