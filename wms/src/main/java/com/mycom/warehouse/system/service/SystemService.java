package com.mycom.warehouse.system.service;

import java.util.List;
import java.util.Map;

import com.mycom.warehouse.system.vo.SettingVO;

public interface SystemService {
	public void add(Map<String, String> map, String sort) throws Exception;
	public void update(Map<String, String> map, String sort) throws Exception;
	public void delete(Map<String, String> map, String sort) throws Exception;
	public Map<String,Object> rateList() throws Exception;
	public List<SettingVO> settingList() throws Exception;
	public Object searchRate(Map<String, String> map) throws Exception;
}
