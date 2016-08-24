package net.zowbman.base.shell;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title:ShellHandler
 * Description:shell 脚本执行类
 * @author    zwb
 * @date      2016年8月22日 下午5:20:09
 *
 */
public class ShellHandler {
	private static Logger logger = LoggerFactory.getLogger(ShellHandler.class);
	
	/**
	 * shell脚本执行方法
	 * @param shellScript 脚本命令
	 * @return
	 */
	public static String process(String[] shellScript){
		try {
			Process process = Runtime.getRuntime().exec(shellScript);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
	        LineNumberReader input = new LineNumberReader(ir);
	        String line;
	        logger.info("读取的信息：");
	        while((line = input.readLine()) != null){
	            logger.info(line);
	        }
	        input.close();
	        ir.close();
	        return line;
		} catch (Exception e) {
			logger.error("Exception catch:" , e);
		}
		return "0";
	}
}
