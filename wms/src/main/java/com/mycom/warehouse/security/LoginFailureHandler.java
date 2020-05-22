package com.mycom.warehouse.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailureHandler implements AuthenticationFailureHandler {
	
	private String loginIdName;
    private String loginPwdName;
    private String errorMsgName;
    private String defaultFailureUrl;
    

	public String getLoginIdName() {
		return loginIdName;
	}


	public void setLoginIdName(String loginIdName) {
		this.loginIdName = loginIdName;
	}


	public String getLoginPwdName() {
		return loginPwdName;
	}


	public void setLoginPwdName(String loginPwdName) {
		this.loginPwdName = loginPwdName;
	}


	public String getErrorMsgName() {
		return errorMsgName;
	}


	public void setErrorMsgName(String errorMsgName) {
		this.errorMsgName = errorMsgName;
	}


	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}


	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMsg = null;

        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMsg = "아이디 또는 비밀번호가 틀렸습니다.";
        }

        HttpSession session = request.getSession();
        session.setAttribute("errorMsg", errorMsg);
        
        RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
        redirectStratgy.sendRedirect(request, response, "/main.do");	
	}

}
