package cc.rengu.android.driver.device;
interface AidlDeviceInfo{
	String getSN();//获得设备序列号
	String getCSN();//获得客户定义的设备序列号 CSN
	String getVID();//获得厂商 ID
	String getVName();//获得厂商 Name
	String getKSN();//获得客户定义的密钥序列号 KSN
	boolean isSupportIcCard();//是否支持 IC 卡
	boolean isSupportMagCard();//是否支持磁条卡
	boolean isSupportRFCard();//是否支持非接卡
	boolean isSupportPrint();//是否支持打印
	boolean isSupportOffLine();//是否支持脱机打印
	boolean isSupportBeep();//是否支持蜂鸣器
	boolean isSupportLed();//是否支持LED
	String getVersion();//获取系统版本号
}