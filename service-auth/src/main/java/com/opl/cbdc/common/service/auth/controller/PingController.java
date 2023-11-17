package com.opl.cbdc.common.service.auth.controller;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
	private static final Logger logger = LoggerFactory.getLogger(PingController.class);

	@GetMapping("/ping")
	public ResponseEntity<Object> ping() {
		logger.info("CHECK SERVICE STATUS =====================>");
		Map<String, Object> obj = new HashMap<>();
		obj.put("status", HttpStatus.OK.value());
		obj.put("message", "Service is working fine!!");
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
}
