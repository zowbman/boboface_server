package com.boboface.ads.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zowbman.base.helper.CodeHelper.CODE;
import net.zowbman.base.model.vo.PubRetrunMsg;
import net.zowbman.base.util.BaseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.boboface.ads.model.po.TBobofaceAdsServer;
import com.boboface.ads.model.po.TBobofaceAdsServerCustom;
import com.boboface.base.controller.BaseController;
import com.boboface.dll.aliyun.AliyunAcs;
import com.boboface.dll.aliyun.AliyunClient;
import com.boboface.serviceTree.model.po.TBobofaceServiceTree;

/**
 * 
 * Title:ServerController
 * Description:服务器控制类
 * @author    zwb
 * @date      2016年9月2日 上午10:29:43
 *
 */
@Controller
@RequestMapping("/boboface")
public class ServerController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ServerController.class);
	
	/**
	 * 服务器列表
	 * @return
	 */
	@RequestMapping("/json/v1/server/list")
	public @ResponseBody PubRetrunMsg serverListV1(Integer serverTreeId){
		Map<String, Object> data = new HashMap<String, Object>();
		List<TBobofaceAdsServerCustom> adsServers = iAdsServerService.findAllServer();//获取已挂载实例
		List<TBobofaceAdsServerCustom> tempServers;
		AliyunClient aliyunClient = new AliyunAcs();
		List<Instance> instances = aliyunClient.findAllInstance();//获取所有实例
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		for (Instance instance : instances) {
			String title = "";
			tempServers = new ArrayList<TBobofaceAdsServerCustom>();
			Map<String, Object> map = new HashMap<String, Object>();
			String ip = instance.getInnerIpAddress() != null ? instance.getInnerIpAddress().iterator().next() : "未知";
			map.put("ip", ip);
			map.put("serverName", instance.getInstanceName());
			map.put("regionId", instance.getRegionId());
			for (TBobofaceAdsServerCustom adsServer : adsServers) {
				if(adsServer.getServerip().equals(ip)){
					if(adsServer.getParentServiceTree() != null && adsServer.getTitle() != null){//设置产品模块
						title += (adsServer.getParentServiceTree().getTitle() + ">" + adsServer.getTitle() + ",");
					}
					map.put("title", title.length() == 0 ? title : title.subSequence(0, title.length() - 1));
					if(adsServer.getServiceTreeId() != null && adsServer.getServiceTreeId().equals(serverTreeId)){//设置服务器挂载
						map.put("serverMount", "checked");
					}
				}else{
					tempServers.add(adsServer);
				}
			}
			adsServers = tempServers;
			listMap.add(map);
		}
		data.put("list", listMap);
		return new PubRetrunMsg(CODE.D100000, data);
	}
	
	/**
	 * 服务器挂载业务树
	 * @param serverTreeId 业务树id
	 * @param ips 服务器ips
	 * @return
	 */
	@RequestMapping(value = "/json/v1/server/mount/{id}", method = RequestMethod.POST)
	public @ResponseBody PubRetrunMsg serverMountV1(@PathVariable("id") Integer serverTreeId, String[] ips){
		Map<String, Object> data = new HashMap<String, Object>();
		
		TBobofaceServiceTree serverTree = iServiceTreeService.getById(serverTreeId);
		if(serverTree == null){
			return new PubRetrunMsg(CODE.D200002, "非法参数,无法找到serverTreeId:" + serverTreeId + "的数据");
		}
	
		List<String> listIps = (ips == null ? new ArrayList<String>() : Arrays.asList(ips));//无挂载服务器ip
		List<String> mountIps = new ArrayList<String>();//已挂载服务器ip
		
		List<TBobofaceAdsServer> mountServers = iAdsServerService.findServerByServiceTreeId(serverTree.getId());//根据业务树id查询
		for (TBobofaceAdsServer mouAdsServer : mountServers) {
			mountIps.add(mouAdsServer.getServerip());
		}
		
		List<TBobofaceAdsServer> adsServers = iAdsServerService.findServerByIps(listIps);//获取服务器列表（有可能以前挂载过存在的）
		
		Map<String, Object> map = BaseUtil.compareArry(mountIps, listIps);
		//需挂载服务器
		List<String> addArry = (List<String>) map.get("add_arry");
		TBobofaceAdsServer newServer = new TBobofaceAdsServer();
		for (String s : addArry) {
			boolean flag = true;
			for (TBobofaceAdsServer adsServer : adsServers) {
				if(s.equals(adsServer.getServerip())){//如果以前存在过就添加关联表
					flag = false;
					newServer = adsServer;
					break;
				}
			}
			if(flag){//新增服务器和关联表
				newServer = new TBobofaceAdsServer();
				newServer.setServerip(s);
				newServer.setAddtime((int)BaseUtil.currentTimeMillis());
				iAdsServerService.saveSeletive(newServer);
				iAdsServerService.saveServerMount(newServer.getId(), serverTree.getId());
			}else{//新增关联表数据
				iAdsServerService.saveServerMount(newServer.getId(), serverTree.getId());
			}
		}
		//需取消挂载服务器
		List<String> deleteArry = (List<String>) map.get("delete_arry");
		iAdsServerService.deleteServerMount(deleteArry, serverTree.getId());
		
		return new PubRetrunMsg(CODE.D100000, data);
	}
}
