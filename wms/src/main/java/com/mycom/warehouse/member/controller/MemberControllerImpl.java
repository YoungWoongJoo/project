package com.mycom.warehouse.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.member.service.MemberService;
import com.mycom.warehouse.member.vo.MemberVO;

@Controller("memberController")
@RequestMapping(value = "/member")
public class MemberControllerImpl extends BaseController implements MemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	MemberVO memberVO;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	/*
	@Override
	@RequestMapping(value="/login.do" ,method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestParam Map<String, String> loginMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = null;
		memberVO=null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		memberVO = memberService.login(loginMap);
		if(memberVO!= null && memberVO.getMember_id()!=null){
			HttpSession session = request.getSession();
			session.setAttribute("isLogOn", true);
			session.setAttribute("memberVO", memberVO);
			message  = "<script>";
		    message += " location.href='"+request.getContextPath()+"/main.do';";
		    message += " </script>";
		}
		
		else
		{
			message  = "<script>";
		    message +=" alert('아이디나  비밀번호가 틀립니다. 다시 로그인해주세요');";
		    message += "history.back();";
		    message += " </script>";
		}
		
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/logout.do", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session=request.getSession();
		session.removeAttribute("isLogOn");
		session.removeAttribute("memberVO");
		mav.setViewName("redirect:/main.do");
		return mav;
	}
	*/

	@Override
	@RequestMapping(value="/addMember.do", method = RequestMethod.POST)
	public ResponseEntity<String> addMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			memberVO.setMember_pw(passwordEncoder.encode(memberVO.getMember_pw()));
		    memberService.addMember(memberVO);
		    message  = "<script>";
		    message +=" alert('회원 가입을 마쳤습니다.로그인창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/main.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += " location.href='"+request.getContextPath()+"/member/memberForm.do';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/checkId.do" ,method = RequestMethod.POST)
	public ResponseEntity<String> checkId(@RequestParam("id") String member_id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ResponseEntity<String> resEntity = null;
		String result = memberService.checkID(member_id);
		System.out.println("result : "+result);
		resEntity = new ResponseEntity<String>(result, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/updateMember.do")
	public ResponseEntity<String> updateMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			memberVO.setMember_pw(passwordEncoder.encode(memberVO.getMember_pw()));
		    memberService.updateMember(memberVO);
		    HttpSession session = request.getSession();
		    session.removeAttribute("memberVO");
		    session.setAttribute("memberVO", memberVO);
		    message  = "<script>";
		    message +=" alert('회원 정보 수정을 마쳤습니다.마이페이지로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/mypage/main.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += " location.href='"+request.getContextPath()+"/mypage/updateForm.do';";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

}
