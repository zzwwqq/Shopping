<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/jsps/header.jsp"%>
<center>
	<table width="750" height="60" cellpadding="0" cellspacing="0"
		border="0">

		<tr>
			<!-- 商品详情 -->
			<% 
              Items item = (Items)request.getAttribute("item");
             if(item!=null)
             {
          %>
			<td width="70%" valign="top">
				<table>
					<tr>
						<td id="img" rowspan="4"><img
							src="<%=basePath%>images/items/<%=item.getPicture()%>"
							width="200" height="160" /></td>
					</tr>
					<tr>
						<td><B><%=item.getName() %></B></td>
					</tr>
					<tr>
						<td>产地：<%=item.getCity()%></td>
					</tr>
					<tr>
						<td>价格：<%=item.getPrice() %>￥
						</td>
					</tr>
					<tr>
						<td><a href=""><img id="addCartImg" alt="图片"
								src="<%=basePath %>images/cart/add.png" /></a></td>
					</tr>
				</table>
			</td>
			<% 
            }
          %>

			<!-- 浏览过的商品 -->
			<td width="30%" bgcolor="#EEE" align="center"><br> <b>您浏览过的商品</b><br>
				<!-- 循环开始 --> <% 
                ArrayList<Items> itemlist = (ArrayList<Items>)request.getAttribute("itemlist");
                if(itemlist != null && itemlist.size() > 0 )
                {
                   System.out.println("itemlist.size="+itemlist.size());
                   for(Items i:itemlist)
                   {                
                   System.out.println("========"+i.getName());        
             %>
				<div>
					<dl>
						<dt>
							<a href="ItemsServlet?method=showItemsDetail&id=<%=i.getId()%>"><img
								src="/WebCourse/images/items/<%=i.getPicture() %>" width="120"
								height="90" border="1" /></a>
						</dt>
						<dd class="dd_name"><%=i.getName() %></dd>
						<dd class="dd_city">
							产地:<%=i.getCity() %>&nbsp;&nbsp;价格:<%=i.getPrice() %>
							￥
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
