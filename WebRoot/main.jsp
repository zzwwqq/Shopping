<%@page import="com.zwq.page.service.PageService"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ page import = "com.zwq.page.service.PageService" %>
<%@ include file = "/jsps/header.jsp"%>
<link rel="stylesheet" type="text/css" href="css/mainpage/mainpage.css">

<%!  
PageBean<Items> pageBean;

%>
			<center>
				<table width="1000" height="70" cellpadding="0" cellspacing="0"
		border="0">
					<tr>
						<td>
				<!-- 商品循环开始 -->
	 <% 
               PageService pageService = new PageService(); 
               pageBean = pageService.getLimitRowAllItems(1);
               
               //System.out.println(pageBean.getCurrentPageNum());
               //System.out.println(pageBean.getTotalPage());
               //System.out.println(pageBean.getTotalRecords());
               //System.out.println(pageBean.getBeanList().get(0).getName());
               
               
               
               if(pageBean.getBeanList() != null && pageBean.getBeanList().size() > 0)
               {
	               for(int i = 0; i < pageBean.getBeanList().size(); i++)
	               {
	                  Items item = pageBean.getBeanList().get(i);
       %>
							<div>
								<dl>
									<dt>
										<a href="ItemsServlet?method=showItemsDetail&id=<%=item.getId()%>">
										<img src="/WebCourse/images/items/<%=item.getPicture() %>" width="200" height="150" border="1"
											/></a>
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
					<!-- 产生分页的连接-->
	<a class = "page" href="PageServlet?method=getPageBean&currentPageNum=1">首页</a>
	<c:choose>
		<c:when test="<%=pageBean.getCurrentPageNum() == 1 %>">
			<span>上一页</span>当前第1/<%=pageBean.getTotalPage() %>页
	 </c:when>
		<c:otherwise>
			<a class = "page"
				href="PageServlet?method=getPageBean&currentPageNum=${pageBean.currentPageNum - 1 }">上一页</a>当前第${pageBean.currentPageNum}/${pageBean.totalPage }页
</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%=pageBean.getCurrentPageNum() == pageBean.getTotalPage() %>">
			<span>下一页</span>
		</c:when>
		<c:otherwise>
			<a class = "page"
				href="PageServlet?method=getPageBean&currentPageNum=${pageBean.currentPageNum + 1 }">下一页</a>
		</c:otherwise>
	</c:choose>
	<a class = "page"
		href="PageServlet?method=getPageBean&currentPageNum=${pageBean.totalPage }">尾页</a>
	跳至 &nbsp;<input id="currentPageText" size=1
		value="<%=pageBean.getCurrentPageNum() %>" class="" type="text">&nbsp;页
	&nbsp; <a class = "page" href="javascript:_go();" class="go">GO</a>
	<!-- 分页结束 -->
			</center>
<%@ include file = "/jsps/footer.jsp"%>