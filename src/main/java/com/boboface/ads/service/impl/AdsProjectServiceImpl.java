package com.boboface.ads.service.impl;

import java.util.List;

import net.zowbman.base.model.vo.OrderStyleEnum;
import net.zowbman.base.model.vo.PageBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.boboface.ads.model.po.TBobofaceAdsProject;
import com.boboface.ads.service.IAdsProjectService;
import com.boboface.base.service.impl.BaseServiceImpl;
import com.boboface.exception.CustomException;
import com.github.pagehelper.PageHelper;

/**
 * 
 * Title:AdsProjectServiceImpl
 * Description:ads project service 实现类
 * @author    zwb
 * @date      2016年8月18日 下午3:59:49
 *
 */
@Service
public class AdsProjectServiceImpl extends BaseServiceImpl<TBobofaceAdsProject> implements IAdsProjectService {
	
	private static Logger logger = LoggerFactory.getLogger(AdsProjectServiceImpl.class);

	@Override
	public List<TBobofaceAdsProject> findProjectListByPageBeanAndServiceTreeId(PageBean pageBean, Integer serviceTreeId) throws CustomException{
		if(pageBean == null || pageBean.getPageNum() == null || pageBean.getPageSize() == null)
			throw new CustomException("分页数据不允许为空");
		if(serviceTreeId == null)
			throw new CustomException("serviceTreeId不允许为空");
		Example example = new Example(TBobofaceAdsProject.class);
		if(OrderStyleEnum.ACS.equals(pageBean.getOrderStyleEnum())){
			example.orderBy(pageBean.getOrderByColumn()).asc();
		}else if(OrderStyleEnum.DESC.equals(pageBean.getOrderStyleEnum())){
			example.orderBy(pageBean.getOrderByColumn()).desc();
		}
		PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize(), true, false);
		example.createCriteria().andEqualTo("servicetreeid", serviceTreeId);
		return tBobofaceAdsProjectMapper.selectByExample(example);
	}
	
	@Override
	public List<TBobofaceAdsProject> findProjectListByServiceTreeId(Integer serviceTreeId) throws CustomException {
		if(serviceTreeId == null){
			throw new CustomException("业务树id不允许为空");
		}
		Example example = new Example(TBobofaceAdsProject.class);
		example.createCriteria().andEqualTo("servicetreeid", serviceTreeId);
		return tBobofaceAdsProjectMapper.selectByExample(example);
	}

	@Override
	public boolean findProjectStoragepathIsExist(String storagepath) throws CustomException {
		if(storagepath == null){
			throw new CustomException("存储路径不允许为空");
		}
		Example example = new Example(TBobofaceAdsProject.class);
		example.createCriteria().andEqualTo("storagepath", storagepath);
		int count = tBobofaceAdsProjectMapper.selectCountByExample(example);
		if(count > 0){
			return true;
		}
		return false;
	}
}
