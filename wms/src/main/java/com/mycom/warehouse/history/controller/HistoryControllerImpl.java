package com.mycom.warehouse.history.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.history.service.HistoryService;
import com.mycom.warehouse.history.vo.HistoryVO;
import com.mycom.warehouse.member.vo.MemberVO;
import com.mycom.warehouse.warehouse.service.WarehouseService;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Controller("historyController")
@RequestMapping(value="/history")
public class HistoryControllerImpl extends BaseController implements HistoryController {
	@Autowired
	MemberVO memberVO;
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	HistoryService historyService;
	
	
	@Override
	@RequestMapping(value="/register.do")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse resoponse) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		List<WarehouseVO> warehouseList = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", warehouseList);
		int year = yearToStringTwoNum();
		mav.addObject("year", year);
		return mav;
	}

	@Override
	@RequestMapping(value="/list.do")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse resoponse) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		memberVO = (MemberVO)session.getAttribute("memberVO");
		String member_id = memberVO.getMember_id();
		List<WarehouseVO> warehouseList = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", warehouseList);
		return mav;
	}

	@Override
	@RequestMapping(value="/registerCheck.do")
	public ModelAndView registerCheck(@ModelAttribute("historyVO")HistoryVO historyVO, HttpServletRequest request, HttpServletResponse resoponse)
			throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("historyVO", historyVO);
		return mav;
	}

	@Override
	@RequestMapping(value="/addNewHistory.do")
	public ResponseEntity<String> addNewHistory(@ModelAttribute("historyVO")HistoryVO historyVO, HttpServletRequest request,
			HttpServletResponse resoponse) throws Exception {
		String msg = null;
		ResponseEntity<String> res = null;
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/html; charset=utf-8"); 
		
		try {
			String msg1 = historyService.insert(historyVO);
			msg  = "<script>";
			msg +=" alert('"+msg1+"');";
			if(msg1.equals("재고 관리를 마쳤습니다. 재고 관리 내역 창으로 이동합니다."))
				msg += "location.href='"+request.getContextPath()+"/history/list.do'";
			else
				msg += "history.back();";
			msg += " </script>";
		}
		catch(Exception e) {
			msg  = "<script>";
			msg +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
			msg += "history.back();";
			msg += " </script>";
			e.printStackTrace();
		}
		
		res = new ResponseEntity<String>(msg, header, HttpStatus.OK);
		
		return res;
	}

	@Override
	@RequestMapping(value="/getList.do")
	public @ResponseBody List<HistoryVO> getList(HistoryVO historyVO, HttpServletRequest request,
			HttpServletResponse resoponse) throws Exception {
		return historyService.selectList(historyVO);
	}

	@Override
	@RequestMapping(value="/delete.do")
	public ResponseEntity<String> deleteHistory(HistoryVO historyVO, HttpServletRequest request,
			HttpServletResponse resoponse) throws Exception {
		String msg = null;
		ResponseEntity<String> res = null;
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/html; charset=utf-8"); 
		
		try {
			historyService.delete(historyVO);
			msg  = "<script>";
			msg += "alert('재고 관리 내역을 삭제했습니다. 재고 관리 내역 창으로 이동합니다.');";
			msg += "location.href='"+request.getContextPath()+"/history/list.do'";
			msg += " </script>";
		}
		catch(Exception e) {
			msg  = "<script>";
			msg +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
			msg += "history.back();";
			msg += " </script>";
			e.printStackTrace();
		}
		
		res = new ResponseEntity<String>(msg, header, HttpStatus.OK);
		
		return res;
	}

}
