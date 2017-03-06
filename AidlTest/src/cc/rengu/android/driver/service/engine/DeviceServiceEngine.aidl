package cc.rengu.android.driver.service.engine;
interface DeviceServiceEngine{
    IBinder getDeviceInfo();//
	IBinder getPBOC();
	IBinder getPinpad();
	IBinder getPrinter();//
	IBinder getICCard();
	IBinder getRFCard();
	IBinder getScanner();
	IBinder getLed();
	IBinder getBeeper();
	IBinder getTerminalManage();//
	IBinder getSerialComm();//
	
	/*
	Printer getPrinter();
	InnerScanner getInnerScanner();
	SerialPort getSerialPort();
	MagCard getMagCard();
	IccCard getIccCard(in int slotNo);
	CpuCard getCPUCard(IccCard iccCard);
	PinPad getPinPad();
	EMV getEmv();
	NetWork getNetWork();
	*/
}