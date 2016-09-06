package com.boboface.ads.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.boboface.ads.model.po.TBobofaceAdsServer;
import com.boboface.ads.model.po.TBobofaceAdsServerCustom;
import com.boboface.ads.service.IAdsServerService;
import com.boboface.base.service.impl.BaseServiceImpl;

/**
 * 
 * Title:AdsServerServiceImpl
 * Description:ads 服务器 service 实现类
 * @author    zwb
 * @date      2016年9月2日 下午3:41:08
 *
 */
@Service
public class AdsServerServiceImpl extends BaseServiceImpl<TBobofaceAdsServer> implements IAdsServerService {

	@Override
	public List<TBobofaceAdsServerCustom> findAllServer() {
		return tBobofaceAdsServerMapperCustom.findAllServer();
	}

	@Override
	public List<TBobofaceAdsServer> findServerByIps(List<String> ips) {
		List<TBobofaceAdsServer> tBobofaceAdsServers = new ArrayList<TBobofaceAdsServer>();
		if(ips != null && ips.size() != 0){
			Example example = new Example(TBobofaceAdsServer.class);
			example.createCriteria().andIn("serverip", ips.size() == 0 ? null : ips);
			tBobofaceAdsServers = tBobofaceAdsServerMapper.selectByExample(example);
		}
		return tBobofaceAdsServers;
	}

	@Override
	public void saveServerMount(Integer serverId, Integer serviceTreeId) {
		if(serverId != null && serviceTreeId != null){
			Map<String , Integer> map = new HashMap<String, Integer>();
			map.put("serverId", serverId);
			map.put("serviceTreeId", serviceTreeId);
			tBobofaceAdsServerMapperCustom.saveServerMount(map);
		}
	}

	@Override
	public void deleteServerMount(List<String> ips, Integer serviceTreeId) {
		List<TBobofaceAdsServer> adsServers = new ArrayList<TBobofaceAdsServer>();
		if(ips != null && ips.size() != 0){
			Example example = new Example(TBobofaceAdsServer.class);
			example.createCriteria().andIn("serverip", ips);
			example.selectProperties("id");
			adsServers = tBobofaceAdsServerMapper.selectByExample(example);
		}
		List<Integer> ids = new ArrayList<Integer>();
		for (TBobofaceAdsServer adsServer : adsServers) {
			ids.add(adsServer.getId());
		}
		
		if(ids != null && ids.size() != 0 && serviceTreeId != null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", ids);
			map.put("serviceTreeId", serviceTreeId);
			tBobofaceAdsServerMapperCustom.deleteServerMount(map);
		}
	}

	@Override
	public List<TBobofaceAdsServer> findServerByServiceTreeId(Integer serviceTreeId) {
		List<TBobofaceAdsServer> tBobofaceAdsServers = new ArrayList<TBobofaceAdsServer>();
		if(serviceTreeId != null ){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("serviceTreeId", serviceTreeId);
			tBobofaceAdsServers = tBobofaceAdsServerMapperCustom.findServerByIpsAndServiceTreeId(map);
		}
		return tBobofaceAdsServers;
	}
}
