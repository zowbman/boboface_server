package com.boboface.ads.service;

import java.util.List;

import com.boboface.ads.model.po.TBobofaceAdsContent;
import com.boboface.ads.model.vo.AdsProjectBuildContent;
import com.boboface.base.model.vo.EnvEnum;
import com.boboface.base.service.IBaseService;

/**
 * 
 * Title:IAdsContentService
 * Description:ads content service 接口
 * @author    zwb
 * @date      2016年8月16日 下午3:09:39
 *
 */
public interface IAdsContentService extends IBaseService<TBobofaceAdsContent> {
	/**
	 * 根据appId获取模板内容
	 * @param appId
	 * @return List<TBobofaceAdsContent>
	 */
	List<TBobofaceAdsContent> getByAppId(Integer appId);
	
	/**
	 * 根据环境变量获取映射文件和模板文件
	 * @param appId 项目id
	 * @param envEnum 环境变量
	 * @return AdsProjectBuildContent
	 */
	AdsProjectBuildContent getByAppIdAndEnv(Integer appId, EnvEnum envEnum);
}
