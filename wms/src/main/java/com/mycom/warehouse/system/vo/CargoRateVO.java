package com.mycom.warehouse.system.vo;

import org.springframework.stereotype.Component;

@Component("cargoRateVO")
public class CargoRateVO {
	private int cargo_rate_seq_num;
	private String rate_year;
	private String wrap_sort; //단량(포장재 종류)
	private String load_rate; //상차료
	private String unload_rate; //하차료
	private String warehousing_rate; //입고료
	private String release_rate; //출고료
	private String purchase_warehousing_rate; //매입장소입고료
	private String purchase_load_rate; //매입직송상차료
	private String security_rate; //매입장소경비료
	private String transfer_rate; //이적료
	private String migration_20m_rate; //이송료20m초과50m까지
	private String migration_50m_rate; //이송료50m초과20m마다
	private String bag_purchase_rate; //톤백매입료
	
	public int getCargo_rate_seq_num() {
		return cargo_rate_seq_num;
	}
	public void setCargo_rate_seq_num(int cargo_rate_seq_num) {
		this.cargo_rate_seq_num = cargo_rate_seq_num;
	}
	public String getRate_year() {
		return rate_year;
	}
	public void setRate_year(String rate_year) {
		this.rate_year = rate_year;
	}
	public String getWrap_sort() {
		return wrap_sort;
	}
	public void setWrap_sort(String wrap_sort) {
		this.wrap_sort = wrap_sort;
	}
	public String getLoad_rate() {
		return load_rate;
	}
	public void setLoad_rate(String load_rate) {
		this.load_rate = load_rate;
	}
	public String getUnload_rate() {
		return unload_rate;
	}
	public void setUnload_rate(String unload_rate) {
		this.unload_rate = unload_rate;
	}
	public String getWarehousing_rate() {
		return warehousing_rate;
	}
	public void setWarehousing_rate(String warehousing_rate) {
		this.warehousing_rate = warehousing_rate;
	}
	public String getRelease_rate() {
		return release_rate;
	}
	public void setRelease_rate(String release_rate) {
		this.release_rate = release_rate;
	}
	public String getPurchase_warehousing_rate() {
		return purchase_warehousing_rate;
	}
	public void setPurchase_warehousing_rate(String purchase_warehousing_rate) {
		this.purchase_warehousing_rate = purchase_warehousing_rate;
	}
	public String getPurchase_load_rate() {
		return purchase_load_rate;
	}
	public void setPurchase_load_rate(String purchase_load_rate) {
		this.purchase_load_rate = purchase_load_rate;
	}
	public String getSecurity_rate() {
		return security_rate;
	}
	public void setSecurity_rate(String security_rate) {
		this.security_rate = security_rate;
	}
	public String getTransfer_rate() {
		return transfer_rate;
	}
	public void setTransfer_rate(String transfer_rate) {
		this.transfer_rate = transfer_rate;
	}
	public String getMigration_20m_rate() {
		return migration_20m_rate;
	}
	public void setMigration_20m_rate(String migration_20m_rate) {
		this.migration_20m_rate = migration_20m_rate;
	}
	public String getMigration_50m_rate() {
		return migration_50m_rate;
	}
	public void setMigration_50m_rate(String migration_50m_rate) {
		this.migration_50m_rate = migration_50m_rate;
	}
	public String getBag_purchase_rate() {
		return bag_purchase_rate;
	}
	public void setBag_purchase_rate(String bag_purchase_rate) {
		this.bag_purchase_rate = bag_purchase_rate;
	}
}
