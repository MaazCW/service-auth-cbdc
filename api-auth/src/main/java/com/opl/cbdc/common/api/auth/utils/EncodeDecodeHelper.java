package com.opl.cbdc.common.api.auth.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;


public class EncodeDecodeHelper {
	private EncodeDecodeHelper(){
		// nothing to do.
	}
	private static Logger logger = LoggerFactory.getLogger(EncodeDecodeHelper.class);

	public static String encode(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		try {
			return Base64.getEncoder().encodeToString(str.getBytes());
		} catch (Exception e) {
            logger.error("Exception" ,e);
			return null;
		}

	}

	public static String decode(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		try {
			byte[] value = Base64.getDecoder().decode(str);
			return new String(value);
		} catch (Exception e) {
			logger.error("Exception" ,e);
			return null;
		}
	}

}
