<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript">
		function fn_update(obj){
			var tr = $(obj).parent().parent();
			var form = $("<form></form>");
			var setting_region = $("<input type='hidden' name='setting_region' value="+tr.children().eq(0).text()+">");
			form.append(setting_region);
			form.attr("method", 'post');
			form.attr("action", '${contextPath}/system/setting/update.do');
			form.appendTo('body');
			form.submit();
		};

		function fn_delete(obj){
			var tr = $(obj).parent().parent();
			var form = $("<form></form>");
			var setting_region = $("<input type='hidden' name='setting_region' value="+tr.children().eq(0).text()+">");
			form.append(setting_region);
			form.attr("method", 'post');
			form.attr("action", '${contextPath}/system/setting/delete.do');
			form.appendTo('body');
			form.submit();
		};
	</script>
	<title>시스템 설정 확인</title>
</head>

<body>
	<h1>시스템 설정 확인</h1>
	<p></p>
	<table border="1">
		<thead>
			<th>지역(시군단위)</th><th>자치단체장</th><th>양곡관리관</th><th>양곡담당자email</th><th>수정</th><th>삭제</th>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty settingList }">
					<c:forEach var="settingVO" items="${settingList}">
						<tr>
							<td>${settingVO.setting_region}</td>
							<td>${settingVO.setting_chief_name}</td>
							<td>${settingVO.setting_manager_name}</td>
							<td>${settingVO.setting_officer_email}</td>
							<td><input type="button" value="수정" onclick="fn_update(this)"></td>
							<td><input type="button" value="삭제" onclick="fn_delete(this)"></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan='6'>등록된 시스템 설정이 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>




</body>

</html>