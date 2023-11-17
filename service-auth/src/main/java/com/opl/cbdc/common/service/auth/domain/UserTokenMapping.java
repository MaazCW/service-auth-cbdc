package com.opl.cbdc.common.service.auth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.opl.cbdc.utils.common.EncryptionUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "user_token_mapping")
public class UserTokenMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_name")
	@Convert(converter = EncryptionUtils.class)
	private String userName;

	@Column(name = "user_type")
	private Long userType;

	@Column(name = "user_branch_id")
	private Long userBranchId;

	@Column(name = "user_role_id")
	private Long userRoleId;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "login_token")
	private Integer loginToken;

	@Column(name = "expires_in")
	private String expiresIn;

	@Column(name = "active")
	private Boolean active;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_date")
	private Date loginDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logout_date")
	private Date logoutDate;

	@Column(name = "user_ip")
	private String userIp;

	@Column(name = "user_browser")
	private String userBrowser;

	@Column(name = "domain_isactive")
	private Boolean domainIsactive;

	@Column(name = "user_org_id")
	private Long userOrgId;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "mobile_os")
	private String mobileOs;

	@Column(name = "model_no")
	private String modelNo;

	@Column(name = "os_version")
	private String osVersion;

	@Column(name = "app_version")
	private String appVersion;

	@Column(name = "browser_version")
	private String browserVersion;

	@Column(name = "device")
	private String device;

	@Column(name = "device_type")
	private String deviceType;

	@Column(name = "device_os")
	private String deviceOs;

	@Column(name = "device_os_version")
	private String deviceOsVersion;

	@Column(name = "user_agent")
	private String userAgent;

	@Column(name = "campaign_code")
	private String campaignCode;

	@Column(name = "campaign_master_id")
	private Long campaignMasterId;
	
	@Column(name = "is_mobile_login")
	private Boolean isMobileLogin;
	
	@Column(name = "mobile_fcm_token")
	private String mobileFcmToken;
	
	@Column(name = "client_user_id")
	private Long clientUserId;

}
