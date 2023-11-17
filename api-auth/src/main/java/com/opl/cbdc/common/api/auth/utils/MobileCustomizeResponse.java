package com.opl.cbdc.common.api.auth.utils;

public enum MobileCustomizeResponse {
	
	ERROR400(400L,"Auth token is missing"),
	ERROR401(401L,"Invalid auth token"),
	ERROR402(402L,"Auth token Expire"),
	ERROR403(403L,"Invalid request"),
	ERROR404(404L,"Invalid parameter"),
	ERROR405(405L,"The user is Inactive from system"),
	ERROR406(406L,"Invalid Login"),
	SUCCESS200(200L,"Success"),
	INTERNALSERVERERROR407(407L,"Other Error");
	
	private final Long statusCode;
	private final String description;
	
	private MobileCustomizeResponse(Long statusCode, String description) {
		this.statusCode = statusCode;
		this.description = description;
	}

	public Long getStatusCode() {
		return statusCode;
	}

	public String getDescription() {
		return description;
	} 
}
