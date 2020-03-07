package com.reyoung.model;

import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2020-02-23.
 */
public class AttentPlan {

    private Integer attentId;

    private User user;

    private String attenttitle;//标题

    private String applyperson;

    private String applyabstract;

    private Integer receive;

    private String applytime;

    private Date applytime1;

    private String buyreson;

    private Integer flowid;

    private String attentrequire;

    private List<AttentDetail> attentDetails;

    public AttentPlan() {
    }

    public AttentPlan(Integer attentId, User user, String attenttitle, String applyperson, String applyabstract, Integer receive, String applytime, Date applytime1, String buyreson, Integer flowid, String attentrequire, List<AttentDetail> attentDetails) {
        this.attentId = attentId;
        this.user = user;
        this.attenttitle = attenttitle;
        this.applyperson = applyperson;
        this.applyabstract = applyabstract;
        this.receive = receive;
        this.applytime = applytime;
        this.applytime1 = applytime1;
        this.buyreson = buyreson;
        this.flowid = flowid;
        this.attentrequire = attentrequire;
        this.attentDetails = attentDetails;
    }

    public Integer getAttentId() {
        return attentId;
    }

    public void setAttentId(Integer attentId) {
        this.attentId = attentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAttenttitle() {
        return attenttitle;
    }

    public void setAttenttitle(String attenttitle) {
        this.attenttitle = attenttitle;
    }

    public String getApplyperson() {
        return applyperson;
    }

    public void setApplyperson(String applyperson) {
        this.applyperson = applyperson;
    }

    public String getApplyabstract() {
        return applyabstract;
    }

    public void setApplyabstract(String applyabstract) {
        this.applyabstract = applyabstract;
    }

    public Integer getReceive() {
        return receive;
    }

    public void setReceive(Integer receive) {
        this.receive = receive;
    }

    public String getApplytime() {
        return applytime;
    }

    public void setApplytime(String applytime) {
        this.applytime = applytime;
    }

    public Date getApplytime1() {
        return applytime1;
    }

    public void setApplytime1(Date applytime1) {
        this.applytime1 = applytime1;
    }

    public String getBuyreson() {
        return buyreson;
    }

    public void setBuyreson(String buyreson) {
        this.buyreson = buyreson;
    }

    public Integer getFlowid() {
        return flowid;
    }

    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    public List<AttentDetail> getAttentDetails() {
        return attentDetails;
    }

    public void setAttentDetails(List<AttentDetail> attentDetails) {
        this.attentDetails = attentDetails;
    }

    public String getAttentrequire() {
        return attentrequire;
    }

    public void setAttentrequire(String attentrequire) {
        this.attentrequire = attentrequire;
    }

    @Override
    public String toString() {
        return "AttentPlan{" +
                "attentId=" + attentId +
                ", user=" + user +
                ", attenttitle='" + attenttitle + '\'' +
                ", applyperson='" + applyperson + '\'' +
                ", applyabstract='" + applyabstract + '\'' +
                ", receive=" + receive +
                ", applytime='" + applytime + '\'' +
                ", applytime1=" + applytime1 +
                ", buyreson='" + buyreson + '\'' +
                ", flowid=" + flowid +
                ", attentrequire='" + attentrequire + '\'' +
                ", attentDetails=" + attentDetails +
                '}';
    }

}
