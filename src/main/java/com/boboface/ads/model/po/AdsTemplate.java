package com.boboface.ads.model.po;

import java.util.ArrayList;
import java.util.List;

import net.zowbman.base.util.BaseUtil;

/**
 * 
 * Title:adsTemplate
 * Description:ads模板
 * 			//tpl.setting
			//default.map.online
			//default.map.pre
			//default.map.test
			//default.map.dev
 * @author    zwb
 * @date      2016年8月19日 下午3:30:07
 *
 */
public class AdsTemplate {
	
	/**
	 * 获取模板文件对象
	 * @param appId
	 * @return
	 */
	public static List<TBobofaceAdsContent> getAdsTemplate(Integer appId){
		String[] templates = new String[]{"tpl.setting","default.map.online","default.map.pre","default.map.test","default.map.dev"};
		List<TBobofaceAdsContent> tBobofaceAdsContents = new ArrayList<TBobofaceAdsContent>();
		for(int i = 0; i < templates.length; i++){
			TBobofaceAdsContent tBobofaceAdsContent = new TBobofaceAdsContent();
			tBobofaceAdsContent.setTitle(templates[i]);
			tBobofaceAdsContent.setAppid(appId);
			tBobofaceAdsContent.setAddtime((int)BaseUtil.currentTimeMillis());
			tBobofaceAdsContents.add(tBobofaceAdsContent);
		}
		return tBobofaceAdsContents;
	}
}
