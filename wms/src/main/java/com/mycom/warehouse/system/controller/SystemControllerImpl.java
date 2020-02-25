package com.mycom.warehouse.system.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.common.Controller.BaseController;
import com.mycom.warehouse.system.service.SystemService;
import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.SettingVO;
import com.mycom.warehouse.system.vo.StorageRateVO;

@Controller("systemController")
@RequestMapping(value="/system")
public class SystemControllerImpl extends BaseController implements SystemController {
	@Autowired
	SystemService systemService;

	@Override
	@RequestMapping(value="/storageRate/add.do")
	public ResponseEntity<String> addStorageRate(@ModelAttribute("StorageRateVO") StorageRateVO storageRateVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.addStorageRate(storageRateVO);
		    message  = "<script>";
		    message +=" alert('보관료 요율 등록을 마쳤습니다.요율 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/rateList.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/storageRate/update.do")
	public ResponseEntity<String> updateStorageRate(@ModelAttribute("StorageRateVO") StorageRateVO storageRateVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.updateStorageRate(storageRateVO);
		    message  = "<script>";
		    message +=" alert('보관료 요율 수정을 마쳤습니다.요율 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/rateList.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/storageRate/delete.do")
	public ResponseEntity<String> deleteStorageRate(@ModelAttribute("StorageRateVO") StorageRateVO storageRateVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.deleteStorageRate(storageRateVO);
		    message  = "<script>";
		    message +=" alert('보관료 요율 삭제를 마쳤습니다.요율 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/rateList.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/cargoRate/add.do")
	public ResponseEntity<String> addCargoRate(@ModelAttribute("CargoRateVO") CargoRateVO cargoRateVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.addCargoRate(cargoRateVO);
		    message  = "<script>";
		    message +=" alert('하역료 요율 등록을 마쳤습니다.요율 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/rateList.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/cargoRate/update.do")
	public ResponseEntity<String> updateCargoRate(@ModelAttribute("CargoRateVO") CargoRateVO cargoRateVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.updateCargoRate(cargoRateVO);
		    message  = "<script>";
		    message +=" alert('하역료 요율 수정을 마쳤습니다.요율 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/rateList.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/cargoRate/delete.do")
	public ResponseEntity<String> deleteCargoRate(@ModelAttribute("CargoRateVO") CargoRateVO cargoRateVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.deleteCargoRate(cargoRateVO);
		    message  = "<script>";
		    message +=" alert('하역료 요율 삭제를 마쳤습니다.요율 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/rateList.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/setting/add.do")
	public ResponseEntity<String> addSetting(@ModelAttribute("settingVO")SettingVO settingVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.addSetting(settingVO);
		    message  = "<script>";
		    message +=" alert('시스템 설정 등록을 마쳤습니다.시스템 설정 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/setting/list.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/setting/update.do")
	public ResponseEntity<String> updateSetting(@ModelAttribute("settingVO")SettingVO settingVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.updateSetting(settingVO);
		    message  = "<script>";
		    message +=" alert('시스템 설정 수정을 마쳤습니다.시스템 설정 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/setting/list.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/setting/delete.do")
	public ResponseEntity<String> deleteSetting(@ModelAttribute("settingVO")SettingVO settingVO, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = null;
		ResponseEntity<String> resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
		    systemService.deleteSetting(settingVO);
		    message  = "<script>";
		    message +=" alert('시스템 설정 삭제를 마쳤습니다.시스템 설정 목록창으로 이동합니다.');";
		    message += " location.href='"+request.getContextPath()+"/system/setting/list.do';";
		    message += " </script>";
		    
		}catch(Exception e) {
			message  = "<script>";
		    message +=" alert('작업 중 오류가 발생했습니다. 다시 시도해 주세요');";
		    message += "history.back();";
		    message += " </script>";
			e.printStackTrace();
		}
		resEntity =new ResponseEntity<String>(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	@Override
	@RequestMapping(value="/rateList.do")
	public ModelAndView rateList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String viewName = (String)session.getAttribute("viewName");
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
