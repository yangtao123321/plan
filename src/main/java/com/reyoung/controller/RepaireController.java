package com.reyoung.controller;

import com.reyoung.model.*;
import com.reyoung.service.FlowinfosService;
import com.reyoung.service.RepairePlanService;
import com.reyoung.service.SectionService;
import com.reyoung.service.UserService;
import com.reyoung.tools.GetYear;
import com.reyoung.tools.TextMail2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yangtao on 2020-02-08.
 */
@Controller
public class RepaireController {

    @Resource(name = "sectionService")
    private SectionService sectionService;

    @Resource(name = "repairePlanService")
    private RepairePlanService repairePlanService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "flowinfosService")
    private FlowinfosService flowinfosService;

    @RequestMapping("/climptorepaire.do")
    public String climptorepaire(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/repaire/RepairePage";

        }

    }

    //跳转到维修计划 兼容小屏幕
    @RequestMapping("/climptorepaire1.do")
    public String climptorepaire1(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/smallscreen/repaire/RepairePage";

        }

    }

    //跳转到维修计划申请页 ie8
    @RequestMapping("/climptorepaireie8.do")
    public String climptorepaireie8(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("userinfo");

        String s= GetYear.gettimes();

        List<Section> sections = sectionService.findallsection();

        if (user==null) {

            return "login";

        }else {

            request.setAttribute("starttime",s);

            request.setAttribute("sections",sections);

            return "WEB-INF/IE8/repaire/RepairePage";

        }

    }

    //提交维修计划
    @RequestMapping("/applyrepaireplan.do")
    public @ResponseBody String applyrepaireplan(HttpServletRequest request,RepairePlan repairePlan,Flowinfos flowinfos) {

        Integer res = repairePlanService.addrepaireplan(repairePlan);

        repairePlan.setApplytime1(GetYear.formtim(repairePlan.getApplytime()));

        if (res==1) {//维修计划添加成功

            flowinfos.setIncident(repairePlan.getRepaireid());

            flowinfos.setFlows(new Flows(repairePlan.getFlowid()));

            flowinfos.setPerson(repairePlan.getApplyperson());

            flowinfos.setFlowabstract(repairePlan.getApplyabstract());

            flowinfos.setStartime(repairePlan.getApplytime1());

            flowinfos.setStartime1(repairePlan.getApplytime());

            flowinfos.setFlag(0);

            flowinfos.setAchieve(0);

            //将流程信息添加到数据库
            Integer r = flowinfosService.addflowinfo(flowinfos);

            if (r==1) {//添加成功了

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


    }

}
