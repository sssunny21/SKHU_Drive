<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>HOME</h1>
<hr />

<sec:authorize access="authenticated">
<a><sec:authentication property="user.u_name" />님 환영합니다.</a>
</sec:authorize>

<sec:authorize access="not authenticated">
   <p style="font-size: 14pt;">로그인 후 이용부탁드립니다.</p>
</sec:authorize>