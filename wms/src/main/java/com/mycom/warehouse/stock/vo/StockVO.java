package com.mycom.warehouse.stock.vo;

import org.springframework.stereotype.Component;

@Component("stockVO")
public class StockVO {
	private int stock_seq_num;
	private String warehouse_name;
	private String stock_year;
	private String stock_sort;
	private String stock_unit;
	private String stock_quantity_40kg;
	private String stock_quantity_bag;
	
	public int getStock_seq_num() {
		return stock_seq_num;
	}
	public void setStock_seq_num(int stock_seq_num) {
		this.stock_seq_num = stock_seq_num;
	}
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public String getStock_year() {
		return stock_year;
	}
	public void setStock_year(String stock_year) {
		this.stock_year = stock_year;
	}
	public String getStock_sort() {
		return stock_sort;
	}
	public void setStock_sort(String stock_sort) {
		this.stock_sort = stock_sort;
	}
	public String getStock_unit() {
		return stock_unit;
	}
	public void setStock_unit(String stock_unit) {
		this.stock_unit = stock_unit;
	}
	public String getStock_quantity_40kg() {
		return stock_quantity_40kg;
	}
	public void setStock_quantity_40kg(String stock_quantity_40kg) {
		this.stock_quantity_40kg = stock_quantity_40kg;
	}
	public String getStock_quantity_bag() {
		return stock_quantity_bag;
	}
	public void setStock_quantity_bag(String stock_quantity_bag) {
		this.stock_quantity_bag = stock_quantity_bag;
	}
}
