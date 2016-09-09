package com.boboface.ads.mapper;

import java.util.List;
import java.util.Map;

import com.boboface.ads.model.po.TBobofaceAdsServer;
import com.boboface.ads.model.po.TBobofaceAdsServerCustom;


/**
 * 
 * Title:TBobofaceAdsServerMapper
 * Description:ads 服务器 扩展mapper接口
 * @author    zwb
 * @date      2016年9月2日 下午3:38:27
 *
 */
public interface TBobofaceAdsServerMapperCustom {
	
	/**
	 * 获取全部服务器实例
	 * @return List<TBobofaceAdsServerCustom>
	 */
	List<TBobofaceAdsServerCustom> findAllServer();
	
	/**
	 * 保存服务器挂载
	 * @param map
	 */
	void saveServerMount(Map<String, Integer> map);
	
	/**
	 * 取消服务器挂载
	 * @param map
	 */
	void deleteServerMount(Map<String, Object> map);
	
	/**
	 * 根据ips和业务树id查询服务器列表
	 * @param map 
	 * 		ips->服务器ip
	 * 		id->业务树id
	 * @return List<TBobofaceAdsServer>
	 */
	List<TBobofaceAdsServer> findServerByIpsAndServiceTreeId(Map<String, Object> map);
	
	/**
	 * 根据业务树id查询服务器列表（分页）
	 * @param map
	 * start->分页（开始）
	 * end->一页数量
	 * id->业务树id
	 * @return
	 */
	List<TBobofaceAdsServer> findServerByPageBeanAndServiceTreeId(Map<String, Object> map);
	
	/**
	 * 根据业务树id查询服务器总数
	 * @param serverTreeId 业务树id
	 * @return long
	 */
	long findServerCountByPageBeanAndServiceTreeId(Integer serverTreeId);
}