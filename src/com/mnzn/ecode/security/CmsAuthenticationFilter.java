package com.mnzn.ecode.security;


import java.sql.Timestamp;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.mnzn.ecode.common.captcha.Captchas;
import com.mnzn.ecode.common.web.Servlets;
import com.mnzn.ecode.core.entity.User;
import com.mnzn.ecode.security.ShiroDbRealm.ShiroUser;
import com.mnzn.ecode.service.UserService;
import com.octo.captcha.service.CaptchaService;

/**
 * CmsAuthenticationFilter
 * 
 * @author cxy
 * 
 */
public class CmsAuthenticationFilter  extends FormAuthenticationFilter {
	
	public static final String CAPTCHA_ERROR_COUNT_KEY = "shiroCaptchaErrorCount";
	public static final String CAPTCHA_REQUIRED_KEY = "shiroCaptchaRequired";
	public static final String CAPTCHA_PARAM = "captcha";
	public static final int CAPTCHAERRORS_TIME=3;
	public static final String CURRENT_USER = "curUser";
	public static final String FALLBACK_URL_PARAM = "fallbackUrl";
	
	@Override
	protected boolean executeLogin(ServletRequest request,
			ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
					+ "must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		String username = (String) token.getPrincipal();
		User user = accountService.findMobileUser(username);
		if(user == null){
			return onLoginFailure(token, new UnactivatedAccountException(),
					request, response);
		}
		
		HttpServletRequest hsr = (HttpServletRequest) request;
		HttpServletResponse hsp = (HttpServletResponse) response;
		if (isCaptchaSessionRequired(hsr, hsp)) {
			String captcha = request.getParameter(CAPTCHA_PARAM);
			if (captcha == null|| !Captchas.isValid(captchaService, hsr, captcha)) {
				return onLoginFailure(token, new IncorrectCaptchaException(),
						request, response);
			}
		}
		
		String ip = Servlets.getRemoteAddr(request);
		// 登录时，session会失效，先将错误次数取出
		Integer errorCount = (Integer) hsr.getSession().getAttribute(
				CAPTCHA_ERROR_COUNT_KEY);
		// 登录时，session会失效，先将SavedRequest取出
		SavedRequest savedRequest = (SavedRequest) hsr.getSession()
				.getAttribute(WebUtils.SAVED_REQUEST_KEY);
		try {
			Subject subject = getSubject(request, response);
			// 防止session fixation attack(会话固定攻击)，让旧session失效
			if (subject.getSession(false) != null) {
				subject.logout();
			}
			subject.login(token);
			// 将SavedRequest放回session
			hsr.getSession().setAttribute(WebUtils.SAVED_REQUEST_KEY,
					savedRequest);
			return onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			Object cred = token.getCredentials();
			String password = "";
			if (cred instanceof char[]) {
				password = new String((char[]) cred);
			}
			// 将错误次数放回session
			hsr.getSession().setAttribute(CAPTCHA_ERROR_COUNT_KEY, errorCount);
			// 将SavedRequest放回session
			hsr.getSession().setAttribute(WebUtils.SAVED_REQUEST_KEY,
					savedRequest);
			return onLoginFailure(token, e, request, response);
		}
	}

	@Override
	public boolean onPreHandle(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		boolean isAllowed = isAccessAllowed(request, response, mappedValue);
		if (isAllowed && isLoginRequest(request, response)) {
			try {
				issueSuccessRedirect(request, response);
			} catch (Exception e) {
			}
			return false;
		}
		return isAllowed || onAccessDenied(request, response, mappedValue);
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
		String ip = Servlets.getRemoteAddr(request);
		HttpServletRequest hsr = (HttpServletRequest) request;
		HttpServletResponse hsp = (HttpServletResponse) response;
		// 清除需要验证码的session
		removeCaptchaSession(hsr, hsp);
		User user = accountService.findMobileUser(shiroUser.userName);
		user.setErrorCount(0);
		user.setLoginIp(ip);
		user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		User nuser = userService.update(user);
	    hsr.getSession().setAttribute(CURRENT_USER, nuser);
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String username = (String) token.getPrincipal();
		HttpServletRequest hsr = (HttpServletRequest) request;
		HttpServletResponse hsp = (HttpServletResponse) response;
		HttpSession session = hsr.getSession();
		Integer errorCount = (Integer) session.getAttribute(CAPTCHA_ERROR_COUNT_KEY);
		if (errorCount != null) {
			errorCount++;
		} else {
			errorCount = 1;
		}
		session.setAttribute(CAPTCHA_ERROR_COUNT_KEY, errorCount);
		String ip = Servlets.getRemoteAddr(request);
		if(username!=null){
			User user = accountService.findMobileUser(username);
			if(user!=null){
				user.setErrorCount(errorCount);
				user.setLoginIp(ip);
				User nuser = userService.update(user);
			}
			if (errorCount >= CAPTCHAERRORS_TIME) {
				// 加入需要验证码的session
				addCaptchaSession(hsr, hsp);
			}
		}
		
		return super.onLoginFailure(token, e, request, response);
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest req, ServletResponse resp)
			throws Exception {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		String successUrl = request.getParameter(FALLBACK_URL_PARAM);
		if (StringUtils.isBlank(successUrl)) {
			if (request.getRequestURI().indexOf("login.mn")>0) {
				// 后台直接返回首页
				successUrl = request.getContextPath()+"/";
				// 清除SavedRequest
				WebUtils.getAndClearSavedRequest(request);
				WebUtils.issueRedirect(request, response, successUrl, null,
						true);
				return;
			} else {
				successUrl = getSuccessUrl();
			}
		}
		WebUtils.redirectToSavedRequest(request, response, successUrl);
	}


	protected void removeCaptchaSession(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(CAPTCHA_REQUIRED_KEY);
			session.removeAttribute(CAPTCHA_ERROR_COUNT_KEY);
		}
	}

	protected void addCaptchaSession(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute(CAPTCHA_REQUIRED_KEY, true);
	}

	protected boolean isCaptchaSessionRequired(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return session.getAttribute(CAPTCHA_REQUIRED_KEY) != null;
		}
		return false;
	}

	
	private UserService userService;
    @Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	protected AccountService accountService;
	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	private CaptchaService captchaService;
	@Autowired
	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}
}