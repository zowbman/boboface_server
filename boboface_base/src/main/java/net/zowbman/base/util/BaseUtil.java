package net.zowbman.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 判断一个字符串数组中是否包含指定字符串
	 * @param s 字符串
	 * @param strs 字符串数组
	 * @return
	 */
	public static boolean isHave(String s, String... strs){
		for (int i = 0; i < strs.length; i++) {
			if(strs[i].indexOf(s)!=-1){
				return true;
			}
		}
		return false;
	}
	
	/**
     * 授权场景
     * 比较2个数组，获取相对于数组1相同的元素、相对于数组2相同的元素，2个数组相同的元素
     *
     * @param t1 旧的数组
     * @param t2 新的数组
     * 
     * @return 返回3种类型的集合
     * 
     * @date 2016年8月26日 下午5:34:49
     */
    public static Map<String, Object> compareArry(List<String> t1, List<String> t2) {
        /**
         * 集合A (新的数据有，旧的数据没有，即为要添加的数据)
         */
        List<String> listA=new ArrayList<String>();
        for (String str1:t2) {//遍历新的数组元素
            if (!t1.contains(str1)) {//旧的数组中不包含新的元素（即为要添加的元素）
                listA.add(str1);
            }
        }

        /**
         * 集合B (旧的数据中有，新的数据没有，即为要删除的数据)
         */
        List<String> listB = new ArrayList<String>();
        for(String str2:t1){//遍历旧的数组元素
            if( !t2.contains(str2)){//新的数组中不包含旧的数组（即为要删除的元素）
                listB.add(str2);
            }
        }

        /**
         * 集合C (旧的数据中有，新的数据有，即为共同的数据)
         */
        List<String> listC = new ArrayList<String>();
        for(String str3:t1){//遍历旧的数组元素
            if( t2.contains(str3)){//新的数组中包含旧的数组（即为共同的元素）
                listC.add(str3);
            }
        }


        Map<String, Object> map=new HashMap<String, Object>();
        map.put("add_arry", listA);
        map.put("delete_arry", listB);
        map.put("common_date", listC);
        return map;
    }
    
    /**
     * 统计指定字符串出现的个数
     * @param str 字符串
     * @param substr 指定字符
     * @return
     */
	public static int calculate(String str, String substr) {
		int ch = 0;
		int j = 0;
		String str1;
		for (int i = 0; i <= str.length() - 1; i++) {
			j = str.indexOf(substr);
			str1 = str.substring(j + 1, str.length());//获取子字符串
			str = str1;
			if (j != -1)
				ch++;
		}
		return ch;
	}
}
