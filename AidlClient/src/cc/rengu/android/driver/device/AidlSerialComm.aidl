package cc.rengu.android.driver.device;
interface AidlSerialComm{
	int open(); //打开连接在串口3上的设备
	String getVersion();//获取JNI版本信息
	int setconfig(in int data1, in int data2, in byte[]buf);//设置串口参数
	int ioctl(in int cmd, in byte[]args);//操作对应设备
	int read(in byte[]buf, in int lengthMax, in int timeoutSec);//读取串口数据
	int write(in byte[]buf, in int lengthMax, in int timeoutSec);//写数据
	int close();//关闭连接在串口3上的设备
}