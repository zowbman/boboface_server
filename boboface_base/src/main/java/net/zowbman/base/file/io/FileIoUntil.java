package net.zowbman.base.file.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title:FileIoUntil
 * Description:文件读取
 * @author    zwb
 * @date      2016年8月25日 下午6:39:51
 *
 */
public class FileIoUntil {
	
	private static Logger logger = LoggerFactory.getLogger(FileIoUntil.class);
	
	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件 
	 * @param fileName 文件名
	 * @return String
	 */
	public static String readFileByLines(String fileName) {
		String text = "";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				text += (tempString + "\n");
			}
			reader.close();
		} catch (IOException e) {
			logger.error("catch IOException", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("catch IOException", e1);
				}
			}
		}
		return text;
	}
	
	/**
	 * 字符写入文件
	 * @param filePath 文件地址
	 * @param data 字符
	 */
	public static void writeFile(String filePath, String data){
		try {
			File file = new File(filePath);
			if(!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if(!file.exists())
				file.createNewFile();
			FileWriter fileWritter = new FileWriter(file);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();
		} catch (IOException e) {
			logger.error("catch IOException", e);
		}
	}
	
	/**
	 * 以行为单位读取文件，读取模板文件
	 * @param fileName 文件名
	 * @return List<String>
	 */
	public static List<String> readFileByLinesReturnList(String fileName){
		List<String> textList = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				textList.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			logger.error("catch IOException", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					logger.error("catch IOException", e1);
				}
			}
		}
		return textList;
	}
}
