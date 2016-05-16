<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<sec:authorize access="authenticated">
<div class="col-sm-3 col-md-2 sidebar">
	<ul class="nav nav-sidebar">
		<div class="dropdown">
			<button class="btn btn-default dropdown-toggle" type="button"
				id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="true">
				학과 선택 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
				<li><a href="/drive/pdrive/main.pd?d_id=1">소프트웨어공학과</a></li>
				<li><a href="/drive/pdrive/main.pd?d_id=2">컴퓨터공학과</a></li>
				<li><a href="/drive/pdrive/main.pd?d_id=3">정보통신공학과</a></li>
				<li><a href="/drive/pdrive/main.pd?d_id=4">글로컬it학과</a></li>
			</ul>
		</div>

		<li><a href="#">Google_Drive</a></li>
		<li><a href="#">One_Drive</a></li>
	</ul>
</div>
</sec:authorize>