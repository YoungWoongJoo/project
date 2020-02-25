<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script>
      $(document).ready(function() {
        var name = "${warehouseVO.warehouse_name}"
        var owner = "${warehouseVO.warehouse_owner}"
        var code = "${warehouseVO.warehouse_code}"
        var region = "${warehouseVO.warehouse_region}"
        var rating = "${warehouseVO.warehouse_rating}"
        var region_name = "${warehouseVO.warehouse_region_name}"
        if (name != null && name != "") {
          $("#select_wh").val(name)
          $("#warehouse_owner").val(owner)
          $("#warehouse_code").val(code)
          $("input[value=" + region + "]").attr("checked", true)
          if (rating == "저온") {
            $("input[value=" + rating + "]").attr("checked", true)
          } else {
            $("#warehouse_rating").attr("checked", true)
            $("#warehouse_rating").val(rating)
            $("#detail_view").attr("style", "display:block")
            $("#detail_rating").val(rating)
          }
          $("#warehouse_region_name").val(region_name)
        }

        $("input:radio[name='warehouse_rating']").click(function() {
          if ($("#warehouse_rating").is(":checked")) {
            $("#detail_view").attr("style", "display:block")
          } else {
            $("#detail_view").attr("style", "display:none")
            $("#detail_rating").val("선택")
          }
        })

        $("#detail_rating").change(function() {
          var value = $("#detail_rating").val()
          $("#warehouse_rating").val(value)
        })

        $("#select_wh").change(function() {
          var warehouse_name = this.value
          $.ajax({
            type: "post",
            async: true,
            url: "${contextPath}/warehouse/searchWarehouse.do",
            dataType: "Text",
            data: { warehouse_name: warehouse_name },
            success: function(data) {
              var warehouseVO = JSON.parse(data)
              $("#select_wh").val(warehouseVO.warehouse_name)
              $("#warehouse_owner").val(warehouseVO.warehouse_owner)
              $("#warehouse_code").val(warehouseVO.warehouse_code)
              $("input[value=" + warehouseVO.warehouse_region + "]").attr("checked", true)
              if (warehouseVO.warehouse_rating == "저온") {
                $("input[value=" + warehouseVO.warehouse_rating + "]").attr("checked", true)
                $("#detail_view").attr("style", "display:none")
              } else {
                $("#warehouse_rating").attr("checked", true)
                $("#warehouse_rating").val(warehouseVO.warehouse_rating)
                $("#detail_view").attr("style", "display:block")
                $("#detail_rating").val(warehouseVO.warehouse_rating)
              }
              $("#warehouse_region_name").val(warehouseVO.warehouse_region_name)
            },
            error: function(data, textStatus) {
              //alert("에러가 발생했습니다. 다시 시도해주세요.");
            },
            complete: function(data, textStatus) {
              //alert("작업을완료 했습니다");
            }
          })
        })
      })
    </script>
    <title>창고 수정</title>
  </head>
  <body>
    <h1>창고 수정하기</h1>
    <form id="frm_warehouse" method="POST" action="${contextPath}/warehouse/updateWarehouse.do">
      창고 :
      <select id="select_wh" name="warehouse_name">
        <option value="선택" selected disabled>선택</option>
        <c:forEach var="warehouse" items="${warehouseList}">
          <option>${warehouse.warehouse_name}</option>
        </c:forEach> </select
      ><br />
      창고주명 :
      <input type="text" name="warehouse_owner" id="warehouse_owner" required autocomplete="off" /><br />
      코드번호 :
      <input type="text" name="warehouse_code" id="warehouse_code" required autocomplete="off" /><br />
      지역구분 :
      <input type="radio" name="warehouse_region" value="특지" required />특지&nbsp;
      <input type="radio" name="warehouse_region" value="갑지" />갑지&nbsp; <input type="radio" name="warehouse_region" value="을지" />을지<br />
      <p>
        ○ 특 지 : 특별시, 특별자치시 및 광역시<br />
        ○ 갑 지 : ① 시 지역, ② 주요 항구도시 : 장항(차기 요율 개정 시 주요항구도시 특혜조항 삭제 예정)<br />
        ○ 을 지 : 상기 지역을 제외한 전(全) 지역
      </p>
      등급구분 :
      <input type="radio" id="warehouse_rating" name="warehouse_rating" required />일반창고&nbsp;
      <input type="radio" name="warehouse_rating" value="저온" />저온창고<br />
      <div id="detail_view" style="display: none;">
        상세구분:
        <select id="detail_rating" required>
          <option selected disabled>선택</option>
          <option value="특급">특급</option>
          <option value="1급">1급</option>
          <option value="2급">2급</option>
          <option value="3급">3급</option>
          <option value="등외">등외</option>
        </select>
      </div>
      소재지(시·군단위) : <input type="text" id="warehouse_region_name" name="warehouse_region_name" required autocomplete="off" />
      <p>
        <input type="submit" id="btn_submit" value="수정하기" />
        <input type="reset" value="취소" />
      </p>
    </form>
  </body>
</html>
