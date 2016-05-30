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

<form:form method="post" modelAttribute="user">


	<br>
	<label>비밀번호 </label>
	<input type="password" name="u_pw" />
	<br>
	<label>이름 </label>
	<form:input path="u_name" />
	<br>
	<label>전화번호 </label>
	<form:input path="u_tel" />
	<br>
	<label>이메일 </label>
	<form:input path="u_email" />
	<br>
	<label>학년 </label>
	<br>
	<label class="radio-inline"> <input type="radio" name="u_grade"
		id="1" value="1" />1
	</label>
	<label class="radio-inline"> <input type="radio" name="u_grade"
		id="2" value="2" />2
	</label>
	<label class="radio-inline"> <input type="radio" name="u_grade"
		id="3" value="3" />3
	</label>
	<label class="radio-inline"> <input type="radio" name="u_grade"
		id="4" value="4" />4
	</label>
	<br>
	<label>비밀번호 찾기 질문 </label>
	<form:input path="u_qpw" />
	<br>
	<label>비밀번호 찾기 답 </label>
	<form:input path="u_apw" />
	<br>

	<label>학과 </label>
	<form:select path="d_id">
		<form:option value="0" label="없음" />
		<form:options itemValue="d_id" itemLabel="d_name"
			items="${ department }" />
	</form:select>
	<br>
	<div>
		<input type="submit" class="btn btn-primary" value="저장" />
	</div>
</form:form>
