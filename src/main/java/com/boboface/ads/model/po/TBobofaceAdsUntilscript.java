package com.boboface.ads.model.po;

import javax.persistence.Id;

/**
 * 
 * Title:TBobofaceAdsUntilscript
 * Description:工具脚本po类
 * @author    zwb
 * @date      2016年8月25日 下午5:40:51
 *
 */
public class TBobofaceAdsUntilscript {
	@Id
    private Integer id;
    private String title;
    private Integer appid;
    private Integer addtime;
    private String content;

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

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
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