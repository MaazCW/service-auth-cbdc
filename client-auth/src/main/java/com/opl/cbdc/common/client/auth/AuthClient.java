package com.opl.cbdc.common.client.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.opl.cbdc.common.api.auth.exception.AuthException;
import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.AuthRequest;
import com.opl.cbdc.common.api.auth.model.AuthResponse;
import com.opl.cbdc.common.api.auth.model.AuthCommonRes;
import com.opl.cbdc.common.api.auth.model.MobileAppLoginRequest;
import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
import com.opl.cbdc.common.api.auth.utils.EncodeDecodeHelper;

public class AuthClient {

	private static Logger logger = LoggerFactory.getLogger(AuthClient.class);

	private String oauthBaseUrl;

	private RestTemplate restTemplate;

	private static final String REFRESH_TOKEN = "/getRefreshToken";
	private static final String ACCESS_TOKEN = "/getAccessToken";
	private static final String LOGOUT_USER = "/logoutUser";
	private static final String CHECK_ACCESS_TOKEN = "/checkAccessToken";
	private static final String GET_TOKENS = "/getTokenByLoginToken";
	private static final String GET_TOKENS_SIDBI = "/getTokenByLoginTokenForSidbi";
	private static final String GET_TOKENS_BY_LOGIN_TOKEN = "/getByLoginToken";
	private static final String GET_LAST_LOGIN_DETAILS = "/getLastLoginDetailsFromUserId";
	private static final String APP_TOKEN = "/app/tokenAuthentication";
	private static final String APP_LOGIN = "/app/login";
	private static final String APP_LOGOUT = "/app/logout";
	private static final String APP_ADD_REQUEST = "/app/addRequest";
	private static final String CHECK_AUTH_STARTED = "/checkAuthStarted";
	private static final String UPDATE_CUSTOMER_IN_TOKEN_MAPPING = "/updateCustomerInTokenMapping";
	private static final String GET_TOKEN_FOR_PARTNER = "/getTokenForBankerAsPartner";
	public AuthClient() {

	}

	public AuthClient(String oauthBaseUrl) {
		this.oauthBaseUrl = oauthBaseUrl;
		restTemplate = new RestTemplate();
	}
	
