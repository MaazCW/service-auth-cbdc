package com.opl.cbdc.common.api.auth.model;

import java.io.Serializable;

public class AuthCommonRes  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private int status;
	private String message;
	private Object data;
	private Long response_code;
	private String response_code_message;
	private String success;
	
	public AuthCommonRes(){
		
	}
	
	public AuthCommonRes(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	public Long getResponse_code() {
		return response_code;
	}

	public void setResponse_code(Long response_code) {
		this.response_code = response_code;
	}

	public String getResponse_code_message() {
		return response_code_message;
	}

	public void setResponse_code_message(String response_code_message) {
		this.response_code_message = response_code_message;
	}
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public AuthCommonRes(String success, Object data, Long response_code, String response_code_message, String message) {
		super();
		this.success = success;
		this.data = data;
		this.response_code = response_code;
		this.message = message;
		this.response_code_message = response_code_message;
	}
	public AuthCommonRes(String success, Long response_code, String response_code_message, String message) {
		super();
		this.success = success;
		this.response_code = response_code;
		this.response_code_message = response_code_message;
		this.message = message;
	}

	@Override
	public String toString() {
		return "CommonResponse [id=" + id + ", status=" + status + ", message=" + message + ", data=" + data
				+ ", response_code=" + response_code + ", response_code_message=" + response_code_message + ", success="
				+ success + "]";
	}
	
	
	
}
