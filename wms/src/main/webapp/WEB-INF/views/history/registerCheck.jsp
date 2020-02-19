<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>재고 관리 확인</title>
  </head>
  <body>
    <h1>재고 관리 확인</h1>

    <table border="1">
      <thead>
        <th>창고</th>
        <th>날짜</th>
        <th>년산</th>
        <th>곡종</th>
        <th>단량</th>
        <th>입출고</th>
        <th>하역</th>
        <th>수량</th>
      </thead>
      <tbody>
        <tr>
          <td>${historyVO.warehouse_name}</td>
          <td>${historyVO.history_date}</td>
          <td>${historyVO.stock_year}</td>
          <td>${historyVO.stock_sort2}${historyVO.stock_sort1}</td>
          <td>${historyVO.stock_unit}</td>
          <td>${historyVO.history_sort1}</td>
          <td>${historyVO.history_sort2}</td>
          <td>${historyVO.history_quantity}</td>
        </tr>
      </tbody>
    </table>
    <form
      id="frm_history"
      method="POST"
      action="${contextPath}/history/addNewHistory.do"
    >
      <input type="hidden" name="warehouse_name" value="${historyVO.warehouse_name}">
      <input type="hidden" name="history_date" value="${historyVO.history_date}">
      <input type="hidden" name="stock_year" value="${historyVO.stock_year}">
      <input type="hidden" name="stock_sort1" value="${historyVO.stock_sort1}">
      <input type="hidden" name="stock_sort2" value="${historyVO.stock_sort2}">
      <input type="hidden" name="stock_unit" value="${historyVO.stock_unit}">
      <input type="hidden" name="history_sort1" value="${historyVO.history_sort1}">
      <input type="hidden" name="history_sort2" value="${historyVO.history_sort2}">
      <input type="hidden" name="history_quantity" value="${historyVO.history_quantity}">
      <p>
        위의 내용을 확인하시고 관리 내용이 맞으면 아래의 "등록하기" 버튼을 눌러 진행해주세요.<br>
        다른 부분이 있으면 "취소" 버튼을 눌러 이전 페이지로 돌아가 수정해주세요.<br>
        관리 내용은 한번 추가되면 삭제나 수정을 할 수 없습니다. 정확히 확인해주세요.<p></p>
        <input type="submit" value="등록하기" />
        <input type="button" value="취소" onclick="history.back()"/>
      </p>
    </form>
  </body>
</html>
