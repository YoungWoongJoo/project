package com.mycom.warehouse.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.SettingVO;
import com.mycom.warehouse.system.vo.StorageRateVO;

public interface SystemDao {
	public void addStorageRate(StorageRateVO storageRateVO) throws DataAccessException;
	public void updateStorageRate(StorageRateVO storageRateVO) throws DataAccessException;
	public void deleteStorageRate(StorageRateVO storageRateVO) throws DataAccessException;
	public void addCargoRate(CargoRateVO cargoRateVO) throws DataAccessException;
	public void updateCargoRate(CargoRateVO cargoRateVO) throws DataAccessException;
	public void deleteCargoRate(CargoRateVO cargoRateVO) throws DataAccessException;
	public void addSetting(SettingVO settingVO) throws DataAccessException;
	public void updateSetting(SettingVO settingVO) throws DataAccessException;
	public void deleteSetting(SettingVO settingVO) throws DataAccessException;
	public Map<String,Object> rateList() throws DataAccessException;
	public List<SettingVO> settingList() throws DataAccessException;
}
