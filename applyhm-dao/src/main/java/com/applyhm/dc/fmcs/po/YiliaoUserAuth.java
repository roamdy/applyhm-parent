package com.applyhm.dc.fmcs.po;

import java.io.Serializable;
import java.util.Date;

import org.json.simple.JSONObject;

public class YiliaoUserAuth implements Serializable {

    private static final long serialVersionUID = -7561057143737644349L;

    private String tel;                     // 手机号
    private String orignImg;                // 认证图片
    private Date createtime;                // 添加时间
    private Integer yibaojuId;              // 医保局ID
    private Integer hospitalId;             // 医院ID
    private Integer status;                 // 状态

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        if( null != this.tel) {
            json.put("tel", this.tel);
        }
        if( null != this.orignImg ) {
            json.put("orignImg", this.orignImg);
        }
        if( null != this.yibaojuId ) {
            json.put("yibaojuId", this.yibaojuId);
        }
        if( null != this.hospitalId) {
            json.put("hospitalId", this.hospitalId);
        }
        if( null != this.status ) {
            json.put("status", this.status);
        }
        return json;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getOrignImg() {
        return orignImg;
    }

    public void setOrignImg(String orignImg) {
        this.orignImg = orignImg;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getYibaojuId() {
        return yibaojuId;
    }

    public void setYibaojuId(Integer yibaojuId) {
        this.yibaojuId = yibaojuId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}