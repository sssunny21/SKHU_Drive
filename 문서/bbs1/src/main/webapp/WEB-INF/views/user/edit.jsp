<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>edit</title>
<style>
	label {margin-top:20px}
</style>
</head>
<body>
<div class="container">
	<h1>사용자 정보 수정</h1>
	
	<form:form method="post" modelAttribute="user">
		<label>ID</label>
		<span readonly>${user.id }</span>
		<br>
		<label>로그인ID</label>
		<form:input path="loginId"/>
		<br>
		
		<label>이름</label>
		<form:input path="name"/>
		<br>
		
        <label>이메일</label>
		<form:input path="email"/>
		<br>
		
        <label>사용자 유형</label>
        <form:select path="userType">
        	<form:option value="관리자" />
        	<form:option value="교수" />
        	<form:option value="학생" />
        </form:select>
		<br>
		
        <label>학과</label>
        <form:select path="departmentId">
        	<form:options itemValue="id" itemLabel="departmentName" items="${department }" />
        </form:select>
        
		<br>
		<div>
			<input type="submit" class="btn btn-primary" value="저장" />
			<a href="list.esun" class="btn btn-primary">목록가기</a>
		</div>
	</form:form>
	
	<c:if test="${not empty error }">
		<div class="alert alert-error">${error }</div>
	</c:if>
	<c:if test="${not empty success }">
		<div class="alert alert-success">${success }</div>
	</c:if>
</div>
</body>
</html>