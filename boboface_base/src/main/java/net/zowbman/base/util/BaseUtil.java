package net.zowbman.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Title:BaseUtil
 * Description:基础工具类
 * @author    zwb
 * @date      2016年7月18日 下午6:10:21
 *
 */
public class BaseUtil {
	/**
	 * 时间戳10位转13位
	 * @param timeMillis
	 * @return
	 */
	public static long timeMillis13(long timeMillis){
		return timeMillis * 1000L;
	}
	
	/**
	 * 时间戳13位转10位
	 * @param timeMillis
	 * @return
	 */
	public static long timeMillis10(long timeMillis){
		return timeMillis / 1000L;
	}
	
	/**
	 * 当前时间戳(10位)
	 * @return
	 */
	public static long currentTimeMillis(){
		return timeMillis10(System.currentTimeMillis());
	}
	
	/**
	 * 删除数组中指定元素（索引）
	 * @param arrs 需要删除元素的数组
	 * @param index 需要删除元素的索引（越界时抛出异常）
	 * @return
	 */
	public static<T> T[] removeArrayItem(T[] arrs, int index){
		int len = arrs.length;
		if(index < 0 || index >= len)
			throw new IllegalArgumentException("索引出界");
		List<T> list = new ArrayList<T>();
		for(int i = 0; i < len; i++){
			if(i != index)
				list.add(arrs[i]);
		}
		T[] arrsResult = list.toArray(arrs);
		return Arrays.copyOf(arrsResult, arrsResult.length - 1);
	}
}
