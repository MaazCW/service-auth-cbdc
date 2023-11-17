package com.opl.cbdc.common.service.auth.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.opl.cbdc.common.api.auth.model.AuthClientResponse;
import com.opl.cbdc.common.api.auth.model.MobileAppLoginRequest;
import com.opl.cbdc.common.api.auth.model.MobilelAppLoginResponse;
import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
import com.opl.cbdc.common.service.auth.domain.AppLoginMaster;
import com.opl.cbdc.common.service.auth.domain.AppRequestLogMaster;
import com.opl.cbdc.common.service.auth.domain.User;
import com.opl.cbdc.common.service.auth.repositories.AppLoginMasterRepository;
import com.opl.cbdc.common.service.auth.repositories.AppRequestLogMasterRepository;
import com.opl.cbdc.common.service.auth.repositories.UserRepository;
import com.opl.cbdc.common.service.auth.service.AppLoginMasterService;
import com.opl.cbdc.utils.common.OPLUtils;

@Service
@Transactional
public class AppLoginMasterServieImpl implements AppLoginMasterService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AppLoginMasterRepository mobileAppLogMasterRepository;

	@Autowired
	private AppRequestLogMasterRepository mobileReqLogMasterRepository;
	
	private static Logger logger = LoggerFactory.getLogger(AppLoginMasterServieImpl.class);
	
	@Override
	public MobilelAppLoginResponse login (MobileAppLoginRequest request){
		MobilelAppLoginResponse response = new MobilelAppLoginResponse(); 
		User user = null;
		if(!OPLUtils.isObjectNullOrEmpty(request.getEmail()) && !OPLUtils.isObjectNullOrEmpty(request.getPassword())) {
			String password = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());
			user = userRepository.getUserByEmailAndPassword(request.getEmail(), password);
		}else {
			user = userRepository.getUserByMobile(request.getMobileNo());
		}
			if(user == null) {
				response.setValid(false);
				return response;
			}
			response.setValid(true);
			String token = AuthCredentialUtils.generateRandomToken();
			response.setToken(token);
			response.setUserType(user.getUserTypeMaster().getId());
			AppLoginMaster logMaster = new AppLoginMaster(request.getEmail(),token,new Date());
			logMaster.setIsActive(true);
			logMaster.setUserId(user.getUserId());
			logMaster.setUserType(user.getUserTypeMaster().getId());
			logMaster.setImeiNo(request.getImeiNo());
			logMaster.setMobileNo(request.getMobileNo());
			logMaster.setModelNo(request.getModelNo());
			logMaster.setMobileOs(request.getMobileOs());
			logMaster.setOsVersion(request.getOsVersion());
			mobileAppLogMasterRepository.save(logMaster);
		
		return response;
	}
	
	@Override
	public AuthClientResponse checkToken(MobileAppLoginRequest request) {
		AuthClientResponse response = new AuthClientResponse();
		AppLoginMaster master = mobileAppLogMasterRepository.getByToken(request.getToken());
		if(master == null) {
			response.setAuthenticate(false);
			response.setMessage("Token is not valid");
			logger.warn("Toke not valid ------------------>"+request.getToken());
			return response;
		} else {
			AppRequestLogMaster logMaster = mobileReqLogMasterRepository.checkRequestNo(master.getId(), request.getRequestNo());
			if(logMaster != null) {
				response.setAuthenticate(false);
				response.setMessage("Dublicate Request Number");
				logger.warn("Dublicate Request Number------------------>"+request.getRequestNo());
				return response;
			} else {
				AppRequestLogMaster reqLogMaster = new AppRequestLogMaster();
				reqLogMaster.setMobileNo(request.getMobileNo());
				reqLogMaster.setMobileOs(request.getMobileOs());
				reqLogMaster.setImeiNo(request.getImeiNo());
				reqLogMaster.setModelNo(request.getModelNo());
				reqLogMaster.setOsVersion(request.getOsVersion());
				reqLogMaster.setRequestNo(request.getRequestNo());
				reqLogMaster.setRequestObj(request.getRequestObj());
				reqLogMaster.setRequestUrl(request.getRequestUrl());
				reqLogMaster.setUserId(master.getUserId());
				reqLogMaster.setCreatedDate(new Date());
				reqLogMaster.setIsActive(Boolean.TRUE);
				reqLogMaster.setAppLoginId(master.getId()); 
				mobileReqLogMasterRepository.save(reqLogMaster);
				response.setAuthenticate(true);
				response.setUserId(master.getUserId());
				response.setUserType(master.getUserType());
				response.setMessage("Token is valid");
				logger.warn("Token is valid------------------>"+request.getToken());
				return response;
			}
		}
	}
	
	@Override
	public boolean logout(String token) {
		AppLoginMaster appLoginMaster = mobileAppLogMasterRepository.getByToken(token);
		if(appLoginMaster == null) {
			return false;
		} else {
			mobileReqLogMasterRepository.activeFalse(appLoginMaster.getId());
			mobileAppLogMasterRepository.logout(token);	
		}
		return true;
	}
	
	@Override
	public void addRequest(MobileAppLoginRequest request) {
		AppRequestLogMaster reqLogMaster = new AppRequestLogMaster();
		BeanUtils.copyProperties(request, reqLogMaster);
		reqLogMaster.setCreatedDate(new Date());
		reqLogMaster.setIsActive(Boolean.FALSE);
		mobileReqLogMasterRepository.save(reqLogMaster);
	}

	@Override
	public MobileAppLoginRequest checkMobileNumberActiveForLogin(String mobileNumber, Long userTypeId) {
		MobileAppLoginRequest mobileAppLoginRequest = new MobileAppLoginRequest();
		User user = userRepository.findOneByMobileAndUserTypeMasterId(mobileNumber,userTypeId);
		if(!OPLUtils.isObjectNullOrEmpty(user) && user.isActive()){
			mobileAppLoginRequest.setEmail(user.getEmail());
			mobileAppLoginRequest.setPassword(user.getPassword());
			mobileAppLoginRequest.setMobileNo(user.getMobile());
			return mobileAppLoginRequest;
		}
		return null;
	}
	
}
