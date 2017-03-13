package com.aisino.t8.printer;

import com.zqprintersdk.ZQPrinterSDK;

/**
 * 打印机指令
 * @author yan
 *
 */
public interface IPrinterCommand {
	public int exec(ZQPrinterSDK prn);
}
