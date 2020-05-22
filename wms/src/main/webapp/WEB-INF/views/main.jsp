<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
<c:if test="${not empty errorMsg}">
var msg = "${errorMsg}";
alert(msg);
<c:remove var="errorMsg" scope="session"/>
</c:if>
</script>

<title>창고통합관리시스템</title>
</head>
<body>
	<div id="login">
		<sec:authorize access="isAuthenticated()">
			<form method="post" action="<c:url value='/logout' />">
			<h1>환영합니다. <sec:authentication property="principal"/>님.</h1>
			<input type="submit" value="로그아웃">
			<input type="button" value="마이페이지" onclick="location.href='${contextPath}/mypage/main.do'">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		</sec:authorize>
		<sec:authorize access="isAnonymous()">
			<h1>로그인</h1>
			<form id=loginForm method="post" action="<c:url value='/login' />">
					<input name="member_id" type="text" size="20" placeholder="아이디 입력" autofocus autocomplete="off" value="${member_id}"><br>
					<input name="member_pw" type="password" size="20" placeholder="비밀번호 입력" autocomplete="off"><br><br>
					<input type="submit" value="로그인"> <br>
					<input type="button" value="회원가입" onclick="location.href='${contextPath}/member/memberForm.do'">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>
		</sec:authorize>
	</div>
</body>
</html>
