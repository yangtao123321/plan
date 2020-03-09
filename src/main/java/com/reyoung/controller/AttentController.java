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
 * Created by yangtao on 2020-02-23.
 */
@Controller
public class AttentController {

    @Resource(name = "sectionService")
    private SectionService sectionService;

    @Resource(name = "attentPlanService")
    private AttentPlanService attentPlanService;

    @Resource(name = "userService")
    private UserService userService;

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
