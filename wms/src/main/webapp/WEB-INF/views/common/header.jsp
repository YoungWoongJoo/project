<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"
    isELIgnored="false"
    %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<body>
<div id="header_img">
    <a href="${contextPath}/main.do">
        <img src="${contextPath}/resources/image/warehouse.png">
    </a>
</div>
    <div id="header_text">
        창고통합관리시스템
    </div>
</body>