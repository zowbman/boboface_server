package com.boboface.ads.scriptshell;

public class ProjectBuildVo {
	private String appName;//项目名称
	private String gitPath;//git地址
	private String targetCode;//分支|tag
	private String appFolder;//项目目录地址
	
	/**
	 * 
	 * @param appName 项目名称
	 * @param gitPath git地址
	 * @param targetCode 分支|tag
	 * @param appFolder 项目目录地址
	 */
	public ProjectBuildVo(String appName, String gitPath, String targetCode, String appFolder){
		this.appName = appName;
		this.gitPath = gitPath;
		this.targetCode = targetCode;
		this.appFolder = appFolder;
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
}
