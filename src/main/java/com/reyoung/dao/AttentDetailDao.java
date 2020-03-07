package com.reyoung.dao;

import com.reyoung.model.AttentDetail;
import com.reyoung.model.AttentPlan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yangtao on 2020-02-23.
 */
@Repository("attentDetailDao")
public interface AttentDetailDao {

    public Integer addattentdetailist(@Param("attentDetails") List<AttentDetail> attentDetails);

    public List<AttentDetail> findattentdetailbyattentplan(AttentPlan attentPlan);

    public Integer delattentdetailbydetails(@Param("attentDetails") List<AttentDetail> attentDetails);

}
