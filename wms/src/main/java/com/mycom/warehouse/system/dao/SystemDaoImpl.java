package com.mycom.warehouse.system.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.SettingVO;
import com.mycom.warehouse.system.vo.StorageRateVO;

@Repository("systemDao")
public class SystemDaoImpl implements SystemDao {
	@Autowired
	SqlSession sqlSession;

	@Override
	public void addStorageRate(StorageRateVO storageRateVO) throws DataAccessException {
		sqlSession.insert("mapper.setting.addStorageRate", storageRateVO);
	}

	@Override
	public void updateStorageRate(StorageRateVO storageRateVO) throws DataAccessException {
		sqlSession.update("mapper.setting.updateStorageRate", storageRateVO);
	}

	@Override
	public void deleteStorageRate(StorageRateVO storageRateVO) throws DataAccessException {
		sqlSession.delete("mapper.setting.deleteStorageRate", storageRateVO);
	}

	@Override
	public void addCargoRate(CargoRateVO cargoRateVO) throws DataAccessException {
		sqlSession.insert("mapper.setting.addCargoRate", cargoRateVO);
	}

	@Override
	public void updateCargoRate(CargoRateVO cargoRateVO) throws DataAccessException {
		sqlSession.update("mapper.setting.updateCargoRate", cargoRateVO);
	}

	@Override
	public void deleteCargoRate(CargoRateVO cargoRateVO) throws DataAccessException {
		sqlSession.delete("mapper.setting.deleteCargoRate", cargoRateVO);
	}

	@Override
	public void addSetting(SettingVO settingVO) throws DataAccessException {
		sqlSession.insert("mapper.setting.addSetting", settingVO);
	}

	@Override
	public void updateSetting(SettingVO settingVO) throws DataAccessException {
		sqlSession.update("mapper.setting.updateSetting", settingVO);
	}

	@Override
	public void deleteSetting(SettingVO settingVO) throws DataAccessException {
		sqlSession.delete("mapper.setting.deleteSetting", settingVO);
	}

	@Override
	public Map<String, Object> rateList() throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StorageRateVO> storageRateList = sqlSession.selectList("mapper.setting.selectStorageRateList");
		List<CargoRateVO> cargoRateList = sqlSession.selectList("mapper.setting.selectCargoRateList");
		map.put("storageRateList", storageRateList);
		map.put("cargoRateList", cargoRateList);
		return map;
	}

	@Override
	public List<SettingVO> settingList() throws DataAccessException {
		return sqlSession.selectList("mapper.setting.selectSettingList");
	}

}
