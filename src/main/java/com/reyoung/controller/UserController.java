package com.reyoung.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.reyoung.model.*;
import com.reyoung.pager.PageBean;
import com.reyoung.service.*;
import com.reyoung.tools.GetYear;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by yangtao on 2019-12-29.
 */
@Controller
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "positionService")
    private PositionService positionService;

    @Resource(name = "departmentService")
    private DepartmentService departmentService;

    @Resource(name = "sectionService")
    private SectionService sectionService;

    @Resource(name = "flowinfosService")
    private FlowinfosService flowinfosService;

    @Resource(name = "flowsService")
    private FlowsService flowsService;

    @RequestMapping("/savepas.do")
    public @ResponseBody String savepas(HttpServletRequest request,User user,HttpServletResponse response) {

        if (user.getSavefalg()!=null&&"1".equals(user.getSavefalg())) {//要求记住密码

            String loginfo=user.getUsername()+","+user.getPassword();

            Cookie userCookie=new Cookie("loginfo",loginfo);

            userCookie.setMaxAge(24*60*60*7);//设置7天免登录

            userCookie.setPath("/");

            response.addCookie(userCookie);

        }else if ((user.getSavefalg()!=null&&"0".equals(user.getSavefalg()))||user.getSavefalg()==null) {//不需要记住密码

            Cookie userCookie=new Cookie("loginfo",null);

            userCookie.setMaxAge(0);

            userCookie.setPath("/");

            response.addCookie(userCookie);

        }

        return "success";

    }

    @RequestMapping("/climptoregister.do")
    public String climptoregister(HttpServletRequest request) {

        List<Position> positions = positionService.findallposition();

        List<Department> departments = departmentService.findalldepartment();

        List<Section> sections = sectionService.findallsection();

        request.setAttribute("pos",positions);

        request.setAttribute("dep",departments);

        request.setAttribute("sec",sections);

        return "register";

    }

    @RequestMapping("/checkusernamexit.do")
    public @ResponseBody String checkusernamexit(HttpServletRequest request,User user) {

        Integer res = userService.findnamexit(user);


        if (res==0) {//不存在该用户,可以进行添加操作

            return "ok";

        }else {//该用户已经存在,不能重复去注册



            return "fails";


        }

    }

    @RequestMapping("/addaccount.do")
    public @ResponseBody String addaccount(HttpServletRequest request,User user) {

        user.setPassword(DigestUtils.md5Hex(user.getUsername() + user.getPassword()));

        Integer res = userService.addaccount(user);

        return res+"";

    }

    @RequestMapping("/login.do")
    public @ResponseBody String login(HttpServletRequest request,User user,HttpServletResponse response) {

        String pas=user.getPassword();

        user.setPassword(DigestUtils.md5Hex(user.getUsername() + user.getPassword()));

        //校验用户登录信息的合法性
        User user1 = userService.checkdologin(user);

        if (user1!=null) {//说明登录成功了。

            if ("1".equals(user.getSavefalg())) {//需要记住密码

                String loginfo=user.getUsername()+","+pas;

                Cookie userCookie=new Cookie("loginfo",loginfo);

                userCookie.setMaxAge(24*60*60*7);//设置7天免登录

                userCookie.setPath("/");

                response.addCookie(userCookie);

            }else {//不需要记住密码

                Cookie userCookie=new Cookie("loginfo",null);

                userCookie.setMaxAge(0);

                userCookie.setPath("/");

                response.addCookie(userCookie);

            }

            request.getSession().setAttribute("userinfo",user1);//将用户信息保存到session域中

            request.getSession().setMaxInactiveInterval(60*60);//设置session 1小时候后 失效

            String json = JSONObject.toJSONString(user1, SerializerFeature.WriteMapNullValue);

            return json;

        }else {

            return "loginfail";

        }

    }

    //校验用户名和密码是否匹配
    @RequestMapping("/login1.do")
    public @ResponseBody String login1(HttpServletRequest request,User user,HttpServletResponse response) {

        String pas=user.getPassword();

        user.setPassword(DigestUtils.md5Hex(user.getUsername() + user.getPassword()));

        //校验用户登录信息的合法性
        User user1 = userService.checkdologin(user);

        if (user1!=null) {//说明登录成功了。

            return "loginsuccess";

        }else {

            return "loginfail";

        }

    }

    @RequestMapping("/welcome.do")
    public String climptowelcome(HttpServletRequest request,PageBean<Flowinfos> pageBean) {

        // 分页的显示 添加字段**********需要给flowinfo添加一个完成的标志字段    默认值为0   完成值为 1 何时完成可以自定义*******

        User user= (User) request.getSession().getAttribute("userinfo");

        String s = GetYear.getDates();

        if (user==null) {//session失效，需要重新登录

            return "login";

        }else {

            request.setAttribute("dates",s);

            if (user.getPosition().getPosname().equals("经办人")) {//需要跳转到purchase页面

                if (user.getUsername().equals("admin")) {//用来查询信息用的账号

                    return "WEB-INF/query/query";

                }else {

                    //查询所有可以申请的流程
                    List<Flows> flowses = flowsService.findallflows();

                    if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {

                        pageBean.setCurrentPage(1);

                    }

                    pageBean.setPageSize(15);//设置每页显示的条数

                    Integer totalRecord=flowinfosService.findflowinfoscountbyuid(user);

                    pageBean.setTotalRecord(totalRecord);

                    pageBean.getTotalPage();

                    PageBean<Flowinfos> page = flowinfosService.findflowinfosbyuid(user, pageBean);

                    request.setAttribute("page",page);

                    //根据userid查询flowinfo信息
                    flowinfosService.findflowinfosbyuid(user,pageBean);

                    request.setAttribute("flows",flowses);

                    return "WEB-INF/jsps/purchase";


                }



            }else if (user.getPosition().getPosname().equals("单位负责人")) {

                //需要查询出自己待审批的任务   根据自己所在的单位和职位查询出需要待审批的Flowinfos

                if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {//如果首页不存在则将第一页设置为首页

                    pageBean.setCurrentPage(1);

                }

                pageBean.setPageSize(15);//设置每页显示的条数

                Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

                pageBean.setTotalRecord(totalRecord);

                pageBean.getTotalPage();

                //List<Flowinfos> flowinfoses = flowinfosService.finddealsbyuser(user);
                PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

                request.setAttribute("page",page);

                return "WEB-INF/jsps/dunit";

            }else if (user.getPosition().getPosname().equals("部门经理")) {

                //需要查询出自己待审批的任务   根据自己所在的单位和职位查询出需要待审批的Flowinfos

                if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {//如果首页不存在则将第一页设置为首页

                    pageBean.setCurrentPage(1);

                }

                pageBean.setPageSize(15);//设置每页显示的条数

                Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

                pageBean.setTotalRecord(totalRecord);

                pageBean.getTotalPage();

                //List<Flowinfos> flowinfoses = flowinfosService.finddealsbyuser(user);
                PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

                request.setAttribute("page",page);

                return "WEB-INF/jsps/manager";

            }else if (user.getPosition().getPosname().equals("公司总裁")) {


                return "WEB-INF/jsps/president";

            }

            return "WEB-INF/jsps/purchase";

        }

    }

    @RequestMapping("/climpwelcomesmall.do")
    public String climpwelcomesmall(HttpServletRequest request,PageBean<Flowinfos> pageBean) {

        // 分页的显示 添加字段**********需要给flowinfo添加一个完成的标志字段    默认值为0   完成值为 1 何时完成可以自定义*******

        User user= (User) request.getSession().getAttribute("userinfo");

        String s = GetYear.getDates();

        if (user==null) {//session失效，需要重新登录

            return "login1";

        }else {

            request.setAttribute("dates",s);

            if (user.getPosition().getPosname().equals("经办人")) {//需要跳转到purchase页面

                //查询所有可以申请的流程
                List<Flows> flowses = flowsService.findallflows();

                if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {

                    pageBean.setCurrentPage(1);

                }

                pageBean.setPageSize(15);//设置每页显示的条数

                Integer totalRecord=flowinfosService.findflowinfoscountbyuid(user);

                pageBean.setTotalRecord(totalRecord);

                pageBean.getTotalPage();

                PageBean<Flowinfos> page = flowinfosService.findflowinfosbyuid(user, pageBean);

                request.setAttribute("page",page);

                //根据userid查询flowinfo信息
                flowinfosService.findflowinfosbyuid(user,pageBean);

                request.setAttribute("flows",flowses);

                return "WEB-INF/smallscreen/jsps/purchase";

            }else if (user.getPosition().getPosname().equals("单位负责人")) {

                //需要查询出自己待审批的任务   根据自己所在的单位和职位查询出需要待审批的Flowinfos

                if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {//如果首页不存在则将第一页设置为首页

                    pageBean.setCurrentPage(1);

                }

                pageBean.setPageSize(15);//设置每页显示的条数

                Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

                pageBean.setTotalRecord(totalRecord);

                pageBean.getTotalPage();

                //List<Flowinfos> flowinfoses = flowinfosService.finddealsbyuser(user);
                PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

                request.setAttribute("page",page);

                return "WEB-INF/smallscreen/jsps/dunit";

            }else if (user.getPosition().getPosname().equals("部门经理")) {

                //需要查询出自己待审批的任务   根据自己所在的单位和职位查询出需要待审批的Flowinfos

                if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {//如果首页不存在则将第一页设置为首页

                    pageBean.setCurrentPage(1);

                }

                pageBean.setPageSize(15);//设置每页显示的条数

                Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

                pageBean.setTotalRecord(totalRecord);

                pageBean.getTotalPage();

                //List<Flowinfos> flowinfoses = flowinfosService.finddealsbyuser(user);
                PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

                request.setAttribute("page",page);

                return "WEB-INF/smallscreen/jsps/manager";

            }

            return "WEB-INF/jsps/purchase";

        }

    }

    @RequestMapping("/welcome1.do")//ie8
    public String climptowelcome1(HttpServletRequest request,PageBean<Flowinfos> pageBean) {

        // 分页的显示 添加字段**********需要给flowinfo添加一个完成的标志字段    默认值为0   完成值为 1 何时完成可以自定义*******

        User user= (User) request.getSession().getAttribute("userinfo");

        String s = GetYear.getDates();

        if (user==null) {//session失效，需要重新登录

            return "ie8/login2";

        }else {

            request.setAttribute("dates",s);

            if (user.getPosition().getPosname().equals("经办人")) {//需要跳转到purchase页面

                if (user.getUsername().equals("admin")) {//用来查询信息用的账号

                    return "WEB-INF/IE8/query/query";

                }else{

                    //查询所有可以申请的流程
                    List<Flows> flowses = flowsService.findallflows();

                    if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {

                        pageBean.setCurrentPage(1);

                    }

                    pageBean.setPageSize(15);//设置每页显示的条数

                    Integer totalRecord=flowinfosService.findflowinfoscountbyuid(user);

                    pageBean.setTotalRecord(totalRecord);

                    pageBean.getTotalPage();

                    PageBean<Flowinfos> page = flowinfosService.findflowinfosbyuid(user, pageBean);

                    request.setAttribute("page",page);

                    //根据userid查询flowinfo信息
                    flowinfosService.findflowinfosbyuid(user,pageBean);

                    request.setAttribute("flows",flowses);

                    return "WEB-INF/IE8/jsps/purchase";

                }

            }else if (user.getPosition().getPosname().equals("单位负责人")) {

                //需要查询出自己待审批的任务   根据自己所在的单位和职位查询出需要待审批的Flowinfos

                if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {//如果首页不存在则将第一页设置为首页

                    pageBean.setCurrentPage(1);

                }

                pageBean.setPageSize(15);//设置每页显示的条数

                Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

                pageBean.setTotalRecord(totalRecord);

                pageBean.getTotalPage();

                //List<Flowinfos> flowinfoses = flowinfosService.finddealsbyuser(user);
                PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

                request.setAttribute("page",page);

                return "WEB-INF/IE8/jsps/dunit";

            }else if (user.getPosition().getPosname().equals("部门经理")) {

                //需要查询出自己待审批的任务   根据自己所在的单位和职位查询出需要待审批的Flowinfos

                if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {//如果首页不存在则将第一页设置为首页

                    pageBean.setCurrentPage(1);

                }

                pageBean.setPageSize(15);//设置每页显示的条数

                Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

                pageBean.setTotalRecord(totalRecord);

                pageBean.getTotalPage();

                //List<Flowinfos> flowinfoses = flowinfosService.finddealsbyuser(user);
                PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

                request.setAttribute("page",page);

                return "WEB-INF/IE8/jsps/manager";

            }

            return "WEB-INF/jsps/purchase";

        }

    }

    //查询待办的任务
    @RequestMapping("/findoingtask.do")
    public @ResponseBody String findoingtask(HttpServletRequest request,PageBean<Flowinfos> pageBean) {

        User user= (User) request.getSession().getAttribute("userinfo");

        if (user.getPosition().getPosid()==1) {//提报人的查询

            if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {

                pageBean.setCurrentPage(1);

            }

            pageBean.setPageSize(15);//设置每页显示的条数

            Integer totalRecord=flowinfosService.findflowinfoscountbyuid(user);

            pageBean.setTotalRecord(totalRecord);

            pageBean.getTotalPage();

            PageBean<Flowinfos> page = flowinfosService.findflowinfosbyuid(user, pageBean);

            String json = JSONObject.toJSONString(page, SerializerFeature.WriteMapNullValue);

            return json;

        }else if (user.getPosition().getPosid()<=3){//查询单位负责人和部门经理的待办的任务

            if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {

                pageBean.setCurrentPage(1);

            }

            pageBean.setPageSize(15);//设置每页显示的条数

            Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

            pageBean.setTotalRecord(totalRecord);

            pageBean.getTotalPage();

            PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

            String json = JSONObject.toJSONString(page, SerializerFeature.WriteMapNullValue);

            return json;

        }else {

            if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {

                pageBean.setCurrentPage(1);

            }

            pageBean.setPageSize(15);//设置每页显示的条数

            Integer totalRecord=flowinfosService.finddealscountbyuser(user);//查询待办任务的总条数

            pageBean.setTotalRecord(totalRecord);

            pageBean.getTotalPage();

            PageBean<Flowinfos> page = flowinfosService.finddealsbyuser(user, pageBean);

            String json = JSONObject.toJSONString(page, SerializerFeature.WriteMapNullValue);

            return json;

        }

    }

    //查询已处理的任务
    @RequestMapping("/findealtask.do")
    public @ResponseBody String findealtask(HttpServletRequest request,PageBean<Flowinfos> pageBean) {

        User user= (User)request.getSession().getAttribute("userinfo");

        if (pageBean.getCurrentPage()==null||pageBean.getCurrentPage()==0) {

            pageBean.setCurrentPage(1);

        }

        pageBean.setPageSize(15);

        if (user.getPosition().getPosid()==1) {//提报人查询已处理任务

            Integer totalRecord=flowinfosService.findapplyflowinfoedbyuid(user);

            pageBean.setTotalRecord(totalRecord);

            pageBean.getTotalPage();

            PageBean<Flowinfos> page = flowinfosService.findapplyflowinfoedlistbyuid(pageBean, user);

            String json = JSONObject.toJSONString(page, SerializerFeature.WriteMapNullValue);

            return json;

        }else if(user.getPosition().getPosid()==2) {//经办人查询已处理的任务

            Integer totalRecord = flowinfosService.findflowinfoedcount(user);

            pageBean.setTotalRecord(totalRecord);

            pageBean.getTotalPage();

            PageBean<Flowinfos> page = flowinfosService.findflowinfoedlist(pageBean, user);

            String json = JSONObject.toJSONString(page, SerializerFeature.WriteMapNullValue);

            return json;


        }else if(user.getPosition().getPosid()==3) {//部门经理查询已处理的任务

            Integer totalRecord = flowinfosService.findflowinfoedcount(user);

            pageBean.setTotalRecord(totalRecord);

            pageBean.getTotalPage();

            PageBean<Flowinfos> page = flowinfosService.findflowinfoedlist(pageBean, user);

            String json = JSONObject.toJSONString(page, SerializerFeature.WriteMapNullValue);

            return json;

        }else if (user.getPosition().getPosid()==4) {//总经理审核

            Integer totalRecord = flowinfosService.findflowinfoedcount(user);

            pageBean.setTotalRecord(totalRecord);

            pageBean.getTotalRecord();

            PageBean<Flowinfos> page = flowinfosService.findflowinfoedlist(pageBean, user);

            String json = JSONObject.toJSONString(page, SerializerFeature.WriteMapNullValue);

            return json;

        }

        return null;

    }

    //注销用户功能
    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest request) {

        request.getSession().removeAttribute("userinfo");

        return "forward:/welcome.do";

    }

    //注销功能 小屏幕
    @RequestMapping("/logout1.do")
    public String logout1(HttpServletRequest request) {

        request.getSession().removeAttribute("userinfo");

        return "forward:/climpwelcomesmall.do";

    }

    @RequestMapping("/logoutie8.do")
    public String logoutie8(HttpServletRequest request) {

        request.getSession().removeAttribute("userinfo");

        return "forward:/welcome1.do";

    }

    //校验原密码是否正确
    @RequestMapping("/checkorgpas.do")
    public @ResponseBody String checkorgpas(HttpServletRequest request,User user) {

        user.setPassword(DigestUtils.md5Hex(user.getUsername() + user.getPassword()));

        User user1 = userService.checkdologin(user);

        if (user1==null) {//原密码不正确

            return "error";

        }else {//原密码正确不处理

            return "success";

        }

    }

    //修改密码的功能
    @RequestMapping("/updatepassword.do")
    public @ResponseBody String updatepassword(HttpServletRequest request,User user,HttpServletResponse response) {

        user.setPassword(DigestUtils.md5Hex(user.getUsername() + user.getPassword()));

        Integer res = userService.updatepas(user);

        if (res==1) {

            //密码修改成功后立刻让cookie失效
            Cookie userCookie=new Cookie("loginfo",null);

            userCookie.setMaxAge(0);

            userCookie.setPath("/");

            response.addCookie(userCookie);

            return "ok";

        }else {

            return "fails";

        }

    }

    //查询所有的流程
    @RequestMapping("/queryallflows.do")
    public @ResponseBody String queryallflows(HttpServletRequest request) {

        List<Flows> flowses = flowsService.findallflows();

        String json = JSONObject.toJSONString(flowses, SerializerFeature.WriteMapNullValue);

        return json;

    }

    @RequestMapping("/test123.do")
    public @ResponseBody String test123(HttpServletRequest request,String s) {

        System.out.println(s);

        return s;

    }

    //跳转到修改密码的页面
    @RequestMapping("/climptouppas.do")
    public String climptouppas(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        if (user==null) {

            return "ie8/login2";

        }

        request.setAttribute("users",user);

        return "WEB-INF/IE8/uppas";

    }

    //校验输入的原密码是否是正确的
    @RequestMapping("/vailiedpsd.do")
    public @ResponseBody String vailiedpsd(HttpServletRequest request,User user) {

        User user1 = userService.finduserbyuid(user.getUid());

        if (user1.getPassword().equals(DigestUtils.md5Hex(user1.getUsername() + user.getPassword()))) {

           return "success";

        }else {

           return "fails";

       }


    }

    //修改密码功能
    @RequestMapping("/updatepsd.do")
    public @ResponseBody String updatepsd(HttpServletRequest request,User user) {

        User user1 = userService.finduserbyuid(user.getUid());

        user1.setPassword(DigestUtils.md5Hex(user1.getUsername() + user.getPassword()));

        Integer res = userService.updatepas(user1);

        return res+"";

    }

    //修改密码的功能
    @RequestMapping("/climpuppaspage.do")
    public String climpuppaspage(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        if (user==null) {

            return "login";

        }else {

            String s = GetYear.getDates();

            request.setAttribute("use",user);

            request.setAttribute("dates",s);

            return "uppaspage";

        }

    }


    //修改默认密码的功能 小屏幕
    @RequestMapping("/climpuppaspagesmallscreen.do")
    public String climpuppaspagesmallscreen(HttpServletRequest request) {

        User user= (User) request.getSession().getAttribute("userinfo");

        if (user==null) {

            return "login";

        }else {

            String s = GetYear.getDates();

            request.setAttribute("use",user);

            request.setAttribute("dates",s);

            return "uppaspagesmallscreen";

        }

    }

    //修改默认密码的功能
    @RequestMapping("/uppasdefaultpassword.do")
    public @ResponseBody String uppasdefaultpassword(HttpServletRequest request,User user) {

        User user1 = userService.finduserbyuid(user.getUid());

        user.setPassword(DigestUtils.md5Hex(user1.getUsername()+user.getPassword()));

        user.setUid(user1.getUid());

        Integer res = userService.updatedefaultpassword(user);

        return res+"";

    }


}
