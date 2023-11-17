package com.opl.cbdc.common.api.auth.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userId;
	private String username;
	private String password;
	private String grant_type;
	private String clientId;
	private String clientSecret;
	private String refreshToken;
	private String accessToken;
	private Integer loginToken;
	private String userBrowser;
	private String userIp;
	private boolean isDomainLogin;

	/**
	 * SET TRUE IN CASE OF MOBILE LOGIN AND FALSE IN CASE OF WEB LOGIN
	 */
	private Boolean isMobileLogin;
	private String fcmToken;

	private String modelNo;
	private Long userTypeId;

	private String imeiNo;

	private String mobileOs;

	private String osVersion;

	private String appVersion;

	private String browserVersion;
	private String device;
	private String deviceType;
	private String deviceOs;
	private String deviceOsVersion;
	private String userAgent;
	private String campaignCode;
	private Long campaignMasterId;
	private Long clientUserId;

	public AuthRequest() {

	}

	public AuthRequest(Long userId) {
		this.userId = userId;
	}

	public AuthRequest(Integer loginToken) {
		this.loginToken = loginToken;
	}

	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public AuthRequest(String username, String accessToken, String refreshToken) {
		this(username, null, null, refreshToken, accessToken, null);
	}

	public AuthRequest(String username, String clientId, String clientSecret, String refreshToken, String accessToken,
			String grant_type) {
		this.username = username;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
		this.grant_type = grant_type;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public Long getCampaignMasterId() {
		return campaignMasterId;
	}

	public void setCampaignMasterId(Long campaignMasterId) {
		this.campaignMasterId = campaignMasterId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
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

	public Integer getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(Integer loginToken) {
		this.loginToken = loginToken;
	}

	public String getUserBrowser() {
		return userBrowser;
	}

	public void setUserBrowser(String userBrowser) {
		this.userBrowser = userBrowser;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public boolean getIsDomainLogin() {
		return isDomainLogin;
	}

	public void setIsDomainLogin(boolean isDomainLogin) {
		this.isDomainLogin = isDomainLogin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public void setDomainLogin(boolean isDomainLogin) {
		this.isDomainLogin = isDomainLogin;
	}

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public Boolean getIsMobileLogin() {
		return isMobileLogin;
	}

	public void setIsMobileLogin(Boolean isMobileLogin) {
		this.isMobileLogin = isMobileLogin;
	}
	
	public Long getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(Long clientUserId) {
		this.clientUserId = clientUserId;
	}

	@Override
	public String toString() {
		return "AuthRequest{" + "userId=" + userId + ", username='" + username + '\'' + ", password='" + password + '\''
				+ ", grant_type='" + grant_type + '\'' + ", clientId='" + clientId + '\'' + ", clientSecret='"
				+ clientSecret + '\'' + ", refreshToken='" + refreshToken + '\'' + ", accessToken='" + accessToken
				+ '\'' + ", loginToken=" + loginToken + ", userBrowser='" + userBrowser + '\'' + ", userIp='" + userIp
				+ '\'' + ", isDomainLogin=" + isDomainLogin + ", modelNo='" + modelNo + '\'' + ", imeiNo='" + imeiNo
				+ '\'' + ", mobileOs='" + mobileOs + '\'' + ", osVersion='" + osVersion + '\'' + ", appVersion='"
				+ appVersion + '\'' + '}';
	}
}
