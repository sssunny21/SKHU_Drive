<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
label {
	margin-top: 10px;
}

div {
	margin-left: 20px;
}

#share_t {
	display: none;
}
</style>
<h1>폴더 만들기</h1>
<form:form method="post" modelAttribute="folder">
	<label>공유 여부 </label>
	<div class="checkbox">
		<input type="checkbox" id="share_tn">공유폴더
	</div>
	<div id="share_n">
		<label>폴더 이름 </label>
		<form:input path="folder_name" />
		<br>
	</div>
	<form:hidden path="drive_id" />
	
	<div id="share_t">
	<label>폴더 이름 </label>
	<form:input path="sfolder_name" />
	<br>
	<label>폴더 암호 </label>
	<form:input path="sfolder_pw"/>
	</div>
	<div>
		<input type="submit" class="btn btn-primary" value="만들기" /> <input
			type="button" class="btn" onclick="window.close()" value="닫기" />
	</div>
</form:form>

