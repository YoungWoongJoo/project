package com.mycom.warehouse.mypage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface MypageController {
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
