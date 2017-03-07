package com.aisino.t8.printer;

import android.os.Bundle;

import com.zqprintersdk.PrinterConst.QRCode;
import com.zqprintersdk.ZQPrinterSDK;
/**
 * 打印二维码
 * @author yan
 *
 */
public class AddQrCodeCommand implements IPrinterCommand {

	private Bundle bundle;
	private String content;
	
	public AddQrCodeCommand(Bundle b, String s) {
		bundle = b;
		content = s;
	}
	
	@Override
	public int exec(ZQPrinterSDK prn) {
		
		return prn.Prn_PrintQRCode(QRCode.CHN, content);
	}

}
