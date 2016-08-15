package net.zowbman.base.data.type.conversion.string;

import java.util.Collection;
import java.util.List;

/**
 * 
 * Title:IStringDataTypeConversion
 * Description:转化字符串接口
 * @author    zwb
 * @date      2016年7月18日 下午5:42:00
 *
 */
public interface IStringDataTypeConversion {
	/**
	 * 字符串分割成字符串数组
	 * @param string 字符串
	 * @param splitStr 分隔符
	 * @return String[]
	 */
	public String[] stringToStrings(String string, String splitStr);
	
	/**
	 * 字符串数组按指定字符连接
	 * @param splitStr 连接符
	 * @param strings 字符串数组
	 * @return String
	 */
	public String stringsToString(String splitStr, String... strings);
	
	/**
	 * 字符串分割成字符串集合
	 * @param string 字符串
	 * @param splitStr 分隔符
	 * @return Collection<String>
	 */
	public Collection<String> stringToCollection(String string, String splitStr);
	
	/**
	 * 字符串List按指定字符连接
	 * @param ListStr 字符串List
	 * @param splitStr 连接符
	 * @return String
	 */
	public String listStrToString(List<String> ListStr, String splitStr);
	
	/**
	 * 长整型List转字符串List
	 * @param listLong 长整型List
	 * @return List<String>
	 */
	public List<String> listStringToListLong(List<Long> listLong);
}
