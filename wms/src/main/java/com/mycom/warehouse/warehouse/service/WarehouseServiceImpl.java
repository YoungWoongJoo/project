package com.mycom.warehouse.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.warehouse.dao.WarehouseDao;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Service("warehouseService")
@Transactional(propagation = Propagation.REQUIRED)
public class WarehouseServiceImpl implements WarehouseService {
	@Autowired
	WarehouseDao warehouseDao;
	@Autowired
	WarehouseVO warehouseVO;
	
	@Override
	public List<WarehouseVO> listWarehouse(String member_id) throws Exception {
		List<WarehouseVO> list = warehouseDao.selectWarehouseList(member_id); 
		return list;
	}
	
	@Override
	public WarehouseVO searchWarehouse(String warehouse_name) throws Exception {
		warehouseVO = warehouseDao.searchWarehouse(warehouse_name);
		return warehouseVO;
	}


	@Override
	public void register(WarehouseVO warehouseVO) throws Exception {
		warehouseDao.insertNewWarehouse(warehouseVO);
		return;
	}

	@Override
	public void update(WarehouseVO warehouseVO) throws Exception {
		warehouseDao.updateWarehouse(warehouseVO);
		return;
	}

	@Override
	public void delete(WarehouseVO warehouseVO) throws Exception {
		warehouseDao.deleteWarehouse(warehouseVO);
		return;
	}

}
