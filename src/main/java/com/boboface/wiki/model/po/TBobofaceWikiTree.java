package com.boboface.wiki.model.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.boboface.base.data.type.conversion.DateDataTypeConversion;
import com.boboface.base.data.type.conversion.vo.DATEFORMAT;

/**
 * 
 * Title:TBobofaceWikiTree
 * Description:wiki Tree Po类
 * @author    zwb
 * @date      2016年7月25日 下午12:05:27
 *
 */
public class TBobofaceWikiTree {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private Integer parentid;
    private Byte allowadd;
    private Byte allowdelete;
    private Byte allowedit;
    private Integer sort;
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
    
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }
}