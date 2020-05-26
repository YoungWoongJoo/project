package com.mycom.warehouse.bill.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.bill.dao.BillDao;

@Service("billService")
@Transactional(propagation = Propagation.REQUIRED)
public class BillServiceImpl implements BillService {
	@Autowired
	BillDao billDao;

	@Override
	public boolean checkUpdate(Map<String, Object> map) throws Exception {
		return billDao.checkUpdate(map);
	}

	@Override
	public void upToDateStock(Map<String, Object> map) throws Exception {
		billDao.upToDateStock(map);
	}

	@Override
	public Map<String, Object> selectLists(Map<String, Object> map) throws Exception {
		return billDao.selectLists(map);
	}

}
