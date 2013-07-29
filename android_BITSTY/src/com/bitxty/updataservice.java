package com.bitxty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pulllist.getintnetstate;
import com.schoolnews.updatalist;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.sqlite.getdatafromSQ;
import com.schoolnews.updatalist;;
public class updataservice extends Service{

	 CommandReceiver cmdReceiver;
     boolean flag;
     private getdatafromSQ SQdata= null;
     updatalist updatalist = null;
 	private getdatafromSQ data=null;
     @Override
     public void onCreate() {//重写onCreate方法
             flag = true;
             cmdReceiver = new CommandReceiver();
             super.onCreate();
             updatalist = new updatalist(this);
             data =  ((getdatafromSQ)getApplicationContext());  
     }
     @Override
     public IBinder onBind(Intent intent) {//重写onBind方法
             // TODO Auto-generated method stub
             return null;
     }
     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {//重写onStartCommand方法
             IntentFilter filter = new IntentFilter();//创建IntentFilter对象
             filter.addAction("com.pulllist.updataservice");
             registerReceiver(cmdReceiver, filter);//注册Broadcast Receiver
             doJob();//调用方法启动线程
             return super.onStartCommand(intent, flags, startId);
     }
     //方法：
     public void doJob(){
             new Thread(){
                     public void run(){
                             while(flag){
                            	 update();
                                 try{//睡眠一段时间
                                         Thread.sleep(20000);
                                 }
                                 catch(Exception e){
                                         e.printStackTrace();
                                 }
                             }                                
                     }
                     
             }.start();
     }        
     private class CommandReceiver extends BroadcastReceiver{//继承自BroadcastReceiver的子类
             @Override
             public void onReceive(Context context, Intent intent) {//重写onReceive方法
                     int cmd = intent.getIntExtra("cmd", -1);//获取Extra信息
                     if(cmd == 1){//如果发来的消息是停止服务                                
                             flag = false;//停止线程
                             stopSelf();//停止服务
                     }if(cmd==2){
                    	 
                     }
             }                
     }
     @Override
     public void onDestroy() {//重写onDestroy方法
             this.unregisterReceiver(cmdReceiver);//取消注册的CommandReceiver
             super.onDestroy();
     }        
     public void update()
     { 
		getintnetstate net = new getintnetstate();

		if (net.detect(this)) {
			updatalist.updatenews();
		}

	}

}
