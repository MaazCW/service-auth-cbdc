package com.opl.cbdc.common.service.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.AuthCommonRes;
import com.opl.cbdc.common.api.auth.model.MobileAppLoginRequest;
import com.opl.cbdc.common.api.auth.model.MobilelAppLoginResponse;
import com.opl.cbdc.common.api.auth.utils.MobileCustomizeResponse;
import com.opl.cbdc.common.service.auth.service.AppLoginMasterService;
import com.opl.cbdc.utils.common.OPLUtils;



@RestController
@RequestMapping("/app")
public class AppLoginMasterController {
	
	@Autowired
	private AppLoginMasterService mobileAppLoginService;
	
	private static Logger logger = LoggerFactory.getLogger(AppLoginMasterController.class);
	private static final String FALSE = "false";
	
	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthCommonRes> mobileLogin(@RequestBody MobileAppLoginRequest request) {
		logger.info("Enter in mobile app login service");
		if((OPLUtils.isObjectNullOrEmpty(request.getEmail()) || OPLUtils.isObjectNullOrEmpty(request.getPassword())) &&
				(OPLUtils.isObjectNullOrEmpty(request.getMobileNo()) || OPLUtils.isObjectNullOrEmpty(request.getOtp()))) {
			logger.warn("Email or password is empty / Mobile No or OTP is empty");
			return new ResponseEntity<>(new AuthCommonRes(FALSE, MobileCustomizeResponse.ERROR403.getStatusCode(), MobileCustomizeResponse.ERROR403.getDescription(), "Please enter mandatory details" ),HttpStatus.OK);
		}
		try {
			MobilelAppLoginResponse response =  mobileAppLoginService.login(request);
			if(response.isValid()) {
				logger.info("Login Successfully -------------------"+request.getEmail());
				return new ResponseEntity<>(
						new AuthCommonRes("true", response, MobileCustomizeResponse.SUCCESS200.getStatusCode(), MobileCustomizeResponse.SUCCESS200.getDescription(), "Login Successfully"),HttpStatus.OK);	
			} else {
				logger.info("Invalid username or password");
				return new ResponseEntity<>(new AuthCommonRes(FALSE, MobileCustomizeResponse.ERROR404.getStatusCode(), MobileCustomizeResponse.ERROR404.getDescription(), "Invalid Details" ),HttpStatus.OK);
			}	
		} catch (Exception e) {
			logger.error("Something went wrong when user try login from mobile : ", e);
			return new ResponseEntity<>(new AuthCommonRes(FALSE, MobileCustomizeResponse.INTERNALSERVERERROR407.getStatusCode(), MobileCustomizeResponse.INTERNALSERVERERROR407.getDescription(), "Internal Server Error"),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/tokenAuthentication", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthClientResponse> checkMobileToken(@RequestBody MobileAppLoginRequest request) {
		
		logger.info("Enter in check mobile app token");
		if(OPLUtils.isObjectNullOrEmpty(request.getToken()) || OPLUtils.isObjectNullOrEmpty(request.getRequestNo())) {
			logger.warn("Token or Request number is empty");
			return new ResponseEntity<>(new AuthClientResponse(HttpStatus.BAD_REQUEST.value(),"Invalid request"),HttpStatus.OK);
		}
		try {
			AuthClientResponse response =  mobileAppLoginService.checkToken(request);
			response.setStatus(response.isAuthenticate() ? HttpStatus.OK.value() : HttpStatus.UNAUTHORIZED.value());
			return new ResponseEntity<>(response,HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Something went wrong when user try to authentication token from mobile ---->" +request.getToken(), e);
			return new ResponseEntity<>(
					new AuthClientResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong"),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthCommonRes> logout(@RequestBody MobileAppLoginRequest request) {
		logger.info("Enter in mobile app logout service");
		if(request.getToken() == null) {
			logger.warn("Token is Empty");
			return new ResponseEntity<>(new AuthCommonRes(FALSE, MobileCustomizeResponse.ERROR400.getStatusCode(), MobileCustomizeResponse.ERROR400.getDescription(), "Auth Token is Missing"),HttpStatus.OK);
		}
		try {
			boolean logout = mobileAppLoginService.logout(request.getToken());
			logger.info("Successfully logout service execute========"+logout);
			return new ResponseEntity<>(new AuthCommonRes("true", logout, MobileCustomizeResponse.SUCCESS200.getStatusCode(), MobileCustomizeResponse.SUCCESS200.getDescription(), "Successfully Logged out"),HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Something went wrong when user try logout from mobile : ", e);
			return new ResponseEntity<>(new AuthCommonRes(FALSE, MobileCustomizeResponse.INTERNALSERVERERROR407.getStatusCode(), MobileCustomizeResponse.INTERNALSERVERERROR407.getDescription(), "Internal Server Error"),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/addRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthCommonRes> addRequest(@RequestBody MobileAppLoginRequest request) {
		logger.info("Enter in mobile app addRequest service");
		try {
			mobileAppLoginService.addRequest(request);
			logger.info("Successfully addRequest service execute========");
			return new ResponseEntity<>(
					new AuthCommonRes(HttpStatus.OK.value(), "Updated Successfully",null),HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Something went wrong when user try addrequest from mobile : ", e);
			return new ResponseEntity<>(
					new AuthCommonRes(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Something went wrong",null),HttpStatus.OK);
		}
	}
}
