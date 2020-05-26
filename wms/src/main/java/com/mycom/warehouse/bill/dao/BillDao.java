package com.mycom.warehouse.bill.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BillDao {
	public boolean checkUpdate(Map<String,Object> map) throws DataAccessException;
	public void upToDateStock(Map<String,Object> map) throws DataAccessException;
	public Map<String,Object> selectLists(Map<String,Object> map) throws DataAccessException;
}
