<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file = "/jsps/header.jsp"%>
<link rel="stylesheet" type="text/css" href="css/mainpage/mainpage.css">

			<center>
				<table  cellpadding="0" cellspacing="0"
					border="0">
					<tr>
						<td>
				<!-- 商品循环开始 --> <% 
               ItemsService itemsService = new ItemsService(); 
               ArrayList<Items> list = itemsService.getAllItems();
               if(list != null && list.size() > 0)
               {
	               for(int i=0; i<list.size(); i++)
	               {
	                  Items item = list.get(i);
           %>
							<div>
								<dl>
									<dt>
										<a href="ItemsServlet?method=showItemsDetail&id=<%=item.getId()%>">
										<img src="/WebCourse/images/items/<%=item.getPicture() %>" width="230" height="120"
											border="1" /></a>
									</dt>
									<dd class="dd_name"><%=item.getName() %></dd>
									<dd class="dd_city">
										产地:<%=item.getCity() %>&nbsp;&nbsp;价格:￥
										<%=item.getPrice() %></dd>
								</dl>
							</div> <!-- 商品循环结束 --> <%
                   }
              } 
          %>
						</td>
					</tr>
				</table>
			</center>
<%@ include file = "/jsps/footer.jsp"%>