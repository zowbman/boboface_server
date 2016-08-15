package com.boboface.wiki.service;

import java.util.List;

import com.boboface.base.service.IBaseService;
import com.boboface.wiki.model.po.TBobofaceWikiContent;

/**
 * 
 * Title:IWikiContentService
 * Description:wiki content service 接口
 * @author    zwb
 * @date      2016年7月25日 下午4:06:59
 *
 */
public interface IWikiContentService extends IBaseService<TBobofaceWikiContent> {
	
	/**
	 * 根据wikiTreeId获取wiki内容
	 * @param wikiTreeId
	 * @return List<TBobofaceWikiContent>
	 */
	List<TBobofaceWikiContent> getByWikiTreeId(Integer wikiTreeId);
	
	/**
	 * 根据wikiTreeId删除wiki内容
	 * @param wikiTreeId
	 */
	void deleteByWikiTreeId(Integer wikiTreeId);
}
