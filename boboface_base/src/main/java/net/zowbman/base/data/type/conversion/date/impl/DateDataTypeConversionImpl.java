package net.zowbman.base.data.type.conversion.date.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.zowbman.base.data.type.conversion.date.IDateDataTypeConversion;
import net.zowbman.base.data.type.conversion.date.vo.DATEFORMAT;

/**
 * 
 * Title:DateDataTypeConversionImpl
 * Description:日期转换实现类
 * @author    zwb
 * @date      2016年7月18日 下午6:03:35
 *
 */
public class DateDataTypeConversionImpl implements IDateDataTypeConversion {
	@Override
	public String timeMillisToDate(long timeMillis, boolean isTen, DATEFORMAT dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.getValue());
		String sd = null;
		if(isTen){
			sd = sdf.format(new Date(timeMillis * 1000L));
		}
		sd = sdf.format(new Date(timeMillis));
		return sd;
	}
}
