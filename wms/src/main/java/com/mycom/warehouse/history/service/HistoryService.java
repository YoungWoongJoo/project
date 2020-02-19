package com.mycom.warehouse.history.service;

import java.util.List;

import com.mycom.warehouse.history.vo.HistoryVO;

public interface HistoryService {
	public String insert(HistoryVO historyVO) throws Exception;
	public List<HistoryVO> selectList(HistoryVO historyVO) throws Exception;
}
