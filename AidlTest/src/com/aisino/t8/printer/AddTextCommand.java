package com.aisino.t8.printer;

import com.zqprintersdk.PrinterConst.Alignment;
import com.zqprintersdk.PrinterConst.Font;
import com.zqprintersdk.PrinterConst.HeightSize;
import com.zqprintersdk.PrinterConst.WidthSize;
import com.zqprintersdk.ZQPrinterSDK;

import android.os.Bundle;

/**
 * 打印一行数据，bundle包含参数，content为数据内容
 * @author yan
 *
 */
public class AddTextCommand implements IPrinterCommand {
	
	public Bundle bundle;
	public String content;
	public AddTextCommand(Bundle b, String s) {
		bundle = b;
		content =s;
	}
	@Override
	public int exec(ZQPrinterSDK prn) {
		int ret = prn.Prn_PrintText(content, Alignment.LEFT, Font.DEFAULT, WidthSize.SIZE0|HeightSize.SIZE0);
		return ret;
	}


}
