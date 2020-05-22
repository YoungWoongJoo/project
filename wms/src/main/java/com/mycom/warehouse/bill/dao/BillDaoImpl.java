package com.mycom.warehouse.bill.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.history.vo.HistoryVO;
import com.mycom.warehouse.stock.vo.MonthlyStockVO;
import com.mycom.warehouse.stock.vo.StockVO;
import com.mycom.warehouse.system.vo.CargoRateVO;
import com.mycom.warehouse.system.vo.StorageRateVO;
import com.mycom.warehouse.warehouse.vo.WarehouseVO;

@Repository("billDao")
public class BillDaoImpl implements BillDao {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	StorageRateVO storageRateVO;
	@Autowired
	WarehouseVO warehouseVO;

	@Override
	public boolean checkUpdate(Map<String, Object> map) throws DataAccessException {
		if((int)sqlSession.selectOne("mapper.bill.CheckUpdateHistory", map)>0 || (int)sqlSession.selectOne("mapper.bill.CheckUpdateMonthlyStock", map)==0)
			return false;
		else
			return true;
	}

	@Override
	public void upToDateStock(Map<String, Object> map) throws DataAccessException {
		sqlSession.update("mapper.bill.updateHistory", map);
		List<StockVO> stockList = sqlSession.selectList("mapper.bill.stockList", map);
		sqlSession.delete("mapper.bill.deleteMonthlyStock", map);
		map.put("stockList", stockList);
		sqlSession.insert("mapper.bill.insertMonthlyStock", map);
		map.remove("stockList");
	}

	@Override
	public Map<String, Object> selectLists(Map<String, Object> map) throws DataAccessException {
		Map<String,Object> listMap = new HashMap<String, Object>();
		List<MonthlyStockVO> monthlyStockList = sqlSession.selectList("mapper.bill.selectmonthlyStockList", map);
		List<HistoryVO> historyList = sqlSession.selectList("mapper.bill.selecthistoryList", map);
		String stock_month = (String)map.get("stock_month");
		String rate_year;
		int year = Integer.parseInt(stock_month.split("-")[0]);
		int month = Integer.parseInt(stock_month.split("-")[1]);
		if(year%2!=0)//홀수년도
			year -= 1;
		else//짝수년도
		{
			if(month==1)//짝수년도 1월 청구서 작성시(요율최신화 전) 2년전 요율로 작성
				year -= 2;
		}
		rate_year = Integer.toString(year);
		map.put("rate_year", rate_year);
		String warehouse_name = (String)map.get("warehouse_name");
		warehouseVO = sqlSession.selectOne("mapper.warehouse.searchWarehouse", warehouse_name);
		map.put("warehouse_region", warehouseVO.getWarehouse_region());
		map.put("warehouse_rating", warehouseVO.getWarehouse_rating());
		storageRateVO = sqlSession.selectOne("mapper.setting.searchStorageRateList", map);
		map.remove("warehouse_region");
		map.remove("warehouse_rating");
		List<CargoRateVO> cargoRateList = sqlSession.selectList("mapper.setting.searchCargoRateList", map);
		map.remove("rate_year");
		listMap.put("monthlyStockList", monthlyStockList);
		listMap.put("historyList", historyList);
		listMap.put("storageRateVO", storageRateVO);
		listMap.put("cargoRateList", cargoRateList);
		return listMap;
	}

}
