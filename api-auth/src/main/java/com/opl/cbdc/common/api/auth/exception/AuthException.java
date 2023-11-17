package com.opl.cbdc.common.api.auth.exception;

public class AuthException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3494092473797863483L;

	//Parameterless Constructor
    public AuthException() {}

    //Constructor that accepts a message
    public AuthException(String message)
    {
       super(message);
    }
}