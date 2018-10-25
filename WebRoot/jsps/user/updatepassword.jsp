<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file = "/jsps/header.jsp" %>
<!-- 导入css文件 -->

<html style = "background-image: url('<%=basePath%>images/background/001.gif');"></html>
<script type="text/javascript">
		function refresh(obj){
			obj.src="/WebCourse/VerifyCodeServlet?a="+new Date().getTime();
		}
	</script>
 <center>
 <form id = "updatePasswordForm" action="updatePassword.do" method = "POST">
   
    <table>
    <tr>
    <td class = "tdClass">原密码：</td>
     <td><input type = "password" name = "loginpass" id = "loginpass" class = "inputClass" value = "${userForm.loginpass }"/></td>
      <td><label class = "errorClass" id = "loginpassError">${errors.loginpassError }</label></td>
    </tr>
   <tr>
     <td class = "tdClass">新密码：</td>
     <td><input type = "password" name = "newpass" id = "newpass" class = "inputClass" value = "${userForm.newpass }"/></td>
      <td><label class = "errorClass" id = "newpassError">${errors.newpassError }</label></td>
    </tr>
    
    <tr>
     <td class = "tdClass">确认密码：</td>
     <td><input type = "password" name = "reloginpass" id = "reloginpass" class = "inputClass" value = "${userForm.reloginpass }"/></td>
      <td><label class = "errorClass" id = "reloginpassError">${errors.reloginpassError }</label></td>
    </tr>
    
     <tr>
    <td class = "tdClass">验证码：</td>
     <td><input type = "text" name = "verifyCode" id = "verifyCode" class = "inputClass" value = "${userForm.verifyCode }"/></td>
      <td><label class = "errorClass" id = "verifyCodeError">${errors.verifyCodeError }</label></td>
    </tr>
    
     <tr>
    <td></td>
     <td><div class = "verifyCodeDiv"><img onclick = "javascript:refresh(this)" id = "verifyCodeImage" alt="这是一个验证码" src = "VerifyCodeServlet"></div></td>
      <td><span style = "color:blue" >看不清?点击验证码换一张</span></td>
    </tr>
    
     <tr>
     <td></td>
    <td><input type = "submit" id = "submitBtn" value = "提交">&nbsp;&nbsp;<input type = "reset" value = "重置"/></td>
      <td></td>
    </tr>  
    </table>
  
 </form>
   </center>

<%--
 <div>
 <h2><%=request.getParameter("user") %></h2>
 <hr/>
 <h2><%=session.getAttribute("sessionUser") %></h2>
 </div>
  --%>

<%@ include file = "/jsps/footer.jsp" %>
