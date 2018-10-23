package com.zwq.user.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.zwq.user.domain.User;
import com.zwq.user.service.UserService;
import com.zwq.user.service.exception.UserException;

import cn.itcast.commons.CommonUtils;


public class UserServlet extends HttpServlet{

	private UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

/**
 * 此方式会在浏览器地址栏暴露method=xxx,不私密，有安全隐患	
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String method = request.getParameter("method");
//		switch (method) {
//		case "login":
//			login(request, response);
//			break;
//		default:
//			break;
//		}
//	}
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取ServletPath: /login.do
		String servletPath = request.getServletPath();
		
		//2. 去除/ 和 .do,得到类似于edit或addCustomer 这样的字符串
		String methodName = servletPath.substring(1);
		methodName = methodName.substring(0, methodName.length()-3);
		
		try {
			//3. 利用反射获取methodName对应的方法
            Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			
			//4.利用反射调用相应的方法
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
	

	/**
	 * 注册
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
//	System.out.println("regist");	
		/*
		 * 封装表单数据到User对象
		 */
		User userForm = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		/*
		 * 校验
		 * 校验失败则保存错误信息，同时保存表单数据用于回显，并返回regist.jsp
		 */
		Map<String, String> errors = validateRegist(userForm, request.getSession());
		/*
		 * 验证失败
		 */
		if (errors.size() > 0) {
			//保存错误信息，保存表单数据用于回显
			request.setAttribute("userForm",userForm);
			request.setAttribute("errors", errors);
			//转发
			request.getRequestDispatcher("/jsps/user/regist.jsp").forward(request, response);
			/*
			 * 小提示：
             * forward(request, response);跳转页面，执行这个语句之后，如果此语句后面还有代码，既然跳转了页面后面代码执行不了，原页面的代码没有终止一定会出错。
             * 解决方法，在forward(request, response);跳转后面不要写其他代码，且加上 return；
			 */
			return;
		}
		/*
		 * 校验成功，则调用service层方法保存用户注册信息到数据库
		 */
		userService.regist(userForm);
		/*
		 * 保存成功信息，并转发到msg.jsp
		 */	
		request.setAttribute("code", "success");
		request.setAttribute("msg", "注册成功，请马上去邮箱激活！");
		//request.getRequestDispatcher("index.jsp").forward(request, response);
		request.getRequestDispatcher("jsps/msg.jsp").forward(request, response);
		return;
	}
	
	/**
	 * 验证所有注册信息
	 * 这是后台校验，较前台校验更安全
	 * 将错误信息保存在Map中
	 * 要点：向HashMap中添加元素时，会调用key所在类的equals()方法，判
	 *     断两个key是否相同，若相同 ,则只能添加进后添加的那个元素。
	 * @param userForm
	 * @param session
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws SQLException 
	 */
	private Map<String,String> validateRegist(User userForm,HttpSession session) throws ServletException, IOException, SQLException {
		Map<String, String> errors = new HashMap<String, String>();
		/*
		 * 验证用户名
		 * 注意两种情形：第一种用户名为空null，
		 *           第二种用户名为空格（注意null != 空格）
		 *   trim函数去掉空格      
		 */
		if(userForm.getLoginname() == null || userForm.getLoginname().trim().isEmpty()) {
			errors.put("loginnameError", "用户名不能为空");
		} else if (userForm.getLoginname().length() < 3 || userForm.getLoginname().length() > 20) {
			errors.put("loginnameError", "用户名长度必须在3~20之间！");
		} else if (!userService.ajaxValidateLoginname(userForm.getLoginname())) {
			errors.put("loginnameError", "用户名已被注册！");
		}
				
		/*
		 * 验证密码
		 */
		String loginpass = userForm.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginnameError", "密码不能为空");
		} else if (loginpass.length() < 6 || loginpass.length() > 16) {
			errors.put("loginnameError", "密码长度必须在6~16之间！");
		}
			
		/*
		 * 验证确认密码
		 */
		String reloginpass = userForm.getReloginpass();
		if(!loginpass.equals(reloginpass)) {
			errors.put("reloginpassError", "两次输入的密码不一致！");
		} else if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpassError", "确认密码不能为空！");
		}

		/*
		 * 验证邮箱
		 */
		String email = userForm.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("emailError", "Email不能为空！");
			//java和js有区别：/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$/中的/要去掉，\要改为\\
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")){
			errors.put("emailError", "Email格式错误！");
		} else if (!userService.ajaxValidateEmail(email)) {
			errors.put("emailError", "Email已被注册！");
		}

		/*
		 * 验证手机号
		 */
		String telephone = userForm.getTelephone();
		if(telephone == null || telephone.trim().isEmpty()) {
			errors.put("telephoneError", "手机号不能为空!");
		} else if(!telephone.matches("^([0-9]{11})$")) {
			errors.put("telephoneError", "手机号格式错误，必须为数字，且长度为11位！");
		} else if (!userService.ajaxValidateTelephone(telephone)) {
			errors.put("telephoneError", "手机号已被注册！");
		}
		
		/*
		 * 验证密保答案
		 */
		String answer = userForm.getAnswer();
		if (answer == null || answer.trim().isEmpty()) {
			errors.put("answerError", "密保不能为空!");
		}
		
		/*
		 * 验证验证码
		 */
		String verifyCode = userForm.getVerifyCode();
		String vCode = (String) session.getAttribute("vCode");
		if(verifyCode==null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCodeError", "验证码不能为空!");
		} else if(verifyCode.length() != 4) {
			errors.put("verifyCodeError", "验证码长度不对!");
		} else if (!verifyCode.equalsIgnoreCase(vCode)) {
			errors.put("verifyCodeError", "验证码错误!");
		}		
		return errors;	
	}
	

	/*
	 * 激活功能
	 */
	private void activation(HttpServletRequest request,HttpServletResponse response) {
		String activationCode = request.getParameter("activationCode");
		try {
			userService.activation(activationCode);
			//上一句未抛出异常时，执行下面的语句
			request.setAttribute("code", "success");
			request.setAttribute("msg", "恭喜您激活成功！");
		} catch (UserException e) {
			request.setAttribute("code", "error");
			request.setAttribute("msg", e.getMessage());
		}
		try {
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return;
	}
	
	
	/**
	 * 登录功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("login");
		/*
		 * 封装表单数据
		 */
		User userForm = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		/*
		 * 校验
		 * 如果校验失败，返回登录界面，并回显表单数据
		 * 特别提示：如果转发语句的后面还有语句，那么一定要在转发语句后面添加return;否则会抛异常
		 */
		Map<String, String> errors = validateLogin(userForm, request.getSession());
		if (errors.size() > 0) {
			request.setAttribute("userForm", userForm);
			request.setAttribute("errors", errors);
			/*
			 * 特别提示：如果转发语句的后面还有语句，那么一定要在转发语句后面添加return;否则会抛异常
			 * 转发时，路径中第一个"/"代表" http://localhost:8080/blog/"
			 * 重定向，时路径中第一个"/"代表" http://localhost:8080/"
			 */
			request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, response);
			return;
		}
	
		/*
		 * 校验成功，保存用户数据到session和cookie中，并跳转到成功界面
		 */
		//保存用户到session，目的：当要修改当前用户密码时，可以直接从session中获取用户名，然后拿着这个用户名和表单中输入的旧密码一起到数据库查询，来确定旧密码是否正确
		User user = userService.login(userForm.getLoginname(), userForm.getLoginpass());
		request.getSession().setAttribute("sessionUser", user);
		URLEncoder.encode(user.getLoginname(), "UTF-8");
		Cookie cookie = new Cookie("loginname", user.getLoginname());
		cookie.setMaxAge(60*60*24*10);//cookie保存10天
		response.addCookie(cookie);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "恭喜您登录成功！");
		//重定向后，request被清空，不能再获取request中的数据
		//response.sendRedirect("/blog/index.jsp");
		//转发
		request.getRequestDispatcher("/jsps/main.jsp").forward(request, response);
	}
	
	/**
	 * 登录校验
	 * @return
	 */
	private Map<String, String> validateLogin(User userForm,HttpSession session) {
		Map<String, String>errors=new HashMap<String, String>();
		String loginname = userForm.getLoginname();
		String loginpass = userForm.getLoginpass();
		String verifyCode = userForm.getVerifyCode();
		User user = new User();
		/*
		 * 验证用户名
		 */
		if (loginname == null|| loginname.trim().isEmpty()) {
			errors.put("loginnameError", "用户名不能为空！");
		}
		if (loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginnameError", "用户名长度必须在3~20之间！");
		}
			
		/*
		 * 验证密码
		 */
		user = userService.login(loginname, loginpass);
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpassError", "密码不能为空！");
		}
		if(user == null) {
			errors.put("loginpassError", "用户名或密码错误!");
		} else {
			if (user.getStatus() == 0) {
				errors.put("loginpassError", "账号未激活，请查看邮件激活!");
			}
		}
		
		
		/*
		 * 验证验证码
		 */
	    String vCode = (String) session.getAttribute("vCode");
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCodeError", "验证码不能为空！");
		}
		if (verifyCode.length() != 4) {
			errors.put("verifyCodeError","验证码长度不对!");
		}
		if(!verifyCode.equalsIgnoreCase(vCode)) {
			errors.put("verifyCodeError", "验证码错误!");
		}
		return errors;		
	}
	
	
	/**
	 * 修改密码
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void updatePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.封装表单数据到user中
		 * 2.校验
		 * 3.从session中获取uid
		 * 4.使用uid和表单中的oldPass和newPass来调用service方法
		 * >如果出现异常，保存异常信息到request，转发到pwd.jsp
		 * 5.保存成功信息到request中
		 * 6.转发到msg.jsp
		 */

		User userForm = CommonUtils.toBean(request.getParameterMap(), User.class);

		Map<String, String> errors = validateUpdatePassword(userForm, request.getSession());
		if (errors.size() > 0) {
			request.setAttribute("userForm", userForm);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/jsps/user/updatepassword.jsp").forward(request, response);
			return;
		}	
		User user = (User) request.getSession().getAttribute("sessionUser");
		if(user == null) {
			request.setAttribute("msg", "您还未登录!");
			request.setAttribute("code", "error");
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);;
			return;
		}
		
		/*
		 * 用户已经登录
		 */
		try {
			userService.updatePassword(user.getUid(), userForm.getNewpass(), userForm.getLoginpass());			
			request.setAttribute("msg", "修改密码成功！");
			request.setAttribute("code", "success");
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);;
			return;
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("userForm", userForm);
			request.getRequestDispatcher("/jsps/user/updatepassword.jsp").forward(request, response);;
			return;
		}
	
	}
	
	private Map<String,String> validateUpdatePassword(User userForm,HttpSession session) {
		Map<String, String>errors=new HashMap<String, String>();
		String loginpass = userForm.getLoginpass();
		String newpass = userForm.getNewpass();
		String reloginpass = userForm.getReloginpass();
		String verifyCode = userForm.getVerifyCode();
		String vCode = (String) session.getAttribute("vCode");

		/*
		 * 非空校验 ，格式校验
		 */
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpassError", "原密码不能为空！");
		} else if (loginpass.length() < 6 || loginpass.length() > 16) {
			errors.put("loginnameError", "密码长度必须在6~16之间！");
		}
		if (newpass == null || newpass.trim().isEmpty()) {
			errors.put("newpassError", "新密码不能为空！");
		} else if (newpass.length() < 6 || newpass.length() > 16) {
			errors.put("newpassError", "密码长度必须在6~16之间！");
		}
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpassError", "确认密码不能为空！");
		} else if(!newpass.equals(reloginpass)) {
			errors.put("reloginpassError", "两次密码不一致！");
		}
		
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCodeError", "验证码不能为空！");
		}  else if(verifyCode.length() != 4) {
			errors.put("verifyCodeError", "验证码长度不对!");
		} else if (!verifyCode.equalsIgnoreCase(vCode)) {
			errors.put("verifyCodeError", "验证码错误!");
		}	
		return errors;
		
	}

	/**
	 * 前台js文件中ajax请求该函数
	 * 前台仅仅需要一个Boolean值
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	private void ajaxValidateLoginname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {			 
		String loginname = request.getParameter("loginname");
		boolean b = userService.ajaxValidateLoginname(loginname);	
		/*
		 * 将信息返回客户端
		 */
		response.getWriter().print(b);
		
	}
	
	private void ajaxValidateEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String email = request.getParameter("email");
		boolean b = userService.ajaxValidateEmail(email);
		response.getWriter().print(b);
	}
		
	private void ajaxValidateTelephone(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		String telephone = request.getParameter("telephone");
		boolean b = userService.ajaxValidateTelephone(telephone);
		response.getWriter().print(b);
	}
		
	/**
	 * 验证码保存在session中，而不是数据库中，所以此处直接比较session中的验证码和用户输入的验证码
	 */
	private void ajaxValidateVerifyCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 从表单获取验证码
		 */
		String verifyCode = request.getParameter("verifyCode");
		/*
		 * 从session中获取验证码
		 */
		String vCode = (String) request.getSession().getAttribute("vCode");
		
		/*
		 * 比较验证码，注意：忽略大小写
		 */
		boolean b = verifyCode.equalsIgnoreCase(vCode);
		/*
		 * 返回信息到客户端
		 */
		response.getWriter().print(b);
	}
	
	/**
	 * 找回密码
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findPassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 封装表单数据
		 */
		User userForm = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		/*
		 * 校验
		 */
		Map<String, String>errors = userService.validatefindPassword(userForm, request.getSession());
		if (errors.size() > 0) {
			request.setAttribute("userForm", userForm);
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/jsps/user/findPassword.jsp").forward(request, response);;
			return;
		} 
		
		/*
		 * 校验通过,调用service层方法，发送短信
		 * 保存成功信息，转发到信息板
		 */
		userService.findPassword(userForm);
		request.setAttribute("code", "success");
		request.setAttribute("msg", "短信已发送，请注意查收！");
		request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
		return;
	}
	
	public void quit(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		//手动销毁session
		request.getSession().invalidate();
		request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, response);
	}
	
	
			
}
