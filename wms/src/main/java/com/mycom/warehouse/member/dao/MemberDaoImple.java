package com.mycom.warehouse.member.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycom.warehouse.member.vo.MemberVO;

@Repository("memberDao")
public class MemberDaoImple implements MemberDao {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public MemberVO login(Map<String, String> loginMap) throws DataAccessException {
		MemberVO member = (MemberVO) sqlSession.selectOne("mapper.member.login", loginMap);
		return member;
	}

	@Override
	public void insertNewMember(MemberVO memberVO) throws DataAccessException {
		sqlSession.insert("mapper.member.insertNewMember", memberVO);
	}

	@Override
	public String selectCheckID(String id) throws DataAccessException {
		String result = sqlSession.selectOne("mapper.member.selectCheckID", id);
		return result;
	}

	@Override
	public void updateMember(MemberVO memberVO) throws DataAccessException {
		sqlSession.update("mapper.member.updateMember", memberVO);
	}

}
