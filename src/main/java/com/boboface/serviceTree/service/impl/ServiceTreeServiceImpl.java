package com.boboface.serviceTree.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.boboface.base.model.vo.OrderStyleEnum;
import com.boboface.base.service.impl.BaseServiceImpl;
import com.boboface.exception.CustomException;
import com.boboface.serviceTree.model.po.TBobofaceServiceTree;
import com.boboface.serviceTree.model.po.TBobofaceServiceTreeCustom;
import com.boboface.serviceTree.service.IServiceTreeService;

/**
 * 
 * Title:ServiceTreeServiceImpl
 * Description:service tree service 实现类
 * @author    zwb
 * @date      2016年8月16日 下午4:15:55
 *
 */
@Service
public class ServiceTreeServiceImpl extends BaseServiceImpl<TBobofaceServiceTree> implements IServiceTreeService {

	private static Logger logger = LoggerFactory.getLogger(ServiceTreeServiceImpl.class);
	
	@Override
	public List<TBobofaceServiceTree> findAllBySortAndIsMountAds(OrderStyleEnum orderStyleEnum, Byte isMountAds) throws CustomException {
		if(isMountAds == null)
			throw new CustomException("是否挂载ads参数不能为null");
		Example example = new Example(TBobofaceServiceTree.class);
		if(OrderStyleEnum.ACS.equals(orderStyleEnum)){
			example.orderBy("sort").asc();
		}else if(OrderStyleEnum.DESC.equals(orderStyleEnum)){
			example.orderBy("sort").desc();
		}
		example.createCriteria().andEqualTo("ismountads", isMountAds);
		return tBobofaceServiceTreeMapper.selectByExample(example);
	}

	@Override
	public List<TBobofaceServiceTree> findAllBySort(OrderStyleEnum orderStyleEnum) {
		Example example = new Example(TBobofaceServiceTree.class);
		if(OrderStyleEnum.ACS.equals(orderStyleEnum)){
			example.orderBy("sort").asc();
		}else if(OrderStyleEnum.DESC.equals(orderStyleEnum)){
			example.orderBy("sort").desc();
		}
		return tBobofaceServiceTreeMapper.selectByExample(example);
	}

	@Override
	public TBobofaceServiceTree findRootServiceTree() {
		Example example = new Example(TBobofaceServiceTree.class);
		example.createCriteria().andIsNull("parentid");
		List<TBobofaceServiceTree> tBobofaceServiceTrees = tBobofaceServiceTreeMapper.selectByExample(example);
		if(tBobofaceServiceTrees == null)
			return null;
		if(tBobofaceServiceTrees.size() > 1){
			logger.error("有多个根节点");
			return null;
		}
		return tBobofaceServiceTrees.iterator().next();
	}

	@Override
	public List<TBobofaceServiceTree> findChildrenServiceTree(Integer parentServiceTreeId) throws CustomException {
		if(parentServiceTreeId == null)
			throw new CustomException("父业务树id不能为null");
		Example example = new Example(TBobofaceServiceTree.class);
		example.createCriteria().andEqualTo("parentid", parentServiceTreeId);
		return tBobofaceServiceTreeMapper.selectByExample(example);
	}

	@Override
	public List<TBobofaceServiceTreeCustom> findRootServiceTreeCustom() {
		return tBobofaceServiceTreeMapperCustom.findRootServiceTree();
	}
}
