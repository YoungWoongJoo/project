package com.mycom.warehouse.history.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.history.vo.HistoryVO;
import com.mycom.warehouse.stock.vo.StockVO;

@Repository("historyDao")
public class HistoryDaoImpl implements HistoryDao {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	StockVO stockVO;

	@Override
	public String insertNewHistory(HistoryVO historyVO) throws DataAccessException {
		stockVO = sqlSession.selectOne("mapper.history.selectStock", historyVO);//재고검색
		String history_sort = historyVO.getHistory_sort1();
		String prev_quantity;
		Double present_quantity;
		String stock_quantity_40kg;
		String[] quantity;
		int unit;
		double bag;
		String msg;
		switch(history_sort)
		{
		case "입고":
			if(stockVO==null)//새로 입고되는 경우(창고에 없는 종류의 재고가 입고)
			{
				historyVO.setStock_prev("0");
				sqlSession.insert("mapper.history.insertNewStock", historyVO);
				stockVO = new StockVO();
				stockVO.setStock_seq_num(historyVO.getStock_seq_num());
			}
			else//기존 재고에 추가 입고되는 경우
			{
				historyVO.setStock_prev(stockVO.getStock_quantity_40kg());
				historyVO.setStock_seq_num(stockVO.getStock_seq_num());
			}
			prev_quantity = historyVO.getStock_prev();
			present_quantity = Double.parseDouble(prev_quantity) + Double.parseDouble(historyVO.getHistory_quantity());
			historyVO.setStock_present(present_quantity.toString());
			stockVO.setStock_quantity_40kg(present_quantity.toString());
			
			//단량별 포대 개수 계산 시작
			stock_quantity_40kg = stockVO.getStock_quantity_40kg();
			quantity = stock_quantity_40kg.split("."); //짜투리 확인 quantity[0]은 40kg포대개수, quantity[1]은 짜투리(kg)
			unit = Integer.parseInt(historyVO.getStock_unit());
			
			if(quantity==null||quantity.length<2)//수량이 정수일 경우(짜투리가 없을경우)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //수량이 실수일경우 (짜투리가 있을경우)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*(double)unit)+Double.parseDouble(quantity[1]);//총kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			stockVO.setStock_quantity_bag(Integer.toString((int)bag));
			//단량별 포대 개수 계산 끝
			
			sqlSession.update("mapper.history.updateStock", stockVO);
			sqlSession.insert("mapper.history.insertNewHistory", historyVO);
			msg = "재고 관리를 마쳤습니다. 재고 관리 내역 창으로 이동합니다.";
			break;
		case "출고":
			if(stockVO==null)
			{
				msg = "창고에 입력하신 재고가 없습니다. 다시 시도해주세요.";
				break;
			}
			historyVO.setStock_seq_num(stockVO.getStock_seq_num());
			historyVO.setStock_prev(stockVO.getStock_quantity_40kg());
			prev_quantity = historyVO.getStock_prev();
			present_quantity = Double.parseDouble(prev_quantity) - Double.parseDouble(historyVO.getHistory_quantity());
			if(present_quantity<0)
			{
				msg = "입력하신 출고량이 이월량보다 큽니다. 다시 시도해주세요.";
				break;
			}
			historyVO.setStock_present(present_quantity.toString());
			stockVO.setStock_quantity_40kg(present_quantity.toString());
			
			//단량별 포대 개수 계산 시작
			stock_quantity_40kg = stockVO.getStock_quantity_40kg();
			quantity = stock_quantity_40kg.split("."); //짜투리 확인 quantity[0]은 40kg포대개수, quantity[1]은 짜투리(kg)
			unit = Integer.parseInt(historyVO.getStock_unit());
			
			if(quantity==null||quantity.length<2)//수량이 정수일 경우(짜투리가 없을경우)
			{
				bag = (Double.parseDouble(stock_quantity_40kg)*40)/(double)unit;
				bag = Math.ceil(bag);
			}
			else //수량이 실수일경우 (짜투리가 있을경우)
			{
				double total;
				total = (Double.parseDouble(quantity[0])*(double)unit)+Double.parseDouble(quantity[1]);//총kg
				bag = total/(double)unit;
				bag = Math.ceil(bag);
			}
			stockVO.setStock_quantity_bag(Integer.toString((int)bag));
			//단량별 포대 개수 계산 끝
			
			sqlSession.update("mapper.history.updateStock", stockVO);
			sqlSession.insert("mapper.history.insertNewHistory", historyVO);
			msg = "재고 관리를 마쳤습니다. 재고 관리 내역 창으로 이동합니다.";
			break;
		default : //이적
			if(stockVO==null)
			{
				msg = "창고에 입력하신 재고가 없습니다. 다시 시도해주세요.";
				break;
			}
			if(Double.parseDouble(stockVO.getStock_quantity_40kg())<Double.parseDouble(historyVO.getHistory_quantity()))
			{
				msg = "입력하신 이적량이 이월량보다 큽니다. 다시 시도해주세요.";
				break;
			}
			historyVO.setStock_seq_num(stockVO.getStock_seq_num());
			historyVO.setStock_prev(stockVO.getStock_quantity_40kg());
			historyVO.setStock_present(stockVO.getStock_quantity_40kg());
			sqlSession.update("mapper.history.updateStock", stockVO);
			sqlSession.insert("mapper.history.insertNewHistory", historyVO);
			msg = "재고 관리를 마쳤습니다. 재고 관리 내역 창으로 이동합니다.";
		}
		return msg;		
	}

	@Override
	public List<HistoryVO> selectList(HistoryVO historyVO) throws DataAccessException {
		return sqlSession.selectList("mapper.history.selectList", historyVO);
	}

}
