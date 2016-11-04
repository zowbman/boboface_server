package com.boboface.wiki.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.boboface.base.model.vo.OrderStyleEnum;
import com.boboface.base.service.impl.BaseServiceImpl;
import com.boboface.exception.CustomException;
import com.boboface.wiki.model.po.TBobofaceWikiTree;
import com.boboface.wiki.service.IWikiTreeService;

/**
 * 
 * Title:WikiTreeServiceImpl
 * Description:wiki tree service 实现类
 * @author    zwb
 * @date      2016年7月25日 下午12:16:59
 *
 */
@Service
public class WikiTreeServiceImpl extends BaseServiceImpl<TBobofaceWikiTree> implements IWikiTreeService {

	@Override
	public Integer getMaxSortTreeNode() {
		Integer sort = tBobofaceWikiTreeMapperCustom.getMaxSortTreeNode();
		if(sort == null)
			sort = 0;
		return sort;
	}

	@Override
	public void updateWikiTreeNodeSort(Integer nodeId, Integer targetNodeId, String type) throws CustomException {
		TBobofaceWikiTree treeNode = tBobofaceWikiTreeMapper.selectByPrimaryKey(nodeId);
		TBobofaceWikiTree targetTreeNode = tBobofaceWikiTreeMapper.selectByPrimaryKey(targetNodeId);
		if(treeNode == null || targetTreeNode == null){
			new CustomException("无法找到节点以及目标节点");
		}
		
		//"inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
		if("inner".equals(type)){
			treeNode.setParentid(targetTreeNode.getId());
			tBobofaceWikiTreeMapper.updateByPrimaryKeySelective(treeNode);
		}else if("prev".equals(type)){
			treeNode.setParentid(targetTreeNode.getParentid());
			if(treeNode.getSort() > targetTreeNode.getSort()){
				Integer temp = treeNode.getSort();
				treeNode.setSort(targetTreeNode.getSort());
				targetTreeNode.setSort(temp);	
			}
			tBobofaceWikiTreeMapper.updateByPrimaryKeySelective(treeNode);
			tBobofaceWikiTreeMapper.updateByPrimaryKeySelective(targetTreeNode);
		}else if("next".equals(type)){
			treeNode.setParentid(targetTreeNode.getParentid());
			if(treeNode.getSort() < targetTreeNode.getSort()){
				Integer temp = treeNode.getSort();
				treeNode.setSort(targetTreeNode.getSort());
				targetTreeNode.setSort(temp);
			}else{
				Example example = new Example(TBobofaceWikiTree.class);
				example.orderBy("sort").desc();
				Criteria criteria = example.createCriteria();
				criteria.andCondition("sort<", treeNode.getSort());
				criteria.andEqualTo("parentid", treeNode.getParentid());
				List<TBobofaceWikiTree> wikiTrees = tBobofaceWikiTreeMapper.selectByExample(example);
				if(wikiTrees != null && wikiTrees.size() > 0){
					TBobofaceWikiTree tempWikiTree = wikiTrees.iterator().next();
					Integer temp = treeNode.getSort();
					treeNode.setSort(tempWikiTree.getSort());
					tempWikiTree.setSort(temp);
					tBobofaceWikiTreeMapper.updateByPrimaryKeySelective(tempWikiTree);
				}
			}
			tBobofaceWikiTreeMapper.updateByPrimaryKeySelective(treeNode);
			tBobofaceWikiTreeMapper.updateByPrimaryKeySelective(targetTreeNode);
		}else{
			new CustomException("非法参数,type:" + type);
		}
	}

	@Override
	public List<TBobofaceWikiTree> findAllByOderBySort(OrderStyleEnum orderStyleEnum) {
		Example example = new Example(TBobofaceWikiTree.class);
		if(OrderStyleEnum.ACS.equals(orderStyleEnum)){
			example.orderBy("sort").asc();
		}else{
			example.orderBy("sort").desc();
		}
		
		return tBobofaceWikiTreeMapper.selectByExample(example);
	}
}
