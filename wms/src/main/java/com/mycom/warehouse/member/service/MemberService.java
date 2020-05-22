package com.mycom.warehouse.member.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.mycom.warehouse.member.vo.MemberVO;

public interface MemberService {
	public MemberVO login(Map<String, String> loginMap) throws Exception;
	public void addMember(MemberVO memberVO) throws Exception;
	public String checkID(String id) throws Exception;
	public void updateMember(MemberVO memberVO) throws Exception;
}
