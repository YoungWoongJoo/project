package com.mycom.warehouse.history.vo;

import org.springframework.stereotype.Component;

@Component("historyVO")
public class HistoryVO {
	private int history_seq_num;
	private int stock_seq_num;
	private String warehouse_name;
	private String history_date;
	private String stock_year;
	private String stock_sort1;
	private String stock_sort2;
	private String stock_unit;
	private String history_sort1;
	private String history_sort2;
	private String stock_prev;
	private String history_quantity;
	private String stock_present;
	private String history_state;
	
	public int getHistory_seq_num() {
		return history_seq_num;
	}
	public void setHistory_seq_num(int history_seq_num) {
		this.history_seq_num = history_seq_num;
	}
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
	public String getHistory_date() {
		return history_date;
	}
	public void setHistory_date(String history_date) {
		this.history_date = history_date;
	}
	public String getStock_year() {
		return stock_year;
	}
	public void setStock_year(String stock_year) {
		this.stock_year = stock_year;
	}
	public String getStock_sort1() {
		return stock_sort1;
	}
	public void setStock_sort1(String stock_sort1) {
		this.stock_sort1 = stock_sort1;
	}
	public String getStock_sort2() {
		return stock_sort2;
	}
	public void setStock_sort2(String stock_sort2) {
		this.stock_sort2 = stock_sort2;
	}
	public String getStock_unit() {
		return stock_unit;
	}
	public void setStock_unit(String stock_unit) {
		this.stock_unit = stock_unit;
	}
	public String getHistory_sort1() {
		return history_sort1;
	}
	public void setHistory_sort1(String history_sort1) {
		this.history_sort1 = history_sort1;
	}
	public String getHistory_sort2() {
		return history_sort2;
	}
	public void setHistory_sort2(String history_sort2) {
		this.history_sort2 = history_sort2;
	}
	public String getStock_prev() {
		return stock_prev;
	}
	public void setStock_prev(String stock_prev) {
		this.stock_prev = stock_prev;
	}
	public String getHistory_quantity() {
		return history_quantity;
	}
	public void setHistory_quantity(String history_quantity) {
		this.history_quantity = history_quantity;
	}
	public String getStock_present() {
		return stock_present;
	}
	public void setStock_present(String stock_present) {
		this.stock_present = stock_present;
	}
	public String getHistory_state() {
		return history_state;
	}
	public void setHistory_state(String history_state) {
		this.history_state = history_state;
	}
}
