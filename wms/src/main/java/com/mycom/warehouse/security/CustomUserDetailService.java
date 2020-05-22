package com.mycom.warehouse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycom.warehouse.member.dao.MemberDao;

@Service
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	private MemberDao memberDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = memberDao.getUserById(username);
		if(userDetails==null)
			throw new UsernameNotFoundException("입력하신 id는 등록된 id가 아닙니다.\n");
		return userDetails;
	}

}
