<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	request.setCharacterEncoding("utf-8");
%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

</head>
<body>
	<div id="wrap">
		<header>
			<tiles:insertAttribute name="header" />
		</header>
		<div class="clear"></div>
		<aside>
			<tiles:insertAttribute name="side" />
		</aside>
		<article>
			<tiles:insertAttribute name="body" />
		</article>
		<div class="clear"></div>
		<footer>
			<tiles:insertAttribute name="footer" />
		</footer>
	</div>
</body>

