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
	
	public final String prepareProjectBuildTemplage(ProjectBuildVo projectBuildVo){
		//1、准备执行
		ready();
		//2、编译
		compile(projectBuildVo);
		//3、在目标机器创建临时目录
		//4、下载相关配置文件以及脚本
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
	 * 编译部署
	 * @param projectBuildVo 包装类
	 */
	private void compile(ProjectBuildVo projectBuildVo) {
		String path = getClass().getResource("/").getPath();
		path += "shell/ads.build1.0.sh ";
		tempStrings.add("dos2unix " + path);
		//# $1 appName
		//# $2 gitPath
		//# $3 targetCode
		//# $4 appFolder
		tempStrings.add("sh " + path + 
		projectBuildVo.getAppName() + " " +
		projectBuildVo.getGitPath() + " " +
		projectBuildVo.getTargetCode() + " " +
		projectBuildVo.getAppFolder());
	}
	
	
	/**
	 * 2、执行shell命令
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
