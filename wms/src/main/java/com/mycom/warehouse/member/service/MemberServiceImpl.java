package com.mycom.warehouse.member.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycom.warehouse.member.dao.MemberDao;
import com.mycom.warehouse.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation=Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberDao memberDao;

	@Override
	public MemberVO login(Map<String, String> loginMap) throws Exception {
		return memberDao.login(loginMap);
	}

	@Override
	public void addMember(MemberVO memberVO) throws Exception {
		memberDao.insertNewMember(memberVO);
		return;		
	}

	@Override
	public String checkID(String id) throws Exception {
		return memberDao.selectCheckID(id);
	}

	@Override
	public void updateMember(MemberVO memberVO) throws Exception {
		memberDao.updateMember(memberVO);		
	}

}
