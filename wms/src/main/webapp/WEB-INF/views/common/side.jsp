<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
	
<nav>
    <h1>창고관리</h1>
	<ul>
		<li><a href="${contextPath}/history/register.do">재고 관리</a></li>
		<li><a href="${contextPath}/stock/list.do">재고 현황</a></li>
		<li><a href="${contextPath}/history/list.do">재고 관리 내역</a></li>
		<li><a href="${contextPath}/bill/view.do">보관임/부대비 청구서</a></li>
	</ul>
	<h1>시스템 설정</h1>
	<ul>
		<li><a href="${contextPath}/system/rate/register.do">요율 설정</a></li>
		<li><a href="${contextPath}/system/rate/list.do">요율 설정 확인</a></li>
		<li><a href="${contextPath}/system/setting/register.do">시스템 설정</a></li>
		<li><a href="${contextPath}/system/setting/list.do">시스템 설정 확인</a></li>
	</ul>
	<h1>마이페이지</h1>
	<ul>
		<li><a href="${contextPath}/mypage/main.do">마이페이지</a></li>
		<li><a href="${contextPath}/mypage/updateForm.do">회원 정보 수정</a></li>
		<li><a href="${contextPath}/warehouse/register.do">창고 등록</a></li>
		<li><a href="${contextPath}/warehouse/updateForm.do">창고 수정</a></li>
		<li><a href="${contextPath}/stock/register.do">재고 등록</a></li>
		<li><a href="${contextPath}/stock/list.do">재고 현황</a></li>
	</ul>
</nav>

</html>