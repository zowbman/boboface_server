package com.boboface.ads.scriptshell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.zowbman.base.data.type.conversion.string.IStringDataTypeConversion;
import net.zowbman.base.data.type.conversion.string.impl.StringDataTypeConversionImpl;
import net.zowbman.base.file.io.FileIoUntil;
import net.zowbman.base.shell.ShellHandler;
import net.zowbman.base.util.BaseUtil;

import org.apache.commons.lang.StringUtils;
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
	
	/**
	 *  执行结果
	 */
	private String lineResult = "";
	
	public final String prepareProjectBuildTemplage(ProjectBuildVo projectBuildVo){
		//1、编译
		ready();
		cloneAndCompile(projectBuildVo);
		runShell();//跑一次脚本
		//2、准备模板文件以及工具脚本
		initTemplateAndScript(projectBuildVo);
		//3、开始根据模板和映射表来实例化配置文件
		instantiateConfigurationFile(projectBuildVo);
		
		
		shellScripts = new ArrayList<String>();
		tempStrings = new ArrayList<String>();
		//4、上机操作(在目标机器创建临时目录，下载相关配置文件以及脚本、运行前置脚本、部署代码、运行后置脚本)
		ready();
		onlineOperation(projectBuildVo);
		runShell();//跑一次脚本
		
		return lineResult;
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
		projectBuildVo.getTargetCode() +" 2>&1");
	}
	
	/**
	 * 2、生成模板文件以及工具脚本
	 * @param projectBuildVo
	 */
	private void initTemplateAndScript(ProjectBuildVo projectBuildVo){
		//prefix.sh | suffix.sh
		String adsToolPath = "/__bobofaceAdsCompile__/adsTools/" + projectBuildVo.getAppId() + "/";
		if(projectBuildVo.getAdsUntilScripts() == null)
			return;
		if(projectBuildVo.getAdsUntilScripts().size() != 2)//只有两个工具脚本prefix.sh | suffix.sh
			return;
		FileIoUntil.writeFile(adsToolPath + projectBuildVo.getAdsUntilScripts().get(0).getTitle(), projectBuildVo.getAdsUntilScripts().get(0).getContent());
		FileIoUntil.writeFile(adsToolPath + projectBuildVo.getAdsUntilScripts().get(1).getTitle(), projectBuildVo.getAdsUntilScripts().get(1).getContent());
		//ads.default.conf
		List<String> tempAdsDefaultConf = FileIoUntil.readFileByLinesReturnList(getClass().getResource("/").getPath() + "shell/ads.default.conf");
		String regexStr = "\\@(.*?)\\@";
		tempAdsDefaultConf.set(0, tempAdsDefaultConf.get(0).replaceAll(regexStr, ""));//ip
		tempAdsDefaultConf.set(1, tempAdsDefaultConf.get(1).replaceAll(regexStr, projectBuildVo.getAppName()));//projectName
		tempAdsDefaultConf.set(2, tempAdsDefaultConf.get(2).replaceAll(regexStr, "/__bobofaceAds__/adsTools/" + projectBuildVo.getAppId()));//curPath
		tempAdsDefaultConf.set(3, tempAdsDefaultConf.get(3).replaceAll(regexStr, "/__bobofaceAds__/" + projectBuildVo.getAppId()));//tmpPath
		tempAdsDefaultConf.set(4, tempAdsDefaultConf.get(4).replaceAll(regexStr, projectBuildVo.getAppFolder() + "/" + projectBuildVo.getAppName()));//installPath
		tempAdsDefaultConf.set(5, tempAdsDefaultConf.get(5).replaceAll(regexStr, projectBuildVo.getOwner()));//owner
		tempAdsDefaultConf.set(6, tempAdsDefaultConf.get(6).replaceAll(regexStr, projectBuildVo.getOwnerGroup()));//ownerGroup
		tempAdsDefaultConf.set(7, tempAdsDefaultConf.get(7).replaceAll(regexStr, ""));//lastVersion
		tempAdsDefaultConf.set(8, tempAdsDefaultConf.get(8).replaceAll(regexStr, ""));//curVersion
		FileIoUntil.writeFile("/__bobofaceAdsCompile__/adsTools/" + projectBuildVo.getAppId() + "/ads.default.conf", iStringDataTypeConversion.listStrToString(tempAdsDefaultConf, "\n"));
	}
	
	/**
	 * 3、根据映射表以及配置文件实例化模板文件
	 * @param adsContent
	 */
	private void instantiateConfigurationFile(ProjectBuildVo projectBuildVo){
		String adsConfTplPath = "/__bobofaceAdsCompile__/" + projectBuildVo.getAppId() + "/" + projectBuildVo.getAppName() + "/outer";
		
		//从映射文件封装需要实例化的配置文件
		List<String> mappingFile = Arrays.asList(projectBuildVo.getAdsContent().getMappingFile().split("\n"));//回车分割
		List<String> insConfFiles = new ArrayList<String>();//配置文件
		for (String line : mappingFile) {
			String[] values = line.split("=", 2);//2为返回数组的个数
			if(values != null && values.length == 2){
				String key = values[0].trim();
				String value = values[1].trim();
				if("file".equals(key)){//如果key为'file'
					insConfFiles.add(value);
					logger.info("映射文件参数:" + key + "|" + value);
				}
			}
		}
		
		List<String> templateFile = Arrays.asList(projectBuildVo.getAdsContent().getTemplateFile().split("\n"));//回车分割
		//封装模板参数为map
		Map<String, String> templateMap = new HashMap<String, String>();
		for (String line : templateFile) {
			String[] values = line.split("=", 2);//2为返回数组的个数
			if(values != null && values.length == 2){
				String key = values[0].trim();
				String value = values[1].trim();
				templateMap.put(key, value);
				logger.info("模板参数:" + key + "|" + value);
			}
		}
		
		//编列映射列表
		for (String file : insConfFiles) {
			List<String> listText = FileIoUntil.readFileByLinesReturnList(adsConfTplPath + file.substring(1,file.length()));
			String newText = "";
			lineResult += "开始实例化配置文件:" + file + "<br>";
			for (String text : listText) {
				Map<Integer, Integer> coordinate = instanceCoordinate(text, new HashMap<Integer, Integer>(), 0);
				//查找出来的坐标key进行排序（否则出现越界）
				if(coordinate != null){
					Map<Integer, Integer> map = new TreeMap<Integer, Integer>(new Comparator<Integer>() {
						@Override
						public int compare(Integer o1, Integer o2) {
							return o1.compareTo(o2);//升序排序
						}
					});
					map.putAll(coordinate);
					
					int index = 0;
					Iterator<Entry<Integer, Integer>> it = map.entrySet().iterator();
					while(it.hasNext()){
						Entry<Integer, Integer> entry = it.next();
						String templateKey = text.substring(entry.getKey() + 1, entry.getValue() - 1);
						logger.info(templateKey+"坐标:"+ (entry.getKey() + 1)+":"+ (entry.getValue() - 1));	
						String templateValue = templateMap.get(templateKey);
						if(templateValue != null){//从模板文件中查询到值
							newText += text.substring(index, entry.getKey()) + templateValue;//替换模板中的值
						}else{
							newText += text.substring(index,entry.getValue());//不替换
						}
						index = entry.getValue();
					}
					newText += (text.substring(index,text.length()) + "\n");
				}else{
					newText += text + "\n";
				}
			}
			//写文件
			FileIoUntil.writeFile(adsConfTplPath + file.substring(1,file.length()), newText);
		}
	}
	
	/**
	 * 实例化模板文件坐标
	 * @param text 文本
	 * @param coordinate 坐标
	 * 		key ->开始下标
	 * 		value ->结束下标
	 * @param index
	 * @return Map<Integer,Integer>
	 */
	private Map<Integer,Integer> instanceCoordinate(String text, Map<Integer,Integer> coordinate, int index){
		String[] texts = StringUtils.substringsBetween(text, "@", "@");
		int at = BaseUtil.calculate(text, "@") - BaseUtil.calculate(text, "\\@");
		if(at%2 != 0){
			lineResult += "模板不符合规范,没有成对出现";
			return null;
		}
		if(texts != null){
			for (String s : texts) {
				
				int pos = 0;
				String lescape = "";
				String rescape = "";
				pos = text.indexOf("@" + s + "@");
				
				try{
					lescape = Character.toString(text.charAt(pos - 1));
					rescape = Character.toString(text.charAt(pos + s.length()));
				}catch(StringIndexOutOfBoundsException ex){}
				if("\\".equals(lescape)){//左边是转义@
					text = text.substring(pos + 1, text.length());
					instanceCoordinate(text, coordinate, pos + index + 1);
				}else if("\\".equals(rescape)){//右边是转义@
					String temp = text.substring(pos + s.length() + 2,text.length());
					if(temp.indexOf("\\@") != -1){
						
					}else if(temp.indexOf("@") != -1){
						lineResult += "模板不符合规范,key中出现非法字符(\\@)";
						return null;
					}
				}else{
					coordinate.put(pos + index, pos + index + s.length() + 2);
				}
			}
		}
		return coordinate.size() != 0 ? coordinate : null;
	}
	
	/**
	 * 4、上机操作(在目标机器创建临时目录，下载相关配置文件以及脚本、运行前置脚本、部署代码、运行后置脚本)
	 * @param projectBuildVo
	 */
	private void onlineOperation(ProjectBuildVo projectBuildVo){
		String path = getClass().getResource("/").getPath();
		path += "shell/ads.onlineOperation.sh ";
		tempStrings.add("dos2unix " + path);
		tempStrings.add("sh " + path + projectBuildVo.getAppId() + " " +
				projectBuildVo.getAppName() + " " +
				projectBuildVo.getIp() + " " + 
				projectBuildVo.getAppFolder() + "/" + projectBuildVo.getAppName() + " " +
				projectBuildVo.getOwner() + " " +
				projectBuildVo.getOwnerGroup() + " " + " 2>&1");
	}
	
	/**
	 * 执行shell命令
	 */
	private void runShell(){
		
		shellScripts.add(iStringDataTypeConversion.listStrToString(tempStrings, " && "));
		
		String[] strings = new String[shellScripts.size()];
		for(int i = 0;i < shellScripts.size(); i++){
			strings[i] = shellScripts.get(i);
		}
		lineResult += ShellHandler.process(strings);
	}
	
	
	//测试实例化模板文件代码
	/*public static void main(String[] args) {
		//\@xxxx@yyy@xxxx
		//xxxxx@yyyxxxxx
		//xxx @yyyy@ xx\@xxxx @yyyyy@ xxxxx
		String text = "jdbc.password       = @jdbc.password@aaa";
		Map<Integer,Integer> coordinate = test1(text,new HashMap<Integer, Integer>(), 0);
		System.out.println(coordinate);
		for (Integer key : coordinate.keySet()) {
			System.out.println(text.substring(key +1,coordinate.get(key)-1));
			System.out.println("key->" + key + ",value->" +  coordinate.get(key));
		}
		System.out.println(text);
	}
	
	private static Map<Integer, Integer> test1(String text, Map<Integer, Integer> coordinate, int index){
		String[] texts = StringUtils.substringsBetween(text, "@", "@");
		int at = BaseUtil.calculate(text, "@") - BaseUtil.calculate(text, "\\@");
		if(at%2 != 0){
			System.out.println("模板不符合规范,没有成对出现");
			return null;
		}
		if(texts != null){
			for (String s : texts) {
				
				int pos = 0;
				String lescape = "";
				String rescape = "";
				pos = text.indexOf("@" + s + "@");
				
				try{
					lescape = Character.toString(text.charAt(pos - 1));
					rescape = Character.toString(text.charAt(pos + s.length()));
				}catch(StringIndexOutOfBoundsException ex){}
				if("\\".equals(lescape)){//左边是转义@
					text = text.substring(pos + 1, text.length());
					test1(text, coordinate, pos + index + 1);
				}else if("\\".equals(rescape)){//右边是转义@
					String temp = text.substring(pos + s.length() + 2,text.length());
					if(temp.indexOf("\\@") != -1){
						
					}else if(temp.indexOf("@")!= -1){
						System.out.println("模板不符合规范,key中出现非法字符(\\@)");
						return null;
					}
				}else{
					coordinate.put(pos + index, pos + index + s.length() + 2);
				}
			}
		}
		return coordinate.size() != 0 ? coordinate : null;
	}	*/
}
