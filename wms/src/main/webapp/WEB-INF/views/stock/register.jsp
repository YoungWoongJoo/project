<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script>
      $(document).ready(function() {
        var warehouse_name = "${warehouse_name}";
        if (warehouse_name != "" && warehouse_name != null) {
          $("#select_wh").val(warehouse_name);
        }

        $("input[name='stock_sort1']").change(function() {
          var text = "";
          $("#sort_detail").empty();
          if (this.value == "현미") {
            text =
              "원산지 : <input type='text' name='stock_sort2' autocomplete='off' required>";
          } else {
            text =
              "품종 : <input type='text' name='stock_sort2' autocomplete='off' required>";
          }
          $("#sort_detail").append(text);
        });
      });
    </script>
    <title>재고 등록</title>
  </head>
  <body>
    <h1>재고 등록하기</h1>

    <form
      id="frm_stock"
      method="POST"
      action="${contextPath}/stock/addNewStock.do"
    >
      창고 :
      <select id="select_wh" name="warehouse_name" required>
        <option value="선택" selected disabled>선택</option>
        <c:forEach var="warehouse" items="${warehouseList}">
          <option>${warehouse.warehouse_name}</option>
        </c:forEach> </select
      ><br />
      년산 :
      <select name="stock_year" required>
        <c:forEach var="i" begin="0" end="3">
          <option>${year-i}</option>
        </c:forEach> </select
      ><br />
      곡종 :
      <input type="radio" name="stock_sort1" value="쌀" required />쌀&nbsp;
      <input type="radio" name="stock_sort1" value="현미" />현미(TRQ밥쌀
      포함)&nbsp; <input type="radio" name="stock_sort1" value="벼" />벼<br />
      <div id="sort_detail"></div>
      단량 :
      <select name="stock_unit" required>
        <option selected disabled>선택</option>
        <option value="10">10kg</option>
        <option value="20">20kg</option>
        <option value="40">40kg</option>
        <option value="800">800kg</option>
        <option value="1000">1000kg</option> </select
      ><br />
      수량 :
      <input
        type="text"
        name="stock_quantity_40kg"
        required
        autocomplete="off"
      />
      <p>
        <input type="submit" value="등록하기" />
        <input type="reset" value="취소" />
      </p>
    </form>
  </body>
</html>
