<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yangtao
  Date: 2020-01-20
  Time: 下午 2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <title>滤芯计划审批页面</title>

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/xcConfirm.css">

    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.cookie.js" ></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/xcConfirm.js"></script>

    <script type="text/javascript" language="JavaScript">

        $(function() {

            //没
            $(document).on('click','.refuse',function() {

                var flowinfosid=$(".flowinfoid").text();

                var suggest=$(".suggestval").val().trim();

                //发送ajax请求后台服务器
                $.ajax({
                    url:"${pageContext.request.contextPath}/approbackflowinfobyuser.do",
                    type:"post",
                    async:false,
                    data:{"flowinfos.flowinfoid":flowinfosid,"suggest":suggest,"approflag":"2"},
                    dataType:"json",
                    success:function(data) {

                        if(data=='1') {

                            var userAgent = navigator.userAgent;
                            if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") != -1) {
                                location.href = "about:blank";
                            } else {
                                window.opener = null;
                                window.open('', '_self');
                            }

                            window.close();

                        }else {

                            alert("操作失败!");

                        }


                    }


                });

            });

            //同意了
            $(document).on('click','.agree',function() {

                var flowinfosid=$(".flowinfoid").text();

                var suggest=$(".suggestval").val().trim();

                var txt=  "请输入密码";
                window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.input,{

                    onOk:function(v){

                        if(v.trim()=='') {

                            alert("密码不能为空!");

                        }else {

                            var username=$(".username").text().trim();

                            var password=v;

                            var res='';

                            //发送ajax校验密码的合法性

                            $.ajax({

                                url:"${pageContext.request.contextPath}/login1.do",
                                type:"post",
                                async:false,
                                data:{"username":username,"password":password},
                                dataType:"json",
                                success:function(data) {

                                    if(data=='loginsuccess') {//登录成功了

                                        res='1';

                                    }else {//登录失败了

                                        res='0';

                                    }

                                }


                            });

                            if(res=='1') {

                                //发送ajax请求后台服务器
                                //发送ajax请求后台服务器
                                $.ajax({
                                    url:"${pageContext.request.contextPath}/agreeflowinfobyuser.do",
                                    type:"post",
                                    beforeSend: function (){

                                        $(".suggestval").after("<div class='load'><img src='${pageContext.request.contextPath}/picture/load.gif' /></div>");

                                    },
                                    async:true,
                                    data:{"flowinfos.flowinfoid":flowinfosid,"suggest":suggest,"approflag":"1"},
                                    dataType:"json",
                                    success:function(data) {

                                        $(".load").remove();

                                        if(data=='1') {//关闭当前页

                                            var userAgent = navigator.userAgent;
                                            if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") != -1) {
                                                location.href = "about:blank";
                                            } else {
                                                window.opener = null;
                                                window.open('', '_self');
                                            }

                                            window.close();

                                        }

                                    },
                                    error:function () { //请求数据失败
                                        alert("服务器繁忙!");
                                    }

                                });

                            }else{

                                alert("密码输入错误!");

                            }

                        }



                    }
                });


            });

        });

    </script>

    <style type="text/css">

        *{
             margin-top: 0px;
             margin-left: 0px;
             margin-right: 0px;
             font-family: "楷体";


         }

        .top{
            border: 1px solid #009ae3;
            height: 60px;
            line-height: 60px;
            text-align: center;
            background-color: #009ae3;
            color: white;
            font-size: 26px;


        }

        .mai{

            position: relative;
            border: 1px solid #c0bfbe;
            margin-top: 10px;
            height: auto;
            width: 80%;
            margin-left: 10%;
            background-color: #f4f3f2;

        }

        .tb,.tb1,.tb2{
            position:relative;
            margin-top: 1%;
            border-collapse: collapse;
            border: none;
            width: 100%;
            margin-bottom: 3%;
            font-size: 15px;
        }

        .tb td,.tb1 td,.tb2 td{

            font-family: "楷体";
            border: 1px solid #c0bfbe;
            text-align: center;
            height: 35px;
            font-size: 15px;



        }

        .tr1{

            background-color: white;

        }

        .tr1:hover{

            background-color: rgba(240, 255, 239, 0.50);

            color:blue;

            font-weight: bold;

        }

        .suggest{

            position: relative;
            height: 35px;
            line-height: 35px;
            margin-left: 1%;
            text-align: center;
            float: left;
            font-weight: bold;
            font-size: 16px;

        }

        .suggestval{

            position: relative;
            height: 35px;
            line-height: 35px;
            margin-left: 1%;
            width: 90%;
            text-align: center;
            border: 1px solid #c6c6c6;


        }

        .agree{


            position: relative;
            border: 1px solid #00a1f5;
            height: 35px;
            background-color: #00a1f5;
            color: white;
            outline-style: none;
            width: 10%;
            margin-top: 3%;
            margin-left: 10%;


            cursor: pointer;



        }

        .refuse{

            position: relative;
            border: 1px solid #00a1f5;
            height: 35px;
            background-color: #00a1f5;
            color: white;
            outline-style: none;
            width: 10%;
            margin-top: 3%;

            margin-left: 30%;


            cursor: pointer;


            margin-bottom: 5%;

        }

        .agree:hover,.refuse:hover{

            border: 1px solid #005796;
            background-color: #005796;

        }

        .load{

            position: relative;
            height: 10px;
            text-align: center;
            margin-top: 3%;

        }

        ::-ms-clear, ::-ms-reveal { display: none; }

        .sgBtn{width: 135px; height: 35px; line-height: 35px; margin-left: 10px; margin-top: 10px; text-align: center; background-color: #0095D9; color: #FFFFFF; float: left; border-radius: 5px;}

        .a{
            background-color: white;
        }

        .a:hover{

            background-color: rgba(240, 255, 239, 0.50);

            color:blue;

            font-weight: bold;

        }

    </style>

</head>

<body>

<div class="top">滤芯计划审核页面</div>

<div class="username" hidden="hidden">${sessionScope.get("userinfo").username}</div>

<div class="mai">

    <div hidden="hidden" class="flowinfoid">${flowinfos.flowinfoid}</div>

    <div style="font-weight: bold;margin-top: 1%;margin-left: 1%;font-size: 16px">申请信息</div>

    <table class="tb">

        <tr>
            <td width="15%" style="border-left: none">申请单位</td>
            <td width="20%" class="a">${filterpla.user.department.deptname}</td>
            <td width="10%">提报人</td>
            <td width="15%" class="a">${filterpla.applyperson}</td>
            <td width="10%">申请时间</td>
            <td width="25%" class="a" style="border-right: none">${filterpla.applytime}</td>
        </tr>

        <tr>

            <td style="border-left: none">流程内容摘要</td>
            <td colspan="2" class="a">${filterpla.applyabstract}</td>
            <td>申请原因</td>
            <td colspan="2" class="a" style="border-right: none">${filterpla.applyreason}</td>


        </tr>

        <tr>

            <td style="border-left: none">采购要求</td>
            <td colspan="2" class="a">${filterpla.buyrequires}</td>
            <td>采购单位</td>
            <td class="a" style="border-right: none" colspan="2">${sec.sectionname}</td>

        </tr>

    </table>

    <div style="font-weight: bold;margin-top: 1%;margin-left: 1%;font-size: 16px">采购详情</div>

    <table class="tb1">

        <tr style="font-weight: bold">
            <td width="15%" style="border-left: none">名称</td>
            <td width="15%">尺寸</td>
            <td width="15%">精度</td>
            <td width="15%">接口</td>
            <td width="15%">膜层数</td>
            <td width="15%">用途</td>
            <td width="10%" style="border-right: none">数量</td>
        </tr>

        <c:forEach items="${filterpla.filterDetails}" var="d">

            <tr class="tr1">

            <td style="border-left: none">${d.fdetailname}</td>
            <td>${d.fdetailsize}</td>
            <td>${d.fdgree}</td>
            <td>${d.fdetailinterface}</td>
            <td>${d.fherpin}</td>
            <td>${d.useing}</td>
            <td style="border-right: none">${d.fdetailnum}</td>

            </tr>

        </c:forEach>

    </table>

    <div class="suggest">审批意见</div>

    <input class="suggestval" type="text" />

    <div style="clear: left"></div>

    <input class="refuse" type="button" value="拒绝" />

    <input class="agree" type="button" value="同意" />

    <table class="tb2" style="font-size: 15px">

        <tr>
            <td width="10%" style="border-left: none;font-weight: bold">审批环节</td>
            <td width="10%" style="font-weight: bold">审批人</td>
            <td width="10%" style="font-weight: bold">审批结果</td>
            <td width="25%" style="font-weight: bold">审批时间</td>
            <td width="35%" style="font-weight: bold">审批意见</td>
            <td width="10%" style="border-right: none;font-weight: bold">审批签名</td>
        </tr>

        <c:forEach items="${appro}" var="approve">

            <c:choose>

                <c:when test="${approve.user.position.posid==1}">

                    <tr>

                        <td style="border-left: none">发起步骤</td>
                        <td>${approve.user.truename}</td>
                        <td></td>
                        <td>${approve.dealtime}</td>
                        <td style="font-family: 楷体;color: #ff2a45;background-color: white">${approve.suggest}</td>
                        <td style="height: 50px" style="border-right: none"></td>

                    </tr>


                </c:when>

                <c:otherwise>

                    <tr>

                        <c:choose>

                            <c:when test="${approve.user.position.posid==2}">

                                <td style="border-left: none">单位负责人审核</td>
                                <td>${approve.user.truename}</td>
                                <c:choose>

                                    <c:when test="${approve.approflag==1}">

                                        <td style="color: #00aa00;font-family: 仿宋;font-weight: bold">同意</td>

                                    </c:when>


                                    <c:when test="${approve.approflag==2}">

                                        <td style="color: #e70000;font-family: 仿宋;font-weight: bold">退回</td>

                                    </c:when>

                                </c:choose>
                                <td>${approve.dealtime}</td>
                                <td style="color: #e70000;font-weight: bold">${approve.suggest}</td>
                                <td style="height: 50px"  style="border-right: none"><img width="100%" height="100%" src="${pageContext.request.contextPath}${approve.signature}" /></td>

                            </c:when>


                            <c:when test="${approve.user.position.posid==3}">

                                <td style="border-left: none">部门经理审核</td>
                                <td>${approve.user.truename}</td>
                                <c:choose>

                                    <c:when test="${approve.approflag==1}">

                                        <td style="color: #00aa00;font-family: 仿宋;font-weight: bold">同意</td>

                                    </c:when>


                                    <c:when test="${approve.approflag==2}">

                                        <td style="color: #e70000;font-family: 仿宋;font-weight: bold">退回</td>

                                    </c:when>

                                </c:choose>
                                <td>${approve.dealtime}</td>
                                <td style="color: #e70000;font-weight: bold">${approve.suggest}</td>
                                <td style="height: 50px" style="border-right: none"><img width="100%" height="100%" src="${pageContext.request.contextPath}${approve.signature}" /></td>

                            </c:when>

                            <c:when test="${approve.user.position.posid==4}">

                                <td style="border-left: none">总裁审核</td>
                                <td>${approve.user.department.deptname}</td>
                                <td>${approve.approflag}</td>
                                <td>${approve.dealtime}</td>
                                <td style="font-family: 楷体;color: #ff2a45;background-color: white">${approve.suggest}</td>
                                <td style="border-right: none">${approve.signature}</td>

                            </c:when>

                        </c:choose>

                    </tr>

                </c:otherwise>

            </c:choose>

        </c:forEach>

    </table>

</div>

<div class="ceng"></div>

</body>

</html>
