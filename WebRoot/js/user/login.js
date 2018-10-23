$(function(){
	//选择所有显示错误的标签，然后遍历所有显示错误的标签
	$(".errorClass").each(function(){
		showError($(this));
	});
	
   $(".close").click(function(){
	   /*
	    * 仅关闭body
	    */
//	   $("body").css("display","none");
//	   window.closed();
	   
	   /*
	    * 关闭整个html
	    */
	   if(confirm("您确定要关闭本页吗？")){
	  
	     if (navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Chrome") !=-1) {  
		           window.location.href="about:blank";  
		           window.close();  
		       } else {  
		           window.opener = null;  
		           window.open("", "_self");  
		           window.close();  
		       } 
	   } 
   });
 
 
  /**
   * 得到焦点
   */
  $(".inputClass").focus(function(){
	 var labelId = $(this).attr("id")+"Error";
	 $("#"+labelId).text("");//清空内容
	 showError($("#"+labelId));//隐藏标签
	 
  })
   
   $(".verify").focus(function(){
	 var labelId = $(this).attr("id")+"Error";
	 $("#"+labelId).text("");//清空内容
	 showError($("#"+labelId));//隐藏标签	 
  })
  
  /**
   * 失去焦点，校验
   */
  $(".inputClass").blur(function(){
	  var id = $(this).attr("id");
	  var functionName = "validate"+id.substring(0, 1).toUpperCase()+id.substring(1)+"()";
	  eval(functionName);//执行对应的校验函数 	  
  });
  
  $(".verify").blur(function(){
	  var id = $(this).attr("id");
	  var functionName = "validate"+id.substring(0, 1).toUpperCase()+id.substring(1)+"()";
	  eval(functionName);//执行对应的校验函数 	  
  });
  
  
  /*
	 * 只有boolValue为true时，才能提交数据到后台
	 */
	$("#loginForm").submit(function(){
		var boolValue = true;  //表示校验通过		
		if (!validateLoginname()) {
			boolValue = false;
		}
		if (!validateLoginpass()) {
			boolValue = false;
		}
		if (!validateVerifyCode()) {
			boolValue = false;
		}	
		return boolValue;
	});  
});

//显示错误信息
function showError(element) {
	var text = element.text();
	if(text){//有内容，显示
		element.css("display","");
	} else {//无内容，隐藏
		element.css("display","none");
	}
}

/**
 * 校验登录名
 */
function validateLoginname() {
	var id = "loginname";
	var value = $("#" + id).val();
	/*
	 * 非空校验
	 */
	if(!value){
		$("#"+id+"Error").text("用户名不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}	
	
	/*
	 * 长度校验
	 */
	if(value.length < 3 || value.length > 20){
		$("#"+id+"Error").text("用户名长度必须在3~20之间！");
		showError($("#"+id+"Error"));
		return false;
	}
	
//	/*
//	 * 是否注册校验
//	 */
//	$.ajax({		
//		url:"ajaxValidateLoginname.do",
//		data:{method:"ajaxValidateLoginname",loginname:value},
//	    type:"POST",
//	    dataType:"json",
//	    async:false,
//	    cache:false,
//	    success:function(result){
//	    	if(!result){
//	    		$("#"+id+"Error").text("用户名已被注册！");
//	    		showError($("#"+id+"Error"));
//	    		return false;
//	    	}
//	    	return true;
//	    }
//});
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
	if(!value){
		$("#"+id+"Error").text("密码不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}	
	
	/*
	 * 长度校验
	 */
	if(value.length < 6 || value.length > 16){
		$("#"+id+"Error").text("密码长度必须在6~16之间！");
		showError($("#"+id+"Error"));
		return false;
	}
	
	/*
	 * 密码格式限制
	 */
	if(!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,16}$/.test(value)){
		$("#"+id+"Error").text("密码必须同时含有字母和数字！");
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
	if(!value){
		$("#" + id+"Error").text("验证码不能为空！");
		showError($("#"+id+"Error"));
		return false;
	}
	
	if(value.length != 4) {
		$("#" + id+"Error").text("验证码长度不对！");
		showError($("#"+id+"Error"));
		return false;
	}
	
	$.ajax({
		url:"ajaxValidateVerifyCode.do",
		data:{method:"ajaxValidateVerifyCode", verifyCode:value},
		type:"POST",
		dataType:"json",
		async:false,
		cache:false,
		success:function(result) {
			if(!result) {
				$("#" + id+"Error").text("验证码错误！");
				showError($("#"+id+"Error"));
				return false;
			}
			return true;
		}
	});
	return true;		
}
