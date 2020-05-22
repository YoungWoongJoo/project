package com.mycom.warehouse.system.vo;

import org.springframework.stereotype.Component;

@Component("storageRateVO")
public class StorageRateVO {
	private int storage_rate_seq_num;
	private String rate_year;
	private String warehouse_region; //창고지역등급
	private String warehouse_rating; //창고등급(일반/저온)
	private String white_rice_rate; //쌀보관료
	private String brown_rice_rate; //현미보관료
	private String rice_rate; //벼보관료
	
	public int getStorage_rate_seq_num() {
		return storage_rate_seq_num;
	}
	public void setStorage_rate_seq_num(int storage_rate_seq_num) {
		this.storage_rate_seq_num = storage_rate_seq_num;
	}
	public String getRate_year() {
		return rate_year;
	}
	public void setRate_year(String rate_year) {
		this.rate_year = rate_year;
	}
	public String getWarehouse_region() {
		return warehouse_region;
	}
	public void setWarehouse_region(String warehouse_region) {
		this.warehouse_region = warehouse_region;
	}
	public String getWarehouse_rating() {
		return warehouse_rating;
	}
	public void setWarehouse_rating(String warehouse_rating) {
		this.warehouse_rating = warehouse_rating;
	}
	public String getWhite_rice_rate() {
		return white_rice_rate;
	}
	public void setWhite_rice_rate(String white_rice_rate) {
		this.white_rice_rate = white_rice_rate;
	}
	public String getBrown_rice_rate() {
		return brown_rice_rate;
	}
	public void setBrown_rice_rate(String brown_rice_rate) {
		this.brown_rice_rate = brown_rice_rate;
	}
	public String getRice_rate() {
		return rice_rate;
	}
	public void setRice_rate(String rice_rate) {
		this.rice_rate = rice_rate;
	}
}
