package com.applyhm.dc.danwei.po;

import java.io.Serializable;

public class Danwei implements Serializable{

    private static final long serialVersionUID = -4014293711555936440L;
    private Integer id;     // ID
    private String type;    // 类型 hospital yibaoju
    private String danwei;  // 单位名称
    private String remark;  // 备注

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDanwei() {
        return danwei;
    }
    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
