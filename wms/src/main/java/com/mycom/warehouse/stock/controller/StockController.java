package com.mycom.warehouse.stock.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.stock.vo.StockVO;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

public interface StockController {
	public ModelAndView registerForm(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> addNewStock(@ModelAttribute("stockVO") StockVO stockVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<List<StockVO>> getList(@RequestParam("warehouse_name") String warehouse_name, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateForm(@ModelAttribute("stockVO") StockVO stockVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> updateStock(@ModelAttribute("stockVO") StockVO stockVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> deleteStock(@ModelAttribute("stockVO") StockVO stockVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public List<String> keywordSearch(@RequestParam("keyword") String keyword, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public int yearToStringTwoNum();
}
