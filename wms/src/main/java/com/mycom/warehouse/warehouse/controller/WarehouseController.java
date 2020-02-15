package com.mycom.warehouse.warehouse.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.warehouse.vo.WarehouseVO;

public interface WarehouseController {
	public ResponseEntity<String> addWarehouse(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView warehouseUpdateForm(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView updateWarehouse(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView deleteWarehouse(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public WarehouseVO searchWarehouse(@RequestParam("warehouse_name")String warehouse_name, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
