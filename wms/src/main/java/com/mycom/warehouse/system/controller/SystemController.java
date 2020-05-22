package com.mycom.warehouse.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface SystemController {
	public ResponseEntity<String> systemAdd(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> systemUpdate(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> systemDelete(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateForm(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView rateList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView settingList(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
