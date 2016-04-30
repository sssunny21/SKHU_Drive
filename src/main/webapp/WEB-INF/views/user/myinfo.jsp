<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
label {
	margin-top: 10px;
}
</style>

<h2>내 정보 수정</h2>
<hr />

<form:form method="post" modelAttribute="user">

	<label>로그인ID</label>
	<form:input path="u_id" />
	<br>
	<label>비밀번호</label>
	<form:input path="u_pw" />
	<br>
	<label>이름</label>
	<form:input path="u_name" />
	<br>
	<label>생년월일</label>
	<form:input path="u_birth" />
	<br>
	<label>전화번호</label>
	<form:input path="u_tel" />
	<br>
	<div>
		<button type="submit" class="btn btn-primary">
			<i class="icon-ok icon-white"></i> 저장
		</button>
	</div>
</form:form>
