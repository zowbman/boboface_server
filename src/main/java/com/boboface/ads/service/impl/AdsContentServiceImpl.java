package com.boboface.ads.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.boboface.ads.model.po.TBobofaceAdsContent;
import com.boboface.ads.service.IAdsContentService;
import com.boboface.base.service.impl.BaseServiceImpl;

/**
 * 
 * Title:AdsContentServiceImpl
 * Description:ads content service 实现类
 * @author    zwb
 * @date      2016年8月16日 下午3:11:08
 *
 */
@Service
public class AdsContentServiceImpl extends BaseServiceImpl<TBobofaceAdsContent> implements IAdsContentService {
	
	private static Logger logger = LoggerFactory.getLogger(AdsContentServiceImpl.class);

	@Override
	public List<TBobofaceAdsContent> getByAppId(Integer appId) {
		if(appId == null){
			logger.error("appId 不允许为null");
			return null;
		}
		Example example = new Example(TBobofaceAdsContent.class);
		example.createCriteria().andEqualTo("appid", appId);
		return tBobofaceAdsContentMapper.selectByExample(example);
	}
}
