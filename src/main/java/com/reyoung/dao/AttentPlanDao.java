package com.reyoung.dao;

import com.reyoung.model.AttentPlan;
import com.reyoung.model.Flowinfos;
import org.springframework.stereotype.Repository;

/**
 * Created by yangtao on 2020-02-23.
 */
@Repository("attentPlanDao")
public interface AttentPlanDao {

    public Integer addattentplan(AttentPlan attentPlan);

    public AttentPlan findattentplanbyflowinfos(Flowinfos flowinfos);

    public Integer delattentplanbyaid(AttentPlan attentPlan);

}
