package com.reyoung.model;

/**
 * Created by yangtao on 2020-02-23.
 */
public class AttentDetail {

    private Integer attentdetailid;

    private String attentname;

    private String attentspecial;

    private String attentnum;

    private Integer attentplanid;

    public AttentDetail() {
    }

    public AttentDetail(Integer attentdetailid, String attentname, String attentspecial, String attentnum, Integer attentplanid) {
        this.attentdetailid = attentdetailid;
        this.attentname = attentname;
        this.attentspecial = attentspecial;
        this.attentnum = attentnum;
        this.attentplanid = attentplanid;
    }

    public Integer getAttentdetailid() {
        return attentdetailid;
    }

    public void setAttentdetailid(Integer attentdetailid) {
        this.attentdetailid = attentdetailid;
    }

    public String getAttentname() {
        return attentname;
    }

    public void setAttentname(String attentname) {
        this.attentname = attentname;
    }

    public String getAttentspecial() {
        return attentspecial;
    }

    public void setAttentspecial(String attentspecial) {
        this.attentspecial = attentspecial;
    }

    public String getAttentnum() {
        return attentnum;
    }

    public void setAttentnum(String attentnum) {
        this.attentnum = attentnum;
    }

    public Integer getAttentplanid() {
        return attentplanid;
    }

    public void setAttentplanid(Integer attentplanid) {
        this.attentplanid = attentplanid;
    }

    @Override
    public String toString() {
        return "AttentDetail{" +
                "attentdetailid=" + attentdetailid +
                ", attentname='" + attentname + '\'' +
                ", attentspecial='" + attentspecial + '\'' +
                ", attentnum='" + attentnum + '\'' +
                ", attentplanid=" + attentplanid +
                '}';
    }

}
