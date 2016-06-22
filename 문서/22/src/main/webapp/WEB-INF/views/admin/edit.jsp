<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
label {
	margin-top: 10px;
}
</style>
<h1>관리자 회원 정보 수정</h1>

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
	<label>사용자 권한</label>
	<form:select path="u_group">
		<form:option value="1" label="준회원"   />
		<form:option value="2" label="정회원"   />
		<form:option value="3" label="우수회원"  />
		<form:option value="4" label="관리자"   />
	</form:select>
	<div>
		<input type="submit" class="btn btn-primary" value="저장" />
		<a href="list.pd" class="btn btn-primary" >목록으로 나가기</a>
	</div>
	<br/>
</form:form>

