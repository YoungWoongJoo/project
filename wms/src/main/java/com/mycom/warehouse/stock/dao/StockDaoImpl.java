package com.mycom.warehouse.stock.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.stock.vo.StockVO;

@Repository("stockDao")
public class StockDaoImpl implements StockDao {
	@Autowired
	SqlSession sqlSession;

	@Override
	public void insertNewStock(StockVO stockVO) throws DataAccessException {
		sqlSession.insert("mapper.stock.insertNewStock", stockVO);
	}

	@Override
	public List<StockVO> selectStockList(String warehouse_name) throws DataAccessException {
		return sqlSession.selectList("mapper.stock.selectStockList", warehouse_name);
	}

	@Override
	public void updateStock(StockVO stockVO) throws DataAccessException {
		sqlSession.insert("mapper.stock.updateStock", stockVO);
		
	}

	@Override
	public void deleteStock(StockVO stockVO) throws DataAccessException {
		sqlSession.delete("mapper.stock.deleteStock", stockVO);
		
	}

	@Override
	public StockVO searchStock(StockVO stockVO) throws DataAccessException {
		stockVO = sqlSession.selectOne("mapper.stock.searchStock", stockVO);
		return stockVO;
	}

}
