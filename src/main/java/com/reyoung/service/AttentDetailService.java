package com.reyoung.service;

import com.reyoung.model.AttentDetail;
import com.reyoung.model.AttentPlan;

import java.util.List;

/**
 * Created by yangtao on 2020-02-23.
 */
public interface AttentDetailService {

    public Integer addattentdetailist(List<AttentDetail> attentDetails);

    public List<AttentDetail> findattentdetailbyattentplan(AttentPlan attentPlan);

    public Integer delattentdetailbydetails(List<AttentDetail> attentDetails);

}
