<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
label {
	margin-top: 10px;
}
</style>
<h1>교수용 회원가입</h1>

<form:form method="post" modelAttribute="professor">

	<label>교수번호 </label>
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
	<form:select path="d_id">
		<form:option value="1" label="소프트웨어공학과" />
		<form:option value="2" label="컴퓨터공학과" />
		<form:option value="3" label="정보통신공학과" />
		<form:option value="4" label="글로컬it학과" />
	</form:select>

	<div>
		<input type="submit" class="btn btn-primary" value="저장" />
	</div>
</form:form>
