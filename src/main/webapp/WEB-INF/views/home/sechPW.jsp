<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
   prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
label {
   margin-top: 10px;
}
</style>

<h2>비밀번호 찾기</h2>

<form:form method="post" modelAttribute="user">

   <label>학번</label>
   <form:input path="u_id" />
   <br />
   <label>비밀번호 찾기 질문</label>
   <form:input path="u_qpw" />
   <br />
   <label>비밀번호 찾기 답 </label>
   <form:input path="u_apw" />
   <br />

   <input type="submit" class="btn btn-primary" value="저장" />

   <br />

</form:form>