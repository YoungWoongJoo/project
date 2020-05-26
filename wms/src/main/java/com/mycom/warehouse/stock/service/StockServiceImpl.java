package com.mycom.warehouse.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.stock.dao.StockDao;
import com.mycom.warehouse.stock.vo.MonthlyStockVO;
import com.mycom.warehouse.stock.vo.StockVO;

@Service("stockService")
@Transactional(propagation = Propagation.REQUIRED)
public class StockServiceImpl implements StockService {
	@Autowired
	StockDao stockDao;
	
	@Override
	public void register(StockVO stockVO, MonthlyStockVO monthlyStockVO) throws Exception {

		stockDao.insertNewStock(stockVO, monthlyStockVO);
	}

	@Override
	public List<StockVO> list(String warehouse_name) throws Exception {
		return stockDao.selectStockList(warehouse_name);
	}

	@Override
	public void update(StockVO stockVO) throws Exception {
		stockDao.updateStock(stockVO);
		
	}

	@Override
	public void delete(StockVO stockVO) throws Exception {
		stockDao.deleteStock(stockVO);
		
	}

	@Override
	public StockVO search(StockVO stockVO) throws Exception {
		stockVO = stockDao.searchStock(stockVO);
		return stockVO;
	}

	@Override
	public List<String> keywordSearch(String keyword) throws Exception {
		return stockDao.keywordSearch(keyword);
	}

	@Override
	public StockVO selectStock(StockVO stockVO) throws Exception {
		return stockDao.selectStock(stockVO);
	}

	@Override
	public List<String> selectMonth(String warehouse_name) throws Exception {
		return stockDao.selectMonth(warehouse_name);
	}

}
