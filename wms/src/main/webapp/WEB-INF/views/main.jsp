<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>창고통합관리시스템</title>
</head>
<body>
	<h1>창고관리</h1>
	<ul>
		<li><a href="#">입고/출고</a></li>
		<li><a href="#">입출고 내역</a></li>
		<li><a href="${contextPath}/stock/list.do">재고 현황</a></li>
		<li><a href="${contextPath}/warehouse/register.do">창고 등록</a></li>
		<li><a href="#">보관임/부대비 청구서</a></li>
	</ul>
	<p></p>
	<c:choose>
		<c:when test="${isLogOn=='true' and memberVO!=null}">
			환영합니다. ${memberVO.member_id}님.
			<p>
			<input type="button" value="로그아웃" onclick="location.href='${contextPath}/member/logout.do'">
			<input type="button" value="마이페이지" onclick="location.href='${contextPath}/mypage/main.do'">
		</c:when>
		<c:otherwise>
			<form id=loginForm method="post" action="${contextPath}/member/login.do">
				<p>
					<input name="member_id" type="text" placeholder="아이디 입력" autofocus>
				</p>
				<p>
					<input name="member_pw" type="password" placeholder="비밀번호 입력">
				</p>
				<p>
					<input type="submit" value="로그인"> 
					<input type="button" value="회원가입" onclick="location.href='${contextPath}/member/memberForm.do'">
				</p>
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>
