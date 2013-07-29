package com.pulllist;
 
import android.app.Activity;
import android.content.Context; 
import android.view.MotionEvent;
import android.widget.ImageView; 
import android.widget.LinearLayout; 
import android.widget.TextView; 
import android.content.Intent;
import com.activity.*;
//自定义ImageButton，模拟ImageButton，并在其下方显示文字 
//提供Button的部分接口 
public class ImageMenu extends LinearLayout { 
  public Context mcontext;
  public String type;
  public int ID;
  public ImageMenu(Context context, int imageResId, String table, String flag ,int id) {
    super(context); 
    mcontext = context;
    mButtonImage = new ImageView(context); 
    mButtonText = new TextView(context); 
    ID = id;
    type = flag;
    setImageResource(imageResId); 
    mButtonImage.setPadding(0, 0, 0, 0); 
//    getBackground().setAlpha(20);       //設置透明度
    //invalidate();    
    setText(table); 
    setTextColor(0xFF000000); 
    mButtonText.setPadding(0, 0, 0, 0); 
 
    //设置本布局的属性 
    setClickable(true);  //可点击 
    setFocusable(true);  //可聚焦 
    setBackgroundResource(android.R.drawable.btn_default);  //布局才用普通按钮的背景 
    setOrientation(LinearLayout.VERTICAL);  //垂直布局 
     
    //首先添加Image，然后才添加Text 
    //添加顺序将会影响布局效果 
    addView(mButtonImage); 
    addView(mButtonText); 
    
  } 
 
  // ----------------public method----------------------------- 
   
  
  public void setImageResource(int resId) { 
    mButtonImage.setImageResource(resId); 
  } 
 
   
  public void setText(String resId) { 
    mButtonText.setText(resId); 
  } 
 
  public void setText(CharSequence buttonText) { 
    mButtonText.setText(buttonText); 
  } 
//  @Override
//  public boolean dispatchTouchEvent(MotionEvent ev) {
//	  super.dispatchTouchEvent(ev);
//  }
  @Override
  public boolean onTouchEvent(MotionEvent ev){
//	    Intent tent = new Intent(mcontext,NewListActivity.class);
//		tent.putExtra("Email", "20091946@bit.edu.cn");
//		tent.putExtra("password", "123456");
//		//tent.putExtra("pop3", newsID);
//     	((Activity) mcontext).startActivityForResult(tent, 1);
	  if(type.equals("school"))
	  {
		//GetXmlFiledata data = new GetXmlFiledata("http://ss.bit.edu.cn/OutNews/校园网通知公告.xml");
		Intent tent = new Intent();
		tent.setClass(mcontext,com.activity.school_newslistActivity.class);
		tent.putExtra("type", ID);
		((Activity) mcontext).startActivityForResult(tent, 0);
	  }
	  if(type.equals("RSS"))
	  {
		  
		Intent tent = new Intent();
		tent.setClass(mcontext,com.activity.rss_ActivityMain.class);
		tent.putExtra("type", ID);
		((Activity) mcontext).startActivityForResult(tent, 0);
	  }
	  return false;
  }
  
  public void setTextColor(int color) { 
    mButtonText.setTextColor(color); 
  } 
 
  // ----------------private attribute----------------------------- 
  private ImageView mButtonImage = null; 
  private TextView mButtonText = null; 
} 