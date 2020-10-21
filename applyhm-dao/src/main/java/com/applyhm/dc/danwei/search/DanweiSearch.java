package com.applyhm.dc.danwei.search;

import com.applyhm.core.frame.search.BaseSearch;

public class DanweiSearch extends BaseSearch {

    private static final long serialVersionUID = -3245547393272902937L;

    private Integer id;
    private String type;
    private String q;
    private String name;

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
    public String getQ() {
        return q;
    }
    public void setQ(String q) {
        this.q = q;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
