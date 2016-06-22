<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<style>
	label {margin-top:10px;}
</style>
    <h1>회원가입</h1>

    <form:form method="post" modelAttribute="user">
        <label>로그인ID</label>
        <input type="text" name="u_id" />
        <br>
        <label>비밀번호</label>
        <input type="password" name="u_pw"/>
		<br>
        <label>이름</label>
        <input type="text" name="u_name"/>
		<br>
        <label>생년월일</label>
        <input type="text" name="u_birth" />
		<br>
        <label>전화번호</label>
        <input type="text" name="u_tel"/>
		<br>
        <div>
            <input type="submit" class="btn btn-primary" value="저장" />
        </div>
    </form:form>
	