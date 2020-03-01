package com.mycom.warehouse.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycom.warehouse.system.vo.SettingVO;

public interface SystemDao {
	public void add(Map<String, String> map, String sort) throws DataAccessException;
	public void update(Map<String, String> map, String sort) throws DataAccessException;
	public void delete(Map<String, String> map, String sort) throws DataAccessException;
	public Map<String,Object> rateList() throws DataAccessException;
	public List<SettingVO> settingList() throws DataAccessException;
	public Object searchRate(Map<String,String> map) throws DataAccessException;
	public Object selectSystemVO(Map<String,String> map, String sort) throws DataAccessException;
}
