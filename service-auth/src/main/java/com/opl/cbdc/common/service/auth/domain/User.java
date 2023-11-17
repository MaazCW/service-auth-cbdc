package com.opl.cbdc.common.service.auth.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
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
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Convert(converter = EncryptionUtils.class)
	private String email;

	@Convert(converter = EncryptionUtils.class)
	private String mobile;

	private String password;

	@Convert(converter = EncryptionUtils.class)
	private String username;

	@Convert(converter = EncryptionUtils.class)
	@Column(name = "first_name")
	private String firstName;

	@Convert(converter = EncryptionUtils.class)
	@Column(name = "last_name")
	private String lastName;

	@Convert(converter = EncryptionUtils.class)
	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_business_type_id")
	private Integer lastBusinessTypeId;

	@Column(name = "terms_accepted")
	private boolean termsAccepted;

	@Column(name = "otp_verified")
	private boolean otpVerified;

	@Column(name = "email_verified")
	private boolean emailVerified;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "is_locked")
	private Boolean isLocked;

	@Column(name = "login_counter")
	private Integer loginCounter;

	@Column(name = "campaign_code")
	private String campaignCode;

	@Column(name = "password1")
	private String password1;

	@Column(name = "password2")
	private String password2;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sign_up_date")
	private Date signUpDate;

	@ManyToOne
	@JoinColumn(name = "user_org_id")
	private UserOrganisationMaster userOrgId;

	@ManyToOne
	@JoinColumn(name = "user_type_id")
	private UserTypeMaster userTypeMaster;

	@Column(name = "is_pass_changed")
	private Boolean isPassChanged;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "branch_id")
	private BranchMaster branchId;

	@ManyToOne
	@JoinColumn(name = "user_role_id")
	private UserRoleMaster userRoleId;

}
