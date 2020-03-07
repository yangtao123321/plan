package com.reyoung.serviceimpl;

import com.reyoung.dao.AttentPlanDao;
import com.reyoung.model.AttentPlan;
import com.reyoung.model.Flowinfos;
import com.reyoung.multidatasource.DataSource;
import com.reyoung.service.AttentPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yangtao on 2020-02-23.
 */
@Service("attentPlanService")
@DataSource("dataSource")
public class AttentPlanServiceImpl implements AttentPlanService {

    @Resource(name = "attentPlanDao")
    private AttentPlanDao attentPlanDao;

    @Override
    public Integer addattentplan(AttentPlan attentPlan) {
        return attentPlanDao.addattentplan(attentPlan);
    }

    @Override
    public AttentPlan findattentplanbyflowinfos(Flowinfos flowinfos) {
        return attentPlanDao.findattentplanbyflowinfos(flowinfos);
    }

    @Override
    public Integer delattentplanbyaid(AttentPlan attentPlan) {
        return attentPlanDao.delattentplanbyaid(attentPlan);
    }
}
