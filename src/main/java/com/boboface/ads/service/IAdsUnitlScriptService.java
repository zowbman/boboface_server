package com.boboface.ads.service;

import java.util.List;

import com.boboface.ads.model.po.TBobofaceAdsUntilscript;
import com.boboface.base.service.IBaseService;

/**
 * 
 * Title:IAdsUnitlScriptService
 * Description:ads unitlScript
 * @author    zwb
 * @date      2016年8月25日 下午6:22:24
 *
 */
public interface IAdsUnitlScriptService extends IBaseService<TBobofaceAdsUntilscript> {
	
	/**
	 * 根据appId获取工具脚本
	 * @param appId
	 * @return List<TBobofaceAdsUntilscript>
	 */
	List<TBobofaceAdsUntilscript> getByAppId(Integer appId);
}
