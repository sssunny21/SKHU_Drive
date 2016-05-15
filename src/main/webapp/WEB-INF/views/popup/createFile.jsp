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
</style>
<h1>파일 업로드</h1>
<form:form method="post" enctype="multipart/form-data">
	<label>폴더 선택</label>
	
    <label>파일 선택</label>
    	<input type="file" id="file" name="file">
	<div>
		<input type="submit" class="btn btn-primary" value="만들기" />
		<input type="button" class="btn" onclick="window.close()" value="닫기" />
	</div>
</form:form>

