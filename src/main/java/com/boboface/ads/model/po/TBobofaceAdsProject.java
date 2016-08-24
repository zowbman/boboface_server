package com.boboface.ads.model.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * Title:TBobofaceAdsProject
 * Description:ads 项目po类
 * @author    zwb
 * @date      2016年8月18日 下午3:47:29
 *
 */
public class TBobofaceAdsProject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String appname;
    private String introduction;
    private String storagepath;
    private Integer servicetreeid;
    private Integer addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getStoragepath() {
        return storagepath;
    }

    public void setStoragepath(String storagepath) {
        this.storagepath = storagepath == null ? null : storagepath.trim();
    }

    public Integer getServicetreeid() {
        return servicetreeid;
    }

    public void setServicetreeid(Integer servicetreeid) {
        this.servicetreeid = servicetreeid;
    }

    public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }
}