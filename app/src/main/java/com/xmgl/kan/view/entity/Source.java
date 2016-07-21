package com.xmgl.kan.view.entity;

import java.util.List;

/**
 */
public class Source {

    private String url;
    private String name;
    private String orgno;
    private String orgname;
    private Integer intvalue;

    private Boolean urlenable = false;

    private List<Params> paramlist;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgno() {
        return orgno;
    }

    public void setOrgno(String orgno) {
        this.orgno = orgno;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public Integer getIntvalue() {
        return intvalue;
    }

    public void setIntvalue(Integer intvalue) {
        this.intvalue = intvalue;
    }

    public Boolean getUrlenable() {
        return urlenable;
    }

    public void setUrlenable(Boolean urlenable) {
        this.urlenable = urlenable;
    }

    public List<Params> getParamlist() {
        return paramlist;
    }

    public void setParamlist(List<Params> paramlist) {
        this.paramlist = paramlist;
    }
}
