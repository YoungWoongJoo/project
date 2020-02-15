package com.mycom.warehouse.mypage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.member.vo.MemberVO;
import com.mycom.warehouse.warehouse.service.WarehouseService;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Controller("mypageController")
@RequestMapping(value="/mypage")
public class MypageControllerImpl extends BaseController implements MypageController {
	@Autowired
	WarehouseService warehouseService;
	@Autowired
	MemberVO memberVO;

	@Override
	@RequestMapping(value="/main.do")
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session=request.getSession();
		session=request.getSession();
		session.setAttribute("side_menu", "my_page"); //마이페이지 사이드 메뉴로 설정한다.
		
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		memberVO=(MemberVO)session.getAttribute("memberVO");
		String member_id=memberVO.getMember_id();
		List<WarehouseVO> list = warehouseService.listWarehouse(member_id);
		mav.addObject("warehouseList", list);
		return mav;
	}

}
