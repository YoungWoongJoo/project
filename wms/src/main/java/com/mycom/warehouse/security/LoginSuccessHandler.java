package com.mycom.warehouse.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.mycom.warehouse.member.vo.MemberVO;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	RequestCache requestCache = new HttpSessionRequestCache();
	RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
	@Autowired
	CustomUserDetailService customUserDetailService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		String errorMsg = (String)session.getAttribute("errorMsg");
		if(errorMsg!=null)
			session.removeAttribute("errorMsg");
		String member_id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		MemberVO memberVO = (MemberVO) customUserDetailService.loadUserByUsername(member_id);
		session.setAttribute("memberVO", memberVO);
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if(savedRequest != null){

		String targetUrl = savedRequest.getRedirectUrl();
		redirectStratgy.sendRedirect(request, response, targetUrl); //捞傈 林家

		}else{
			String defaultUrl = "/main.do";
			redirectStratgy.sendRedirect(request, response, defaultUrl); //default 林家

		}
	}

}
