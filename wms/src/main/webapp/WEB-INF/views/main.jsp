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
	<div id="login">
		<c:choose>
			<c:when test="${isLogOn=='true' and memberVO!=null}">
				<h1>환영합니다. ${memberVO.member_id}님.</h1>
				<input type="button" value="로그아웃" onclick="location.href='${contextPath}/member/logout.do'">
				<input type="button" value="마이페이지" onclick="location.href='${contextPath}/mypage/main.do'">
			</c:when>
			<c:otherwise>
				<h1>로그인</h1>
				<form id=loginForm method="post" action="${contextPath}/member/login.do">
						<input name="member_id" type="text" size="20" placeholder="아이디 입력" autofocus autocomplete="off"><br>
						<input name="member_pw" type="password" size="20" placeholder="비밀번호 입력" autocomplete="off"><br><br>
						<input type="submit" value="로그인"> <br>
						<input type="button" value="회원가입" onclick="location.href='${contextPath}/member/memberForm.do'">
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
