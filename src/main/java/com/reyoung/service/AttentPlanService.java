package com.reyoung.service;

import com.reyoung.model.AttentPlan;
import com.reyoung.model.Flowinfos;

/**
 * Created by yangtao on 2020-02-23.
 */
public interface AttentPlanService {

    public Integer addattentplan(AttentPlan attentPlan);

    public AttentPlan findattentplanbyflowinfos(Flowinfos flowinfos);

    public Integer delattentplanbyaid(AttentPlan attentPlan);

}
