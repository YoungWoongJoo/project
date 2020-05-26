package com.mycom.warehouse.member.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component("memberVO")
public class MemberVO implements UserDetails{
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
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (member_id != null && member_id.equals("admin")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authorities;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member_pw;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member_id;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
