package com.aisino.t8.printer;

public class PrinterConstant {
	public static final int SUCCESS = 0x00;//成功
	public static final int ERROR_DATA = 0x01;//数据错误
	public static final int ERROR_EMPTY_PAPER = 0x02;//缺纸
	public static final int ERROR_HIGH_TEM = 0x03;//高温
	public static final int ERRROR_BUSY= 0x04;//打印机忙
	public static final int ERRROR_OTHER= 0x05;//其他错误
	
	public static final String STATUS_COVER_OPEN = "开盖";
	public static final String STATUS_ONLINE = "状态正常";
	public static final String STATUS_EMPTY_PAPER = "缺纸";
	public static final String STATUS_OFFLINE = "掉线";
	public static final String STATUS_OTHER_ERROR = "未连接";
}
