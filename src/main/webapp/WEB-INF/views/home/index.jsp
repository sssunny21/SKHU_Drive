<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>HOME</h1>
<hr />

<h3>현재 사용자</h3>

<sec:authorize access="authenticated">
<!--
   <p>로그인했슴다</p>
   <ul>
   <c:forEach var="professor" items="${professor}">
      <a href="/drive/pdrive/main.pd?d_id=${professor.d_id }" class="btn btn-primary" role="button">
      ${professor.p_name }</a>
   </c:forEach>
   </ul>
-->
</sec:authorize>

<sec:authorize access="not authenticated">
   <p style="font-size: 14pt;">로그인 후 이용부탁드립니다.</p>
</sec:authorize>