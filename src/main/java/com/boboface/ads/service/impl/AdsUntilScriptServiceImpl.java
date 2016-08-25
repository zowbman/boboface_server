package com.boboface.ads.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.boboface.ads.model.po.TBobofaceAdsUntilscript;
import com.boboface.ads.service.IAdsUnitlScriptService;
import com.boboface.base.service.impl.BaseServiceImpl;

/**
 * 
 * Title:AdsProjectServiceImpl
 * Description:ads untilScript service 实现类
 * @author    zwb
 * @date      2016年8月18日 下午3:59:49
 *
 */
@Service
public class AdsUntilScriptServiceImpl extends BaseServiceImpl<TBobofaceAdsUntilscript> implements IAdsUnitlScriptService {
	
	private static Logger logger = LoggerFactory.getLogger(AdsUntilScriptServiceImpl.class);

	@Override
	public List<TBobofaceAdsUntilscript> getByAppId(Integer appId) {
		if(appId == null){
			logger.error("appId 不允许为null");
			return null;
		}
		Example example = new Example(TBobofaceAdsUntilscript.class);
		example.createCriteria().andEqualTo("appid", appId);
		return tBobofaceAdsUntilscriptMapper.selectByExample(example);
	}
}
