<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Welcome Login</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<!-- 引入 login.css文件 -->
<link rel="stylesheet" type="text/css" href="<c:url value = '/css/user/reset.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value = '/css/user/login.css'/>">
<!--导入jQuery文件 -->
<script type="text/javascript" src = "<c:url value = '/js/jquery-3.2.1.min.js'/>"></script>
<!-- 导入js文件 -->
<script type="text/javascript" src = "<c:url value = '/js/user/login.js'/>"></script>
	
<!-- 点击验证码，切换到下一张 -->
	<script type="text/javascript">
		function refresh(obj){
			obj.src="/WebCourse/VerifyCodeServlet?a="+new Date().getTime();
		}
	</script>
</head>

<body>

	<h1>Welcome</h1>
	<div class="login-form">
		<div class="close"></div>
		<div class="clo"></div>
		
		<!-- 脱离文档流 -->
		<div class="head-info">
			<label class="lbl1"></label> <label class="lbl2"></label> <label class="lbl3"></label>
		</div>
		<div class="clear"></div>
		<!-- 为了清除浮动 -->
		<div class="avtar">
			<img src="images/avtar.png" />
		</div>
		<!-- action 属性规定当提交表单时，向何处发送表单数据。 
		登录和注册两个input语句不能换行，必须在同一行
		-->
		<div>
<!-- 		<form name="login" method="post" action="UserServlet?method=login">
 -->	
	<form id = "loginForm" name="login" method="post" action="login.do">
				<label id="loginnameError" class="errorClass">${errors.loginnameError }</label>
				<input type="text" name="loginname" value = "${userForm.loginname }" class="inputClass" id = "loginname"
				<%-- 	
					onfocus="this.value = '';"
					onblur="if(this.value==''){this.value='Username';}" 
				--%> 
				/>	
				     <label id="loginpassError" class="errorClass">${errors.loginpassError }</label>
					 <input type="password" name="loginpass" value = "${userForm.loginpass }" class="inputClass" id = "loginpass"
				<%--
					onfocus="this.value='';"
					onblur="if(this.value==''){this.value='1223334444'}"  
				--%>
				/>
				    <label id="verifyCodeError" class="errorClass">${errors.verifyCodeError }</label>
					<label class="registerlabel">输入验证码：</label>
    	            <input type="text" name="verifyCode" value = "${userForm.verifyCode }" id = "verifyCode" class="verify"
    	      <%--	      
    	            value="" 
    	            onfocus="this.value='';"		
					onblur="if(this.value==''){this.value=''}"
			--%>	
			        />	
    	            <img title="点击"  onclick="javascript:refresh(this);" src="VerifyCodeServlet"/>
    	            <br/>
    	            <p class="hereregister">如果您还没有注册，请单击<a href="jsps/user/regist.jsp" class="hereregister1">这里</a>注册！<a href="jsps/user/findPassword.jsp" class="fondpassword">忘记密码？</a> </p>
					<input type="submit" value="登录" class="signin" /><input type="reset" value="重置" class="reset"/>									
				<!-- 脱离文档流 -->
			</form>
		</div>

	</div>
  
	<div class="copy-rights">
		<p>
			Copyright &copy; 2018. zwq All rights reserved.please visit github
			for more information. <a href="https://github.com/" title="zwqgithub"
				target="_blank">GitHub</a>
		<p />
<%@ include file = "/jsps/footer.jsp" %>
