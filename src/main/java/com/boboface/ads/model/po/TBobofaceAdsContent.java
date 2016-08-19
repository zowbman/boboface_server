package com.boboface.ads.model.po;

import javax.persistence.Id;

/**
 * 
 * Title:TBobofaceAdsContent
 * Description:ads内容Po类
 * @author    zwb
 * @date      2016年8月16日 下午3:03:36
 *
 */
public class TBobofaceAdsContent {
	@Id
    private Integer id;
    private Integer adstreeid;
    private Integer addtime;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdstreeid() {
		return adstreeid;
	}

	public void setAdstreeid(Integer adstreeid) {
		this.adstreeid = adstreeid;
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