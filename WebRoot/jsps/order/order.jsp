<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file = "/jsps/header.jsp" %>


<c:choose>
<c:when test="${sessionScope.sessionUser != null }">
<!-- 拍下付款 -->
<c:if test="${oneParam eq 'Take_the_payment' }">
<html style = "background-image: url('<%=basePath%>images/background/001.gif');">
    <h1>购买成功</h1>
</html>
</c:if>
<!-- 点击购买 -->
<c:if test="${param.oneParam == 'Immediate_payment' }">
<body>
    <h3>扫描下方二维码完成付款</h3>   
	<img style = "align:center;" width = 250; height=350; alt="二维码" src="<%=basePath%>images/background/order.jpg">
</body>
</c:if>
</c:when>
<c:otherwise>
<html style = "background-image: url('<%=basePath%>images/background/002.gif');">
    <h1 style="color:red">您还未登录！</h1>
</html>
</c:otherwise>
</c:choose>
<%@ include file = "/jsps/footer.jsp" %>

