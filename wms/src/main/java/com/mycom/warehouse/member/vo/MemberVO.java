package com.mycom.warehouse.member.vo;

import org.springframework.stereotype.Component;

@Component("memberVO")
public class MemberVO {
	private String member_id;
	private String member_pw;
	private String member_email;
	
	public void setMember_id(String member_id)
	{
		this.member_id = member_id;
	}
	public String getMember_id()
	{
		return this.member_id;
	}
	
	public void setMember_pw(String member_pw)
	{
		this.member_pw = member_pw;
	}
	public String getMember_pw()
	{
		return this.member_pw;
	}
	
	public void setMember_email(String member_email)
	{
		this.member_email = member_email;
	}
	public String getMember_email()
	{
		return this.member_email;
	}
	
}
