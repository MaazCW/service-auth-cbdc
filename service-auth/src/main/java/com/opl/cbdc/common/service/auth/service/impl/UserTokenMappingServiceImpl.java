package com.opl.cbdc.common.service.auth.service.impl;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.AuthRequest;
import com.opl.cbdc.common.api.auth.model.AuthResponse;
import com.opl.cbdc.common.api.auth.model.CustomUserTokenMapping;
import com.opl.cbdc.common.api.auth.model.LogResponse;
import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
import com.opl.cbdc.common.api.auth.utils.EncodeDecodeHelper;
import com.opl.cbdc.common.service.auth.domain.User;
import com.opl.cbdc.common.service.auth.domain.UserMobileFcmMapping;
import com.opl.cbdc.common.service.auth.domain.UserTokenMapping;
import com.opl.cbdc.common.service.auth.repositories.UserMobileFcmMappingRepository;
import com.opl.cbdc.common.service.auth.repositories.UserRepository;
import com.opl.cbdc.common.service.auth.repositories.UserTokenMappingRepository;
import com.opl.cbdc.common.service.auth.service.UserTokenMappingService;
import com.opl.cbdc.utils.common.MultipleJSONObjectHelper;
import com.opl.cbdc.utils.common.OPLUtils;
import com.opl.cbdc.utils.config.UrlsAns;
import com.opl.cbdc.utils.config.UrlsAns.UrlType;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserTokenMappingServiceImpl implements UserTokenMappingService {

//	private static final Random random = new Random();
	private static final SecureRandom random = new SecureRandom();
	private static final Long MARKET_PLACE = 1L;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserTokenMappingRepository userTokenMappingRepository;
	
	@Autowired
	private UserMobileFcmMappingRepository userMobileFcmMappingRepo;

	@Autowired
	private TokenStore tokenStore;
	
	@Value("${com.opl.test.mode}")
	private String testMode;
	
	@Override
	public AuthClientResponse checkAccessToken(AuthRequest req) {
		List<UserTokenMapping> userTokenMapping = userTokenMappingRepository.checkAccessToken(req.getUsername(), req.getAccessToken(),req.getLoginToken());
		AuthClientResponse response = new AuthClientResponse();
		if(userTokenMapping.isEmpty() || userTokenMapping == null) {
			return response;
		} else {
			if(testMode != null && "ON".equals(testMode)) {
				response.setUserId(userTokenMapping.get(0).getUserId());
				response.setUserType(userTokenMapping.get(0).getUserType());
				response.setUserRoleId(userTokenMapping.get(0).getUserRoleId());
				response.setUserOrgId(userTokenMapping.get(0).getUserOrgId());
				response.setUserBranchId(userTokenMapping.get(0).getUserBranchId());
				return response;
			}
			User user = userRepository.findById(userTokenMapping.get(0).getUserId()).orElse(null);
			if(user != null) {
				response.setUserRoleId(user.getUserRoleId() != null ? user.getUserRoleId().getRoleId() : null);
				response.setLastBusinessTypeId(user.getLastBusinessTypeId());
				response.setEmail(user.getEmail());
				response.setUserName(user.getUsername());
				response.setMobile(user.getMobile());
				String name = !OPLUtils.isObjectNullOrEmpty(user.getFirstName()) ? user.getFirstName() : "";
				name = name + (!OPLUtils.isObjectNullOrEmpty(user.getMiddleName()) ? " " + user.getMiddleName() : "");
				name = name + (!OPLUtils.isObjectNullOrEmpty(user.getLastName()) ? " " + user.getLastName() : "");
				response.setName(!OPLUtils.isObjectNullOrEmpty(user.getLastName()) ? name : user.getUsername());
				response.setUserOrgId(user.getUserOrgId() != null ? user.getUserOrgId().getUserOrgId() : null);
				response.setUserBranchId(user.getBranchId() != null ? user.getBranchId().getId() : null);
			}
			response.setUserId(userTokenMapping.get(0).getUserId());
			response.setUserType(userTokenMapping.get(0).getUserType());
			response.setClientUserId(userTokenMapping.get(0).getClientUserId());
			return response;
		}
	}

	@Override
	public void updateAccessToken(CustomUserTokenMapping mapping) {
		userTokenMappingRepository.updateAccessToken(mapping.getUserName(),mapping.getRefreshToken(),mapping.getAccessToken(),mapping.getExpiresIn(),mapping.getLoginToken());
	}

	
	@Override
	public AuthResponse createNewUserWithToken(CustomUserTokenMapping mapping,AuthResponse response) {
		
		Map<String, String> map = AuthCredentialUtils.getUserName(mapping.getUserName());
		String userName = map.get(AuthCredentialUtils.USER_NAME);
		String isEmail = map.get(AuthCredentialUtils.IS_EMAIL);
		
		UserTokenMapping userTokenMapping = new UserTokenMapping();
		BeanUtils.copyProperties(mapping, userTokenMapping, "id","active");
		userTokenMapping.setUserName(userName);
		User user = null;
		if(AuthCredentialUtils.IS_EMAIL_TRUE_VALUE.equals(isEmail)) {
			user = userRepository.getByCaseSensitiveEmailAndUserTypeMasterId(userName, mapping.getUserTypeId());
		} else {
			user = userRepository.findOneByMobileAndUserTypeMasterId(userName, mapping.getUserTypeId());
		}
		userTokenMapping.setActive(true);
		userTokenMapping.setLoginDate(new Date());
		userTokenMapping.setUserIp(mapping.getUserIp());
		userTokenMapping.setUserBrowser(mapping.getUserBrowser());
		userTokenMapping.setDomainIsactive(mapping.isDomainLogin());
		Integer randomNumber = getRandomNumber();
		userTokenMapping.setLoginToken(randomNumber);
		if(user != null){
			userTokenMapping.setUserId(user.getUserId());
			userTokenMapping.setUserType(user.getUserTypeMaster().getId());
			if(!OPLUtils.isObjectNullOrEmpty(user.getUserOrgId())){
				response.setUserOrgId(user.getUserOrgId().getUserOrgId());
				userTokenMapping.setUserOrgId(user.getUserOrgId().getUserOrgId());
			}
			if(!OPLUtils.isObjectNullOrEmpty(user.getBranchId())){
				response.setUserBranchId(user.getBranchId().getId());
				userTokenMapping.setUserBranchId(user.getBranchId().getId());
			}
			if(!OPLUtils.isObjectNullOrEmpty(user.getUserRoleId())){
				response.setUserRoleId(user.getUserRoleId().getRoleId());
				userTokenMapping.setUserRoleId(user.getUserRoleId().getRoleId());
			}
			response.setUserType(user.getUserTypeMaster().getId());
			response.setUserId(user.getUserId());
		}
		userTokenMapping.setMobileOs(mapping.getMobileOs());
		userTokenMapping.setImeiNo(mapping.getImeiNo());
		userTokenMapping.setModelNo(mapping.getModelNo());
		userTokenMapping.setOsVersion(mapping.getOsVersion());
		userTokenMapping.setAppVersion(mapping.getAppVersion());
		userTokenMapping.setBrowserVersion(mapping.getBrowserVersion());
		userTokenMapping.setDevice(mapping.getDevice());
		userTokenMapping.setDeviceType(mapping.getDeviceType());
		userTokenMapping.setDeviceOs(mapping.getDeviceOs());
		userTokenMapping.setDeviceOsVersion(mapping.getDeviceOsVersion());
		userTokenMapping.setUserAgent(mapping.getUserAgent());
		userTokenMapping.setCampaignCode(mapping.getCampaignCode());
		userTokenMapping.setCampaignMasterId(mapping.getCampaignMasterId());
		userTokenMapping.setIsMobileLogin(mapping.isMobileLogin());
		userTokenMapping.setMobileFcmToken(mapping.getFcmToken());
		userTokenMappingRepository.save(userTokenMapping);
		/**
		 * UPDATE FCM TOKEN FOR MOBILE LOGIN
		 */
		if(mapping.isMobileLogin() && user != null && mapping.getFcmToken() != null) {
			userMobileFcmMappingRepo.inActiveByToken(mapping.getFcmToken());
			UserMobileFcmMapping usermobileFcmMap = new UserMobileFcmMapping();
			usermobileFcmMap.setUserId(user.getUserId());
			usermobileFcmMap.setToken(mapping.getFcmToken());
			usermobileFcmMap.setActive(true);
			usermobileFcmMap.setCreatedDate(new Date());
			userMobileFcmMappingRepo.save(usermobileFcmMap);
		}
		response.setLoginToken(randomNumber);
		response.setUserName(userName);
		return response;
	}
	
	@Override
	public AuthResponse getTokenByLoginToken(Integer loginToken){
		UserTokenMapping userTokenMapping = userTokenMappingRepository.getTokenByLoginToken(loginToken);
		AuthResponse response = new AuthResponse();
		if(userTokenMapping != null){
			userTokenMappingRepository.inactiveDomainLogin(loginToken);
			response.setAccess_token(userTokenMapping.getAccessToken());
			response.setLoginToken(userTokenMapping.getLoginToken());
			response.setRefresh_token(userTokenMapping.getRefreshToken());
			response.setExpires_in(String.valueOf(userTokenMapping.getExpiresIn()));
			response.setUserType(userTokenMapping.getUserType());
			response.setEmail(userTokenMapping.getUserName());
			response.setUserOrgId(userTokenMapping.getUserOrgId());
			response.setUserRoleId(userTokenMapping.getUserRoleId());
			response.setUserBranchId(userTokenMapping.getUserBranchId());
		}
		return response;
	}
	
	@Override
	public AuthResponse getTokenByLoginTokenForSidbi(Integer loginToken){
		UserTokenMapping userTokenMapping = userTokenMappingRepository.getTokenByLoginTokenForSidbi(loginToken);
		if(userTokenMapping != null){
			AuthResponse response = new AuthResponse();
			response.setAccess_token(userTokenMapping.getAccessToken());
			response.setLoginToken(userTokenMapping.getLoginToken());
			response.setRefresh_token(userTokenMapping.getRefreshToken());
			response.setExpires_in(String.valueOf(userTokenMapping.getExpiresIn()));
			response.setUserType(userTokenMapping.getUserType());
			response.setEmail(userTokenMapping.getUserName());
			response.setUserOrgId(userTokenMapping.getUserOrgId());
			response.setUserRoleId(userTokenMapping.getUserRoleId());
			response.setUserBranchId(userTokenMapping.getUserBranchId());
			return response;
		}
		return null;
	}
	
	@Override
	public AuthResponse getByLoginToken(Integer loginToken){
		UserTokenMapping userTokenMapping = userTokenMappingRepository.getByLoginToken(loginToken);
		AuthResponse response = new AuthResponse();
		if(userTokenMapping != null){
			response.setAccess_token(userTokenMapping.getAccessToken());
			response.setLoginToken(userTokenMapping.getLoginToken());
			response.setRefresh_token(userTokenMapping.getRefreshToken());
			response.setExpires_in(String.valueOf(userTokenMapping.getExpiresIn()));
			response.setUserType(userTokenMapping.getUserType());
			response.setEmail(userTokenMapping.getUserName());
			response.setUserOrgId(userTokenMapping.getUserOrgId());
			response.setUserRoleId(userTokenMapping.getUserRoleId());
			response.setUserBranchId(userTokenMapping.getUserBranchId());
		}
		return response;
	}
	
	
	@Override
	public void logoutuser(AuthRequest req){
		UserTokenMapping tokenMapping = userTokenMappingRepository.findByUserNameAndRefreshTokenAndLoginTokenAndActive(req.getUsername(),req.getRefreshToken(),req.getLoginToken(), true);
		if(tokenMapping != null) {
			tokenMapping.setActive(Boolean.FALSE);
			tokenMapping.setLogoutDate(new Date());
			userTokenMappingRepository.save(tokenMapping);
			/**
			 * INACTIVE PREVIOUS TOKEN FOR MOBILE LOGIN
			 */
			if(tokenMapping.getIsMobileLogin() != null && tokenMapping.getIsMobileLogin() && tokenMapping.getMobileFcmToken() != null) {
				userMobileFcmMappingRepo.inActiveByToken(tokenMapping.getMobileFcmToken());				
			}
		} else {
			log.info("While Logout Not Found Object From UserName -> " + req.getUsername() + " --- RefreshToken --> "+ req.getRefreshToken() + "----- LoginToken -----> " + req.getLoginToken());
		}
		//userTokenMappingRepository.logoutUser(req.getUsername(),req.getRefreshToken(),req.getLoginToken());
	}
	
	@Override
	public boolean isUserAlreadyActive(String userName,String refreshToken){
		Long count = userTokenMappingRepository.isUserAlreadyActive(userName, refreshToken);
		return count > 0;
	}

	private Integer getRandomNumber() {
		Integer randomNumber = 1000000 + random.nextInt(9000000);
		Long count = userTokenMappingRepository.checkLoginToken(randomNumber);
		if(count > 0) {
			getRandomNumber();
		}
		return 1000000 + random.nextInt(9000000);
	}
	
	@Override
	public LogResponse getLastLoginDetailsFromUserId(Long userId){
		LogResponse response = new LogResponse();
		UserTokenMapping lastLoginDetails = userTokenMappingRepository.getLastLoginDetailsFromUserId(userId);
		if(lastLoginDetails != null){
			response.setUserEmail(lastLoginDetails.getUserName());
			response.setLoginDate(lastLoginDetails.getLoginDate());
			response.setUserId(lastLoginDetails.getUserId());
		}
		return response;
	}
	
	@Override
	public AuthResponse getTokensByUserId(Long userId){
		AuthResponse response = null;
		UserTokenMapping lastLoginDetails = userTokenMappingRepository.findFirstByUserIdAndActiveOrderByIdDesc(userId, true);
		if(lastLoginDetails != null){
			response = new AuthResponse();
			response.setEmail(EncodeDecodeHelper.encode(lastLoginDetails.getUserName()));
			response.setRefresh_token(EncodeDecodeHelper.encode(lastLoginDetails.getRefreshToken()));
			response.setAccess_token(EncodeDecodeHelper.encode(lastLoginDetails.getAccessToken()));
			response.setLoginToken(lastLoginDetails.getLoginToken());
			response.setUserId(lastLoginDetails.getUserId());
		}
		return response;
	}
	
	@Override
	public boolean checkUserLogginedOrNot(Long userId){
		Long id = userTokenMappingRepository.checkUserLogginedOrNot(userId);
		return id > 0;
	}

	@Override
	public boolean inActiveAllSessionByUserName(String userName) {
		userTokenMappingRepository.inActiveAllSessionByUserName(AuthCredentialUtils.getUserName(userName).get(AuthCredentialUtils.USER_NAME));
		return false;
	}

	@Override
	public Integer updateCustomerInTokenMapping(AuthRequest req) {
		return userTokenMappingRepository.updateCustomerId(req.getClientUserId(), req.getUsername(), req.getAccessToken(),req.getLoginToken());
	}
	
	
	@Override
	public boolean inActiveAllSessionByUserNameAndUserType(String userName,Long userType) {
		userTokenMappingRepository.inActiveAllSessionByUserName(AuthCredentialUtils.getUserName(userName).get(AuthCredentialUtils.USER_NAME),userType);
		return false;
	}

	/**
	 * GENERATE ACCESS TOKEN USING USER CREDENTIALS
	 */
	@Override
	public AuthResponse getRefreshToken(AuthRequest authRequest) {
		log.info("Enter in Get RefreshToken When User Try To Login===================>" + authRequest.getUsername());
		try {
			RestTemplate restTemplate = new RestTemplate();

			// O-Auth URL
			String oauthToken = UrlsAns.getLocalIpAddress(UrlType.AUTH).concat(AuthCredentialUtils.OAUTH_TOKEN_URI);

			// CALL O-AUTH USING RESTTEMPLATE
			HttpHeaders headers = createHeader(authRequest.getClientId(), authRequest.getClientSecret());
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add(AuthCredentialUtils.OAUTH_PARAM_USERNAME, authRequest.getUsername() + "$" + authRequest.getUserTypeId());
			params.add(AuthCredentialUtils.OAUTH_PARAM_PASSWORD, authRequest.getPassword());
			params.add(AuthCredentialUtils.OAUTH_PARAM_GRANTTYPE, authRequest.getGrant_type());
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
			ResponseEntity<String> responseInString = restTemplate.postForEntity(oauthToken, requestEntity, String.class);
			log.info("ACCESS TOKEN GENERATED SUCCEESSFULLY FROM CREDENTIALS.");

			// BINDING IN CUSTOM OBJECT AuthResponse CLASS
			if (!OPLUtils.isObjectNullOrEmpty(responseInString) && !OPLUtils.isObjectNullOrEmpty(responseInString.getBody())) {
				String oauthResponse = responseInString.getBody();
				AuthResponse response = responseAdapter(oauthResponse);
				// create new user with refresh token and access token in
				// UserTokenMapping Table
				inActiveAllSessionByUserName(authRequest.getUsername());
				response = createNewUserWithToken(bindResponseInCustomObject(response, authRequest), response);
				log.info("Successfully Generate Refreshtoken-----------------" + response.getUserName());
				return response;
			}
		} catch (HttpStatusCodeException e) {
			log.info("HttpStatusCodeException ------------------> {}", e);

		} catch (Exception e) {
			log.info("Get RefreshToken, UserName ------------------> " + authRequest.getUsername());
			log.error("Throw Exception While Generate Refresh Token When Login User ---------> {}", e);
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public AuthResponse getAccessToken(AuthRequest authRequest) {
		log.info("Enter in Get getAccessToken() From Refresh Token.");
		String checkOauthTokenUrl = UrlsAns.getLocalIpAddress(UrlType.AUTH).concat(AuthCredentialUtils.OAUTH_TOKEN_URI);
		RestTemplate restTemplate = new RestTemplate();
		try {

			HttpHeaders headers = createHeader(authRequest.getClientId(), authRequest.getClientSecret());
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add(AuthCredentialUtils.OAUTH_PARAM_REFRESHTOKEN, authRequest.getRefreshToken());
			params.add(AuthCredentialUtils.OAUTH_PARAM_GRANTTYPE, "refresh_token");
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
			ResponseEntity<String> responseInString = restTemplate.postForEntity(checkOauthTokenUrl, requestEntity, String.class);
			log.info("GET ACCESS TOKEN FROM REFRESH TOKEN SUCCESSFUL.");

			AuthResponse response = responseAdapter(responseInString.getBody());
			// if successfully create access token then update new access token
			// in UserTokenMappin Table
			updateAccessToken(bindResponseInCustomObject(response, authRequest));
			log.info("Successfully Generate Accesstoken---" + authRequest.getUsername());
			return response;
		} catch (Exception e) {
			log.info("Method :- GetAccessToken, AccessToken----->{} ---UserName --> {} -->RefershToken-->{} -- Login Token -->{}", authRequest.getAccessToken(),
					authRequest.getUsername(), authRequest.getRefreshToken(), authRequest.getLoginToken());
			log.error("Throw Exception While Generate New Access Token : ", e);

			return null;
		}
	}

	@Override
	public AuthClientResponse validateOAuthAccessToken(AuthRequest req) {
		// if oauth check token is valid then we check our custom user token
		// mapping table using username and accesstoken

		boolean isValid = false;
		if (testMode != null && "ON".equals(testMode)) {
			isValid = true;
		} else {
			isValid = validateAccessTokenWithOauth(req);
		}

		AuthClientResponse response = new AuthClientResponse();
		if (isValid) {
			response = checkAccessToken(req);
			if (response.getUserId() != null) {
				response.setAuthenticate(true);
			} else {
				log.info("Method :- GetAccessToken, AccessToken----->{} ---UserName -->{} -->RefershToken-->{} -- Login Token -->{}", req.getAccessToken(),
						req.getUsername(), req.getRefreshToken(), req.getLoginToken());
				log.warn("Check AccessToken on Every Request, Invalid Accesstoken or Refreshtoken or Logintoken");
				response.setAuthenticate(false);
			}
			return response;
		}
		log.info("Method :- GetAccessToken, AccessToken----->{} ---UserName -->{}-->RefershToken-->{}-- Login Token -->{}", req.getAccessToken(), req.getUsername(),
				req.getRefreshToken(), req.getLoginToken());
		log.warn("Check AccessToken on Every Request, AccessToken is Expire or Invalid");
		response.setAuthenticate(false);

		return response;
	}

	@Override
	public AuthClientResponse logoutOAuthUser(AuthRequest request) {
    	log.info("Enter in Logout User------------------>");
		AuthClientResponse response = new AuthClientResponse();
		response.setAuthenticate(false);
		try {
			if (StringUtils.hasText(request.getAccessToken()) && StringUtils.hasText(request.getUsername())) {
				if (isUserAlreadyActive(request.getUsername(), request.getRefreshToken())) {
					OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(request.getAccessToken());
					if (oAuth2AccessToken != null) {
						tokenStore.removeAccessToken(oAuth2AccessToken);
					}
				}

				logoutuser(request);

				log.info("Successfully logoutUser----" + request.getUsername() + "---LoginToken----" + request.getLoginToken());
				response.setAuthenticate(true);
			} else {
				log.info("Method :- GetAccessToken, AccessToken----->" + request.getAccessToken() + "---UserName -->" + request.getUsername() + "-->RefershToken-->"
						+ request.getRefreshToken() + "-- Login Token -->" + request.getLoginToken());
				log.warn("Logout User, Token and username not valid");
				response.setMessage("Token and username is not valid");
			}
			return response;
		} catch (Exception e) {
			log.info("Method :- GetAccessToken, AccessToken----->" + request.getAccessToken() + "---UserName -->" + request.getUsername() + "-->RefershToken-->"
					+ request.getRefreshToken() + "-- Login Token -->" + request.getLoginToken());
			log.error("Throw exception while logout user : ", e);
			return response;
		}
	}

	@Override
	public AuthResponse getTokenForPartner(AuthRequest authRequest) {
		String oauthToken = UrlsAns.getLocalIpAddress(UrlType.AUTH).concat(AuthCredentialUtils.OAUTH_TOKEN_URI);

		RestTemplate restTemplate = new RestTemplate();
		try {
			// CALL O-AUTH USING RESTTEMPLATE
			HttpHeaders headers = createHeader(authRequest.getClientId(), authRequest.getClientSecret());
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add(AuthCredentialUtils.OAUTH_PARAM_USERNAME, authRequest.getUsername() + "$" + authRequest.getUserTypeId());
			params.add(AuthCredentialUtils.OAUTH_PARAM_PASSWORD, authRequest.getPassword());
			params.add(AuthCredentialUtils.OAUTH_PARAM_GRANTTYPE, authRequest.getGrant_type());
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
			ResponseEntity<String> responseInString = restTemplate.postForEntity(oauthToken, requestEntity, String.class);
			log.info("ACCESS TOKEN GENERATED SUCCEESSFULLY FROM CREDENTIALS FOR PARTNER.");

			// convert response to AuthResponse class
			if (!OPLUtils.isObjectNullOrEmpty(responseInString) && !OPLUtils.isObjectNullOrEmpty(responseInString.getBody())) {
				String oauthResponse = responseInString.getBody();
				AuthResponse response = responseAdapter(oauthResponse);
				// create new user with refresh token and access token in
				// UserTokenMapping Table
				inActiveAllSessionByUserNameAndUserType(authRequest.getUsername(), authRequest.getUserTypeId());
				response = createNewUserWithToken(bindResponseInCustomObject(response, authRequest), response);
				log.info("Successfully Generate PartnerToken-----------------" + response.getUserName());
				return response;
			}
		} catch (HttpStatusCodeException e) {
			log.info("HttpStatusCodeException ------------------> {}", e);

		} catch (Exception e) {
			log.info("Get PartnerToken, UserName ------------------> " + authRequest.getUsername());
			log.error("Throw Exception While Generate Partner Token  ---------> {}", e);
		}
		return null;
	}

	/**
	 * CALL OAUTH FOR VALIDATION ACCESS TOKEN
	 * @param req
	 * @return
	 */
	private boolean validateAccessTokenWithOauth(AuthRequest req) {
		try {
//			VALIDATE TOKEN IN OAUTH-2
			String url = UrlsAns.getLocalIpAddress(UrlType.AUTH).concat(AuthCredentialUtils.OAUTH_CHECK_TOKEN_URI);

			HttpHeaders headers = createHeader(req.getClientId(), req.getClientSecret());
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
			params.add(AuthCredentialUtils.OAUTH_PARAM_TOKEN, req.getAccessToken());
			
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.postForEntity(url, requestEntity, String.class);
			return Boolean.TRUE;
		} catch (Exception e) {
			log.info("Throw Exception while check access token");
			return false;
		}
	}

	/**
	 * CONVERT RESPONSE TO CUSTOM OBJECT
	 * 
	 * @param response
	 * @param req
	 * @return
	 */
	private CustomUserTokenMapping bindResponseInCustomObject(AuthResponse response, AuthRequest req) {
		CustomUserTokenMapping mapping = new CustomUserTokenMapping();
		mapping.setUserName(req.getUsername());
		mapping.setRefreshToken(response.getRefresh_token());
		mapping.setAccessToken(response.getAccess_token());
		mapping.setExpiresIn(response.getExpires_in());
		mapping.setLoginToken(req.getLoginToken());
		mapping.setUserIp(req.getUserIp());
		mapping.setUserBrowser(req.getUserBrowser());
		mapping.setDomainLogin(req.getIsDomainLogin());
		mapping.setMobileOs(req.getMobileOs());
		mapping.setAppVersion(req.getAppVersion());
		mapping.setImeiNo(req.getImeiNo());
		mapping.setModelNo(req.getModelNo());
		mapping.setOsVersion(req.getOsVersion());
		mapping.setUserTypeId(req.getUserTypeId());
		mapping.setBrowserVersion(req.getBrowserVersion());
		mapping.setDevice(req.getDevice());
		mapping.setDeviceType(req.getDeviceType());
		mapping.setDeviceOs(req.getDeviceOs());
		mapping.setDeviceOsVersion(req.getDeviceOsVersion());
		mapping.setUserAgent(req.getUserAgent());
		mapping.setCampaignCode(req.getCampaignCode());
		mapping.setCampaignMasterId(req.getCampaignMasterId() != null ? req.getCampaignMasterId() : MARKET_PLACE);
		mapping.setMobileLogin(req.getIsMobileLogin());
		mapping.setFcmToken(req.getFcmToken());
		return mapping;
	}

	/**
	 * CONVERT RESPONSE STRING TO AuthResponse OBJECT
	 * 
	 * @param response
	 * @return
	 */
	private AuthResponse responseAdapter(String response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(response, AuthResponse.class);
		} catch (Exception e) {
			log.info("Response String -----------------> " +response);
			if(response.contains("DefaultOAuth2AccessToken")) {
				return MultipleJSONObjectHelper.xmlStringToPojo(response, AuthResponse.class);
			}
			log.error("Throw exception when convert string response to object : ", e);
			return null;
		}
	}
	
	/**
	 * CREATE REQUEST HEADER FOR AUTHORIZATION
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	private HttpHeaders createHeader(String clientId, String clientSecret) {
		String authSecret = clientId + ":" + clientSecret;
		HttpHeaders headers = new HttpHeaders();
		headers.set("authorization", "Basic " + Base64Utils.encodeToString(authSecret.getBytes()));
		headers.set("cache-control", "no-cache");
		return headers;
	}
	
}
