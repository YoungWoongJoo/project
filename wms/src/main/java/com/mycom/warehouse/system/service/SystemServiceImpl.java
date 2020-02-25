package com.mycom.warehouse.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.system.dao.SystemDao;
import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.SettingVO;
import com.mycom.warehouse.system.vo.StorageRateVO;

@Service("systemService")
@Transactional(propagation = Propagation.REQUIRED)
public class SystemServiceImpl implements SystemService {
	@Autowired
	SystemDao systemDao;

	@Override
	public void addStorageRate(StorageRateVO storageRateVO) throws Exception {
		systemDao.addStorageRate(storageRateVO);
	}

	@Override
	public void updateStorageRate(StorageRateVO storageRateVO) throws Exception {
		systemDao.updateStorageRate(storageRateVO);
	}

	@Override
	public void deleteStorageRate(StorageRateVO storageRateVO) throws Exception {
		systemDao.deleteStorageRate(storageRateVO);
	}

	@Override
	public void addCargoRate(CargoRateVO cargoRateVO) throws Exception {
		systemDao.addCargoRate(cargoRateVO);
	}

	@Override
	public void updateCargoRate(CargoRateVO cargoRateVO) throws Exception {
		systemDao.updateCargoRate(cargoRateVO);
	}

	@Override
	public void deleteCargoRate(CargoRateVO cargoRateVO) throws Exception {
		systemDao.deleteCargoRate(cargoRateVO);
	}

	@Override
	public void addSetting(SettingVO settingVO) throws Exception {
		systemDao.addSetting(settingVO);

	}

	@Override
	public void updateSetting(SettingVO settingVO) throws Exception {
		systemDao.updateSetting(settingVO);
	}

	@Override
	public void deleteSetting(SettingVO settingVO) throws Exception {
		systemDao.deleteSetting(settingVO);
	}

	@Override
	public Map<String, Object> rateList() throws Exception {
		return systemDao.rateList();
	}

	@Override
	public List<SettingVO> settingList() throws Exception {
		return systemDao.settingList();
	}

}
