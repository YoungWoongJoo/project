<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function fn_getList(){
		var warehouse_name = $("#select_wh").val();
		if(warehouse_name == null)
		{
			alert("창고를 선택해주세요.");
			return;
		}
		var history_date = $("#input_date").val();
		if(history_date == '')
		{
			alert("날짜를 선택해주세요.");
			return;
		}
		$.ajax({
			type : "post",
				async : "true",
				url : "${contextPath}/history/getList.do",
				dataType : "Text",
				data : {warehouse_name : warehouse_name, history_date : history_date},
				beforeSend : function(xhr)
		          {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
		              xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
		          },
				success: function(data, textStatus) {
					if (data == "") {
						alert("에러가 발생했습니다. 다시 시도해주세요.");
						location.reload();
						return;
					}
					$("#history_table").empty();
					var historyVO = JSON.parse(data);
					var str = '<thead>'
							+ '<th>창고</th>'
							+ '<th>날짜</th>'
							+ '<th>년산</th>'
							+ '<th>곡종</th>'
							+ '<th>단량</th>'
							+ '<th>입출고</th>'
							+ '<th>하역</th>'
							+ '<th>이월량</th>'
							+ '<th>입출고량</th>'
							+ '<th>잔량</th>'
							+ '<th>삭제</th>'
							+ '</thead>';
					if (historyVO.length != 0) {
						$.each(historyVO,function(i){
							str += '<tr>'
								+ "<input type='hidden' name='history_seq_num' value="+historyVO[i].history_seq_num+">"
								+ '<td>'+historyVO[i].warehouse_name+'</td>'
								+ '<td>'+historyVO[i].history_date+'</td>'
								+ '<td>'+historyVO[i].stock_year+'</td>'
								+ '<td>'+historyVO[i].stock_sort2+historyVO[i].stock_sort1+'</td>'
								+ '<td>'+historyVO[i].stock_unit+'</td>'
								+ '<td>'+historyVO[i].history_sort1+'</td>'
								+ '<td>'+historyVO[i].history_sort2+'</td>'
								+ '<td>'+historyVO[i].stock_prev+'</td>'
								+ '<td>'+historyVO[i].history_quantity+'</td>'
								+ '<td>'+historyVO[i].stock_present+'</td>';
								if(historyVO[i].history_state == 'enable')
							{
								str += "<td></td>";
							}
							else{
								str += "<td>불가</td>";
							}
							str	+= '</tr>';
						});
					} else {
						str += '<tr><td colspan="10">선택된 창고에 관리 이력이 없습니다.</td></tr>';
					}
					$("#history_table").append(str);
					for(var i=0; i<$("#history_table tr").length; i++)
					{
						for(var j=4; j<$("#history_table tr").eq(i).find('td').length;j++)
						{
							var num;
							num = $("#history_table tr").eq(i).find('td').eq(j).text();
							num = numberFormat(num);
							$("#history_table tr").eq(i).find('td').eq(j).text(num);
						}
					}
				},
				error: function(data, textStatus) {
					alert("에러가 발생했습니다. 다시 시도해주세요.");
				},
				complete: function(data, textStatus) {
					for(var i=1; i<$("#history_table tr").length; i++)
						{
						if($("#history_table tr").eq(i).children().eq(11).text()!='불가')
						$("#history_table tr").eq(i).children().eq(11).append("<input type='button' value='삭제' onclick='fn_delete(this)'>");
						}
					
				}
		});
	}

	function fn_delete(obj){
		var tr = $(obj).parent().parent();
		var form = $("<form></form>");
		var history_seq_num = $("<input type='hidden' name='history_seq_num' value="+tr.children().eq(0).val()+">");
		form.append(history_seq_num);
		var input = $("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}'/>");
		form.append(input);
		form.attr("method", 'post');
		form.attr("action", '${contextPath}/history/delete.do');
		form.appendTo('body');
		form.submit();
	}

	function numberFormat(num) {
   		return num.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}

</script>
<title>재고 관리 내역</title>
</head>
<body>
	<h1>재고 관리 내역</h1>
	<p></p>
	<c:choose>
		<c:when test="${not empty warehouseList }">
			창고 : <select id="select_wh" name="warehouse_name">
						<option selected disabled>선택</option>
		            <c:forEach var="warehouse" items="${warehouseList}">
		                <option>${warehouse.warehouse_name}</option>
		            </c:forEach>
				</select>&nbsp;
			날짜 : <input id="input_date" type="month" name="history_date" required>&nbsp;
			<input type="button" value="조회" onclick="fn_getList()">
			<br>
		    <p></p>
			<table id="history_table" border="1">
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