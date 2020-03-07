<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yangtao
  Date: 2020-02-12
  Time: 下午 3:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>

    <title>一致性评价采购计划详情页</title>

    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.cookie.js" ></script>

    <script type="text/javascript" language="JavaScript">

        $(function() {

            $(document).on('click','.close',function() {

                var userAgent = navigator.userAgent;
                if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") != -1) {
                    location.href = "about:blank";
                } else {
                    window.opener = null;
                    window.open('', '_self');
                }

                window.close();

            });

        });

    </script>

    <style type="text/css">

        *{
            margin-top: 0px;
            margin-left: 0px;
            margin-right: 0px;
            font-family: "楷体";

            font-size: 13px;

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
            width: 90%;
            margin-left: 5%;
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



        }

        .a{

            background-color: white;

        }

        .a:hover{

            background-color: #f0ffef;

            color: blue;

            font-weight: bold;

        }

        .load{

            position: relative;
            height: 10px;
            text-align: center;
            margin-top: 3%;

        }

        .close{

            position: relative;
            border: 1px solid #0081c8;
            margin-bottom: 6%;
            height: 30px;
            line-height: 30px;
            width: 10%;
            margin-left: 45%;
            text-align: center;
            background-color: #0081c8;
            color: white;
            cursor: pointer;

        }

        .close:hover{
            border: 1px solid #005c94;
            background-color: #005c94;
        }

        .buyreason{

            position: relative;
            width: 100%;
            height: 30%;
            text-align: center;
            padding-top: 7%;
            font-family: "楷体";
            font-size: 15px;
            font-weight: bold;
            overflow-y: hidden;
            outline-style: none;
            margin-top: 1%;


            border: 1px solid #c6c6c6;
            border-left: none;
            border-right: none;

        }

        .buyreason:hover{

            background-color: #f0ffef;

            color: blue;

            font-size: 16px;

        }

        .tr1{

            background-color: white;

        }

        .tr1:hover{

            background-color: #f0ffef;

            color: blue;

            font-weight: bold;

        }

    </style>

</head>

<body>

<div class="top">一致性评价采购计划详情页</div>

<div class="mai">

    <div hidden="hidden" class="flowinfoid">${flowinfos.flowinfoid}</div>

    <div style="font-weight: bold;margin-top: 1%;margin-left: 1%;font-size: 16px">申请信息</div>

    <table class="tb">

        <tr>
            <td width="15%" style="border-left: none">申请单位</td>
            <td width="20%" class="a">${otherplan.user.department.deptname}</td>
            <td width="10%">提报人</td>
            <td width="15%" class="a">${otherplan.applyperson}</td>
            <td width="10%">申请时间</td>
            <td width="25%" class="a" style="border-right: none">${otherplan.applytime}</td>
        </tr>

        <tr>

            <td style="border-left: none">流程内容摘要</td>
            <td colspan="2" class="a">${otherplan.applyabstract}</td>

            <td>采购单位</td>
            <td class="a" style="border-right: none" colspan="2">${sec.sectionname}</td>

        </tr>

    </table>

    <div style="font-weight: bold;margin-top: 1%;margin-left: 1%">采购原因</div>

    <textarea class="buyreason">${otherplan.buyreson}</textarea>

    <c:choose>

        <c:when test="${otherplan.otherDetails==null||otherplan.otherDetails.size()==0}">



        </c:when>

        <c:otherwise>

            <div style="font-weight: bold;margin-top: 3%;margin-left: 1%">采购详情</div>

            <table class="tb1">

                <tr style="font-weight: bold">
                    <td width="30%" style="border-left: none">品名</td>
                    <td width="30%">厂家</td>
                    <td width="30%" style="border-right: none">规格</td>
                    <td width="10%" style="border-right: none">数量</td>
                </tr>

                <c:forEach items="${otherplan.otherDetails}" var="detail">

                    <tr class="tr1">

                        <td>${detail.otherproname}</td>
                        <td>${detail.othersupplier}</td>
                        <td>${detail.otherspecial}</td>
                        <td>${detail.othernum}</td>

                    </tr>

                </c:forEach>

            </table>

        </c:otherwise>

    </c:choose>

    <div style="clear: left;font-weight: bold;margin-top: 2%;margin-left: 1%">审批记录</div>

    <table class="tb2">

        <tr>
            <td width="15%" style="border-left: none;font-weight: bold">审批环节</td>
            <td width="10%" style="font-weight: bold">审批人</td>
            <td width="10%" style="font-weight: bold">审批结果</td>
            <td width="25%" style="font-weight: bold">审批时间</td>
            <td width="30%" style="font-weight: bold">审批意见</td>
            <td width="10%" style="border-right: none;font-weight: bold">审批签名</td>
        </tr>

        <c:forEach items="${appro}" var="approve">

            <c:choose>

                <c:when test="${approve.user.position.posid==1}">

                    <tr style="font-size: 15px">

                        <td style="border-left: none">发起步骤</td>
                        <td>${approve.user.truename}</td>
                        <td></td>
                        <td>${approve.dealtime}</td>
                        <td style="color: #e074a2;background-color: white;font-weight: bold">${approve.suggest}</td>
                        <td style="border-right: none;height: 50px"></td>

                    </tr>

                </c:when>

                <c:otherwise>

                    <tr style="font-size: 15px">

                        <c:choose>

                            <c:when test="${approve.user.position.posid==2}">

                                <td style="border-left: none">单位负责人审核</td>
                                <td>${approve.user.truename}</td>
                                <c:choose>

                                    <c:when test="${approve.approflag==1}">

                                        <td style="color: #009a00;font-weight: bold">同意</td>

                                    </c:when>

                                    <c:when test="${approve.approflag==2}">

                                        <td style="color: #e70000;font-weight: bold">退回</td>

                                    </c:when>

                                </c:choose>
                                <td>${approve.dealtime}</td>
                                <td style="color: #e074a2;background-color: white;font-weight: bold">${approve.suggest}</td>
                                <td style="border-right: none;height: 50px"><img width="100%" height="100%" src="${pageContext.request.contextPath}${approve.signature}" /></td>

                            </c:when>


                            <c:when test="${approve.user.position.posid==3}">

                                <td style="border-left: none">部门经理审核</td>
                                <td>${approve.user.truename}</td>

                                <c:choose>
                                    <c:when test="${approve.approflag==1}">

                                        <td style="color: #009a00;font-weight: bold">同意</td>

                                    </c:when>

                                    <c:when test="${approve.approflag==2}">

                                        <td style="color: #e70000;font-weight: bold">退回</td>

                                    </c:when>

                                </c:choose>

                                <td>${approve.dealtime}</td>
                                <td style="color: #e074a2;background-color: white;font-weight: bold">${approve.suggest}</td>
                                <td style="border-right: none;height: 50px"><img width="100%" height="100%" src="${pageContext.request.contextPath}${approve.signature}" /></td>

                            </c:when>

                            <c:when test="${approve.user.position.posid==4}">

                                <td style="border-left: none">总裁审核</td>
                                <td>${approve.user.department.deptname}</td>
                                <td>${approve.approflag}</td>
                                <td>${approve.dealtime}</td>
                                <td style="font-family: 仿宋;color: #e074a2;background-color: white;font-weight: bold">${approve.suggest}</td>
                                <td style="border-right: none;height: 50px"><img width="100%" height="100%" src="${pageContext.request.contextPath}${approve.signature}" /></td>

                            </c:when>


                        </c:choose>

                    </tr>

                </c:otherwise>

            </c:choose>

        </c:forEach>

    </table>

    <div class="close">关闭</div>

</div>

</body>

</html>
