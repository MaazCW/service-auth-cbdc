package com.opl.cbdc.common.service.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.AuthCommonRes;
import com.opl.cbdc.common.api.auth.model.AuthRequest;
import com.opl.cbdc.common.api.auth.model.AuthResponse;
import com.opl.cbdc.common.api.auth.model.LogResponse;
import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
import com.opl.cbdc.common.service.auth.service.UserTokenMappingService;
import com.opl.cbdc.utils.common.OPLUtils;

@RestController
public class UserTokenMappingController {

	private static Logger logger = LoggerFactory.getLogger(UserTokenMappingController.class);
	private static final String SUCCESSFULLY_GET_RESULT = "Successfully get result";
	private static final String SOMETHING_WENT_WRONG = "Something went wrong";
	
	@Value("${com.opl.test.mode}")
	private String testMode;

	@Autowired
	private UserTokenMappingService userTokenMappingService;

	/**
	 * GENERATE ACCESS TOKEN FROM USER CREDENTIALS IN OAUTH-2
	 * 
	 * @param authRequest
	 * @return
	 */
	@PostMapping(value = "/getRefreshToken", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getRefreshToken(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in Get RefreshToken When User Try To Login===================>{}", authRequest.getUsername());
		return userTokenMappingService.getRefreshToken(authRequest);
	}

	/**
	 * GENERATE NEW ACCESS TOKEN FROM REFREHTOKEN IN OAUTH-2
	 * 
	 * @param authRequest
	 * @return
	 */
	@PostMapping(value = "/getAccessToken", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getAccessToken(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in get accessToken from Refresh Token");
		return userTokenMappingService.getAccessToken(authRequest);
	}

	/**
	 * CHECK ACCESS TOKEN VALID OR NOT IN OAUTH-2
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/checkAccessToken", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthClientResponse> checkAccessToken(@RequestBody AuthRequest req) {
		AuthClientResponse authClientResponse = userTokenMappingService.validateOAuthAccessToken(req);
		return new ResponseEntity<>(authClientResponse, HttpStatus.OK);
	}

	/**
	 * LOGOUT IN OAUTH-2 AND CUSTOM TOKENS
	 * 
	 * @param AuthRequest
	 * @return
	 */
	@PostMapping(value = "/logoutUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthClientResponse logoutUser(@RequestBody AuthRequest request) {
		logger.info("Enter in logout user");
		return userTokenMappingService.logoutOAuthUser(request);
	}

	/**
	 * GENERATE TOKEN FOR BANKER AS PARTNER
	 * 
	 * @param authRequest
	 * @return
	 */
	@PostMapping(value = "/getTokenForBankerAsPartner", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getTokenForPartner(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in getTokenForBankerAsPartner When User Try To Login===================>{}", authRequest.getUsername());
		return userTokenMappingService.getTokenForPartner(authRequest);
	}
	
	@PostMapping(value = "/getTokenByLoginToken", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getTokenByLoginToken(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in tokens by login token for domain login method");
		try {
			AuthResponse response = userTokenMappingService.getTokenByLoginToken(authRequest.getLoginToken());
			if(response != null){
				logger.info("Successfully get tokens by login token for domain login");
				return response;
			}
			logger.warn("Token not found by login token when domain login");
			return response;
		} catch (Exception e) {
			logger.info("Get Token By LoginToken, Login Token ---------------> {}", authRequest.getLoginToken());
			logger.error("Throw Exception While Get TOkens By Login Token When Domain Login : ", e);
			return null;
		}

	}
	
	@PostMapping(value = "/getTokenByLoginTokenForSidbi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getTokenByLoginTokenForSidbi(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in tokens by login token for sidbi login method");
		try {
			AuthResponse response = userTokenMappingService.getTokenByLoginTokenForSidbi(authRequest.getLoginToken());
			if(response != null){
				logger.info("Successfully get tokens by login token for sidbi login");
				return response;
			}
			logger.warn("Token not found by login token when sidbi login");
			return null;
		} catch (Exception e) {
			logger.info("Get Token By LoginToken, Login Token ---------------> {}", authRequest.getLoginToken());
			logger.error("Throw Exception While Get TOkens By Login Token When sidbi login : ", e);
			return null;
		}

	}
	
	@PostMapping(value = "/getByLoginToken", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse getByLoginToken(@RequestBody AuthRequest authRequest) {
		logger.info("Enter in tokens by login token");
		try {
			AuthResponse response = userTokenMappingService.getByLoginToken(authRequest.getLoginToken());
			if(response != null){
				logger.info("Successfully get tokens by login token ----------------------------->{}", authRequest.getLoginToken());
				return response;
			}
			logger.warn("Token not found by login token ");
			return response;
		} catch (Exception e) {
			logger.info("Token By LoginToken, Login Token ---------------> {}", authRequest.getLoginToken());
			logger.error("Throw Exception While Get TOkens By Login Token ", e);
			return null;
		}

	}
	
	@PostMapping(value = "/getLastLoginDetailsFromUserId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthCommonRes> getLastLoginDetailsFromUserId(@RequestBody AuthRequest req) {
		logger.info("Enter in get last login details ");
		try {
			LogResponse logResponse = userTokenMappingService.getLastLoginDetailsFromUserId(req.getUserId());
			logger.info("Successfully get last login details");
			return new ResponseEntity<>(new AuthCommonRes(HttpStatus.OK.value(),SUCCESSFULLY_GET_RESULT,logResponse), HttpStatus.OK);
		} catch(Exception e){
			logger.error("Throw Exception while get log details : ", e);
			return new ResponseEntity<>(
					new AuthCommonRes(HttpStatus.INTERNAL_SERVER_ERROR.value(),SOMETHING_WENT_WRONG,null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/getTokensByUserId", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthCommonRes> getTokensByUserId(@RequestBody AuthRequest req) {
		logger.info("Enter in getTokensByUserId ");
		if(OPLUtils.isObjectNullOrEmpty(req.getUserId()) || OPLUtils.isObjectNullOrEmpty(req.getGrant_type())){
			return new ResponseEntity<>(new AuthCommonRes(HttpStatus.BAD_REQUEST.value(),"Request Parameter Null Or Empty",null), HttpStatus.BAD_REQUEST);
		}
		if(!AuthCredentialUtils.TOKEN_REQ_KEY.equals(req.getGrant_type())){
			return new ResponseEntity<>(new AuthCommonRes(HttpStatus.UNAUTHORIZED.value(),"Required Token Key is Wrong !!",null), HttpStatus.UNAUTHORIZED);
		}
		try {
			AuthResponse res = userTokenMappingService.getTokensByUserId(req.getUserId());
			if(res == null){
				return new ResponseEntity<>(new AuthCommonRes(HttpStatus.BAD_REQUEST.value(),"Invalid UserId !!",null), HttpStatus.BAD_REQUEST);
			} else {
				return new ResponseEntity<>(new AuthCommonRes(HttpStatus.OK.value(),SUCCESSFULLY_GET_RESULT,res), HttpStatus.OK);
			}
		} catch(Exception e){
			logger.error("Throw Exception while get log details : ", e);
			return new ResponseEntity<>(
					new AuthCommonRes(HttpStatus.INTERNAL_SERVER_ERROR.value(),SOMETHING_WENT_WRONG,null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/check_user_loggined", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthCommonRes> checkUserLoggined(@RequestBody AuthRequest req) {
		logger.info("Enter in check user loggined or not ");
		try {
			boolean loggined = userTokenMappingService.checkUserLogginedOrNot(req.getUserId());
			logger.info("Successfully check user loggined or not ");
			return new ResponseEntity<>(new AuthCommonRes(HttpStatus.OK.value(),SUCCESSFULLY_GET_RESULT,loggined), HttpStatus.OK);
		} catch(Exception e){
			logger.error("Throw Exception while check user loggined or not : ", e);
			return new ResponseEntity<>(
					new AuthCommonRes(HttpStatus.INTERNAL_SERVER_ERROR.value(),SOMETHING_WENT_WRONG,null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/checkAuthStarted",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public AuthResponse checkAuthStarted() {
		logger.info("check Auth service Started or not");
		try {
			return  new AuthResponse("Auth service Started", HttpStatus.OK.value());			
		} catch (Exception e) {
			logger.error("Throw Exception While check Auth service Started or not : ", e);
			return null;
		}
		
	}
	
	@PostMapping("/updateCustomerInTokenMapping")
	public ResponseEntity<AuthCommonRes> updateCustomerInTokenMapping(@RequestBody AuthRequest req) {
		logger.info("INSIDE updateCustomerInTokenMapping() ----->");
		try {
			if (OPLUtils.isObjectNullOrEmpty(req.getClientUserId()) || OPLUtils.isObjectNullOrEmpty(req.getUsername()) || OPLUtils.isObjectNullOrEmpty(req.getAccessToken()) || OPLUtils.isObjectNullOrEmpty(req.getLoginToken())) {
				return new ResponseEntity<>(new AuthCommonRes(HttpStatus.BAD_REQUEST.value(), "Request Parameter Null Or Empty", null), HttpStatus.BAD_REQUEST);
			}
			Integer updateCount = userTokenMappingService.updateCustomerInTokenMapping(req);
			if (OPLUtils.isObjectNullOrEmpty(updateCount) || updateCount == 0) {
				return new ResponseEntity<>(new AuthCommonRes(HttpStatus.BAD_REQUEST.value(), "No record found", null), HttpStatus.OK);
			}
			return new ResponseEntity<>(new AuthCommonRes(HttpStatus.OK.value(), "Customer id updated in token mapping", null), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Throw Exception While updateCustomerInTokenMapping() : ", e);
			return new ResponseEntity<>(new AuthCommonRes(HttpStatus.INTERNAL_SERVER_ERROR.value(), SOMETHING_WENT_WRONG, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
