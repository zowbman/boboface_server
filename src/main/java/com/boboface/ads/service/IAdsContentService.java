package com.boboface.ads.service;

import java.util.List;

import com.boboface.ads.model.po.TBobofaceAdsContent;
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
	 * 根据adsTreeId获取模板内容
	 * @param adsTreeId
	 * @return List<TBobofaceAdsContent>
	 */
	List<TBobofaceAdsContent> getByAdsTreeId(Integer adsTreeId);
}
