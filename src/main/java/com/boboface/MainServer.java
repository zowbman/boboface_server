package com.boboface;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/***
 * 
 * Title:ServiceCenterServer
 * Description:jetty启动入口
 * @author    zwb
 * @date      2016年3月31日 上午10:28:19
 *
 */
public class MainServer {
	
	private static Logger logger = LoggerFactory.getLogger(MainServer.class);
	
	public MainServer(){}
	
	public static void main(String[] args) throws Exception {
		InetSocketAddress inetSocketAddress = new InetSocketAddress("0.0.0.0", 12001);
		Server server = new Server(inetSocketAddress);
		String webPath = getWebappPath(Lists.newArrayList("src/main/webapp" , "lib/webapp"));
        //项目访问路径
        WebAppContext context = new WebAppContext();
        context.setDescriptor(webPath + "/WEB-INF/web.xml");
        context.setResourceBase(webPath);
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        logger.info("jetty server start...");
        server.start();
	}
	
	private static String getWebappPath(List<String> webappPaths) {
        for (String webappPath : webappPaths) {
            if (new File(webappPath, "WEB-INF/web.xml").exists()) {
                //logger.warn("find {}", webappPath);
                return webappPath;
            }
        }
        throw new IllegalStateException("not find any webappPath");
    }
}
