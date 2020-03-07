package com.reyoung.serviceimpl;

import com.reyoung.dao.AttentDetailDao;
import com.reyoung.model.AttentDetail;
import com.reyoung.model.AttentPlan;
import com.reyoung.multidatasource.DataSource;
import com.reyoung.service.AttentDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangtao on 2020-02-23.
 */
@Service("attentDetailService")
@DataSource("dataSource")
public class AttentDetailServiceImpl implements AttentDetailService {

    @Resource(name = "attentDetailDao")
    private AttentDetailDao attentDetailDao;

    @Override
    public Integer addattentdetailist(List<AttentDetail> attentDetails) {

        return attentDetailDao.addattentdetailist(attentDetails);

    }

    @Override
    public List<AttentDetail> findattentdetailbyattentplan(AttentPlan attentPlan) {
        return attentDetailDao.findattentdetailbyattentplan(attentPlan);
    }

    @Override
    public Integer delattentdetailbydetails(List<AttentDetail> attentDetails) {
        return attentDetailDao.delattentdetailbydetails(attentDetails);
    }

}
