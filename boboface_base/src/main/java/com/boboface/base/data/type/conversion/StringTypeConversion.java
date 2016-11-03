package com.boboface.base.data.type.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boboface.base.util.BaseUtil;

/**
 * 
 * Title:StringDataTypeConversion
 * Description:字符串转换实现类
 * @author    zwb
 * @date      2016年7月18日 下午5:43:05
 *
 */
public class StringTypeConversion {
	
	private static Logger logger = LoggerFactory.getLogger(StringTypeConversion.class);
	
	/**
	 * String -> String[](字符串分割成字符串数组)
	 * @param string 字符串
	 * @param splitStr 分隔符
	 * @return String[]
	 */
	public static String[] stringToStrings(String string, String splitStr) {
		if(string == null)
			return null;
		return string.split(splitStr);
	}

	/**
	 * String[] -> String(字符串数组按指定字符连接)
	 * @param splitStr 连接符
	 * @param strings 字符串数组
	 * @return String
	 */
	public static String stringsToString(String splitStr, String... strings) {
		if (strings == null && splitStr == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : strings) {
			if (flag) {
				result.append(splitStr);
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	/**
	 * String -> Collection<String>(字符串分割成字符串集合)
	 * @param string 字符串
	 * @param splitStr 分隔符
	 * @return Collection<String>
	 */
	public static Collection<String> stringToCollection(String string, String splitStr) {
		Collection<String> collection = new HashSet<String>();
		String[] strings = string.split(splitStr);
		for (String s : strings) {
			collection.add(s);
		}
		return collection;
	}

	/**
	 * List<T> -> String(List<T>转String，参数非对象List)
	 * @param ListStr 字符串List
	 * @param splitStr 连接符
	 * @return String
	 */
	public static <T> String listStrToString(List<T> list, String splitStr) {
		if (list == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (T t : list) {
			if(!BaseUtil.isWrapClass(t.getClass()) && !(t instanceof String)){
				logger.error("非基本类型集合");
				return null;
			}
			if (flag) {
				result.append(splitStr);
			} else {
				flag = true;
			}
			result.append(t);
		}
		return result.toString();
	}
	
	/**
	 * List<T> -> List<String>(List<T>转List<String>，参数非对象List)
	 * @param list<T>
	 * @return List<String>
	 */
	public static <T> List<String> listToListString(List<T> list) {
		if(list == null)
			return null;
		List<String> strings = new ArrayList<String>();
		for (T t : list) {
			if(!BaseUtil.isWrapClass(t.getClass()) && !(t instanceof String)){
				logger.error("非基本类型集合");
				return null;
			}
			strings.add(t.toString());
		}
		return strings;
	}
}
