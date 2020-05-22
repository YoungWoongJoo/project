<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
	$(document).ready(function(){
		$("#select_wh").change(function() {
			$("#storage_bill_table").prop("style", "display:none");
			$("#cargo_bill_table").prop("style", "display:none");
			$("#div_bill").prop("style", "display:none");
			$("#btn_download").hide();
			$("#storage_bill_table tfoot").children().eq(0).children().eq(2).text("");
			$("#cargo_bill_table tfoot").children().eq(0).children().eq(2).text("");
          var warehouse_name = this.value;
          $.ajax({
            type: "post",
            async: true,
            url: "${contextPath}/stock/selectMonth.do",
            dataType: "Text",
            data: { warehouse_name: warehouse_name },
            beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
            },
            success: function(data) {
              var stock_month = JSON.parse(data)
			  $("select[name='stock_month']").empty();
			  $("select[name='stock_month']").append("<option value='선택' selected disabled>선택</option>");
              $.each(stock_month,function(i){
				var option = "<option>"+stock_month[i]+"</option>";
				$("select[name='stock_month']").append(option);
			  });
            },
            error: function(data, textStatus) {
              //alert("에러가 발생했습니다. 다시 시도해주세요.");
            },
            complete: function(data, textStatus) {
              //alert("작업을완료 했습니다");
            }
          })
        })
        
        $("#select_month").change(function() {
        	$("#storage_bill_table").prop("style", "display:none");
			$("#cargo_bill_table").prop("style", "display:none");
			$("#div_bill").prop("style", "display:none");
			$("#btn_download").hide();
        });

		$("#btn_bill").click(function(){
			$("#storage_bill_table").prop("style", "display:none");
			$("#cargo_bill_table").prop("style", "display:none");
			$("input[name='bill']").attr("checked",false);
			$("#storage_bill_table tbody").empty();
			$("#cargo_bill_table tbody").empty();
			var warehouse_name = $("#select_wh").val();
			if(warehouse_name == null)
			{
				alert("창고를 선택해주세요.");
				return;
			}
			var stock_month = $("#select_month").val();
			if(stock_month == null)
			{
				alert("날짜를 선택해주세요.");
				return;
			}
			$("#div_bill").prop("style", "display:block");
			$.ajax({
				type : "post",
				url : "${contextPath}/bill/calc.do",
				data : {warehouse_name : warehouse_name, stock_month : stock_month},
				dataType : "text",
				beforeSend : function(xhr)
	            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
	                xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
	            },
				success : function(data){
					var map = JSON.parse(data);
					var storageBill = map.storageBill;
					var cargoBill = map.cargoBill;
					var storageBillError = map.storageBillError;
					var cargoBillError = map.cargoBillError;
					var txt = "";
					var totalPrice = 0;
					if(storageBillError==null&&cargoBillError==null)
					{
						$("#btn_download").show();
					}
					if(storageBill!=null)
					{
						for(var i=0; i<storageBill.length;i++)
						{	
							txt += "<tr>";
							for(var j=0; j<13; j++)
							{
								if(j<5)
								{
									txt += "<td>";
								}
								else
								{
									txt += "<td style='text-align: right'>";
								}
								if(storageBill[i][j]!=null)
								{
									var num = "";
									if(j>=5&&j!=11||j==2)
									{
										txt += numberFormat(storageBill[i][j]);
									}
									else
									{
										txt += storageBill[i][j];
									}
								}
								txt += "</td>";
							}
							txt += "</tr>";
							if(storageBill[i][12]!=null)
							{
								totalPrice += parseInt(storageBill[i][12]);
							}
						}
					}
					else if(storageBillError!=null){
						txt += "<tr><td colspan='13'>";
						txt += storageBillError;
						txt += "</td></tr>";
					}
					$("#storage_bill_table tbody").append(txt);
					$("#storage_bill_table tfoot").children().eq(0).children().eq(2).text(totalPrice.toLocaleString());
					totalPrice = 0;
					txt = "";

					if(cargoBillError!=null)
					{
						txt += "<tr><td colspan='15'>";
						txt += cargoBillError;
						txt += "</td></tr>";
					}
					else if(cargoBill.length==0&&cargoBillError==null)
					{
						txt += "<tr><td colspan='15'>";
						txt += "청구할 부대비 내역이 없습니다.";
						txt += "</td></tr>";
					}
					else if(cargoBill.length!=0)
					{
						for(var i=0; i<cargoBill.length;i++)
						{	
							txt += "<tr>";
							for(var j=0; j<15; j++)
							{
								if(j<4)
								{
									txt += "<td>";
								}
								else
								{
									txt += "<td style='text-align: right'>";
								}
								if(cargoBill[i][j]!=null)
								{
									var num = "";
									if(j>=4||j==2)
									{
										txt += numberFormat(cargoBill[i][j]);
									}
									else
									{
										txt += cargoBill[i][j];
									}
								}
								txt += "</td>";
							}
							txt += "</tr>";
							if(cargoBill[i][14]!=null)
							{
								totalPrice += parseInt(cargoBill[i][14]);
							}
						}
					}
					$("#cargo_bill_table tbody").append(txt);
					$("#cargo_bill_table tfoot").children().eq(0).children().eq(2).text(totalPrice.toLocaleString());
				}
			});
		});

		$("input[name='bill']").click(function(){
			if(this.value==1)
			{
				$("#cargo_bill_table").hide();
				$("#storage_bill_table").show();
			}
			else{
				$("#storage_bill_table").hide();
				$("#cargo_bill_table").show();
			}
		});
	})

	function downloadExcel()
	{
		var form = $("<form></form>");
		form.attr("method", 'post');
        form.attr("action", '${contextPath}/bill/downloadExcel.do');
		var input = "";
		var warehouse_name = $("#select_wh").val();
		input = $("<input type='hidden' name='warehouse_name' value=" + warehouse_name + ">");
		form.append(input);
		var stock_month = $("#select_month").val();
		input = $("<input type='hidden' name='stock_month' value=" + stock_month + ">");
		form.append(input);
		input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
		form.append(input);

        form.appendTo('body');
        form.submit();
	}

	function numberFormat(num) {
   		return num.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

</script>
<title>보관임/부대비 청구서</title>
</head>
<body>
	<h1>보관임/부대비 청구서</h1>
	창고 :
      <select id="select_wh" name="warehouse_name" required>
			<option value="선택" selected disabled>선택</option>
			<c:forEach var="warehouse" items="${warehouseList}">
			<option>${warehouse.warehouse_name}</option>
			</c:forEach>
		</select>&nbsp;
	날짜 : <select id="select_month" name="stock_month" required><br>
			<option value="선택" selected disabled>선택</option>
			</select>&nbsp;
	<input type="button" id="btn_bill" value="정산 및 조회">&nbsp;
	<input type="button" id="btn_download" value="Excel로 저장" onclick="downloadExcel()" style="display:none">
	<div id="div_bill" style="display:none">
		<input type="radio" id="storage_bill" name="bill" value="1">보관료 청구서&nbsp;
		<input type="radio" id="cargo_bill" name="bill" value="2">부대비 청구서
		<div id="storage_bill_table" style="display:none">
			<h3>보관료 청구서</h3>
			<table border="1">
				<thead>
					<th>년산</th>
					<th>곡종</th>
					<th>단량</th>
					<th>보관기간</th>
					<th>보관일수</th>
					<th>이월량</th>
					<th>입고량</th>
					<th>출고량</th>
					<th>잔량</th>
					<th>적수</th>
					<th>적수중량</th>
					<th>요율</th>
					<th>금액</th>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<td>합계</td>
					<td colspan="11"></td>
					<td></td>
				</tfoot>
			</table>
		</div>
		<div id="cargo_bill_table" style="display:none">
			<h3>부대비 청구서</h3>
			<table border="1">
				<thead>
					<th>년산</th>
					<th>곡종</th>
					<th>단량</th>
					<th>작업월일</th>
					<th>수매장소입고료</th>
					<th>입고량</th>
					<th>출고량</th>
					<th>하차량</th>
					<th>상차량</th>
					<th>수매장소상차량</th>
					<th>수매경비료</th>
					<th>톤백매입료</th>
					<th>환산중량</th>
					<th>요율</th>
					<th>금액</th>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<td>합계</td>
					<td colspan="13"></td>
					<td></td>
				</tfoot>
			</table>
		</div>
	</div>
</body>
</html>