package com.boboface.base.sort.rule;

/**
 * 
 * Title:OrderRule
 * Description:排序规则
 * @author    zwb
 * @date      2016年7月18日 下午6:22:34
 *
 */
public class OrderRule {
	public final static int _LONG = 0; 
	public final static int _STRING = 1;
	public final static int _DATA = 2;
	public final static int _BIGDECIMAL = 3;
	public final static int _INTEGER = 4;
	
	/**
	 * 属性名
	 */
	private String propertyName;
	
	/**
	 * 是否升序
	 */
	private boolean isAsc;
	
	private int dataType;

	public OrderRule() {
		super();
	}
	
	public OrderRule(String propertyName, boolean isAsc, int dataType) {
		this.propertyName = propertyName;
		this.isAsc = isAsc;
		this.dataType = dataType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public static int getLong() {
		return _LONG;
	}

	public static int getString() {
		return _STRING;
	}

	public static int getData() {
		return _DATA;
	}

	public static int getBigdecimal() {
		return _BIGDECIMAL;
	}

	public static int getInteger() {
		return _INTEGER;
	}
}
