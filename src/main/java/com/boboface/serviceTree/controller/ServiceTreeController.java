package com.boboface.serviceTree.controller;

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
import net.zowbman.base.util.BaseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boboface.ads.controller.AdsController;
import com.boboface.ads.model.po.AdsUnitl;
import com.boboface.ads.model.po.TBobofaceAdsContent;
import com.boboface.ads.model.po.TBobofaceAdsProject;
import com.boboface.ads.model.po.TBobofaceAdsUntilscript;
import com.boboface.ads.model.vo.TBobofaceAdsProjectVo;
import com.boboface.base.controller.BaseController;
import com.boboface.exception.CustomException;
import com.boboface.serviceTree.model.po.TBobofaceServiceTree;
import com.boboface.serviceTree.model.po.TBobofaceServiceTreeCustom;
import com.boboface.serviceTree.model.vo.TBobofaceServiceTreeVo;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/boboface")
public class ServiceTreeController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AdsController.class);
	
	/**
	 * ads tree listData
	 * @return PubRetrunMsg
	 * @throws CustomException 
	 */
	@RequestMapping("/json/v1/serviceTree/list")
	public @ResponseBody PubRetrunMsg serviceTreeListV1() throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		List<TBobofaceServiceTree> tBobofaceServiceTrees = iServiceTreeService.findAllBySort(OrderStyleEnum.ACS);
		//根节点
		List<TBobofaceServiceTree> newServiceTrees = new ArrayList<TBobofaceServiceTree>();
		TBobofaceServiceTree newServiceTree = new TBobofaceServiceTree();
		newServiceTree.setId(0);
		newServiceTree.setTitle("boboface");
		newServiceTree.setAllowadd((byte)0);
		newServiceTree.setAllowedit((byte)0);
		newServiceTree.setAllowdelete((byte)0);
		tBobofaceServiceTrees.add(newServiceTree);
		for (TBobofaceServiceTree serviceTree : tBobofaceServiceTrees) {
			if(serviceTree.getParentid() == null){
				serviceTree.setParentid(0);
			}
			newServiceTrees.add(serviceTree);
		}
		data.put("list", tBobofaceServiceTrees);
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * 获取父级树数据
	 * @param serviceTreeType 新建业务树类型
	 * @return PubRetrunMsg
	 * @throws CustomException 
	 */
	@RequestMapping("/json/v1/serviceTree/parentServiceTree/{serviceTreeType}")
	public @ResponseBody PubRetrunMsg parentServiceTreeV1(@PathVariable("serviceTreeType") String serviceTreeType) throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		if(!BaseUtil.isHave(serviceTreeType, "second","third")){
			logger.error("参数错误");
			return new PubRetrunMsg(CODE._200001, null); 
		}
		TBobofaceServiceTree rootServiceTree = null;
		List<TBobofaceServiceTree> childrenServiceTrees = new ArrayList<TBobofaceServiceTree>();
		if("second".equals(serviceTreeType)){
			rootServiceTree = iServiceTreeService.findRootServiceTree();
			if(rootServiceTree == null)
				throw new CustomException("业务树无根节点，请联系研发人员");
		}else if("third".equals(serviceTreeType)){
			//查询根节点id
			rootServiceTree = iServiceTreeService.findRootServiceTree();
			if(rootServiceTree == null)
				throw new CustomException("业务树无根节点，请联系研发人员");
			//根据根节点id查询
			childrenServiceTrees = iServiceTreeService.findChildrenServiceTree(rootServiceTree.getId());
		}
		data.put("rootServiceTree", rootServiceTree);
		data.put("childrenServiceTrees", childrenServiceTrees);
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * 保存业务树
	 * @param tBobofaceServiceTreeVo 业务树包装类
	 * @return PubRetrunMsg
	 */
	@RequestMapping(value = "/json/v1/serviceTree/add", method=RequestMethod.POST)
	public @ResponseBody PubRetrunMsg serviceTreeAddV1(TBobofaceServiceTreeVo tBobofaceServiceTreeVo){
		Map<String, Object> data = new HashMap<String, Object>();
		TBobofaceServiceTree tBobofaceServiceTree = new TBobofaceServiceTree();
		tBobofaceServiceTree.setTitle(tBobofaceServiceTreeVo.getServiceTree().getTitle());//标题
		tBobofaceServiceTree.setParentid(tBobofaceServiceTreeVo.getServiceTree().getParentid());//父业务树
		Integer total = iServiceTreeService.getTotal(new TBobofaceServiceTree());
		tBobofaceServiceTree.setSort(total + 1);
		tBobofaceServiceTree.setAddtime((int)BaseUtil.currentTimeMillis());
		tBobofaceServiceTree.setAllowedit((byte)1);//允许编辑
		tBobofaceServiceTree.setAllowdelete((byte)1);//允许删除
		iServiceTreeService.saveSeletive(tBobofaceServiceTree);
		data.put("msg", "业务树保存成功");
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * sts tree by page
	 * @param pageNum 页码
	 * @param pageSize 一页数据量
	 * @param request 
	 * @return PubRetrunMsg
	 * @throws CustomException
	 */
	@RequestMapping("/json/v1/serviceTree/treeListByPage")
	public @ResponseBody PubRetrunMsg serviceTreeListByPageV1(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "15") Integer pageSize, HttpServletRequest request) throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		PageBean pageBean = new PageBean(pageNum, pageSize, "id", OrderStyleEnum.DESC);
		List<TBobofaceServiceTree> tBobofaceServiceTrees = iServiceTreeService.findAllByPageBean(pageBean);
		PageInfo<TBobofaceServiceTree> pageInfo = new PageInfo<TBobofaceServiceTree>(tBobofaceServiceTrees);
		PageInfoCustom pageInfoCustom = new PageInfoCustom(pageInfo);
		data.put("list", tBobofaceServiceTrees);
		data.put("pageInfo", pageInfoCustom);
		data.put("pageUrl", PageHelper.pageUrl(request));
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * sts tree 根据id查询serviceTree 节点
	 * @param wikiTreeId
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/serviceTree/{id}")
	public @ResponseBody PubRetrunMsg serviceTreeV1(@PathVariable("id") Integer serviceTreeId){
		Map<String, Object> data = new HashMap<String, Object>();
		if(serviceTreeId == null){
			logger.info("参数错误（serviceTreeId" + serviceTreeId);
			return new PubRetrunMsg(CODE._200001, null);
		}
		TBobofaceServiceTree tBobofaceServiceTree = iServiceTreeService.getById(serviceTreeId);
		data.put("serviceTree", tBobofaceServiceTree);
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * sts tree 修改权限
	 * @param tBobofaceServiceTreeVo 包装类
	 * @return PubRetrunMsg
	 */
	@RequestMapping(value = "/json/v1/serviceTree/savePrivilege", method=RequestMethod.POST)
	public @ResponseBody PubRetrunMsg serviceTreeSavePrivilegeV1(TBobofaceServiceTreeVo tBobofaceServiceTreeVo){
		TBobofaceServiceTree tBobofaceServiceTree = iServiceTreeService.getById(tBobofaceServiceTreeVo.getServiceTree().getId());
		//tBobofaceServiceTree.setAllowadd(tBobofaceServiceTreeVo.getServiceTree().getAllowadd());
		tBobofaceServiceTree.setAllowedit(tBobofaceServiceTreeVo.getServiceTree().getAllowedit());
		tBobofaceServiceTree.setAllowdelete(tBobofaceServiceTreeVo.getServiceTree().getAllowdelete());
		iServiceTreeService.updateSeletive(tBobofaceServiceTree);
		return new PubRetrunMsg(CODE._100000, null);
	}
	
	/**
	 * sts tree remove 删除
	 * @param id 资源Id
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/serviceTree/remove/{id}")
	public @ResponseBody PubRetrunMsg serviceTreeRemoveV1(@PathVariable("id") Integer serviceTreeId){
		if(serviceTreeId == null){
			logger.info("参数错误（id：" + serviceTreeId);
			return new PubRetrunMsg(CODE._200001, null);
		}
		TBobofaceServiceTree tBobofaceServiceTree = iServiceTreeService.getById(serviceTreeId);
		if(tBobofaceServiceTree == null){
			logger.info("非法参数，无法找serviceTreeId：" + serviceTreeId + "的数据");
			return new PubRetrunMsg(CODE._200001, null);
		}
		if(tBobofaceServiceTree.getAllowdelete() == (byte)0){//不允许删除
			logger.info("非法请求，wikiTree：" + tBobofaceServiceTree.getTitle() + "不允许删除节点操作");
			return new PubRetrunMsg(CODE._200004, null);
		}
		iServiceTreeService.delete(serviceTreeId);
		return new PubRetrunMsg(CODE._100000, null);
	}
	
	/**
	 * service tree rename 重命名
	 * @param newName 新名称
	 * @param wikiTreeId
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/serviceTree/rename/{id}/{newName}")
	public @ResponseBody PubRetrunMsg servicesTreeRenameV1(@PathVariable("id") Integer serviceTreeId, @PathVariable("newName") String newName){
		if(newName == null || serviceTreeId == null){
			logger.info("参数错误（newName：" + newName + ",serviceTreeId" + serviceTreeId);
			return new PubRetrunMsg(CODE._200001, null);
		}
		TBobofaceServiceTree tBobofaceServiceTree = iServiceTreeService.getById(serviceTreeId);
		if(tBobofaceServiceTree == null){
			logger.info("非法参数，无法找serviceTreeId：" + serviceTreeId + "的数据");
			return new PubRetrunMsg(CODE._200001, null);
		}
		if(tBobofaceServiceTree.getAllowedit() == (byte)0){//不允许修改
			logger.info("非法请求，serviceTree：" + tBobofaceServiceTree.getTitle() + "不允许进行重命名操作");
			return new PubRetrunMsg(CODE._200002, null);
		}
		tBobofaceServiceTree.setTitle(newName);
		iServiceTreeService.updateSeletive(tBobofaceServiceTree);
		return new PubRetrunMsg(CODE._100000, null);
	}
	
	/**
	 * ads挂载项目的业务树
	 * @return PubRetrunMsg
	 * @throws CustomException
	 */
	@RequestMapping("/json/v1/serviceTree/adsMountProjectServiceTree")
	public @ResponseBody PubRetrunMsg adsMountProjectServiceTreeListV1() throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		List<TBobofaceServiceTreeCustom> rootServiceTree = iServiceTreeService.findRootServiceTreeCustom();
		if(rootServiceTree == null)
			throw new CustomException("业务树无根节点，请联系研发人员");
		int maxDepth = getMaxDepth(rootServiceTree);
		data.put("maxDepth", maxDepth);
		data.put("rootServiceTree", rootServiceTree.iterator().next());
		return new PubRetrunMsg(CODE._100000, data);
	}
	
	/**
	 * ads挂载项目的业务树深度
	 * @param serviceTrees 业务树
	 * @return int
	 */
	private static int getMaxDepth(List<TBobofaceServiceTreeCustom> serviceTrees){
		if(serviceTrees == null || serviceTrees.size() == 0){
			return 0;
		}else{
			int i = 0;
			for (TBobofaceServiceTreeCustom serviceTree : serviceTrees) {
				i = getMaxDepth(serviceTree.getChildServiceTree());
			}
			return 1 + i;
		}
	}
	
	/**
	 * ads挂载项目
	 * @param tBobofaceAdsProjectVo
	 * @return PubRetrunMsg
	 * @throws CustomException 
	 */
	@RequestMapping(value = "/json/v1/serviceTree/adsMountProject/add", method=RequestMethod.POST)
	public @ResponseBody PubRetrunMsg adsMountProjectAddV1(TBobofaceAdsProjectVo tBobofaceAdsProjectVo) throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		TBobofaceServiceTree serviceTree = iServiceTreeService.getById(tBobofaceAdsProjectVo.getAdsProject().getServicetreeid());
		if(serviceTree == null){
			throw new CustomException("不存在被挂载业务树，刷新重试");
		}else{
			TBobofaceAdsProject adsProject = new TBobofaceAdsProject();
			adsProject.setServicetreeid(serviceTree.getId());//业务树id
			adsProject.setAppname(tBobofaceAdsProjectVo.getAdsProject().getAppname());//项目名称
			adsProject.setIntroduction(tBobofaceAdsProjectVo.getAdsProject().getIntroduction());//简介
			adsProject.setStoragepath(tBobofaceAdsProjectVo.getAdsProject().getStoragepath());//存储路径
			adsProject.setGitpath(tBobofaceAdsProjectVo.getAdsProject().getGitpath());//git地址
			adsProject.setRunuser(tBobofaceAdsProjectVo.getAdsProject().getRunuser());//运行账号
			adsProject.setRungroup(tBobofaceAdsProjectVo.getAdsProject().getRungroup());//账号属组
			adsProject.setAddtime((int)BaseUtil.currentTimeMillis());
			iAdsProjectService.saveSeletive(adsProject);
			serviceTree.setIsmountads((byte)1);
			iServiceTreeService.updateSeletive(serviceTree);
			//新建模板
			List<TBobofaceAdsContent> tBobofaceAdsContents = AdsUnitl.getAdsTemplate(adsProject.getId());
			for (TBobofaceAdsContent tBobofaceAdsContent : tBobofaceAdsContents) {
				iAdsContentService.saveSeletive(tBobofaceAdsContent);
			}
			//新建脚本工具
			List<TBobofaceAdsUntilscript> tBobofaceAdsUntilscripts = AdsUnitl.getAdsUnitlScript(adsProject.getId(), getClass().getResource("/").getPath() + "shell/");
			for (TBobofaceAdsUntilscript tBobofaceAdsUntilscript : tBobofaceAdsUntilscripts) {
				iAdsUnitlScriptService.saveSeletive(tBobofaceAdsUntilscript);
			}
			data.put("msg", adsProject.getAppname() + "项目挂载成功");
		}
		return new PubRetrunMsg(CODE._100000, data);
	}
}
