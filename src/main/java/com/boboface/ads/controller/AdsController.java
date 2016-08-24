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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boboface.ads.model.po.TBobofaceAdsContent;
import com.boboface.ads.model.po.TBobofaceAdsProject;
import com.boboface.ads.model.vo.TBobofaceAdsContentVo;
import com.boboface.ads.model.vo.TBobofaceAdsProjectBuildVo;
import com.boboface.ads.scriptshell.OrdinaryProject;
import com.boboface.ads.scriptshell.ProjectBuild;
import com.boboface.ads.scriptshell.ProjectBuildVo;
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
				if(serviceTree.getParentid() == null){//boboface 根节点
					Map<String, Object> rootMap = new HashMap<String, Object>();
					rootMap.put("id", 0);
					rootMap.put("title", "boboface");
					rootMap.put("parentid", null);
					rootMap.put("isParent", true);
					rootMap.put("open", true);
					map.put("parentid", 0);
					listMap.add(rootMap);
				}else{
					map.put("parentid", serviceTree.getParentid());
				}
				map.put("id", serviceTree.getId());
				map.put("title", serviceTree.getTitle());
				map.put("isParent", true);//全部为父节点
				map.put("open", true);
				listMap.add(map);
				if(adsProjects != null){
					for (TBobofaceAdsProject adsProject : adsProjects) {
						if(adsProject.getServicetreeid().equals(serviceTree.getId())){
							map = new HashMap<String, Object>();
							map.put("id", adsProject.getId());
							map.put("title", adsProject.getAppname());
							map.put("parentid", serviceTree.getId());
							//map.put("allowadd", 1);
							listMap.add(map);
							//获取模板文件
							List<TBobofaceAdsContent> adsTemplates = iAdsContentService.getByAppId(adsProject.getId());
							for (TBobofaceAdsContent adsTemplate : adsTemplates) {
								map = new HashMap<String, Object>();
								map.put("id", adsTemplate.getId());
								map.put("title", adsTemplate.getTitle());
								map.put("parentid", adsProject.getId());
								listMap.add(map);
							}
						}
					}
				}
			}
		}
		data.put("list", listMap);
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * 获取ads配置模板
	 * @param appId
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/ads/content/{id}")
	public @ResponseBody PubRetrunMsg adsContentV1(@PathVariable("id") Integer appId){
		Map<String, Object> data = new HashMap<String, Object>();
		TBobofaceAdsContent tBobofaceAdsContent = iAdsContentService.getById(appId);
		data.put("adsContent", tBobofaceAdsContent);
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * 获取ads项目列表
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
	
	/**
	 * ads 项目模板添加
	 * @return
	 */
	@RequestMapping(value = "/json/v1/ads/projectTemplateAdd/{id}", method=RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsProjectTemplateAddV1(@PathVariable("id") Integer appId){
		return new PubRetrunMsg(CODE._100000, null);
	}
	
	/**
	 * ads 项目模板保存
	 * @param tBobofaceAdsContentVo
	 * @return
	 * @throws CustomException 
	 */
	@RequestMapping(value = "/json/v1/ads/projectTemplate/save", method = RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsProjectTemplateSaveV1(TBobofaceAdsContentVo tBobofaceAdsContentVo) throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		TBobofaceAdsContent adsTemplate = iAdsContentService.getById(tBobofaceAdsContentVo.getAdsTemplate().getId());
		if(adsTemplate == null){
			data.put("msg", "找不到所修改配置文件，如正常操作出现的问题，请联系研发人员，保存失败");
			logger.error("找不到所修改配置文件，如正常操作出现的问题，请联系研发人员");
			return new PubRetrunMsg(CODE._200000, data);
		}
		adsTemplate.setContent(tBobofaceAdsContentVo.getAdsTemplate().getContent());
		iAdsContentService.updateSeletive(adsTemplate);
		data.put("msg", "模板文件保存成功");
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * 获取ads 项目信息
	 * @param appId 项目id
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/ads/project/{id}")
	public @ResponseBody PubRetrunMsg adsProjectV1(@PathVariable("id") Integer appId){
		Map<String, Object> data = new HashMap<String, Object>();
		if(appId == null){
			logger.info("参数错误（appId：" + appId + "）");
			return new PubRetrunMsg(CODE._200001, null);
		}
		TBobofaceAdsProject tBobofaceAdsProject = iAdsProjectService.getById(appId);
		data.put("adsProject", tBobofaceAdsProject);
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * ads 项目部署
	 * @param tBobofaceAdsProjectBuildVo
	 * @return
	 */
	@RequestMapping(value = "/json/v1/ads/projectBuild", method = {RequestMethod.POST})
	public @ResponseBody PubRetrunMsg adsProjectBuildV1(TBobofaceAdsProjectBuildVo tBobofaceAdsProjectBuildVo){
		Map<String, Object> data = new HashMap<String, Object>();
		TBobofaceAdsProject adsProject = iAdsProjectService.getById(tBobofaceAdsProjectBuildVo.getAppId());
		if(adsProject == null){
			logger.info("参数错误,不存在发布项目");
			data.put("msg", "不存在发布项目");
			return new PubRetrunMsg(CODE._200001, data);
		}
		//项目部署
		ProjectBuild projectBuild = new OrdinaryProject();
		//如果tag为空，则发布branch
		String targetCode = tBobofaceAdsProjectBuildVo.getBranchTag() != null ? tBobofaceAdsProjectBuildVo.getBranchTag() : tBobofaceAdsProjectBuildVo.getAppBranch();
		ProjectBuildVo projectBuildVo = new ProjectBuildVo(
				adsProject.getAppname(),
				"git@github.com:zowbman/boboface_server.git", targetCode, adsProject.getStoragepath());
		
		String buildResult = projectBuild.prepareProjectBuildTemplage(projectBuildVo);
		data.put("buildResult", buildResult);
		return new PubRetrunMsg(CODE._100000, data);
	}
}
