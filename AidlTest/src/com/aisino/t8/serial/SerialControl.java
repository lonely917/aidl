package com.aisino.t8.serial;

import java.io.File;
import java.util.Arrays;

public class SerialControl {
	
	public static final String version = "T8_1.0";
	
	private int baudrate = 9600;//波特率
	private int databits = 8;//数据位7，8
	private int stopbits = 1;//停止位1，2
	private int parity = 'N';//奇偶校验N E O S
	private int flag = 0;//控制位标志 包含数据位、校验以及停止位
	private int data2;//保留
	private String path = "dev/ttyS0";//默认设备，串口3
	private byte[] flagByte = null;//控制位
	private SerialPort serial = null;//串口类
	
	//打开连接在串口 3接口上的设备，要求返回设备描述符的int值，底层不支持，成功统一返回0，失败返回0
	public int open(){
		try {
			serial = new SerialPort(new File(path), baudrate, 0);//使用默认参数打开
			return 0;
		} catch (Exception e) {
			return -1;//失败返回-1
		}
	}
	
	//获取 JNI 版本信息
	public String getVersion(){
		return version;
	}
	
	//设置串口参数，暂存,jni底层没有提供串口设置操作，打开使用默认参数，只是使用波特率参数
	public int setconfig(int data1,int data2,byte[] buf){
		baudrate = data1;
		this.data2 = data2;
		flagByte = Arrays.copyOf(buf, buf.length);
		{
			//重新打开串口，jni没有提供单独设置波特率的函数,策略..修改波特率参数，重新打开串口
			if(serial!=null){
				close();
			}
			
			try {
				open();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		return 0;
	}
	
	//操作对应设备
	public int ioctl(int cmd ,byte[] args){
		return -14;//不支持
	}
	
	//读取串口数据 忽略timeoutSec
	public int read(byte[] buf ,int lengthMax, int timeoutSec){
		int len = -1;
		if(serial != null){
			try {
				len = serial.getInputStream().read(buf, 0, lengthMax);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return len;
	}
	
	//向串口中写入数据 忽略timeoutSec 写入的就是buf的全数据，lengthMax无用
	public int write(byte[] buf ,int lengthMax, int timeoutSec){
		int ret = -1;
		if(serial != null){
			try {
				serial.getOutputStream().write(buf, 0, buf.length);
				ret = buf.length;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return ret;	
	}
	
	//关闭连接在串口 3上的设备,要求返回设备描述符的int值，底层不支持，统一返回0
	public int close(){
		if(serial!=null){
			serial.close();
		}
		return 0;
	}
}
