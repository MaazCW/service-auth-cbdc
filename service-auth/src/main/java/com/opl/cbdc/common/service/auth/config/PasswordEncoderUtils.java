package com.opl.cbdc.common.service.auth.config;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import com.opl.cbdc.common.api.auth.utils.AuthCredentialUtils;
import com.opl.cbdc.utils.common.EncryptionUtils;

public class PasswordEncoderUtils implements org.springframework.security.crypto.password.PasswordEncoder {

	private static final Logger logger = LoggerFactory.getLogger(PasswordEncoderUtils.class);

	@Override
	public String encode(CharSequence rawPassword) {
		return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String[] pwd = rawPassword.toString().split(Pattern.quote("$"));
		String rawPasswordDecode = "";
		if (pwd.length < 2) {
			if (AuthCredentialUtils.TEMP_PP.equals(rawPassword)) {
				return true;
			}
			rawPasswordDecode = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
		} else {
			String lastOne = pwd[pwd.length - 1];
			if (!AuthCredentialUtils.LOGIN_KEY.equals(new EncryptionUtils().encryptionWithKey(lastOne))) {
				logger.info("Secret key is invalid while check user authentication !! ---->{}", lastOne);
				return false;
			}
			rawPasswordDecode = pwd[0];
		}
		return rawPasswordDecode.equals(encodedPassword);
	}

}
