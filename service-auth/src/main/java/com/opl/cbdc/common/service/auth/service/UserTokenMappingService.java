package com.opl.cbdc.common.service.auth.service;

import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.AuthRequest;
import com.opl.cbdc.common.api.auth.model.AuthResponse;
import com.opl.cbdc.common.api.auth.model.CustomUserTokenMapping;
import com.opl.cbdc.common.api.auth.model.LogResponse;

public interface UserTokenMappingService {

	public AuthClientResponse checkAccessToken(AuthRequest req);

	public void updateAccessToken(CustomUserTokenMapping mapping);

	public AuthResponse createNewUserWithToken(CustomUserTokenMapping mapping, AuthResponse response);

	public boolean inActiveAllSessionByUserName(String userName);

	public void logoutuser(AuthRequest req);

	public boolean isUserAlreadyActive(String userName, String refreshToken);

	public AuthResponse getTokenByLoginToken(Integer loginToken);

	public AuthResponse getTokenByLoginTokenForSidbi(Integer loginToken);

	public AuthResponse getByLoginToken(Integer loginToken);

	public LogResponse getLastLoginDetailsFromUserId(Long userId);

	public boolean checkUserLogginedOrNot(Long userId);

	public AuthResponse getTokensByUserId(Long userId);

	public Integer updateCustomerInTokenMapping(AuthRequest authRequest);

	public boolean inActiveAllSessionByUserNameAndUserType(String userName, Long userType);

	public AuthResponse getRefreshToken(AuthRequest authRequest);

	public AuthResponse getAccessToken(AuthRequest authRequest);

	public AuthClientResponse validateOAuthAccessToken(AuthRequest authRequest);

	public AuthClientResponse logoutOAuthUser(AuthRequest authRequest);

	public AuthResponse getTokenForPartner(AuthRequest authRequest);

}
