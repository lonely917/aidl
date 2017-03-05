package com.bityan.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

	private final String tag = "ywb";
	
	private final ActInterface.Stub binder = new ActInterface.Stub() {
		
		@Override
		public String saySomething(String s) throws RemoteException {
			Log.i(tag, s);
			return getPackageName()+s;
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	@Override
	public void onCreate() {
		Log.i(tag, "service create!");
		super.onCreate();
	}

}
