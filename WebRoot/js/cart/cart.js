$(function() {
	$("#cnt").blur(function() {
		var itemQuantity = $("#cnt").val();
		if(!/^[1-9]\d*$/.test(itemQuantity)) {
			alert("数量必须是合法整数！");
			$("#cnt").val("1");
		}
	});
});