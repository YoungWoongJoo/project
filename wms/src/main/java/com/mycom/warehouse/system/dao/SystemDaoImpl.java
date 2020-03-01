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
	public void add(Map<String, String> map, String sort) throws DataAccessException {
		sqlSession.insert("mapper.setting.add"+sort, map);
	}

	@Override
	public void update(Map<String, String> map, String sort) throws DataAccessException {
		sqlSession.update("mapper.setting.update"+sort, map);
	}

	@Override
	public void delete(Map<String, String> map, String sort) throws DataAccessException {
		sqlSession.delete("mapper.setting.delete"+sort, map);
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

	@Override
	public Object searchRate(Map<String, String> map) throws DataAccessException {
		if(map.containsKey("storage_rate_seq_num"))
		{
			String storage_rate_seq_num = map.get("storage_rate_seq_num");
			return sqlSession.selectOne("mapper.setting.searchStorageRate", storage_rate_seq_num);
		}
		else
		{
			String cargo_rate_seq_num = map.get("cargo_rate_seq_num");
			return sqlSession.selectOne("mapper.setting.searchCargoRate", cargo_rate_seq_num);
		}
	}

	@Override
	public Object selectSystemVO(Map<String, String> map, String sort) throws DataAccessException {
		return sqlSession.selectOne("mapper.setting.selectOne"+sort, map);
	}

}
