package com.opl.cbdc.common.service.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.opl.cbdc.common.api.auth.model.UserRequest;
import com.opl.cbdc.common.service.auth.domain.User;
import com.opl.cbdc.common.service.auth.repositories.UserRepository;
import com.opl.cbdc.common.service.auth.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserRequest findOneByEmailByUserTypeId(String email, Long userTypeId) {
		User user =  userRepository.findOneByEmailAndUserTypeMasterId(email, userTypeId);
		UserRequest req = new UserRequest();
		if(user != null){
			req.setId(user.getUserId());
			req.setEmail(user.getEmail());
			req.setPassword(user.getPassword());
			req.setUserType(user.getUserTypeMaster().getId());
		}
		return req;
	}

}
