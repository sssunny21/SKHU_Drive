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
<h1>폴더 수정</h1>
<form:form method="post" modelAttribute="folder">


	<label>폴더 이름 </label>
	<form:input path="folder_name" />
	<br>

	<input type="submit" class="btn btn-primary" value="수정" />
	<input type="button" class="btn" onclick="window.close()" value="닫기" />
</form:form>

