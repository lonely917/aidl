package com.aisino.t8.printer;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.zqprintersdk.PrinterConst.BitmapSize;
import com.zqprintersdk.ZQPrinterSDK;

/**
 * 打印bitmap，bundle包含参数，content为数据内容
 * @author yan
 *
 */
public class AddPictureCommand implements IPrinterCommand {
	
	public Bundle bundle;
	public Bitmap content;
	public AddPictureCommand(Bundle b, Bitmap bitmap) {
		bundle = b;
		content =bitmap;
	}
	@Override
	public int exec(ZQPrinterSDK prn) {
		int ret = prn.Prn_PrintBitmap(content, BitmapSize.SIZE0);
		return ret;
	}


}
