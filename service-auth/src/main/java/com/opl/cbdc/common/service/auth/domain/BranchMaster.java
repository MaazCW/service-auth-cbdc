package com.opl.cbdc.common.service.auth.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

import com.opl.cbdc.utils.common.SchemaMaster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the branch_mster database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "branch_master", schema = SchemaMaster.USERS, catalog = SchemaMaster.USERS)
@NamedQuery(name = "BranchMaster.findAll", query = "SELECT b FROM BranchMaster b")
public class BranchMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "city_id")
	private Integer cityId;

	private String code;

	@Column(name = "contact_person_email")
	private String contactPersonEmail;

	@Column(name = "contact_person_name")
	private String contactPersonName;

	@Column(name = "contact_person_number")
	private String contactPersonNumber;

	@Column(name = "country_id")
	private Integer countryId;

	@Column(name = "created_by")
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "fax_no")
	private String faxNo;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "is_ho")
	private Boolean isHo;

	@Column(name = "land_mark")
	private String landMark;

	private String name;

	@ManyToOne
	@JoinColumn(name = "org_id")
	private UserOrganisationMaster orgId;

	@Column(name = "parent_branch_id")
	private Integer parentBranchId;

	private Integer pincode;

	@Column(name = "premises_no")
	private String premisesNo;

	private String remarks;

	@Column(name = "state_id")
	private Integer stateId;

	@Column(name = "street_name")
	private String streetName;

	@Column(name = "telephone_no")
	private String telephoneNo;

	@Column(name = "ifsc_code")
	private String ifscCode;

//	@ManyToOne
//	@JoinColumn(name="location_id")
//	private LocationMaster locationId;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "job_id")
	private Long jobId;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "loan_system_id")
	private Long loanSystemId;

	@Column(name = "smec_code")
	private String smecCode;

	@Column(name = "smec_name")
	private String smecName;

	@Column(name = "smec_email")
	private String smecEmail;

	@Column(name = "smec_mobile")
	private String smecMobile;

	@Column(name = "branch_type")
	private Integer branchType;

	@Column(name = "branch_ip")
	private String branchIp;

}
