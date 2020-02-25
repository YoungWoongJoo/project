package com.mycom.warehouse.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.SettingVO;
import com.mycom.warehouse.system.vo.StorageRateVO;

public interface SystemController {
	public ResponseEntity<String> addStorageRate(@ModelAttribute("storageRateVO")StorageRateVO storageRateVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> updateStorageRate(@ModelAttribute("storageRateVO")StorageRateVO storageRateVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> deleteStorageRate(@ModelAttribute("storageRateVO")StorageRateVO storageRateVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> addCargoRate(@ModelAttribute("cargoRateVO")CargoRateVO cargoRateVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> updateCargoRate(@ModelAttribute("cargoRateVO")CargoRateVO cargoRateVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> deleteCargoRate(@ModelAttribute("cargoRateVO")CargoRateVO cargoRateVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> addSetting(@ModelAttribute("settingVO")SettingVO settingVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> updateSetting(@ModelAttribute("settingVO")SettingVO settingVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> deleteSetting(@ModelAttribute("settingVO")SettingVO settingVO, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView rateList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView settingList(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
