<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	
</script>
<title>재고 현황</title>
</head>
<body>
	<h1>창고 재고 현황</h1>
	<p></p>
	<c:choose>
		<c:when test="${not empty warehouseList }">
			창고 : <select id="select_wh" name="warehouse_name">
						<option selected disabled>선택</option>
		            <c:forEach var="warehouse" items="${warehouseList}">
		                <option>${warehouse.warehouse_name}</option>
		            </c:forEach>
		        </select><br>
		    <p></p>
			<table id="stock_table" border="1">
			</table>
		        
		</c:when>
		<c:otherwise>
			등록된 창고가 없습니다.<br>
			<a href="${contextPath}/warehouse/register.do">여기</a>를 클릭하여 창고등록창으로 이동해주세요.<br>
			<input type="button" value="창고 등록" onclick="location.href='${contextPath}/warehouse/register.do'">
		</c:otherwise>
	
	</c:choose>
	
	
	
</body>
</html>