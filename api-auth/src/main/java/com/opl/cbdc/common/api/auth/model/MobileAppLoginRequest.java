package com.opl.cbdc.common.api.auth.model;

import java.io.Serializable;

public class MobileAppLoginRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private String modelNo;
	private String mobileNo;
	private String imeiNo;
	private String token;
	private String mobileOs;
	private String osVersion;
	private String requestNo;
	private String requestObj;
	private String requestUrl;
	private String otp;
	
	public MobileAppLoginRequest() {
		
	}
	
	public MobileAppLoginRequest(String modelNo, String mobileNo, String imeiNo, String token, String mobileOs,
			String osVersion, String requestNo, String requestObj, String requestUrl) {
		super();
		this.modelNo = modelNo;
		this.mobileNo = mobileNo;
		this.imeiNo = imeiNo;
		this.token = token;
		this.mobileOs = mobileOs;
		this.osVersion = osVersion;
		this.requestNo = requestNo;
		this.requestObj = requestObj;
		this.requestUrl = requestUrl;
		
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getModelNo() {
		return modelNo;
	}
	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getImeiNo() {
		return imeiNo;
	}
	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMobileOs() {
		return mobileOs;
	}
	public void setMobileOs(String mobileOs) {
		this.mobileOs = mobileOs;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestObj() {
		return requestObj;
	}
	public void setRequestObj(String requestObj) {
		this.requestObj = requestObj;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	
	
	
	
}
