package com.mycom.warehouse.warehouse.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Repository("warehouseDao")
public class WarehouseDaoImpl implements WarehouseDao{
	@Autowired
	SqlSession sqlSession;
	@Autowired
	WarehouseVO warehouseVO;
	
	@Override
	public List<WarehouseVO> selectWarehouseList(String member_id) throws DataAccessException {
		List<WarehouseVO> list = new ArrayList<WarehouseVO>();
		list = sqlSession.selectList("mapper.warehouse.selectWarehouseList",member_id);
		return list;
	}
	
	@Override
	public WarehouseVO searchWarehouse(String warehouse_name) throws DataAccessException {
		warehouseVO = sqlSession.selectOne("mapper.warehouse.searchWarehouse", warehouse_name);
		return warehouseVO;
	}

	@Override
	public void insertNewWarehouse(WarehouseVO warehouseVO) throws DataAccessException {
		sqlSession.insert("mapper.warehouse.insertNewWarehouse", warehouseVO);
		return;
	}

	@Override
	public void updateWarehouse(WarehouseVO warehouseVO) throws DataAccessException {
		sqlSession.update("mapper.warehouse.updateWarehouse", warehouseVO);
		return;
	}

	@Override
	public void deleteWarehouse(WarehouseVO warehouseVO) throws DataAccessException {
		sqlSession.delete("mapper.warehouse.deleteWarehouse", warehouseVO);
		return;
	}

}
