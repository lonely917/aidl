package com.aisino.t8.printer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.zqprintersdk.ZQPrinterSDK;

/**
 * 记录打印命令
 * 
 * @author yan
 * 
 */
public class CommandRecorder {

	public static ZQPrinterSDK prn = null;
	public static List<IPrinterCommand> commands = new ArrayList<IPrinterCommand>();

	public static void init() {
		if (prn == null) {
			prn = new ZQPrinterSDK();
		}
	}

	public static void addCommand(IPrinterCommand c) {
		commands.add(c);
	}

	public static int exec(Context context) {
		init();
		prn.Prn_Connect("USB0", context);// 连接打印机
		prn.Prn_BeginTransaction();// 开始执行批处理任务
		for (IPrinterCommand command : commands) {
			command.exec(prn);// 依次传递指令
		}
		prn.Prn_EndTransaction();// 结束批处理，此句触发批处理操作
		prn.Prn_Disconnect();// 断开打印机
		commands.clear();//清空任务
		return 0;
	}
	
	public static void clsCommands(){
		commands.clear();
	}
	
	public static String getStatus(Context context){
		init();
		prn.Prn_Connect("USB0", context);// 连接打印机
		int ret = prn.Prn_Status();//获取状态
		prn.Prn_Disconnect();// 断开打印机
		return ret+"";
	}

}
