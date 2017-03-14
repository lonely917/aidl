package com.bityan.aidlclient;

import java.io.IOException;
import java.io.InputStream;

import cc.rengu.android.driver.device.AidlDeviceInfo;
import cc.rengu.android.driver.device.AidlPrinter;
import cc.rengu.android.driver.device.AidlPrinterListener;
import cc.rengu.android.driver.device.AidlSerialComm;
import cc.rengu.android.driver.device.Beeper;
import cc.rengu.android.driver.device.Led;
import cc.rengu.android.driver.service.engine.DeviceServiceEngine;

import com.bityan.aidltest.ActInterface;
import com.zqprintersdk.ZQPrinterSDK;
import com.zqprintersdk.PrinterConst.BitmapSize;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	ZQPrinterSDK prn;
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
		prn = new ZQPrinterSDK();
		prn.SDK_GetVersion();
		
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
	
	public void onClickTestPrinter(View v)
	{
		Log.i(tag, "client call printer");
		try {
			AidlPrinter printer = AidlPrinter.Stub.asInterface(deviceServiceEngine.getPrinter());
			
			//测试打印行为
			//获取打印机状态
			String status = printer.getStatus();
			Log.i(tag, "printer status:"+status);
			
			//添加打印任务,条码打印
			Bundle b = new Bundle();
			b.putString("align", "left");
			printer.addBarCode(b, "1234ajak");
			
//			添加文字打印任务
			printer.addText(b, "123444444444444444444\r\n");
			printer.addText(b, "短发散发的萨芬第三方司法所地方\r\n");
			
			//添加图片打印任务
			try {
				InputStream is;
				is = getAssets().open("logo.png");
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, size);
				b.putString("align", "right");
				printer.addPicture(b, bm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//添加走纸任务
			printer.paperSkip(5);
			
			//开始打印
			printer.startPrinter(new AidlPrinterListener.Stub() {
				
				@Override
				public void onFinish() throws RemoteException {
					Log.i(tag, "onFinish");
				}
				
				@Override
				public void onError(int code, String detail) throws RemoteException {
					Log.i(tag, "onError");
				}
			});
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onClickPrintBar(View v)
	{
		Log.i(tag, "client call printer");
		try {
			AidlPrinter printer = AidlPrinter.Stub.asInterface(deviceServiceEngine.getPrinter());

			
			//添加打印任务,条码打印
			Bundle b = new Bundle();
			b.putString("align", "left");
			printer.addBarCode(b, "1234ajak");
			
			//添加走纸任务
			printer.paperSkip(5);
			
			//开始打印
			printer.startPrinter(new AidlPrinterListener.Stub() {
				
				@Override
				public void onFinish() throws RemoteException {
					Log.i(tag, "onFinish");
				}
				
				@Override
				public void onError(int code, String detail) throws RemoteException {
					Log.i(tag, "onError");
				}
			});
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void onClickPrintText(View v)
	{
		Log.i(tag, "client call printer");
		try {
			AidlPrinter printer = AidlPrinter.Stub.asInterface(deviceServiceEngine.getPrinter());
			
			Bundle b = new Bundle();
			b.putString("align", "left");
			
			//添加文字打印任务
			printer.addText(b, "123444444444444444444\r\n");
			printer.addText(b, "短发散发的萨芬第三方司法所地方\r\n");
			
			//添加走纸任务
			printer.paperSkip(5);
			
			//开始打印
			printer.startPrinter(new AidlPrinterListener.Stub() {
				
				@Override
				public void onFinish() throws RemoteException {
					Log.i(tag, "onFinish");
				}
				
				@Override
				public void onError(int code, String detail) throws RemoteException {
					Log.i(tag, "onError");
				}
			});
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onClickPrintPic(View v)
	{
//		printPic();
		Log.i(tag, "client call printer");
		try {
			AidlPrinter printer = AidlPrinter.Stub.asInterface(deviceServiceEngine.getPrinter());
			
			//添加图片打印任务
			Bundle b = new Bundle();
			b.putString("align", "right");

			try {
				InputStream is;
				is = getAssets().open("logo.png");
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, size);
				printer.addPicture(b, bm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//添加走纸任务
			printer.paperSkip(5);
			
			//开始打印
			printer.startPrinter(new AidlPrinterListener.Stub() {
				
				@Override
				public void onFinish() throws RemoteException {
					Log.i(tag, "onFinish");
				}
				
				@Override
				public void onError(int code, String detail) throws RemoteException {
					Log.i(tag, "onError");
				}
			});
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onClickPrintQr(View v)
	{
		Log.i(tag, "client call printer");
		try {
			AidlPrinter printer = AidlPrinter.Stub.asInterface(deviceServiceEngine.getPrinter());
			
			//添加打印任务,二维码打印
			Bundle b = new Bundle();
			b.putString("align", "right");
			printer.addQrCode(b, "1234334晏文博");
			
			//开始打印
			printer.startPrinter(new AidlPrinterListener.Stub() {
				
				@Override
				public void onFinish() throws RemoteException {
					Log.i(tag, "onFinish");
				}
				
				@Override
				public void onError(int code, String detail) throws RemoteException {
					Log.i(tag, "onError");
				}
			});
			
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void onClickOpenSerial(View v){
		Log.i(tag,"onClickOpenSerial");
		try {
			AidlSerialComm serial = AidlSerialComm.Stub.asInterface(deviceServiceEngine.getSerialComm());
			int ret = serial.open();
			Log.i(tag, "serial open :"+ret);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void onClickCloseSerial(View v){
		Log.i(tag,"onClickCloseSerial");
		try {
			AidlSerialComm serial = AidlSerialComm.Stub.asInterface(deviceServiceEngine.getSerialComm());
			int ret = serial.close();
			Log.i(tag, "serial close :"+ret);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void onClickSerialSend(View v){
		Log.i(tag,"onClickSerialSend");
		try {
			AidlSerialComm serial = AidlSerialComm.Stub.asInterface(deviceServiceEngine.getSerialComm());
			byte[] content = "aaaaa".getBytes();
			int ret = serial.write(content, 20, 0);
			Log.i(tag, "serial send :"+ret);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void printPic() {
//		prn = new ZQPrinterSDK();
		prn.Prn_Connect("USB0", this);// 连接打印机
		prn.SetCharacterSet("gb2312");
		try {
			InputStream is = getAssets().open("logo.png");
			int size = is.available();
			//Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, size);
			prn.Prn_PrintString("        \r\n");
			int ret = prn.Prn_PrintBitmap(bm, BitmapSize.ZQSIZE0);//使用正常大小..实际是0.5倍
			prn.Prn_LineFeed(5);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
}
