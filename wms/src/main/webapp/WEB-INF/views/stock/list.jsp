<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	$(function(){
		$("#select_wh").change(function(){
			$("#stock_table").empty();
			var wh_name = this.value;

			$.ajax({
				type : "post",
				async : "true",
				url : "${contextPath}/stock/getList.do",
				dataType : "Text",
				data : {warehouse_name : wh_name},
				beforeSend : function(xhr)
		          {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
		              xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		          },
				success: function(data, textStatus) {
					var stockVO = JSON.parse(data);
					var str = '<thead>'
							+ '<th>년산</th>'
							+ '<th>곡종</th>'
							+ '<th>단량</th>'
							+ '<th>수량</th>'
							+ '<th>포대수</th>'
							+ '</thead>';
					if (stockVO.length != 0) {
						$.each(stockVO,function(i){
							str += '<tr>'
								+ "<input type='hidden' name='stock_seq_num' value="+stockVO[i].stock_seq_num+">"
								+ '<td>'+stockVO[i].stock_year+'</td>'
								+ '<td>'+stockVO[i].stock_sort2+stockVO[i].stock_sort1+'</td>'
								+ '<td>'+stockVO[i].stock_unit+'</td>'
								+ '<td>'+stockVO[i].stock_quantity_40kg+'</td>'
								+ '<td>'+stockVO[i].stock_quantity_bag+'</td></tr>';
						});
					} else {
						str += '<tr><td colspan="5">선택된 창고에 재고가 없습니다.</td></tr>';
					}
					$("#stock_table").append(str);
					for(var i=0; i<$("#stock_table tr").length; i++)
					{
						for(var j=0; j<$("#stock_table tr").eq(i).find('td').length;j++)
						{
							var num;
							num = $("#stock_table tr").eq(i).find('td').eq(j).text();
							num = numberFormat(num);
							$("#stock_table tr").eq(i).find('td').eq(j).text(num);
						}
					}
				},
				error: function(data, textStatus) {
					alert("에러가 발생했습니다. 다시 시도해주세요.");
				},
				complete: function(data, textStatus) {
					//alert("작업을완료 했습니다");
				}
			});
		});
	});

	function numberFormat(num) {
   		return num.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

	function fn_update(obj){
		var tr = $(obj).parent().parent();
		var form = $("<form></form>");
		var stock_seq_num = $("<input type='hidden' name='stock_seq_num' value="+tr.children().eq(0).val()+">");
		form.append(stock_seq_num);
		var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
		form.append(input);
		form.attr("method", 'post');
		form.attr("action", '${contextPath}/stock/updateForm.do');
		form.appendTo('body');
		form.submit();
	};

	function fn_delete(obj){
		var tr = $(obj).parent().parent();
		var form = $("<form></form>");
		var stock_seq_num = $("<input type='hidden' name='stock_seq_num' value="+tr.children().eq(0).val()+">");
		form.append(stock_seq_num);
		var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
		form.append(input);
		form.attr("method", 'post');
		form.attr("action", '${contextPath}/stock/delete.do');
		form.appendTo('body');
		form.submit();
	}

</script>
<title>재고 현황</title>
</head>
<body>
	<h1>창고 재고 현황</h1>
	<p></p>
	<c:choose>
		<c:when test="${not empty warehouseList }">
			창고 : <select id="select_wh" name="warehouse_name">
						<option selected disabled>선택</option>
		            <c:forEach var="warehouse" items="${warehouseList}">
		                <option>${warehouse.warehouse_name}</option>
		            </c:forEach>
		        </select><br>
		    <p></p>
			<table id="stock_table" border="1">
			</table>
		        
		</c:when>
		<c:otherwise>
			등록된 창고가 없습니다.<br>
			<a href="${contextPath}/warehouse/register.do">여기</a>를 클릭하여 창고등록창으로 이동해주세요.<br>
			<input type="button" value="창고 등록" onclick="location.href='${contextPath}/warehouse/register.do'">
		</c:otherwise>
	
	</c:choose>
	
	
	
</body>
</html>