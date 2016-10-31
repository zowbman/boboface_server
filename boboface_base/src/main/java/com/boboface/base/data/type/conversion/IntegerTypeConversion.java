package com.boboface.base.data.type.conversion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title:IIntegerDataTypeConversionImpl
 * Description:整型类型转换
 * @author    zwb
 * @date      2016年7月18日 下午5:45:50
 *
 */
public class IntegerTypeConversion {
	
	private static Logger logger = LoggerFactory.getLogger(IntegerTypeConversion.class);

	public static Integer[] stringsToIntegers(String... strings) {
		if (strings == null) {
			return null;
		}
		Integer[] integers = new Integer[strings.length];
		for (int i = 0; i < strings.length; i++) {
			integers[i] = Integer.valueOf(strings[i]);
		}
		return integers;
	}

	public static byte[] inputStreamToByte(InputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		try {
			while ((rc = inputStream.read(buff, 0, 100)) > 0) {
				byteArrayOutputStream.write(buff, 0, rc);
			}

		} catch (IOException e) {
			logger.error("数据读取转换异常 catch", e);
		}
		byte[] in2b = byteArrayOutputStream.toByteArray();
		return in2b;
	}
}
