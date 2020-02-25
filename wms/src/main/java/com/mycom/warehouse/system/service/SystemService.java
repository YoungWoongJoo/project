package com.mycom.warehouse.system.service;

import java.util.List;
import java.util.Map;

import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.SettingVO;
import com.mycom.warehouse.system.vo.StorageRateVO;

public interface SystemService {
	public void addStorageRate(StorageRateVO storageRateVO) throws Exception;
	public void updateStorageRate(StorageRateVO storageRateVO) throws Exception;
	public void deleteStorageRate(StorageRateVO storageRateVO) throws Exception;
	public void addCargoRate(CargoRateVO cargoRateVO) throws Exception;
	public void updateCargoRate(CargoRateVO cargoRateVO) throws Exception;
	public void deleteCargoRate(CargoRateVO cargoRateVO) throws Exception;
	public void addSetting(SettingVO settingVO) throws Exception;
	public void updateSetting(SettingVO settingVO) throws Exception;
	public void deleteSetting(SettingVO settingVO) throws Exception;
	public Map<String,Object> rateList() throws Exception;
	public List<SettingVO> settingList() throws Exception;
}
