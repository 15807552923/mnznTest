package com.mnzn.ecode.security;

import org.apache.shiro.authc.CredentialsException;

/**
 * 验证码错误异常
 * 
 * 
 */
public class IncorrectCaptchaException extends CredentialsException {
	private static final long serialVersionUID = 1L;
}
