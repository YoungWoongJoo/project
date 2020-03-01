package com.mycom.warehouse.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.system.service.SystemService;
import com.mycom.warehouse.system.vo.SettingVO;

@Controller("systemController")
@RequestMapping(value="/system")
public class SystemControllerImpl extends BaseController implements SystemController {
	@Autowired
	SystemService systemService;


	@Override
	@RequestMapping(value="/{sort}/add.do")
	public ResponseEntity<String> systemAdd(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String msg = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		String msgSort = null;
		switch(sort)
		{
		case "setting":
			msgSort = "�ý��� ���� ";
			break;
		default:
			msgSort = "���� ���� ";
		}
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			systemService.add(map, sort);
			msg  = "<script>";
			msg +=" alert('"+msgSort+"����� �Ϸ��߽��ϴ�. "+msgSort+"Ȯ��â���� �̵��մϴ�.');";
			if(sort.equals("setting"))
				msg += "location.href='"+request.getContextPath()+"/system/setting/list.do';";
			else
				msg += "location.href='"+request.getContextPath()+"/system/rate/list.do';";
			msg += " </script>";
		}
		catch(Exception e) {
			msg  = "<script>";
			msg +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			msg += " history.back();";
			msg += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/{sort}/update.do")
	public ResponseEntity<String> systemUpdate(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String msg = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		String msgSort = null;
		switch(sort)
		{
		case "setting":
			msgSort = "�ý��� ���� ";
			break;
		default:
			msgSort = "���� ���� ";
		}
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			systemService.update(map, sort);
			msg  = "<script>";
			msg +=" alert('"+msgSort+"������ �Ϸ��߽��ϴ�. "+msgSort+"Ȯ��â���� �̵��մϴ�.');";
			if(sort.equals("setting"))
				msg += "location.href='"+request.getContextPath()+"/system/setting/list.do';";
			else
				msg += "location.href='"+request.getContextPath()+"/system/rate/list.do';";
			msg += " </script>";
		}
		catch(Exception e) {
			msg  = "<script>";
			msg +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			msg += " history.back();";
			msg += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/{sort}/delete.do")
	public ResponseEntity<String> systemDelete(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String msg = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		String msgSort = null;
		switch(sort)
		{
		case "setting":
			msgSort = "�ý��� ���� ";
			break;
		default:
			msgSort = "���� ���� ";
		}
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			systemService.delete(map, sort);
			msg  = "<script>";
			msg +=" alert('"+msgSort+"������ �Ϸ��߽��ϴ�. "+msgSort+"Ȯ��â���� �̵��մϴ�.');";
			if(sort.equals("setting"))
				msg += "location.href='"+request.getContextPath()+"/system/setting/list.do';";
			else
				msg += "location.href='"+request.getContextPath()+"/system/rate/list.do';";
			msg += " </script>";
		}
		catch(Exception e) {
			msg  = "<script>";
			msg +=" alert('�۾� �� ������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			msg += " history.back();";
			msg += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/{sort}/updateForm.do")
	public ModelAndView updateForm(@RequestParam Map<String, String> map, @PathVariable String sort, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		if(sort.equals("setting"))
			mav.addObject(sort+"VO", map);
		else {
			Object obj = systemService.searchRate(map);
			if(map.containsKey("storage_rate_seq_num"))
			{
				mav.addObject("storageRateVO", obj);
			}
			else
			{
				mav.addObject("cargoRateVO", obj);
			}
		}
		return mav;
	}

	@Override
	@RequestMapping(value="/rate/list.do")
	public ModelAndView rateList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		Map<String, Object> listMap = systemService.rateList();
		mav.addObject("listMap", listMap);
		return mav;
	}

	@Override
	@RequestMapping(value="/setting/list.do")
	public ModelAndView settingList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		List<SettingVO> settingList = systemService.settingList();
		mav.addObject("settingList", settingList);
		return mav;
	}
}
