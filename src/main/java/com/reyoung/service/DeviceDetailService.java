package com.reyoung.service;

import com.reyoung.model.DeviceDetail;
import com.reyoung.model.DevicePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangtao on 2020-02-11.
 */
public interface DeviceDetailService {

    public Integer adddevicedetailbydetails(List<DeviceDetail> deviceDetails);


    public List<DeviceDetail> finddevicedetaillistbydeviceplan(DevicePlan devicePlan);


    public Integer deldevicedetailbydevicelist(@Param("deviceDetails") List<DeviceDetail> deviceDetails);

}
