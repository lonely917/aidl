package com.bityan.aidlclient;

import cc.rengu.android.driver.device.AidlDeviceInfo;
import cc.rengu.android.driver.device.Beeper;
import cc.rengu.android.driver.device.Led;
import cc.rengu.android.driver.service.engine.DeviceServiceEngine;

import com.bityan.aidltest.ActInterface;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String tag = "ywb";
	private ActInterface actInterface;
	private DeviceServiceEngine deviceServiceEngine;
	private ServiceConnection sc = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			actInterface = ActInterface.Stub.asInterface(service);
		}
	};

	private ServiceConnection sc_engine = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			deviceServiceEngine = DeviceServiceEngine.Stub.asInterface(service);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//第一个服务
		Intent intent = new Intent("com.bityan.aidltest");
		bindService(intent, sc, Service.BIND_AUTO_CREATE);//绑定服务
		
		//DeviceServiceEngine对应的服务
		Intent engineIntent = new Intent();
		engineIntent.setAction("cc.rengu.DeviceService");
		bindService(engineIntent, sc_engine, Service.BIND_AUTO_CREATE);
		
	}
	
	@Override
	protected void onDestroy() {
		unbindService(sc);//解除绑定，否则提示leaked service connection
		unbindService(sc_engine);
		super.onDestroy();
	}
	
	public void onClickCall(View v)
	{
		Log.i(tag, "client call");
		String s = "empty";
		try {
			s = actInterface.saySomething("call");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(tag, "client call result->"+s);
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}
	
	public void onClickCallBeeper(View v)
	{
		Log.i(tag, "client call beeper");
		Beeper beeper = null;
		try {
//			beeper = (Beeper) deviceServiceEngine.getBeeper();//这样转化会提示错误
			IBinder binder = deviceServiceEngine.getBeeper();
			beeper = Beeper.Stub.asInterface(binder);
			beeper.beep();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(beeper!=null)
		{
			try {
				beeper.beep();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void onClickCallLed(View v)
	{
		Log.i(tag, "client call led");
		Led led = null;
		try {
			led = Led.Stub.asInterface(deviceServiceEngine.getLed());//根据LED对应Binder获取LED对象
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(led!=null){
			try {
				led.setLed(true, 0);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void onClickCallDeviceInfo(View v)
	{
		Log.i(tag, "client call deviceInfo");
		String s = "empty";
		try {
			AidlDeviceInfo binder = AidlDeviceInfo.Stub.asInterface(deviceServiceEngine.getDeviceInfo());
			s = "version:"+binder.getVersion()+"sr:"+binder.getSN()+"vendor:"+binder.getVName();
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(tag, e.toString());
		}
		Log.i(tag, "call result:"+s);
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}
}
