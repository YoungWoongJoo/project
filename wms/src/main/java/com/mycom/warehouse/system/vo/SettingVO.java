package com.mycom.warehouse.system.vo;

import org.springframework.stereotype.Component;

@Component("settingVO")
public class SettingVO {
	private String setting_region; //â�� ������(�ñ�����)
	private String setting_chief_rank; //������ġ��ü������
	private String setting_chief_name; //������ġ��ü���̸�
	private String setting_manager_rank; //������������
	public String getSetting_chief_rank() {
		return setting_chief_rank;
	}
	public void setSetting_chief_rank(String setting_chief_rank) {
		this.setting_chief_rank = setting_chief_rank;
	}
	public String getSetting_manager_rank() {
		return setting_manager_rank;
	}
	public void setSetting_manager_rank(String setting_manager_rank) {
		this.setting_manager_rank = setting_manager_rank;
	}
	private String setting_manager_name; //���������̸�
	private String setting_officer_email; //�������email
	
	public String getSetting_region() {
		return setting_region;
	}
	public void setSetting_region(String setting_region) {
		this.setting_region = setting_region;
	}
	public String getSetting_chief_name() {
		return setting_chief_name;
	}
	public void setSetting_chief_name(String setting_chief_name) {
		this.setting_chief_name = setting_chief_name;
	}
	public String getSetting_manager_name() {
		return setting_manager_name;
	}
	public void setSetting_manager_name(String setting_manager_name) {
		this.setting_manager_name = setting_manager_name;
	}
	public String getSetting_officer_email() {
		return setting_officer_email;
	}
	public void setSetting_officer_email(String setting_officer_email) {
		this.setting_officer_email = setting_officer_email;
	}
}
