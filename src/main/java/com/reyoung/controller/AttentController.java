package com.reyoung.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.reyoung.model.*;
import com.reyoung.service.AttentDetailService;
import com.reyoung.service.AttentPlanService;
import com.reyoung.service.FlowinfosService;
import com.reyoung.service.SectionService;
import com.reyoung.tools.GetYear;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangtao on 2020-02-23.
 */
@Controller
public class AttentController {

    @Resource(name = "sectionService")
    private SectionService sectionService;

    @Resource(name = "attentPlanService")
    private AttentPlanService attentPlanService;

    @Resource(name = "attentDetailService")
    private AttentDetailService attentDetailService;

    @Resource(name = "flowinfosService")
    private FlowinfosService flowinfosService;

    @RequestMapping("/climpattentpage.do")
    public String climpattentpage(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime", s);

            request.setAttribute("sections", sections);

            return "WEB-INF/attent/AttentPage";
        }


    }

    //跳转到其它计划申请的页面
    @RequestMapping("/climpattentpage1.do")
    public String climpattentpage1(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime", s);

            request.setAttribute("sections", sections);

            return "WEB-INF/smallscreen/attent/AttentPage";
        }

    }

    @RequestMapping("/applyattentplan.do")
    public @ResponseBody String applyattentplan(HttpServletRequest request,AttentPlan attentPlan,String details,Flowinfos flowinfos) {

        List<AttentDetail> attentDetails=new ArrayList<>();

        JSONArray array= JSON.parseArray(details);

        for (int i=0;i<array.size();i++) {

            AttentDetail attentDetail=new AttentDetail();

            JSONObject jsonObject= (JSONObject) array.get(i);

            String attentname=jsonObject.getString("attentname");

            String attentspecial=jsonObject.getString("attentspecial");

            String attentnum=jsonObject.getString("attentnum");

            attentDetail.setAttentname(attentname);
            attentDetail.setAttentspecial(attentspecial);
            attentDetail.setAttentnum(attentnum);

            attentDetails.add(attentDetail);

        }

        attentPlan.setApplytime1(GetYear.formtim(attentPlan.getApplytime()));

        Integer res = attentPlanService.addattentplan(attentPlan);

        if (res==1) {

            Integer attentplanid=attentPlan.getAttentId();

            for (AttentDetail a:attentDetails) {

                a.setAttentplanid(attentplanid);

            }

            if (attentDetails==null||attentDetails.size()==0) {//说明没有添加任何的采购明细

                flowinfos.setFlows(new Flows(attentPlan.getFlowid()));
                flowinfos.setPerson(attentPlan.getApplyperson());

                flowinfos.setFlowabstract(attentPlan.getApplyabstract());

                flowinfos.setStartime1(attentPlan.getApplytime());

                flowinfos.setStartime(attentPlan.getApplytime1());

                flowinfos.setUser(attentPlan.getUser());

                flowinfos.setIncident(attentPlan.getAttentId());

                flowinfos.setFlag(0);//0表示没有任何人审批过

                //将流程信息添加到数据库
                Integer r = flowinfosService.addflowinfo(flowinfos);

                if (r==1) {

                    return "success";

                }else {

                    return "fails";

                }

            }else {

                Integer num = attentDetailService.addattentdetailist(attentDetails);

                if ((attentDetails.size()==num&&num>0)||attentDetails.size()==0) {//说明存在采购的明细

                    flowinfos.setFlows(new Flows(attentPlan.getFlowid()));
                    flowinfos.setPerson(attentPlan.getApplyperson());

                    flowinfos.setFlowabstract(attentPlan.getApplyabstract());

                    flowinfos.setStartime1(attentPlan.getApplytime());

                    flowinfos.setStartime(attentPlan.getApplytime1());

                    flowinfos.setUser(attentPlan.getUser());

                    flowinfos.setIncident(attentPlan.getAttentId());

                    flowinfos.setFlag(0);//0表示没有任何人审批过

                    //将流程信息添加到数据库
                    Integer r = flowinfosService.addflowinfo(flowinfos);

                    if (r==1) {

                        return "success";

                    }else {

                        return "fails";

                    }

                }else {

                    return "fails";

                }

            }

        }else {

            return "fails";
        }

    }

}
