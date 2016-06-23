<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    label { margin-top: 10px; }
</style>

<h1>로그인</h1>
<hr />

<form method="POST" action="login_processing.pd">
    <label>로그인ID</label>
    <input type="text" name="u_id"/>
	<br>
    <label>비밀번호</label>
    <input type="password" name="u_pw" />

    <hr />
    <button type="submit" class="btn btn-primary">
        <i class="icon-check icon-white"></i> 로그인
   </button>
         <a href="sechPW.pd">비밀번호찾기</a>
         <a href="join_main.pd">회원가입</a>
</form>

<c:if test="${ param.error != null }">
    <div class="alert alert-error">로그인 실패</div>
</c:if>