	/**
	 * FOR GET REFRESH TOKEN BY USERNAME AND PASSWORD
	 * 
	 * @param authRequest
	 * @return
	 * @throws AuthException
	 */
	public AuthResponse getRefreshToken(AuthRequest authRequest) {
		try {
			// set client and secret
			String url = oauthBaseUrl.concat(REFRESH_TOKEN);
			authRequest = setClientAndSecret(authRequest);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);
			AuthResponse response = restTemplate.exchange(url, HttpMethod.POST,
					new HttpEntity<AuthRequest>(authRequest, headers), AuthResponse.class).getBody();
			if (response != null) {
				response.setAccess_token(EncodeDecodeHelper.encode(response.getAccess_token()));
				response.setRefresh_token(EncodeDecodeHelper.encode(response.getRefresh_token()));
			}
			return response;	
		} catch (Exception e) {
			logger.error("Error while call auth module for get refresh token------------->", e);
		}
		return null;
	}

	public AuthResponse getTokenByLoginToken(AuthRequest authRequest) {
		String url = oauthBaseUrl.concat(GET_TOKENS);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);
			AuthResponse response = restTemplate.exchange(url, HttpMethod.POST,
					new HttpEntity<AuthRequest>(authRequest, headers), AuthResponse.class).getBody();
			/*AuthResponse response = restTemplate.postForObject(url, authRequest, AuthResponse.class);*/
			if (response != null) {
				response.setAccess_token(EncodeDecodeHelper.encode(response.getAccess_token()));
				response.setRefresh_token(EncodeDecodeHelper.encode(response.getRefresh_token()));
			}
			return response;
		} catch (Exception e) {
			logger.error("Throw Exception while Get Token By Login Token For Domain Login", e);
			return null;
		}
	}
	
	public AuthResponse getTokenByLoginTokenForSidbi(AuthRequest authRequest) {
		String url = oauthBaseUrl.concat(GET_TOKENS_SIDBI);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);
			AuthResponse response = restTemplate.exchange(url, HttpMethod.POST,
					new HttpEntity<AuthRequest>(authRequest, headers), AuthResponse.class).getBody();
			/*AuthResponse response = restTemplate.postForObject(url, authRequest, AuthResponse.class);*/
			if (response != null) {
				response.setAccess_token(EncodeDecodeHelper.encode(response.getAccess_token()));
				response.setRefresh_token(EncodeDecodeHelper.encode(response.getRefresh_token()));
			}
			return response;
		} catch (Exception e) {
			logger.error("Throw Exception while Get Token By Login Token For SIDBI LOGIN", e);
			return null;
		}
	}
	
	public AuthResponse getByLoginToken(AuthRequest authRequest) {
		String url = oauthBaseUrl.concat(GET_TOKENS_BY_LOGIN_TOKEN);
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);
			AuthResponse response = restTemplate.exchange(url, HttpMethod.POST,
					new HttpEntity<AuthRequest>(authRequest, headers), AuthResponse.class).getBody();
			/*AuthResponse response = restTemplate.postForObject(url, authRequest, AuthResponse.class);*/
			if (response != null) {
				response.setAccess_token(EncodeDecodeHelper.encode(response.getAccess_token()));
				response.setRefresh_token(EncodeDecodeHelper.encode(response.getRefresh_token()));
			}
			return response;
		} catch (Exception e) {
			logger.error("Throw Exception while Get Token By Login Token", e);
			return null;
		}
	}

	public AuthRequest setClientAndSecret(AuthRequest req) {
		if(req.getIsMobileLogin() != null && req.getIsMobileLogin()) {
			// req.setClientId(AuthCredentialUtils.MOBILE_CLIENT_ID);
			// req.setClientSecret(AuthCredentialUtils.MOBILE_CLIENT_SECRET);
			req.setClientId(AuthCredentialUtils.CLIENT_ID);
			req.setClientSecret(AuthCredentialUtils.CLIENT_SECRET);
		} else {
			req.setIsMobileLogin(Boolean.FALSE);
			req.setClientId(AuthCredentialUtils.CLIENT_ID);
			req.setClientSecret(AuthCredentialUtils.CLIENT_SECRET);	
		}
		
		req.setGrant_type(AuthCredentialUtils.GRANT_TYPE);
		return req;
	}

	public AuthRequest decode(AuthRequest req) {
		//logger.info("auth request -->"+req);
		req.setRefreshToken(EncodeDecodeHelper.decode(req.getRefreshToken()));
		req.setUsername(EncodeDecodeHelper.decode(req.getUsername()));
		req.setAccessToken(EncodeDecodeHelper.decode(req.getAccessToken()));
		return req;
	}

	/**
	 * GET ACCESS TOKEN BY REFRESH TOKEN BY CLIENT ID AND SECRET
	 * 
	 * @param authRequest
	 * @return
	 */
	public AuthResponse getAccessToken(AuthRequest authRequest) {
		try {
			String url = oauthBaseUrl.concat(ACCESS_TOKEN);
			authRequest = setClientAndSecret(authRequest);
			// decode username and refreshtoken
			authRequest = decode(authRequest);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			AuthResponse response = restTemplate.exchange(url, HttpMethod.POST,
					new HttpEntity<AuthRequest>(authRequest, headers), AuthResponse.class).getBody();
			
			/*AuthResponse response = restTemplate.postForObject(url, authRequest, AuthResponse.class);*/
			if (response != null) {
				response.setAccess_token(EncodeDecodeHelper.encode(response.getAccess_token()));
				response.setRefresh_token(EncodeDecodeHelper.encode(response.getRefresh_token()));
			}
			return response;	
		} catch (Exception e) {
			logger.error("Error while call auth module for get access token------------->", e);
		}
		return null;
		
	}

	/**
	 * CHECK ACCSS TOKEN VALID OR NOT
	 * 
	 * @param req
	 * @return
	 */
	public AuthClientResponse isAccessTokenValidOrNot(AuthRequest req) {

		try {
			String url = oauthBaseUrl.concat(CHECK_ACCESS_TOKEN);
			//logger.info("Auth Rest URL For Authenticate Token =============>" + url);
			req = decode(req);
			req = setClientAndSecret(req);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			HttpEntity<AuthRequest> entity = new HttpEntity<>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, AuthClientResponse.class).getBody();	
		} catch (Exception e) {
			logger.error("Error while call auth module for access token valid or not------------->", e);
		}
		return null;
		
	}

	public AuthClientResponse logoutUser(AuthRequest req) {
		try {
			String url = oauthBaseUrl.concat(LOGOUT_USER);
			req = decode(req);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			HttpEntity<AuthRequest> entity = new HttpEntity<>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, AuthClientResponse.class).getBody();
			/*return restTemplate.postForObject(url, req, AuthClientResponse.class);	*/
		} catch (Exception e) {
			logger.error("Error while call auth module for logout service------------->", e);
		}
		return null;
	}

	public AuthCommonRes getLastLoginDetailsByUserId(AuthRequest req) {
		try {
			String url = oauthBaseUrl.concat(GET_LAST_LOGIN_DETAILS);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			HttpEntity<AuthRequest> entity = new HttpEntity<>(req, headers);
			return restTemplate.exchange(url, HttpMethod.POST, entity, AuthCommonRes.class).getBody();
			/*return restTemplate.postForObject(url, req, CommonResponse.class);	*/
		} catch (Exception e) {
			logger.error("Error while call auth module for logout service------------->", e);
		}
		return null;
	}

	public AuthClientResponse appToken(MobileAppLoginRequest request) {
		try {
			String url = oauthBaseUrl.concat(APP_TOKEN);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<MobileAppLoginRequest>(request, headers), AuthClientResponse.class).getBody();
			/*return restTemplate.postForObject(url, request, AuthClientResponse.class);*/	
		} catch (Exception e) {
			logger.error("Error while call auth module for app token------------->", e);
		}
		return null;
	}

	public AuthCommonRes appLogin(MobileAppLoginRequest request) {
		try {
			String url = oauthBaseUrl.concat(APP_LOGIN);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<MobileAppLoginRequest>(request, headers), AuthCommonRes.class).getBody();
			/*return restTemplate.postForObject(url, request, AuthClientResponse.class);*/	
		} catch (Exception e) {
			logger.error("Error while call auth module for app login------------->", e);
		}
		return null;
	}

	public AuthCommonRes appLogout(MobileAppLoginRequest request) {
		try {
			String url = oauthBaseUrl.concat(APP_LOGOUT);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<MobileAppLoginRequest>(request, headers), AuthCommonRes.class).getBody();
			/*return restTemplate.postForObject(url, request, AuthClientResponse.class);*/	
		} catch (Exception e) {
			logger.error("Error while call auth module for app logout------------->", e);
		}
		return null;
	}

	public AuthCommonRes appRequest(MobileAppLoginRequest request) {
		try {
			String url = oauthBaseUrl.concat(APP_ADD_REQUEST);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<MobileAppLoginRequest>(request, headers), AuthCommonRes.class).getBody();
			/*return restTemplate.postForObject(url, request, AuthClientResponse.class);*/	
		} catch (Exception e) {
			logger.error("Error while call auth module for app logout------------->", e);
		}
		return null;
	}
	
	public AuthCommonRes checkUserLoggined(AuthRequest request) {
		try {
			String url = oauthBaseUrl.concat(CHECK_ACCESS_TOKEN);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<AuthRequest>(request, headers), AuthCommonRes.class).getBody();
			/*return restTemplate.postForObject(url, request, CommonResponse.class);*/
		} catch(Exception e) {
			logger.error("Throw Exception while check user loggin or not", e);
			return null;
		}
	}
	
	public AuthResponse checkAuthStarted() {
		try {
			String url = oauthBaseUrl.concat(CHECK_AUTH_STARTED);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			return restTemplate.exchange(url, HttpMethod.GET, entity, AuthResponse.class).getBody();
		} catch(Exception e) {
			logger.error("Throw Exception while check user loggin or not", e);
			return null;
		}
	}

	public AuthCommonRes updateCustomerInTokenMapping(AuthRequest request) {
		try {
			String url = oauthBaseUrl.concat(UPDATE_CUSTOMER_IN_TOKEN_MAPPING);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			return restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<AuthRequest>(request, headers), AuthCommonRes.class).getBody();
		} catch(Exception e) {
			logger.error("Throw Exception while check user loggin or not", e);
			return null;
		}
	}

	/**
	 * FOR GET PARTENR TOKEN BY USERNAME AND PASSWORD
	 * 
	 * @param authRequest
	 * @return
	 * @throws AuthException
	 */
	public AuthResponse getTokenForBankerAsPartner(AuthRequest authRequest) {
		try {
			String url = oauthBaseUrl.concat(GET_TOKEN_FOR_PARTNER);
			authRequest = setClientAndSecret(authRequest);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Accept", "application/json");
			headers.setContentType(MediaType.APPLICATION_JSON);
			AuthResponse response = restTemplate.exchange(url, HttpMethod.POST,
					new HttpEntity<AuthRequest>(authRequest, headers), AuthResponse.class).getBody();
			if (response != null) {
				response.setAccess_token(EncodeDecodeHelper.encode(response.getAccess_token()));
				response.setRefresh_token(EncodeDecodeHelper.encode(response.getRefresh_token()));
			}
			return response;	
		} catch (Exception e) {
			logger.error("Error while call auth module for get refresh token------------->", e);
		}
		return null;
	}

}
