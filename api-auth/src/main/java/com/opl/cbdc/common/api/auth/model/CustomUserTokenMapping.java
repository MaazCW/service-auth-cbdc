package com.opl.cbdc.common.api.auth.model;

import java.io.Serializable;

public class CustomUserTokenMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private String userName;
	private String refreshToken;
	private String accessToken;
	private String expiresIn;
	private boolean active;
	private Integer loginToken;
	private String userIp;
	private String userBrowser;
	private boolean isDomainLogin;
	private String modelNo;
	private Long userTypeId;

	private String imeiNo;

	private String mobileOs;

	private String osVersion;

	private String appVersion;

	private String browserName;
	private String browserVersion;
	private String device;
	private String deviceType;
	private String deviceOs;
	private String deviceOsVersion;
	private String userAgent;
	private String campaignCode;
	private Long campaignMasterId;
	private Boolean isMobileLogin;
	private String fcmToken;

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public Boolean isMobileLogin() {
		return isMobileLogin;
	}

	public void setMobileLogin(Boolean isMobileLogin) {
		this.isMobileLogin = isMobileLogin;
	}

	public CustomUserTokenMapping() {

	}

	public CustomUserTokenMapping(String userName, String accessToken) {
		this.userName = userName;
		this.accessToken = accessToken;
	}

	public Long getCampaignMasterId() {
		return campaignMasterId;
	}

	public void setCampaignMasterId(Long campaignMasterId) {
		this.campaignMasterId = campaignMasterId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(Integer loginToken) {
		this.loginToken = loginToken;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserBrowser() {
		return userBrowser;
	}

	public void setUserBrowser(String userBrowser) {
		this.userBrowser = userBrowser;
	}

	public boolean isDomainLogin() {
		return isDomainLogin;
	}

	public void setDomainLogin(boolean isDomainLogin) {
		this.isDomainLogin = isDomainLogin;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
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

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public Long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceOs() {
		return deviceOs;
	}

	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}

	public String getDeviceOsVersion() {
		return deviceOsVersion;
	}

	public void setDeviceOsVersion(String deviceOsVersion) {
		this.deviceOsVersion = deviceOsVersion;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
}
