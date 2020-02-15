package com.mycom.warehouse.stock.vo;

import org.springframework.stereotype.Component;

@Component("stockVOs")
public class StockVOs {
	private StockVO[] stockVO;

	public StockVO[] getStockVO() {
		return stockVO;
	}

	public void setStockVO(StockVO[] stockVO) {
		this.stockVO = stockVO;
	}
	
	
}
