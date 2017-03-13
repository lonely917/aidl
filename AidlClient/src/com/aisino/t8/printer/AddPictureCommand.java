package com.aisino.t8.printer;

import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.zqprintersdk.PrinterConst.Alignment;
import com.zqprintersdk.PrinterConst.BitmapSize;
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
		
//		//获取参数
//		int height = bundle.getInt("height", 1);
//		int width = bundle.getInt("width", 1);
//		int offset = bundle.getInt("offset", 0);
		
		//预留字段 //zq打印机并不支持宽度和高度设定，没有偏移设定，但是有对齐设置
//		String alignString = bundle.getString("align", "left");
		
//		int alignment = Printer.getAlign(alignString);
//		prn.Prn_SetAlignment(alignment);
//		prn.Prn_PrintString("LEFT SIZE0 Print\r\n");
		prn.Prn_PrintText("LeftDefault00\r\n", PrinterConst.Alignment.LEFT, PrinterConst.Font.DEFAULT, 
				PrinterConst.WidthSize.SIZE0 | PrinterConst.HeightSize.SIZE0);
//		int ret = prn.Prn_PrintBitmap(content, BitmapSize.SIZE0);//使用正常大小..实际是0.5倍
		
		try {
			
			InputStream is = context.getAssets().open("logo.png");
			int size = is.available();
			//Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			Bitmap bm = BitmapFactory.decodeByteArray(buffer, 0, size);
			int ret = prn.Prn_PrintBitmap(bm, BitmapSize.SIZE0);//使用正常大小..实际是0.5倍
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}


}
