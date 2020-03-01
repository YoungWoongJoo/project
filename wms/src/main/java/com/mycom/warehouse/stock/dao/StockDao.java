package com.mycom.warehouse.stock.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycom.warehouse.stock.vo.StockVO;

public interface StockDao {
	public void insertNewStock(StockVO stockVO) throws DataAccessException;
	public List<StockVO> selectStockList(String warehouse_name) throws DataAccessException;
	public void updateStock(StockVO stockVO) throws DataAccessException;
	public void deleteStock(StockVO stockVO) throws DataAccessException;
	public StockVO searchStock(StockVO stockVO) throws DataAccessException;
	public List<String> keywordSearch(String keyword) throws DataAccessException;
	public StockVO selectStock(StockVO stockVO) throws DataAccessException;
}
