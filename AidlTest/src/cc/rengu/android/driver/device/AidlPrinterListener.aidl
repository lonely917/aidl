package cc.rengu.android.driver.device;
interface AidlPrinterListener{
	void onError(in int code, in String detail);//打印出错
	void onFinish();//打印完成
}