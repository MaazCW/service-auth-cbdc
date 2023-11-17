package com.opl.cbdc.common.service.auth.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.opl.cbdc.utils.common.SchemaMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the user_organisation_master database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "user_organisation_master", schema = SchemaMaster.USERS, catalog = SchemaMaster.USERS)
@NamedQuery(name = "UserOrganisationMaster.findAll", query = "SELECT u FROM UserOrganisationMaster u")
public class UserOrganisationMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_org_id")
	private Long userOrgId;

	@Column(name = "organisation_name")
	private String organisationName;

	@Column(name = "display_org_name")
	private String displayOrgName;

	@Column(name = "organisation_code")
	private String organisationCode;

	@Column(name = "org_type")
	private Integer orgType;

	// bi-directional many-to-one association to UserTypeMaster
	@ManyToOne
	@JoinColumn(name = "user_type_id")
	private UserTypeMaster userTypeMaster;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "uat_url")
	private String uatUrl;

	@Column(name = "production_url")
	private String productionUrl;

	@Column(name = "is_reverse_api_activated")
	private Boolean isReverseApiActivated;

	@Column(name = "code_lang")
	private Integer codeLanguage;

	@Column(name = "config")
	private String config;

	@Column(name = "general_fields")
	private String generalFields;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "modified_date")
	private Timestamp modifiedDate;

	@Column(name = "is_active")
	private boolean isActive;

	@Lob
	@Column(name = "general_config")
	private String generalConfig;

	@Column(name = "control_block_msme")
	private String controlBlockMsme;

	@Column(name = "control_block_ntb")
	private String controlBlockNtb;

	@Column(name = "campaign_type")
	private Long campaignType;

}