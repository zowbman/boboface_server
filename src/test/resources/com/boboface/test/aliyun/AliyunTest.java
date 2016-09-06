package com.boboface.test.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesRequest;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance.LockReason;
import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse.Instance.Tag;
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
        	for (Instance instance : response.getInstances()) {//实例列表
				System.out.println("autoReleaseTime:" + instance.getAutoReleaseTime());
				System.out.println("clusterId:" + instance.getClusterId());
				System.out.println("creationTime:" + instance.getCreationTime());
				System.out.println("description:" + instance.getDescription());
				System.out.println("expiredTime:" + instance.getExpiredTime());
				System.out.println("hostName:" + instance.getHostName());
				System.out.println("imageId:" + instance.getImageId());
				System.out.println("instanceChargeType:" + instance.getInstanceChargeType());
				System.out.println("instanceId:" + instance.getInstanceId());
				System.out.println("instanceName:" + instance.getInstanceName());
				System.out.println("instanceNetworkType:" + instance.getInstanceNetworkType());
				System.out.println("instanceType:" + instance.getInstanceType());
				System.out.println("instanceTypeFamily:" + instance.getInstanceTypeFamily());
				System.out.println("regionId:" + instance.getRegionId());
				System.out.println("serialNumber:" + instance.getSerialNumber());
				System.out.println("vlanId:" + instance.getVlanId());
				System.out.println("zoneId:" + instance.getZoneId());
				System.out.println("cpu:" + instance.getCpu());
				System.out.println("internetMaxBandwidthIn:" + instance.getInternetMaxBandwidthIn());
				System.out.println("internetMaxBandwidthOut:" + instance.getInternetMaxBandwidthOut());
				System.out.println("deviceAvailable:" + instance.getDeviceAvailable());
				System.out.println("eipAddress->allocationId:" + instance.getEipAddress().getAllocationId());
				System.out.println("eipAddress->internetChargeType:" + instance.getEipAddress().getInternetChargeType());
				System.out.println("eipAddress->ipAddress:" + instance.getEipAddress().getIpAddress());
				System.out.println("eipAddress->bandwidth:" + instance.getEipAddress().getBandwidth());
				System.out.println("eipAddress->isSupportUnassociate:" + instance.getEipAddress().getIsSupportUnassociate());
				int index = 0;
				for (String s : instance.getInnerIpAddress()) {
					System.out.println("innerIpAddress" + index + ":" + s);
				}
				System.out.println("ioOptimized:" + instance.getIoOptimized());
				index = 0;
				for (LockReason lockReason : instance.getOperationLocks()) {
					System.out.println("operationLocks->lockReason" + index + ":" + lockReason.getLockReason());
				}
				index = 0;
				for (String s : instance.getPublicIpAddress()) {
					System.out.println("publicIpAddress" + index + ":" + s);
				}
				index = 0;
				for (String s : instance.getSecurityGroupIds()) {
					System.out.println("securityGroupIds" + index + ":" + s);
				}
				System.out.println("status:" + instance.getStatus());
				index = 0;
				for (Tag tag : instance.getTags()) {
					System.out.println("tag" + index + ":" + tag.getTagKey() + "|" + tag.getTagValue());
				}
				System.out.println("vpcAttributes->natIpAddress:" + instance.getVpcAttributes().getNatIpAddress());
				System.out.println("vpcAttributes->vpcId:" + instance.getVpcAttributes().getVpcId());
				System.out.println("vpcAttributes->vSwitchId:" + instance.getVpcAttributes().getVSwitchId());
				index = 0;
				for (String s : instance.getVpcAttributes().getPrivateIpAddress()) {
					System.out.println("vpcAttributes->privateIpAddress" + index + ":" + s);
				}
			}
        }catch (ClientException e) {
        	e.printStackTrace();
        }
	}
	public static void main(String[] args) {
		new AliyunTest().sample();
	}
}
