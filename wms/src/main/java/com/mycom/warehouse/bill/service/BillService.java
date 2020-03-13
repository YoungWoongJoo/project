package com.mycom.warehouse.bill.service;

import java.util.Map;

public interface BillService {
	public boolean checkUpdate(Map<String,Object> map) throws Exception;
	public void upToDateStock(Map<String,Object> map) throws Exception;
	public Map<String,Object> calc(Map<String,Object> map) throws Exception;
	public Map<String,Object> selectLists(Map<String, Object> map) throws Exception;
}
