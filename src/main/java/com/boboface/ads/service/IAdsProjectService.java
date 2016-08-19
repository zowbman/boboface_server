package com.boboface.ads.service;

import java.util.List;

import net.zowbman.base.model.vo.PageBean;

import com.boboface.ads.model.po.TBobofaceAdsProject;
import com.boboface.base.service.IBaseService;
import com.boboface.exception.CustomException;

/**
 * 
 * Title:IAdsProjectService
 * Description:ads project service接口
 * @author    zwb
 * @date      2016年8月18日 下午3:58:21 
 *
 */
public interface IAdsProjectService extends IBaseService<TBobofaceAdsProject> {
	
	/**
	 * 
	 * @param pageBean 分页信息
	 * @param serviceTreeId 业务树id
	 * @return List<TBobofaceAdsProject>
	 * @throws CustomException
	 */
	List<TBobofaceAdsProject> findProjectListByPageBeanAndSeviceTreeId(PageBean pageBean, Integer serviceTreeId) throws CustomException;
}
