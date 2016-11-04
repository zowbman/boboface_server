package com.boboface.ads.service;

import java.util.List;

import com.boboface.ads.model.po.TBobofaceAdsProject;
import com.boboface.base.model.vo.PageBean;
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
	 * 根据业务树id获取项目列表(分页)
	 * @param pageBean 分页信息
	 * @param serviceTreeId 业务树id
	 * @return List<TBobofaceAdsProject>
	 * @throws CustomException
	 */
	List<TBobofaceAdsProject> findProjectListByPageBeanAndServiceTreeId(PageBean pageBean, Integer serviceTreeId) throws CustomException;
	
	
	/**
	 * 根据业务树id获取项目列表
	 * @param serviceTreeId 业务树id
	 * @return List<TBobofaceAdsProject>
	 * @throws CustomException
	 */
	List<TBobofaceAdsProject> findProjectListByServiceTreeId(Integer serviceTreeId) throws CustomException;

	/**
	 * 存储路径是否已存在项目
	 * @param storagepath 存储路径
	 * @return boolean
	 */
	boolean findProjectStoragepathIsExist(String storagepath) throws CustomException;
	
	/**
	 * 根据ids删除ads项目
	 * @param ids
	 */
	void deleteByIds(Integer[] ids) throws CustomException;
}
