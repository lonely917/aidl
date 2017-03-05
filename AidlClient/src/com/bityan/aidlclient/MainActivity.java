package com.bityan.aidlclient;

import com.bityan.aidltest.ActInterface;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ActInterface actInterface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent("com.bityan.aidltest");
		bindService(intent, sc, Service.BIND_AUTO_CREATE);
	}
	
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
	
	public void onClickCall(View v)
	{
		String s = "empty";
		try {
			s = actInterface.saySomething("call");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}
}
