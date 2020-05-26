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
			var setting_chief_rank = $("<input type='hidden' name='setting_chief_rank' value="+tr.children().eq(1).text()+">");
			var setting_chief_name = $("<input type='hidden' name='setting_chief_name' value="+tr.children().eq(2).text()+">");
			var setting_manager_rank = $("<input type='hidden' name='setting_manager_rank' value="+tr.children().eq(3).text()+">");
			var setting_manager_name = $("<input type='hidden' name='setting_manager_name' value="+tr.children().eq(4).text()+">");
			var setting_officer_email = $("<input type='hidden' name='setting_officer_email' value="+tr.children().eq(5).text()+">");
			form.append(setting_region);
			form.append(setting_chief_rank);
			form.append(setting_chief_name);
			form.append(setting_manager_rank);
			form.append(setting_manager_name);
			form.append(setting_officer_email);
			var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
			form.append(input);
			form.attr("method", 'post');
			form.attr("action", '${contextPath}/system/setting/updateForm.do');
			form.appendTo('body');
			form.submit();
		};

		function fn_delete(obj){
			var tr = $(obj).parent().parent();
			var form = $("<form></form>");
			var setting_region = $("<input type='hidden' name='setting_region' value="+tr.children().eq(0).text()+">");
			form.append(setting_region);
			var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
			form.append(input);
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
			<th>지역(시군단위)</th><th>자치단체장 직급</th><th>자치단체장 성함</th><th>양곡관리관 직급</th><th>양곡관리관 성함</th><th>양곡담당자email</th><th>수정</th><th>삭제</th>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty settingList }">
					<c:forEach var="settingVO" items="${settingList}">
						<tr>
							<td>${settingVO.setting_region}</td>
							<td>${settingVO.setting_chief_rank}</td>
							<td>${settingVO.setting_chief_name}</td>
							<td>${settingVO.setting_manager_rank}</td>
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