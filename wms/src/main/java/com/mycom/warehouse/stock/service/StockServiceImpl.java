package com.mycom.warehouse.stock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.stock.dao.StockDao;
import com.mycom.warehouse.stock.vo.StockVO;

@Service("stockService")
@Transactional(propagation = Propagation.REQUIRED)
public class StockServiceImpl implements StockService {
	@Autowired
	StockDao stockDao;
	
	@Override
	public void register(StockVO stockVO) throws Exception {

		stockDao.insertNewStock(stockVO);
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

}
