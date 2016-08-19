package com.boboface.serviceTree.model.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.zowbman.base.data.type.conversion.date.DateDataTypeConversion;
import net.zowbman.base.data.type.conversion.date.vo.DATEFORMAT;

/**
 * 
 * Title:TBobofaceAdsTree
 * Description:service Tree Po类
 * @author    zwb
 * @date      2016年8月16日 上午10:32:42
 *
 */
public class TBobofaceServiceTree {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer parentid;
    private Integer sort;
    private Byte ismountads;
    private Byte allowadd;
    private Byte allowdelete;
    private Byte allowedit;
    private Integer addtime;
    
    /**
     * 获取发布时间戳转日期结果
     * @return String
     */
    public String getAddTimeToData(){
    	if(this.addtime == null)
    		return null;
		return DateDataTypeConversion.timeMillisToDate(this.addtime, true, DATEFORMAT.YYYY_MM_DD);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }
    
    public Integer getSort() {
		return sort;
	}
    
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Byte getIsmountads() {
		return ismountads;
	}
	
	public void setIsmountads(Byte ismountads) {
		this.ismountads = ismountads;
	}
	
	public Byte getAllowadd() {
        return allowadd;
    }

    public void setAllowadd(Byte allowadd) {
        this.allowadd = allowadd;
    }

    public Byte getAllowdelete() {
        return allowdelete;
    }

    public void setAllowdelete(Byte allowdelete) {
        this.allowdelete = allowdelete;
    }

    public Byte getAllowedit() {
        return allowedit;
    }

    public void setAllowedit(Byte allowedit) {
        this.allowedit = allowedit;
    }

    public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }
}