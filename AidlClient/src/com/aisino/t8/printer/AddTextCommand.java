package com.aisino.t8.printer;

import android.os.Bundle;

import com.zqprintersdk.PrinterConst.Font;
import com.zqprintersdk.ZQPrinterSDK;

/**
 * 打印一行数据，bundle包含参数，content为数据内容
 * 
 * @author yan
 * 
 */
public class AddTextCommand implements IPrinterCommand {

	public Bundle bundle;
	public String content;

	public AddTextCommand(Bundle b, String s) {
		bundle = b;
		content = s;
	}

	@Override
	public int exec(ZQPrinterSDK prn) {

		// 获取参数
		String alignString = bundle.getString("align", "left");
		String fontString = bundle.getString("font", "small");

		// 参数转化
		int alignment = Printer.getAlign(alignString);
		int fontSize = Printer.getFontSize(fontString);

		//中文支持
		prn.SetCharacterSet("gb2312");
		
		// 打印
		int ret = prn.Prn_PrintText(content, alignment, Font.DEFAULT, fontSize);
		return ret;
	}

}
