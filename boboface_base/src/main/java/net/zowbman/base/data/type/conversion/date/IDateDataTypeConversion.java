package net.zowbman.base.data.type.conversion.date;

import net.zowbman.base.data.type.conversion.date.vo.DATEFORMAT;

/**
 * 
 * Title:IDateDataTypeConversion
 * Description:日期转化接口
 * @author    zwb
 * @date      2016年7月18日 下午5:51:02
 *
 */
public interface IDateDataTypeConversion {
	
	/**
	 * 时间戳转字符串日期
	 * @param timeMillis 时间戳
	 * @param isTen 是否10位
	 * @param dateFormat 格式
	 * @return
	 */
	public String timeMillisToDate(long timeMillis, boolean isTen, DATEFORMAT dateFormat);
}
