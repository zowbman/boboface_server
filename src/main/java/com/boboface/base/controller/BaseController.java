package com.boboface.base.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boboface.wiki.service.IWikiContentService;
import com.boboface.wiki.service.IWikiTreeService;

/**
 * 
 * Title:BaseController
 * Description:基础公共控制器类
 * @author    zwb
 * @date      2016年7月25日 下午12:13:27
 *
 */
@Component
public class BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	 * wiki tree service接口
	 */
	@Autowired
	protected IWikiTreeService iWikiTreeService;
	
	/**
	 * wiki content service接口
	 */
	@Autowired
	protected IWikiContentService iWikiContentService;
}
