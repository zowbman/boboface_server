package net.zowbman.base.sort.complarator;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import net.zowbman.base.data.type.conversion.string.IStringDataTypeConversion;
import net.zowbman.base.data.type.conversion.string.impl.StringDataTypeConversionImpl;
import net.zowbman.base.sort.ISortObject;
import net.zowbman.base.sort.rule.OrderRule;
import net.zowbman.base.util.BaseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title:SortObjectImpl
 * Description:重写排序实现类
 * 使用方式：
 * 	List<OrderRule> orderRules = new ArrayList<OrderRule>();//排序规则队列
 * 	orderRules.add(new OrderRule("state", true, OrderRule._STRING));//排序规则
 * 	ISortObject iSortObject = new SortObjectImpl(new ArrayList<OrderRule>);
 * 	ObjectComparator objectComparator = new ObjectComparator(iSortObject);
 * 	Collections.sort(resultDate, objectComparator);
 * @author    zwb
 * @date      2016年7月18日 下午6:19:51
 *
 */
public class SortObjectImpl implements ISortObject {
	
	/**
	 * 类型转换
	 */
	private IStringDataTypeConversion iStringDataTypeConversion = new StringDataTypeConversionImpl();
	
	private static Logger logger = LoggerFactory.getLogger(SortObjectImpl.class);
	
	List<OrderRule> orderRules;
	
	public SortObjectImpl(List<OrderRule> orderRules) {
		this.orderRules = orderRules;
	}

	@Override
	public int compare(Object o1, Object o2) {
		int result = 0;
		try{
			for (OrderRule orderRule : orderRules) {
				Object v1 = getValue(o1, orderRule.getPropertyName());
				Object v2 = getValue(o2, orderRule.getPropertyName());
				result = sort(v1, v2, orderRule.getDataType());
				if(!orderRule.isAsc())
					result *= -1;
				if(result != 0)
					break;
			}
		} catch (Exception e){
			logger.error("排序比较异常:", e);
		}
		return result;
	}
	
	/**
	 * 比较类型
	 * @param v1
	 * @param v2
	 * @param dataType 比较类型
	 * @return
	 */
	private int sort(Object v1, Object v2, int dataType){
		int result = 0;
		switch (dataType) {
		case OrderRule._STRING:
			String s1 = (String) v1;
			String s2 = (String) v2;
			result = s1.compareTo(s2);
			break;
		case OrderRule._BIGDECIMAL:
			BigDecimal d1 = (BigDecimal) v1;
			BigDecimal d2 = (BigDecimal) v2;
			result = d1.compareTo(d2);
			break;
		case OrderRule._LONG:
			Long l1 = (Long) v1;
			Long l2 = (Long) v2;
			result = l1.compareTo(l2);
			break;
		case OrderRule._INTEGER:
			Integer i1 = (Integer) v1;
			Integer i2 = (Integer) v2;
			result = i1.compareTo(i2);
			break;
		default:
			result = 0;
			break;
		}
		return result;
	}
	
	/**
	 * 递归反射获取值
	 * @param obj
	 * @param propertyName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private Object getValue(Object obj, String propertyName) throws IllegalArgumentException, IllegalAccessException{
		Object result = null;
		String[] proNames = propertyName.split("\\.");//分割字符串
		Field field = null;
		if(obj instanceof HashMap){
			result = ((HashMap<?, ?>) obj).get(proNames[0]);
		}else{
			field = getField(obj.getClass(), proNames[0]);
			result = field.get(obj);
		}
		if(proNames.length == 1){
			return result;
		}else{
			String[] newProNames = BaseUtil.removeArrayItem(proNames, 0);//去除第一个
			String newPropertyName = iStringDataTypeConversion.stringsToString(".", newProNames);
			return getValue(result, newPropertyName);
		}
	}
	
	/**
	 * 反射获得属性，若奔雷不存在该属性则递归调用父类查找，若属性始终不存在返回空
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	private Field getField(Class<?> clazz, String fieldName){
		Field field = null;
		if(clazz == null)
			return null;
		try{
			field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		}catch(NoSuchFieldException e){
			logger.info("递归调用父类查找:", e);
			return getField(clazz.getSuperclass(), fieldName);
		}
	}
}
