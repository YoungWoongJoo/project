package com.mycom.warehouse.stock.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.stock.vo.MonthlyStockVO;
import com.mycom.warehouse.stock.vo.StockVO;

@Repository("stockDao")
public class StockDaoImpl implements StockDao {
	@Autowired
	SqlSession sqlSession;

	@Override
	public void insertNewStock(StockVO stockVO, MonthlyStockVO monthlyStockVO) throws DataAccessException {
		sqlSession.insert("mapper.stock.insertNewStock", stockVO);
		monthlyStockVO.setStock_seq_num(stockVO.getStock_seq_num());
		sqlSession.insert("mapper.stock.insertNewMonthlyStock", monthlyStockVO);
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

	@Override
	public List<String> keywordSearch(String keyword) throws DataAccessException {
		return sqlSession.selectList("mapper.stock.selectKeywordSearch", keyword);
	}

	@Override
	public StockVO selectStock(StockVO stockVO) throws DataAccessException {
		return sqlSession.selectOne("mapper.stock.selectStock", stockVO);
	}

	@Override
	public List<String> selectMonth(String warehouse_name) throws DataAccessException {
		return sqlSession.selectList("mapper.stock.selectMonth", warehouse_name);
	}

}
