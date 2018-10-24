<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/jsps/header.jsp"%>
<script type="text/javascript"	
  src="/WebCourse/js/cart/cart.js"></script>

<center>
<h1>${error }</h1>
	<table width="750" height="60" cellpadding="0" cellspacing="0"
		border="0">
		<tr>
		
			<!-- 商品详情 -->
			<%
				Items item = (Items) request.getAttribute("item");
				if (item != null) {
			%>
			<td width="70%" valign="top">
				<table>
					<tr>
						<td id="img" rowspan="4"><img
							src="<%=basePath%>images/items/${item.picture}" width="240"
							height="190" /></td>
					</tr>
					<tr>
						<td><B>${item.name }</B></td>
					</tr>
					<tr>
						<td>产地：${item.city }</td>
					</tr>
					<tr>
						<td>价格：${item.price }￥</td>
					</tr>
					<tr>
						<td>
							<div class="divForm">
								<form id="form1" action="CartItemsServlet" method="post">
									<input type="hidden" name="method" value="add" /> 
									<input type="hidden" name="id" value="${item.id }" />
									买：
									<input id="cnt" style="width: 40px;text-align: center;" type="text" name="itemQuantity" value="1" />件
								</form>
								<a href="javascript:$('#form1').submit();"><img id="addCartImg" alt="图片"					 
								src="<%=basePath%>images/cart/add.png" /></a>
							</div>
						</td>
					</tr>
				</table>
			</td>
			<%
				}
			%>

			<!-- 浏览过的商品 -->
			<td width="30%" bgcolor="#EEE" align="center"><br> <b>您浏览过的商品</b><br>
				<!-- 循环开始 --> <%
 	ArrayList<Items> itemlist = (ArrayList<Items>) request.getAttribute("itemlist");
 	if (itemlist != null && itemlist.size() > 0) {
 		System.out.println("itemlist.size=" + itemlist.size());
 		for (Items i : itemlist) {
 			System.out.println("========" + i.getName());
 %>
				<div>
					<dl>
						<dt>
							<a href="ItemsServlet?method=showItemsDetail&id=<%=i.getId()%>"><img
								src="/WebCourse/images/items/<%=i.getPicture()%>" width="120"
								height="90" border="1" /></a>
						</dt>
						<dd class="dd_name"><%=i.getName()%></dd>
						<dd class="dd_city">
							产地:<%=i.getCity()%>&nbsp;&nbsp;价格:<%=i.getPrice()%>￥
						</dd>
					</dl>
				</div> <%
 	}
 	}
 %> <!-- 循环结束 --></td>
		</tr>

	</table>
</center>
<%@ include file="/jsps/footer.jsp"%>
