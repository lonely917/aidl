package cc.rengu.android.driver.device;
import cc.rengu.android.driver.device.AidlPrinterListener;
interface AidlPrinter{
	void addText(in Bundle format, in String text);//添加一行打印文本
	void paperSkip(in int line);//走纸
	void addPicture(in Bundle format,in Bitmap bitmap);//添加打印图片
	void addBarCode(in Bundle format,in String barCode);//添加打印条码
	void addQrCode(in Bundle format, in String qrCode);//添加打印二维码
	void startPrinter(in AidlPrinterListener listener);//开始打印
	String getStatus();//获取打印机状态
}