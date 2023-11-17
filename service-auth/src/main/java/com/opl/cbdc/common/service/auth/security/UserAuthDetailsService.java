package com.opl.cbdc.common.service.auth.security;

import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
import com.opl.cbdc.common.service.auth.domain.User;
import com.opl.cbdc.common.service.auth.repositories.UserRepository;

@Service("UserAuthDetailsService")
public class UserAuthDetailsService implements UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(UserAuthDetailsService.class);
	private static final String USER = "User ";
	private static final String IS_NOT_ACTIVATED = " is not activated";

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String un) throws UsernameNotFoundException {

		String[] pwd = un.toString().split(Pattern.quote("$"));

		Map<String, String> map = AuthCredentialUtils.getUserName(pwd[0]);
		String username = map.get(AuthCredentialUtils.USER_NAME);
		String isEmail = map.get(AuthCredentialUtils.IS_EMAIL);

		Long userTypeId = Long.valueOf(pwd[1]);

		// find user object by user name
		User user = null;
		if (AuthCredentialUtils.IS_EMAIL_TRUE_VALUE.equals(isEmail)) {
			user = userRepository.getByCaseSensitiveEmailAndUserTypeMasterId(username, userTypeId);
		} else {
			user = userRepository.findOneByMobileAndUserTypeMasterId(username, userTypeId);
		}
		if (user == null) {
			log.info(USER + username + " was not found in the database");
			throw new UsernameNotFoundException(USER + username + " was not found in the database");
		}

		if (!user.isActive()) {
			log.info(USER + username + IS_NOT_ACTIVATED);
			throw new UserNotActivatedException(USER + username + IS_NOT_ACTIVATED);
		}

		String pass = user.getPassword();
		if (AuthCredentialUtils.IS_EMAIL_FALSE_VALUE.equals(isEmail) && user.getPassword() == null) {
			pass = AuthCredentialUtils.TEMP_PP;
		}

		// send user name and password to user security class for check valid or not
		return new org.springframework.security.core.userdetails.User(username, pass, Collections.emptyList());

	}
}
