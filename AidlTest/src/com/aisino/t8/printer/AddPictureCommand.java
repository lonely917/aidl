package com.aisino.t8.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.zqprintersdk.PrinterConst;
import com.zqprintersdk.ZQPrinterSDK;

/**
 * 打印bitmap，bundle包含参数，content为数据内容
 * @author yan
 *
 */
public class AddPictureCommand implements IPrinterCommand {
	
	public Bundle bundle;
	public Bitmap content;
	public Context context;
	
	public AddPictureCommand(Bundle b, Bitmap bitmap, Context context) {
		bundle = b;
		content =bitmap;
		this.context = context;
	}
	@Override
	public int exec(ZQPrinterSDK prn) {
		
		//获取参数
		int height = bundle.getInt("height", 1);
		int width = bundle.getInt("width", 1);
		int offset = bundle.getInt("offset", 0);
		
		//预留字段 //zq打印机并不支持宽度和高度设定，没有偏移设定，但是有对齐设置
		String alignString = bundle.getString("align", "left");
		
		int alignment = Printer.getAlign(alignString);
		prn.Prn_SetAlignment(alignment);
		return prn.Prn_PrintBitmap(content, PrinterConst.BitmapSize.ZQSIZE3);//0标准大小，实际是0.5倍，3标识倍宽倍高，实际原大小
	}


}
