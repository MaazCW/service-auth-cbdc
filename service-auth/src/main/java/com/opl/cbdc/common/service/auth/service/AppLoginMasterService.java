package com.opl.cbdc.common.service.auth.service;

import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.MobileAppLoginRequest;
import com.opl.cbdc.common.api.auth.model.MobilelAppLoginResponse;


public interface AppLoginMasterService {

	public MobilelAppLoginResponse login (MobileAppLoginRequest request);
	
	public AuthClientResponse checkToken(MobileAppLoginRequest request);
	
	public boolean logout(String token);

	public void addRequest(MobileAppLoginRequest request);
	
	public MobileAppLoginRequest checkMobileNumberActiveForLogin(String mobileNumber, Long userTypeId);
	
}
