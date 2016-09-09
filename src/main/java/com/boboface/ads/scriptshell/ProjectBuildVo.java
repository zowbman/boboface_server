package com.boboface.ads.scriptshell;

import java.util.List;

import com.boboface.ads.model.po.TBobofaceAdsUntilscript;
import com.boboface.ads.model.vo.AdsProjectBuildContent;

public class ProjectBuildVo {
	private Integer appId;//项目id
	private String appName;//项目名称
	private String gitPath;//git地址
	private String ip;//部署目标机器
	private String targetCode;//分支|tag
	private String appFolder;//项目目录地址
	private String owner;//运行账号
	private String ownerGroup;//账号属组
	private AdsProjectBuildContent adsContent;//模板文件（一个环境一个模板文件）
	private List<TBobofaceAdsUntilscript> adsUntilScripts;//工具脚本(多个)
	
	/**
	 * @param appId 项目id
	 * @param appName 项目名称
	 * @param gitPath git地址
	 * @param ip 部署目标机器
	 * @param targetCode 分支|tag
	 * @param appFolder 项目目录地址
	 * @param owner 运行账号
	 * @param ownerGroup //账号属组
	 * @param adsTemplate 模板文件
	 * @param adsUntilScripts 工具脚本文件
	 */
	public ProjectBuildVo(
			Integer appId,
			String appName, 
			String gitPath,
			String ip,
			String targetCode,
			String appFolder,
			String owner,
			String ownerGroup,
			AdsProjectBuildContent adsContent,
			List<TBobofaceAdsUntilscript> adsUntilScripts){
		this.appId = appId;
		this.appName = appName;
		this.gitPath = gitPath;
		this.ip = ip;
		this.targetCode = targetCode;
		this.appFolder = appFolder;
		this.owner = owner;
		this.ownerGroup = ownerGroup;
		this.adsContent = adsContent;
		this.adsUntilScripts = adsUntilScripts;
	}
	
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getGitPath() {
		return gitPath;
	}
	public void setGitPath(String gitPath) {
		this.gitPath = gitPath;
	}
	public String getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	public String getAppFolder() {
		return appFolder;
	}
	public void setAppFolder(String appFolder) {
		this.appFolder = appFolder;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnerGroup() {
		return ownerGroup;
	}
	public void setOwnerGroup(String ownerGroup) {
		this.ownerGroup = ownerGroup;
	}
	public AdsProjectBuildContent getAdsContent() {
		return adsContent;
	}
	public void setAdsContent(AdsProjectBuildContent adsContent) {
		this.adsContent = adsContent;
	}
	public List<TBobofaceAdsUntilscript> getAdsUntilScripts() {
		return adsUntilScripts;
	}
	public void setAdsUntilScripts(List<TBobofaceAdsUntilscript> adsUntilScripts) {
		this.adsUntilScripts = adsUntilScripts;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
