package net.zowbman.base.sort.complarator;

import java.util.Comparator;

import net.zowbman.base.sort.ISortObject;

/**
 * 
 * Title:ObjectComparator
 * Description:比较类
 * @author    zwb
 * @date      2016年7月18日 下午6:15:02
 *
 */
public class ObjectComparator implements Comparator<Object> {
	
	private ISortObject iSortObject;
	
	public ObjectComparator(ISortObject iSortObject) {
		this.iSortObject = iSortObject;
	}

	@Override
	public int compare(Object o1, Object o2) {
		return iSortObject.compare(o1, o2);
	}
}
