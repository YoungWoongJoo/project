package com.mycom.warehouse.stock.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.stock.vo.StockVO;
import com.mycom.warehouse.stock.vo.StockVOs;

public interface StockController {
	public ResponseEntity<String> register(@RequestParam("warehouse_name") String warehouse_name, @ModelAttribute("stockVOs") StockVOs stockVOs, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<List<StockVO>> getList(@RequestParam("warehouse_name") String warehouse_name, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
