package com.boboface.ads.scriptshell;

import java.util.ArrayList;
import java.util.List;

import net.zowbman.base.data.type.conversion.string.IStringDataTypeConversion;
import net.zowbman.base.data.type.conversion.string.impl.StringDataTypeConversionImpl;
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
	
	public final void prepareProjectBuildTemplage(ProjectBuildVo projectBuildVo){
		ready();
		//1、进入编译位置
		compileRoot(projectBuildVo.getAppName());
		//2、拉去代码
		cloneCode(projectBuildVo.getGitPath(), projectBuildVo.getBranch(), projectBuildVo.getTag());
		//3、变更程序代码脚本编码类型
		//4、执行make.sh
		//5、clone到存储位置
		//6、执行run.sh
		//7、执行shell命令
		runShell();
	}
	
	private void ready(){
		shellScripts.add("/bin/sh");
		shellScripts.add("-c");
	}
	
	/**
	 * 1、进入编译位置
	 * @param appName 项目名
	 */
	private void compileRoot(String appName) {
		//tempStrings.add("cd /compile/" + appName);
	}
	
	/**
	 * 2、拉取代码
	 * @param gitPath git地址
	 * @param branch 分支
	 * @param Tag 标签
	 */
	private void cloneCode(String gitPath, String branch, String Tag){
		tempStrings.add("git clone " + gitPath);
	}
	
	/**
	 * 2、执行shell命令
	 */
	private void runShell(){
		
		shellScripts.add(iStringDataTypeConversion.listStrToString(tempStrings, " && "));
		
		String[] strings = new String[shellScripts.size()];
		for(int i = 0;i < shellScripts.size(); i++){
			strings[i] = shellScripts.get(i);
		}
		String lineResult = ShellHandler.process(strings);
		logger.info(lineResult);
	}
}
