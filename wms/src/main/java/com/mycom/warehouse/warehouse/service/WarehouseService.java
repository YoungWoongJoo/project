package com.mycom.warehouse.warehouse.service;

import java.util.List;

import com.mycom.warehouse.warehouse.vo.WarehouseVO;

public interface WarehouseService {
	public List<WarehouseVO> listWarehouse(String member_id) throws Exception;
	public WarehouseVO searchWarehouse(String warehouse_name) throws Exception;
	public void register(WarehouseVO warehouseVO) throws Exception;
	public void update(WarehouseVO warehouseVO) throws Exception;
	public void delete(WarehouseVO warehouseVO) throws Exception;
}
