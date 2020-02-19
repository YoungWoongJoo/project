package com.mycom.warehouse.history.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycom.warehouse.history.vo.HistoryVO;

public interface HistoryDao {
	public String insertNewHistory(HistoryVO historyVO) throws DataAccessException;
	public List<HistoryVO> selectList(HistoryVO historyVO) throws DataAccessException;
}
