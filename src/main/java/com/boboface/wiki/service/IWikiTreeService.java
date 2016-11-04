package com.boboface.wiki.service;

import java.util.List;

import com.boboface.base.model.vo.OrderStyleEnum;
import com.boboface.base.service.IBaseService;
import com.boboface.exception.CustomException;
import com.boboface.wiki.model.po.TBobofaceWikiTree;

/**
 * 
 * Title:IWikiTreeService
 * Description:wiki tree service 接口
 * @author    zwb
 * @date      2016年7月25日 下午12:16:00
 *
 */
public interface IWikiTreeService extends IBaseService<TBobofaceWikiTree> {
	
	/**
	 * 获取节点sort最大的值
	 * @return Integer
	 */
	Integer getMaxSortTreeNode();
	
	/**
	 * 更新排序 
	 * @param nodeId
	 * @param targetNodeId
	 * @param type
	 */
	void updateWikiTreeNodeSort(Integer nodeId, Integer targetNodeId, String type) throws CustomException;
	
	/**
	 * 根据排序条件查询tree list
	 * @param orderStyleEnum
	 * @return List<TBobofaceWikiTree>
	 */
	List<TBobofaceWikiTree> findAllByOderBySort(OrderStyleEnum orderStyleEnum);
}
