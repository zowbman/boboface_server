package net.zowbman.base.model.vo;

import java.util.Map;

import net.zowbman.base.helper.CodeHelper;
import net.zowbman.base.helper.CodeHelper.CODE;
import net.zowbman.base.util.BaseUtil;

/**
 * 
 * Title:PubRetrunMsg
 * Description:公共返回协议
 * @author    zwb
 * @date      2016年7月25日 上午11:20:30
 *
 */
public class PubRetrunMsg {
	private int code;
	private String msg;
	private long times;
	private Object data;
	
	public PubRetrunMsg() {
		super();
	}
	public PubRetrunMsg(CODE code, Object data) {
		Map<Integer, String> map = CodeHelper.code(code);
		this.code = map.keySet().iterator().next();
		this.msg = map.values().iterator().next();
		this.times = BaseUtil.currentTimeMillis();
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getTimes() {
		return times;
	}
	public void setTimes(long times) {
		this.times = times;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
