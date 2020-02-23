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
        autocomplete="off"
      /><br />
      창고주명 :
      <input
        type="text"
        name="warehouse_owner"
        id="warehouse_owner"
        required
        autocomplete="off"
      /><br />
      코드번호 :
      <input
        type="text"
        name="warehouse_code"
        id="warehouse_code"
        required
        autocomplete="off"
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
  </body>
</html>
