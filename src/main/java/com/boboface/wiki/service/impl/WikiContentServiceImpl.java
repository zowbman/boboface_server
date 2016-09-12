package com.boboface.wiki.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.boboface.base.service.impl.BaseServiceImpl;
import com.boboface.wiki.model.po.TBobofaceWikiContent;
import com.boboface.wiki.service.IWikiContentService;

/**
 * 
 * Title:WikiContentServiceImpl
 * Description:wiki content service 实现类
 * @author    zwb
 * @date      2016年7月25日 下午4:08:07
 *
 */
@Service
public class WikiContentServiceImpl extends BaseServiceImpl<TBobofaceWikiContent> implements IWikiContentService {
	
	private static Logger logger = LoggerFactory.getLogger(WikiContentServiceImpl.class);

	@Override
	public List<TBobofaceWikiContent> getByWikiTreeId(Integer wikiTreeId) {
		List<TBobofaceWikiContent> wikiContents = new ArrayList<TBobofaceWikiContent>();
		if(wikiTreeId == null){
			logger.error("wikiTreeId 不允许为null");
			return wikiContents;
		}
		Example example = new Example(TBobofaceWikiContent.class);
		example.createCriteria().andEqualTo("wikitreeid", wikiTreeId);
		wikiContents = tBobofaceWikiContentMapper.selectByExample(example);
		return wikiContents;
	}

	@Override
	public void deleteByWikiTreeId(Integer wikiTreeId) {
		if(wikiTreeId == null){
			logger.error("wikiTreeId 不允许为null");
		}
		Example example = new Example(TBobofaceWikiContent.class);
		example.createCriteria().andEqualTo("wikitreeid", wikiTreeId);
		tBobofaceWikiContentMapper.deleteByExample(example);
	}
}
