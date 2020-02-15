package com.mycom.warehouse.member.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycom.warehouse.member.vo.MemberVO;

public interface MemberDao {
	public MemberVO login(Map<String,String> loginMap) throws DataAccessException;
	public void insertNewMember(MemberVO memberVO) throws DataAccessException;
	public String selectCheckID(String id) throws DataAccessException;
	public void updateMember(MemberVO memberVO) throws DataAccessException;
}
