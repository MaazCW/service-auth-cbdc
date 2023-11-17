package com.opl.cbdc.common.api.auth.model;

import java.util.Collections;
import java.util.List;

public class AuthResponse {

	private String access_token;
	private String token_type;
	private String expires_in;
	private String refresh_token;
	private String scope;
	private Long userType;
	private Long userId;
	private String message;
	private Integer status;
	private String email;
	private Integer loginToken;
	private Long userOrgId;
	private Long userRoleId;
	private Long userBranchId;
	private List<String> campaignCode;
	private Boolean isFromDealerUser;
	private String userName;

	public AuthResponse() {
		campaignCode = Collections.emptyList();
	}

	public AuthResponse(String message, Integer status) {
		this.message = message;
		this.status = status;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(Integer loginToken) {
		this.loginToken = loginToken;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserOrgId() {
		return userOrgId;
	}

	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}

	public List<String> getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(List<String> campaignCode) {
		this.campaignCode = campaignCode;
	}

	public Boolean getIsFromDealerUser() {
		return isFromDealerUser;
	}

	public void setIsFromDealerUser(Boolean fromDealerUser) {
		isFromDealerUser = fromDealerUser;
	}

	public Long getUserBranchId() {
		return userBranchId;
	}

	public void setUserBranchId(Long userBranchId) {
		this.userBranchId = userBranchId;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "AuthResponse [access_token=" + access_token + ", token_type=" + token_type + ", expires_in="
				+ expires_in + ", refresh_token=" + refresh_token + ", scope=" + scope + ", userType=" + userType
				+ ", userId=" + userId + ", message=" + message + ", status=" + status + ", email=" + email
				+ ", loginToken=" + loginToken + ", userOrgId=" + userOrgId + ", userRoleId=" + userRoleId
				+ ", userBranchId=" + userBranchId + ", campaignCode=" + campaignCode + ", isFromDealerUser="
				+ isFromDealerUser + ", userName=" + userName + "]";
	}

	

}
