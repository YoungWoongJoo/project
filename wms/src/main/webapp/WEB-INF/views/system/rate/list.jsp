<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
    <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="storageRateList" value="${listMap.storageRateList}"/>
<c:set var="cargoRateList" value="${listMap.cargoRateList}"/>
<c:set var="yearList" value="${listMap.yearList}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <script type="text/javascript">
            $(document).ready(function() {
              $("select[name='year']").change(function()
              {
                $("#div_table").show();
                var i,j;
                for(i=0; i<$("table>tbody>tr").length; i++)
               	{
                	for(j=1; j<$("table>tbody>tr").eq(i).children().length; j++)
               		{
                		$("table>tbody>tr").eq(i).children().eq(j).empty();
               		}
               	}
                for(i=0; i<$("table>tfoot").children().length; i++)
               	{
                	for(j=1; j<$("table>tfoot").children().eq(i).children().length; j++)
               		{
                		$("table>tfoot").children().eq(i).children().eq(j).empty();
               		}
               	}
                
                var rate_year = this.value;
                var tab_tr = "";
                var tab_tf = "";
                var tr = "";
                var td = "";
                
                <c:forEach var="storageRateVO" items="${storageRateList}">//일반창고 보관료 요율 설정이 있을경우
                if(${storageRateVO.rate_year}==rate_year)
                {
                  if("${storageRateVO.warehouse_rating}"!="" &&"${storageRateVO.warehouse_rating}"!="저온") {
                    tab_tr= $("#normal_warehouse_table tr");
                    tab_tf= $("#normal_warehouse_table tfoot");
                      tr = 2;
                      if("${storageRateVO.warehouse_region}"=="특지")
                      { td = 1; } 
                      else if("${storageRateVO.warehouse_region}"=="갑지") { td =6; } 
                      else 
                        td = 11;
                      if("${storageRateVO.warehouse_rating}"=="특급") 
                      { 
                        td += 0; 
                        } 
                      else if("${storageRateVO.warehouse_rating}"=="1급") 
                      { td += 1; } 
                      else if("${storageRateVO.warehouse_rating}"=="2급") 
                      { td += 2; } 
                      else if("${storageRateVO.warehouse_rating}"=="3급") 
                      { td += 3; } 
                      else 
                          td += 4; 
                      }
                      
                      else if("${storageRateVO.warehouse_rating}"!="" &&"${storageRateVO.warehouse_rating}"=="저온")//저온창고 보관료 요율 설정이 있을경우
                      {
                        tab_tr= $("#lowTemp_warehouse_table tr");
                        tab_tf= $("#lowTemp_warehouse_table tfoot");
                        tr = 1;
                      if("${storageRateVO.warehouse_region}"=="특지")
                      { td = 1; } 
                      else if("${storageRateVO.warehouse_region}"=="갑지") 
                      { td =2; } 
                      else 
                        td = 3;
                      }
                    tab_tr.eq(tr).children().eq(td).text("${storageRateVO.white_rice_rate}");
                    tab_tr.eq(tr+1).children().eq(td).text("${storageRateVO.brown_rice_rate}");
                    tab_tr.eq(tr+2).children().eq(td).text("${storageRateVO.rice_rate}");
                    $(tab_tf).children().eq(0).children().eq(td).append("<input type='button' id='btn_update' value='수정' onclick='fn_storage_update(${storageRateVO.storage_rate_seq_num})'>");
                    $(tab_tf).children().eq(1).children().eq(td).append("<input type='button' id='btn_update' value='삭제' onclick='fn_storage_delete(${storageRateVO.storage_rate_seq_num})'>");
                  </c:forEach>
                }
              
            <c:forEach var="cargoRateVO" items="${cargoRateList}">
            if(${cargoRateVO.rate_year}==rate_year)
            {
              if("${cargoRateVO.wrap_sort}"!="" &&"${cargoRateVO.wrap_sort}"!="톤백") {
                tab_tr= $("#normal_cargo_table tr");
                tab_tf= $("#normal_cargo_table tfoot");
                tr = 2;
                if("${cargoRateVO.wrap_sort}"=="10")
                {
                  td = 1;
                }
                else if("${cargoRateVO.wrap_sort}"=="20")
                {
                  td = 2;
                }
                else
                {
                  td = 3;
                }
                tab_tr.eq(tr).children().eq(td).text("${cargoRateVO.load_rate}");
                tab_tr.eq(tr+1).children().eq(td).text("${cargoRateVO.unload_rate}");
                tab_tr.eq(tr+2).children().eq(td).text("${cargoRateVO.warehousing_rate}");
                tab_tr.eq(tr+3).children().eq(td).text("${cargoRateVO.release_rate}");
                tab_tr.eq(tr+4).children().eq(td).text("${cargoRateVO.purchase_warehousing_rate}");
                tab_tr.eq(tr+5).children().eq(td).text("${cargoRateVO.purchase_load_rate}");
              }
              else{
                tab_tr= $("#tonBag_cargo_table tr");
                tab_tf= $("#tonBag_cargo_table tfoot");
                tab_tr.eq(1).children().eq(1).text("${cargoRateVO.bag_purchase_rate}");
                tr = 2;
                td = 1;
                tab_tr.eq(tr).children().eq(td).text("${cargoRateVO.purchase_warehousing_rate}");
                tab_tr.eq(tr+1).children().eq(td).text("${cargoRateVO.purchase_load_rate}");
                tab_tr.eq(tr+2).children().eq(td).text("${cargoRateVO.load_rate}");
                tab_tr.eq(tr+3).children().eq(td).text("${cargoRateVO.unload_rate}");
                tab_tr.eq(tr+4).children().eq(td).text("${cargoRateVO.warehousing_rate}");
                tab_tr.eq(tr+5).children().eq(td).text("${cargoRateVO.release_rate}");
              }
              tab_tr.eq(tr+6).children().eq(td).text("${cargoRateVO.security_rate}");
              tab_tr.eq(tr+7).children().eq(td).text("${cargoRateVO.transfer_rate}");
              tab_tr.eq(tr+8).children().eq(td).text("${cargoRateVO.migration_20m_rate}");
              tab_tr.eq(tr+9).children().eq(td).text("${cargoRateVO.migration_50m_rate}");
              $(tab_tf).children().eq(0).children().eq(td).append("<input type='button' id='btn_update' value='수정' onclick='fn_cargo_update(${cargoRateVO.cargo_rate_seq_num})'>");
              $(tab_tf).children().eq(1).children().eq(td).append("<input type='button' id='btn_update' value='삭제' onclick='fn_cargo_delete(${cargoRateVO.cargo_rate_seq_num})'>");
            }
            </c:forEach>
            var num;
            for(var i=0; i<$("table tbody").length; i++)
            {
              for(var j=0; j<$("table tbody").eq(i).find('tr').length; j++)
              {
                for(var k=0; k<$("table tbody").eq(i).find('tr').eq(j).find('td').length; k++)
                {
                  num = $("table tbody").eq(i).find('tr').eq(j).find('td').eq(k).text();
                  num = numberFormat(num);
                  $("table tbody").eq(i).find('tr').eq(j).find('td').eq(k).text(num);
                }
              }
            }
              });
            });

            function numberFormat(num) {
                return num.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }

            function fn_storage_update(obj){
              var storage_rate_seq_num = $("<input type='hidden' name='storage_rate_seq_num' value="+obj+">");
              updateForm(storage_rate_seq_num);
            }
            function fn_cargo_update(obj){
              
              var cargo_rate_seq_num = $("<input type='hidden' name='cargo_rate_seq_num' value="+obj+">");
              updateForm(cargo_rate_seq_num);
            }
            function updateForm(obj)
            {
              var form = $("<form></form>");
              form.append(obj);
              form.attr("method", 'post');
              form.attr("action", '${contextPath}/system/rate/updateForm.do');
              form.appendTo('body');
              form.submit();
            }

            function fn_storage_delete(obj){
              var form = $("<form></form>");
              var storage_rate_seq_num = $("<input type='hidden' name='storage_rate_seq_num' value="+obj+">");
              form.append(storage_rate_seq_num);
              form.attr("method", 'post');
              form.attr("action", '${contextPath}/system/storageRate/delete.do');
              form.appendTo('body');
              form.submit();
            }
            function fn_cargo_delete(obj){
              
              var form = $("<form></form>");
              var cargo_rate_seq_num = $("<input type='hidden' name='cargo_rate_seq_num' value="+obj+">");
              form.append(cargo_rate_seq_num);
              form.attr("method", 'post');
              form.attr("action", '${contextPath}/system/cargoRate/delete.do');
              form.appendTo('body');
              form.submit();
            }      
      
    </script>
    <title>요율 설정 확인</title>
  </head>
  <body>
    <h1>요율 설정 확인</h1>
    날짜 : <select name='year'>
              <option value="선택" selected disabled>선택</option>
              <c:forEach var="year" items="${yearList}">
                <option>${year}</option>
              </c:forEach>
           </select>년 2월 기준
    <div id="div_table" style="display:none">
      <h3>일반창고 양곡 보관료</h3>
      <table border="1" id="normal_warehouse_table">
        <thead>
          <tr>
            <th rowspan="2">구분</th>
            <th colspan="5">특지</th>
            <th colspan="5">갑지</th>
            <th colspan="5">을지</th>
          </tr>
          <tr>
            <c:forEach begin="1" end="3"><th>특급</th><th>1급</th><th>2급</th><th>3급</th><th>등외</th></c:forEach>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>쌀</td>
            <c:forEach begin="1" end="15"><td></td></c:forEach>
          </tr>
          <tr>
            <td>현미</td>
            <c:forEach begin="1" end="15"><td></td></c:forEach>
          </tr>
          <tr>
            <td>벼</td>
            <c:forEach begin="1" end="15"><td></td></c:forEach>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td>수정</td>
            <c:forEach begin="1" end="15"><td></td></c:forEach>
          </tr>
          <tr>
            <td>삭제</td>
            <c:forEach begin="1" end="15"><td></td></c:forEach>
          </tr>
        </tfoot>
      </table>
      <h3>저온창고 양곡 보관료</h3>
      <table border="1" id="lowTemp_warehouse_table">
        <thead>
          <tr>
            <th>구분</th>
            <th>특지</th>
            <th>갑지</th>
            <th>을지</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>쌀</td>
            <c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>현미</td>
            <c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>벼</td>
            <c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td>수정</td>
            <c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>삭제</td>
            <c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
        </tfoot>
      </table>
      <h3>양곡 일반 하역료</h3>
      <table border="1" id="normal_cargo_table">
        <thead>
          <tr>
            <th rowspan="2">항목</th><th colspan="3">요율</th>
          </tr>
          <tr>
            <th>지대 10kg</th><th>지대 20kg</th><th>지대 10·20kg 외</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>상차료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>하차료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>입고료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>출고료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>매입장소입고료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>매입직송상차료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>매입장소경비료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>이적료</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>이송료20m~50m</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
            <td>이송료50m~</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
        </tbody>
        <tfoot>
          <tr>
          <td>수정</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
          <tr>
          <td>삭제</td><c:forEach begin="1" end="3"><td></td></c:forEach>
          </tr>
        </tfoot>
      </table>
      <h3>양곡 톤백 하역료</h3>
      <table border="1" id="tonBag_cargo_table">
        <thead>
          <tr>
            <th>항목</th><th>요율</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>톤백 매입료</td><td></td>
          </tr>
          <tr>
            <td>톤백 매입장소입고료</td><td></td>
          </tr>
          <tr>
            <td>톤백 매입직송상차료</td><td></td>
          </tr>
          <tr>
            <td>톤백 상차료</td><td></td>
          </tr>
          <tr>
            <td>톤백 하차료</td><td></td>
          </tr>
          <tr>
            <td>톤백 입고료</td><td></td>
          </tr>
          <tr>
            <td>톤백 출고료</td><td></td>
          </tr>
          <tr>
            <td>톤백 매입장소경비료</td><td></td>
          </tr>
          <tr>
            <td>톤백 이적료</td><td></td>
          </tr>
          <tr>
            <td>톤백 이송료20m~50m</td><td></td>
          </tr>
          <tr>
            <td>톤백 이송료50m~</td><td></td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
          <td>수정</td><td></td>
          </tr>
          <tr>
          <td>삭제</td><td></td>
          </tr>
        </tfoot>
      </table>
    </div>
  </body>
</html>