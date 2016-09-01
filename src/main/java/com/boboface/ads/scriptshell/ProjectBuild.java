package com.boboface.ads.scriptshell;

import java.util.ArrayList;
import java.util.List;

import net.zowbman.base.data.type.conversion.string.IStringDataTypeConversion;
import net.zowbman.base.data.type.conversion.string.impl.StringDataTypeConversionImpl;
import net.zowbman.base.file.io.FileIoUntil;
import net.zowbman.base.shell.ShellHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Title:ProjectBuild
 * Description:拉取代码算法框架
 * @author    zwb
 * @date      2016年8月22日 下午5:40:52
 *
 */
public abstract class ProjectBuild {
	
	private static Logger logger = LoggerFactory.getLogger(ProjectBuild.class);
	
	/**
	 * 最终执行shell命令
	 */
	private List<String> shellScripts = new ArrayList<String>();
	
	/**
	 * 
	 */
	private List<String> tempStrings = new ArrayList<String>();
	
	/**
	 * 数据转换接口
	 */
	IStringDataTypeConversion iStringDataTypeConversion = new StringDataTypeConversionImpl();
	
	public final String prepareProjectBuildTemplage(ProjectBuildVo projectBuildVo){
		//1、准备执行
		ready();
		//2、编译
		cloneAndCompile(projectBuildVo);
		//3、准备模板文件以及工具脚本
		initTemplateAndScript(projectBuildVo);
		//3、在目标机器创建临时目录，下载相关配置文件以及脚本
		createTmpDirectoryAndDowmLoad(projectBuildVo.getAppId(), projectBuildVo.getAppName());
		//5、运行前置脚本
		//6、开始实例化配置文件
		//7、检查运行账号
		//8、重临时目录同步到工作目录
		//9、运行后置脚本
		return runShell();
	}
	
	private void ready(){
		shellScripts.add("/bin/sh");
		shellScripts.add("-c");
	}
	
	/**
	 * 1、拉取代码->编译
	 * @param projectBuildVo 包装类
	 */
	private void cloneAndCompile(ProjectBuildVo projectBuildVo) {
		String path = getClass().getResource("/").getPath();
		path += "shell/ads.codeCloneAndCompile.sh ";
		tempStrings.add("dos2unix " + path);
		//source ./ads.codeCloneAndCompile.sh appId appName gitPath branch|tag
		tempStrings.add("sh " + path + 
		projectBuildVo.getAppId() + " " +
		projectBuildVo.getAppName() + " " +
		projectBuildVo.getGitPath() + " " +
		projectBuildVo.getTargetCode() + " 2>&1");
	}
	
	/**
	 * 2、生成模板文件以及工具脚本
	 * @param projectBuildVo
	 */
	private void initTemplateAndScript(ProjectBuildVo projectBuildVo){
		//prefix.sh | suffix.sh
		String adsConfTplPath = "/__bobofaceAdsCompile__/adsTools/" + projectBuildVo.getAppId() + "/";
		if(projectBuildVo.getAdsUntilScripts() == null)
			return;
		if(projectBuildVo.getAdsUntilScripts().size() != 2)//只有两个工具脚本prefix.sh | suffix.sh
			return;
		FileIoUntil.writeFile(adsConfTplPath + projectBuildVo.getAdsUntilScripts().get(0).getTitle(), projectBuildVo.getAdsUntilScripts().get(0).getContent());
		FileIoUntil.writeFile(adsConfTplPath + projectBuildVo.getAdsUntilScripts().get(1).getTitle(), projectBuildVo.getAdsUntilScripts().get(1).getContent());
		//ads.default.conf
		List<String> tempAdsDefaultConf = FileIoUntil.readFileByLinesReturnList(getClass().getResource("/").getPath() + "shell/ads.default.conf");
		String regexStr = "\\@(.*?)\\@";
		tempAdsDefaultConf.set(0, tempAdsDefaultConf.get(0).replaceAll(regexStr, ""));//ip
		tempAdsDefaultConf.set(1, tempAdsDefaultConf.get(1).replaceAll(regexStr, projectBuildVo.getAppName()));//projectName
		tempAdsDefaultConf.set(2, tempAdsDefaultConf.get(2).replaceAll(regexStr, "/__bobofaceAds__/adsTools/" + projectBuildVo.getAppId()));//curPath
		tempAdsDefaultConf.set(3, tempAdsDefaultConf.get(3).replaceAll(regexStr, "/__bobofaceAds__/" + projectBuildVo.getAppId()));//tmpPath
		tempAdsDefaultConf.set(4, tempAdsDefaultConf.get(4).replaceAll(regexStr, projectBuildVo.getAppFolder()));//installPath
		tempAdsDefaultConf.set(5, tempAdsDefaultConf.get(5).replaceAll(regexStr, projectBuildVo.getOwner()));//owner
		tempAdsDefaultConf.set(6, tempAdsDefaultConf.get(6).replaceAll(regexStr, projectBuildVo.getOwnerGroup()));//ownerGroup
		tempAdsDefaultConf.set(7, tempAdsDefaultConf.get(7).replaceAll(regexStr, ""));//lastVersion
		tempAdsDefaultConf.set(8, tempAdsDefaultConf.get(8).replaceAll(regexStr, ""));//curVersion
		FileIoUntil.writeFile("/__bobofaceAdsCompile__/adsTools/" + projectBuildVo.getAppId() + "/ads.default.conf", iStringDataTypeConversion.listStrToString(tempAdsDefaultConf, "\n"));
		//模板文件
	}
	
	/**
	 * 3、创建临时目录以及下载工具脚本配置模板以及项目代码
	 * @param appId
	 */
	private void createTmpDirectoryAndDowmLoad(Integer appId, String appName){
		String path = getClass().getResource("/").getPath();
		path += "shell/ads.createTmpDirectoryAndDownLoad.sh ";
		tempStrings.add("dos2unix " + path);
		tempStrings.add("sh " + path + appId + " " + appName + " 2>&1");
	}
	
	/**
	 * 4、执行shell命令
	 */
	private String runShell(){
		
		shellScripts.add(iStringDataTypeConversion.listStrToString(tempStrings, " && "));
		
		String[] strings = new String[shellScripts.size()];
		for(int i = 0;i < shellScripts.size(); i++){
			strings[i] = shellScripts.get(i);
		}
		String lineResult = "";
		lineResult += ShellHandler.process(strings);
		return lineResult;
	}
}
