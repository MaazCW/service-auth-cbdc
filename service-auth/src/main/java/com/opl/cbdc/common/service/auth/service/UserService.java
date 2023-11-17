package com.opl.cbdc.common.service.auth.service;

import com.opl.cbdc.common.api.auth.model.UserRequest;

public interface UserService {

	public UserRequest findOneByEmailByUserTypeId(String email, Long userTypeId);
}
