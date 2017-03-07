package cc.rengu.service;

import java.util.List;

import com.aisino.t8.printer.AddBarCodeCommand;
import com.aisino.t8.printer.AddPictureCommand;
import com.aisino.t8.printer.AddQrCodeCommand;
import com.aisino.t8.printer.AddTextCommand;
import com.aisino.t8.printer.CommandRecorder;
import com.aisino.t8.printer.IPrinterCommand;
import com.aisino.t8.printer.PaperSkipCommand;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import cc.rengu.android.driver.device.AidlDeviceInfo;
import cc.rengu.android.driver.device.AidlPrinter;
import cc.rengu.android.driver.device.AidlPrinterListener;
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
	
	//打印机
	private final AidlPrinter.Stub binderForPrinter = new AidlPrinter.Stub() {
		
		@Override
		public void startPrinter(AidlPrinterListener listener)throws RemoteException {
			
			//开启线程进行打印工作..待完善..结果和提示补充
			
			int code = CommandRecorder.exec(getApplicationContext());
			switch (code) {
			case 0:
				listener.onFinish();
				break;
			case 1:
				listener.onError(1, "");
				break;
			default:
				break;
			}
		}
		
		@Override
		public void paperSkip(int line) throws RemoteException {
			CommandRecorder.addCommand(new PaperSkipCommand(line));
		}
		
		@Override
		public String getStatus() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void addText(Bundle format, String text) throws RemoteException {
			CommandRecorder.addCommand(new AddTextCommand(format, text));
		}
		
		@Override
		public void addQrCode(Bundle format, String qrCode) throws RemoteException {
			CommandRecorder.addCommand(new AddQrCodeCommand(format, qrCode));
		}
		
		@Override
		public void addPicture(Bundle format, Bitmap bitmap) throws RemoteException {
			CommandRecorder.addCommand(new AddPictureCommand(format, bitmap));
		}
		
		@Override
		public void addBarCode(Bundle format, String barCode)throws RemoteException {
			CommandRecorder.addCommand(new AddBarCodeCommand(format, barCode));
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
