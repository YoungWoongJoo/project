package com.mycom.warehouse.warehouse.vo;

import org.springframework.stereotype.Component;

@Component("warehouseVO")
public class WarehouseVO {
	private String warehouse_name;
	private String warehouse_owner;
	private String warehouse_code;
	private String warehouse_region;
	private String warehouse_rating;
	private String warehouse_region_name;
	private String member_id;
	
	public String getWarehouse_name() {
		return warehouse_name;
	}
	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}
	public String getWarehouse_owner() {
		return warehouse_owner;
	}
	public void setWarehouse_owner(String warehouse_owner) {
		this.warehouse_owner = warehouse_owner;
	}
	public String getWarehouse_code() {
		return warehouse_code;
	}
	public void setWarehouse_code(String warehouse_code) {
		this.warehouse_code = warehouse_code;
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
	public String getWarehouse_region_name() {
		return warehouse_region_name;
	}
	public void setWarehouse_region_name(String warehouse_region_name) {
		this.warehouse_region_name = warehouse_region_name;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
	
}
