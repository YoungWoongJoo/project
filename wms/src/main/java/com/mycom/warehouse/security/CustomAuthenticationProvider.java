package com.mycom.warehouse.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mycom.warehouse.member.vo.MemberVO;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	CustomUserDetailService customUserDetailService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		List<GrantedAuthority> authorities = null;
		
		MemberVO user = (MemberVO) customUserDetailService.loadUserByUsername(username);
		
		try {
            user = (MemberVO)customUserDetailService.loadUserByUsername(username);
  
            if (!passwordEncoder.matches(password, user.getPassword()))
                    throw new BadCredentialsException("비밀번호 불일치");
  
            authorities = (List<GrantedAuthority>) user.getAuthorities();
        } catch(UsernameNotFoundException e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
  
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
