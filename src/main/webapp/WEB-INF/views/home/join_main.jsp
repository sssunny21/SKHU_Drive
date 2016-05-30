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

<h2>비민번호 수정</h2>
<hr />
<form:form method="post" modelAttribute="user">

	<a href="/drive/home/user_join.pd">학생용 회원가입 </a>
	<br />
	<label>교수용 회원가입 </label>
	<form:input path="professor_code" />
	<input type="submit" class="btn btn-primary" value="저장" />
	<br />
</form:form>