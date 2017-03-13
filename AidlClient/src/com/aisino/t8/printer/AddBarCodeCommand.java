package com.aisino.t8.printer;

import android.os.Bundle;

import com.zqprintersdk.PrinterConst.Barcode;
import com.zqprintersdk.PrinterConst.BarcodeText;
import com.zqprintersdk.PrinterConst;
import com.zqprintersdk.ZQPrinterSDK;

/**
 * 打印条形码指令
 * 
 * @author yan
 * 
 */
public class AddBarCodeCommand implements IPrinterCommand {

	private Bundle bundle;
	private String content;

	public AddBarCodeCommand(Bundle b, String s) {
		bundle = b;
		content = s;
	}

	@Override
	public int exec(ZQPrinterSDK prn) {
		
		//获取参数
		String alignString = bundle.getString("align", "left");
		int height = bundle.getInt("height", 64);
		int width = bundle.getInt("width", 2);
		
		//本地参数限制
		if (height > 255) {
			height = 255;
		} else if (height < 1) {
			height = 1;
		}
		
		if(width > 7){
			width = 7;
		}else if(width < 1){
			width = 1;
		}
		
		//设置对齐并打印，使用纯条码无字符格式
		int alignment = Printer.getAlign(alignString);
		prn.Prn_SetAlignment(alignment);
		return prn.Prn_PrintBarcode(content, PrinterConst.Barcode.CODE128, height,
				width, PrinterConst.BarcodeText.TEXT_NONE);
	}

}
