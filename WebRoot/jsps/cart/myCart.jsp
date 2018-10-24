<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file = "/jsps/header.jsp" %>
<script src="<c:url value='/js/cart/cartList.js'/>"></script>
<script src="<c:url value='/js/jquery-1.5.1.js'/>"></script>


<c:choose>
<c:when test="${cartItemList eq null }">
    <h1>购物车为空</h1>
</c:when>
<c:when test="${cartItemList.size() <= 0 }">
    <h1>购物车为空</h1>
</c:when>
<c:otherwise>
<table width="750" height="319" border="0" align="center">
  <tr align="center">
    <th>
    <!-- 全选复选框 ，默认选中-->
   	<input type="checkbox" id="selectAll" checked=fasle/>
   	<label for="selectAll">全选</label>
    </th>
    <th width="200">商品</th>
    <th width="82">单价</th>
    <th width="76">数量</th>
    <th width="99">小计</th>
    <th width="118">操作</th>
    </tr>
 <c:forEach items="${cartItemList }" var="CartItems">
   <tr align="center">
		<td align="right" width="70px">
	        <!-- 每个商品的复选框 ，默认被选中-->
			<input type="checkbox" value="${CartItems.cartItemId }" name="checkboxBtn" checked="checked"/>
		</td>
		<td align="center">
			<a class="linkImage" href="ItemsServlet?method=showItemsDetail&id=${CartItems.items.id }">
			<img border="0" width="70" align="top" src="<c:url value='/images/items/${CartItems.items.picture }'/>"/>
			</a>
			  <a class = "myCart_href" href="<c:url value='/ItemsServlet?method=showItemsDetail&id=${CartItems.items.id }'/>">
		    <span>${CartItems.items.name }</span>
		    </a>
		</td>
		<td>
		<!-- 每个商品的单价 -->
		<span>&yen;<span class="currPrice">${CartItems.items.price }</span></span>
		</td>
		<td>
		<!-- 每个商品的数量 -->
			<a href = "CartItemsServlet?method=reduceNum&itemQuantity=${CartItems.itemQuantity }&cartItemId=${CartItems.cartItemId}" class = "myCart_href"  class="reduce" id="${CartItems.cartItemId }Reduce">-</a>
			<input class="quantity" size = 1 readonly="readonly" id="${CartItems.cartItemId }Quantity" type="text" value="${CartItems.itemQuantity }"/>
			<a href = "CartItemsServlet?method=addNum&itemQuantity=${CartItems.itemQuantity }&cartItemId=${CartItems.cartItemId}" class = "myCart_href" class="add" id="${CartItems.cartItemId }Add">+</a>
		</td>
		<td width="100px">
		<!--小计 -->
			<span class="price_n">&yen;<span class="subTotal" id="${CartItems.cartItemId }Subtotal">${CartItems.subtotal }</span></span>
		</td>
		<td>
			<a class = "myCart_href" href="<c:url value='/CartItemsServlet?method=deleteCartItems&cartItemId=${CartItems.cartItemId }'/>">删除</a>
		</td>
	</tr>
	
</c:forEach>

	<tr>
		<td align="center" colspan="2" class="tdBatchDelete" >
			<a class = "myCart_href" href="CartItemsServlet?method=deleteAllCartItems&uid=${cartItemList.get(0).user.uid }">批量删除</a>
		</td>
		<td colspan="4" align="center" class="tdTotal">
			<span id = "totalPriceSpan">总计：</span>
			  <span class="price_t">
			    <span id="total"></span>&yen;
			  </span>
		</td>
	</tr>
	<tr>
		<td colspan="6" align = "right">
			<a id = "byeA" class = "byeA" href="<c:url value='/jsps/order/order.jsp'/>"><img  alt="点击购买" id = "byeImg" class = "byeImg" src='<c:url value = '/images/cart/bye.png'/>'></a>
		</td>
	</tr>
</table>
</c:otherwise>
</c:choose>
<%@ include file = "/jsps/footer.jsp" %>
