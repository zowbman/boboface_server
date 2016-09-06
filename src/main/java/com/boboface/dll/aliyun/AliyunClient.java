package com.boboface.dll.aliyun;

import java.util.List;

import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 
 * Title:AliyunAcs
 * Description:阿里云客服端
 * @author    zwb
 * @date      2016年9月2日 上午10:36:46
 *
 */
public abstract class AliyunClient {
    
	protected DescribeInstancesRequest describe = new DescribeInstancesRequest();//请求
	
    protected IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen", "LTAIyhWMyw09opY3", "ocejsAAcTGN7VA9CeVQaWGSViqasWI");
    
    /**
     * 获取全部服务器实例
     */
    public abstract List<Instance> findAllInstance();
    
}
