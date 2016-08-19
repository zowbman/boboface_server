package com.boboface.ads.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.zowbman.base.helper.CodeHelper.CODE;
import net.zowbman.base.helper.PageHelper;
import net.zowbman.base.model.vo.OrderStyleEnum;
import net.zowbman.base.model.vo.PageBean;
import net.zowbman.base.model.vo.PageInfoCustom;
import net.zowbman.base.model.vo.PubRetrunMsg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boboface.ads.model.po.TBobofaceAdsContent;
import com.boboface.ads.model.po.TBobofaceAdsProject;
import com.boboface.base.controller.BaseController;
import com.boboface.exception.CustomException;
import com.boboface.serviceTree.model.po.TBobofaceServiceTree;
import com.github.pagehelper.PageInfo;

/**
 * 
 * Title:AdsController
 * Description:ads控制类
 * @author    zwb
 * @date      2016年8月16日 上午10:35:00
 *
 */
@Controller
@RequestMapping("/boboface")
public class AdsController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AdsController.class);
	
	/**
	 * ads tree listData
	 * @return PubRetrunMsg
	 * @throws CustomException 
	 */
	@RequestMapping("/json/v1/ads/treeList")
	public @ResponseBody PubRetrunMsg adsTreeListV1() throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		//List<TBobofaceServiceTree> tBobofaceServiceTrees = iServiceTreeService.findAllBySortAndIsMountAds(OrderStyleEnum.ACS, (byte)1);
		List<TBobofaceServiceTree> serviceTrees = iServiceTreeService.findAll();
		//获取项目
		List<TBobofaceAdsProject> adsProjects = iAdsProjectService.findAll();
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(serviceTrees != null){
			for (TBobofaceServiceTree serviceTree : serviceTrees) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", serviceTree.getId());
				map.put("title", serviceTree.getTitle());
				map.put("parentid", serviceTree.getParentid());
				listMap.add(map);
				if(adsProjects != null){
					for (TBobofaceAdsProject adsProject : adsProjects) {
						if(adsProject.getServicetreeid().equals(serviceTree.getId())){
							map = new HashMap<String, Object>();
							map.put("id", adsProject.getId());
							map.put("title", adsProject.getAppname());
							map.put("parentid", serviceTree.getId());
							listMap.add(map);
						}
					}
				}
				
			}
		}

		data.put("list", listMap);
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * ads content data
	 * @param adsTreeId
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/ads/content/{id}")
	public @ResponseBody PubRetrunMsg adsContentV1(@PathVariable("id") Integer adsTreeId){
		Map<String, Object> data = new HashMap<String, Object>();
		List<TBobofaceAdsContent> tBobofaceAdsContents = iAdsContentService.getByAdsTreeId(adsTreeId);
		if(tBobofaceAdsContents == null || tBobofaceAdsContents.size() != 1)
			logger.info("根据wikiTreeId:"+ adsTreeId +"查询数据集等于0或大于1条,总共:"+ tBobofaceAdsContents.size() +"条");
		data.put("adsContent", tBobofaceAdsContents == null || tBobofaceAdsContents.size() != 1 ? null : tBobofaceAdsContents.iterator().next());
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * ads project list
	 * @param serviceTreeId 业务树id
	 * @param pageNum 页码
	 * @param pageSize 一页数量
	 * @param request
	 * @return PubRetrunMsg
	 * @throws CustomException
	 */
	@RequestMapping("/json/v1/ads/projectList/{id}")
	public @ResponseBody PubRetrunMsg adsProjectListV1(@PathVariable("id") Integer serviceTreeId, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "15") Integer pageSize, HttpServletRequest request) throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		PageBean pageBean = new PageBean(pageNum, pageSize, "id", OrderStyleEnum.DESC);
		List<TBobofaceAdsProject> adsProjects = iAdsProjectService.findProjectListByPageBeanAndSeviceTreeId(pageBean, serviceTreeId);
		PageInfo<TBobofaceAdsProject> pageInfo = new PageInfo<TBobofaceAdsProject>(adsProjects);
		PageInfoCustom pageInfoCustom = new PageInfoCustom();
		BeanUtils.copyProperties(pageInfo, pageInfoCustom);
		data.put("list", adsProjects);
		data.put("pageInfo", pageInfoCustom);
		data.put("pageUrl", PageHelper.pageUrl(request));
		return new PubRetrunMsg(CODE._100000, data);
	}
}
