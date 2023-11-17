package com.opl.cbdc.common.api.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthClientResponse {

	private Long userId;
	private String message;
	private Integer status;
	private boolean isAuthenticate;
	private Long userType;
	private Long userOrgId;
	private Long userBranchId;
	private Long userRoleId;
	private Integer lastBusinessTypeId;
	private String email;
	private String mobile;
	private String name;
	private String userName;
	private Long clientUserId;

	public AuthClientResponse(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

}
