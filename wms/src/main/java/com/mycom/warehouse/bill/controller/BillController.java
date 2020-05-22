package com.mycom.warehouse.bill.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public interface BillController {
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public @ResponseBody Map<String,Object> calc(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void downloadExcel(@RequestParam Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
