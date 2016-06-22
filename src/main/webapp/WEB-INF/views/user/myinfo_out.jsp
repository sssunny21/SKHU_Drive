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

<h2>회원 탈퇴</h2>
<hr />
<form:form method="post" modelAttribute="test">
	<label>비밀번호 </label>
	<form:input path="password" />
	
	<input type="submit" class="btn btn-primary" value="저장" />
	<br />
</form:form>