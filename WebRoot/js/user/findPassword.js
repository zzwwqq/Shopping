$(function(){
	/*
	 * 遍历所有错误标签
	 */
	$(".errorClass").each(function(){
		showError($(this));
	});
	
	/*
	 * 输入框得到焦点，隐藏错误信息
	 */
	$(".inputClass").focus(function(){
		var labelId = $(this).attr("id")+"Error";
		$("#"+labelId).text("");//清空内容
		showError($("#"+labelId));
	})
	
	/*
	 * 输入框失去焦点，校验
	 */
	$(".inputClass").blur(function(){
		var id = $(this).attr("id");
		//得到对应的校验函数名
		var functionName = "validate" + id.substring(0, 1).toUpperCase() + id.substring(1) + "()";
		eval(functionName); //执行对应的校验函数 	
	});
	
	/*
	 * 只有全部输入符合要求时，才能提交数据到后台
	 */
	$("#updatePasswordForm").submit(function() {
		var boolValue = true; //表示校验通过
		if(!validateLoginname()){
			boolValue = false;
		}
		if(!validateTelephone()) {
			boolValue = false;
		}
		if(!validateAnswer()) {
			boolValue = false;
		}
		if (!validateVerifyCode()) {
			boolValue = false;
		} 		
		return boolValue;
	}) ;
	
	
	
	
})

/**
 * 显示错误信息
 */
function showError(element){
	var value = element.text();
	if(!value) {
		element.css("display","none");//无内容，隐藏标签
	} else {
		element.css("display","");//有内容，显示
	}	
}


/**
 * 校验用户名
 */
function validateLoginname() {
	var id = "loginname";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if (!value) {
		$("#" + id + "Error").text("用户名不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}

	/*
	 * 长度校验
	 */
	if (value.length < 3 || value.length > 20) {
		$("#" + id + "Error").text("用户名长度必须在3~20之间！");
		showError($("#" + id + "Error"));
		return false;
	}
	return true;
}

/**
 * 校验手机号
 */
function validateTelephone() {
	var id = "telephone";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if (!value) {
		$("#" + id + "Error").text("手机号不能为空！");
		showError($("#" + id + "Error"));
		return false;
	} 
	if(!/^[0-9]{11}$/.test(value)){
		$("#"+id+"Error").text("手机号格式不对,必须为11位数字!");
		showError($("#"+id+"Error"));
		return false;
	}
	return true;
}

/**
 * 验证密保答案
 */
function validateAnswer() {
	var id = "answer";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if (!value) {
		$("#" + id + "Error").text("密保答案不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}
	return true;
}
/**
 * 校验验证码
 */
function validateVerifyCode() {
	var id = "verifyCode";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if (!value) {
		$("#" + id + "Error").text("验证码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}

	if (value.length != 4) {
		$("#" + id + "Error").text("验证码长度不对！");
		showError($("#" + id + "Error"));
		return false;
	}

	$.ajax({
		url : "ajaxValidateVerifyCode.do",
		data : {
			method : "ajaxValidateVerifyCode",
			verifyCode : value
		},
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (!result) {
				$("#" + id + "Error").text("验证码错误！");
				showError($("#" + id + "Error"));
				return false;
			} 				
				return true;
				
		}
	});
	return true;
}