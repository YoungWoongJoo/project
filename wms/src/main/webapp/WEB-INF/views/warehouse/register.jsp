<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script>
      var index = 0;
      $(document).ready(function() {
        $("input:radio[name='warehouse_rating']").click(function() {
          if ($("#warehouse_rating").is(":checked")) {
            $("#detail_view").attr("style", "display:block");
          } else {
            $("#detail_view").attr("style", "display:none");
            $("#detail_rating").val("선택");
          }
        });

        $("#detail_rating").change(function() {
          var value = $("#detail_rating").val();
          $("#warehouse_rating").val(value);
        });
      });

      function fn_register() {
        var warehouse_name = $("#_warehouse_name").val();
        $("#warehouse_name").val(warehouse_name);
        $("#frm_warehouse").attr(
          "action",
          "${contextPath}/warehouse/addWarehouse.do"
        );
        $("#frm_warehouse").submit();
      }

      function fn_update() {
        var checked = $("input:radio[id='radioName']").is(":checked");
        if (checked == true) {
          $("#frm_title").val("창고 수정하기");
          $("#frm_warehouse").attr("style", "display:block");
          $("#frm_stock").attr("style", "display:none");
          var radio = $('$("#radioName"):checked');
          var tr = radio.parent().parent();
          var td = tr.children();
          $("input[name=warehouse_name]").val(td.eq(1).text());
          $("input[name=_warehouse_name]").val(td.eq(1).text());
          $("input[name=warehouse_owner]").val(td.eq(2).text());
          $("input[name=warehouse_code]").val(td.eq(3).text());
          $("input[name=warehouse_rating]").val(td.eq(4).text());

          $("#btn_submit").val("수정하기");
          $("input[name=_warehouse_name]").prop("disabled", true);
          $("#frm_warehouse").attr(
            "action",
            "${contextPath}/warehouse/updateWarehouse.do"
          );
          $("#btn_submit").removeAttr("onclick");
        } else {
          alert(
            "선택된 창고가 없습니다.\n위에서 수정하고 싶은 창고를 선택해주세요."
          );
        }
      }

      function fn_stock_register() {
        var checked = $("input:radio[id='radioName']").is(":checked");
        if (checked == true) {
          $("#frm_warehouse").attr("style", "display:none");
          $("#frm_stock").attr("style", "display:block");
          var radio = $('$("#radioName"):checked');
          var tr = radio.parent().parent();
          var td = tr.children();
          $("select#select_wh").val(td.eq(1).text());
        } else {
          alert(
            "선택된 창고가 없습니다.\n위에서 수정하고 싶은 창고를 선택해주세요."
          );
        }
      }

      function fn_add_row() {
        $("#stock_table>tbody:last").append(
          "<tr>" +
            '<td><input type="text" name="stockVO[' +
            index +
            '].stock_year" required></td>' +
            '<td><input type="text" name="stockVO[' +
            index +
            '].stock_sort" required></td>' +
            '<td><select name="stockVO[' +
            index +
            '].stock_unit" required>' +
            "<option selected disabled>선택</option>" +
            '<option value="10">10kg</option>' +
            '<option value="20">20kg</option>' +
            '<option value="40">40kg</option>' +
            '<option value="800">800kg</option>' +
            '<option value="1000">1000kg</option>' +
            "</select></td>" +
            '<td><input type="text" name="stockVO[' +
            index +
            '].stock_quantity_40kg"required></td>' +
            '<td><input type="button" value="삭제" onclick="fn_delete_row(this)"></td>' +
            "</tr>"
        );
        index++;
      }
      function fn_delete_row(obj) {
        $(obj)
          .parent()
          .parent()
          .remove();
      }
    </script>
    <title>창고 등록</title>
  </head>
  <body>
    <p></p>
    <form
      id="frm_warehouse"
      method="POST"
      action="${contextPath}/warehouse/addNewWarehouse.do"
    >
      <h2 id="frm_title">창고 등록하기</h2>
      창고명 :
      <input
        type="text"
        name="warehouse_name"
        required
      /><br />
      창고주명 :
      <input
        type="text"
        name="warehouse_owner"
        id="warehouse_owner"
        required
      /><br />
      코드번호 :
      <input
        type="text"
        name="warehouse_code"
        id="warehouse_code"
        required
      /><br />
      지역구분 :
      <input
        type="radio"
        name="warehouse_region"
        value="특지"
        required
      />특지&nbsp;
      <input type="radio" name="warehouse_region" value="갑지" />갑지&nbsp;
      <input type="radio" name="warehouse_region" value="을지" />을지<br />
      <p>
        ○ 특 지 : 특별시, 특별자치시 및 광역시<br />
        ○ 갑 지 : ① 시 지역, ② 주요 항구도시 : 장항(차기 요율 개정 시
        주요항구도시 특혜조항 삭제 예정)<br />
        ○ 을 지 : 상기 지역을 제외한 전(全) 지역
      </p>
      등급구분 :
      <input
        type="radio"
        id="warehouse_rating"
        name="warehouse_rating"
        required
      />일반창고&nbsp;
      <input
        type="radio"
        name="warehouse_rating"
        value="저온창고"
      />저온창고<br />
      <div id="detail_view" style="display: none;">
        상세구분:
        <select id="detail_rating" required>
          <option selected disabled>선택</option>
          <option value="일반/특급">특급</option>
          <option value="일반/1급">1급</option>
          <option value="일반/2급">2급</option>
          <option value="일반/3급">3급</option>
          <option value="일반/등외">등외</option>
        </select>
      </div>

      <p>
        <input type="submit" id="btn_submit" value="등록하기" />
        <input type="reset" value="취소" />
      </p>
    </form>

    <form
      id="frm_stock"
      method="POST"
      style="display: none"
      action="${contextPath}/stock/register.do"
    >
      <h2>재고 등록하기</h2>
      창고 :
      <select id="select_wh" name="warehouse_name">
        <c:forEach var="warehouse" items="${warehouseList}">
          <option>${warehouse.warehouse_name}</option>
        </c:forEach> </select
      ><br />
      <p></p>
      <table border="1" id="stock_table">
        <thead>
          <th>년산</th>
          <th>곡종</th>
          <th>단량</th>
          <th>수량</th>
          <th></th>
        </thead>
        <tbody></tbody>
      </table>
      <p>
        <input type="button" value="품목 추가" onclick="fn_add_row()" />
      </p>
      <p>
        <input type="submit" value="등록하기" />
        <input type="reset" value="취소" />
      </p>
    </form>
  </body>
</html>
