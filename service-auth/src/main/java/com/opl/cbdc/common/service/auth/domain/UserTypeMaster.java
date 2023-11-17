package com.opl.cbdc.common.service.auth.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the user_type_master database table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name="user_type_master")
@NamedQuery(name="UserTypeMaster.findAll", query="SELECT u FROM UserTypeMaster u")
public class UserTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String code;

	@Column(name="created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	private String description;

	@Column(name="is_active")
	private boolean isActive;

	@Column(name="modified_by")
	private Long modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userTypeMaster")
	private List<User> users;

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserTypeMaster(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserTypeMaster(null);

		return user;
	}

}