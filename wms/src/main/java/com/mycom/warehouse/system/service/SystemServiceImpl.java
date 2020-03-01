package com.mycom.warehouse.system.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.system.dao.SystemDao;
import com.mycom.warehouse.system.vo.SettingVO;

@Service("systemService")
@Transactional(propagation = Propagation.REQUIRED)
public class SystemServiceImpl implements SystemService {
	@Autowired
	SystemDao systemDao;

	@Override
	public void add(Map<String, String> map, String sort) throws Exception {
		systemDao.add(map, sort);
	}

	@Override
	public void update(Map<String, String> map, String sort) throws Exception {
		systemDao.update(map, sort);
	}

	@Override
	public void delete(Map<String, String> map, String sort) throws Exception {
		systemDao.delete(map, sort);
	}

	@Override
	public Map<String, Object> rateList() throws Exception {
		return systemDao.rateList();
	}

	@Override
	public List<SettingVO> settingList() throws Exception {
		return systemDao.settingList();
	}

	@Override
	public Object searchRate(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return systemDao.searchRate(map);
	}

	@Override
	public Object selectSystemVO(Map<String, String> map, String sort) throws Exception {
		return systemDao.selectSystemVO(map, sort);
	}

}
