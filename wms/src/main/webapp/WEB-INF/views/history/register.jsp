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
        var loopSearch = true;
        var mouse = false;
        var warehouse_name = "${warehouse_name}";
        if (warehouse_name != "" && warehouse_name != null) {
          $("#select_wh").val(warehouse_name);
        }

        /*
        $("input[name='stock_sort1']").click(function() {
          var text = "";
          $("#sort_detail").empty();
          if (this.value == "현미") {
            text =
              "원산지 : <input type='text' id='stock_sort2' name='stock_sort2' size='10' autocomplete='off' required>";
          } else {
            text =
              "품종 : <input type='text' id='stock_sort2' name='stock_sort2' size='10' autocomplete='off' required>";
          }
          $("#sort_detail").append(text);
        });
        */

        $("input[name='history_sort1']").click(function() {
          var text = "하역 구분 : ";
          $("#history_detail").empty();
          switch (this.value) {
            case "입고":
              text +=
                "<input type='radio' id='history_sort2' name='history_sort2' value='하차' required>하차&nbsp;";
              text +=
                "<input type='radio' id='history_sort2' name='history_sort2' value='현장수매'>현장수매&nbsp;";
              text +=
                "<input type='radio' id='history_sort2' name='history_sort2' value='수매이동'>수매이동&nbsp;";
              text +=
                "<input type='radio' id='history_sort2' name='history_sort2' value='이송'>이송";
              break;
            case "출고":
              text +=
                "<input type='radio' id='history_sort2' name='history_sort2' value='상차' required>상차&nbsp;";
              text +=
                "<input type='radio' id='history_sort2' name='history_sort2' value='이송'>이송";
              break;
            case "이적":
              text +=
                "<input type='radio' id='history_sort2' name='history_sort2' value='없음' checked required>없음";
              break;
          }
          $("#history_detail").append(text);
        });

        $(document).on("click", "#history_sort2", function() {
          $("#transfer_detail").empty();
          if (this.value == "이송") {
            var text = "이송 구분 : ";
            text += "<select id='transfer_select' required>";
            text += "<option value='선택' selected disabled>선택</option>";
            text += "<option value='20'>20m초과 50m까지</option>";
            text += "<option value='50'>50m초과</option>";
            text += "</select>&nbsp;";
            text += "<span id='div_distance'></span>";
            $("#transfer_detail").append(text);
          }
        });

        $(document).on("change", "#transfer_select", function() {
          var distance = $(this).parent().find("span");
          distance.empty();
          if(this.value == "20")
        	{        	  
        	  $(document).find("input[id='history_sort2']:checked").val("이송"+this.value+"m");
        	}
          else if (this.value == "50") {
            var text =
              "거리 입력 : <input type='text' id='distance' size='3' value='70' required autocomplete='off'>(m)";
              distance.append(text);
          }
        });
        
        $(document).on("focusout", "#distance", function(){
        	$(document).find("input[id='history_sort2']:checked").val("이송"+this.value+"m");
        });
        
        $(document).on("mouseover", "#suggestList", function() {
        	mouse = true;
        });
        $(document).on("mouseout", "#suggestList", function() {
        	mouse = false;
        });
        $(document).on("focusout", "#stock_sort2", function() {
        	if(mouse == false)
        		{
        		 hide("suggest");
        		}
        });

        $(document).on("keyup", "#stock_sort2", function() {
          if (loopSearch == false) return;
          var value = this.value;
          $.ajax({
            type: "post",
            async: true, //false인 경우 동기식으로 처리한다.
            url: "${contextPath}/stock/keywordSearch.do",
            dataType: "Text",
            data: { keyword: value },
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
            },
            success: function(data, textStatus) {
              if (data != "") {
                var keyword = JSON.parse(data);
                displayResult(keyword);
              }
              else{
            	  hide("suggest");
              }
            },
            error: function(data, textStatus) {
              alert("에러가 발생했습니다." + data);
            },
            complete: function(data, textStatus) {
              //alert("작업을완료 했습니다");
            }
          }); //end ajax
        });

        function displayResult(keyword) {
          var count = keyword.length;
          if (count > 0) {
            var html = "";
            for (var i in keyword) {
              html +=
                "<a href=\"javascript:select('" +
                keyword[i] +
                "')\">" +
                keyword[i] +
                "</a><br/>";
            }
            var listView = document.getElementById("suggestList");
            listView.innerHTML = html;
            show("suggest");
          } else {
            hide("suggest");
          }
        }
      });

      function select(selectedKeyword) {
        $("#stock_sort2").val(selectedKeyword);
        loopSearch = false;
        hide("suggest");
      }

      function show(elementId) {
        var element = document.getElementById(elementId);
        if (element) {
          element.style.display = "block";
        }
      }

      function hide(elementId) {
        var element = document.getElementById(elementId);
        if (element) {
          element.style.display = "none";
        }
      }
      
    </script>
    <title>재고 관리</title>
  </head>
  <body>
    <h1>재고 관리하기</h1>

    <form
      id="frm_history"
      method="POST"
      action="${contextPath}/history/registerCheck.do"
    >
      창고 :
      <select id="select_wh" name="warehouse_name" required>
        <option value="선택" selected disabled>선택</option>
        <c:forEach var="warehouse" items="${warehouseList}">
          <option>${warehouse.warehouse_name}</option>
        </c:forEach> </select
      ><br />
      날짜 : <input type="date" name="history_date" required /><br />
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
        원산지/품종 : <input type='text' id='stock_sort2' name='stock_sort2' size='10' autocomplete='off' required>
      <div id="suggest">
        <div id="suggestList"></div>
      </div>
      <br>
      단량 :
      <select name="stock_unit" required>
        <option selected disabled>선택</option>
        <option value="10">10kg</option>
        <option value="20">20kg</option>
        <option value="40">40kg</option>
        <option value="800">800kg</option>
        <option value="1000">1000kg</option> </select
      ><br />
      입출고 구분 :
      <input
        type="radio"
        name="history_sort1"
        value="입고"
        required
      />입고&nbsp;
      <input type="radio" name="history_sort1" value="출고" />출고&nbsp;
      <input type="radio" name="history_sort1" value="이적" />이적<br />
      <div id="history_detail">
        하역 구분 : 입출고구분 선택
      </div>
      <div id="transfer_detail"></div>
      수량 :
      <input type="text" name="history_quantity" size="7" required autocomplete="off" />
      <p>
        <input type="submit" value="등록하기" />
        <input type="reset" value="취소" />
      </p>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </body>
</html>
