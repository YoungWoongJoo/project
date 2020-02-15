package com.mycom.warehouse.warehouse.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycom.warehouse.warehouse.vo.WarehouseVO;

public interface WarehouseDao {
	public List<WarehouseVO> selectWarehouseList(String member_id) throws DataAccessException;
	public WarehouseVO searchWarehouse(String warehouse_name) throws DataAccessException;
	public void insertNewWarehouse(WarehouseVO warehouseVO) throws DataAccessException;
	public void updateWarehouse(WarehouseVO warehouseVO) throws DataAccessException;
	public void deleteWarehouse(WarehouseVO warehouseVO) throws DataAccessException;
}
