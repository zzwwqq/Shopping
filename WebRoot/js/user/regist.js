
$(function() {
	//选择所有显示错误的标签，然后遍历所有显示错误的标签
	$(".errorClass").each(function() {
		showError($(this));
	});

	/*
	 * 注册按钮鼠标移入/移出时切换图片
	 * hover(over,out)
	 * over和out都是函数，over是鼠标移入时执行，out是鼠标移出时执行
	 */
	$("#submitBtn").hover(function() {
		$("#submitBtn").attr("src", "/WebCourse/images/regist2.jpg");
	},
		function() {
			$("#submitBtn").attr("src", "/WebCourse/images/regist1.jpg");
		}
	);

	/*
	 * 某个输入框得到焦点，隐藏错误信息
	 */
	//选择所有的输入框
	$(".inputClass").focus(function() {
		//某个输入框获取到焦点,通过当前输入框的id属性找到对应的label标签的id
		//alert("s");
		var labelId = $(this).attr("id") + "Error";
		$("#" + labelId).text(""); //清空内容
		showError($("#" + labelId));
	});

	/*
	 * 输入框失去焦点，校验
	 */
	$(".inputClass").blur(function() {
		var id = $(this).attr("id"); //获取当前输入框的id属性的值
		//得到对应的校验函数名
		var functionName = "validate" + id.substring(0, 1).toUpperCase() + id.substring(1) + "()";
		eval(functionName); //执行对应的校验函数 	
	});

	/*
	 * 只有全部输入符合要求时，才能提交数据到后台
	 */
	$("#registForm").submit(function() {
		var boolValue = true; //表示校验通过		
		if (!validateLoginname()) {
			boolValue = false;
		}
		if (!validateLoginpass()) {
			boolValue = false;
		}
		if (!validateReloginpass()) {
			boolValue = false;
		}
		if (!validateEmail()) {
			boolValue = false;
		}
		if(!validateTelephone()) {
			boolValue = false;
		}
		if(!validateAnswer()){
			boolValue = false;
		}
		if (!validateVerifyCode()) {
			boolValue = false;
		}
		return boolValue;
	}) ;
});

//显示错误信息
function showError(element) {
	var text = element.text();
	if (text) { //有内容，显示
		element.css("display", "");
	} else { //无内容，隐藏
		element.css("display", "none");
	}
}


/**
 * 校验注册用户名
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

	/*
	 * 是否注册校验
	 */
	$.ajax({
		url : "ajaxValidateLoginname.do",
		data : {
			method : "ajaxValidateLoginname",
			loginname : value
		},
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (!result) {
				$("#" + id + "Error").text("用户名已被注册！");
				showError($("#" + id + "Error"));
				return false;
			} else{
			return true;
				
			}
		}
	});
	return true;
}

/**
 * 校验密码
 */
function validateLoginpass() {
	var id = "loginpass";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if (!value) {
		$("#" + id + "Error").text("密码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}

	/*
	 * 长度校验
	 */
	if (value.length < 6 || value.length > 16) {
		$("#" + id + "Error").text("密码长度必须在6~16之间！");
		showError($("#" + id + "Error"));
		return false;
	}

	/*
	 * 密码格式限制
	 */
	if (!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,16}$/.test(value)) {
		$("#" + id + "Error").text("密码必须同时含有字母和数字！");
		showError($("#" + id + "Error"));
		return false;
	}
	return true;
}

/**
 * 校验确认密码
 */
function validateReloginpass() {
	var id = "reloginpass";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if (!value) {
		$("#" + id + "Error").text("密码不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}

	/*
	 * 校验两次输入是否一致
	 */
	var loginpassvalue = $("#loginpass").val();
	if (value != loginpassvalue) { //不能用equals
		$("#" + id + "Error").text("两次输入的密码不一致！");
		showError($("#" + id + "Error"));
		return false;
	}
	return true;
}


/**
 * 校验Email
 */
function validateEmail() {
	var id = "email";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if (!value) {
		$("#" + id + "Error").text("邮箱不能为空！");
		showError($("#" + id + "Error"));
		return false;
	}

	/*
	 * 邮箱格式校验
	 */
	if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
		$("#" + id + "Error").text("邮箱格式不对！");
		showError($("#" + id + "Error"));
		return false;
	}

	/*
	 * 校验邮箱是否注册
	 */
	$.ajax({
		url : "ajaxValidateEmail.do",
		data : {
			method : "ajaxValidateEmail",
			email : value
		},
		type : "POST",
		dataType : "json",
		async : false,
		cache : false,
		success : function(result) {
			if (!result) {
				$("#" + id + "Error").text("邮箱已被注册！");
				showError($("#" + id + "Error"));
				return false;
			} else{
				return true;				
			}
		}
	});
	return true;
}

/**
 * 校验手机号
 */
function validateTelephone() {
	var id = "telephone";
	var value = $("#"+id).val();//获取表单中的value值
	if(!value){
		$("#"+id+"Error").text("手机号不能为空！");
		showError($("#"+id+"Error"));
		return false;
	} 
	if(!/^([0-9]{11})$/.test(value)){
		$("#"+id+"Error").text("手机号格式错误,必须为数字0-9,长度为11位！");
		showError($("#"+id+"Error"));
		return false;
	}
	
	$.ajax({
		url:"ajaxValidateTelephone.do",
		data:{
			method:"ajaxValidateTelephone",
			telephone:value			
		},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result){
			if(!result){
				$("#"+id+"Error").text("手机号已被注册!");
				showError($("#"+id+"Error"));
				return false;
			}
			return true;
		}
	});
	return true;	
}


/**
 * 密保答案校验
 */
function validateAnswer() {
	var id = "answer";
	var value = $("#"+id).val();
	if(!value) {
		$("#"+id+"Error").text("答案不能为空!");
		showError($("#"+id+"Error"));
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
			} else{				
				return true;
			}
				
		}
	});
	return true;
}