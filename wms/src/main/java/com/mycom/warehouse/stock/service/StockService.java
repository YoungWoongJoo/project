package com.mycom.warehouse.stock.service;

import java.util.List;

import com.mycom.warehouse.stock.vo.StockVO;

public interface StockService {
	public void register(StockVO stockVO) throws Exception;
	public List<StockVO> list(String warehouse_name) throws Exception;
	public void update(StockVO stockVO) throws Exception;
	public void delete(StockVO stockVO) throws Exception;
	public StockVO search(StockVO stockVO) throws Exception;
	public List<String> keywordSearch(String keyword) throws Exception;
}
