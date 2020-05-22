<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8" isELIgnored="false"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
  <head>
    <script>
      var msg = "";
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

      function fn_submit() {
        var email = $("#member_email").val();
        if (!email) {
          alert("이메일을 입력해주세요.");
          return;
        } else {
          if (msg != "") {
            alert(msg);
          } else {
            $("#frm_member").prop(
              "action",
              "${contextPath}/member/updateMember.do"
            );
            $("#frm_member").prop("method", "POST");
            $("#frm_member").submit();
          }
        }
      }
    </script>
    <meta charset="UTF-8" />
    <title>회원 정보 수정</title>
  </head>
  <body>
    <form id="frm_member">
      <p>
        아이디 :
        <input
          name="_member_id"
          id="member_id"
          type="text"
          value="${memberVO.member_id}"
          size="20"
          disabled
        />
        <input type="hidden" name="member_id" value="${memberVO.member_id }" />
      </p>
      <p>
        비밀번호 : <input type="password" id="member_pw" name="member_pw" size="20"/>
      </p>
      <p>
        비밀번호 재입력 :
        <input
          type="password"
          id="member_pw_check"
          placeholder="비밀번호 확인"
          size="20"
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
          value="${memberVO.member_email}"
          autocomplete="off"
        />
      </p>
      <p>
        <input type="button" value="수정하기" onclick="fn_submit()" />
        <input type="reset" value="리셋하기" onclick="fn_reset()" />
      </p>
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </body>
</html>
