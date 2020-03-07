<%--
  Created by IntelliJ IDEA.
  User: yangtao
  Date: 2020-03-05
  Time: 下午 2:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <title>修改密码</title>

    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.cookie.js" ></script>

    <script type="text/javascript" language="JavaScript">

        $(function() {

            $(document).on('click','.save',function() {

                var uid=$(".uid").text().trim();

                var username=$(".username").text().trim();

                var newpas=$(".newpasval").val().trim();

                var newpas1=$(".newpas1val").val().trim();

                if(newpas==''||newpas1=='') {

                    alert("密码不能为空!");

                }else if(newpas!=newpas1) {

                    alert("两次密码输入不一致!");

                }

                if(newpas!=''&&newpas==newpas1) {//校验通过了,可以修改密码

                    //发送ajax请求后台服务器,修改用户密码
                    $.ajax({
                        url:"${pageContext.request.contextPath}/uppasdefaultpassword.do",
                        type:"post",
                        async:false,
                        data:{"uid":uid,"username":username,"password":newpas},
                        dataType:"json",
                        success:function(data) {

                            if(data=='1') {//密码修改成功了

                                window.location="${pageContext.request.contextPath}/logout.do";

                            }else{

                                alert("密码修改失败了!");

                            }

                        }



                    });

                }

            });

            $(document).on('click','.cancel',function() {

                $(".newpasval").val("");

                $(".newpas1val").val("");

                window.location="${pageContext.request.contextPath}/logout.do";

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

        .bannertop{

            position: relative;
            border: 1px solid #00a0da;
            height: 10%;
            background-color: #00a0da;


        }

        .c1{
            position: relative;
            clear: both;
            width: 10%;
        }

        .backimg{
            position: relative;
            margin-left: 1%;
            margin-top: 10px;
            margin-bottom: 10px;
            float: left;
            display: block;
        }

        .userbanner{

            position: relative;
            margin-left: 65%;
            height: 35px;
            margin-top: 3px;
            float: left;
            display: block;

        }

        .userinfo{

            position: relative;
            color: white;
            margin-top: 5px;
            font-size: 16px;
            margin-left: 1%;
            float: left;
            width: 6%;
            font-family: "楷体";

        }

        .uppas{

            position: relative;
            margin-left: 5%;
            height: 30px;
            width: 70px;
            margin-top: 9px;
            float: left;
            display: block;
            cursor: pointer;

        }

        .logout{

            position: relative;
            margin-left: 2%;
            height: 30px;
            width: 70px;
            margin-top: 9px;
            float: left;
            display: block;
            cursor: pointer;

        }

        .menu{
            position: relative;
            border: 1px solid #ebe7e4;
            height: 35px;
            border-bottom: 1px solid #008cd0;

            background-color: #ebe7e4;
            font-size: 15px;
        }

        .m1{
            text-align:center;
            position: relative;
            list-style-type: none;
            float: left;
            border-right: 1px solid #979797;
            height: 35px;
            line-height: 35px;
            color: #008dd1;
            width: 10%;
            cursor: pointer;

        }

        .ceng{

            display: none;
            position: fixed;
            /*border: 1px solid #ff0000;*/
            height: 100%;
            background-color: rgba(76, 75, 74, 0.50);
            z-index:1;
            -moz-opacity:0.60; /*支持 FireFox 浏览器*/
            opacity:0.70;  /*支持 Chrome, Opera, Safari 等浏览器*/
            filter: alpha(opacity=80);
            width: 100%;

        }

        .uppasdiv{

            display: block;
            position: relative;
            border: 1px dashed #d835a4;
            height: 70%;
            width: 45%;
            margin-top: 1%;

            margin-left: 30%;

            z-index: 3;
            background-color: #fdfff8;


            font-family: "楷体";

        }

        .mai{

            position: relative;

            height: 70%;

            margin-top: 3%;

            width: 99.9%;

        }

        .tit{

            position: relative;
            height: 35px;
            line-height: 35px;
            text-align: center;
            margin-top: 1%;
            font-size: 21px;
            color: red;

        }

        .newpas{

            position: relative;
            border: 1px solid #afaead;
            height: 35px;
            line-height: 35px;
            width: 15%;
            text-align: center;
            margin-left: 25%;
            margin-top: 10%;
            background-color: #afaead;
            float: left;

            font-family: "楷体";

        }

        .newpasval{

            position: relative;
            border: 1px solid #afaead;
            float: left;
            margin-left: 3%;
            margin-top: 10%;
            height: 35px;
            line-height: 35px;
            text-align: center;
            width: 30%;
            outline-style: none;

            font-family: "楷体";

        }

        .newpas1{

            position: relative;
            border: 1px solid #afaead;
            height: 35px;
            line-height: 35px;
            width: 15%;
            text-align: center;
            margin-left: 25%;
            margin-top: 10%;
            background-color: #afaead;
            float: left;

            font-family: "楷体";

        }

        .newpas1val{

            position: relative;
            border: 1px solid #afaead;
            float: left;
            margin-left: 3%;
            margin-top: 10%;
            height: 35px;
            line-height: 35px;
            text-align: center;
            width: 30%;
            outline-style: none;

            font-family: "楷体";

        }

        .cancel{

            position: relative;
            border: 1px solid #007dca;
            height: 35px;
            width: 10%;
            background-color: #007dca;
            color: white;
            cursor: pointer;
            margin-left: 30%;
            margin-top: 10%;

            font-family: "楷体";

        }

        .save{

            position: relative;
            border: 1px solid #007dca;
            height: 35px;
            width: 10%;
            background-color: #007dca;
            color: white;
            cursor: pointer;
            margin-left: 15%;
            margin-top: 10%;

            font-family: "楷体";


        }

        .cancel:hover,.save:hover{

            background-color: #006099;
            border: 1px solid #006099;

        }

    </style>

</head>

<body>

<div class="ceng"></div>

<div class="bannertop">

    <img class="backimg" src="${pageContext.request.contextPath}/picture/fenzhen1.png" />

    <img class="userbanner" src="${pageContext.request.contextPath}/picture/user.png" />

    <div class="userinfo">${sessionScope.get("userinfo").truename},您好!<br />${requestScope.get("dates")}</div>

    <div class="uid" hidden="hidden">${sessionScope.get("userinfo").uid}</div>

    <div class="username" hidden="hidden">${sessionScope.get("userinfo").username}</div>

</div>

<div class="c1"></div>

<div class="mai">

    <div class="tit"><span style="font-weight: bold;color: #00ae00;font-size: 30px">${use.truename},</span>您好。您当前为初始密码,请修改密码，谢谢!</div>

    <div class="uppasdiv">

        <div class="newpas">新密码:</div>

        <input class="newpasval" type="password" />

        <div style="clear: both"></div>

        <div class="newpas1">确认新密码:</div>

        <input class="newpas1val" type="password"/>

        <div style="clear: left"></div>

        <input class="cancel" type="button" value="取消" />

        <input class="save" type="button" value="保存" />

    </div>

</div>

</body>

</html>
