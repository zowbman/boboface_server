package com.boboface.ads.model.vo;

import com.boboface.base.model.vo.EnvEnum;

/**
 * 
 * Title:AdsProjectBuildContentVo
 * Description:项目部署映射文件模板文件包装类
 * @author    zwb
 * @date      2016年9月6日 下午6:12:54
 *
 */
public class AdsProjectBuildContent {
	private Integer appId;//项目id
	private String mappingFile;//映射文件按
	private String templateFile;//模板文件
	private EnvEnum envEnum;//环境
	
	public AdsProjectBuildContent() {
		super();
	}
	
	public AdsProjectBuildContent(Integer appId, String mappingFile, String templateFile, EnvEnum envEnum){
		this.appId = appId;
		this.mappingFile = mappingFile;
		this.templateFile = templateFile;
		this.envEnum = envEnum;
	}
	
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getMappingFile() {
		return mappingFile;
	}
	public void setMappingFile(String mappingFile) {
		this.mappingFile = mappingFile;
	}
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	public EnvEnum getEnvEnum() {
		return envEnum;
	}
	public void setEnvEnum(EnvEnum envEnum) {
		this.envEnum = envEnum;
	}
}
