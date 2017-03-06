package cc.rengu.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import cc.rengu.android.driver.device.AidlDeviceInfo;
import cc.rengu.android.driver.device.Beeper;
import cc.rengu.android.driver.device.Led;
import cc.rengu.android.driver.service.engine.DeviceServiceEngine;


public class DeviceService extends Service {

	private final String tag = "ywb";
	
	//设备信息binder
	private final AidlDeviceInfo.Stub  binderForDeviceInfo = new AidlDeviceInfo.Stub() {
		
		@Override
		public boolean isSupportRFCard() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isSupportPrint() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isSupportOffLine() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isSupportMagCard() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isSupportLed() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isSupportIcCard() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isSupportBeep() throws RemoteException {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public String getVersion() throws RemoteException {
			return android.os.Build.VERSION.RELEASE;
		}
		
		@Override
		public String getVName() throws RemoteException {
			return android.os.Build.MANUFACTURER;
		}
		
		@Override
		public String getVID() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getSN() throws RemoteException {
			return android.os.Build.SERIAL;
		}
		
		@Override
		public String getKSN() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getCSN() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	//蜂鸣器 binder
	private final Beeper.Stub binderForBeeper = new Beeper.Stub() {
		
		@Override
		public void beep() throws RemoteException {
			Log.i(tag, "beep");
		}
	};
	
	//Led binder
	private final Led.Stub binderForLed = new Led.Stub() {
		
		@Override
		public void setLed(boolean onOff, int light) throws RemoteException {
			Log.i(tag, "setLed");
		}
	};
	
	private final DeviceServiceEngine.Stub binder = new DeviceServiceEngine.Stub() {

		@Override
		public IBinder getBeeper() throws RemoteException {
			Log.d(tag, "getBeeper");
			return binderForBeeper;
		}

		@Override
		public IBinder getLed() throws RemoteException {
			Log.i(tag, "getLed");
			return binderForLed ;
		}

		@Override
		public IBinder getDeviceInfo() throws RemoteException {
			Log.i(tag, "geDeviceInfo");
			return binderForDeviceInfo;
		}

		@Override
		public IBinder getPBOC() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBinder getPinpad() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBinder getPrinter() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBinder getICCard() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBinder getRFCard() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBinder getScanner() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBinder getTerminalManage() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IBinder getSerialComm() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	@Override
	public void onCreate() {
		Log.i(tag, "dapi service create!");
		super.onCreate();
	}
}
