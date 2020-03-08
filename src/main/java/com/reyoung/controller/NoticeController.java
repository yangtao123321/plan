package com.reyoung.controller;

import com.reyoung.service.FlowinfosService;
import com.reyoung.tools.GetYear;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangtao on 2020-03-07.
 */

//设置待办任务提醒的功能
@Controller
public class NoticeController {

    @Resource(name = "flowinfosService")
    private FlowinfosService flowinfosService;

    //查询出所有未完成的任务,确定进行到哪一步,对计划进行提醒的功能
    @RequestMapping("/sendemailtonoticesomedoingtask.do")
    public @ResponseBody String sendemailtonoticesomedoingtask(HttpServletRequest request) {

        ServletContext context = request.getServletContext();

        if (context.getAttribute("exec")==null) {

            //定义一个线程 每隔50分钟对车间主任工作任务的当日反馈情况进行查询,没有反馈则需要邮件进行提醒
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

            ScheduledFuture<?> future = exec.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {

                    if (GetYear.getHour()==7) {//在每天的7点钟左右发送邮件提醒的功能

                        flowinfosService.findnofinishedflowinfos();

                    }

                    flowinfosService.findnofinishedflowinfos();//每间隔50分钟查询1次数据库,保持数据库的连接

                }

            }, 0, 50, TimeUnit.MINUTES);

            context.setAttribute("exec",exec);

            if (context.getAttribute("exec")!=null) {

                return "ok";

            }else {

                return "fail";

            }


        }else {

            return "ok1";

        }

    }

    //关闭邮件提醒的功能
    @RequestMapping("/closeemailnotice.do")
    public @ResponseBody String closeemailnotice(HttpServletRequest request) {

        ServletContext context = request.getServletContext();

        ScheduledExecutorService exec= (ScheduledExecutorService) context.getAttribute("exec");

        //销毁线程
        if (exec!=null) {
            exec.shutdownNow();
            context.removeAttribute("exec");
        }

        if (context.getAttribute("exec")==null) {

            return "ok";

        }else {

            return "faile";

        }

    }

}
