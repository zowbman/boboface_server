package com.boboface.ads.scriptshell;

public class ProjectBuildVo {
	private String appName;//项目名称
	private String gitPath;//git地址
	private String branch;//分支
	private String tag;//tag
	
	public ProjectBuildVo(String appName, String gitPath){
		this.appName = appName;
		this.gitPath = gitPath;
	}
	
	public ProjectBuildVo(String appName, String gitPath, String branch, String tag){
		this.appName = appName;
		this.gitPath = gitPath;
		this.branch = branch;
		this.tag = tag;
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
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
