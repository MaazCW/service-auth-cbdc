package com.opl.cbdc.common.api.auth.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.AuthRequest;
import com.opl.cbdc.utils.common.OPLUtils;

public class AuthCredentialUtils {

	private static final Logger logger = LoggerFactory.getLogger(AuthCredentialUtils.class);

	public static final String GRANT_TYPE = "password";
	public static final String CLIENT_ID = "cw";
	public static final String CLIENT_SECRET = "cw";
	public static final String MOBILE_CLIENT_ID = "cwmobile";
	public static final String MOBILE_CLIENT_SECRET = "cwmobile";
//	public static final String OAUTH_SERVER_URI = "http://192.168.1.112:8052/capitaworld";
	public static final String OAUTH_TOKEN_URI = "/oauth/token";
	public static final String OAUTH_CHECK_TOKEN_URI = "/oauth/check_token";

	public static final String OAUTH_PARAM_USERNAME = "username";
	public static final String OAUTH_PARAM_PASSWORD = "password";
	public static final String OAUTH_PARAM_GRANTTYPE = "grant_type";
	public static final String OAUTH_PARAM_REFRESHTOKEN = "refresh_token";
	public static final String OAUTH_PARAM_TOKEN = "token";

	public static final String REQUEST_HEADER_ACCESS_TOKEN = "tk_ac";
	public static final String REQUEST_HEADER_USERNAME = "ur_cu";
	public static final String REQUEST_HEADER_REFRESH_TOKEN = "tk_rc";
	public static final String REQUEST_HEADER_LOGIN_TOKEN = "tk_lg";
	public static final String REQUEST_HEADER_AUTHENTICATE = "req_auth";
	public static final String REQUEST_HEADER_IS_DECRYPT = "is_decrypt";
	public static final String REQUEST_HEADER_IS_DECRYPT_VALUE = "true";
	public static final String REQUEST_HEADER_AUTHENTICATE_VALUE = "true";
	public static final String MOBILE_REQUEST = "appreq";
	public static final String TOKEN_REQ_KEY = "AHDU32422KNDKWE90987";
	public static final String LOGIN_KEY = "5b2d175a8e627284fda9f6d5ff9d732c";
	public final static String SESSION_OBJ_KEY = "user";

	public static final int OTP_REQUEST_LOGIN_TYPE = 2;
	
	private static final String EMAIL_TYPE = "EMAIL";
	private static final String MOBILE_TYPE = "MOBILE";
	private static final String USER_NAME_PATT = ":::::";
	public static final String USER_NAME = "userName";
	public static final String IS_EMAIL = "isEmail";
	
	public static final String IS_EMAIL_TRUE_VALUE = "true";
	public static final String IS_EMAIL_FALSE_VALUE = "false";
	
	public static final String TEMP_PP = "ASDGKNDKWE90987";	
	
	public static String setUserName(String emailOrMobile, boolean isEmail) {
		return emailOrMobile + USER_NAME_PATT + (isEmail ? EMAIL_TYPE : MOBILE_TYPE);
	}
	
	public static Map<String, String> getUserName(String userName) {
		String[] split = userName.split(USER_NAME_PATT);
		Map<String, String> map = new HashMap<>();
		map.put(USER_NAME, split[0].toString());
		if(EMAIL_TYPE.equals(split[1].toString())) {
			map.put(IS_EMAIL, IS_EMAIL_TRUE_VALUE);	
		} else {
			map.put(IS_EMAIL, IS_EMAIL_FALSE_VALUE);
		}
		return map;
	}

	public static String generateRandomToken() {
		return UUID.randomUUID().toString();
	}

	public final class AppRequestHeader {
		private AppRequestHeader() {
			// Nothing to do for X and Y
		}

		public static final String MOBILE_NO = "mobile";
		public static final String MODEL_NO = "model";
		public static final String MOBILE_OS = "os";
		public static final String OS_VERSION = "osvrsn";
		public static final String IMEI_NO = "imei";
		public static final String REQUEST_NO = "req";
		public static final String AUTH_TOKEN = "tk";
	}
	
	public static void setClient(HttpHeaders headers) {
		headers.set(REQUEST_HEADER_AUTHENTICATE, REQUEST_HEADER_AUTHENTICATE_VALUE);
	}
	
	public static boolean isClient(HttpServletRequest request) {
		String reqAuth = request.getHeader(REQUEST_HEADER_AUTHENTICATE);
		if(reqAuth != null && !reqAuth.equals("")  && REQUEST_HEADER_AUTHENTICATE_VALUE.equals(reqAuth)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public static boolean isDecryptClient(HttpServletRequest request) {
		String reqAuth = request.getHeader(REQUEST_HEADER_IS_DECRYPT);
		if(reqAuth != null && !reqAuth.equals("") && REQUEST_HEADER_IS_DECRYPT_VALUE.equals(reqAuth)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * THIS METHOD IS CHECK TOKEN IS NOT NULL AND SET ALL TOKENS IN AUTH REQUEST
	 * MODEL CLASS THIS IS METHOD IS USE IN AUTHENTICATION INTECEPTOR CLASS FOR
	 * AUTHENTICATE TOKEN AND REQUEST
	 * 
	 * @param HttpServletRequest
	 * @return AuthRequest
	 */
	public static AuthRequest httpResToAuthReq(HttpServletRequest request) {
		if (request == null) {
			return null;
		}

		try {
			String accessToken = request.getHeader(REQUEST_HEADER_ACCESS_TOKEN);
			String refreshToken = request.getHeader(REQUEST_HEADER_REFRESH_TOKEN);
			String username = request.getHeader(REQUEST_HEADER_USERNAME);
			String loginToken = request.getHeader(REQUEST_HEADER_LOGIN_TOKEN);

			if (OPLUtils.isObjectNullOrEmpty(accessToken) || OPLUtils.isObjectNullOrEmpty(username)
					|| OPLUtils.isObjectNullOrEmpty(refreshToken) || OPLUtils.isObjectNullOrEmpty(loginToken)) {
				return null;
			}
			AuthRequest authRequest = new AuthRequest(username, accessToken, refreshToken);
			authRequest.setLoginToken(Integer.valueOf(loginToken));
			return authRequest;
		} catch (Exception e) {
			logger.error("Exception while Convert HttpResponse To AuthRequest -> ", e);
			return null;
		}
	}
	
	/**
	 * Get userId from Auth object for customer and normal user
	 * @param clientResponse
	 * @return
	 */
	public static Long getAuthUserId(AuthClientResponse clientResponse) {
		if (!OPLUtils.isObjectNullOrEmpty(clientResponse)) {
			if (!OPLUtils.isObjectNullOrEmpty(clientResponse.getClientUserId())) {
				return clientResponse.getClientUserId();
			} else {
				return clientResponse.getUserId();
			}
		}
		return null;
	}

}
