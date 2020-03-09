package com.reyoung.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.reyoung.model.*;
import com.reyoung.service.*;
import com.reyoung.tools.GetYear;
import com.reyoung.tools.TextMail2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangtao on 2020-02-11.
 */
@Controller
public class DeviceController {

    @Resource(name = "sectionService")
    private SectionService sectionService;

    @Resource(name = "devicePlanService")
    private DevicePlanService devicePlanService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "deviceDetailService")
    private DeviceDetailService deviceDetailService;

    @Resource(name = "flowinfosService")
    private FlowinfosService flowinfosService;

    @RequestMapping("/climpdevicepage.do")
    public String climpdevicepage(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/device/DevicePage";

        }

    }

    //跳转到设备采购的页面
    @RequestMapping("/climpdevicepage1.do")
    public String climpdevicepage1(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/smallscreen/device/DevicePage";

        }

    }

    //跳转到设备计划申请页面 ie8
    @RequestMapping("/climpdevicepageie8.do")
    public String climpdevicepageie8(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/IE8/device/DevicePage";

        }

    }

    //提交购买设备计划表
    @RequestMapping("/applybuydeviceplan.do")
    public @ResponseBody String applybuydeviceplan(HttpServletRequest request,DevicePlan devicePlan,String details,Flowinfos flowinfos) {

        List<DeviceDetail> deviceDetails=new ArrayList<>();

        JSONArray array= JSON.parseArray(details);

        for (int i=0;i<array.size();i++) {

            DeviceDetail deviceDetail=new DeviceDetail();

            JSONObject jsonObject= (JSONObject) array.get(i);

            String devicename=jsonObject.getString("devicename");

            String devicebank=jsonObject.getString("devicebank");

            String devicenum=jsonObject.getString("devicenum");

            deviceDetail.setDevicename(devicename);
            deviceDetail.setDevicebank(devicebank);
            deviceDetail.setDevicenum(devicenum);

            deviceDetails.add(deviceDetail);

        }

        devicePlan.setApplytime1(GetYear.formtim(devicePlan.getApplytime()));

        Integer res = devicePlanService.adddeviceplan(devicePlan);

        if (res==1) {//deviceplan信息添加成功了

            Integer deviceid = devicePlan.getDeviceid();

            for (DeviceDetail d:deviceDetails) {

                d.setDeviceplanid(deviceid);

            }

            Integer res1 = deviceDetailService.adddevicedetailbydetails(deviceDetails);

            if (deviceDetails.size()==res1) {//是否全部添加的数据库

                flowinfos.setFlows(new Flows(devicePlan.getFlowid()));

                flowinfos.setPerson(devicePlan.getApplyperson());

                flowinfos.setFlowabstract(devicePlan.getApplyabstract());

                flowinfos.setStartime1(devicePlan.getApplytime());

                flowinfos.setStartime(devicePlan.getApplytime1());

                flowinfos.setUser(devicePlan.getUser());

                flowinfos.setIncident(devicePlan.getDeviceid());

                flowinfos.setFlag(0);//0表示没有任何人审批过

                //将流程信息添加到数据库
                Integer r = flowinfosService.addflowinfo(flowinfos);

                if (r==1) {//提交成功

                    User user = userService.finduserbyuid(flowinfos.getUser().getUid());

                    if (user.getSection().getSectionid()==1) {//粉针事业部

                        List<User> list = userService.finduserdunitlist(user);

                        String subject="计划审批提醒";

                        String username="";

                        if (list.size()>1) {//2个及以上

                            username="各位主任,你们好!";

                        }else if (list.size()==1) {

                            username=list.get(0).getTruename()+",你好!";

                        }else {

                            username="你好!";

                        }

                        String context="<font face='楷体' style='font-size:19px'><span style='font-weight:bold'>"+username+"</span><br /><br />&nbsp;&nbsp;&nbsp;您有1条计划需要审批,信息如下:<br /><br />&nbsp;&nbsp;&nbsp;内容摘要:&nbsp;<span style='color:red;font-weight:bold'>"+flowinfos.getFlowabstract()+"</span>,&nbsp;提报人:"+flowinfos.getPerson()+",&nbsp;提报单位:"+user.getDepartment().getDeptname()+"<br /><br />&nbsp;&nbsp;&nbsp;请及时处理!</font>";

                        try {

                            TextMail2.sendMail("yangtao@reyoung.com", "YANGyang136164", "192.168.8.3", list, "", subject, context);

                        } catch (Exception e) {



                        }

                    }else {//其他的部门  暂不进行处理



                    }

                    return "success";

                }else {//提交失败

                    return "fails";

                }

            }else {

                return "fails";

            }


        }else {

            return "fails";

        }

    }

}
