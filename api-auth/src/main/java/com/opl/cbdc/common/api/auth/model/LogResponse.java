package com.opl.cbdc.common.api.auth.model;

import java.io.Serializable;
import java.util.Date;
public class LogResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date loginDate;
	private Long userId;
	private String userEmail;
	
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
}
