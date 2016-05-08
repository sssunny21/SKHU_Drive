<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<h1>HOME</h1>
<hr />

<h3>현재 사용자</h3>

<sec:authorize access="authenticated">
	<table class="table table-bordered" style="width: 500px;">
		<tr>
			<td>로그인ID</td>
			<td><sec:authentication property="user.u_id" /></td>
		</tr>
		<tr>
			<td>이름</td>
			<td><sec:authentication property="user.u_name" /></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><sec:authentication property="user.u_email" /></td>
		</tr>
		<tr>
			<td>전화번호</td>
			<td><sec:authentication property="user.u_tel" /></td>
		</tr>
		<tr>
			<td>학년</td>
			<td><sec:authentication property="user.u_grade" /></td>
		</tr>
		<tr>
			<td>학과</td>
			<td><sec:authentication property="user.d_id" /></td>
		</tr>
		

	</table>
	<a href="myinfo.pd" class="btn btn-primary">개인정보 수정</a>
	
</sec:authorize>

<sec:authorize access="not authenticated">
	<p style="font-size: 14pt;">익명의 사용자</p>
</sec:authorize>