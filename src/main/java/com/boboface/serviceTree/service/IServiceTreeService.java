package com.boboface.serviceTree.service;

import java.util.List;

import net.zowbman.base.model.vo.OrderStyleEnum;

import com.boboface.base.service.IBaseService;
import com.boboface.exception.CustomException;
import com.boboface.serviceTree.model.po.TBobofaceServiceTree;
import com.boboface.serviceTree.model.po.TBobofaceServiceTreeCustom;

/**
 * 
 * Title:IServiceTreeService
 * Description:service tree service 接口
 * @author    zwb
 * @date      2016年8月16日 下午4:10:30
 *
 */
public interface IServiceTreeService extends IBaseService<TBobofaceServiceTree> {
	
	/**
	 * 根据排序查询业务树
	 * @param orderStyleEnum 排序
	 * @return
	 */
	List<TBobofaceServiceTree> findAllBySort(OrderStyleEnum orderStyleEnum);
	
	/**
	 * 根据排序查找已挂载ads的serviceTree
	 * @param orderStyleEnum 排序
	 * @param isMountAds 是否挂载自动部署系统
	 * @return
	 */
	List<TBobofaceServiceTree> findAllBySortAndIsMountAds(OrderStyleEnum orderStyleEnum, Byte isMountAds) throws CustomException;
	
	/**
	 * 查询根树
	 * @return
	 */
	TBobofaceServiceTree findRootServiceTree();
	
	/**
	 * 查询根据父业务树id查询子业务树
	 * @param parentServiceTreeId 父业务树
	 * @return
	 */
	List<TBobofaceServiceTree> findChildrenServiceTree(Integer parentServiceTreeId) throws CustomException;
	
	/**
	 * 查询根业务树（扩展接口）
	 * @return
	 */
	List<TBobofaceServiceTreeCustom> findRootServiceTreeCustom();
}
