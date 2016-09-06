package com.boboface.serviceTree.model.po;

import java.util.List;

/**
 * 
 * Title:TBobofaceServiceTreeCustom
 * Description:service tree 扩展类
 * @author    zwb
 * @date      2016年8月18日 上午10:52:45
 *
 */
public class TBobofaceServiceTreeCustom extends TBobofaceServiceTree {
	
	private List<TBobofaceServiceTreeCustom> childServiceTree;//子树

	public List<TBobofaceServiceTreeCustom> getChildServiceTree() {
		return childServiceTree;
	}

	public void setChildServiceTree(List<TBobofaceServiceTreeCustom> childServiceTree) {
		this.childServiceTree = childServiceTree;
	}
}
