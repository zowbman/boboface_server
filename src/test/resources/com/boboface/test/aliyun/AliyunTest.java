package com.boboface.test.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;



/**
 * 
 * Title:AliyunTest
 * Description:阿里云测试
 * @author    zwb
 * @date      2016年8月31日 下午6:11:55
 *
 */
public class AliyunTest {
	public void sample() {
        DescribeInstancesRequest describe = new DescribeInstancesRequest();
        IClientProfile profile = DefaultProfile.getProfile("cn-shenzhen", "LTAIyhWMyw09opY3", "ocejsAAcTGN7VA9CeVQaWGSViqasWI");
        IAcsClient client = new DefaultAcsClient(profile);
        try {
        	DescribeInstancesResponse response = client.getAcsResponse(describe);
        	System.out.println(response.getInstances().iterator().next().getInstanceName());
        }catch (ClientException e) {
        	e.printStackTrace();
        }
	}
	public static void main(String[] args) {
		new AliyunTest().sample();
	}
}
