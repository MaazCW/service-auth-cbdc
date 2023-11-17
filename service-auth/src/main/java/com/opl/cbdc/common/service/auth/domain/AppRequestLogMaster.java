package com.opl.cbdc.common.service.auth.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "app_request_log_master")
public class AppRequestLogMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "app_login_id")
	private Long appLoginId;

	@Column(name = "request_no")
	private String requestNo;

	@Column(name = "request_obj")
	private String requestObj;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "model_no")
	private String modelNo;

	@Column(name = "mobile_os")
	private String mobileOs;

	@Column(name = "imei_no")
	private String imeiNo;

	@Column(name = "os_version")
	private String osVersion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "request_url")
	private String requestUrl;

	@Column(name = "is_active")
	private Boolean isActive;

}
