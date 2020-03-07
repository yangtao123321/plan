<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yangtao
  Date: 2020-02-11
  Time: 上午 8:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>

    <title>一致性评价类计划申请页</title>

    <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/WdatePicker.js"></script>
    <script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/jquery.cookie.js" ></script>

    <script type="text/javascript" language="JavaScript">

        $(function() {

            $(document).on('click','.add',function() {

                var proname=$(".productname").val().trim();

                var supplier=$(".productsupplier").val().trim();

                var special=$(".productspecial").val().trim();

                var pronum=$(".productnum").val().trim();

                if(proname=='') {

                    alert("品名不能为空!");

                }else if(pronum=='') {

                    alert("数量不能为空!");

                }


                if(proname!=''&&pronum!='') {//校验通过了,可以进行添加的操作

                    var tb=$(".devicelisttb");

                    var tr=$("<tr class='devicedetailtr' style='background-color: white'></tr>");

                    var td1=$("<td style='border-left: 1px solid #dfe1e5'>"+proname+"</td>");
                    var td2=$("<td>"+supplier+"</td>");
                    var td3=$("<td>"+special+"</td>");
                    var td4=$("<td>"+pronum+"</td>");
                    var td5=$("<td style='border-right: 1px solid #dfe1e5'><input class='deldeviceinfo' type='button' value='删除' /></td>");

                    tr.append(td1);
                    tr.append(td2);
                    tr.append(td3);
                    tr.append(td4);
                    tr.append(td5);

                    tb.append(tr);

                }


                $(".productname").val("");

                $(".productsupplier").val("");

                $(".productspecial").val("");

                $(".productnum").val("");


            });

            $(document).on('click','.cancel',function() {

                $(".productname").val("");

                $(".productsupplier").val("");

                $(".productspecial").val("");

                $(".productnum").val("");

            });

            $(document).on('click','.deldeviceinfo',function() {

                if(confirm("确认删除该条信息吗?")) {

                    $($($(this).parent()).parent()).remove();

                }

            });

            $(document).on("click",'.filtersave',function() {

                var saveDataAry=[];

                var details={};//字面量创建对象

                $(".devicedetailtr").each(function() {

                    var d=$(this).children();

                    details.otherproname= d.eq(0).text();
                    details.othersupplier= d.eq(1).text();
                    details.otherspecial= d.eq(2).text();
                    details.othernum= d.eq(3).text();

                    saveDataAry.push(details);

                    details={};

                });

                var uid=$(".userid").text();

                var othertitle="采  购  计  划  表";

                var person=$(".apperson").val().trim();

                var abstract=$(".abstract").val().trim();

                var startime=$(".startime").val().trim();

                var reason=$(".context").val().trim();

                var receive=$(".receiver option:checked").val();

                var flowid=$(".flowid").text();

                if(person=='') {

                    alert("提报人不能为空!");

                }else if(abstract=='') {

                    alert("流程内容摘要不能为空!");

                }else if(reason=='') {

                    alert("购买原因不能为空!");

                }else if(receive=='0') {

                    alert("请选择接收单位!")

                }

                if(person!=''&&abstract!=''&&reason!=''&&receive!='0') {//第一次校验成功了

                        //发送ajax请求后台服务器
                        $.ajax({
                            url:"${pageContext.request.contextPath}/applyotherplan.do",
                            type:"post",
                            async:false,
                            data:{"details":JSON.stringify(saveDataAry),"user.uid":uid,"othertitle":othertitle,"applyperson":person,"applyabstract":abstract,"receive":receive,"applytime":startime,"buyreson":reason,"flowid":flowid},
                            dataType:"json",
                            success:function(data) {

                                if(data=='success') {

                                    alert("流程提交成功!");

                                    var userAgent = navigator.userAgent;
                                    if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") != -1) {
                                        location.href = "about:blank";
                                    } else {
                                        window.opener = null;
                                        window.open('', '_self');
                                    }

                                    window.close();

                                }else {

                                    alert("流程提交失败!");
                                    window.location="${pageContext.request.contextPath}/climpotherpage.do";

                                }

                            }


                        });



                }









            });

            $(".lookpic").click(function() {

                var uid=$(".userid").text().trim();

                var flowid=$(".flowid").text().trim();

                var receive=$(".receiver option:checked").val();

                if(receive==0) {

                    alert("请选择接收单位!");

                }else{

                    var form = $("<form method='post' target='_blank'></form>");
                    form.attr({"action":"${pageContext.request.contextPath}/climpflowpicpre.do"});
                    var input1 = $("<input type='hidden'>").attr("name", "uid").val(uid);
                    var input2=$("<input type='hidden'/>").attr("name","flowid").val(flowid);
                    var input3=$("<input type='hidden' />").attr("name","receive").val(receive);

                    form.append(input1);
                    form.append(input2);
                    form.append(input3);
                    // 这步很重要，如果没有这步，则会报错无法建立连接
                    $("body").append($(form));
                    form.submit();

                }

            });

            $(document).on('click','.filterclose',function() {

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


        }

        .top{

            border: 1px solid #00acf0;
            height: 60px;

            line-height: 60px;

            background-color: #00acf0;
            color: white;
            font-size: 26px;

        }

        .mai{
            position: relative;
            border: 1px solid #e9e8e7;
            margin-top: 10px;
            height: auto;
            width: 80%;
            margin-left: 10%;
            background-color: #f4f3f2;
        }

        /*申请信息样式表*/
        .appinfo{

            position: relative;
            height: 30px;
            font-family: "楷体";
            line-height: 30px;
            margin-left: 5%;
            font-weight: bold;
            font-size: 16px;

        }

        .apptb,.bydetailtb,.devicelisttb{

            position:relative;
            margin-top: 1%;

            border-collapse: collapse;
            border: none;
            width: 100%;
            margin-bottom: 3%;


        }

        .apptb td,.appdetailtb td,.caigouyaoqiu td,.shigongyaoqiu td,.supplier td,.bydetailtb td,.devicelisttb td{

            font-family: "楷体";
            border: 1px solid #abadb1;
            text-align: center;
            height: 40px;
            font-size: 15px;

        }

        .filterlist td{

            font-family: "楷体";
            border: 1px solid #00223c;
            text-align: center;
            height: 30px;
            font-size: 15px;

        }

        .bydetailtb td{

            height: 35px;

        }

        .devicelisttb td{

            height: 35px;

        }

        .apperson,.abstract,.productname,.productsupplier,.productspecial,.productnum{

            position: relative;
            height: 100%;
            width: 100%;
            outline-style: none;
            border: 1px solid white;
            text-align: center;
            font-size: 15px;
            font-family: "楷体";

        }

        .context{

            position: relative;
            border: 1px solid #d7d9dd;
            margin-top: 1%;
            text-align: center;
            font-size: 16px;
            padding:0px;
            overflow: hidden;
            height: 30%;
            width: 100%;
            padding-top: 5%;
            outline-style: none;
            font-family: "楷体";
            font-weight: bold;

            border-left: none;
            border-right: none;

        }

        .startime{

            position: relative;
            height: 100%;
            width: 100%;
            outline-style: none;
            border: 1px solid #ffffff;
            text-align: center;
            font-size: 15px;
            color: #000000;
            font-family: "楷体";

        }

        .appdetail{

            position: relative;
            height: 30px;
            font-family: "楷体";
            line-height: 30px;
            margin-left: 5%;
            font-weight: bold;
            float: left;
            font-size: 16px;

        }

        .buydetail{

            position: relative;
            height: 30px;
            font-family: "楷体";
            line-height: 30px;
            margin-left: 5%;
            font-weight: bold;
            float: left;
            margin-top: 2%;
            font-size: 16px;
            margin-bottom: 1%;

        }

        .devicelist{

            position: relative;
            height: 30px;
            font-family: "楷体";
            line-height: 30px;
            margin-left: 5%;
            font-weight: bold;
            font-size: 15px;

        }

        .receive{

            position: relative;
            height: 30px;
            font-family: "楷体";
            line-height: 30px;
            margin-left: 60%;
            color: #000000;
            font-weight: bold;
            float: left;
            font-size: 15px;


        }

        .receiver{

            border: 1px solid #abadb1;
            position: relative;
            height: 30px;
            font-family: "楷体";
            line-height: 30px;
            margin-left: 1%;
            font-weight: bold;
            outline-style: none;
            width: 15%;
            font-size: 15px;


        }

        .receiver option{

            font-family: "楷体";

        }

        select::-ms-expand {
            display: none;
        }

        .filterclose,.filtersave{

            position: relative;
            border: 1px solid #0cbef7;
            height: 35px;
            width: 10%;
            outline-style: none;
            background-color: #0cbef7;
            color: white;
            font-size: 15px;
            margin-left: 30%;
            cursor: pointer;



            margin-bottom: 5%;

        }

        .filtersave{

            margin-left: 20%;

        }

        .filterclose:hover,.filtersave:hover{

            background-color: #004575;
            border: 1px solid #004575;

        }

        .cancel{

            position: relative;

            border: 1px solid red;

            height: 80%;
            float: left;

            margin-left: 20%;


            background-color: #68b7f5;
            color: white;
            border: 1px solid #a6aca8;
            cursor: pointer;


        }

        .add{

            position: relative;

            border: 1px solid red;

            height: 80%;
            float: left;

            margin-left: 15%;


            background-color: #68b7f5;
            color: white;
            border: 1px solid #a6aca8;
            cursor: pointer;


        }

        .cancel:hover,.add:hover{

            background-color: #006caa;

        }

        .deldeviceinfo{

            background-color: #68b7f5;
            border: 1px solid #b4bab6;
            color: white;
            height: 100%;
            width: 100%;
            cursor: pointer;

        }

        .deldeviceinfo:hover{

            background-color: #0064a6;

        }

        .lookpic{

            border: 1px solid #dfe1e5;

            height: 30px;

            width: 90px;

            background-color: #dfe1e5;

            color: #000000;

            font-family: "楷体";

            float: right;

            margin-top: 1%;

            margin-right: 5%;

            cursor: pointer;

        }

        .title{

            margin-left: 40%;
            font-family: "楷体";

        }

        ::-ms-clear, ::-ms-reveal { display: none; }

    </style>

</head>

<body>

<div class="top">

    <span class="title">一致性评价类计划申请页</span>
    <input class="lookpic" type="button" value="查看流程图"/>

</div>

<div class="mai">

    <div class="appinfo">申请信息</div>

    <div hidden="hidden" class="userid">${sessionScope.get("userinfo").uid}</div>

    <div hidden="hidden" class="flowid">4</div>

    <table class="apptb">

        <tr>
            <td width="15%" style="border-left: 1px solid #dfe1e5">申请单位</td>
            <td width="30%">${sessionScope.get("userinfo").department.deptname}</td>
            <td width="15%">提报人<span style="color: red">*</span></td>
            <td width="30%" style="border-right: 1px solid #dfe1e5"><input class="apperson" type="text" /></td>
        </tr>

        <tr>
            <td width="15%" style="border-left: 1px solid #dfe1e5">摘要<span style="color: red">*</span></td>
            <td width="30%"><input class="abstract" type="text" /></td>
            <td width="15%">发起时间</td>
            <td width="30%" style="border-right: 1px solid #dfe1e5"><input readonly="true" class="startime" type="text" value="${requestScope.get("starttime")}" /></td>
        </tr>

    </table>

    <div class="appdetail">购买原因<span style="color: red">*</span></div>

    <div class="receive">选择采购部门</div>

    <select class="receiver">

        <option value="0">请选择</option>

        <c:forEach items="${sections}" var="s">

            <option value="${s.sectionid}">${s.sectionname}</option>

        </c:forEach>

    </select>

    <textarea class="context"></textarea>

    <div class="buydetail">填写采购明细<span style="color: red">*</span></div>

    <table class="bydetailtb" style="margin-top: 4%">

        <tr>

            <td width="5%" style="border-left: 1px solid #dfe1e5">品名<span style="color: red">*</span></td>
            <td width="20%"><input style="font-size: 15px" class="productname" type="text" /></td>
            <td width="5%">厂家</td>
            <td width="20%"><input style="font-size: 15px" class="productsupplier" type="text" /></td>
            <td width="5%">规格</td>
            <td width="20%"><input style="font-size: 15px" class="productspecial" type="text" /></td>
            <td width="5%">数量<span style="color: red">*</span></td>
            <td width="7%"><input style="font-size: 15px" class="productnum" type="text" /></td>
            <td width="18%" style="border-right: 1px solid #dfe1e5"><input class="cancel" type="button" value="取消" /> <input class="add" type="button" value="添加" /></td>

        </tr>

    </table>

    <div class="devicelist">采购明细单</div>

    <table class="devicelisttb">

        <tr style="font-weight: bold">

            <td width="30%" style="border-left: 1px solid #dfe1e5">品名</td>
            <td width="30%">厂家</td>
            <td width="20%">规格</td>
            <td width="10%">数量</td>
            <td width="10%" style="border-right: 1px solid #dfe1e5">操作</td>

        </tr>

    </table>

    <input class="filterclose" type="button" value="关闭" />

    <input class="filtersave" type="button" value="提交" />

</div>

</body>

</html>
