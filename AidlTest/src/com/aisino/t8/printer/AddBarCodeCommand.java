package com.aisino.t8.printer;

import android.os.Bundle;

import com.zqprintersdk.PrinterConst.Barcode;
import com.zqprintersdk.PrinterConst.BarcodeText;
import com.zqprintersdk.ZQPrinterSDK;

public class AddBarCodeCommand implements IPrinterCommand {

	private Bundle bundle;
	private String content;
	public AddBarCodeCommand(Bundle b, String s){
		bundle = b;
		content = s;
	}
	@Override
	public int exec(ZQPrinterSDK prn) {
		return prn.Prn_PrintBarcode(content, Barcode.EAN8, 60, 2, BarcodeText.TEXT_ABOVE);
	}

}
