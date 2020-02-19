package com.mycom.warehouse.history.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.history.dao.HistoryDao;
import com.mycom.warehouse.history.vo.HistoryVO;

@Service("historyService")
@Transactional(propagation = Propagation.REQUIRED)
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	HistoryDao historyDao;

	@Override
	public String insert(HistoryVO historyVO) throws Exception {
		return historyDao.insertNewHistory(historyVO);
	}

	@Override
	public List<HistoryVO> selectList(HistoryVO historyVO) throws Exception {
		// TODO Auto-generated method stub
		return historyDao.selectList(historyVO);
	}

}
