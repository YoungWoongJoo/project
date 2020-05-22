<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script>
      $(document).ready(function() {
        var date = new Date();
        var year = date.getFullYear();
        if(year%2!=0)
        {
          year--;
        }
        $("span").append(year);
        $("input[name='rate_year']").val(year);
        $("input[id='rate_sort']").click(function(){
          if(this.value=="보관료 요율")
          {
            $("#div_cargoRate").attr("style", "display:none");
            $("#div_storageRate").attr("style", "display:block");
          }
          else{
            $("#div_cargoRate").attr("style", "display:block");
            $("#div_storageRate").attr("style", "display:none");
          }
        });

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

        $("select[id='wrap_sort']").change(function(){
            if(this.value=="톤백")
            {
              $("#nonTonBag_cargoRate input").each(function(index,item){
                $(item).attr("required", false);
                $(item).attr("disabled", true);
              });
              $("#TonBag_cargoRate input").each(function(index,item){
                $(item).attr("required", true);
                $(item).attr("disabled", false);
              });
              $("#TonBag_cargoRate input[name='transfer_rate']").attr("required", false);
              $("#TonBag_cargoRate").attr("style", "display:block");
              $("#nonTonBag_cargoRate").attr("style", "display:none");
            }
            else{
              $("#nonTonBag_cargoRate input").each(function(index,item){
                $(item).attr("required", true);
                $(item).attr("disabled", false);
              });
              $("#TonBag_cargoRate input").each(function(index,item){
                $(item).attr("required", false);
                $(item).attr("disabled", true);
              });
              $("#TonBag_cargoRate").attr("style", "display:none");
              $("#nonTonBag_cargoRate").attr("style", "display:block");
            }
        });
      })
    </script>
    <title>요율 등록</title>
  </head>
  <body>
  요율 종류 : <input type="radio" id="rate_sort" name="rate_sort" value="보관료 요율">보관료 요율&nbsp;
              <input type="radio" id="rate_sort" name="rate_sort" value="하역료 요율">하역료 요율
  <div id="div_storageRate" style="display:none">
    <h1>보관료 요율 등록</h1>
    <form id="frm_storageRate" method="POST" action="${contextPath}/system/storageRate/add.do">
      <span></span>년 2월 기준<p>
      <input type="hidden" name="rate_year">
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
      <h3>보관료 입력</h3>
      쌀 : <input type="text" name="white_rice_rate" required autocomplete="off" /><br />
      현미 : <input type="text" name="brown_rice_rate" required autocomplete="off" /><br />
      벼 : <input type="text" name="rice_rate" required autocomplete="off" />
      <p>
        <input type="submit" id="btn_submit" value="등록하기" />
        <input type="reset" value="취소" />
      </p>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>

  <div id="div_cargoRate" style="display:none">
  <h1>하역료 요율 등록</h1>
    <form id="frm_cargoRate" method="POST" action="${contextPath}/system/cargoRate/add.do">
      <span></span>년 2월 기준<p>
      <input type="hidden" name="rate_year">
      포장재 종류 : <select id="wrap_sort" name="wrap_sort">
                      <option value="선택" selected disabled>선택</option>
                      <option value="10">10kg</option>
                      <option value="20">20kg</option>
                      <option value="40">40kg</option>
                      <option value="톤백">톤백</option>
                    </select>
      <h3>하역료 입력</h3>
      <div id="nonTonBag_cargoRate" style="display:none">
          <input type="hidden" name="bag_purchase_rate" required autocomplete="off" />
        상차료 : <input type="text" name="load_rate" required autocomplete="off" /><br />
        하차료 : <input type="text" name="unload_rate" required autocomplete="off" /><br />
        입고료 : <input type="text" name="warehousing_rate" required autocomplete="off" /><br />
        출고료 : <input type="text" name="release_rate" required autocomplete="off" /><br />
        매입장소입고료 : <input type="text" name="purchase_warehousing_rate" required autocomplete="off" /><br />
        매입직송상차료 : <input type="text" name="purchase_load_rate" required autocomplete="off" /><br />
        매입장소경비료 : <input type="text" name="security_rate" required autocomplete="off" /><br />
        이적료 : <input type="text" name="transfer_rate" required autocomplete="off" /><br />
        이송료<br>
          - 20m 초과 50m 까지 : <input type="text" name="migration_20m_rate" required autocomplete="off" /><br />
          - 50m 초과 20m 마다 : <input type="text" name="migration_50m_rate" required autocomplete="off" />
      </div>
      <div id="TonBag_cargoRate" style="display:none">
        톤백 매입료 : <input type="text" name="bag_purchase_rate" required autocomplete="off" /><br />
        톤백 매입장소입고료 : <input type="text" name="purchase_warehousing_rate" required autocomplete="off" /><br />
        톤백 매입직송상차료 : <input type="text" name="purchase_load_rate" required autocomplete="off" /><br />
        톤백 상차료 : <input type="text" name="load_rate" required autocomplete="off" /><br />
        톤백 하차료 : <input type="text" name="unload_rate" required autocomplete="off" /><br />
        톤백 입고료 : <input type="text" name="warehousing_rate" required autocomplete="off" /><br />
        톤백 출고료 : <input type="text" name="release_rate" required autocomplete="off" /><br />
        톤백 이적료 : <input type="text" name="transfer_rate" required autocomplete="off" /><br />
        톤백 매입장소경비료 : <input type="text" name="security_rate" required autocomplete="off" /><br />
        톤백 이송료<br>
          - 20m 초과 50m 까지 : <input type="text" name="migration_20m_rate" required autocomplete="off" /><br />
          - 50m 초과 20m 마다 : <input type="text" name="migration_50m_rate" required autocomplete="off" />
      </div>
      <p>
        <input type="submit" value="등록하기" />
        <input type="reset" value="취소" />
      </p>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>
  </body>
</html>
