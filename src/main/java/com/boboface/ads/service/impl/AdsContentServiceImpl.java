package com.boboface.ads.service.impl;

import java.util.List;

import net.zowbman.base.model.vo.EnvEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.boboface.ads.model.po.TBobofaceAdsContent;
import com.boboface.ads.model.vo.AdsProjectBuildContent;
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

	@Override
	public AdsProjectBuildContent getByAppIdAndEnv(Integer appId, EnvEnum envEnum) {
		Example example = new Example(TBobofaceAdsContent.class);
		TBobofaceAdsContent mappingFile = new TBobofaceAdsContent();//映射内容
		TBobofaceAdsContent templateFile = new TBobofaceAdsContent();//模板内容
		
		String title = "default.map.online";//默认线上模板
		if(EnvEnum.dev.equals(envEnum)){
			title = "default.map.dev";
		}else if(EnvEnum.test.equals(envEnum)){
			title = "default.map.test";
		}else if(EnvEnum.pre.equals(envEnum)){
			title = "default.map.pre";
		}
		
		example.createCriteria().andEqualTo("title", "tpl.setting");
		mappingFile = tBobofaceAdsContentMapper.selectByExample(example).iterator().next();
		example = new Example(TBobofaceAdsContent.class);
		example.createCriteria().andEqualTo("title", title);
		example.createCriteria().andEqualTo("appid", appId);
		templateFile = tBobofaceAdsContentMapper.selectByExample(example).iterator().next();
		
		AdsProjectBuildContent adsProjectBuildContent = new AdsProjectBuildContent(appId,mappingFile.getContent(),templateFile.getContent(),envEnum);
		return adsProjectBuildContent;
	}
}
