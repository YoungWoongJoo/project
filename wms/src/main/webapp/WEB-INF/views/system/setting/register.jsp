<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>시스템 설정 등록</title>
  </head>

  <body>
    <h1>시스템 설정 등록</h1>
    <form id="frm_systemSetting" method="POST" action="${contextPath}/system/setting/add.do">
      지역(시군단위) : <input type="text" name="setting_region" required autocomplete="off" /><br />
      자치단체장 : <input type="text" name="setting_chief_name" required autocomplete="off" /><br />
      양곡관리관 : <input type="text" name="setting_manager_name" required autocomplete="off" /><br />
      양곡담당자email : <input type="email" name="setting_officer_email" required autocomplete="off" />
      <p>
        <input type="submit" id="btn_submit" value="등록하기" />
        <input type="reset" value="취소" />
      </p>
    </form>
  </body>
</html>
