<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <script>
      var msg = "";
      var loginCheck = "";
      $(document).ready(function() {
        $("#member_pw_check").focusout(function() {
          var pwd1 = $("#member_pw").val();
          var pwd2 = $("#member_pw_check").val();
          if (pwd1 != "" || pwd2 != "") {
            if (pwd1 == pwd2) {
              $("#alert-success").css("display", "inline-block");
              $("#alert-danger").css("display", "none");
              msg = "";
            } else {
              msg = "비밀번호가 일치하지 않습니다. 비밀번호를 재확인해주세요.";
              $("#alert-success").css("display", "none");
              $("#alert-danger").css("display", "inline-block");
            }
          }
        });
      });
      function fn_checkID() {
        var member_id = $("#_member_id").val();
        if (member_id == "") {
          alert("ID를 입력하세요.");
          return;
        }
        $.ajax({
          type: "post",
          async: true,
          url: "${contextPath}/member/checkId.do",
          dataType: "Text",
          data: { id: member_id },
          success: function(data, textStatus) {
            if (data == "false") {
              loginCheck = "true";
              alert("사용할 수 있는 ID입니다.");
              $("#btn_checkID").prop("disabled", true);
              $("#_member_id").prop("disabled", true);
              $("#member_id").val(member_id);
            } else {
              alert("사용할 수 없는 ID입니다.");
              $("#_member_id").val("");
            }
          },
          error: function(data, textStatus) {
            alert("에러가 발생했습니다.");
          },
          complete: function(data, textStatus) {
            //alert("작업을완료 했습니다");
          }
        });
      }

      function fn_reset() {
        $("#member_id").prop("disabled", false);
      }

      function fn_submit() {
        var id = $("#member_id").val();
        var pw = $("#member_pw").val();
        var email = $("#member_email").val();
        if (!id || !pw || !email) {
          alert("모든 항목을 입력해주세요.");
          return;
        }
        if (loginCheck != "true") {
          alert("ID중복체크를 해주세요.");
          return;
        } else {
          if (msg != "") {
            alert(msg);
          } else {
            $("#frm_member").prop(
              "action",
              "${contextPath}/member/addMember.do"
            );
            $("#frm_member").prop("method", "POST");
            $("#frm_member").submit();
          }
        }
      }
    </script>
    <meta charset="UTF-8" />
    <title>회원 가입</title>
  </head>
  <body>
    <form id="frm_member">
      <p>
        아이디 :
        <input
          name="_member_id"
          id="_member_id"
          type="text"
          autocomplete="off"
        />
        <input type="hidden" name="member_id" id="member_id" />
        <input
          type="button"
          id="btn_checkID"
          value="ID중복체크"
          onclick="fn_checkID()"
        />
      </p>
      <p>
        비밀번호 : <input type="password" id="member_pw" name="member_pw" />
      </p>
      <p>
        비밀번호 재입력 :
        <input
          type="password"
          id="member_pw_check"
          placeholder="비밀번호 확인"
        />
        <span id="alert-success" style="display: none;"
          >비밀번호가 일치합니다.</span
        >
        <span
          id="alert-danger"
          style="display: none; color: #d92742; font-weight: bold; "
          >비밀번호가 일치하지 않습니다.</span
        >
      </p>
      <p>
        이메일 :
        <input
          type="email"
          name="member_email"
          id="member_email"
          autocomplete="off"
        />
      </p>
      <p>
        <input type="button" value="가입하기" onclick="fn_submit()" />
        <input type="reset" value="리셋하기" onclick="fn_reset()" />
      </p>
    </form>
  </body>
</html>
