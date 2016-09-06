package com.boboface.ads.model.po;

import com.boboface.serviceTree.model.po.TBobofaceServiceTree;


/**
 * 
 * Title:TbobofaceAdsServerCustom
 * Description:
 * @author    zwb
 * @date      2016年9月2日 下午3:54:13
 *
 */
public class TBobofaceAdsServerCustom extends TBobofaceAdsServer {
	
	private String title;//业务名称
	
	private Integer serviceTreeId;//业务树id
	
	private Integer serviceTreeParentId;//父业务树id
	
	private TBobofaceServiceTree parentServiceTree;//父业务树

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getServiceTreeId() {
		return serviceTreeId;
	}

	public void setServiceTreeId(Integer serviceTreeId) {
		this.serviceTreeId = serviceTreeId;
	}

	public TBobofaceServiceTree getParentServiceTree() {
		return parentServiceTree;
	}

	public void setParentServiceTree(TBobofaceServiceTree parentServiceTree) {
		this.parentServiceTree = parentServiceTree;
	}

	public Integer getServiceTreeParentId() {
		return serviceTreeParentId;
	}

	public void setServiceTreeParentId(Integer serviceTreeParentId) {
		this.serviceTreeParentId = serviceTreeParentId;
	}
	
}
