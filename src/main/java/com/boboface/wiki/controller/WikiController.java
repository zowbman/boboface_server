package com.boboface.wiki.controller;

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

import com.boboface.base.controller.BaseController;
import com.boboface.exception.CustomException;
import com.boboface.wiki.model.po.TBobofaceWikiContent;
import com.boboface.wiki.model.po.TBobofaceWikiTree;
import com.boboface.wiki.model.vo.TBobofaceWikiContentVo;
import com.boboface.wiki.model.vo.TBobofaceWikiTreeVo;
import com.github.pagehelper.PageInfo;

/**
 * 
 * Title:WikiController
 * Description:wiki控制器
 * @author    zwb
 * @date      2016年7月22日 下午11:11:52
 *
 */
@Controller
@RequestMapping("/boboface")
public class WikiController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(WikiController.class);
	
	/**
	 * 测试接口
	 * @return
	 */
	@RequestMapping("/json/v1/wiki/test")
	public @ResponseBody PubRetrunMsg test(){
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * wiki 树数据
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/wiki/treeList")
	public @ResponseBody PubRetrunMsg wikiTreeListV1(){
		Map<String, Object> data = new HashMap<String, Object>();
		List<TBobofaceWikiTree> tBobofaceWikiTrees = iWikiTreeService.findAll();
		data.put("list", tBobofaceWikiTrees);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * wiki 内容
	 * @param wikiTreeId 树id
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/wiki/content/{id}")
	public @ResponseBody PubRetrunMsg wikiContentV1(@PathVariable("id") Integer wikiTreeId){
		Map<String, Object> data = new HashMap<String, Object>();
		List<TBobofaceWikiContent> tBobofaceWikiContents = iWikiContentService.getByWikiTreeId(wikiTreeId);
		if(tBobofaceWikiContents != null && tBobofaceWikiContents.size() > 1){
			logger.info("根据wikiTreeId:"+ wikiTreeId +"查询数据集大于1条,总共:"+ tBobofaceWikiContents.size() +"条");
			data.put("wikiConten", tBobofaceWikiContents.iterator().next());
		}else{
			data.put("wikiConten", null);
		}
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	
	/**
	 * wiki tree rename 重命名
	 * @param newName 新名称
	 * @param wikiTreeId
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/wiki/tree/rename/{id}/{newName}")
	public @ResponseBody PubRetrunMsg wikiTreeRenameV1(@PathVariable("id") Integer wikiTreeId, @PathVariable("newName") String newName){
		if(newName == null || wikiTreeId == null){
			return new PubRetrunMsg(CODE.D200001, "参数错误,newName和wikiTreeId不允许为空");
		}
		TBobofaceWikiTree tBobofaceWikiTree = iWikiTreeService.getById(wikiTreeId);
		if(tBobofaceWikiTree == null){
			return new PubRetrunMsg(CODE.D200002, "非法参数,无法找到wikiTreeId:" + wikiTreeId + "的数据");
		}
		if(tBobofaceWikiTree.getAllowedit() == (byte)0){//不允许修改
			return new PubRetrunMsg(CODE.D200100, "非法请求，wikiTree：" + tBobofaceWikiTree.getTitle() + "不允许进行重命名操作");
		}
		tBobofaceWikiTree.setTitle(newName);
		iWikiTreeService.updateSeletive(tBobofaceWikiTree);
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * wiki 树删除
	 * @param id 资源Id
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/wiki/tree/remove/{id}")
	public @ResponseBody PubRetrunMsg wikiTreeRemoveV1(@PathVariable("id") Integer wikiTreeId){
		if(wikiTreeId == null){
			return new PubRetrunMsg(CODE.D200001, "参数错误,wikiTreeId不允许为空");
		}
		TBobofaceWikiTree tBobofaceWikiTree = iWikiTreeService.getById(wikiTreeId);
		if(tBobofaceWikiTree == null){
			return new PubRetrunMsg(CODE.D200002, "非法参数,无法找到wikiTreeId:" + wikiTreeId + "的数据");
		}
		if(tBobofaceWikiTree.getAllowdelete() == (byte)0){//不允许删除
			return new PubRetrunMsg(CODE.D200101, "非法请求,wikiTree:" + tBobofaceWikiTree.getTitle() + "不允许删除节点操作");
		}
		iWikiContentService.deleteByWikiTreeId(wikiTreeId);
		iWikiTreeService.delete(wikiTreeId);
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * wiki 树节点添加
	 * @param parenId 父节点
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/wiki/tree/add/{parenId}")
	public @ResponseBody PubRetrunMsg wikiTreeAddV1(@PathVariable("parenId") Integer parenId){
		Map<String, Object> data = new HashMap<String, Object>();
		if(parenId == null){
			return new PubRetrunMsg(CODE.D200001, "参数错误,parenId不允许为空");
		}
		TBobofaceWikiTree tBobofaceWikiTree = iWikiTreeService.getById(parenId);
		if(tBobofaceWikiTree == null){
			return new PubRetrunMsg(CODE.D200002, "非法参数,无法找wikiTreeId:" + parenId + "的数据");
		}
		if(tBobofaceWikiTree.getAllowadd() == (byte)0){//不允许修改
			return new PubRetrunMsg(CODE.D200102, "非法请求,wikiTree:" + tBobofaceWikiTree.getTitle() + "不允许添加子级节点操作");
		}
		tBobofaceWikiTree = new TBobofaceWikiTree();
		tBobofaceWikiTree.setTitle("new node");
		tBobofaceWikiTree.setParentid(parenId);
		tBobofaceWikiTree.setAddtime((int)BaseUtil.currentTimeMillis());
		tBobofaceWikiTree.setAllowedit((byte)1);
		tBobofaceWikiTree.setAllowdelete((byte)1);
		iWikiTreeService.saveSeletive(tBobofaceWikiTree);
		tBobofaceWikiTree.setTitle(tBobofaceWikiTree.getTitle() + "_" + tBobofaceWikiTree.getId());
		iWikiTreeService.updateSeletive(tBobofaceWikiTree);
		data.put("wikiTree", tBobofaceWikiTree);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * 根据id查询wiki树节点
	 * @param wikiTreeId
	 * @return PubRetrunMsg
	 */
	@RequestMapping("/json/v1/wiki/tree/{id}")
	public @ResponseBody PubRetrunMsg wikiTreeV1(@PathVariable("id") Integer wikiTreeId){
		Map<String, Object> data = new HashMap<String, Object>();
		if(wikiTreeId == null){
			return new PubRetrunMsg(CODE.D200001, "参数错误,wikiTreeId不允许为空");
		}
		TBobofaceWikiTree tBobofaceWikiTree = iWikiTreeService.getById(wikiTreeId);
		data.put("wikiTree", tBobofaceWikiTree);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * wiki树 修改权限
	 * @param tBobofaceWikiTreeVo 包装类
	 * @return PubRetrunMsg
	 */
	@RequestMapping(value = "/json/v1/wiki/tree/savePrivilege", method=RequestMethod.POST)
	public @ResponseBody PubRetrunMsg wikiTreeSavePrivilegeV1(TBobofaceWikiTreeVo tBobofaceWikiTreeVo){
		TBobofaceWikiTree tBobofaceWikiTree = iWikiTreeService.getById(tBobofaceWikiTreeVo.getWikiTree().getId());
		tBobofaceWikiTree.setAllowadd(tBobofaceWikiTreeVo.getWikiTree().getAllowadd());
		tBobofaceWikiTree.setAllowedit(tBobofaceWikiTreeVo.getWikiTree().getAllowedit());
		tBobofaceWikiTree.setAllowdelete(tBobofaceWikiTreeVo.getWikiTree().getAllowdelete());
		iWikiTreeService.updateSeletive(tBobofaceWikiTree);
		return new PubRetrunMsg(CODE.D100000);
	}
	
	/**
	 * wiki树列表（非页）
	 * @param pageNum 页码
	 * @param pageSize 当前页数量
	 * @param request 
	 * @return PubRetrunMsg
	 * @throws CustomException
	 */
	@RequestMapping("/json/v1/wiki/treeListByPage")
	public @ResponseBody PubRetrunMsg wikiTreeListByPageV1(@RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "15") Integer pageSize, HttpServletRequest request) throws CustomException{
		Map<String, Object> data = new HashMap<String, Object>();
		PageBean pageBean = new PageBean(pageNum, pageSize, "id", OrderStyleEnum.DESC);
		List<TBobofaceWikiTree> tBobofaceWikiTrees = iWikiTreeService.findAllByPageBean(pageBean);
		PageInfo<TBobofaceWikiTree> pageInfo = new PageInfo<TBobofaceWikiTree>(tBobofaceWikiTrees);
		PageInfoCustom pageInfoCustom = new PageInfoCustom(pageInfo);
		data.put("list", tBobofaceWikiTrees);
		data.put("pageInfo", pageInfoCustom);
		data.put("pageUrl", PageHelper.pageUrl(request));
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * wiki内容 保存
	 * @param tBobofaceWikiContentVo
	 * @return PubRetrunMsg
	 */
	@RequestMapping(value = "/json/v1/wiki/content/save", method=RequestMethod.POST)
	public @ResponseBody PubRetrunMsg wikiContentSaveV1(TBobofaceWikiContentVo tBobofaceWikiContentVo){
		Map<String, Object> data = new HashMap<String, Object>();
		List<TBobofaceWikiContent> tBobofaceWikiContents = iWikiContentService.getByWikiTreeId(tBobofaceWikiContentVo.getWikiContent().getWikitreeid());
		if(tBobofaceWikiContents.size() > 1)
			logger.info("根据wikiTreeId:"+ tBobofaceWikiContentVo.getWikiContent().getId() +"查询数据集大于1条,总共:"+ tBobofaceWikiContents.size() +"条");
		TBobofaceWikiContent tBobofaceWikiContent = tBobofaceWikiContents == null || tBobofaceWikiContents.size() != 1 ? null : tBobofaceWikiContents.iterator().next();
		if(tBobofaceWikiContent == null){//新增
			tBobofaceWikiContent = new TBobofaceWikiContent();
			tBobofaceWikiContent.setWikitreeid(tBobofaceWikiContentVo.getWikiContent().getWikitreeid());
			tBobofaceWikiContent.setContent(tBobofaceWikiContentVo.getWikiContent().getContent());
			tBobofaceWikiContent.setAddtime((int)BaseUtil.currentTimeMillis());
			iWikiContentService.saveSeletive(tBobofaceWikiContent);
		}else{//修改
			tBobofaceWikiContent.setContent(tBobofaceWikiContentVo.getWikiContent().getContent());
			iWikiContentService.updateSeletive(tBobofaceWikiContent);
		}
		return new PubRetrunMsg(CODE.D100000);
	}
}
