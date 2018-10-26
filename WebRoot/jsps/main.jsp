<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<%@ include file="/jsps/header.jsp"%>
<script type="text/javascript">

	function _go() {
		var currentPageNum = $("#currentPageText").val();//获取文本框中输入的当前页码
		if(!/^[1-9]\d*$/.test(currentPageNum)) {//对当前页码进行整数校验
			alert('请输入正确的页码！');
			return;
		}
		if(currentPageNum > ${pageBean.totalPage}) {//判断当前页码是否大于最大页
			alert('请输入正确的页码！');
			return;
		}
		location = "PageServlet?method=getPageBean&currentPageNum="+currentPageNum;
	}
</script>
<center>
	<table width="1000" height="70" cellpadding="0" cellspacing="0"
		border="0">
		<tr>
			<td>
				<!-- 商品循环开始 --> 
				<c:if test="${pageBean.beanList != null && pageBean.beanList.size() > 0 }">
					<c:forEach items="${pageBean.beanList }" var="items">
						<div>
							<dl>
								<dt>
									<a href="ItemsServlet?method=showItemsDetail&id=${items.id }">
										<img src="/WebCourse/images/items/${items.picture }"
										width="200" height="150" border="1" />
									</a>
								</dt>
								<dd class="dd_name">${items.name }</dd>
								<dd class="dd_city">产地:${items.city }&nbsp;&nbsp;价格:￥
									${items.price }</dd>
							</dl>
						</div>
						<!-- 商品循环结束 -->
					</c:forEach>
				</c:if>
			</td>
		</tr>
	</table>

	
	<!-- 产生分页的连接-->
	<a class = "page" href="PageServlet?method=getPageBean&currentPageNum=1">首页</a>
	<c:choose>
		<c:when test="${pageBean.currentPageNum == 1 }">
			<span>上一页</span>当前第${pageBean.currentPageNum}/${pageBean.totalPage }页
	 </c:when>
		<c:otherwise>
			<a class = "page"
				href="PageServlet?method=getPageBean&currentPageNum=${pageBean.currentPageNum - 1 }">上一页</a>当前第${pageBean.currentPageNum}/${pageBean.totalPage }页
</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${pageBean.currentPageNum == pageBean.totalPage }">
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
		value="${pageBean.currentPageNum }" class="" type="text">&nbsp;页
	&nbsp; <a class = "page" href="javascript:_go();" class="go">GO</a>
	<!-- 分页第一块结束 -->
	
	<!-- 分页第二块开始 -->
	<c:choose>
	<c:when test="${pageBean.totalPage <= 6 }">
		<c:set var = "begin" value = "1"></c:set>
		<c:set var = "end" value = "${pageBean.totalPage }"></c:set>
	</c:when>
	<c:otherwise>
		<c:set var = "begin" value = "${pageBean.currentPageNum-2 }"></c:set>
		<c:set var = "end" value = "${pageBean.currentPageNum+3 }"></c:set>
		<c:if test="${begin < 1 }">
            <c:set var = "begin" value = "1"></c:set>
		    <c:set var = "end" value = "6"></c:set>
		</c:if>
	    <c:if test="${end > pageBean.totalPage }">
		    <c:set var = "end" value = "${pageBean.totalPage }"></c:set>
		    <c:set var = "begin" value = "${pageBean.totalPage-5 }"></c:set>
		</c:if>
	</c:otherwise>
</c:choose> 

<c:forEach begin = "${begin }" end = "${end }" var = "i">
    <c:choose>
    <c:when test="${i eq pageBean.currentPageNum }">
    	<span class = "spanBtnSelect">${i }</span>
    </c:when>
    <c:otherwise>
    	<a style="color:orange;" href="PageServlet?method=getPageBean&currentPageNum=${i }" class = "aBtn">${i }</a>
    </c:otherwise>
    </c:choose>
</c:forEach>  

<c:if test="${end < pageBean.totalPage }">
	<span class="spanApostrophe">...</span> 
</c:if>
<!-- 分页第二块结束 -->  	
</center>
<%@ include file="/jsps/footer.jsp"%>