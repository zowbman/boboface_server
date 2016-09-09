package com.boboface.ads.model.vo;

/**
 * 
 * Title:TBobofaceAdsProjectBuildVo
 * Description:项目部署vo类
 * @author    zwb
 * @date      2016年8月22日 下午3:07:41
 *
 */
public class TBobofaceAdsProjectBuildVo {
	private Integer appId;//项目id
	private String appBranch;//项目分支
	private String branchTag;//分支tag
	private String ip;//发布机器
	
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getAppBranch() {
		return appBranch;
	}
	public void setAppBranch(String appBranch) {
		this.appBranch = appBranch;
	}
	public String getBranchTag() {
		return branchTag;
	}
	public void setBranchTag(String branchTag) {
		this.branchTag = branchTag;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
