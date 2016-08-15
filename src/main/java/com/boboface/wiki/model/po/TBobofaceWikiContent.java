package com.boboface.wiki.model.po;

import javax.persistence.Id;

/**
 * 
 * Title:TBobofaceWikiContent
 * Description:wiki内容Po类
 * @author    zwb
 * @date      2016年7月25日 下午4:04:56
 *
 */
public class TBobofaceWikiContent {
	@Id
    private Integer id;
    private Integer wikitreeid;
    private Integer addtime;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWikitreeid() {
        return wikitreeid;
    }

    public void setWikitreeid(Integer wikitreeid) {
        this.wikitreeid = wikitreeid;
    }

    public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}