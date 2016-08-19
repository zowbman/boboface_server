package com.boboface.serviceTree.mapper;

import java.util.List;

import com.boboface.serviceTree.model.po.TBobofaceServiceTree;
import com.boboface.serviceTree.model.po.TBobofaceServiceTreeCustom;



/**
 * 
 * Title:TBobofaceServiceTreeMapper
 * Description:serviceTree Mapper
 * @author    zwb
 * @date      2016年8月16日 下午4:00:39
 *
 */
public interface TBobofaceServiceTreeMapperCustom {
	
	/**
	 * 查询根业务树
	 * @return List<TBobofaceServiceTreeCustom>
	 */
	List<TBobofaceServiceTreeCustom> findRootServiceTree();
	
	/**
	 * 根据父业务树id查询子业务树
	 * @param parentId 父业务树id
	 * @return List<TBobofaceServiceTree>
	 */
	List<TBobofaceServiceTree> findSerivceTreeByParentId(Integer parentId);
}