package com.aisino.t8.printer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;

import com.zqprintersdk.PrinterConst.Alignment;
import com.zqprintersdk.PrinterConst.ErrorCode;
import com.zqprintersdk.PrinterConst.HeightSize;
import com.zqprintersdk.PrinterConst.PrinterStatus;
import com.zqprintersdk.PrinterConst.WidthSize;
import com.zqprintersdk.ZQPrinterSDK;

/**
 * 记录打印命令
 * 
 * @author yan
 * 
 */
public class Printer {
	public static ZQPrinterSDK prn = null;
	public static List<IPrinterCommand> commands = new ArrayList<IPrinterCommand>();
	
	/**
	 * 向队列中添加打印任务
	 * @param c
	 */
	public static void addCommand(IPrinterCommand c, Context context) {
		if (prn == null) {
			prn = new ZQPrinterSDK();
		}
		prn.Prn_Connect("USB0", context);// 连接打印机
		commands.add(c);
	}

	/**
	 * 执行打印任务队列中的所有任务
	 * @param context
	 * @return
	 */
	public static Bundle exec(Context context) {
		int ret = PrinterConstant.SUCCESS;
//		prn.Prn_PrinterInit();//初始化，清空暂存的格式参数
//		prn.Prn_BeginTransaction();// 开始执行批处理任务
		for (IPrinterCommand command : commands) {
			ret = command.exec(prn);// 依次传递指令
		}
//		ret = prn.Prn_EndTransaction();// 结束批处理，此句触发批处理操作
//		prn.Prn_Disconnect();// 断开打印机
		commands.clear();//清空任务
		
		Bundle bundle = new Bundle();
		return bundle;
	}
	
	/**
	 * 清空任务队列
	 */
	public static void clsCommands(){
		commands.clear();
	}
	
	/**
	 * 获取打印机状态
	 * @param context
	 * @return
	 */
	public static String getStatus(Context context){
		String stateString = "VOID";
		if (prn == null) {
			prn = new ZQPrinterSDK();
		}
		prn.Prn_Connect("USB0", context);// 连接打印机
		prn.Prn_PrinterInit();//初始化，清空暂存的格式参数
		int ret = prn.Prn_Status();//获取状态
//		prn.Prn_Disconnect();// 断开打印机
		
		switch (ret) {
		case PrinterStatus.COVEROPEN:
			stateString = PrinterConstant.STATUS_COVER_OPEN;
			break;
		case PrinterStatus.EMPTYPAPER:
			stateString = PrinterConstant.STATUS_EMPTY_PAPER;
			break;
		case PrinterStatus.OFFLINE:
			stateString = PrinterConstant.STATUS_OFFLINE;
			break;
		case PrinterStatus.ONLINE:
			stateString = PrinterConstant.STATUS_ONLINE;
			break;
		case PrinterStatus.OTHERERROR:
			stateString = PrinterConstant.STATUS_OTHER_ERROR;
			break;
		default:
			break;
		}
		return stateString;
	}

	/**
	 * 根据本地数值返回对应bundle，包含错误代码和错误信息
	 * @param code
	 * @return
	 */
	public static Bundle getBundle(int code){
		Bundle bundle = new Bundle();
		switch (code) {
		case ErrorCode.SUCCESS:
			bundle.putInt("code", PrinterConstant.SUCCESS);
			bundle.putString("string", "success");
			break;
		case ErrorCode.PORTERROR://连接错误
			bundle.putInt("code", PrinterConstant.ERRROR_OTHER);
			bundle.putString("string", "连接错误");
			break;
		case ErrorCode.WRITEERROR://发送数据失败
			bundle.putInt("code", PrinterConstant.ERRROR_OTHER);
			bundle.putString("string", "发送数据失败");
			break;
		case ErrorCode.INVALIDPARAM:
			bundle.putInt("code", PrinterConstant.ERRROR_OTHER);
			bundle.putString("string", "参数错误");
			break;
		default:
			break;
		}
		return bundle;
	}
	
	/**
	 * 根据字符返回对齐格式
	 * @param s
	 * @return
	 */
	public static int getAlign(String s){
		int align = Alignment.LEFT;//默认左对齐
		if(s.equalsIgnoreCase("left")){
		}else if(s.equalsIgnoreCase("right")){
			align = Alignment.RIGHT;
		}else if(s.equalsIgnoreCase("center")){
			align = Alignment.CENTER;
		}
		return align;
	}
	
	public static int getFontSize(String s){
		int fontSize = WidthSize.SIZE0|HeightSize.SIZE0; //默认小号，本地对应size0，基础数值。
		if(s.equalsIgnoreCase("small")){
		}else if(s.equalsIgnoreCase("normal")){
			fontSize = WidthSize.SIZE1|HeightSize.SIZE1;
		}else if(s.equalsIgnoreCase("large")){
			fontSize = WidthSize.SIZE2|HeightSize.SIZE2;
		}
		return fontSize;
	}
	
	public static void execCommand(AddPictureCommand cmd){
		if(prn == null)
		{
			prn = new ZQPrinterSDK();
		}
		prn.Prn_Connect("USB0", cmd.context);
		cmd.exec(prn);
	}
}
