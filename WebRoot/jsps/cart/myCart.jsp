<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file = "/jsps/header.jsp" %>
<link rel="stylesheet" type="text/css" href="css/detail/detail.css">
<script src="<c:url value='/js/cart/cartList.js'/>"></script>
<script src="<c:url value='/js/jquery-1.5.1.js'/>"></script>
<script type="text/javascript">

function buy() {
var AllcartItemId="";
$(":checkbox[name=checkboxBtn][checked=true]").each(function() {		
	var cartItemId = $(this).val()+",";
//	alert("执行buy方法，获取cartItemId: "+cartItemId);
	AllcartItemId+=cartItemId;
//	alert(AllcartItemId);
});
$("#cartItemId").attr("value",AllcartItemId);
//alert("执行bye方法并赋值");
 var aaa = $("#cartItemId").val();
// alert("从客户端获取cartItemId：  "+aaa);
}

</script>

<c:choose>
<c:when test="${cartItemList eq null }">
  <html style = "background-image: url('<%=basePath%>images/background/001.gif');">
     <h1>购物车为空</h1>
  </html>
</c:when>
<c:when test="${cartItemList.size() <= 0 }">
   <html style = "background-image: url('<%=basePath%>images/background/001.gif');">
     <h1>购物车为空</h1>
  </html>
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
		<form action="CartItemsServlet" method = "post">
		    <input type = "hidden" name = "method" value = "afterBuyDeleteCartItems"/>
		    <input type = "hidden" class = "cartItemId" id = "cartItemId" name = "cartItemId" value =""/>
		    <input type = "image" id = "buyImg" class = "buyImg" src="<c:url value = '/images/cart/buy.png'/>"/>
		</form>
		</td>
	</tr>
</table>
</c:otherwise>
</c:choose>
<%@ include file = "/jsps/footer.jsp" %>
