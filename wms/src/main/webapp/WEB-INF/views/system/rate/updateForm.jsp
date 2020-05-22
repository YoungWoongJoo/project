<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script>
      $(document).ready(function() {
          if("${storageRateVO}"!="")
          {
            show_storageRateForm();
          }
          else if("${cargoRateVO}"!=""){
            show_cargoRateForm();
            if("${cargoRateVO.wrap_sort}"=="톤백")
            {
              disable_nonTonbog_input();
            }
            else{
              disable_tonbog_input();
            }
          }

      function show_storageRateForm()
      {
        $("#div_cargoRate").attr("style", "display:none");
          $("#div_storageRate").attr("style", "display:block");
      }
      function show_cargoRateForm()
      {
        $("#div_cargoRate").attr("style", "display:block");
          $("#div_storageRate").attr("style", "display:none");
      }
      function disable_nonTonbog_input()
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
      function disable_tonbog_input()
      {
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

      })
    </script>
    <title>요율 수정</title>
  </head>
  <body>
  <div id="div_storageRate" style="display:none">
    <h1>보관료 요율 수정</h1>
    <form id="frm_storageRate" method="POST" action="${contextPath}/system/storageRate/update.do">
      <input type="hidden" name="storage_rate_seq_num" value="${storageRateVO.storage_rate_seq_num}">
      지역구분 : <input type="text" name="_warehouse_region" value="${storageRateVO.warehouse_region}" disabled><br>
                <input type="hidden" name="warehouse_region" value="${storageRateVO.warehouse_region}">
      등급구분 : <input type="text" name="_warehouse_rating" value="${storageRateVO.warehouse_rating}" disabled>
                <input type="hidden" name="warehouse_rating" value="${storageRateVO.warehouse_rating}">
      <h3>보관료 입력</h3>
      쌀 : <input type="text" name="white_rice_rate" required autocomplete="off" value="${storageRateVO.white_rice_rate}"/><br />
      현미 : <input type="text" name="brown_rice_rate" required autocomplete="off" value="${storageRateVO.brown_rice_rate}"/><br />
      벼 : <input type="text" name="rice_rate" required autocomplete="off" value="${storageRateVO.rice_rate}"/>
      <p>
        <input type="submit" id="btn_submit" value="수정하기" />
        <input type="reset" value="취소" />
      </p>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>

  <div id="div_cargoRate" style="display:none">
  <h1>하역료 요율 수정</h1>
    <form id="frm_cargoRate" method="POST" action="${contextPath}/system/cargoRate/update.do">
      <input type="hidden" name="cargo_rate_seq_num" value="${cargoRateVO.cargo_rate_seq_num}">
      포장재 종류 : <input type="text" name="_wrap_sort" value="${cargoRateVO.wrap_sort}" disabled>
                   <input type="hidden" name="wrap_sort" value="${cargoRateVO.wrap_sort}">
      <h3>하역료 입력</h3>
      <div id="nonTonBag_cargoRate" style="display:none">
          <input type="hidden" name="bag_purchase_rate" required autocomplete="off" value="${cargoRateVO.bag_purchase_rate}"/>
        상차료 : <input type="text" name="load_rate" required autocomplete="off" value="${cargoRateVO.load_rate}"/><br />
        하차료 : <input type="text" name="unload_rate" required autocomplete="off" value="${cargoRateVO.unload_rate}"/><br />
        입고료 : <input type="text" name="warehousing_rate" required autocomplete="off" value="${cargoRateVO.warehousing_rate}"/><br />
        출고료 : <input type="text" name="release_rate" required autocomplete="off" value="${cargoRateVO.release_rate}"/><br />
        매입장소입고료 : <input type="text" name="purchase_warehousing_rate" required autocomplete="off" value="${cargoRateVO.purchase_warehousing_rate}"/><br />
        매입직송상차료 : <input type="text" name="purchase_load_rate" required autocomplete="off" value="${cargoRateVO.purchase_load_rate}"/><br />
        매입장소경비료 : <input type="text" name="security_rate" required autocomplete="off" value="${cargoRateVO.security_rate}"/><br />
        이적료 : <input type="text" name="transfer_rate" required autocomplete="off" value="${cargoRateVO.transfer_rate}"/><br />
        이송료<br>
          - 20m 초과 50m 까지 : <input type="text" name="migration_20m_rate" required autocomplete="off" value="${cargoRateVO.migration_20m_rate}"/><br />
          - 50m 초과 20m 마다 : <input type="text" name="migration_50m_rate" required autocomplete="off" value="${cargoRateVO.migration_50m_rate}"/>
      </div>
      <div id="TonBag_cargoRate" style="display:none">
        톤백 매입료 : <input type="text" name="bag_purchase_rate" required autocomplete="off" value="${cargoRateVO.bag_purchase_rate}"/><br />
        톤백 매입장소입고료 : <input type="text" name="purchase_warehousing_rate" required autocomplete="off" value="${cargoRateVO.purchase_warehousing_rate}"/><br />
        톤백 매입직송상차료 : <input type="text" name="purchase_load_rate" required autocomplete="off" value="${cargoRateVO.purchase_load_rate}"/><br />
        톤백 상차료 : <input type="text" name="load_rate" required autocomplete="off" value="${cargoRateVO.load_rate}"/><br />
        톤백 하차료 : <input type="text" name="unload_rate" required autocomplete="off" value="${cargoRateVO.unload_rate}"/><br />
        톤백 입고료 : <input type="text" name="warehousing_rate" required autocomplete="off" value="${cargoRateVO.warehousing_rate}"/><br />
        톤백 출고료 : <input type="text" name="release_rate" required autocomplete="off" value="${cargoRateVO.release_rate}"/><br />
        톤백 이적료 : <input type="text" name="transfer_rate" required autocomplete="off" value="${cargoRateVO.transfer_rate}"/><br />
        톤백 매입장소경비료 : <input type="text" name="security_rate" required autocomplete="off" value="${cargoRateVO.security_rate}"/><br />
        톤백 이송료<br>
          - 20m 초과 50m 까지 : <input type="text" name="migration_20m_rate" required autocomplete="off" value="${cargoRateVO.migration_20m_rate}"/><br />
          - 50m 초과 20m 마다 : <input type="text" name="migration_50m_rate" required autocomplete="off" value="${cargoRateVO.migration_50m_rate}"/>
      </div>
      <p>
        <input type="submit" value="수정하기" />
        <input type="reset" value="취소" />
      </p>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </div>
  </body>
</html>
