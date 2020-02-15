package com.mycom.warehouse.stock.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycom.warehouse.stock.vo.StockVO;

public interface StockDao {
	public void insertNewStock(StockVO stockVO) throws DataAccessException;
	public List<StockVO> selectStockList(String warehouse_name) throws DataAccessException;
}
