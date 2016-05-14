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

<h2>내 정보 수정</h2>
<hr />

<form:form method="post" modelAttribute="professor">


	<label>학번 </label>
	<form:input path="p_id" />
	<br>
	<label>비밀번호 </label>
	<input type="password" name="p_pw" />
	<br>
	<label>이름 </label>
	<form:input path="p_name" />
	<br>
	<label>전화번호 </label>
	<form:input path="p_tel" />
	<br>
	<label>이메일 </label>
	<form:input path="p_email" />

	<br>
	<label>비밀번호 찾기 질문 </label>
	<form:input path="p_qpw" />
	<br>
	<label>비밀번호 찾기 답 </label>
	<form:input path="p_aqw" />
	<br>
	<label>학과 </label>
	<form:select path="d_id2">
		<form:option value="" label="없음" />
		<form:options itemValue="d_id" itemLabel="d_name" items="${ department }" />
	</form:select>
	<br>
	<div>
		<input type="submit" class="btn btn-primary" value="저장" />
	</div>
</form:form>
