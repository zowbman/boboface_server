package net.zowbman.base.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Title:CodeHelper
 * Description:错误代码
 * @author    zwb
 * @date      2016年7月25日 上午11:19:56
 *
 */
public class CodeHelper {
	public enum CODE{
		_100000,//请求成功
		_200000,//系统错误
		_200001,//参数错误
		_200002,//不允许重命名节点操作
		_200003,//不允许添加子级节点操作
		_200004,//不允许删除节点操作
		_200005,//高权密钥错误
		_300000;//未知错误
	}
	
	/**
	 * 代码映射
	 */
	private static final Map<CODE,Integer> CODE_MAP = new HashMap<CODE,Integer>(){
		private static final long serialVersionUID = 1L;
	{
		put(CODE._100000, 100000);
		put(CODE._200000, 200000);
		put(CODE._200001, 200001);
		put(CODE._200002, 200002);
		put(CODE._200003, 200003);
		put(CODE._200004, 200004);
		put(CODE._200005, 200005);
		put(CODE._300000, 300000);
	}};
	
	/**
	 * 信息映射
	 */
	private static final Map<Integer,String> MSG_MAP = new HashMap<Integer,String>(){
		private static final long serialVersionUID = 1L;
	{
		put(100000, "请求成功");
		put(200000, "系统错误");
		put(200001, "参数错误");
		put(200002, "不允许重命名节点操作");
		put(200003, "不允许添加子级节点操作");
		put(200004, "不允许删除节点操作");
		put(200005, "高权密钥错误");
		put(300000, "未知错误");
	}};
	
	private static String msg(Integer code){
		return MSG_MAP.get(code);
	}
	
	/**
	 * 获取信息
	 * @param CODE
	 * @return
	 */
	public static Map<Integer, String> code(CODE CODE){
		Integer code = 300000;
		Integer temp = CODE_MAP.get(CODE);
		if(temp != null)
			code = temp;
		Map<Integer,String> map = new HashMap<Integer, String>();
		map.put(code, msg(code));
		return map;
	}
}
