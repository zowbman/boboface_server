package net.zowbman.base.data.type.conversion.integer.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.zowbman.base.data.type.conversion.integer.IIntegerDataTypeConversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title:IIntegerDataTypeConversionImpl
 * Description:整型转化实现类
 * @author    zwb
 * @date      2016年7月18日 下午5:45:50
 *
 */
public class IIntegerDataTypeConversionImpl implements IIntegerDataTypeConversion {
	
	private static Logger logger = LoggerFactory.getLogger(IIntegerDataTypeConversionImpl.class);
	
	@Override
	public Integer[] stringsToIntegers(String... strings) {
		if (strings == null) {
			return null;
		}
		Integer[] integers = new Integer[strings.length];
		for (int i = 0; i < strings.length; i++) {
			integers[i] = Integer.valueOf(strings[i]);
		}
		return integers;
	}

	@Override
	public byte[] inputStreamToByte(InputStream inputStream) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		try {
			while ((rc = inputStream.read(buff, 0, 100)) > 0) {
				byteArrayOutputStream.write(buff, 0, rc);
			}
			
		} catch (IOException e) {
			logger.error("数据读取转换异常 catch" , e);
		}
		byte[] in2b = byteArrayOutputStream.toByteArray();
		return in2b;
	}
}
