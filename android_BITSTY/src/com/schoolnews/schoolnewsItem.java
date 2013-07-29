package com.schoolnews;


public class schoolnewsItem {
	private String id ="";
	private String name ="";
	private String href ="";
	private String time ="";
	private String text ="";
	private String img  ="";
	private String attch ="";
	private String title ="";
	public String getid() {
		return id;
	}
	public void setid(String str) {
		if(str!=null)
		this.id = str;
	}
	public String getName() {
		return name;
	}
	public void setname(String str) {
		if(str!=null)
		this.name = str;
	}
	public String gethref() {
		return href;
	}
	public void sethref(String str) {
		if(str!=null)
		this.href = str;
	}
	public String gettime() {
		return time;
	}
	public void settime(String str) {
		if(str!=null)
		this.time = str;
	}
	public String gettext() {
		return text;
	}
	public void settext(String str) {
		if(str!=null)
		this.text = str;
		
	}
	public String getattch() {
		return attch;
	}
	public void setattch(String str) {
		if(str!=null)
		this.attch = str;
		
	}
	public String getimg() {
		return img;
	}
	public void setimg(String str) {
		if(str!=null)
		this.img = str;
		
	}
	public String gettitle() {
		return title;
	}
	public void settitle(String str) {
		if(str!=null)
		this.title = title;
		
	}
	
	public String toString() {
		if(text.length() > 20) {
			return text.substring(0, 42) + "...";
		}else {
			return text;
		}
	}
	
}
