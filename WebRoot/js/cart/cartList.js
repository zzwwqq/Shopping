$(function() {
	showTotal();
	
	/*
	 * 给所有条目的复选框添加click事件
	 */
	$(":checkbox[name=checkboxBtn]").click(function() {
		//所有条目的个数
		var all = $(":checkbox[name=checkboxBtn]").length;
		//获取所有被选择条目的个数
		var select = $(":checkbox[name=checkboxBtn][checked=true]").length;
		/*
		 * 比较
		 */
		if (all == select) { //全都选中了			
			$("#selectAll").attr("checked", true); //勾选全选复选框
			//alert("全选中，所以勾选全选复选框" + "all=" + all + " select=" + select);
		} else if (select == 0) { //谁都未选中
			$("#selectAll").attr("checked", false); //取消全选
			//alert("谁都未选中，所以取消全选" + "all=" + all + " select=" + select);
		} else { //选中部分
			$("#selectAll").attr("checked", false); //取消全选
			//alert("只选中了部分，所以取消全选" + "all=" + all + " select=" + select);
		}
		showTotal(); //重新计算总计	
	});


	  /*
	   * 给全选添加click事件
	   */
	$("#selectAll").click(function() {
		/*
		 * 1.获取全选状态
		 */
		var bool = $("#selectAll").attr("checked"); //获取id为selectAll的元素的属性为checked的值
		//alert("全选按钮的选中状态"+bool);
		/*
		 * 2.让所有条目的复选框与全选的状态同步
		 */
		$(":checkbox[name=checkboxBtn]").each(function() {
			$(this).attr("checked",bool);
		})
		
		/*
	   	 * 4.重新计算总计
	     */
		showTotal();
	});
});




/*
 * 计算总计
 */
function showTotal() {
	var total = 0;
	/*
	 * 1.遍历所有复选框，筛选出名字为checkboxBtn且被选中的复选框
	 */
	$(":checkbox[name=checkboxBtn][checked=true]").each(function() {
		//2.获取复选框的值，即其他复选框元素的前缀
		var id = $(this).val();
		//alert("id为："+id);
		//3.再通过前缀找到小计元素，获取其文本
		var text = $("#" + id + "Subtotal").text();
		//alert("内容"+text);
		//4.累加计算
		total += Number(text);
	});
	//5.把总计显示在总计元素上
	//alert("总计"+total);
	$("#total").text(total);
}