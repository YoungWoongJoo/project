<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <script>
    function fn_delete() {
      var checked = $("input:radio[id='radioName']").is(":checked");
      if (checked == true) {
        var radio = $('input[name="radioName"]:checked');
        var tr = radio.parent().parent();
        var td = tr.children();

        var form = $("<form></form>");
        var name = $("<input type='hidden' name='warehouse_name' value=" + td.eq(2).text() + ">");

        form.append(name);
        var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
		form.append(input);
        form.attr("method", 'post');
        form.attr("action", "${contextPath}/warehouse/deleteWarehouse.do");
        form.appendTo('body');
        form.submit();
      }
      else {
        alert("선택된 창고가 없습니다.\n위에서 삭제하고 싶은 창고를 선택해주세요.");
      }
    }

    function fn_update() {
      var checked = $("input:radio[id='radioName']").is(":checked");
      if (checked == true) {
        var radio = $('input[name="radioName"]:checked');
        var tr = radio.parent().parent();
        var td = tr.children();

        var form = $("<form></form>");
        var name = $("<input type='hidden' name='warehouse_name' value=" + td.eq(2).text() + ">");
        var owner = $("<input type='hidden' name='warehouse_owner' value=" + td.eq(3).text() + ">");
        var code = $("<input type='hidden' name='warehouse_code' value=" + td.eq(4).text() + ">");
        var region = $("<input type='hidden' name='warehouse_region' value=" + td.eq(5).text() + ">");
        var rating = $("<input type='hidden' name='warehouse_rating' value=" + td.eq(6).text() + ">");
        var region_name = $("<input type='hidden' name='warehouse_region_name' value=" + td.eq(1).text() + ">");

        form.append(name);
        form.append(owner);
        form.append(code);
        form.append(region);
        form.append(rating);
        form.append(region_name);
        var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
		form.append(input);
        form.attr("method", 'POST');
        form.attr("action", "${contextPath}/warehouse/updateForm.do");
        form.appendTo('body');
        form.submit();
      } else {
        alert(
          "선택된 창고가 없습니다.\n위에서 수정하고 싶은 창고를 선택해주세요."
        );
      }
    }

    function fn_stock_register() {
      var checked = $("input:radio[id='radioName']").is(":checked");
      if (checked == true) {
        var radio = $('input[name="radioName"]:checked');
        var tr = radio.parent().parent();
        var td = tr.children();

        var form = $("<form></form>");
        var name = $("<input type='hidden' name='warehouse_name' value=" + td.eq(2).text() + ">");

        form.append(name);
        var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
		form.append(input);
        form.attr("method", 'POST');
        form.attr("action", "${contextPath}/stock/register.do");
        form.appendTo('body');
        form.submit();
      } else {
        alert(
          "선택된 창고가 없습니다.\n위에서 재고를 등록하고 싶은 창고를 선택해주세요."
        );
      }
    }

  </script>

  <title>마이 페이지</title>
</head>

<body>
  <h1>회원 정보</h1>
  이메일 : ${memberVO.member_email}
  <p></p>
  <input type="button" value="회원정보 수정" onclick="location.href='${contextPath}/mypage/updateForm.do'">
  <br><br>
  <h1>내 창고 목록</h1>
  <p></p>
  <table>
    <thead>
      <th>선택</th>
      <th>소재지</th>
      <th>창고명</th>
      <th>창고주명</th>
      <th>코드번호</th>
      <th>지역구분</th>
      <th>등급구분</th>
    </thead>
    <c:choose>
      <c:when test="${empty warehouseList}">
        <tr>
          <td colspan="7">등록된 창고가 없습니다. 아래에서 창고를 등록해주세요.</td>
        </tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="warehouse" items="${warehouseList}">
          <tr>
            <td><input type="radio" name="radioName" id="radioName"></td>
            <td>${warehouse.warehouse_region_name}</td>
            <td>${warehouse.warehouse_name}</td>
            <td>${warehouse.warehouse_owner}</td>
            <td>${warehouse.warehouse_code}</td>
            <td>${warehouse.warehouse_region}</td>
            <td>${warehouse.warehouse_rating}</td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
    <tr></tr>
  </table>
  <p></p><input type="button" value="창고등록" onclick="location.href='${contextPath}/warehouse/register.do'">
  <c:if test="${not empty warehouseList }">
    <input type="button" value="재고등록" onclick="fn_stock_register()">
    <input type="button" value="수정하기" onclick="fn_update()">
    <input type="button" value="삭제하기" onclick="fn_delete()">

  </c:if>

</body>

</html>