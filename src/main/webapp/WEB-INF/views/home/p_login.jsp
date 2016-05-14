<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
label {
	margin-top: 10px;
}
</style>

<h1>교수용 로그인</h1>
<hr />

<form method="POST" action="p_login_processing.pd">
	<label>로그인ID</label> <input type="text" name="p_id" /> 
	<br> 
	<label>비밀번호</label>
	<input type="password" name="p_pw" />

	<hr />
	<div>
		<input type="submit" class="btn btn-primary" value="저장" /> 
	</div>
</form>

<c:if test="${ param.error != null }">
	<div class="alert alert-error">로그인 실패</div>
</c:if>
