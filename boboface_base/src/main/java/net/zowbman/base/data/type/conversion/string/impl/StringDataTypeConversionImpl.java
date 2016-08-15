package net.zowbman.base.data.type.conversion.string.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import net.zowbman.base.data.type.conversion.string.IStringDataTypeConversion;

/**
 * 
 * Title:StringDataTypeConversionImpl
 * Description:字符串转换实现类
 * @author    zwb
 * @date      2016年7月18日 下午5:43:05
 *
 */
public class StringDataTypeConversionImpl implements IStringDataTypeConversion {
	@Override
	public String[] stringToStrings(String string, String splitStr) {
		if(string == null)
			return null;
		return string.split(splitStr);
	}

	@Override
	public String stringsToString(String splitStr, String... strings) {
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

	@Override
	public Collection<String> stringToCollection(String string, String splitStr) {
		Collection<String> collection = new HashSet<String>();
		String[] strings = string.split(splitStr);
		for (String s : strings) {
			collection.add(s);
		}
		return collection;
	}

	@Override
	public String listStrToString(List<String> ListStr, String splitStr) {
		if (ListStr == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : ListStr) {
			if (flag) {
				result.append(splitStr);
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}
	
	@Override
	public List<String> listStringToListLong(List<Long> listLong) {
		if(listLong == null)
			return null;
		List<String> strings = new ArrayList<String>();
		for (Long l : listLong) {
			strings.add(l.toString());
		}
		return strings;
	}
}
