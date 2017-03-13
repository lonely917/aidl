package com.aisino.t8.printer;

import com.zqprintersdk.ZQPrinterSDK;

/**
 * 打印空行 line指定行数
 * @author yan
 *
 */
public class PaperSkipCommand implements IPrinterCommand {
	
	public int line;
	
	public PaperSkipCommand(int l) {
		line = l;
	}
	
	@Override
	public int exec(ZQPrinterSDK prn) {
		return prn.Prn_LineFeed(line);
	}

}
