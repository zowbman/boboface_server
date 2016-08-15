package net.zowbman.base.data.type.conversion.integer;

import java.io.InputStream;

/**
 * 
 * Title:IIntegerDataTypeConversion
 * Description:转化整型接口
 * @author    zwb
 * @date      2016年7月18日 下午5:46:18
 *
 */
public interface IIntegerDataTypeConversion {
	/**
	 * 字符串数组转整型数组
	 * strings=>integers
	 * @param strings 字符串数组
	 * @return Integer[]
	 */
	public Integer[] stringsToIntegers(String... strings);
	
	/**
	 * 输入流转字节数组
	 * @param inputStream 输入流
	 * @return byte[]
	 */
	public byte[] inputStreamToByte(InputStream inputStream);
}
