package com.mycom.warehouse.warehouse.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.member.vo.MemberVO;
import com.mycom.warehouse.warehouse.service.WarehouseService;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Controller("warehouseController")
@RequestMapping(value="/warehouse")
public class WarehouseControllerImpl extends BaseController implements WarehouseController {
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	MemberVO memberVO;
	@Autowired
	WarehouseVO warehouseVO;

	@Override
	@RequestMapping(value="/addNewWarehouse.do", method = RequestMethod.POST)
	public ResponseEntity<String> addWarehouse(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String msg = null;
		HttpSession session = request.getSession();
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		memberVO = (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		warehouseVO.setMember_id(member_id);
		try {
			warehouseService.register(warehouseVO);
			msg  = "<script>";
			msg +=" alert('창고 등록을 완료했습니다. 마이페이지로 이동합니다.');";
			msg += " location.href='"+request.getContextPath()+"/mypage/main.do';";
			msg += " </script>";
		}
		catch(Exception e) {
			msg  = "<script>";
			msg +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
			msg += " history.back();";
			msg += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
	@Override
	@RequestMapping(value="/updateForm.do", method = RequestMethod.POST)
	public ModelAndView warehouseUpdateForm(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String viewName = (String)request.getAttribute("viewName");
		mav.addObject("warehouseVO", warehouseVO);
		mav.setViewName(viewName);
		return mav;
	}

	@Override
	@RequestMapping(value="/updateWarehouse.do", method = RequestMethod.POST)
	public ModelAndView updateWarehouse(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		warehouseService.update(warehouseVO);
		mav.setViewName("redirect:/warehouse/register.do");
		return mav;
	}

	@Override
	@RequestMapping(value="/deleteWarehouse.do", method = RequestMethod.POST)
	public ModelAndView deleteWarehouse(@ModelAttribute("warehouseVO") WarehouseVO warehouseVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		warehouseService.delete(warehouseVO);
		mav.setViewName("redirect:/mypage/main.do");
		return mav;
	}
	
	

}
