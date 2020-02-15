package com.mycom.warehouse.common.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mycom.warehouse.member.vo.MemberVO;

public class LoginCheckInterceptor extends  HandlerInterceptorAdapter {
	@Autowired
	MemberVO memberVO;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Object isLogOn = session.getAttribute("isLogOn");
		memberVO = (MemberVO)session.getAttribute("memberVO");
		if(isLogOn==null || memberVO==null)
		{
			response.setContentType("text/html; charset=UTF-8");
			String contextPath = request.getContextPath();
			PrintWriter out = response.getWriter();
			out.println("<script>alert('로그인 후 이용가능합니다. 로그인해주세요.'); location.href='"+contextPath+"/main.do'</script>");
			out.flush();
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
