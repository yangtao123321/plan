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
public class OtherController {

    @Resource(name = "sectionService")
    private SectionService sectionService;

    @Resource(name = "otherPlanService")
    private OtherPlanService otherPlanService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "otherDetailService")
    private OtherDetailService otherDetailService;

    @Resource(name = "flowinfosService")
    private FlowinfosService flowinfosService;

    @RequestMapping("/climpotherpage.do")
    public String climpotherpage(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/other/OtherPage";

        }

    }

    //跳转到其他计划申请页
    @RequestMapping("/climpotherpage1.do")
    public String climpotherpage1(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/smallscreen/other/OtherPage";

        }

    }

    //跳转到其他计划申请页面
    @RequestMapping("/climpotherpageie8.do")
    public String climpotherpageie8(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/IE8/other/OtherPage";

        }

    }

    //提交采购计划单
    @RequestMapping("/applyotherplan.do")
    public @ResponseBody String applyotherplan(HttpServletRequest request,OtherPlan otherPlan,String details,Flowinfos flowinfos) {

        List<OtherDetail> otherDetails=new ArrayList<>();

        JSONArray array= JSON.parseArray(details);

            for (int i=0;i<array.size();i++){

                OtherDetail otherDetail=new OtherDetail();

                JSONObject jsonObject= (JSONObject) array.get(i);

                String proname=jsonObject.getString("otherproname");

                String prosupplier=jsonObject.getString("othersupplier");

                String prospecial=jsonObject.getString("otherspecial");

                String pronum=jsonObject.getString("othernum");

                otherDetail.setOtherproname(proname);
                otherDetail.setOthersupplier(prosupplier);
                otherDetail.setOtherspecial(prospecial);
                otherDetail.setOthernum(pronum);

                otherDetails.add(otherDetail);

            }

        otherPlan.setApplytime1(GetYear.formtim(otherPlan.getApplytime()));

        Integer res = otherPlanService.addotherplan(otherPlan);

        if (res==1) {

            Integer otherplanid = otherPlan.getOtherplanid();

            for (OtherDetail o:otherDetails) {

                o.setOtherplanid(otherplanid);

            }

            Integer res1 = otherDetailService.addotherdetailbyotherdetail(otherDetails);

            if ((res1==otherDetails.size()&&res1>0)||otherDetails.size()==0) {

                flowinfos.setFlows(new Flows(otherPlan.getFlowid()));
                flowinfos.setPerson(otherPlan.getApplyperson());

                flowinfos.setFlowabstract(otherPlan.getApplyabstract());

                flowinfos.setStartime1(otherPlan.getApplytime());

                flowinfos.setStartime(otherPlan.getApplytime1());

                flowinfos.setUser(otherPlan.getUser());

                flowinfos.setIncident(otherPlan.getOtherplanid());

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

                return "fails";

            }

        }else {

            return "fails";

        }

    }

}
