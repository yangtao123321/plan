package com.reyoung.serviceimpl;

import com.reyoung.dao.*;
import com.reyoung.model.*;
import com.reyoung.multidatasource.DataSource;
import com.reyoung.pager.PageBean;
import com.reyoung.service.ApproveService;
import com.reyoung.service.AttentDetailService;
import com.reyoung.service.FlowinfosService;
import com.reyoung.service.OtherDetailService;
import com.reyoung.tools.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangtao on 2020-01-16.
 */
@Service("flowinfosService")
@DataSource("dataSource")
public class FlowinfosServiceImpl implements FlowinfosService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "flowinfosDao")
    private FlowinfosDao flowinfosDao;

    @Resource(name = "approveService")
    private ApproveService approveService;

    @Resource(name = "filterPlanDao")
    private FilterPlanDao filterPlanDao;

    @Resource(name = "filterDetailDao")
    private FilterDetailDao filterDetailDao;

    @Resource(name = "repairePlanDao")
    private RepairePlanDao repairePlanDao;

    @Resource(name = "devicePlanDao")
    private DevicePlanDao devicePlanDao;

    @Resource(name = "deviceDetailDao")
    private DeviceDetailDao deviceDetailDao;

    @Resource(name = "otherPlanDao")
    private OtherPlanDao otherPlanDao;

    @Resource(name = "otherDetailService")
    private OtherDetailService otherDetailService;

    @Resource(name = "attentPlanDao")
    private AttentPlanDao attentPlanDao;

    @Resource(name = "attentDetailService")
    private AttentDetailService attentDetailService;

    @Override
    public Integer addflowinfo(Flowinfos flowinfos) {

        return flowinfosDao.addflowinfo(flowinfos);

    }

    @Override
    public Integer finddealscountbyuser(User user) {

        List<Flowinfos> flowinfoses = flowinfosDao.findallflowinfos();

        List<Flowinfos> flowinfoses2=new ArrayList<>();

        for (Flowinfos f:flowinfoses) {//筛选出未完成的流程

            if (f.getAchieve()==0) {

                flowinfoses2.add(f);

            }

        }

        List<Flowinfos> flowinfoses1=new ArrayList<>();

        for (Flowinfos f:flowinfoses2) {

            //筛选出部门来  同一个部门的流程信息进入
            if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()) {

                //筛选同一个单位的 车间主任只获取自己车间的  文件小组获取本部门下所有车间的滤芯计划

                if (f.getFlows().getFlowid()==1) {//滤芯计划

                    if (user.getPosition().getPosname().equals("单位负责人")&&user.getUsername().equals("zhangna")) {//粉针事业部张主任审批滤芯计划

                        if (f.getFlag()==user.getPosition().getApproflag()+1) {//到达张主任这里的情况

                            flowinfoses1.add(f);

                        }

                    }else if (user.getPosition().getPosname().equals("单位负责人")){

                        //307车间需要陈主任的审批
                        if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                            if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                            if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }


                        }else {

                            if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }

                        }

                    }else if (user.getPosition().getPosname().equals("部门经理")) {

                        if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()) {

                            if (user.getPosition().getApproflag()+2==f.getFlag()) {

                                flowinfoses1.add(f);

                            }

                        }


                    }

                }else if (f.getFlows().getFlowid()==2) {//维护保养计划

                    if (user.getPosition().getPosid()==2) {

                        //307车间需要陈主任的审批
                        if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                            if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                            if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }


                        }else {

                            if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);
                            }

                        }

                    }else if (user.getPosition().getPosid()==3) {

                        if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()&&user.getPosition().getApproflag()==f.getFlag()) {

                            flowinfoses1.add(f);

                        }

                    }

                }else if (f.getFlows().getFlowid()==3) {//设备类采购的流程

                    if (user.getPosition().getPosid()==2) {

                        //307车间需要陈主任的审批
                        if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                            if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                            if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }


                        }else {

                            if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);
                            }

                        }

                    }else if (user.getPosition().getPosid()==3) {

                        if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()&&user.getPosition().getApproflag()==f.getFlag()) {

                            flowinfoses1.add(f);

                        }

                    }

                }else if(f.getFlows().getFlowid()==4) {//一致性评价类计划

                    if (user.getPosition().getPosid()==2) {

                        //307车间需要陈主任的审批
                        if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                            if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                            if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }


                        }else {

                            if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);
                            }

                        }

                    }else if (user.getPosition().getPosid()==3) {

                        if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()&&user.getPosition().getApproflag()==f.getFlag()) {

                            flowinfoses1.add(f);

                        }

                    }

                }else if (f.getFlows().getFlowid()==5) {//其他采购计划

                    if (user.getPosition().getPosid()==2) {

                        //307车间需要陈主任的审批
                        if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                            if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                            if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);

                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }


                        }else {

                            if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                flowinfoses1.add(f);
                            }

                        }

                    }else if (user.getPosition().getPosid()==3) {

                        if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()&&user.getPosition().getApproflag()==f.getFlag()) {

                            flowinfoses1.add(f);

                        }

                    }

                }

            }else if (user.getPosition().getPosname().equals("公司总裁")) {//只审批计划的负责部门不是本部门的情况

                if (f.getFlows().getFlowid()==1) {//滤芯的计划

                    FilterPlan filterPlan = filterPlanDao.findfilterplanbyincident(f);

                    if (f.getUser().getSection().getSectionid()!=filterPlan.getReceive()) {

                        List<Approve> approves = approveService.findapprobyok(f);

                        for (Approve a:approves) {

                            if (a.getUser().getPosition().getPosid()==3) {

                                flowinfoses1.add(f);

                            }

                        }

                    }


                }else if (f.getFlows().getFlowid()==2) {//维护保养计划

                    RepairePlan repairePlan = repairePlanDao.findrepairedetailbyrid(f);

                    //判断申请人和接收人不是同一个部门，才能到达苗总这里来审批
                 if (f.getUser().getSection().getSectionid()!=repairePlan.getReceive()) {

                     //根据fid查询出approve中的所有审批的记录    查询出该流程所有的已审批的记录
                     List<Approve> approves = approveService.findapprobyok(f);

                     for (Approve a:approves) {

                         if (a.getUser().getPosition().getPosid()==3) {//如果审批记录里面存在部门经理审批通过的审批记录，则将该流程添加到自己的待办任务中

                             flowinfoses1.add(f);

                         }

                     }

                 }

                }else if (f.getFlows().getFlowid()==3) {//设备类采购流程

                    DevicePlan devicePlan = devicePlanDao.finddeviceplanbyflowinfo(f);

                    if (f.getUser().getSection().getSectionid()!=devicePlan.getReceive()) {

                        List<Approve> approves = approveService.findapprobyok(f);

                        for (Approve a:approves) {

                            if (a.getUser().getPosition().getPosid()==3) {

                                flowinfoses1.add(f);

                            }

                        }

                    }

                }else if (f.getFlows().getFlowid()==4) {//一致性药品采购流程

                    OtherPlan otherPlan = otherPlanDao.findotherplanbyflowinfos(f);

                    if (f.getUser().getSection().getSectionid()!=otherPlan.getReceive()) {

                        List<Approve> approves = approveService.findapprobyok(f);

                        for (Approve a:approves) {

                            if (a.getUser().getPosition().getPosid()==3) {

                                flowinfoses1.add(f);

                            }

                        }

                    }

                }else if (f.getFlows().getFlowid()==5) {//其他采购流程

                    AttentPlan attentPlan = attentPlanDao.findattentplanbyflowinfos(f);

                    if (f.getUser().getSection().getSectionid()!=attentPlan.getReceive()) {

                        List<Approve> approves = approveService.findapprobyok(f);

                        for (Approve a:approves) {

                            if (a.getUser().getPosition().getPosid()==3) {

                                flowinfoses1.add(f);

                            }
                        }

                    }

                }

            }

        }

        return flowinfoses1.size();

    }

    @Override
    public PageBean<Flowinfos> finddealsbyuser(User user,PageBean<Flowinfos> pageBean) {

        Approve approve=new Approve();

        List<Flowinfos> flowinfoses = flowinfosDao.findallflowinfos();

        List<Flowinfos> newflowinfoses=new ArrayList<>();

        for (Flowinfos f:flowinfoses) {//筛选出未完成的流程

            if (f.getAchieve()==0) {

                newflowinfoses.add(f);

            }

        }

        List<Flowinfos> flowinfoses1=new ArrayList<>();

            for (Flowinfos f:newflowinfoses) {

                //筛选出部门来  同一个部门的流程信息进入
                if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()) {

                    //筛选同一个单位的 车间主任只获取自己车间的  文件小组获取本部门下所有车间的滤芯计划

                    if (f.getFlows().getFlowid()==1) {//滤芯计划

                        if (user.getPosition().getPosname().equals("单位负责人")&&user.getUsername().equals("zhangna")) {//粉针事业部张主任审批滤芯计划

                            if (f.getFlag()==user.getPosition().getApproflag()+1) {//到达张主任这里的情况

                                flowinfoses1.add(f);

                            }

                        }else if (user.getPosition().getPosname().equals("单位负责人")){//一般的单位负责人   只获取自己单位的计划

                            //307车间需要陈主任的审批
                            if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                                if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }

                            }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                                if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }


                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (user.getPosition().getPosname().equals("部门经理")) {//部门经理审核 需获取本部门下所有单位的计划

                            if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()) {

                                if (f.getFlag()==user.getPosition().getApproflag()+2) {//流程到达部门经理这里

                                    flowinfoses1.add(f);

                                }

                            }

                        }

                    }else if (f.getFlows().getFlowid()==2) {//维护保养计划


                        if (user.getPosition().getPosid()==2) {

                            //307车间需要陈主任的审批
                            if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                                if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }

                            }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                                if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }


                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (user.getPosition().getPosid()==3) {

                            if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()&&user.getPosition().getApproflag()==f.getFlag()) {

                                flowinfoses1.add(f);

                            }


                        }



                    }else if (f.getFlows().getFlowid()==3) {

                        if (user.getPosition().getPosid()==2) {

                            //307车间需要陈主任的审批
                            if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                                if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }

                            }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                                if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }


                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (user.getPosition().getPosid()==3) {

                            if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()&&user.getPosition().getApproflag()==f.getFlag()) {

                                flowinfoses1.add(f);

                            }


                        }


                    }else {//滤芯以外的其他的计划

                        if (user.getPosition().getPosid()==2) {

                            //307车间需要陈主任的审批
                            if (f.getUser().getDepartment().getDeptname().equals("307车间")) {

                                if ((user.getUsername().equals("chenwendong"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }

                            }else if (f.getUser().getDepartment().getDeptname().equals("207车间")){

                                if ((user.getUsername().equals("sijihui"))&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }else {

                                    if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                        flowinfoses1.add(f);

                                    }

                                }


                            }else {

                                if ((user.getDepartment().getDeptid()==f.getUser().getDepartment().getDeptid())&&(user.getPosition().getApproflag()==f.getFlag())) {

                                    flowinfoses1.add(f);

                                }

                            }

                        }else if (user.getPosition().getPosid()==3) {

                            if (user.getSection().getSectionid()==f.getUser().getSection().getSectionid()&&user.getPosition().getApproflag()==f.getFlag()) {

                                flowinfoses1.add(f);

                            }

                        }

                    }

                }else if (user.getPosition().getPosname().equals("公司总裁")) {

                    if (f.getFlows().getFlowid()==1) {//滤芯的计划

                        FilterPlan filterPlan = filterPlanDao.findfilterplanbyincident(f);

                        if (f.getUser().getSection().getSectionid()!=filterPlan.getReceive()) {

                            List<Approve> approves = approveService.findapprobyok(f);

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==3) {

                                    flowinfoses1.add(f);

                                    break;

                                }

                            }

                        }

                    }else if (f.getFlows().getFlowid()==2) {//维修保养流程

                        RepairePlan repairePlan = repairePlanDao.findrepairedetailbyrid(f);

                        if (f.getUser().getSection().getSectionid()!=repairePlan.getReceive()) {

                            List<Approve> approves = approveService.findapprobyok(f);

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==3) {

                                    flowinfoses1.add(f);

                                    break;

                                }

                            }

                        }

                    }else if (f.getFlows().getFlowid()==3) {//设备类采购流程

                        DevicePlan devicePlan = devicePlanDao.finddeviceplanbyflowinfo(f);

                        if (f.getUser().getSection().getSectionid()!=devicePlan.getReceive()) {

                            List<Approve> approves = approveService.findapprobyok(f);

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==3) {

                                    flowinfoses1.add(f);

                                    break;

                                }

                            }

                        }

                    }else if (f.getFlows().getFlowid()==4) {//一致性药品采购流程

                        OtherPlan otherPlan = otherPlanDao.findotherplanbyflowinfos(f);

                        if (f.getUser().getSection().getSectionid()!=otherPlan.getReceive()) {

                            List<Approve> approves = approveService.findapprobyok(f);

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==3) {

                                    flowinfoses1.add(f);

                                    break;

                                }
                            }

                        }

                    }else if (f.getFlows().getFlowid()==5) {//其他采购流程

                        AttentPlan attentPlan = attentPlanDao.findattentplanbyflowinfos(f);

                        if (f.getUser().getSection().getSectionid()!=attentPlan.getReceive()) {

                            List<Approve> approves = approveService.findapprobyok(f);

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==3) {

                                    flowinfoses1.add(f);

                                    break;

                                }

                            }
                        }

                    }

                }

            }

        for (Flowinfos f:flowinfoses1) {

            //添加流程的到达时间   查询有没有审批的记录
            approve.setUser(user);
            approve.setFlowinfos(f);
            approve.setApproflag(0);//设置未审批的状态

            Integer res = approveService.findapprobyapp(approve);//查询一下自己的审批记录有没有提前加进去

            //不存在自己的审批,需要新增一条审批记录，并将上次处理的时间保存进去  到达时间   结束时间审批的时候新增进去
            if (res==0) {

                //根据Flowinfoid查询出所有其他的审批记录
                List<Approve> approves = approveService.findapprolistbyflowinfoid(f);

                if (approves.size()==0) {//没有一个人添加审批记录，直接添加即可

                    approve.setArrivetime(GetYear.getstrtim(f.getStartime()));

                    approveService.addapprovebyappro(approve);//添加审批记录

                }else {//存在多个审批记录的情况

                    if (f.getFlows().getFlowid()==1) {//滤芯计划

                        if (user.getPosition().getPosid()==2&&user.getUsername().equals("zhangna")) {

                            //System.out.println("zhangna"); 此时车间负责人已经审批完成，需要查询单位负责人的

                            for (Approve a:approves) {//筛选出和自己相同职位的审批记录并且已经审批完成的  一个流程同级审批只有一个审批记录

                                if (a.getUser().getPosition().getPosid()==user.getPosition().getPosid()&&a.getApproflag()==1) {

                                    approve.setArrivetime(a.getDealtime());

                                    approveService.addapprovebyappro(approve);

                                    break;//跳出本次循环体

                                }

                            }

                        }else if (user.getPosition().getPosid()==2) {

                            approve.setArrivetime(GetYear.getstrtim(f.getStartime()));

                            approveService.addapprovebyappro(approve);//添加审批记录

                        }else if (user.getPosition().getPosid()==3) {//部门经理审核 滤芯计划需要查询zhangna的审批记录获取到达的时间

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==user.getPosition().getPosid()-1&&a.getUser().getUsername().equals("zhangna")&&a.getApproflag()==1) {

                                    approve.setArrivetime(a.getDealtime());

                                    approveService.addapprovebyappro(approve);

                                    break;//跳出本次循环体

                                }

                            }

                        }else if (user.getPosition().getPosid()==4) {//总经理审批 需要获取部门经理的审批记录

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==user.getPosition().getPosid()-1&&a.getApproflag()==1) {

                                    approve.setArrivetime(a.getDealtime());

                                    approveService.addapprovebyappro(approve);

                                    break;

                                }

                            }

                        }

                    }else {

                        if (user.getPosition().getPosid()==2) {//单位负责人审核

                            approve.setArrivetime(GetYear.getstrtim(f.getStartime()));

                            approveService.addapprovebyappro(approve);//添加审批记录

                        }else if (user.getPosition().getPosid()==3) {//部门负责人审核

                            for (Approve a:approves) {

                                if (a.getUser().getPosition().getPosid()==user.getPosition().getPosid()-1&&a.getApproflag()==1) {

                                    approve.setArrivetime(a.getDealtime());

                                    approveService.addapprovebyappro(approve);

                                    break;//跳出本次循环体

                                }

                            }

                        }else if (user.getPosition().getPosid()==4) {//总经理


                            for (Approve a:approves) {


                                if (a.getUser().getPosition().getPosid()==user.getPosition().getPosid()-1&&a.getApproflag()==1) {

                                    approve.setArrivetime(a.getDealtime());

                                    approveService.addapprovebyappro(approve);

                                    break;

                                }

                            }


                        }

                    }

                }

            }

            //根据uid和Flowinfoid查询审批记录

            Approve approve1 = approveService.findapprovebyuidandfid(approve);

            f.setApprove(approve1);

        }

        List<Flowinfos> flowinfoses2=null;

        if (pageBean.getCurrentPage()==pageBean.getTotalPage()) {
            flowinfoses2= flowinfoses1.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), flowinfoses1.size());
        }else if (pageBean.getCurrentPage()<pageBean.getTotalPage()){
            flowinfoses2 = flowinfoses1.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), pageBean.getCurrentPage()*pageBean.getPageSize());
        }else {
            flowinfoses2=null;
        }

        pageBean.setList(flowinfoses2);

        return pageBean;

    }

    @Override
    public Flowinfos findflwoinfobyfid(Flowinfos flowinfos) {

        return flowinfosDao.findflwoinfobyfid(flowinfos);

    }

    @Override
    public Integer updateflowinfobyflowinfoid(Approve approve,HttpServletRequest request) {

        Flowinfos flowinfos = approve.getFlowinfos();

        User user = approve.getUser();

        if (approve.getFlowinfos().getFlows().getFlowname().equals("滤芯采购流程")) {

            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getUsername().equals("zhangna")) {//张主任审批滤芯计划   审批条件需要修改向下加一层

                approve.setDealtime(GetYear.gettimes());

                approve.setSignature(user.getSignaturepath());

                Integer res = approveService.updateapprobyuidandfid(approve);

                if (res==1) {//审批记录审核完成了

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getAgreeflag()+2);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()&&!user.getUsername().equals("zhangna")) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了

                        return 2;

                    }

                }

                //如果没有人审批过需要查询出自己的审批记录更新审批值
                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getAgreeflag());

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审批   可以指定流程是否到这一步便结束

                approve.setDealtime(GetYear.gettimes());

                approve.setSignature(user.getSignaturepath());

                Integer res = approveService.updateapprobyuidandfid(approve);

                List<String> mails=new ArrayList<>();

                for (Approve a:approveService.findapprobyok(flowinfos)) {

                    if (a.getUser().getPosition().getPosid()==2) {//获取到审批人的邮箱地址,添加到list集合中

                        mails.add(a.getUser().getEmail());

                    }

                }

                String deptname=flowinfos.getUser().getDepartment().getDeptname();

                if (res==1) {//审批记录更新完成了

                    FilterPlan filterPlan = filterPlanDao.findfilterplanbyincident(approve.getFlowinfos());

                    if (approve.getUser().getSection().getSectionid()==filterPlan.getReceive()) {

                        flowinfos.setFlag(user.getPosition().getAgreeflag()+2);

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer r = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (r==1) {

                            if (approve.getUser().getSection().getSectionid()==1) {//粉针事业部

                                if (deptname.equals("203车间")||deptname.equals("206车间")||deptname.equals("208车间")) {

                                    mails.add("sangyunguo@reyoung.com");

                                }else if (deptname.equals("204车间")||deptname.equals("205车间")||deptname.equals("207车间")) {

                                    mails.add("wangmingming@reyoung.com");

                                }else if (deptname.equals("301车间")||deptname.equals("305车间")) {

                                    mails.add("zhaowei@reyoung.com");

                                }else if (deptname.equals("304车间")||deptname.equals("306车间")||deptname.equals("307车间")) {

                                    mails.add("wangliwen@reyoung.com");

                                }else{

                                    mails.add("sangyunguo@reyoung.com");

                                }

                            }

                            //需要查询审批完成的审批的记录
                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            FilterPlan plan = filterPlanDao.findfilterplanbyincident(flowinfos);

                            plan.setFilterDetails(filterDetailDao.findfilterdetailbyfid(plan));

                            String filename = FiltersTools.makereport(plan, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意，审批意见:"+suggest+"。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            List<String> list=new ArrayList<>();

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),list,subject,context,file);

                            //file.delete();

                            return 1;

                        }

                    }else {

                        flowinfos.setFlag(user.getPosition().getAgreeflag()+2);

                        flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                    }

                }

            }else if (user.getPosition().getPosid()==4) {//公司总裁审批



            }

        }else if (flowinfos.getFlows().getFlowname().equals("维修保养流程")) {

            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了

                        return 2;

                    }

                }

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getAgreeflag());

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审批   可以指定流程是否到这一步便结束

                approve.setDealtime(GetYear.gettimes());

                approve.setSignature(user.getSignaturepath());

                Integer res = approveService.updateapprobyuidandfid(approve);

                List<String> mails=new ArrayList<>();

                for (Approve a:approveService.findapprobyok(flowinfos)) {

                    if (a.getUser().getPosition().getPosid()==2) {//获取到审批人的邮箱地址,添加到list集合中

                        mails.add(a.getUser().getEmail());

                    }

                }

                String deptname=flowinfos.getUser().getDepartment().getDeptname();

                if (res==1) {//审批记录更新完成了

                    RepairePlan repairePlan = repairePlanDao.findrepairedetailbyrid(flowinfos);

                    if (approve.getUser().getSection().getSectionid()==repairePlan.getReceive()) {//同一个部门,签字到部门经理就可以

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer r = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (r==1) {//审批成功了，需要发送一封附件给相关人员

                            if (approve.getUser().getSection().getSectionid()==1) {//粉针事业部

                                if (deptname.equals("203车间")||deptname.equals("206车间")||deptname.equals("208车间")) {

                                    mails.add("sangyunguo@reyoung.com");

                                }else if (deptname.equals("204车间")||deptname.equals("205车间")||deptname.equals("207车间")) {

                                    mails.add("wangmingming@reyoung.com");

                                }else if (deptname.equals("301车间")||deptname.equals("305车间")) {

                                    mails.add("zhaowei@reyoung.com");

                                }else if (deptname.equals("304车间")||deptname.equals("306车间")||deptname.equals("307车间")) {

                                    mails.add("wangliwen@reyoung.com");

                                }else{

                                    mails.add("sangyunguo@reyoung.com");

                                }

                            }

                            List<Approve> approves1=approveService.findapprobyok(flowinfos);

                            RepairePlan plan = repairePlanDao.findrepairedetailbyrid(flowinfos);

                            String filename = RepaireTools.makereport(plan, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意，审批意见:"+suggest+"。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            List<String> list=new ArrayList<>();

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),list,subject,context,file);

                            //file.delete();

                            return 1;

                        }

                    }else {//需要签字到公司总经理

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//默认设置为完成的状态

                        Integer res1 = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (res1==1) {

                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            RepairePlan plan = repairePlanDao.findrepairedetailbyrid(flowinfos);

                            String filename = RepaireTools.makereport1(plan, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意,还需您找苗总签字。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            List<String> list=new ArrayList<>();

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),list,subject,context,file);

                            //邮件发送完成后将文件删除
                            //file.delete();

                            return 1;

                        }

                    }

                }

            }else if (user.getPosition().getPosid()==4) {//公司总裁审批



            }

        }else if (flowinfos.getFlows().getFlowname().equals("设备类采购流程")) {

            //查询所有的关于审批的记录   根据flowinfos id
            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了

                        return 2;

                    }


                }

                //如果没有人审批过需要查询出自己的审批记录更新审批值
                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getAgreeflag());

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审批   可以指定流程是否到这一步便结束

                approve.setDealtime(GetYear.gettimes());

                approve.setSignature(user.getSignaturepath());

                Integer res = approveService.updateapprobyuidandfid(approve);

                List<String> mails=new ArrayList<>();

                for (Approve a:approveService.findapprobyok(flowinfos)) {

                    if (a.getUser().getPosition().getPosid()==2) {//获取到审批人的邮箱地址,添加到list集合中

                        mails.add(a.getUser().getEmail());

                    }

                }

                String deptname=flowinfos.getUser().getDepartment().getDeptname();

                if (res==1) {//审批记录更新完成了

                    DevicePlan devicePlan = devicePlanDao.finddeviceplanbyflowinfo(flowinfos);

                    if (approve.getUser().getSection().getSectionid()==devicePlan.getReceive()) {//同一个部门,签字到部门经理就可以

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer r = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (r==1) {//审批成功了，需要发送一封附件给相关人员

                            if (approve.getUser().getSection().getSectionid()==1) {//粉针事业部

                                if (deptname.equals("203车间")||deptname.equals("206车间")||deptname.equals("208车间")) {

                                    mails.add("sangyunguo@reyoung.com");

                                }else if (deptname.equals("204车间")||deptname.equals("205车间")||deptname.equals("207车间")) {

                                    mails.add("wangmingming@reyoung.com");

                                }else if (deptname.equals("301车间")||deptname.equals("305车间")) {

                                    mails.add("zhaowei@reyoung.com");

                                }else if (deptname.equals("304车间")||deptname.equals("306车间")||deptname.equals("307车间")) {

                                    mails.add("wangliwen@reyoung.com");

                                }else{

                                    mails.add("sangyunguo@reyoung.com");

                                }

                            }

                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            DevicePlan devicePlan1 = devicePlanDao.finddeviceplanbyflowinfo(flowinfos);

                            devicePlan1.setDeviceDetails(deviceDetailDao.finddevicedetaillistbydeviceplan(devicePlan1));

                            String filename = DeviceTools.makereport(devicePlan1, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意，审批意见:"+suggest+"。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            List<String> list=new ArrayList<>();

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),list,subject,context,file);

                            //file.delete();

                            return 1;

                        }

                    }else {//需要签字到公司总经理

                        List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer res1 = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (res1==1) {

                            DevicePlan devicePlan1 = devicePlanDao.finddeviceplanbyflowinfo(flowinfos);

                            devicePlan1.setDeviceDetails(deviceDetailDao.finddevicedetaillistbydeviceplan(devicePlan1));

                            String filename = DeviceTools.makereport1(devicePlan1, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意,还需您找苗总签字。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            List<String> list=new ArrayList<>();

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),list,subject,context,file);

                            //file.delete();

                            return 1;

                        }

                    }

                }

            }else if (user.getPosition().getPosid()==4) {//公司总裁审批

                approve.setDealtime(GetYear.gettimes());

                approve.setSignature(user.getSignaturepath());

                Integer res = approveService.updateapprobyuidandfid(approve);

                if (res==1) {//审批记录更新完成了

                    DevicePlan devicePlan = devicePlanDao.finddeviceplanbyflowinfo(flowinfos);

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer r = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (r==1) {//审批成功了，需要发送一封附件给相关人员

                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            DevicePlan devicePlan1 = devicePlanDao.finddeviceplanbyflowinfo(flowinfos);

                            devicePlan1.setDeviceDetails(deviceDetailDao.finddevicedetaillistbydeviceplan(devicePlan1));

                            DeviceTools.makereport(devicePlan1,flowinfos,approves1,request);

                            File file=new File("D:\\"+flowinfos.getFlowinfoid()+flowinfos.getFlowabstract()+".pdf");

                            //Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3","yangtao@reyoung.com","","流程审批通过","你好,附件是审批完成的计划表",file);

                            file.delete();

                            return 1;

                        }

                }

            }


        }else if (flowinfos.getFlows().getFlowname().equals("一致性药品采购流程")) {

            //查询所有的关于审批的记录   根据flowinfos id
            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了

                        return 2;

                    }

                }

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getAgreeflag());

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审批   可以指定流程是否到这一步便结束

                approve.setDealtime(GetYear.gettimes());

                approve.setSignature(user.getSignaturepath());

                Integer res = approveService.updateapprobyuidandfid(approve);

                List<String> mails=new ArrayList<>();

                for (Approve a:approveService.findapprobyok(flowinfos)) {

                    if (a.getUser().getPosition().getPosid()==2) {//获取到审批人的邮箱地址,添加到list集合中

                        mails.add(a.getUser().getEmail());

                    }

                }

                String deptname=flowinfos.getUser().getDepartment().getDeptname();

                if (res==1) {

                    OtherPlan otherPlan = otherPlanDao.findotherplanbyflowinfos(flowinfos);

                    if (approve.getUser().getSection().getSectionid()==otherPlan.getReceive()) {//同一个部门,签字到部门经理就可以

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer r = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (r==1) {//审批成功了，需要发送一封附件给相关人员

                            if (approve.getUser().getSection().getSectionid()==1) {//粉针事业部

                                if (deptname.equals("203车间")||deptname.equals("206车间")||deptname.equals("208车间")) {

                                    mails.add("sangyunguo@reyoung.com");

                                }else if (deptname.equals("204车间")||deptname.equals("205车间")||deptname.equals("207车间")) {

                                    mails.add("wangmingming@reyoung.com");

                                }else if (deptname.equals("301车间")||deptname.equals("305车间")) {

                                    mails.add("zhaowei@reyoung.com");

                                }else if (deptname.equals("304车间")||deptname.equals("306车间")||deptname.equals("307车间")) {

                                    mails.add("wangliwen@reyoung.com");

                                }else{

                                    mails.add("sangyunguo@reyoung.com");

                                }

                            }

                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            OtherPlan otherPlan1 = otherPlanDao.findotherplanbyflowinfos(flowinfos);

                            otherPlan1.setOtherDetails(otherDetailService.findotherdetailbyotherplan(otherPlan1));

                            String filename = OtherTools.makereport(otherPlan1, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意，审批意见:"+suggest+"。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),mails,subject,context,file);

                            file.delete();

                            return 1;

                        }

                    }else {//需要签字到公司总经理

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer res1 = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (res1==1) {

                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            OtherPlan otherPlan1 = otherPlanDao.findotherplanbyflowinfos(flowinfos);

                            otherPlan1.setOtherDetails(otherDetailService.findotherdetailbyotherplan(otherPlan1));

                            String filename = OtherTools.makereport1(otherPlan1, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意,还需您找苗总签字。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),mails,subject,context,file);

                            file.delete();

                            return 1;

                        }

                    }

                }

            }else if (user.getPosition().getPosid()==4) {//公司总裁审批



            }

        }else if (flowinfos.getFlows().getFlowname().equals("其他采购流程")) {

            //查询所有的关于审批的记录   根据flowinfos id
            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了

                        return 2;

                    }

                }

                //如果没有人审批过需要查询出自己的审批记录更新审批值
                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getAgreeflag());

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审批   可以指定流程是否到这一步便结束

                approve.setDealtime(GetYear.gettimes());

                approve.setSignature(user.getSignaturepath());

                Integer res = approveService.updateapprobyuidandfid(approve);

                List<String> mails=new ArrayList<>();

                for (Approve a:approveService.findapprobyok(flowinfos)) {

                    if (a.getUser().getPosition().getPosid()==2) {//获取到审批人的邮箱地址,添加到list集合中

                        mails.add(a.getUser().getEmail());

                    }

                }

                String deptname=flowinfos.getUser().getDepartment().getDeptname();

                if (res==1) {//审批记录更新完成了

                    AttentPlan attentPlan = attentPlanDao.findattentplanbyflowinfos(flowinfos);

                    if (approve.getUser().getSection().getSectionid()==attentPlan.getReceive()) {//同一个部门,签字到部门经理就可以

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer r = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (r==1) {//审批成功了，需要发送一封附件给相关人员

                            if (approve.getUser().getSection().getSectionid()==1) {//粉针事业部

                                if (deptname.equals("203车间")||deptname.equals("206车间")||deptname.equals("208车间")) {

                                    mails.add("sangyunguo@reyoung.com");

                                }else if (deptname.equals("204车间")||deptname.equals("205车间")||deptname.equals("207车间")) {

                                    mails.add("wangmingming@reyoung.com");

                                }else if (deptname.equals("301车间")||deptname.equals("305车间")) {

                                    mails.add("zhaowei@reyoung.com");

                                }else if (deptname.equals("304车间")||deptname.equals("306车间")||deptname.equals("307车间")) {

                                    mails.add("wangliwen@reyoung.com");

                                }else{

                                    mails.add("sangyunguo@reyoung.com");

                                }

                            }

                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            AttentPlan attentPlan1 = attentPlanDao.findattentplanbyflowinfos(flowinfos);

                            attentPlan1.setAttentDetails(attentDetailService.findattentdetailbyattentplan(attentPlan1));

                            String filename = AttentTools.makereport(attentPlan1, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意，审批意见:"+suggest+"。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),mails,subject,context,file);

                            file.delete();

                            return 1;

                        }

                    }else {//需要签字到公司总经理

                        flowinfos.setFlag(user.getPosition().getAgreeflag());

                        flowinfos.setAchieve(1);//流程完成标志   1 表示全部同意 2 表示单位负责人退回 3 表示张主任退回 4 表示部门经理退回 5 表示总经理退回

                        Integer res1 = flowinfosDao.updateflowinfobyflowinfoid(flowinfos);

                        if (res1==1) {

                            List<Approve> approves1 = approveService.findapprobyok(flowinfos);

                            AttentPlan attentPlan1 = attentPlanDao.findattentplanbyflowinfos(flowinfos);

                            attentPlan1.setAttentDetails(attentDetailService.findattentdetailbyattentplan(attentPlan1));

                            String filename = AttentTools.makereport1(attentPlan1, flowinfos, approves1, request);

                            String s = request.getSession().getServletContext().getRealPath(File.separator+"makereports");

                            File file=new File(s,filename);

                            String subject="计划通过提醒";

                            String suggest=(approve.getSuggest()==null||approve.getSuggest().trim().equals(""))?"同意":approve.getSuggest();

                            String context="<font face='Terminal' style='font-size:19px'><span style='color: black;'>"+flowinfos.getUser().getTruename()+",您好。</span><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贵单位提报的计划 &nbsp;&nbsp;&nbsp;实例号:"+flowinfos.getFlowinfoid()+"&nbsp;&nbsp;&nbsp;名称:<span style='color:#00a400;'>"+flowinfos.getFlowabstract()+"</span>&nbsp;&nbsp;&nbsp;已由<span style='color: red;'>"+user.getTruename()+"</span>同意,还需您找苗总签字。<br><br>&nbsp;&nbsp;&nbsp;详情请参考附件!</font>";

                            Mail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",flowinfos.getUser().getEmail(),mails,subject,context,file);

                            file.delete();

                            return 1;

                        }

                    }

                }

            }else if (user.getPosition().getPosid()==4) {//公司总裁审批



            }

        }

        return null;

    }

    @Override
    public Integer approbackflowinfobyflowinfoid(Approve approve) {

        Flowinfos flowinfos = approve.getFlowinfos();

        User user = approve.getUser();

        if (flowinfos.getFlows().getFlowname().equals("滤芯采购流程")) {

            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getUsername().equals("zhangna")) {

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(3);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了
                        return 2;

                    }

                }

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(2);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag()+2);

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(4);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }


            }else if (user.getPosition().getPosid()==4) {//总经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag()+2);

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(5);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }

        }else if (flowinfos.getFlows().getFlowname().equals("维修保养流程")) {

            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了
                        return 2;

                    }

                }

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(2);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(4);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }


            }else if (user.getPosition().getPosid()==4) {//总经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(5);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }

        }else if (flowinfos.getFlows().getFlowname().equals("设备类采购流程")) {

            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了
                        return 2;

                    }

                }

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(2);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(4);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }


            }else if (user.getPosition().getPosid()==4) {//总经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(5);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }

        }else if (flowinfos.getFlows().getFlowname().equals("一致性药品采购流程")) {


            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了
                        return 2;

                    }

                }

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(2);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(4);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==4) {//总经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(5);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }


        }else if (flowinfos.getFlows().getFlowname().equals("其他采购流程")) {//其他采购流程

            List<Approve> approves = approveService.findapprolistbyflowinfoid(flowinfos);

            if (user.getPosition().getPosid()==2) {

                for (Approve a:approves) {

                    if (a.getUser().getPosition().getPosid()==2&&a.getApproflag()>0&&a.getUser().getUid()!=user.getUid()) {//筛选出单位负责人并且已经审批过得

                        //此时已经有人审批过了，返回 2表示有人已经审批过了
                        return 2;

                    }

                }

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(2);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==3) {//部门经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(4);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }else if (user.getPosition().getPosid()==4) {//总经理审核

                Approve approve1 = approveService.findapprovebyuidandfid(approve);

                //处理事件
                approve1.setDealtime(GetYear.gettimes());
                approve1.setSignature(approve.getUser().getSignaturepath());
                approve1.setSuggest(approve.getSuggest());
                approve1.setApproflag(approve.getApproflag());

                //更新审批操作
                Integer res = approveService.updateapprobyuidandfid(approve1);

                if (res==1) {//审批记录更新成功后，更新flowinfo中的审批记录

                    Flowinfos f=approve.getFlowinfos();
                    f.setFlag(approve.getUser().getPosition().getBackflag());
                    f.setAchieve(5);

                    Integer r = flowinfosDao.updateflowinfobyflowinfoid(f);

                    if (r==1) {

                        return 1;

                    }

                }

            }

        }

        return null;

    }

    @Override
    public Integer findflowinfoscountbyuid(User user) {

        List<Flowinfos> flowinfoses = flowinfosDao.findflowinfosbyuid(user);

        return flowinfoses.size();

    }

    @Override
    public PageBean<Flowinfos> findflowinfosbyuid(User user,PageBean<Flowinfos> pageBean) {

        List<Flowinfos> flowinfoses = flowinfosDao.findflowinfosbyuid(user);

        for (Flowinfos f:flowinfoses) {

            f.setStartime1(GetYear.getstrtim(f.getStartime()));

        }


        List<Flowinfos> flowinfoses1=null;

        if (pageBean.getCurrentPage()==pageBean.getTotalPage()) {
            flowinfoses1= flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), flowinfoses.size());
        }else if (pageBean.getCurrentPage()<pageBean.getTotalPage()){
            flowinfoses1 = flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), pageBean.getCurrentPage()*pageBean.getPageSize());
        }else {
            flowinfoses1=null;
        }

        pageBean.setList(flowinfoses1);

        return pageBean;

    }

    @Override
    public Integer findflowinfoedcount(User user) {

        List<Flowinfos> flowinfoses = flowinfosDao.findealflowinfos();

        List<Flowinfos> flowinfoses1=new ArrayList<>();

        for (Flowinfos f:flowinfoses) {

            List<Approve> approves = approveService.findapprolistbyflowinfoid(f);

            for (Approve a:approves) {

                if (user.getUid()==a.getUser().getUid()&&a.getApproflag()>0) {//查询自己审批的流程

                    flowinfoses1.add(f);

                }

            }

        }

        return flowinfoses1.size();

    }

    @Override
    public PageBean<Flowinfos> findflowinfoedlist(PageBean<Flowinfos> pageBean, User user) {

        List<Flowinfos> flowinfoses = flowinfosDao.findealflowinfos();

        List<Flowinfos> flowinfoses1=new ArrayList<>();

        for (Flowinfos f:flowinfoses) {

            List<Approve> approves = approveService.findapprolistbyflowinfoid(f);

            for (Approve a:approves) {

                if (user.getUid()==a.getUser().getUid()&&a.getApproflag()>0) {//查询自己审批的流程

                    flowinfoses1.add(f);

                }

            }

        }

        //根据uid、fid查询approve信息
        Approve approve=new Approve();

        approve.setUser(user);

        for (Flowinfos f:flowinfoses1) {

            approve.setFlowinfos(f);

            f.setApprove(approveService.findapprovebyuidandfid(approve));

        }

        List<Flowinfos> flowinfoses2=null;

        if (pageBean.getCurrentPage()==pageBean.getTotalPage()) {
            flowinfoses2= flowinfoses1.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), flowinfoses1.size());
        }else if (pageBean.getCurrentPage()<pageBean.getTotalPage()){
            flowinfoses2 = flowinfoses1.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), pageBean.getCurrentPage()*pageBean.getPageSize());
        }else {
            flowinfoses2=null;
        }

        pageBean.setList(flowinfoses2);

        return pageBean;

    }

    @Override
    public Integer findapplyflowinfoedbyuid(User user) {

        List<Flowinfos> flowinfoses = flowinfosDao.findapplyflowinfoedlist(user);

        return flowinfoses.size();

    }

    @Override
    public PageBean<Flowinfos> findapplyflowinfoedlistbyuid(PageBean<Flowinfos> pageBean, User user) {

        List<Flowinfos> flowinfoses = flowinfosDao.findapplyflowinfoedlist(user);

        for (Flowinfos f:flowinfoses) {

            f.setStartime1(GetYear.getstrtim(f.getStartime()));

        }

        List<Flowinfos> flowinfoses1=null;

        if (pageBean.getCurrentPage()==pageBean.getTotalPage()) {
            flowinfoses1= flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), flowinfoses.size());
        }else if (pageBean.getCurrentPage()<pageBean.getTotalPage()){
            flowinfoses1 = flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), pageBean.getCurrentPage()*pageBean.getPageSize());
        }else {
            flowinfoses1=null;
        }

        pageBean.setList(flowinfoses1);

        return pageBean;

    }

    @Override
    public Integer delflowinfosbyfid(Flowinfos flowinfos) {
        return flowinfosDao.delflowinfosbyfid(flowinfos);
    }

    @Override
    public Integer findallflowinfocount() {

     return  flowinfosDao.findallflowinfos().size();

    }

    @Override
    public PageBean<Flowinfos> findallflowinfolist(PageBean<Flowinfos> pageBean) {

        List<Flowinfos> flowinfoses = flowinfosDao.findallflowinfos();

        for (Flowinfos f:flowinfoses) {

            f.setStartime1(GetYear.getstrtim(f.getStartime()));

        }

        List<Flowinfos> flowinfoses1=null;

        if (pageBean.getCurrentPage()==pageBean.getTotalPage()) {
            flowinfoses1= flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), flowinfoses.size());
        }else if (pageBean.getCurrentPage()<pageBean.getTotalPage()){
            flowinfoses1 = flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), pageBean.getCurrentPage()*pageBean.getPageSize());
        }else {
            flowinfoses1=null;
        }

        pageBean.setList(flowinfoses1);

        return pageBean;

    }

    @Override
    public PageBean<Flowinfos> findflowinfosbyid(Flowinfos flowinfos,PageBean<Flowinfos> pageBean) {

        List<Flowinfos> flowinfoses=new ArrayList<>();

        if (flowinfosDao.findflwoinfobyfid(flowinfos)!=null) {

            flowinfoses.add(flowinfosDao.findflwoinfobyfid(flowinfos));

        }

        for (Flowinfos f:flowinfoses) {

            f.setStartime1(GetYear.getstrtim(f.getStartime()));

        }

        List<Flowinfos> flowinfoses1=null;

        if (pageBean.getCurrentPage()==pageBean.getTotalPage()) {
            flowinfoses1= flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), flowinfoses.size());
        }else if (pageBean.getCurrentPage()<pageBean.getTotalPage()){
            flowinfoses1 = flowinfoses.subList((pageBean.getCurrentPage() - 1) * pageBean.getPageSize(), pageBean.getCurrentPage()*pageBean.getPageSize());
        }else {
            flowinfoses1=null;
        }

        pageBean.setList(flowinfoses1);

        return pageBean;

    }

    @Override
    public List<Flowinfos> findnofinishedflowinfos() {

        try {

        List<User> users = userDao.findnoticeuser();

        for (User u:users) {

            List<Flowinfos> flowinfoses = flowinfosDao.findnofinishedflowinfos();

            List<Flowinfos> flowinfoses1=new ArrayList<>();

            for (Flowinfos flowinfos:flowinfoses) {

                if (flowinfos.getUser().getSection().getSectionid()==1) {//粉针事业部

                    if (flowinfos.getFlows().getFlowid()==1) {//滤芯计划,张主任需要审批功能需要+1;

                        if ((flowinfos.getFlag()==u.getPosition().getApproflag()+1)&&(u.getUsername().equals("zhangna"))&&(u.getPosition().getPosid()==2)) {//文件小组负责人

                            flowinfoses1.add(flowinfos);


                        }else if ((flowinfos.getFlag()==u.getPosition().getApproflag())&&(u.getPosition().getPosid()==2)) {//单位负责人

                            if (u.getDepartment().getDeptid()==flowinfos.getUser().getDepartment().getDeptid()) {

                                flowinfoses1.add(flowinfos);

                            }

                        }else if ((flowinfos.getFlag()==u.getPosition().getApproflag()+2)&&(u.getPosition().getPosid()==3)) {//部门经理

                            flowinfoses1.add(flowinfos);

                        }

                    }else {

                        if ((flowinfos.getFlag()==u.getPosition().getApproflag())&&(u.getPosition().getPosid()==2)) {//单位负责人

                            if (u.getDepartment().getDeptid()==flowinfos.getUser().getDepartment().getDeptid()) {

                                flowinfoses1.add(flowinfos);

                            }


                        }else if ((flowinfos.getFlag()==u.getPosition().getApproflag())&&(u.getPosition().getPosid()==3)) {

                            flowinfoses1.add(flowinfos);

                        }
                    }
                }

            }

            //遍历完成后需要将flowinfos添加到user中
            u.setFlowinfoses(flowinfoses1);

        }

        //筛选出未处理计划大于等于10条的人员,每天发送一次邮件的提醒
        List<User> list=new ArrayList<>();

        for (User user:users) {

            if (user.getFlowinfoses().size()>=3) {

                list.add(user);

            }

        }

            for (User user:list) {

                String s = GetYear.getnowymd(new Date());

                String s1 =user.getFdate()==null?"":GetYear.getnowymd(user.getFdate());

                if (user.getFdate()==null||(!s.equals(s1))) {//当fdate==null 或者 日期跟今天的不一样 可以发送邮件提醒

                    String subject="粉针BPM计划审批提醒";

                    String planinfo="";

                    Integer c=1;

                    for (Flowinfos flowinfos:user.getFlowinfoses()) {

                        planinfo=planinfo+"第"+c+"条:&nbsp;<span style='color:red;font-weight:bold'>"+flowinfos.getFlowabstract()+"</span>,&nbsp;提报人:"+flowinfos.getPerson()+",&nbsp;提报单位:"+flowinfos.getUser().getDepartment().getDeptname()+"<br /><br />&nbsp;&nbsp;&nbsp;";

                        c++;

                    }

                    String context="<font face='楷体' style='font-size:19px'><span style='font-weight:bold'>"+user.getTruename()+",您好!</span><br /><br />&nbsp;&nbsp;&nbsp;您有"+user.getFlowinfoses().size()+"条计划需要审批,信息如下:<br /><br />&nbsp;&nbsp;&nbsp;"+planinfo+"<br /><br />&nbsp;&nbsp;&nbsp;请及时处理!</font>";

                    TextMail1.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",user,"",subject,context);

                    user.setFdate(new Date());

                    Integer r = userDao.updatefdate(user);

                }

            }

        } catch (Exception e) {

            List<String> list=new ArrayList<>();

            list.add("yangtao@reyoung.com");

            TextMail.sendMail("yangtao@reyoung.com","YANGyang136164","192.168.8.3",null,"yangtao","待办任务提醒失败!","计划审批提醒邮件发送失败!");

        }finally {

            return null;

        }



    }

}
