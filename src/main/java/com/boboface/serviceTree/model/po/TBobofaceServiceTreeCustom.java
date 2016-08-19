package com.boboface.serviceTree.model.po;

import java.util.Set;

/**
 * 
 * Title:TBobofaceServiceTreeCustom
 * Description:service tree 扩展类
 * @author    zwb
 * @date      2016年8月18日 上午10:52:45
 *
 */
public class TBobofaceServiceTreeCustom extends TBobofaceServiceTree {
	
	private Set<TBobofaceServiceTree> childServiceTree;//子树

	public Set<TBobofaceServiceTree> getChildServiceTree() {
		return childServiceTree;
	}

	public void setChildServiceTree(Set<TBobofaceServiceTree> childServiceTree) {
		this.childServiceTree = childServiceTree;
	}
}
