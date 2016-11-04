package com.boboface.ads.service;

import java.util.List;

import com.boboface.ads.model.po.TBobofaceAdsServer;
import com.boboface.ads.model.po.TBobofaceAdsServerCustom;
import com.boboface.base.model.vo.PageBean;
import com.boboface.base.model.vo.PageInfoCustom;
import com.boboface.base.service.IBaseService;
import com.boboface.exception.CustomException;

/**
 * 
 * Title:IAdsServerService
 * Description:ads 服务器 service接口
 * @author    zwb
 * @date      2016年9月2日 下午3:40:11
 *
 */
public interface IAdsServerService extends IBaseService<TBobofaceAdsServer> {
	
	/**
	 * 获取全部挂载服务器
	 * @return
	 */
	List<TBobofaceAdsServerCustom> findAllServer();
	
	/**
	 * 根据ips查询挂载服务器
	 * @param ips
	 * @return List<TBobofaceAdsServer>
	 */
	List<TBobofaceAdsServer> findServerByIps(List<String> ips);
	
	/**
	 * 保存服务器挂载业务树
	 * @param serverId 服务器id
	 * @param serviceTreeId 业务树id
	 */
	void saveServerMount(Integer serverId, Integer serviceTreeId);
	
	/**
	 * 取消服务器挂载
	 * @param ips 服务器ips
	 */
	void deleteServerMount(List<String> ips, Integer serviceTreeId);
	
	/**
	 * 根据业务树id查询服务器
	 * @param serviceTreeId 业务树id
	 * @return List<TBobofaceAdsServer>
	 */
	List<TBobofaceAdsServer> findServerByServiceTreeId(Integer serviceTreeId);
	
	/**
	 * 根据业务树id查询服务器(分页)
	 * @param pageBean 分页信息
	 * @param serviceTreeId 业务树id
	 * @return PageInfoCustom
	 */
	PageInfoCustom findServerByPageBeanAndServiceTreeId(PageBean pageBean, Integer serviceTreeId) throws CustomException;
}
