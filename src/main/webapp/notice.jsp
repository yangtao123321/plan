<%--
  Created by IntelliJ IDEA.
  User: yangtao
  Date: 2020-03-07
  Time: 上午 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>提醒的页面</title>

    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.cookie.js" ></script>

    <script type="text/javascript" language="JavaScript">

        $(function() {

            //开启邮件提醒的功能
            $(document).on('click','.start',function() {

                //发送ajax请求后台服务器
                $.ajax({
                    url:"${pageContext.request.contextPath}/sendemailtonoticesomedoingtask.do",
                    type:"post",
                    async:false,
                    dataType:"json",
                    success:function(data) {

                        if(data=='ok') {

                            alert("提醒功能开启成功了!");

                        }else if(data=='ok1') {

                            alert("提醒功能已开启,不要重复启动!");

                        }else if(data=='fails') {

                            alert("提醒功能开启失败!");

                        }

                    }


                });

            });

            $(document).on('click','.close',function() {

                //发送ajax请求后台服务器
                $.ajax({
                    url:"${pageContext.request.contextPath}/closeemailnotice.do",
                    type:"post",
                    async:false,
                    data:{},
                    dataType:"json",
                    success:function(data) {

                        if(data=='ok') {

                            alert("关闭成功了!");

                        }else{

                            alert("关闭失败了!");

                        }

                    }

                });

            });

        });

    </script>

    <style type="text/css">

        *{
            position: relative;
            margin-top: 0px;
            margin-left: 0px;
            margin-right: 0px;

            outline-style: none;
            font-family: "楷体";

        }

        .notice{

            position: relative;
            height: 60px;
            line-height: 60px;
            border: 1px solid #4ea7ea;
            color: white;
            text-align: center;

            background-color: #4ea7ea;
            font-size: 26px;



        }

        .start,.close{

            margin-top: 3%;

            position: relative;

            background-color: #14a1ba;

            border: 1px solid #14a1ba;

            height: 50px;

            margin-left: 35%;

            color: white;

            width: 10%;

            font-size: 19px;

            cursor: pointer;

        }

        .start:hover,.close:hover{

            background-color: #428bc2;
            border: 1px solid #428bc2;

        }

    </style>

</head>

<body>

<div class="notice">邮件提醒页面</div>

<input class="start" type="button" value="开启功能" />




<input class="close" type="button" style="margin-left: 3%" value="关闭功能" />

</body>

</html>
