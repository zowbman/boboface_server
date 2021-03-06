package com.boboface.ads.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boboface.ads.model.po.TBobofaceAdsContent;
import com.boboface.ads.model.po.TBobofaceAdsProject;
import com.boboface.ads.model.po.TBobofaceAdsUntilscript;
import com.boboface.ads.model.vo.AdsProjectBuildContent;
import com.boboface.ads.model.vo.TBobofaceAdsContentVo;
import com.boboface.ads.model.vo.TBobofaceAdsProjectBuildVo;
import com.boboface.ads.model.vo.TBobofaceAdsProjectVo;
import com.boboface.ads.model.vo.TBobofaceAdsUnitilscriptVo;
import com.boboface.ads.scriptshell.OrdinaryProject;
import com.boboface.ads.scriptshell.ProjectBuild;
import com.boboface.ads.scriptshell.ProjectBuildVo;
import com.boboface.base.controller.BaseController;
import com.boboface.base.helper.PageHelper;
import com.boboface.base.helper.CodeHelper.CODE;
import com.boboface.base.model.vo.EnvEnum;
import com.boboface.base.model.vo.OrderStyleEnum;
import com.boboface.base.model.vo.PageBean;
import com.boboface.base.model.vo.PageInfoCustom;
import com.boboface.base.model.vo.PubRetrunMsg;
import com.boboface.base.util.BaseUtil;
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
	@RequestMapping("/json/v1/ads/treeList/{type}")
	public @ResponseBody PubRetrunMsg adsTreeListV1(@PathVariable("type") String type) throws CustomException{
		if(!BaseUtil.isHave(type, "template","unitlScript","server")){
			return new PubRetrunMsg(CODE.D200001,"参数错误,参数:" + type + ",非template或unitlScript");
		}
		
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
							if("template".equals(type)){//获取模板文件
								List<TBobofaceAdsContent> adsTemplates = iAdsContentService.getByAppId(adsProject.getId());
								for (TBobofaceAdsContent adsTemplate : adsTemplates) {
									map = new HashMap<String, Object>();
									map.put("id", adsTemplate.getId());
									map.put("title", adsTemplate.getTitle());
									map.put("parentid", adsProject.getId());
									listMap.add(map);
								}
							}else if("unitlScript".equals(type)){//工具脚本
								List<TBobofaceAdsUntilscript> adsUnitlScripts = iAdsUnitlScriptService.getByAppId(adsProject.getId());
								for (TBobofaceAdsUntilscript adsUnitlScript : adsUnitlScripts) {
									map = new HashMap<String, Object>();
									map.put("id", adsUnitlScript.getId());
									map.put("title", adsUnitlScript.getTitle());
									map.put("parentid", adsProject.getId());
									listMap.add(map);
								}
							}
						}
					}
				}
			}
		}
		data.put("list", listMap);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * 获取ads配置模板
	 * @param appId 项目id
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/ads/content/{id}/{type}")
	public @ResponseBody PubRetrunMsg adsContentV1(@PathVariable("id") Integer appId, @PathVariable("type") String type){
		if(!BaseUtil.isHave(type, "template","unitlScript")){
			return new PubRetrunMsg(CODE.D200001, "参数错误,参数:" + type + "，非template或unitlScript");
		}
		Map<String, Object> data = new HashMap<String, Object>();
		if("template".equals(type)){//获取模板文件
			TBobofaceAdsContent tBobofaceAdsContent = iAdsContentService.getById(appId);
			data.put("content", tBobofaceAdsContent);
		}else if("unitlScript".equals(type)){//工具脚本
			TBobofaceAdsUntilscript tBobofaceAdsUntilscript = iAdsUnitlScriptService.getById(appId);
			data.put("content", tBobofaceAdsUntilscript);
		}
		return new PubRetrunMsg(CODE.D100000, data);
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
		List<TBobofaceAdsProject> adsProjects = iAdsProjectService.findProjectListByPageBeanAndServiceTreeId(pageBean, serviceTreeId);
		PageInfo<TBobofaceAdsProject> pageInfo = new PageInfo<TBobofaceAdsProject>(adsProjects);
		PageInfoCustom pageInfoCustom = new PageInfoCustom(pageInfo);
		data.put("list", adsProjects);
		data.put("pageInfo", pageInfoCustom);
		data.put("pageUrl", PageHelper.pageUrl(request));
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * 获取ads项目详细
	 * @param appId 项目id
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/ads/projectDetail/{id}")
	public @ResponseBody PubRetrunMsg adsProjectDetailV1(@PathVariable("id") Integer appId){
		Map<String, Object> data = new HashMap<String, Object>();
		TBobofaceAdsProject adsProject = iAdsProjectService.getById(appId);
		data.put("adsProject", adsProject);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * 修改ads项目配置
	 * @param adsProjectVo 参数包装类
	 * @return PubRetrunMsg
	 * @throws CustomException 
	 */
	@RequestMapping(value = "/json/v1/ads/projectEdit/{id}", method = RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsProjectEditV1(@PathVariable("id") Integer appId, TBobofaceAdsProjectVo adsProjectVo) throws CustomException{
		TBobofaceAdsProject adsProject = iAdsProjectService.getById(appId);
		if(adsProject == null)
			return new PubRetrunMsg(CODE.D200002, "非法参数，无法找到appId:" + appId + "的项目");
		
		if(!adsProject.getStoragepath().equals(adsProjectVo.getAdsProject().getStoragepath())){//如果存储路径非原本的，去查询是否已存在路径
			boolean isExist = iAdsProjectService.findProjectStoragepathIsExist(adsProjectVo.getAdsProject().getStoragepath());
			if(isExist)
				return new PubRetrunMsg(CODE.D200200);
		}
		
		adsProject.setAppname(adsProjectVo.getAdsProject().getAppname());
		adsProject.setGitpath(adsProjectVo.getAdsProject().getGitpath());
		adsProject.setIntroduction(adsProjectVo.getAdsProject().getIntroduction());
		adsProject.setRungroup(adsProjectVo.getAdsProject().getRungroup());
		adsProject.setRunuser(adsProjectVo.getAdsProject().getRunuser());
		adsProject.setServicetreeid(adsProjectVo.getAdsProject().getServicetreeid());
		adsProject.setStoragepath(adsProjectVo.getAdsProject().getStoragepath());
		iAdsProjectService.updateSeletive(adsProject);
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * ads项目删除
	 * @param ids 项目ids
	 * @return PubRetrunMsg
	 * @throws CustomException 
	 */
	@RequestMapping(value = "/json/v1/ads/projectDelete", method = RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsProjectDeleteV1(@RequestParam("ids[]") Integer[] ids) throws CustomException{
		if(ids == null)
			return new PubRetrunMsg(CODE.D200001, "参数错误,ids不允许为空");
		iAdsProjectService.deleteByIds(ids);
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * ads 项目模板添加
	 * @param appId 项目id
	 * @return
	 */
	@RequestMapping(value = "/json/v1/ads/projectTemplateAdd/{id}", method = RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsProjectTemplateAddV1(@PathVariable("id") Integer appId){
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * ads 项目模板保存
	 * @param tBobofaceAdsContentVo
	 * @return PubRetrunMsg
	 */
	@RequestMapping(value = "/json/v1/ads/projectTemplate/save", method = RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsProjectTemplateSaveV1(TBobofaceAdsContentVo tBobofaceAdsContentVo){
		TBobofaceAdsContent adsTemplate = iAdsContentService.getById(tBobofaceAdsContentVo.getAdsTemplate().getId());
		if(adsTemplate == null){
			return new PubRetrunMsg(CODE.D200002,"非法参数,无法找到templateId:" + tBobofaceAdsContentVo.getAdsTemplate().getId() + "模板文件的数据");
		}
		adsTemplate.setContent(tBobofaceAdsContentVo.getAdsTemplate().getContent());
		iAdsContentService.updateSeletive(adsTemplate);
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * ads 项目工具脚本文件保存
	 * @param adsUnitilscriptVo
	 * @return PubRetrunMsg
	 */
	@RequestMapping(value = "/json/v1/ads/unitlScript/save", method = RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsUnitlScriptSaveV1(TBobofaceAdsUnitilscriptVo adsUnitilscriptVo){
		TBobofaceAdsUntilscript adsUntilscript =iAdsUnitlScriptService.getById(adsUnitilscriptVo.getAdsUntilscript().getId());
		if(adsUntilscript == null){
			return new PubRetrunMsg(CODE.D200002, "非法参数,无法找到untilscriptId:" + adsUnitilscriptVo.getAdsUntilscript().getId() + "脚本文件的数据");
		}
		adsUntilscript.setContent(adsUnitilscriptVo.getAdsUntilscript().getContent());
		iAdsUnitlScriptService.updateSeletive(adsUntilscript);
		return new PubRetrunMsg(CODE.D100000);
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
			return new PubRetrunMsg(CODE.D200001, "参数错误,appId不允许为空");
		}
		TBobofaceAdsProject tBobofaceAdsProject = iAdsProjectService.getById(appId);
		data.put("adsProject", tBobofaceAdsProject);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * 获取 ads 项目部署 机器列表
	 * @param serviceTreeId 业务树id
	 * @param pageNum 页码
	 * @param pageSize 当前页数量
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping("/json/v1/ads/projectServerList/{id}")
	public @ResponseBody PubRetrunMsg adsProjectServerListV1(@PathVariable("id") Integer serviceTreeId, @RequestParam(defaultValue = "1") Integer pageNum, 
			@RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest request) throws CustomException{
		PageBean pageBean = new PageBean(pageNum, pageSize, null, null);
		Map<String, Object> data = new HashMap<String, Object>();
		PageInfoCustom pageInfoCustom = iAdsServerService.findServerByPageBeanAndServiceTreeId(pageBean, serviceTreeId);
		data.put("list", pageInfoCustom.getRecordList());
		data.put("pageInfo", pageInfoCustom);
		data.put("pageUrl", PageHelper.pageUrl(request));
		return new PubRetrunMsg(CODE.D100000, data);
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
			return new PubRetrunMsg(CODE.D200002, "非法参数,无法找到appId:" + tBobofaceAdsProjectBuildVo.getAppId() + "的项目");
		}
		//项目部署
		ProjectBuild projectBuild = new OrdinaryProject();
		//如果tag为空，则发布branch
		String targetCode = tBobofaceAdsProjectBuildVo.getBranchTag() != null ? tBobofaceAdsProjectBuildVo.getBranchTag() : tBobofaceAdsProjectBuildVo.getAppBranch();
		List<TBobofaceAdsUntilscript> adsUnitlScripts = iAdsUnitlScriptService.getByAppId(adsProject.getId());//工具脚本
		AdsProjectBuildContent adsContent = iAdsContentService.getByAppIdAndEnv(adsProject.getId(), EnvEnum.online);//项目模板文件
		//准备发布参数
		ProjectBuildVo projectBuildVo = new ProjectBuildVo(
				adsProject.getId(),//appId
				adsProject.getAppname(),//appName
				adsProject.getGitpath(),//gitPath
				tBobofaceAdsProjectBuildVo.getIp(),//ip
				targetCode,//code
				adsProject.getStoragepath(),//storagepath
				adsProject.getRunuser(),//owner
				adsProject.getRungroup(),//ownerGroup
				adsContent, //模板文件
				adsUnitlScripts);//工具脚本
		String buildResult = projectBuild.prepareProjectBuildTemplage(projectBuildVo);
		data.put("buildResult", buildResult);
		return new PubRetrunMsg(CODE.D100000, data);
	}
}
