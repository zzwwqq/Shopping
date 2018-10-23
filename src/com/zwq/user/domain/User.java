package com.zwq.user.domain;

public class User {
	
	//对应数据库表
    private String uid;//主键
    private String loginname;//登录名
    private String loginpass;//登录密码
    private String email;//邮箱
    private int status;//状态，1表示已激活
    private String activationCode;//激活码，它是唯一值！即每个用户的激活码是不同的！
	
    //注册表单
    private String reloginpass;//确认密码
    private String verifyCode;//验证码
    
    
    //修改密码表单
    private String newpass;
    
    private String telephone;//手机号
    private String passwordProtected;//密码保护问题
    private String answer;//密码保护的答案
    

	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getLoginname() {
		return loginname;
	}


	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}


	public String getLoginpass() {
		return loginpass;
	}


	public void setLoginpass(String loginpass) {
		this.loginpass = loginpass;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getActivationCode() {
		return activationCode;
	}


	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}


	public String getReloginpass() {
		return reloginpass;
	}


	public void setReloginpass(String reloginpass) {
		this.reloginpass = reloginpass;
	}


	public String getVerifyCode() {
		return verifyCode;
	}


	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}


	public String getNewpass() {
		return newpass;
	}


	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getPasswordProtected() {
		return passwordProtected;
	}


	public void setPasswordProtected(String passwordProtected) {
		this.passwordProtected = passwordProtected;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public User() {
		super();
		// TODO 自动生成的构造函数存根
	}


	public User(String uid, String loginname, String loginpass, String email, int status, String activationCode,
			String reloginpass, String verifyCode, String newpass, String telephone, String passwordProtected,
			String answer) {
		super();
		this.uid = uid;
		this.loginname = loginname;
		this.loginpass = loginpass;
		this.email = email;
		this.status = status;
		this.activationCode = activationCode;
		this.reloginpass = reloginpass;
		this.verifyCode = verifyCode;
		this.newpass = newpass;
		this.telephone = telephone;
		this.passwordProtected = passwordProtected;
		this.answer = answer;
	}


	@Override
	public String toString() {
		return "User [uid=" + uid + ", loginname=" + loginname + ", loginpass=" + loginpass + ", email=" + email
				+ ", status=" + status + ", activationCode=" + activationCode + ", reloginpass=" + reloginpass
				+ ", verifyCode=" + verifyCode + ", newpass=" + newpass + ", telephone=" + telephone
				+ ", passwordProtected=" + passwordProtected + ", answer=" + answer + "]";
	}
    
    
    
    
    
    
    
}
