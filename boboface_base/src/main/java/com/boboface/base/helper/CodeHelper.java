package com.boboface.base.helper;

import java.util.HashMap;
import java.util.Map;

import com.boboface.base.properties.reader.PropertiesReaderAbstract;
import com.boboface.base.properties.reader.Reader;

/**
 * 
 * Title:CodeHelper
 * Description:错误代码
 * @author    zwb
 * @date      2016年7月25日 上午11:19:56
 *
 */
public class CodeHelper {
	
	/**
	 * reader
	 */
	private static PropertiesReaderAbstract reader = new Reader();
	
	/**
	 * 
	 * Title:CODE
	 * Description:
	 * D100000->请求成功<br>
	 * D200000->系统错误<br>
	 * D200001->参数错误<br>
	 * D200002->非法参数<br>
	 * D300000->未知错误<br>
	 * 
	 * @author    zwb
	 * @date      2016年9月12日 上午11:46:10
	 *
	 */
	public enum CODE{
		D100000,//请求成功
		//base 0~99
		D200000,//系统错误
		D200001,//参数错误
		D200002,//非法参数
		//ads 100~199
		D200100,//wiki树节点不允许重命名操作
		D200101,//wiki树节点不允许删除操作
		D200102,//wiki树节点不允许添加操作
		//sts 200~299
		D200200,//存储路径已有项目存在
		//sts 300~399
		D200300,//请清除挂载此业务树的项目后再次执行此操作
		D200301,//业务树节点不允许删除操作
		D200302,//业务树节点不允许重命名操作
		D200303,//请清除挂载此业务树的服务器后再此执行此操作
		//高权 400~499
		D200400,//高权密钥错误
		//--
		D300000;//未知错误
	}
	
	/**
	 * 获取信息
	 * @param CODE 错误码
	 * @return 错误信息
	 */
	public static Map<Integer, String> code(CODE CODE){
		String keyStr = String.valueOf(CODE.name());
		keyStr = keyStr.substring(1, keyStr.length());
		Integer key = Integer.valueOf(keyStr);
		
		String value = reader.getValue("Code", keyStr);
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(key, value);
		return map;
	}
}
